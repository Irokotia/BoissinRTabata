package com.example.boissinrtabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class SeanceEntrainementActivity extends AppCompatActivity {


    private TextView TextChrono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance_entrainement);

        TextChrono = findViewById(R.id.textChrono);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                TextChrono.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                TextChrono.setText("done!");
            }
        }.start();
    }



}
