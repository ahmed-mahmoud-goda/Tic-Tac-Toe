package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TextView curr_player;
    private MediaPlayer mediaplayer;
    private ImageButton[] slots = new ImageButton[9];
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String[] matrix = {"","","","","","","","",""};
        final String[] player = {"x"};

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        final int[] cell = {9};
        mediaplayer = MediaPlayer.create(this,R.raw.pop);
        boolean onmusic = sp.getBoolean("sound",true);
        Intent receivedIntent = getIntent();
        boolean bot = receivedIntent.getBooleanExtra("bot", false);

        Intent intent = new Intent(GameActivity.this, WinnerActivity.class);

        curr_player = findViewById(R.id.textView);
        slots[0] = findViewById(R.id.imageButton);
        slots[1] = findViewById(R.id.imageButton2);
        slots[2] = findViewById(R.id.imageButton3);
        slots[3] = findViewById(R.id.imageButton4);
        slots[4] = findViewById(R.id.imageButton5);
        slots[5] = findViewById(R.id.imageButton6);
        slots[6] = findViewById(R.id.imageButton7);
        slots[7] = findViewById(R.id.imageButton8);
        slots[8] = findViewById(R.id.imageButton9);

        Random random = new Random();
        curr_player.setText(sp.getString("username", "") + getString(R.string.turn));

        for (int i = 0; i < 9; i++) {
            int temp = i;
            slots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (matrix[temp].equals("")) {
                        boolean isWin = false;

                        if (player[0].equals("x")) {
                            slots[temp].setImageResource(R.drawable.x);
                            if(onmusic == true){
                                mediaplayer.start();
                            }
                            matrix[temp] = "x";


                            isWin = checkWinner(player[0], matrix);
                            if(!isWin){
                                cell[0] -= 1;
                            }
                            if (isWin) {
                                intent.putExtra("winner", "player1");
                                startActivity(intent);
                                finish();
                            } else if (bot) {
                                player[0] = "o";
                                curr_player.setText(getString(R.string.com)+getString(R.string.turn));

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        int randomNumber = random.nextInt(9);
                                        while (!matrix[randomNumber].equals("")) {
                                            randomNumber = random.nextInt(9);
                                        }
                                        matrix[randomNumber] = "o";
                                        slots[randomNumber].setImageResource(R.drawable.circle);
                                        if(onmusic == true){
                                            mediaplayer.start();
                                        }
                                        cell[0] -= 1;

                                        boolean botWin = checkWinner(player[0], matrix);
                                        if (botWin) {
                                            intent.putExtra("winner", "com");
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            player[0] = "x";
                                            curr_player.setText(sp.getString("username", "") + getString(R.string.turn));
                                        }
                                    }
                                }, 1000);
                            }
                        } else if (player[0].equals("o") && !bot) {
                            slots[temp].setImageResource(R.drawable.circle);
                            if(onmusic == true){
                                mediaplayer.start();
                            }
                            matrix[temp] = "o";
                            cell[0]-=1;
                            isWin = checkWinner(player[0], matrix);
                            if (isWin) {
                                intent.putExtra("winner", "player2");
                                startActivity(intent);
                                finish();
                            }
                        }


                        if (cell[0] == 0) {
                            intent.putExtra("winner", "draw");
                            startActivity(intent);
                            finish();
                        } else if (!isWin && !bot) {
                            if (player[0].equals("x")) {
                                player[0] = "o";
                                curr_player.setText(getString(R.string.p2)+getString(R.string.turn));
                            } else {
                                player[0] = "x";
                                curr_player.setText(sp.getString("username", "") + getString(R.string.turn));
                            }
                        }
                    }
                }
            });
        }
    }


    public boolean checkWinner(String player,String[] matrix){
        if((matrix[0]==player && matrix[1]==player && matrix[2]==player) || (matrix[3]==player && matrix[4]==player && matrix[5]==player) || (matrix[6]==player && matrix[7]==player && matrix[8]==player) || (matrix[0]==player && matrix[3]==player && matrix[6]==player) || (matrix[1]==player && matrix[4]==player && matrix[7]==player) || (matrix[2]==player && matrix[5]==player && matrix[8]==player) || (matrix[0]==player && matrix[4]==player && matrix[8]==player) || (matrix[2]==player && matrix[4]==player && matrix[6]==player)){
            return true;
        }
        else{
            return false;
        }
    }

}