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
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView;
    private double vitesse;

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
        gameView = new GameView(this);
        setContentView(gameView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        vitesse = 1.0;

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[1];
        float y = sensorEvent.values[0];
        float z = sensorEvent.values[2];
        Point point = gameView.getCirclePosition();
        point.x += x * 150 * vitesse;
        point.y += y * 150 * vitesse;
        gameView.setCirclePosition(point);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}