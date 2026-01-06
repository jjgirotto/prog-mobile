package com.example.eventapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.model.Message;
import com.example.eventapp.model.MessageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiActivity extends AppCompatActivity {

    private static final String SERVICE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();
    private MessageAdapter adapter;
    private RecyclerView rv_api;
    private static final String VALUE_EMAIL = "VALUE_EMAIL";
    private EditText etMessage;
    String userEmail;
    Button btnEnviar;
    List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        Bundle extras = getIntent().getExtras();
        if (extras != null) userEmail = extras.getString(VALUE_EMAIL);

        etMessage = findViewById(R.id.et_message);
        btnEnviar = findViewById(R.id.btn_enviar);
        btnEnviar.setOnClickListener(v -> enviarMensagem());

        rv_api = findViewById(R.id.rv_api);
        rv_api.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<>();

        adapter = new MessageAdapter(messages);
        rv_api.setAdapter(adapter);

        carregarMensagens();

    }

    //GET
    private void carregarMensagens() {
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
                try {
                    JSONArray array = new JSONArray(json);
                    messages.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        int id = object.getInt("id");
                        String user = "User " + object.getInt("userId");
                        String text = object.getString("body");
                        LocalDateTime date = LocalDateTime.now();
                        messages.add(new Message(id, user, text, date));
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //POST
    private void enviarMensagem() {
        String messageText = etMessage.getText().toString();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Message text is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("title", "Mensagem do App");
            json.put("body", messageText);
            json.put("userId", 1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        RequestBody body = RequestBody.create(json.toString(), MEDIA_TYPE_JSON);
        Request request = new Request.Builder()
                .url(SERVICE_URL)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) return;
                //try { String responseData = response.body().string(); } catch (IOException e) { e.printStackTrace(); }
                runOnUiThread(() -> {
                    Toast.makeText(ApiActivity.this, "Sucesso! Código: " + response.code(), Toast.LENGTH_SHORT).show();
                    etMessage.setText("");
                    // Como a API não salva de verdade, adicionamos na lista visualmente
                    messages.add(0, new Message(999, userEmail, messageText, LocalDateTime.now()));
                    adapter.notifyItemInserted(0);
                    rv_api.scrollToPosition(0);
                });
            }
        });
    }
}