package com.montaury.citadels;

public class Score {

    private int score;

    private Score(int score){
        this.score = score;
    }

     public void addToScore(Score toAdd){
        score += toAdd.getScore();
     }

     public int getScore(){
        return this.score;
     }

     public static Score of(int value){
         return new Score(value);
     }

}
