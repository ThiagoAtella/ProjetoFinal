package com.example.bdbiblioteca1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bdbiblioteca1.database.BibliotecaDatabase;
import com.example.bdbiblioteca1.model.Livro;
import com.example.bdbiblioteca1.model.Usuario;

import java.util.List;

public class CrudActivity extends AppCompatActivity {
    private EditText etNomeTitulo, etSenhaGenero, etNivel;
    private RadioButton rbUsuario, rbLivro;
    private Button btnSalvar, btnAtualizar, btnDeletar, btnBuscar;
    private BibliotecaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        etNomeTitulo = findViewById(R.id.etNomeTitulo);
        etSenhaGenero = findViewById(R.id.etSenhaGenero);
        etNivel = findViewById(R.id.etNivel);
        rbUsuario = findViewById(R.id.rbUsuario);
        rbLivro = findViewById(R.id.rbLivro);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        btnDeletar = findViewById(R.id.btnDeletar);
        btnBuscar = findViewById(R.id.btnBuscar);
        db = new BibliotecaDatabase(this);

        rbUsuario.setOnClickListener(v -> alternarCampos(true));
        rbLivro.setOnClickListener(v -> alternarCampos(false));

        btnSalvar.setOnClickListener(v -> {
            if (rbUsuario.isChecked()) {
                salvarUsuario();
            } else {
                salvarLivro();
            }
        });

        btnAtualizar.setOnClickListener(v -> {
            if (rbUsuario.isChecked()) {
                atualizarUsuario();
            } else {
                atualizarLivro();
            }
        });

        btnDeletar.setOnClickListener(v -> {
            if (rbUsuario.isChecked()) {
                deletarUsuario();
            } else {
                deletarLivro();
            }
        });

        btnBuscar.setOnClickListener(v -> {
            if (rbUsuario.isChecked()) {
                buscarUsuario();
            } else {
                buscarLivro();
            }
        });
    }

    private void alternarCampos(boolean isUsuario) {
        etNivel.setVisibility(isUsuario ? View.VISIBLE : View.GONE);
    }

    private void salvarUsuario() {
        String nome = etNomeTitulo.getText().toString();
        String senha = etSenhaGenero.getText().toString();
        String nivel = etNivel.getText().toString();

        if (nome.isEmpty() || senha.isEmpty() || nivel.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.inserirUsuario(new Usuario(nome, senha, nivel));
        Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show();
    }

    private void salvarLivro() {
        String titulo = etNomeTitulo.getText().toString();
        String genero = etSenhaGenero.getText().toString();

        if (titulo.isEmpty() || genero.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.inserirLivro(new Livro(titulo, 0, genero));
        Toast.makeText(this, "Livro cadastrado!", Toast.LENGTH_SHORT).show();
    }

    private void atualizarUsuario() {
        String nome = etNomeTitulo.getText().toString();
        String senha = etSenhaGenero.getText().toString();
        String nivel = etNivel.getText().toString();

        if (nome.isEmpty() || senha.isEmpty() || nivel.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.atualizarUsuario(new Usuario(nome, senha, nivel));
        Toast.makeText(this, "Usuário atualizado!", Toast.LENGTH_SHORT).show();
    }

    private void atualizarLivro() {
        String titulo = etNomeTitulo.getText().toString();
        String genero = etSenhaGenero.getText().toString();
        String idStr = etNivel.getText().toString();

        if (titulo.isEmpty() || genero.isEmpty() || idStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);
        db.atualizarLivro(new Livro(titulo, id, genero));
        Toast.makeText(this, "Livro atualizado!", Toast.LENGTH_SHORT).show();
    }

    private void deletarUsuario() {
        String nome = etNomeTitulo.getText().toString();

        if (nome.isEmpty()) {
            Toast.makeText(this, "Informe o nome!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.deletarUsuario(nome);
        Toast.makeText(this, "Usuário deletado!", Toast.LENGTH_SHORT).show();
    }

    private void deletarLivro() {
        String idStr = etNivel.getText().toString();

        if (idStr.isEmpty()) {
            Toast.makeText(this, "Informe o código do livro!", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = Integer.parseInt(idStr);
        db.deletarLivro(id);
        Toast.makeText(this, "Livro deletado!", Toast.LENGTH_SHORT).show();
    }

    private void buscarUsuario() {
        String nome = etNomeTitulo.getText().toString();

        Usuario usuario = db.getUsuario(nome);
        if (usuario != null) {
            etSenhaGenero.setText(usuario.getSenha());
            etNivel.setText(usuario.getNivel());
        } else {
            Toast.makeText(this, "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarLivro() {
        String titulo = etNomeTitulo.getText().toString();

        List<Livro> livrosEncontrados = db.buscarLivroPorTitulo(titulo);

        if (!livrosEncontrados.isEmpty()) {
            Livro livro = livrosEncontrados.get(0);
            etNomeTitulo.setText(livro.getTitulo());
            etSenhaGenero.setText(livro.getGenero());
            etNivel.setText(String.valueOf(livro.getCod()));
        } else {
            Toast.makeText(this, "Livro não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
}
