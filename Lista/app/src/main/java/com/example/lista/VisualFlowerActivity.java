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

public class VisualFlowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_flower);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String nome = extras.getString("nome");
            String cuidados = extras.getString("cuidados");
            String mes = extras.getString("mes");
            int foto = extras.getInt("foto");
            TextView txtNome = findViewById(R.id.txtNome);
            TextView txtCuidados = findViewById(R.id.textoDescricaoCuidados);
            TextView txtMesCultivo = findViewById(R.id.txtMesCultivo);
            ImageView imgAnimal = findViewById(R.id.imgFlor);
            txtNome.setText(nome);
            txtCuidados.setText(cuidados);
            txtMesCultivo.setText(mes);
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