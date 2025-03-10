package com.example.bdbiblioteca1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bdbiblioteca1.model.Livro;
import com.example.bdbiblioteca1.model.Usuario;
import com.example.bdbiblioteca1.utils.PasswordUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String senhaSegura = PasswordUtils.generateSecurePassword("MinhaSenhaForte123");
        System.out.println("Senha armazenada: " + senhaSegura);
        boolean valida = PasswordUtils.verifyPassword("MinhaSenhaForte123", senhaSegura);
        System.out.println("Senha correta? " + valida);

        Usuario usuario = new Usuario("João", "Senha123", "adm");

        // Tenta trocar a senha
        usuario.trocarSenha("NovaSenha123");

        // Verifica se a senha informada está correta
        System.out.println(usuario.verificarSenha("NovaSenha123")); // true
        System.out.println(usuario.verificarSenha("Senha123"));     // false
    }
}
