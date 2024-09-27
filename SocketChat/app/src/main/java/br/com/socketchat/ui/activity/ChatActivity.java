package br.com.socketchat.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import br.com.socketchat.model.Message;
import br.com.socketchat.network.WebSocketManager;
import br.com.socketchat.network.api.ApiService;
import br.com.socketchat.network.api.RetrofitClient;
import br.com.socketchat.repository.MessageRepository;
import br.com.socketchat.ui.adapter.MessageAdapter;
import br.com.socketchat.R;
import br.com.socketchat.utils.ImageUtils;
import br.com.socketchat.utils.ToastUtils;

public class ChatActivity extends AppCompatActivity implements TextWatcher, WebSocketManager.IWebSocketListener {

    private final int IMAGE_REQUEST_ID = 1;

    private String name;
    private EditText messageEdit;
    private View sendBtn, pickImgBtn;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private WebSocketManager webSocketManager;
    private MessageRepository messageRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        
        name = getIntent().getStringExtra("name");
        initializeView();
        webSocketManager = new WebSocketManager(this);
        this.messageRepository = new MessageRepository();
        loadHistory();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webSocketManager.closeConnection();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable s) {

        String string = s.toString().trim();
        
        if (string.isEmpty()){
            resetMessageEdit();
        } else {
            sendBtn.setVisibility(View.VISIBLE);
            pickImgBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void resetMessageEdit() {

        messageEdit.removeTextChangedListener(this);
        messageEdit.setText("");
        sendBtn.setVisibility(View.INVISIBLE);
        pickImgBtn.setVisibility(View.VISIBLE);

        messageEdit.addTextChangedListener(this);

    }

    @Override
    public void onMessageReceived(JSONObject jsonObject) {
        runOnUiThread( () -> {
            try {
                jsonObject.put("isSent", false);
                messageAdapter.addItem(jsonObject);
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onConnectionOpened() {
        runOnUiThread( () -> ToastUtils.toast(ChatActivity.this, "Conectado"));

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "join");
            jsonObject.put("name", name);
            webSocketManager.sendMessage(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionClosed() {
        runOnUiThread( () -> ToastUtils.toast(ChatActivity.this, "Desconectado"));
    }

    @Override
    public void onError(String message) {
        runOnUiThread( () -> ToastUtils.toast(ChatActivity.this, message));
    }

    private void initializeView() {

        messageEdit = findViewById(R.id.messageEdit);
        sendBtn = findViewById(R.id.btnenviar);
        pickImgBtn = findViewById(R.id.pickImgBtn);
        recyclerView = findViewById(R.id.recyclerView);

        messageAdapter = new MessageAdapter((getLayoutInflater()));
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageEdit.addTextChangedListener(this);

        sendBtn.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("message", messageEdit.getText().toString());

                // ################# Start API Call ##########################
                Message message = new Message(name,messageEdit.getText().toString());
                messageRepository.post(message, new MessageRepository.MessageRepositoryCallback<Message>() {

                    @Override
                    public void onSuccess(Message message) {}

                    @Override
                    public void onError(String message) {
                        ToastUtils.toast(ChatActivity.this, "Failed to persist message");
                    }
                });
                // ################# END API CALL ##########################

                webSocketManager.sendMessage(jsonObject);
                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                resetMessageEdit();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        pickImgBtn.setOnClickListener( v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Pick image"),
                    IMAGE_REQUEST_ID);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK){

            try {
                InputStream is = getContentResolver().openInputStream(data.getData());
                Bitmap image = BitmapFactory.decodeStream(is);

                sendImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendImage(Bitmap image) {

        String base64StringImage = ImageUtils.encodeToBase64(image);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name);
            jsonObject.put("image", base64StringImage);
            webSocketManager.sendMessage(jsonObject);

            jsonObject.put("isSent", true);
            messageAdapter.addItem(jsonObject);

            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadHistory() {
        // ###################### Start API CALL ############
        messageRepository.getMessageHistory(new MessageRepository.MessageRepositoryCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                for (Message message : messages) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("name", message.getName());
                        jsonObject.put("message", message.getMessage());
                        jsonObject.put("isSent", false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    messageAdapter.addItem(jsonObject);
                }
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
            }

            @Override
            public void onError(String message) {
                ToastUtils.toast(ChatActivity.this, "Failed to load history");
            }
        });

    // ###################### End API CALL ############
    }
}