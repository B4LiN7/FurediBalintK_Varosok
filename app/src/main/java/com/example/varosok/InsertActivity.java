package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    private EditText etxtNev, etxtOrszag, etxtLakossag;
    private Button btnFelvetel, btnVissza;
    private DBHelper dbHelper;
    private boolean isFilled[] = {false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();

        etxtNev.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (dbHelper.isNevInTable(etxtNev.getText().toString().trim())) {
                    etxtNev.setError("Ez a név már létezik!");
                    etxtNev.setTextColor(Color.RED);
                    isFilled[0] = false;
                }
                else if (etxtNev.getText().toString().trim().isEmpty()) {
                    etxtNev.setError("Kötelező kitölteni!");
                    etxtNev.setTextColor(Color.BLACK);
                    isFilled[0] = false;
                }
                else {
                    etxtNev.setTextColor(Color.BLACK);
                    isFilled[0] = true;
                }
                checkIfFilled();
            }
        });

        etxtOrszag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etxtOrszag.getText().toString().isEmpty())
                {
                    etxtOrszag.setError("Kötelező kitölteni!");
                    isFilled[1] = false;
                }
                else {
                    isFilled[1] = true;
                }
                checkIfFilled();
            }
        });

        etxtLakossag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (etxtLakossag.getText().toString().isEmpty())
                {
                    etxtLakossag.setError("Kötelező kitölteni!");
                    isFilled[2] = false;
                }
                else {
                    isFilled[2] = true;
                }
                checkIfFilled();
            }
        });

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

    private void checkIfFilled() {
        if (areAllTrue(isFilled)) {
            btnFelvetel.setEnabled(true);
        }
        else {
            btnFelvetel.setEnabled(false);
        }
    }

    public static boolean areAllTrue(boolean[] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }

    private void init() {
        etxtNev = findViewById(R.id.editText_Insert_Nev);
        etxtOrszag = findViewById(R.id.editText_Insert_Orszag);
        etxtLakossag = findViewById(R.id.editText_Insert_Lakossag);
        btnFelvetel = findViewById(R.id.button_Insert_Felvetel);
        btnVissza = findViewById(R.id.button_Insert_Vissza);
        dbHelper = new DBHelper(this);
        checkIfFilled();
    }
}