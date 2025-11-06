package com.example.euro2dolar_julianaleite_2025116166;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText utilizador, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utilizador = findViewById(R.id.inputUtilizador);
        senha = findViewById(R.id.inputSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnSobre = findViewById(R.id.btnSobre);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()) {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean login() {
        String user = utilizador.getText().toString();
        String password = senha.getText().toString();
        if (!user.isEmpty() && !password.isEmpty()) {
            if (user.equals("demo")){
                if (password.equals("test")){
                    return true;
                } else{
                    Toast.makeText(this, R.string.credenciais, Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else{
                Toast.makeText(this, R.string.credenciais, Toast.LENGTH_SHORT).show();
                return false;
            }
        } else{
            Toast.makeText(this, R.string.campos, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}