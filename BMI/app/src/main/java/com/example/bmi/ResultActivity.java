package com.example.bmi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    public static final String VALUE_HEIGHT = "VALUE_HEIGHT";
    public static final String VALUE_WEIGHT = "VALUE_WEIGHT";
    double bmi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repeat();
        calcularBmi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(ResultActivity.this, HelpActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_send_sms) {
            enviarSMS();
            return true;
         /* Adicione também “getSupportActionBar().setDisplayHomeAsUpEnabled(true);” ao seu método
        onCreate */
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void calcularBmi() {
        TextView txtResult = findViewById(R.id.txtResult);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return; //isto não deverá acontecer, mas só por precaução
        }
        double altura = extras.getDouble(VALUE_HEIGHT);
        double peso = extras.getDouble(VALUE_WEIGHT);
        bmi = peso / (altura * altura);
        txtResult.setText(String.format("%.2f", bmi));
        verificarCategoria(bmi);
    }

    private void verificarCategoria (double bmi) {
        TextView txtCategoria = findViewById(R.id.txtBmiResult);
        int text;
        int color;
        if (bmi <= 18.5) {
            text = R.string.underweight;
            color = R.color.underweight;
        } else if (bmi > 18.5 && bmi <= 24.9) {
            text = R.string.normalweight;
            color = R.color.normalweight;
        } else {
            text = R.string.overweight;
            color = R.color.overweight;
        }
        txtCategoria.setText(text);
        txtCategoria.setBackgroundColor(ContextCompat.getColor(this, color));
    }

    private void repeat() {
        Button btnRepeat = findViewById(R.id.btnRepeat);
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void enviarSMS() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("sms:"));
        intent.putExtra(Intent.EXTRA_TEXT, String.format("My BMI result is %.2f", bmi));
        startActivity(intent);

    }
}