package br.com.socketchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import br.com.socketchat.R;
import br.com.socketchat.viewModel.UserViewModel;

public class SplashActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private static final int SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            hasUser();
            finish();
        }, SPLASH_DELAY);
    }

    private void hasUser(){

        userViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
                getApplication()).create(UserViewModel.class);

        userViewModel.getUserLiveData().observe(this, user -> {
            Intent i;

            if (user != null) {
                i = new Intent(this, MainActivity.class);
            } else {
                i = new Intent(this, CriarUsuarioActivity.class);
            }

            startActivity(i);
            finish();
        });
    }

}