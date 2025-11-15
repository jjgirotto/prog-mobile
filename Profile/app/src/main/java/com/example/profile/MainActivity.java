package com.example.profile;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String VALOR_NOME = "NOME";
    private static final String VALOR_EMAIL = "EMAIL";
    private static final String VALOR_CAMINHO_FOTO = "PHOTO_PATH";
    private static final String VALOR_SENHA = "SENHA";
    private static final String VALOR_DIA = "DIA";
    private static final String VALOR_MES = "MES";
    private static final String VALOR_ANO = "ANO";
    private static final String VALOR_GENERO = "GENERO";
    private static final String VALOR_CAMPEAO = "CAMPEAO";
    private static final String VALOR_USER_TYPE = "USER_TYPE";
    /* Constante que indentifica o pedido de permissão para ler ficheiros */
    private static final int PERMISSAO_LER_FICHEIROS = 1000;
    /* Request code para a escolha de uma imagem da galeria */
    private static final int IMAGE_PICKER_SELECT = 1001;
    //atributo para manter o nome da imagem entre chamadas de metodos
    private String photoPath;
    EditText editNome, editEmail, editSenha;
    private Calendar calendar;
    private int currentYear, currentMonth, currentDay;
    private int ano, mes, dia;
    private EditText dateView;
    RadioButton male, female;
    Spinner userType;
    SwitchCompat campeao;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = findViewById(R.id.inputName);
        editEmail = findViewById(R.id.inputEmail);
        editSenha = findViewById(R.id.inputPassword);
        male = findViewById(R.id.radioMale);
        female = findViewById(R.id.radioFemale);
        userType = findViewById(R.id.spinnerUserType);
        campeao = findViewById(R.id.switchChampion);
        radioGroup = findViewById(R.id.radioGroup);

        calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        //associar um DatePickerDialog ao clique na dateView
        dateView = findViewById(R.id.inputBirthDate);
        dateView.setInputType(InputType.TYPE_NULL);
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view,
                                                  int year,
                                                  int monthOfYear,
                                                  int dayOfMonth) {
                                ano = year; mes = monthOfYear; dia = dayOfMonth;
                                showDate(ano, mes + 1, dia);
                            }
                        }, currentYear, currentMonth, currentDay);
                picker.show();
            }
        });
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        //quando a aplicação é lançada, vamos tentar ler possiveis valores guardados
        lerValores();
        //vamos associar o método guardarValores() ao clique no botao
        Button buttonGuardar = findViewById(R.id.btnSave);
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarValores();
            }
        });

        ImageView imgView = findViewById(R.id.imgProfile);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedirImagemComPermissoes();
            }
        });
    }

    private void lerValores() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //o método getString() recebe duas strings: o nome do valor e o valor
        //por omissão (se não houver valor guardado)
        String nomeGuardado = sharedPref.getString(VALOR_NOME, "");
        String emailGuardado = sharedPref.getString(VALOR_EMAIL,"");
        photoPath = sharedPref.getString(VALOR_CAMINHO_FOTO, "");
        String senhaGuardada = sharedPref.getString(VALOR_SENHA, "");
        int diaGuardado = sharedPref.getInt(VALOR_DIA, currentDay);
        int mesGuardado = sharedPref.getInt(VALOR_MES, currentMonth);
        int anoGuardado = sharedPref.getInt(VALOR_ANO, currentYear);
        int gender = sharedPref.getInt(VALOR_GENERO, 0);
        boolean campeaoGuardado = sharedPref.getBoolean(VALOR_CAMPEAO, false);
        String userTypeGuardado = sharedPref.getString(VALOR_USER_TYPE, "");
        //alterar o texto nas widgets
        editSenha.setText(senhaGuardada);
        editNome.setText(nomeGuardado);
        editEmail.setText(emailGuardado);
        carregarImagem(photoPath);
        showDate(anoGuardado, mesGuardado + 1, diaGuardado);
        radioGroup.check(gender);
        campeao.setChecked(campeaoGuardado);
        userType.setSelection(procurarPosicao(userTypeGuardado));
    }

    private int procurarPosicao(String userTypeGuardado) {
        android.widget.SpinnerAdapter adapter = userType.getAdapter();
        int position = -1;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equals(userTypeGuardado)) {
                position = i;
                break;
            }
        }
        return position;
    }

    private void guardarValores() {
        //obter referencias para widgets
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        int gender = radioGroup.getCheckedRadioButtonId();
        boolean champion = campeao.isChecked();
        String user = userType.getSelectedItem().toString();
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || gender == -1 || user.isEmpty()) {
            Toast.makeText(MainActivity.this, getResources().getText(R.string.toast_empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //guardar valores
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALOR_SENHA, senha);
        edit.putString(VALOR_NOME, nome);
        edit.putString(VALOR_EMAIL, email);
        edit.putString(VALOR_CAMINHO_FOTO, photoPath);
        edit.putInt(VALOR_DIA, currentDay);
        edit.putInt(VALOR_MES, currentMonth);
        edit.putInt(VALOR_ANO, currentYear);
        edit.putInt(VALOR_GENERO, gender);
        edit.putBoolean(VALOR_CAMPEAO, champion);
        edit.putString(VALOR_USER_TYPE, user);
        //esta instrução é que vai guardar os valores
        edit.commit();
        //notificar utilizador da concretizacao da operacao
        Toast.makeText(MainActivity.this, getResources().getText(R.string.info_saved), Toast.LENGTH_SHORT).show();
    }

    private void pedirImagem() {
        //ira lancar uma activity do sistema e posteriormente chamara o metodo onActivityResult (abaixo)
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICKER_SELECT);
    }

    private void carregarImagem(String imgPath) {
        ImageView imgView = findViewById(R.id.imgProfile);
        if (imgPath.isEmpty()) { //usar imagem 'dummy'
            imgView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.profile_placeholder, null));
        } else {
            File imgFile = new File(imgPath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgView.setImageBitmap(myBitmap);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = MainActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            photoPath = cursor.getString(columnIndex);
            cursor.close();
            carregarImagem(photoPath);
        }
    }

    private void pedirImagemComPermissoes() {
        //Verificar se a app tem acesso de leitura aos ficheiros do utilizador
        //Em caso negativo temos de tratar de pedir as permissões necessárias.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //Se o utilizador rejeitou a permissão anteriormente talvez seja bom explicar porque
            //é que a permissão é necessária.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //Mostrar um diálogo que explica a razão para pedir a permissão e voltar
                //a pedir a permissão na esperança que o utilizador aceite desta vez.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_read_file_permission)
                        .setCancelable(false)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                pedirPermissoes();
                            }
                        });
                builder.create().show();
            } else {
                //Na primeira vez simplesmente pedimos o acesso ao ficheiros do utilizador na
                //esperança que ele aceite.
                pedirPermissoes();
            }
            //Em caso afirmativo simplesmente pedir a imagem
        } else {
            pedirImagem();
        }
    }

    private void pedirPermissoes() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_LER_FICHEIROS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSAO_LER_FICHEIROS: {
                //Se o pedido foi cancelado, o array de resultados está vazio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permissão aceite.
                    Toast.makeText(this, R.string.toast_read_file_permission_granted, Toast.LENGTH_SHORT).show();
                    //Lançar o pedido de escolha de imagem.
                    pedirImagem();
                } else {
                    //Permissão rejeitada, não poderá alterar a sua imagem de perfil.
                    Toast.makeText(this, R.string.toast_read_file_permission_denied, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}