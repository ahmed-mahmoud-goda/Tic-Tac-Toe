package com.example.tictactoe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {

    private static final String table = "accounts";
    public Database(Context context) {
        super(context, "mydatabase", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ table+ " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username Text," +
                "password Text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(sqLiteDatabase);
    }
    public int checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { "id" };
        String selection = "username" + " = ? AND " + "password" + " = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            db.close();
            return userId;
        } else {
            cursor.close();
            db.close();
            return -1;
        }

    }
    public String getUsername(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { "username" };
        String selection = "id = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        String username = cursor.getString(0);
        cursor.close();
        db.close();
        return username;
    }
}
