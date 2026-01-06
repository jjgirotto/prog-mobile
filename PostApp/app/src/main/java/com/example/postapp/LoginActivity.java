package com.example.postapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    private static final String VALUE_USERNAME = "VALUE_USERNAME";
    private static final String VALUE_SWITCH = "VALUE_SWITCH";
    String username;
    Button btnEntrar;
    boolean lembrar;
    SwitchCompat switchLembrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        switchLembrar = findViewById(R.id.switch_lembrar);
        btnEntrar = findViewById(R.id.btn_entrar);

        lerUsername();

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchLembrar.isChecked()) {
                    guardarUsername();
                }
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                i.putExtra(VALUE_USERNAME, username);
                startActivity(i);
            }
        });
    }

    private void lerUsername() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String usernameGuardado = sharedPref.getString(VALUE_USERNAME, "");
        boolean switchGuardado = sharedPref.getBoolean(VALUE_SWITCH, false);
        etUsername.setText(usernameGuardado);
        switchLembrar.setChecked(switchGuardado);
    }

    private void guardarUsername() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        username = etUsername.getText().toString();
        lembrar = switchLembrar.isChecked();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(VALUE_USERNAME, username);
        editor.putBoolean(VALUE_SWITCH, lembrar);
        editor.commit();
    }
}