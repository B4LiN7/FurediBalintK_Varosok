package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    private EditText etxtNev, etxtOrszag, etxtLakossag;
    private Button btnFelvetel, btnVissza;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();

        btnFelvetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nev = etxtNev.getText().toString().trim();
                String orszag = etxtOrszag.getText().toString().trim();
                if (nev.isEmpty() || orszag.isEmpty() || etxtLakossag.getText().toString().isEmpty()) {
                    Toast.makeText(InsertActivity.this, "Vannak üresen hagyott mezők!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int lakossag = Integer.parseInt(etxtLakossag.getText().toString());

                if (dbHelper.addToTable(nev, orszag, lakossag)) {
                    Toast.makeText(InsertActivity.this, "Sikeres adatfelvétel", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(InsertActivity.this, "Sikertelen adatfelvétel", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsertActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void init() {
        etxtNev = findViewById(R.id.editText_Insert_Nev);
        etxtOrszag = findViewById(R.id.editText_Insert_Orszag);
        etxtLakossag = findViewById(R.id.editText_Insert_Lakossag);
        btnFelvetel = findViewById(R.id.button_Insert_Felvetel);
        btnVissza = findViewById(R.id.button_Insert_Vissza);
        dbHelper = new DBHelper(this);
    }
}