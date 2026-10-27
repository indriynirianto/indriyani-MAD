package com.example.midexam;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextStudentId;
    SeekBar seekBarAge;
    TextView textAge;
    Button btnAdd, btnView;
    DBHelper dbHelper;

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

        editTextName = findViewById(R.id.editTextName);
        editTextStudentId = findViewById(R.id.editTextStudentId);
        seekBarAge = findViewById(R.id.seekBarAge);
        textAge = findViewById(R.id.textAge);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        dbHelper = new DBHelper(this);

        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAge.setText("Student Age: " + (progress + StudentModel.MIN_AGE));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnAdd.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            String studentId = editTextStudentId.getText().toString();
            int age = seekBarAge.getProgress() + StudentModel.MIN_AGE;

            if (name.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter data", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    dbHelper.addStudent(new StudentModel(name, studentId, age));
                    Toast.makeText(MainActivity.this, "Add Successful", Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextStudentId.setText("");
                    seekBarAge.setProgress(0);
                } catch (SQLiteConstraintException duplicate) {
                    Toast.makeText(MainActivity.this, "Student ID already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ViewStudent.class)));
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
