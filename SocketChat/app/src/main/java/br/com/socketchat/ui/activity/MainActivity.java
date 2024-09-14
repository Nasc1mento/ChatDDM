package br.com.socketchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import br.com.socketchat.R;
import br.com.socketchat.model.User;
import br.com.socketchat.viewModel.UserViewModel;

public class MainActivity extends AppCompatActivity {


    private UserViewModel userViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                getApplication()).create(UserViewModel.class);

        userViewModel.getUserLiveData().observe(this, user -> {
            this.user = user;
        });

        requestPermissions();
        setChatBtnListener();
        setProfileBtnListener();
    }

    private void requestPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
    }

    private void setChatBtnListener() {
        findViewById(R.id.enterbtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            Log.d("NOMEUSER", "aqui " + user.getName());
            intent.putExtra("name", user.getName());
            startActivity(intent);
        });
    }

    private void setProfileBtnListener() {
        findViewById(R.id.btn_perfil).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getName());
            startActivity(intent);
        });
    }
}