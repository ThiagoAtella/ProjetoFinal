package com.example.bdbiblioteca1.utils;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bdbiblioteca1.R;
import com.example.bdbiblioteca1.database.BibliotecaDatabase;
import com.example.bdbiblioteca1.model.Usuario;


public class LoginActivity extends AppCompatActivity{

    private EditText etLoginNome;
    private EditText etLoginSenha;
    private EditText etConfirmaSenha;
    private Button btnLogin;
    private BibliotecaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginNome = findViewById(R.id.etLoginNome);
        etLoginSenha = findViewById(R.id.etLoginSenha);
        etConfirmaSenha = findViewById(R.id.etConfirmaSenha);
        btnLogin = findViewById(R.id.btnLogin);

        db = new BibliotecaDatabase(this);

        // Evento de clique no botão de cadastro
        btnLogin.setOnClickListener(v -> {
            String username = etLoginNome.getText().toString().trim();
            String password = etLoginSenha.getText().toString().trim();
            String confirmPassword = etConfirmaSenha.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(LoginActivity.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            // Criação do novo usuário
            Usuario novoUsuario = new Usuario(username, password, "cliente"); // Nível padrão: cliente
            db.inserirUsuario(novoUsuario);

            Toast.makeText(LoginActivity.this, "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
            finish();  // Finaliza a activity de cadastro e retorna à tela de login
        });
    }
}
