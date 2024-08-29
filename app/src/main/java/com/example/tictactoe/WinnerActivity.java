package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinnerActivity extends AppCompatActivity {
private TextView txt;
private SharedPreferences sp;
private Button btn;
private MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        Intent i = getIntent();
        txt = findViewById(R.id.winner);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sound = sp.getBoolean("sound",true);
        String winner = i.getStringExtra("winner");
        btn = findViewById(R.id.button);
        if(winner.equals("player1")){
            txt.setText(sp.getString("username","")+getString(R.string.won));
            if(sound==true){
                mp = MediaPlayer.create(this,R.raw.win);
                mp.start();
            }
        }
        else if(winner.equals("player2")){
            txt.setText(getString(R.string.won2));
            if(sound==true){
                mp = MediaPlayer.create(this,R.raw.win);
                mp.start();
            }
        }
        else if(winner.equals("draw")){
            txt.setText(getString(R.string.draw));
            if(sound==true){
                mp = MediaPlayer.create(this,R.raw.lose);
                mp.start();
            }
        }
        else{
            txt.setText(getString(R.string.lose));
            if(sound==true){
                mp = MediaPlayer.create(this,R.raw.lose);
                mp.start();
            }
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WinnerActivity.this,Home.class);
                startActivity(i);
                finish();
            }
        });

    }
}