package com.montaury.citadels;

public class Score {

    private int score;

    public Score(){
        this.score = 0;
    }

     public void addToScore(int toAdd){
        score += toAdd;
     }

     public int getScore(){
        return this.score;
     }

}
