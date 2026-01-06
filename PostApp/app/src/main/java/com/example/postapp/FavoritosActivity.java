package com.example.postapp;

import android.hardware.lights.LightState;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.model.AppDatabase;
import com.example.postapp.model.FavPostAdapter;
import com.example.postapp.model.Post;
import com.example.postapp.model.PostDao;

import java.util.List;

public class FavoritosActivity extends AppCompatActivity {

    private AppDatabase db;
    RecyclerView rv;

    private FavPostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        db = AppDatabase.getDataBase(this);
        PostDao dao = db.postDao();
        List<Post> postsFav = dao.getFavPosts();
        rv = findViewById(R.id.rv_fav);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavPostAdapter(postsFav);
        rv.setAdapter(adapter);

    }
}