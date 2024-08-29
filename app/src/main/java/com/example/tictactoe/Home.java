package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {
    private TextView username;
    private SharedPreferences sp;
    private MediaPlayer mediaPlayer;
    private Button logout,pvp,pvc,music,sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        username = findViewById(R.id.textView2);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        username.setText(getString(R.string.welcome) + sp.getString("username",""));
        logout = findViewById(R.id.logout);
        pvp = findViewById(R.id.button4);
        pvc = findViewById(R.id.button3);
        music = findViewById(R.id.music);
        sound = findViewById(R.id.sound);
        mediaPlayer = MediaPlayer.create(this,R.raw.background);
        mediaPlayer.setLooping(true);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.getBoolean("music",true)){
            music.setText(R.string.musicon);
            mediaPlayer.start();
        }
        else{
            music.setText(R.string.musicoff);
        }
        if (sp.getBoolean("sound",true)){
            sound.setText(R.string.soundon);
        }
        else{
            sound.setText(R.string.soundoff);
        }
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getBoolean("music",true)){
                    editor.putBoolean("music",false);
                    music.setText(R.string.musicoff);
                    mediaPlayer.pause();
                }
                else{
                    editor.putBoolean("music",true);
                    music.setText(R.string.musicon);
                    mediaPlayer.start();
                }
                editor.apply();
            }
        });
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getBoolean("sound",true)){
                    editor.putBoolean("sound",false);
                    sound.setText(R.string.soundoff);
                }
                else{
                    editor.putBoolean("sound",true);
                    sound.setText(R.string.soundon);
                }
                editor.apply();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("logged", false);
                editor.apply();
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        pvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, GameActivity.class);
                i.putExtra("bot",false);
                startActivity(i);
            }
        });
        pvc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, GameActivity.class);
                i.putExtra("bot",true);
                startActivity(i);
            }
        });
    }
}