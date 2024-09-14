package br.com.socketchat.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import br.com.socketchat.R;
import br.com.socketchat.model.User;
import br.com.socketchat.viewModel.UserViewModel;

public class CriarUsuarioActivity extends AppCompatActivity implements TextWatcher {

    private EditText etCriarUsuario;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_usuario);
        initViews();

        btnSalvar.setOnClickListener(v -> {
            saveUser();
        });
    }

    private void initViews(){
        etCriarUsuario = findViewById(R.id.et_criarUsuario);
        btnSalvar = findViewById(R.id.btn_salvar);
        etCriarUsuario.addTextChangedListener(this);
    }

    private void saveUser(){

        UserViewModel userViewModel = new ViewModelProvider.AndroidViewModelFactory(
                getApplication()).create(UserViewModel.class);

        User user = new User(etCriarUsuario.getText().toString());
        userViewModel.insert(user);

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