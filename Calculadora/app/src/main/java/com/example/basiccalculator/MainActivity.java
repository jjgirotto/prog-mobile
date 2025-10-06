package com.example.basiccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText1, editText2;
    double value1, value2;
    TextView result;

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
        Button btnSum = findViewById(R.id.btnSum);
        Button btnMinus = findViewById(R.id.btnMinus);
        Button btnMulti = findViewById(R.id.btnMulti);
        Button btnDiv = findViewById(R.id.btnDiv);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        result = findViewById(R.id.result);

        somar(btnSum);
        subtrair(btnMinus);
        multiplicar(btnMulti);
        dividir(btnDiv);
    }

    /**
     * Converte o texto digitado nos campos em valores double e atribui às variáveis {@code value1} e {@code value2}.
     * @return {@code true} se a conversão for bem-sucedida, {@code false} caso contrário
     */
    private boolean updateValues() {
        try {
            value1 = Double.parseDouble(editText1.getText().toString());
            value2 = Double.parseDouble(editText2.getText().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Define o comportamento do botão de soma: ao clicar, os valores são atualizados, somados e exibidos no campo de resultado.
     * @param btnSum botão responsável pela operação de soma
     */
    private void somar (Button btnSum) {
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateValues()) {
                    double resultado = value1 + value2;
                    result.setText(String.valueOf(resultado));
                }
            }
        });
    }

    private void subtrair (Button btnMinus) {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateValues()) {
                    result.setText(String.valueOf(value1 - value2));
                }
            }
        });
    }

    private void multiplicar (Button btnMulti) {
        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateValues()) {
                    result.setText(String.valueOf(value1 * value2));
                }
            }
        });
    }

    /**
     * Aqui é usada uma expressão lambda (v -> { ... }) em vez da forma tradicional com "new View.OnClickListener() { @Override public void onClick(View v) { ... } }".
     * A expressão lambda é uma forma simplificada e mais legível de escrever o mesmo código:
     * elimina a necessidade de criar uma classe anônima e sobrescrever o método onClick,
     * já que o compilador entende automaticamente que se trata de um listener de clique.
     * - Forma tradicional: new View.OnClickListener() e uso de @Override.
     * - Forma lambda: usa apenas "v -> { ... }" e executa o mesmo comportamento.
     */
    private void dividir (Button btnDiv) {
        //formato lambda
        btnDiv.setOnClickListener(v -> {
            if (updateValues()) {
                result.setText(String.valueOf(value1 / value2));
            }
        });
    }
}