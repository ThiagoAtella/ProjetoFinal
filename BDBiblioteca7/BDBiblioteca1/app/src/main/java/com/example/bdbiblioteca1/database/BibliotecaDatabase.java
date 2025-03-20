package com.example.bdbiblioteca1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bdbiblioteca1.model.Livro;
import com.example.bdbiblioteca1.model.Usuario;
import com.example.bdbiblioteca1.utils.PasswordUtils;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "biblioteca.bd";
	private static final int DATABASE_VERSION = 1;

	// Tabela Livros
	private static final String TABLE_LIVROS = "livros";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_TITULO = "titulo";
	private static final String COLUMN_GENERO = "genero";

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
		String CREATE_TABLE_LIVROS = "CREATE TABLE " + TABLE_LIVROS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_TITULO + " TEXT, "
				+ COLUMN_GENERO + " TEXT)";

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

	// 📌 CRUD Usuários

	public void inserirUsuario(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOME, usuario.getNome());
		values.put(COLUMN_SENHA, PasswordUtils.generateSecurePassword(usuario.getSenha()));
		values.put(COLUMN_NIVEL, usuario.getNivel());

		db.insert(TABLE_USUARIOS, null, values);
		db.close();
	}

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

	public void atualizarUsuario(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_SENHA, PasswordUtils.generateSecurePassword(usuario.getSenha()));
		values.put(COLUMN_NIVEL, usuario.getNivel());

		db.update(TABLE_USUARIOS, values, COLUMN_NOME + "=?", new String[]{usuario.getNome()});
		db.close();
	}

	public void deletarUsuario(String nome) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_USUARIOS, COLUMN_NOME + "=?", new String[]{nome});
		db.close();
	}

	// 📌 CRUD Livros

	public void inserirLivro(Livro livro) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITULO, livro.getTitulo());
		values.put(COLUMN_GENERO, livro.getGenero());

		db.insert(TABLE_LIVROS, null, values);
		db.close();
	}

	public Livro getLivro(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_LIVROS,
				new String[]{COLUMN_ID, COLUMN_TITULO, COLUMN_GENERO},
				COLUMN_ID + "=?",
				new String[]{String.valueOf(id)},
				null, null, null, null);

		if (cursor != null && cursor.moveToFirst()) {
			Livro livro = new Livro(
					cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
					cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
					cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO))
			);
			cursor.close();
			return livro;
		}
		return null;
	}

	public void atualizarLivro(Livro livro) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_TITULO, livro.getTitulo());
		values.put(COLUMN_GENERO, livro.getGenero());

		db.update(TABLE_LIVROS, values, COLUMN_ID + "=?", new String[]{String.valueOf(livro.getCod())});
		db.close();
	}

	public void deletarLivro(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LIVROS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
		db.close();
	}
	public boolean autenticarUsuario(String nome, String senhaDigitada) {
		Usuario usuario = getUsuario(nome);
		return usuario != null && PasswordUtils.verifyPassword(senhaDigitada, usuario.getSenha());
	}
	public List<Livro> buscarLivroPorTitulo(String titulo) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Livro> listaLivros = new ArrayList<>();

		Cursor cursor = db.query(TABLE_LIVROS,
				new String[]{COLUMN_ID, COLUMN_TITULO, COLUMN_GENERO},
				COLUMN_TITULO + " LIKE ?",
				new String[]{"%" + titulo + "%"},
				null, null, null);

		if (cursor.moveToFirst()) {
			do {
				Livro livro = new Livro(
						cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO)),
						cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
						cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO))
				);
				listaLivros.add(livro);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaLivros;
	}
}
