package com.example.euro2dolar_julianaleite_2025116166;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    RadioButton radioEuro, radioDolar;
    EditText inputValor;
    double valor, resultado;
    ImageView imgFlag, imgFlagResultado;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputValor = findViewById(R.id.inputValor);

        radioEuro = findViewById(R.id.radioEuro);
        radioDolar = findViewById(R.id.radioDolar);
        imgFlag = findViewById(R.id.imgFlag);
        imgFlagResultado = findViewById(R.id.imgFlagResultado);
        txtResultado = findViewById(R.id.txtResultado);

        radioDolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFlag.setImageResource(R.drawable.eu);
                imgFlagResultado.setImageResource(R.drawable.usa);
            }
        });

        radioEuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgFlag.setImageResource(R.drawable.usa);
                imgFlagResultado.setImageResource(R.drawable.eu);
            }
        });

        Button btnConverter = findViewById(R.id.btnConverter);
        btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (converter()) {
                    calcularResultado();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean converter() {
        try {
            valor = Double.parseDouble(inputValor.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.dados_invalidos, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void calcularResultado() {
        if (radioDolar.isChecked()) {
            resultado = valor / 0.87;
            txtResultado.setText(String.format("%.2f €", resultado));
        } else if (radioEuro.isChecked()) {
            resultado = valor * 0.87;
            txtResultado.setText(String.format("%.2f $", resultado));
        } else {
            Toast.makeText(this, R.string.escolha_opcao, Toast.LENGTH_SHORT).show();
        }
    }
}