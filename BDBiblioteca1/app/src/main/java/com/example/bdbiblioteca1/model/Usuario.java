package com.example.bdbiblioteca1.model;

import com.example.bdbiblioteca1.utils.PasswordUtils;
import java.util.regex.Pattern;

public class Usuario {
    private String nome;
    private String senha;
    private String nivel;
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    public Usuario(String nome, String senha, String nivel) {
        this.nome = nome;
        this.senha = PasswordUtils.generateSecurePassword(senha); // Agora criptografa a senha automaticamente!
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivel() {
        return nivel;
    }

    public void trocarSenha(String novaSenha) {
        if (novaSenha != null && PATTERN.matcher(novaSenha).matches()) {
            this.senha = PasswordUtils.generateSecurePassword(novaSenha);
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Senha inválida!");
        }
    }
}
