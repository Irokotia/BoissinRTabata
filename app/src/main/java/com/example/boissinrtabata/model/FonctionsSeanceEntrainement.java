package com.example.boissinrtabata.model;


public class FonctionsSeanceEntrainement {
    private int dureetotale;

    public int CalculTabatas(int dureePrepa,int dureeSequence, int dureeCycle, int dureeTravail, int dureeRepos, int dureeReposLong){
        dureetotale = 0;
        if((dureeCycle > 1) && (dureeSequence > 1)){
            dureetotale = dureePrepa + (((dureeTravail + dureeRepos)*dureeCycle)+ dureeReposLong)* dureeSequence - dureeReposLong - dureeRepos;
        }else if((dureeSequence == 1) && (dureeCycle == 1) || ((dureeSequence == 1) && (dureeCycle > 1))){
            dureetotale = dureePrepa + (((dureeTravail + dureeRepos)*dureeCycle)-dureeRepos)* dureeSequence;
        }else if( ((dureeSequence > 1) && (dureeCycle == 1))){
            dureetotale = dureePrepa + ((dureeTravail + dureeRepos)*dureeCycle)* dureeSequence + dureeReposLong - dureeRepos;
        }
        return dureetotale;
    }

    public void AjoutSoustractionPrepare(){

    }

    public void AjoutSoutractionWork(){

    }

    public void AjoutSoustractionRest(){

    }
    public void AjoutSoustractionCycles(){

    }
    public void AjoutSoustractionSequence(){

    }
    public void AjoutSoustractionlongRest(){

    }
}
