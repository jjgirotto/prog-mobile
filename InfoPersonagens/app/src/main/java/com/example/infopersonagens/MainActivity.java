package com.example.infopersonagens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    RadioButton radioEmilia, radioNarizinho, radioPedrinho;
    int personagemId;
    ImageView imgEmilia, imgNarizinho, imgPedrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        radioEmilia = findViewById(R.id.radioEmilia);
        radioNarizinho = findViewById(R.id.radioNarizinho);
        radioPedrinho = findViewById(R.id.radioPedrinho);
        imgEmilia = findViewById(R.id.imgEmilia);
        imgNarizinho = findViewById(R.id.imgNarizinho);
        imgPedrinho = findViewById(R.id.imgPedrinho);

        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personagemId = escolha();
                if (personagemId != 0) {
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra(ResultActivity.PERSONAGEM, personagemId);
                    startActivity(i);
                }
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
            Intent i = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(i);
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int escolha() {
        if (radioEmilia.isChecked()) {
            return imgEmilia.getId();
        } else if (radioNarizinho.isChecked()) {
            return imgNarizinho.getId();
        } else if (radioPedrinho.isChecked()) {
            return imgPedrinho.getId();
        } else {
            Toast.makeText(this, "Choose an option!", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }
}