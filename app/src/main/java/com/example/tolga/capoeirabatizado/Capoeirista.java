package com.example.tolga.capoeirabatizado;

import android.os.Parcelable;

public class Capoeirista{

    String name;
    int numberOfMatches;
    int numberOfWins;
    int numberOfLoses;
    int numberOfTies;

    public Capoeirista( String name)
    {
        this.name = name;
        numberOfLoses = 0;
        numberOfMatches = 0;
        numberOfTies = 0;
        numberOfWins = 0;
    }

    public void win()
    {
        numberOfWins ++;
    }
    public void lose()
    {
        numberOfLoses ++;
    }
    public void tie()
    {
        numberOfTies ++;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public int getNumberOfLoses() {
        return numberOfLoses;
    }

    public int getNumberOfTies() {
        return numberOfTies;
    }
}
