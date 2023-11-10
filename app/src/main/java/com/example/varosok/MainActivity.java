package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etxtKereses;
    private Button btnKereses, btnUj;
    private DBHelper  dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        btnKereses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String kereses = etxtKereses.getText().toString().trim();
                if (kereses.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Kötelező keresendő szót megadni", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("Data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("kereses", kereses);
                editor.apply();

                startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
                finish();
            }
        });

        btnUj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
                finish();
            }
        });
    }

    private void init() {
        etxtKereses = findViewById(R.id.editText_Main_Kereses);
        btnKereses = findViewById(R.id.button_Main_Kereses);
        btnUj = findViewById(R.id.button_Main_Uj);
        dbHelper = new DBHelper(this);
    }
}