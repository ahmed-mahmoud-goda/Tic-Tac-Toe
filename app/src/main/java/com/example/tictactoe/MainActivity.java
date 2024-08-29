package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button lbtn,sbtn;
    private TextView utxt,ptxt;
    private SharedPreferences sp;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lbtn = findViewById(R.id.button1);
        sbtn = findViewById(R.id.button2);
        utxt = findViewById(R.id.username);
        ptxt = findViewById(R.id.pass);
        database = new Database(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Signup.class);
                startActivity(i);
            }
        });
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!utxt.getText().toString().trim().isEmpty() && !ptxt.getText().toString().trim().isEmpty()){
                    int exists = database.checkUser(utxt.getText().toString(),ptxt.getText().toString());
                    if(exists != -1) {
                        String user = database.getUsername(exists);
                        editor.putBoolean("logged", true);
                        editor.putString("username","ahmed");
                        editor.apply();
                        Intent i = new Intent(MainActivity.this, Home.class);
                        startActivity(i);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "doesn't Exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}