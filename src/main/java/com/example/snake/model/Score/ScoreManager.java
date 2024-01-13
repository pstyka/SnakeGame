package com.example.snake.model.Score;

import java.io.*;

public class ScoreManager implements ScoreObserver{
    private static ScoreManager instance = new ScoreManager();
    private int currentScore;
    private int bestScore;
    private static final String PATH = "src/main/resources/bestscore.txt";


    private ScoreManager() {

        currentScore = 0;
        bestScore = loadBestScoreFromStorage();
    }
    public static ScoreManager getInstance() {
        return instance;
    }
    @Override
    public void updateScore(int newScore) {
        currentScore = newScore;
        if (currentScore > bestScore) {
            bestScore = currentScore;
            saveBestScoreToStorage();
        }
    }
    public int getCurrentScore() {
        return currentScore;
    }

    public int getBestScore() {
        return bestScore;
    }
    public void resetCurrentScore(){
        this.currentScore=0;
    }
    public void updateCurrentScore(int points){
        this.currentScore +=points;
    }

    private int loadBestScoreFromStorage() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            for(String line; (line = br.readLine()) != null; ) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void saveBestScoreToStorage() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            bw.write(String.valueOf(bestScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
