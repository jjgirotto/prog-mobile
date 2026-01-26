package com.example.teste2_juliana_2025116166;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teste2_juliana_2025116166.model.AppDatabase;
import com.example.teste2_juliana_2025116166.model.Filme;
import com.example.teste2_juliana_2025116166.model.FilmeDao;

public class DetalhesFilmeActivity extends AppCompatActivity {

    public static final String VALUE_ID = "VALUE_ID";
    private int id;
    private AppDatabase db;

    TextView titulo, realizador, tipo, duracao, ano, actor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_filme);

        Toolbar t = findViewById(R.id.toolbar_det);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt(VALUE_ID);
        }

        db = AppDatabase.getDatabase(this);
        FilmeDao filmeDao = db.filmeDao();
        Filme f = filmeDao.getById(id);

        t.setTitle(f.getTitulo());
        titulo = findViewById(R.id.et_titulo);
        realizador = findViewById(R.id.et_realizador);
        actor = findViewById(R.id.et_actor);
        duracao = findViewById(R.id.et_duracao);
        tipo = findViewById(R.id.et_tipo_filme);
        ano = findViewById(R.id.et_ano);

        titulo.setText(f.getTitulo());
        realizador.setText(f.getRealizador());
        tipo.setText(f.getTipoFilme());
        actor.setText(f.getActorPrincipal());
        duracao.setText(String.valueOf(f.getDuracao()));
        ano.setText(String.valueOf(f.getAno()));

    }

}