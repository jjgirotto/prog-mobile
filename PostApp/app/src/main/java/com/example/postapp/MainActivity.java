package com.example.postapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.model.Post;
import com.example.postapp.model.PostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    RecyclerView rv;
    String username;
    private static final String VALUE_USERNAME = "VALUE_USERNAME";
    private List<Post> posts;
    private static final String SERVICE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    PostAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar t = findViewById(R.id.toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv = findViewById(R.id.rv_main);
        rv.setLayoutManager(new LinearLayoutManager(this));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString(VALUE_USERNAME);
        }
        String text = "Bem vindo, " + username;
        t.setTitle(text);
        posts = new ArrayList<>();
        adapter = new PostAdapter(posts);
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
        else if (id == R.id.favoritos) {
            Intent i = new Intent(MainActivity.this, FavoritosActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            loadItems();
        } catch (Exception e) {
            Log.e("Error getting items from remote service", e.toString());
        }
    }
    private void loadItems() {
        Request request = new Request.Builder()
                .url(SERVICE_URL)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) return;
                String json = response.body().string();
                List<Post> novosPosts = new ArrayList<>();
                try {
                    JSONArray array = new JSONArray(json);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id");
                        int userId = object.getInt("userId");
                        String title = object.getString("title");
                        String body = object.getString("body");
                        novosPosts.add(new Post(id, userId, title, body, 0));
                    }
                    runOnUiThread(() -> {
                        posts.clear();
                        posts.addAll(novosPosts);
                        adapter.notifyDataSetChanged();
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}