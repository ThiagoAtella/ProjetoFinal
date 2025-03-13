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
		String CREATE_TABLE_LIVROS = "CREATE TABLE " + TABLE_LIVROS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_GENERO + " TEXT, "
				+ COLUMN_TITULO + " TEXT)";

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

	// Inserir usuário
	public void inserirUsuario(Usuario usuario) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_NOME, usuario.getNome());
		values.put(COLUMN_SENHA, PasswordUtils.generateSecurePassword(usuario.getSenha())); // Agora criptografa a senha!
		values.put(COLUMN_NIVEL, usuario.getNivel());

		db.insert(TABLE_USUARIOS, null, values);
		db.close();
	}

	// Buscar usuário pelo nome
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

	// Autenticar usuário
	public boolean autenticarUsuario(String nome, String senhaDigitada) {
		Usuario usuario = getUsuario(nome);
		return usuario != null && PasswordUtils.verifyPassword(senhaDigitada, usuario.getSenha());
	}

	// Verificar se é administrador
	public boolean isAdmin(String nome) {
		Usuario usuario = getUsuario(nome);
		return usuario != null && "adm".equals(usuario.getNivel());
	}
}
