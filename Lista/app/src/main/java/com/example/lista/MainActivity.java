package com.example.lista;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lista.classes.Animal;
import com.example.lista.classes.Flower;
import com.example.lista.classes.ListInitializer;
import com.example.lista.classes.ListaItemsAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ListaItemsAdapter adapter = new ListaItemsAdapter(ListInitializer.getList(), new ListaItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Object item) {
                if (item instanceof Animal) {
                    Animal animal = (Animal) item;
                    Intent intent = new Intent(MainActivity.this, VisualAnimalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nome", animal.getName());
                    bundle.putString("habitat", animal.getHabitat());
                    bundle.putInt("foto", animal.getPhoto());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else if (item instanceof Flower) {
                    Flower flor = (Flower) item;
                    Intent intent = new Intent(MainActivity.this, VisualFlowerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nome", flor.getName());
                    bundle.putString("cuidados", flor.getSeedingWarnings());
                    bundle.putString("mes", flor.getSeedingMonth());
                    bundle.putInt("foto", flor.getPhoto());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}