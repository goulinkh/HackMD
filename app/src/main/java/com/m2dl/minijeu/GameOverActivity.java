package com.m2dl.minijeu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.m2dl.minijeu.models.Score;

public class GameOverActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private ImageButton retryButton;
    private String sessionScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        sessionScore = getIntent().getStringExtra("score");

        // Stocker le score dans firebase
        FirebaseService firebaseService = new FirebaseService() ;
        firebaseService.writeNewScore(new Score(Integer.parseInt(sessionScore)));

        // afficher l'interface
        setContentView(R.layout.activity_gameover);

        //Gestion des scores [En dur pour tester]
        LinearLayout scoreLayout=findViewById(R.id.five_global_best_score_layout);
        mInflater = LayoutInflater.from(this);
        View scoreView = mInflater.inflate(R.layout.score_item , null, false);
        TextView score=scoreView.findViewById(R.id.score_item_value);
        score.setText(sessionScore);
        scoreLayout.addView(scoreView);



        retryButton=findViewById(R.id.retry_imageButton);
        retryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               runGame();
            }
        });

        Button btn =findViewById(R.id.button);
        GameOverActivity t = this;
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(t, StartActivity.class);
                startActivity(myIntent);
            }
        });

    }

    public void runGame(){
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}

