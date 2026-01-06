package com.example.eventapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.model.AppDataBase;
import com.example.eventapp.model.User;
import com.example.eventapp.model.UserAdapter;
import com.example.eventapp.model.UserDao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String VALUE_EMAIL = "VALUE_EMAIL";
    private static final String VALUE_SENHA = "VALUE_SENHA";
    private static final String VALUE_USER_TYPE = "VALUE_USER_TYPE";
    private static final String VALUE_NOTIF = "VALUE_NOTIF";
    private AppDataBase db;
    private TextView tvGreeting;
    String email, password;
    Spinner tipoPart;
    SwitchCompat notif;
    Button btnRegister;
    private UserAdapter adapter;
    private List<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDataBase.getDataBase(this);
        Toolbar t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tipoPart = findViewById(R.id.userType);
        notif = findViewById(R.id.notifications);
        btnRegister = findViewById(R.id.btn_cadastrar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString(VALUE_EMAIL);
            password = extras.getString(VALUE_SENHA);
        }
        tvGreeting = findViewById(R.id.tv_greeting);
        String greetingText = "Hello, " + email;
        tvGreeting.setText(greetingText);

        lerValores();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValores();
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                i.putExtra(VALUE_EMAIL, email);
                i.putExtra(VALUE_SENHA, password);
                startActivity(i);
            }
        });

        RecyclerView rv = findViewById(R.id.rv_part);
        rv.setLayoutManager(new LinearLayoutManager(this));
        UserDao dao = db.userDao();
        list = dao.getAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        adapter = new UserAdapter(list);
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.novo) {
            Intent i = new Intent(MainActivity.this, RegisterActivity.class);
            i.putExtra(VALUE_EMAIL, email);
            startActivity(i);
            return true;
        } else if (id == R.id.api) {
            Intent i = new Intent(MainActivity.this, ApiActivity.class);
            i.putExtra(VALUE_EMAIL, email);
            startActivity(i);
            return true;
        } else if (id == R.id.sair) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void lerValores() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userTypeGuardado = sharedPref.getString(VALUE_USER_TYPE, "");
        boolean notifGuardado = sharedPref.getBoolean(VALUE_NOTIF, true);
        tipoPart.setSelection(getIndexTipo(userTypeGuardado));
        notif.setChecked(notifGuardado);
    }

    private int getIndexTipo(String value) {
        for (int i = 0; i < tipoPart.getCount(); i++) {
            if (tipoPart.getItemAtPosition(i).toString().equals(value)) {
                return i;
            }
        }
        return 0;
    }

    private void guardarValores() {
        String userType = tipoPart.getSelectedItem().toString();
        boolean notific = notif.isChecked();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALUE_USER_TYPE, userType);
        edit.putBoolean(VALUE_NOTIF, notific);
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        List<User> novaLista = db.userDao().getAll();
        Toast.makeText(this, "Users: " + novaLista.size(), Toast.LENGTH_SHORT).show();
        adapter = new UserAdapter(novaLista);
        RecyclerView rv = findViewById(R.id.rv_part);
        rv.setAdapter(adapter);
    }


}