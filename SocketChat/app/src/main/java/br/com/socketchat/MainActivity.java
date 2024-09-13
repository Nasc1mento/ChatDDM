package br.com.socketchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class MainActivity extends AppCompatActivity {

    private UserRepository userRepository;
    private LiveData<User> userLiveData;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRepository = new UserRepository(getApplication());


        userLiveData = userRepository.getSingleUser();

        userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    MainActivity.this.user = new User(user.getId(), user.getName());
                }
            }
        });

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
         != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);

        findViewById(R.id.enterbtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            Log.d("NOMEUSER", "aqui " + user.getName());
            intent.putExtra("name", user.getName());
            startActivity(intent);
        });

        findViewById(R.id.btn_perfil).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getName());
            startActivity(intent);
        });
    }
}