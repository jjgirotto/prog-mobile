package com.example.eventapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventapp.model.AppDataBase;
import com.example.eventapp.model.User;
import com.example.eventapp.model.UserDao;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private static final String VALUE_EMAIL = "VALUE_EMAIL";
    private static final String VALUE_SENHA = "VALUE_SENHA";
    String email, password, name, gender, birthDate;
    Calendar calendar;
    EditText editName, editAge, editBirthDate;
    int age, currentDay, currentMonth, currentYear, year_, month, day;
    RadioButton btnMale, btnFemale;
    AppDataBase db;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            email = extras.getString(VALUE_EMAIL);
            password = extras.getString(VALUE_SENHA);
        }
        editName = findViewById(R.id.et_name);
        editAge = findViewById(R.id.et_idade);
        editBirthDate = findViewById(R.id.et_birthDate);
        btnSave = findViewById(R.id.btn_save);
        btnMale = findViewById(R.id.male);
        btnFemale = findViewById(R.id.female);
        db = AppDataBase.getDataBase(this);

        calendar = Calendar.getInstance();
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);
        editBirthDate.setInputType(InputType.TYPE_NULL);
        editBirthDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog picker = new DatePickerDialog(RegisterActivity.this,
                         new DatePickerDialog.OnDateSetListener() {
                             @Override
                             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                 year_ = year; month = monthOfYear; day = dayOfMonth;
                                 showDate(year_, month + 1, day);
                             }
                         }, currentYear, currentMonth, currentDay);
                 picker.show();
             }
        });
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);
        currentYear = calendar.get(Calendar.YEAR);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lerDados()) {
                    UserDao dao = db.userDao();
                    dao.insertUser(new User(name, email, password, birthDate, age, gender));
                    Toast.makeText(RegisterActivity.this, "User saved", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error saving user.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean lerDados() {
        try {
            name = editName.getText().toString();
            age = Integer.parseInt(editAge.getText().toString());
            if (btnMale.isChecked()) gender = btnMale.getText().toString();
            else if (btnFemale.isChecked()) gender = btnFemale.getText().toString();
            else {
                Toast.makeText(this, "Choose a gender.", Toast.LENGTH_SHORT).show();
                return false;
            }
            birthDate = editBirthDate.getText().toString();
            return true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void showDate(int year, int month, int day) {
        editBirthDate.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

}