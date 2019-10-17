package com.example.boissinrtabata.model;

import java.util.ArrayList;

public class CalculSeanceEntrainement {
    private int dureetotale;

    public int CalculTabatas(int dureePrepa,int dureeSequence, int dureeCycle, int dureeTravail, int dureeRepos, int dureeReposLong){
        dureetotale = 0;
        dureetotale = (dureePrepa + ((dureeTravail + dureeRepos)*dureeCycle) + dureeReposLong)* dureeSequence;
        return dureetotale;
    }
}
