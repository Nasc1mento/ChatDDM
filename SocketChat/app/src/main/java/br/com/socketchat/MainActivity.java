package br.com.socketchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class MainActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRepository = new UserRepository(getApplication());

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
         != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        EditText editText = findViewById(R.id.editText);
        findViewById(R.id.enterbtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            String name = editText.getText().toString();
            intent.putExtra("name", name);
            userRepository.insert(new User(name));
            startActivity(intent);
        });
    }
}