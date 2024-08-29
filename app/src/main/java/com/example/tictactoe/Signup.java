package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    private Database database;
    private TextView utxt,ptxt;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = new Database(this);
        utxt = findViewById(R.id.username);
        ptxt = findViewById(R.id.pass);
        btn = findViewById(R.id.Signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!utxt.getText().toString().isEmpty() && !ptxt.getText().toString().isEmpty()){
                    SQLiteDatabase db = database.getWritableDatabase();

                    String[] columns = { "id" };
                    String selection = "username = ?";
                    String[] selectionArgs = { utxt.getText().toString() };

                    Cursor cursor = db.query("accounts", columns, selection, selectionArgs, null, null, null);

                    if (cursor != null && cursor.moveToFirst()) {
                        cursor.close();
                        db.close();
                        Toast.makeText(Signup.this, "User Already Exists", Toast.LENGTH_SHORT).show(); ; // Exit the function without inserting
                    }
                    else {
                        insertUser(utxt.getText().toString(), ptxt.getText().toString());
                        Intent i = new Intent(Signup.this, MainActivity.class);
                        Toast.makeText(Signup.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Toast.makeText(Signup.this, "Please Enter Username And Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void insertUser(String name, String password) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username",name);
        values.put("password", password);

        db.insert("accounts", null, values);
        db.close();
    }
}