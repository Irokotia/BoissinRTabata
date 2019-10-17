package com.example.boissinrtabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boissinrtabata.db.SeanceEntrainement;

public class RecapitulatifSeanceEntrainementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recapitulatif_seance_entrainement);
    }

    public void onSeanceEntrainement(View view) {
        Intent ChoixCompteViewActivityIntent = new Intent(RecapitulatifSeanceEntrainementActivity.this, SeanceEntrainement.class);
        // Lancement de la demande de changement d'activité
        startActivity(ChoixCompteViewActivityIntent);
    }
}
