package com.example.teste2_juliana_2025116166;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teste2_juliana_2025116166.model.AppDatabase;
import com.example.teste2_juliana_2025116166.model.Filme;
import com.example.teste2_juliana_2025116166.model.FilmeAdapter;
import com.example.teste2_juliana_2025116166.model.FilmeDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;

    private RecyclerView rv;

    private List<Filme> filmes;
    private AppDatabase db;
    private FilmeDao filmeDao;
    private FilmeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar t = findViewById(R.id.toolbar_main);
        setSupportActionBar(t);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getDatabase(this);
        filmeDao = db.filmeDao();
        filmes = filmeDao.getAll();
        adapter = new FilmeAdapter(filmes);
        rv.setAdapter(adapter);

        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdicionarFilmeActivity.class);
                startActivity(i);
            }
        });
    }

}