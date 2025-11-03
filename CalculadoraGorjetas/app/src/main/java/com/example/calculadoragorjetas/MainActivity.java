package com.example.calculadoragorjetas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText valorConta, numeroPessoas;
    RadioButton excelente, bom, ruim;
    double valor;
    int pessoas, qualidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolMain);
        setSupportActionBar(toolbar);
        valorConta = findViewById(R.id.inputValorConta);
        numeroPessoas = findViewById(R.id.inputNumeroPessoas);
        excelente = findViewById(R.id.radioExcelente);
        bom = findViewById(R.id.radioBom);
        ruim = findViewById(R.id.radioRuim);

        Button btnCalcular = findViewById(R.id.btnCalcular);
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lerNumeros()) {
                    if (verificarQualidade() > 0) {
                        qualidade = verificarQualidade();
                        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                        intent.putExtra(ResultActivity.VALOR_CONTA, valor);
                        intent.putExtra(ResultActivity.NUMERO_PESSOAS, pessoas);
                        intent.putExtra(ResultActivity.QUALIDADE, qualidade);
                        startActivity(intent);
                    }
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
        if (id == R.id.menu_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private int verificarQualidade() {
        if (excelente.isChecked())
            return 20;
        else if (bom.isChecked())
            return 15;
        else if (ruim.isChecked())
            return 10;
        else {
            Toast.makeText(this, "Selecione uma opção!", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private boolean lerNumeros() {
        try {
            valor = Double.parseDouble(valorConta.getText().toString());
            pessoas = Integer.parseInt(numeroPessoas.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preencha o valor com decimais e o número de pessoas com inteiros!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}