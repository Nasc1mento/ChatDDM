package br.com.socketchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import br.com.socketchat.model.User;
import br.com.socketchat.repository.UserRepository;

public class CriarUsuarioActivity extends AppCompatActivity implements TextWatcher {

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
        etCriarUsuario.addTextChangedListener(this);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty()) {
            etCriarUsuario.setError("Mínimo 1 caractere");
            btnSalvar.setEnabled(false);
        } else {
            btnSalvar.setEnabled(true);
        }
    }
}