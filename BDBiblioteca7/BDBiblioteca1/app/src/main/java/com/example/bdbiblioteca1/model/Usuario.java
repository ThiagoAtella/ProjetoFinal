package com.example.bdbiblioteca1.model;

import java.util.regex.Pattern;

public class Usuario {
    private String nome;
    private String senha;
    private String nivel;
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    private static final Pattern PATTERN = Pattern.compile(PASSWORD_REGEX);

    public Usuario(String nome, String senha, String nivel) {
        this.nome = nome;
        this.senha = senha; // Senha não será criptografada aqui, pois já é criptografada no banco.
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
            this.senha = novaSenha; // A criptografia acontece na inserção no banco
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Senha inválida! Deve conter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas e números.");
        }
    }
}
