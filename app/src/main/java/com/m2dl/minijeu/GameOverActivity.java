package com.m2dl.minijeu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class GameOverActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private ImageButton retryButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        setContentView(R.layout.activity_gameover);

        //Gestion des scores [En dur pour tester]
        LinearLayout scoreLayout=findViewById(R.id.five_global_best_score_layout);
        mInflater = LayoutInflater.from(this);
        View bestScore = mInflater.inflate(R.layout.score_item , null, false);
        TextView score=bestScore.findViewById(R.id.score_item_value);
        score.setText("67952");
        scoreLayout.addView(bestScore);

        View secondbestScore = mInflater.inflate(R.layout.score_item , null, false);
        TextView secondscore=secondbestScore.findViewById(R.id.score_item_value);
        secondscore.setText("42965");
        scoreLayout.addView(secondbestScore);


        retryButton=findViewById(R.id.retry_imageButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               runGame();
            }
        });

    }

    public void runGame(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}

