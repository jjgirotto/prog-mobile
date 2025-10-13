package com.example.calculadoramelhorada;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView expressao, resultado;
    double valorAnterior = 0;
    String operacao = "";
    boolean novoNumero = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        expressao = findViewById(R.id.txtExpressao);
        resultado = findViewById(R.id.txtResultado);
        Button btnAC = findViewById(R.id.btnAC);
        Button btnIgual = findViewById(R.id.btnIgual);
        Button btnVirgula = findViewById(R.id.btnVirgula);
        Button btnSinal = findViewById(R.id.btnSinal);
        Button btnPorcentagem = findViewById(R.id.btnPorcentagem);
        int[] numeros = {
                R.id.btnZero, R.id.btnUm, R.id.btnDois, R.id.btnTres,
                R.id.btnQuatro, R.id.btnCinco, R.id.btnSeis,
                R.id.btnSete, R.id.btnOito, R.id.btnNove
        };
        int[] operacoes = {
                R.id.btnMais, R.id.btnMenos, R.id.btnMulti, R.id.btnDivisao, R.id.btnPorcentagem
        };
        for (int id : numeros) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> adicionarNumero(btn.getText().toString()));
        }
        for (int id : operacoes) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> definirOperacao(btn.getText().toString()));
        }
        btnAC.setOnClickListener(v -> {
            expressao.setText("");
            resultado.setText("");
            valorAnterior = 0;
            operacao = "";
            novoNumero = true;
        });
        btnIgual.setOnClickListener(v -> calcularResultado());
        btnVirgula.setOnClickListener(v -> adicionarVirgula());
        btnSinal.setOnClickListener(v -> inverterSinal());
        btnPorcentagem.setOnClickListener(v -> aplicarPorcentagem());
    }

    private void adicionarNumero(String numero) {
        resultado.append(numero);
    }

    private void adicionarVirgula() {
        resultado.append(".");
    }

    private void inverterSinal() {
        String valor = resultado.getText().toString();
        if (valor.startsWith("-")) {
            resultado.setText(valor.substring(1)); //ignora o - posição 0 da substring
        } else {
            resultado.setText("-" + valor); //adiciona o -
        }
    }

    private void aplicarPorcentagem() {
        try {
            double valor = Double.parseDouble(resultado.getText().toString());
            valor = valor / 100;
            resultado.setText(String.valueOf(valor));
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Erro ao aplicar porcentagem", Toast.LENGTH_SHORT).show();
        }
    }

    private void definirOperacao(String op) {
        String textoAtual = resultado.getText().toString();
        if (textoAtual.isEmpty()) {
            return; //não faz nada se não tiver número
        }
        String ultimoCaractere = textoAtual.substring(textoAtual.length() - 1);
        if (ultimoCaractere.equals(" ")) {
            return; // último caractere é espaço, já tem um operador, não faz nada
        }
        resultado.append(" " + op + " "); //adiciona o operador e espaço
    }

    private void calcularResultado() {
        try {
            String texto = resultado.getText().toString().trim();
            if (!texto.contains(" ")) {
                expressao.setText(texto);
                novoNumero = true;
                return; // se não tiver operador (espaço), apenas move o número para cima.
            }

            // divide a expressão em número,operador,número
            String[] partes = texto.split("\\s+"); //separa cortando os espaços em branco
            if (partes.length < 3) {
                Toast.makeText(this, "Expressão inválida", Toast.LENGTH_SHORT).show();
                return; //só válido para expressões com dois números e um operador
            }
            double valor1 = Double.parseDouble(partes[0]);
            String operador = partes[1];
            double valor2 = Double.parseDouble(partes[2]);
            double res;

            switch (operador) {
                case "+":
                    res = valor1 + valor2;
                    break;
                case "-":
                    res = valor1 - valor2;
                    break;
                case "X":
                    res = valor1 * valor2;
                    break;
                case "/":
                    if (valor2 == 0) {
                        Toast.makeText(this, "Impossível dividir por zero", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    res = valor1 / valor2;
                    break;
                default:
                    res = valor2; // operador desconhecido, retorne o segundo número
                    break;
            }
            expressao.setText(texto);
            resultado.setText(String.valueOf(res));
            novoNumero = true;
        } catch (Exception e) {
            Toast.makeText(this, "Erro no cálculo", Toast.LENGTH_SHORT).show();
        }
    }

}