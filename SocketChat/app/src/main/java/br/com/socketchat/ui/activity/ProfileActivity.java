package br.com.socketchat.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.com.socketchat.R;
import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;
import br.com.socketchat.viewModel.UserViewModel;

public class ProfileActivity extends AppCompatActivity implements TextWatcher {

    private UserViewModel userViewModel;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        initViews();
        initSaveBtn();
        initDeleteBtn();
    }

    private void initViews() {
        et = findViewById(R.id.et_updateUser);
        et.setText(getIntent().getStringExtra("name"));
        et.addTextChangedListener(this);
    }

    private void initSaveBtn() {
        findViewById(R.id.save_profile_btn).setOnClickListener(v -> {
            String name = et.getText().toString();
            int id = getIntent().getIntExtra("id", 0);
            userViewModel.update(new User(id, name));
            finish();
        });
    }


    private void initDeleteBtn() {
        findViewById(R.id.delete_profile_btn).setOnClickListener(v -> {
            int id = getIntent().getIntExtra("id", 0);
            userViewModel.delete(new User(id));
            Intent intent = new Intent(this, SplashActivity.class);
            finish();
            startActivity(intent);

        });
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()) {
            et.setError("Mínimo 1 caractere");
            findViewById(R.id.save_profile_btn).setEnabled(false);
        } else {
            findViewById(R.id.save_profile_btn).setEnabled(true);
        }
    }
}
