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

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView;
    private double sensitivity;

    private LinearLayout buttonsLayout;
    private LinearLayout gameLayout;
    private ImageButton sensitivityPlusButton;
    private ImageButton sensitivityMoinsButton;
    private TextView sensitivityInfo;

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
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);


    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[1];
        float y = sensorEvent.values[0];
        float z = sensorEvent.values[2];
        Point point = gameView.getCirclePosition();
        point.x += x * 150 * sensitivity;
        point.y += y * 150 * sensitivity;
        gameView.setCirclePosition(point);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public double getsensitivity() {
        return sensitivity;
    }

    public void setsensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public void majsensitivityInfo() {
        sensitivityInfo.setText(String.valueOf(sensitivity));
    }


}