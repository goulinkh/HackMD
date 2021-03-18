package com.m2dl.minijeu.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Score {
    public int score;
    public Date date;

    public Score() {
    }

    public Score(int score) {
        this.score = score;
        this.date = new Date();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = new Date();
    }
}
