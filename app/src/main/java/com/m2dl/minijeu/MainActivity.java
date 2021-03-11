package com.m2dl.minijeu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor light;
    private Sensor gameRotationVector;
    private GameView gameView;
    private double sensitivity;

    private float lightValue;

    private LinearLayout buttonsLayout;
    private LinearLayout gameLayout;
    private ImageButton sensitivityPlusButton;
    private ImageButton sensitivityMoinsButton;
    private TextView sensitivityInfo;
    private Date startDate;
    private Integer gameViewHeight;
    private Integer gameViewWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensitivity = 1.0;

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
        gameView = new GameView(this);
        buttonsLayout = findViewById(R.id.linearLayout_buttons);
        gameLayout = findViewById(R.id.linearLayout_game);
        sensitivityMoinsButton = findViewById(R.id.imageButton_reduceSensitivity);
        sensitivityPlusButton = findViewById(R.id.imageButton_increaseSensitivity);
        sensitivityInfo = findViewById(R.id.textView_sensitivity);

        gameLayout.addView(gameView);
        majsensitivityInfo();

        //gestion des evenements
        sensitivityPlusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sensitivity < 5) {
                    sensitivity += 0.5;
                    majsensitivityInfo();
                }
            }
        });
        sensitivityMoinsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sensitivity > 1) {
                    sensitivity -= 0.5;
                    majsensitivityInfo();
                }
            }
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_FASTEST);
        gameRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, gameRotationVector, SensorManager.SENSOR_DELAY_GAME);
        startDate = new Date();
        gameView.post(new Runnable() {
            @Override
            public void run() {
                gameViewWidth = Integer.valueOf(gameView.getWidth());
                gameViewHeight = Integer.valueOf(gameView.getHeight());
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.equals(light)) {
            lightValue = sensorEvent.values[0];
        } else if (sensorEvent.sensor.equals(gameRotationVector)) {
            float x = sensorEvent.values[1];
            float y = sensorEvent.values[0];
            Point point = gameView.getCirclePosition();
            point.x += x * 150 * sensitivity;
            point.y += y * 150 * sensitivity;
            gameView.setCirclePosition(point);
            if (!(gameViewHeight == null || gameViewWidth == null)) {
                checkGameOver(point.x, point.y);
            }
        }
    }

    private void checkGameOver(float x, float y) {
        float rayonBalle = 100;
        if ((gameViewWidth < (rayonBalle + x) || (x - rayonBalle) < 0) ||
                (gameViewHeight < (rayonBalle + y) || (y - rayonBalle) < 0)) {
            // TODO: aller a l'activité Game Over avec le score
            System.out.println("le jeu est terminé et le score est : " +
                    (new Date().getTime() - startDate.getTime()) / 1000);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void majsensitivityInfo() {
        sensitivityInfo.setText(String.valueOf(sensitivity));
    }

    public double getsensitivity() {
        return sensitivity;
    }

    public void setsensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getLightValue() {
        return lightValue;
    }
}