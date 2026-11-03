package com.example.rockpaperscissor1;

import java.util.Random;

public class GameChoice {
    public static final int DEFAULT = 0;
    public static final int ROCK = 1;
    public static final int PAPER = 2;
    public static final int SCISSORS = 3;

    private int mChoice = DEFAULT;
    private int mImageId;
    private Random mRandomGenerator;

    public GameChoice() {
        mRandomGenerator = new Random();
        setChoice(DEFAULT);
    }

    public int getChoice() {
        return mChoice;
    }

    public void setChoice(int choice) {
        mChoice = choice;
        switch (choice) {
            case ROCK:
                mImageId = R.drawable.rock;
                break;
            case PAPER:
                mImageId = R.drawable.paper;
                break;
            case SCISSORS:
                mImageId = R.drawable.scissors;
                break;
            default:
                mImageId = R.drawable.questionmark;
                break;
        }
    }

    public int getImageId() {
        return mImageId;
    }

    public void roll() {
        setChoice(mRandomGenerator.nextInt(3) + 1);
    }
}
