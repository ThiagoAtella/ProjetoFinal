package com.example.bdbiblioteca1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bdbiblioteca1.database.BibliotecaDatabase;
import com.example.bdbiblioteca1.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private BibliotecaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        db = new BibliotecaDatabase(this);

        // Criar um usuário admin apenas se ele não existir
        if (db.getUsuario("admin") == null) {
            Usuario admin = new Usuario("admin", "Senha123", "adm");
            db.inserirUsuario(admin);
        }

        // Evento de clique no botão de login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.autenticarUsuario(username, password)) {
                Toast.makeText(MainActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                // Aqui você pode redirecionar para outra activity caso necessário
            } else {
                Toast.makeText(MainActivity.this, "Usuário ou senha incorretos!", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento de clique no texto "Esqueceu sua senha?"
        tvForgotPassword.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "Função de redefinir senha ainda não implementada", Toast.LENGTH_SHORT).show()
        );
    }
}
