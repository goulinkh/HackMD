package com.m2dl.minijeu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    private LinearLayout buttonsLayout;
    private LinearLayout gameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        int valeur_y = sharedPref.getInt("valeur_y", 0);
        int nbParties = sharedPref.getInt("nb_parties", 0);
        valeur_y = (valeur_y + 100) % 400;
        nbParties = nbParties++;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("valeur_y", valeur_y);
        editor.putInt("nb_parties", nbParties);
        editor.apply();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    //Ce code est séparé car il est impossible de manipuler le contunu de la View dans le onCreate
    private void start(){
        buttonsLayout=findViewById(R.id.linearLayout_buttons);
        gameLayout=findViewById(R.id.linearLayout_game);
        GameView myGameView=new GameView(this);
        gameLayout.addView(myGameView);
    }
}