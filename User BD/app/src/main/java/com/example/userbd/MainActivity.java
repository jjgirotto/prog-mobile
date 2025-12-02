package com.example.userbd;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.userbd.classes.AppDatabase;
import com.example.userbd.classes.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    //*** criar o executor service que vai correr as nossas queries à base de dados
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*** criar a instância para a BD SQLite
        db = AppDatabase.getInstance(this);
        //vamos associar o método guardarUser() ao clique no botao add
        Button buttonAdd = findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(view -> guardarUser());
        //vamos associar o método apagarUser() ao clique no botao delete
        Button buttonDelete = findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(view -> apagarUser());
        //vamos associar o método pesquisarUser() ao clique no botao search
        Button buttonSearch = findViewById(R.id.btn_search);
        buttonSearch.setOnClickListener(view -> pesquisarUser());
    }

    //método para inserir um user na BD a partir dos dados das widgets
    private void guardarUser() {
        EditText editNome = findViewById(R.id.et_nome);
        EditText editEmail = findViewById(R.id.et_email);
        EditText editPass = findViewById(R.id.et_password);
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String pass = editPass.getText().toString();
        //validar os campos para verificar se estão convenientemente preenchidos
        if (nome.equals("")) {
            Toast.makeText(this, R.string.missing_name, Toast.LENGTH_SHORT).show();
        } else if (email.equals("")) {
            Toast.makeText(this, R.string.missing_email, Toast.LENGTH_SHORT).show();
        } else if (pass.equals("")) {
            Toast.makeText(this, R.string.missing_password, Toast.LENGTH_SHORT).show();
        } else {
            //inserir o novo User na BD:
            //primeiro cria-se um novo objeto User
            User usr1 = new User(nome, pass, email);
            //e depois insere-se ou atualiza-se mesmo no objeto da BD
            executorService.execute(() -> {
                List<User> users = db.userDao().getAll();
                int indiceUpdate = searchUserByName(users, nome, pass);
                if (indiceUpdate != -1) {
                    usr1.setId(users.get(indiceUpdate).getId());
                    db.userDao().update(usr1);
                    runOnUiThread(() ->
                            Toast.makeText(this, R.string.user_updated, Toast.LENGTH_SHORT).show()
                    );
                } else {
                    db.userDao().insert(usr1);
                    runOnUiThread(() ->
                            Toast.makeText(this, R.string.user_inserted, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        }
    }

    private int searchUserByName(List<User> list, String username, String password) {
        int indiceDelete = -1;
        for (User usr : list) {
            if (usr.getUserName().equals(username) && usr.getPassword().equals(password))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }

    //método para fazer delete a um User na BD caso seja encontrado o user com os dados de username
    //e password preenchidos nas widgets.
    private void apagarUser() {
        //bastará que o username e a password estejam na BD!!! É apenas um exemplo hipotético!
        //obter referencias para widgets
        EditText editNome = findViewById(R.id.et_nome);
        EditText editPass = findViewById(R.id.et_password);
        String nome = editNome.getText().toString();
        String pass = editPass.getText().toString();
        executorService.execute(() -> {
            List<User> users = db.userDao().getAll();
            int indiceDelete = searchUserByName(users, nome, pass);
            if (indiceDelete != -1) {
                // delete one user: the first one with the username and password inserted
                User usr = users.get(indiceDelete);
                db.userDao().delete(usr);
                runOnUiThread(() -> {
                    mostrarUser(null);
                    Toast.makeText(this, R.string.user_deleted, Toast.LENGTH_SHORT).show();
                });
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    //método que preenche as Widgets com os dados do User selecionado, etc.
    //Caso o param user seja null então este método serve para limpar as widgets de dados sendo
    //usado assim no apagarUser()
    private void mostrarUser(User user) {
        //obter referencias para widgets
        EditText editNome = findViewById(R.id.et_nome);
        EditText editEmail = findViewById(R.id.et_email);
        EditText editPass = findViewById(R.id.et_password);
        if (user == null) {
            editNome.setText("");
            editEmail.setText("");
            editPass.setText("");
        } else {
            editNome.setText(user.getUserName());
            editEmail.setText(user.getEmail());
            editPass.setText(user.getPassword());
        }
    }

    //método para ilustrar apenas como se poderia pesquisar usando os dados que já estão nas Widgets.
    //Serve apenas para exemplificar...
    private void pesquisarUser() {
        //É apenas um exemplo hipotético!
        //bastará que o username e a password estejam na BD!!!
        //obter referencias para widgets
        EditText editNome = findViewById(R.id.et_nome);
        EditText editEmail = findViewById(R.id.et_email);
        EditText editPass = findViewById(R.id.et_password);
        String nome = editNome.getText().toString();
        String pass = editPass.getText().toString();
        String email = editEmail.getText().toString();
        executorService.execute(() -> {
            List<User> users = db.userDao().getAll();
            int indiceDelete = searchUserByName(users, nome, pass);
            if (indiceDelete != -1) {
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.user_found1, Toast.LENGTH_SHORT).show()
                );
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show()
                );
            }
            indiceDelete = searchUserByEmail(users, email, pass);
            if (indiceDelete != -1) {
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.user_found2, Toast.LENGTH_SHORT).show()
                );
            } else {
                runOnUiThread(() ->
                        Toast.makeText(this, R.string.user_not_found, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    //método alternativo que devolve o índice do user dados o username e o email (neste exemplo).
    //Devolve -1 caso não seja encontrado
    private int searchUserByEmail(List<User> list, String email, String password) {
        int indiceDelete = -1;
        for (User usr : list) {
            if (usr.getEmail().equals(email) && usr.getPassword().equals(password))
                indiceDelete = list.indexOf(usr);
        }
        return indiceDelete;
    }


}