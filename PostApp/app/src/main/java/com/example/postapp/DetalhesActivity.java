package com.example.postapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postapp.model.AppDatabase;
import com.example.postapp.model.Post;
import com.example.postapp.model.PostDao;

import java.util.List;

public class DetalhesActivity extends AppCompatActivity {

    public static final String VALUE_ID = "VALUE_ID";
    public static final String VALUE_USERID = "VALUE_USERID";
    public static final String VALUE_TITLE = "VALUE_TITLE";
    public static final String VALUE_BODY = "VALUE_BODY";
    private int id, userId;
    private String title, body;
    Button btnFav;
    RecyclerView rv;
    private AppDatabase db;
    TextView tvId, tvTitle, tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        tvId = findViewById(R.id.tv_id);
        tvTitle = findViewById(R.id.tv_titulo);
        tvBody = findViewById(R.id.tv_body);
        btnFav = findViewById(R.id.btn_fav);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt(VALUE_ID);
            userId = extras.getInt(VALUE_USERID);
            title = extras.getString(VALUE_TITLE);
            body = extras.getString(VALUE_BODY);
            tvId.setText(String.valueOf(id));
            tvTitle.setText(title);
            tvBody.setText(body);
        }

        db = AppDatabase.getDataBase(this);
        PostDao dao = db.postDao();
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post postFav = new Post(id, userId, title, body,1);
                try {
                    dao.insert(postFav);
                    Toast.makeText(DetalhesActivity.this, "Salvo nos Favoritos!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(DetalhesActivity.this, "Erro: Já foi salvo ou erro no banco.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}