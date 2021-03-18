package com.m2dl.minijeu.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Score {
    int score;
    Date date;

    public Score() {
    }

    public Score(int score) {
        this.score = score;
        this.date = new Date();
    }
}
