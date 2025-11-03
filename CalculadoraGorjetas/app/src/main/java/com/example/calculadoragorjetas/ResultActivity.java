package com.example.calculadoragorjetas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class ResultActivity extends AppCompatActivity {

    public static final String VALOR_CONTA = "VALOR_CONTA";
    public static final String NUMERO_PESSOAS = "NUMERO_PESSOAS";
    public static final String QUALIDADE = "QUALIDADE";

    TextView txtGorjeta, txtTotalPessoa, txtQualidade;

    int numeroPessoas, qualidade;
    double valorConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolResult);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtGorjeta = findViewById(R.id.txtGorjeta);
        txtTotalPessoa = findViewById(R.id.txtTotalPessoa);
        txtQualidade = findViewById(R.id.txtQualidade);
        Button btnVoltar = findViewById(R.id.btnCalcularNovamente);

        calcularGorjeta();
        btnVoltar.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {
            Intent intent = new Intent(ResultActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Este comando fecha a Activity atual e volta para a anterior
        return true;
    }

    private void calcularGorjeta() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        valorConta = extras.getDouble(VALOR_CONTA);
        numeroPessoas = extras.getInt(NUMERO_PESSOAS);
        qualidade = extras.getInt(QUALIDADE);

        double gorjetaTotal = valorConta * ((double) qualidade / 100);
        double totalComGorjeta = valorConta + gorjetaTotal;
        double totalPorPessoa = totalComGorjeta / numeroPessoas;
        txtGorjeta.setText("€ " + String.format("%.2f", gorjetaTotal));
        txtTotalPessoa.setText("€ " + String.format("%.2f", totalPorPessoa));
        atualizarQualidade();
    }

    private void atualizarQualidade() {
        if (qualidade == 20) {
            txtQualidade.setText(R.string.Qexcelente);
            txtQualidade.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        } else if (qualidade == 15) {
            txtQualidade.setText(R.string.Qbom);
            txtQualidade.setBackgroundColor(ContextCompat.getColor(this, R.color.amarelo));
        } else if (qualidade == 10) {
            txtQualidade.setText(R.string.Qruim);
            txtQualidade.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }
    }
}