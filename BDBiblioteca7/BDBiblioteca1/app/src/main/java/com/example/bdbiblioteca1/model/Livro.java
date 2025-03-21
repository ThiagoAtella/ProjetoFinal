package com.example.bdbiblioteca1.model;

public class Livro {
	private String titulo;
	private int cod;
	private String genero;

	public Livro(String titulo, int cod, String genero) {
		this.titulo = titulo;
		this.cod = cod;
		this.genero = genero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void selecionar() {
		System.out.println("Livro selecionado: " + titulo);
	}
}
