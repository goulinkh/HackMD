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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private LinearLayout buttonsLayout;
    private LinearLayout gameLayout;
    private ImageButton sensibilityPlusButton;
    private ImageButton sensibilityMoinsButton;
    private TextView sensibilityInfo;
    private int sensibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sensibility=0;
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

        //récupération des elements de la vue
        buttonsLayout=findViewById(R.id.linearLayout_buttons);
        gameLayout=findViewById(R.id.linearLayout_game);
        sensibilityMoinsButton=findViewById(R.id.imageButton_reduceSensibility);
        sensibilityPlusButton=findViewById(R.id.imageButton_increaseSensibility);
        sensibilityInfo=findViewById(R.id.textView_sensibility);

        GameView myGameView=new GameView(this);
        gameLayout.addView(myGameView);
        majSensibilityInfo();

        //gestion des evenements
        sensibilityPlusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sensibility<5){
                    sensibility+=1;
                    majSensibilityInfo();
                }
            }
        });
        sensibilityMoinsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sensibility>0){
                    sensibility-=1;
                    majSensibilityInfo();
                }
            }
        });


    }

    public int getSensibility() {
        return sensibility;
    }

    public void setSensibility(int sensibility) {
        this.sensibility = sensibility;
    }

    public void majSensibilityInfo(){
        sensibilityInfo.setText(String.valueOf(sensibility));
    }


}