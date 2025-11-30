package com.example.lista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VisualAnimalActivity extends AppCompatActivity {

    TextView txtNome, txtHabitat;
    ImageView imgAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_animal);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String nome = extras.getString("nome");
            String habitat = extras.getString("habitat");
            int foto = extras.getInt("foto");
            txtNome = findViewById(R.id.txtNome);
            txtHabitat = findViewById(R.id.textoDescricaoHabitat);
            imgAnimal = findViewById(R.id.imgAnimal);
            txtNome.setText(nome);
            txtHabitat.setText(habitat);
            imgAnimal.setImageResource(foto);
        }

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}