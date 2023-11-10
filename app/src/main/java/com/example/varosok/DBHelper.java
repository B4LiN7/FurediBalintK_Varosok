package com.example.varosok;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "varosok.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "varosok";

    private static final String COL_ID = "id";
    private static final String COL_NEV = "nev";
    private static final String COL_ORSZAG = "orszag";
    private static final String COL_LAKOSSAG = "lakossag";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NEV + " TEXT NOT NULL, " +
                COL_ORSZAG + " TEXT NOT NULL, " +
                COL_LAKOSSAG + " TEXT NOT NULL" +
                ");";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(
                "DROP TABLE IF EXISTS " + TABLE_NAME
        );
        onCreate(sqLiteDatabase);
    }

    public boolean addToTable(String nev, String orszag, String lakossag) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NEV, nev);
        values.put(COL_ORSZAG, orszag);
        values.put(COL_LAKOSSAG, lakossag);

        long result = database.insert(TABLE_NAME, null, values);
        return result != -1;
    }
}
