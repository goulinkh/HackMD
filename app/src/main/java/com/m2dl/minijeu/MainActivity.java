package com.m2dl.minijeu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private GameView gameView;
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
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        Point point = gameView.getCirclePosition();
        System.out.println("X: " + x + " Y: " + y + " z:" + z);

        point.x += x * 100;
        point.y += y * 100;
        gameView.setCirclePosition(point);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}