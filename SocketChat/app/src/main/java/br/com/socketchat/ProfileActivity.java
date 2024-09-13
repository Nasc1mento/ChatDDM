package br.com.socketchat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class ProfileActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userRepository = new UserRepository(getApplication());

        initViews();
        initSaveBtn();
        initDeleteBtn();
    }

    private void initViews() {
        et = findViewById(R.id.et_updateUser);
        et.setText(getIntent().getStringExtra("name"));
    }

    private void initSaveBtn() {
        findViewById(R.id.save_profile_btn).setOnClickListener(v -> {
            String name = et.getText().toString();
            int id = getIntent().getIntExtra("id", 0);
            userRepository.update(new User(id, name));
            finish();
        });
    }


    private void initDeleteBtn() {
        findViewById(R.id.delete_profile_btn).setOnClickListener(v -> {
            int id = getIntent().getIntExtra("id", 0);
            userRepository.delete(new User(id));
            Intent intent = new Intent(this, SplashActivity.class);
            finish();
            startActivity(intent);

        });
    }


}
