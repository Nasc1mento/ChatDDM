package br.com.socketchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class CriarUsuarioActivity extends AppCompatActivity {

    private EditText etCriarUsuario;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_usuario);
        initViews();

        btnSalvar.setOnClickListener(v -> {
            salvarUsuario();
        });
    }

    private void initViews(){
        etCriarUsuario = findViewById(R.id.et_criarUsuario);
        btnSalvar = findViewById(R.id.btn_salvar);
    }

    private void salvarUsuario(){
        UserRepository userRepository = new UserRepository(getApplication());

        User user = new User();
        user.setName(etCriarUsuario.getText().toString());

        userRepository.insert(user);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}