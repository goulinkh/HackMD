package com.m2dl.minijeu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2dl.minijeu.models.Score;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StartActivity extends AppCompatActivity {

    private LinearLayout buttonsLayout;
    private TableLayout scoresTable;
    private Button startButton;
    private Button quitButton;
    private FirebaseService firebaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseService = new FirebaseService();

        setContentView(R.layout.activity_start);
        startButton = findViewById(R.id.buttonStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent startGame = new Intent(StartActivity.this, MainActivity.class);
                StartActivity.this.startActivity(startGame);
            }
        });
        quitButton = findViewById(R.id.buttonQuit);
        quitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        scoresTable = findViewById(R.id.tableScores);



        DatabaseReference myDb = FirebaseDatabase.getInstance().getReference();
        Score score = new Score(1);
        String scoreId = UUID.randomUUID().toString();
        StartActivity _this = this;
        firebaseService.getMyDb().child("scores").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Iterator<DataSnapshot> ite =  task.getResult().getChildren().iterator();
                ArrayList<Score> listScores = new ArrayList();
                while (ite.hasNext()) {
                    listScores.add(ite.next().getValue(Score.class));

                }

                System.out.println(listScores.get(0).getScore());

                listScores.sort(Comparator.comparing(Score::getScore));

                //Collections.sort(listScores,(score1, t1) -> score1.getScore() - t1.getScore());
                System.out.println(listScores);

                //listScores.sort((score1,score2) -> score1.getScore() - score2.getScore());


                for(int i=0;i<5;i++){
                    TableRow row = new TableRow(_this);
                    TextView content = new TextView(_this);
                    content.setGravity(Gravity.CENTER_HORIZONTAL);
                    content.setText("");

                    if(i < listScores.size()-1){
                        content.setText(Integer.toString(listScores.get(i).getScore()));
                    }

                    row.addView(content);
                    scoresTable.addView(row);
                }

            }
        });

    }

}