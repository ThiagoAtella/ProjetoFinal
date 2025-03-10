package com.example.bdbiblioteca1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bdbiblioteca1.model.Livro;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "biblioteca.bd";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_LIVROS = "livros";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_GENERO = "genero";
	private static final String COLUMN_TITULO = "titulo";

	public BibliotecaDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_LIVROS + "("
				+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ COLUMN_GENERO + " TEXT, "
				+ COLUMN_TITULO + " TEXT)";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIVROS);
		onCreate(db);
	}

	public void inserirLivro(Livro livro) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GENERO, livro.getGenero());
		values.put(COLUMN_TITULO, livro.getTitulo());

		db.insert(TABLE_LIVROS, null, values);
		db.close();
	}

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
}
