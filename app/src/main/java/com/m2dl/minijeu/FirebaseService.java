package com.m2dl.minijeu;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.m2dl.minijeu.models.Score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FirebaseService {
    DatabaseReference myDb ;

    public FirebaseService() {
        myDb = FirebaseDatabase.getInstance().getReference();
    }


    public void writeNewScore(Score score) {
        String scoreId = UUID.randomUUID().toString();
        myDb.child("scores").child(scoreId).setValue(score).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("Salut monde");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                System.out.println("Re !");

            }
        });
    }

    public DatabaseReference getMyDb() {
        return myDb;
    }
}
