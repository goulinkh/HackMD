package com.m2dl.minijeu;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2dl.minijeu.models.Score;

public class FirebaseService {

    private DatabaseReference myDb;

    public FirebaseService() {
        this.myDb = FirebaseDatabase.getInstance().getReference();
    }

    public Score writeNewScore(Score score){
        //qmyDb.child("scores").push();

        return null;
    }
}
