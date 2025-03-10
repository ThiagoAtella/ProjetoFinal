package com.example.bdbiblioteca1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bdbiblioteca1.model.Livro;
import com.example.bdbiblioteca1.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "biblioteca.bd";
	private static final int DATABASE_VERSION = 1;
	// Tabela Livros
	private static final String TABLE_LIVROS = "livros";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_GENERO = "genero";
	private static final String COLUMN_TITULO = "titulo";

	// Tabela Usuários
	private static final String TABLE_USUARIOS = "usuarios";
	private static final String COLUMN_NOME = "nome";
	private static final String COLUMN_SENHA = "senha";
	private static final String COLUMN_NIVEL = "nivel";

	public BibliotecaDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Criando a tabela de livros
		String CREATE_TABLE_LIVROS = "CREATE TABLE " + TABLE_LIVROS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_GENERO + " TEXT, "
				+ COLUMN_TITULO + " TEXT)";
		// Criando a tabela de usuários
		String CREATE_TABLE_USUARIOS = "CREATE TABLE " + TABLE_USUARIOS + "("
				+ COLUMN_NOME + " TEXT PRIMARY KEY, "
				+ COLUMN_SENHA + " TEXT, "
				+ COLUMN_NIVEL + " TEXT)";

		db.execSQL(CREATE_TABLE_LIVROS);
		db.execSQL(CREATE_TABLE_USUARIOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVROS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
		onCreate(db);
	}
	// Método para adicionar um usuário
	public void inserirUsuario(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOME, usuario.getNome());
		values.put(COLUMN_SENHA, usuario.getSenha());  // Atenção: Senha não está criptografada!
		values.put(COLUMN_NIVEL, usuario.getNivel());

		db.insert(TABLE_USUARIOS, null, values);
		db.close();
	}

	// Método para obter um usuário pelo nome
	public Usuario getUsuario(String nome) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_USUARIOS,
				new String[]{COLUMN_NOME, COLUMN_SENHA, COLUMN_NIVEL},
				COLUMN_NOME + "=?",
				new String[]{nome},
				null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			Usuario usuario = new Usuario(
					cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME)),
					cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SENHA)),
					cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIVEL))
			);
			cursor.close();
			return usuario;
		}
		return null;
	}
	// Método para adicionar um livro
	public void inserirLivro(Livro livro) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GENERO, livro.getGenero());
		values.put(COLUMN_TITULO, livro.getTitulo());

		db.insert(TABLE_LIVROS, null, values);
		db.close();
	}

	// Método para selecionar um livro
	public List<Livro> getTodosLivros() {
		List<Livro> livros = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		String SELECT_ALL = "SELECT * FROM " + TABLE_LIVROS;
		Cursor cursor = db.rawQuery(SELECT_ALL, null);

		if (cursor.moveToFirst()) {
			do {
				Livro livro = new Livro(
						cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO)),
						cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO))
				);
				livros.add(livro);
			} while (cursor.moveToNext());
		}

		cursor.close();
		db.close();
		return livros;
	}

	// Método para verificar se um usuário é administrador
	public boolean isAdmin(String nome) {
		Usuario usuario = getUsuario(nome);
		return usuario != null && "adm".equals(usuario.getNivel());
	}
}
