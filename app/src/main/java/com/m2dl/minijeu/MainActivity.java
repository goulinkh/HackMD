package com.m2dl.minijeu;

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
    private ImageButton sensitivityPlusButton;
    private ImageButton sensitivityMoinsButton;
    private TextView sensitivityInfo;
    private int sensitivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sensitivity=0;
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
        sensitivityMoinsButton=findViewById(R.id.imageButton_reduceSensitivity);
        sensitivityPlusButton=findViewById(R.id.imageButton_increaseSensitivity);
        sensitivityInfo=findViewById(R.id.textView_sensitivity);

        GameView myGameView=new GameView(this);
        gameLayout.addView(myGameView);
        majsensitivityInfo();

        //gestion des evenements
        sensitivityPlusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sensitivity<5){
                    sensitivity+=1;
                    majsensitivityInfo();
                }
            }
        });
        sensitivityMoinsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(sensitivity>0){
                    sensitivity-=1;
                    majsensitivityInfo();
                }
            }
        });


    }


    public int getSensitivity(){
        return sensitivity;
    }

    public int getsensitivity() {
        return sensitivity;
    }

    public void setsensitivity(int sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void majsensitivityInfo(){
        sensitivityInfo.setText(String.valueOf(sensitivity));
    }


}