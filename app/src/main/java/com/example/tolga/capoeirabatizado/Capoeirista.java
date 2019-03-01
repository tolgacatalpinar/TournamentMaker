package com.example.tolga.capoeirabatizado;

public class Capoeirista {

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

}
