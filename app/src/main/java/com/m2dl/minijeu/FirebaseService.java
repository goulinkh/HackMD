package com.m2dl.minijeu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2dl.minijeu.models.Score;

import java.util.List;

public class FirebaseService {


    public FirebaseService() {
    }

    public Score writeNewScore(Score score) {
        DatabaseReference myDb = FirebaseDatabase.getInstance().getReference();
        List<Score> scores = (List<Score>) myDb.child("scores").get().getResult().getValue();
        System.out.println("scores " + scores);
        return null;
    }
}
