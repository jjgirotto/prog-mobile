package com.example.teste2_juliana_2025116166;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teste2_juliana_2025116166.model.AppDatabase;
import com.example.teste2_juliana_2025116166.model.Filme;
import com.example.teste2_juliana_2025116166.model.FilmeDao;

public class AdicionarFilmeActivity extends AppCompatActivity {

    EditText etTitulo, etRealizador, etTipo, etActor, etDuracao, etAno;
    Button btnEnviar;
    String titulo, realizador, tipo, actor;
    int duracao, ano;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_filme);

        Toolbar t = findViewById(R.id.toolbar_add);
        setSupportActionBar(t);

        etTitulo = findViewById(R.id.et_titulo);
        etRealizador = findViewById(R.id.et_realizador);
        etTipo = findViewById(R.id.et_tipo_filme);
        etActor = findViewById(R.id.et_actor);
        etDuracao = findViewById(R.id.et_duracao);
        etAno = findViewById(R.id.et_ano);

        db = AppDatabase.getDatabase(this);

        btnEnviar = findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lerFilme()) {
                    FilmeDao filmeDao = db.filmeDao();
                    filmeDao.insert(new Filme(titulo, realizador, tipo, actor, duracao, ano));
                    Toast.makeText(AdicionarFilmeActivity.this, "Filme inserido com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdicionarFilmeActivity.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private boolean lerFilme() {
        try {
            titulo = etTitulo.getText().toString();
            realizador = etRealizador.getText().toString();
            tipo = etTipo.getText().toString();
            actor = etActor.getText().toString();
            duracao = Integer.parseInt(etDuracao.getText().toString());
            ano = Integer.parseInt(etAno.getText().toString());
            if (titulo.isEmpty() || realizador.isEmpty() || tipo.isEmpty() || actor.isEmpty()) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}