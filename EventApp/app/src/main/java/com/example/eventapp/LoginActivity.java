package com.example.eventapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventapp.model.AppDataBase;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etSenha;
    private static final String VALUE_EMAIL = "VALUE_EMAIL";
    private static final String VALUE_SENHA = "VALUE_SENHA";
    private String email, password;
    private AppDataBase db;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etSenha = findViewById(R.id.et_senha);
        login = findViewById(R.id.btn_entrar);

        lerValores();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValores();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(VALUE_EMAIL, email);
                intent.putExtra(VALUE_SENHA, password);
                startActivity(intent);
            }
        });
    }

    private void lerValores() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String emailGuardado = sharedPref.getString(VALUE_EMAIL, "");
        String senhaGuardada = sharedPref.getString(VALUE_SENHA, "");
        etEmail.setText(emailGuardado);
        etSenha.setText(senhaGuardada);
    }

    private void guardarValores() {
        email = etEmail.getText().toString();
        password = etSenha.getText().toString();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALUE_EMAIL, email);
        edit.putString(VALUE_SENHA, password);
        edit.commit();
        //testar texto toast assim
        Toast.makeText(this, R.string.toast_saved_data, Toast.LENGTH_SHORT).show();
    }
}