package com.example.bdbiblioteca1;

package com.example.bdbiblioteca1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bdbiblioteca1.model.Livro;
import com.example.bdbiblioteca1.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        Usuario cliente = new Usuario("Joaquim", "15", "Gerente");
        Usuario gerente = new Usuario("Paulo", "35", "Cliente");

        Livro livro = new Livro("Senhor dos anéis", "1a2b3c4d5e");
        livro.selecionar();

        BibliotecaDatabase db = new BibliotecaDatabase(getApplicationContext());

        // Criando um novo usuário
        Usuario novoUsuario = new Usuario("joao", "Senha123", "adm");
        db.inserirUsuario(novoUsuario);

        // Verificando se um usuário é admin
        if (db.isAdmin("joao")) {
            System.out.println("Usuário é administrador!");
        } else {
        System.out.println("Usuário não é administrador.");
        }

        // Buscando um usuário pelo nome
        Usuario usuarioBuscado = db.getUsuario("joao");
        if (usuarioBuscado != null) {
            System.out.println("Usuário encontrado: " + usuarioBuscado.getNome());
        } else {
            System.out.println("Usuário não encontrado.");
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    if (username.equals("Joaquim") && password.equals("15")) {
                        Toast.makeText(MainActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Função de redefinir senha ainda não implementada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
