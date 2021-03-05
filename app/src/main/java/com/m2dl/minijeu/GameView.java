package com.m2dl.minijeu;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
    private GameThread thread;
    private int x=0;
    private MainActivity context;
    private List<Point> rectangles;

    SharedPreferences sharedPref;

    public GameView(MainActivity context) {
        super(context);
        this.context = context;
        setFocusable(true);
        getHolder().addCallback(this);
        this.sharedPref = context.getPreferences(Context.MODE_PRIVATE);
        rectangles = new ArrayList<Point>();
    }

    private Runnable mAddPoint = new Runnable(){
        @Override
        public void run() {
            Handler handler = new Handler();
            addPoint(rectangles.size());
            handler.postDelayed(mAddPoint,1000);
        }
    };

    public void addPoint(int index){
        int valeur_y = sharedPref.getInt("valeur_y", 0);
        rectangles.add(new Point(0,valeur_y + 120 * index));

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Handler handler = new Handler();
        addPoint(0);
        thread = new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();

        handler.postDelayed(mAddPoint,1000);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void update() {
        Display display = context.getDisplay();
        Point size = new Point();
        display.getSize(size);
        for(Point rect: rectangles){
            rect.x = (rect.x+1) % size.x;
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(canvas != null){
            canvas.drawColor(Color.WHITE);
            Paint paint = new Paint();
            paint.setColor(Color.rgb(250,0,0));
            rectangles.forEach( rectangle -> {
                System.out.println("Draw" +rectangle);
                canvas.drawRect(rectangle.x,rectangle.y,rectangle.x+100,rectangle.y+100,paint);
            });
        }
    }
}
