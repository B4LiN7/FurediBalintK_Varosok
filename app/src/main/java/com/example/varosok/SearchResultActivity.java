package com.example.varosok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SearchResultActivity extends AppCompatActivity {

    private TextView txtEredmeny;
    private Button btnVissza;
    private DBHelper dbHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        init();

        btnVissza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResultActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void listEredmeny(String kereses) {
        Cursor cursor;
        if (isInteger(kereses)) {
            cursor = dbHelper.getTableByLakossag10(Integer.parseInt(kereses));
        }
        else {
            cursor = dbHelper.getTableByOrszagOrNev(kereses);
        }

        if (null == cursor) {
            txtEredmeny.setText("Hiba történt az adatbázis elérése közben!");
            return;
        }
        if (cursor.getCount() == 0) {
            txtEredmeny.setText("Nem található rekord a következő adattal: " + kereses);
            return;
        }

        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()) {
            sb.append("[")
                    .append(cursor.getString(0)).append("] Ország: ")
                    .append(cursor.getString(2)).append(", Város: ")
                    .append(cursor.getString(1)).append(", Lakosok: ")
                    .append(cursor.getInt(3)).append("\n");
        }

        txtEredmeny.setText(sb.toString());
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void init() {
        txtEredmeny = findViewById(R.id.textView_SearchResult_Eredmeny);
        btnVissza = findViewById(R.id.button_SearchResult_Vissza);
        dbHelper = new DBHelper(this);

        txtEredmeny.setMovementMethod(new ScrollingMovementMethod());

        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String kereses = sharedPreferences.getString("kereses", "");
        listEredmeny(kereses);
    }
}