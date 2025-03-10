package com.example.bdbiblioteca1.model;

public class Livro {
	private String titulo;
	private int cod;
	private String  genero;

	public Livro(String titulo, String cod) {
		this.titulo = titulo;
		this.cod = Integer.parseInt(cod);
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

	public void setCod(String cod) {
		this.cod = Integer.parseInt(cod);
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public void selecionar(){
		System.out.println("Brasil");
	}
}
