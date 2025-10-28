package com.example.conversortemperatura;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    double numero, temperaturaFinal;
    EditText inputTemperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        converterTemperatura();

    }

    private void converterTemperatura() {
        TextView txtResultado = findViewById(R.id.txtResultado);
        Button btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {
                if (lerNumero()) {
                    double resultado = obterTemperatura();
                    txtResultado.setText(String.format("%.2f", resultado));
                }
            }
        });
    }

    private boolean lerNumero() {
        inputTemperatura = findViewById(R.id.inputTemperatura);
        try {
            numero = Double.parseDouble(inputTemperatura.getText().toString());
            return true;
        } catch (NumberFormatException e ) {
            Toast.makeText(MainActivity.this, "Inválido! Apenas números decimais.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    public double obterTemperatura () {
        RadioButton rCelsius = findViewById(R.id.radioCelsius);
        RadioButton rFahrenheit = findViewById(R.id.radioFahrenheit);
        if(rCelsius.isChecked()) {
            temperaturaFinal = (numero - 32) / 1.8;
        } else if (rFahrenheit.isChecked()) {
            temperaturaFinal = (numero * 1.8) + 32;
        } else {
            Toast.makeText(this, "Selecione uma conversão!", Toast.LENGTH_SHORT).show();
        }
        return temperaturaFinal;
    }
}