package com.example.infopersonagens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    public static final String PERSONAGEM = "PERSONAGEM";
    int personagem;
    TextView info;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        info = findViewById(R.id.info);
        img = findViewById(R.id.img);
        exibirInfo();
        Button btnRepetir = findViewById(R.id.btnEscolherNovamente);
        btnRepetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            Toast.makeText(this, "You should click in one of those images in the radio button to choose a character", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.about) {
            Intent i = new Intent(ResultActivity.this, AboutActivity.class);
            startActivity(i);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exibirInfo() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        personagem = extras.getInt(PERSONAGEM);
        if (personagem == R.id.imgEmilia) {
            info.setText(R.string.info_emilia);
            img.setImageResource(R.drawable.emilia);
        } else if (personagem == R.id.imgNarizinho) {
            info.setText(R.string.info_narizinho);
            img.setImageResource(R.drawable.narizinho);
        } else if (personagem == R.id.imgPedrinho) {
            info.setText(R.string.info_pedrinho);
            img.setImageResource(R.drawable.pedrinho);
        }
    }
}