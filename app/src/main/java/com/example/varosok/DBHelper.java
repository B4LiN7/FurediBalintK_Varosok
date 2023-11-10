package com.example.varosok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                COL_LAKOSSAG + " NUMBER NOT NULL" +
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

    public boolean addToTable(String nev, String orszag, int lakossag) {
        if (isNevInTable(nev)) {
            return false;
        }

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NEV, nev);
        values.put(COL_ORSZAG, orszag);
        values.put(COL_LAKOSSAG, lakossag);

        long result = database.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean isNevInTable(String nev)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, COL_NEV + " = ?", new String[] {nev}, null, null, null);
        return cursor.getCount() != 0;
    }

    public Cursor getTable() {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, null, null, null, null, null);
    }

    public Cursor getTableByLakossag10(int lakossag) {
        SQLiteDatabase database = this.getReadableDatabase();
        int min = Math.toIntExact(Math.round(lakossag * 0.9));
        int max = Math.toIntExact(Math.round(lakossag * 1.1));
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, COL_LAKOSSAG + " BETWEEN ? AND ?", new String[] {String.valueOf(min), String.valueOf(max)}, null, null, null);
    }

    public Cursor getTableByLakossag(int lakossag) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, COL_LAKOSSAG + " = ?", new String[] {String.valueOf(lakossag)}, null, null, null);
    }

    public Cursor getTableByOrszagOrNev(String any) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, COL_NEV + " LIKE ? OR " + COL_ORSZAG + " LIKE ?", new String[] {"%" + any + "%", "%" + any + "%"}, null, null, null);
    }

    public Cursor getTableByOrszag(String orszag) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(TABLE_NAME, new String[] {COL_ID, COL_NEV, COL_ORSZAG, COL_LAKOSSAG}, COL_ORSZAG + " = ?", new String[] {orszag}, null, null, null);
    }
}
