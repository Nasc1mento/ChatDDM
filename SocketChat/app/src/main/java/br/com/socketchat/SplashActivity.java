package br.com.socketchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import br.com.socketchat.repository.UserRepository;

public class SplashActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private LiveData<Boolean> hasUsersLiveData;
    private static final int SPLASH_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        new Handler().postDelayed(() -> {
            verificaUsuario();
            finish();
        }, SPLASH_DELAY);
    }

    private void verificaUsuario(){
        userRepository = new UserRepository(getApplication());

        hasUsersLiveData = userRepository.hasUsers();

        hasUsersLiveData.observe(this, hasUsers -> {
            if (hasUsers != null && hasUsers) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(this, CriarUsuarioActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}