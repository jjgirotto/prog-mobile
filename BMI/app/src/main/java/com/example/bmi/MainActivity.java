package com.example.bmi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputHeight, inputWeight;
    Button btnCalcular;
    double altura, peso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);
        btnCalcular = findViewById(R.id.btnCalculate);

        lancarIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void lancarIntent() {
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lerNumeros()) {
                    //criar uma nova Intent para chamar a ResultActivity
                    //passando os valores recebidos para a atividade
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra(ResultActivity.VALUE_HEIGHT, altura);
                    intent.putExtra(ResultActivity.VALUE_WEIGHT, peso);
                    //lançar a ResultActivity
                    startActivity(intent);
                }
            }
        });
    }

    private boolean lerNumeros() {
        try {
            altura = Double.parseDouble(inputHeight.getText().toString());
            peso = Double.parseDouble(inputWeight.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Type only numbers in both fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}