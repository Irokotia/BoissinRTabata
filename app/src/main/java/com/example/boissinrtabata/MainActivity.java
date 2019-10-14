package com.example.boissinrtabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onChoixSeance(View view) {
        Intent ChoixCompteViewActivityIntent = new Intent(MainActivity.this, ChoixSeanceEntrainementActivity.class);

        // Lancement de la demande de changement d'activité
        startActivity(ChoixCompteViewActivityIntent);
    }
}
