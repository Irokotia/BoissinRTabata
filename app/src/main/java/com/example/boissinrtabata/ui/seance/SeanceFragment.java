package com.example.boissinrtabata.ui.seance;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.boissinrtabata.R;
import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;

public class SeanceFragment extends Fragment {

    private SeanceViewModel seanceViewModel;
    private SeanceEntrainement seanceEntrainement;
    private DataBaseClient mDb;
    private TextView nameSeance;
    private TextView nombreRepet;
    private TextView nombreRestant;
    private TextView nombreSeries;
    private int nombrerepet = 0;
    private int nombreseries = 1;
    private TextView timeChrono;
    private Button btnpause_reprendre;
    private Button btnLock;
    private Button btnArreter;
    private long timeGeneral;
    private CountDownTimer timerGeneral;
    private long timeLocal;
    private long timerProgressBar = 0;
    private CountDownTimer timerLocal;
    private CountDownTimer timerTest;
    private int etat_courant;
    private ProgressBar progressBar;
    private int i = 0;
    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final int RESULT_OK = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seanceViewModel =
                ViewModelProviders.of(this).get(SeanceViewModel.class);
        View root = inflater.inflate(R.layout.fragment_seance, container, false);
        // Récupération du DatabaseClient
        mDb = DataBaseClient.getInstance(getContext());
        etat_courant = 0;

        // Récupération des views
        nameSeance = root.findViewById(R.id.nameSeance);
        nombreRepet = root.findViewById(R.id.NombreRepet);
        nombreRestant = root.findViewById(R.id.NombreRestant);
        nombreSeries = root.findViewById(R.id.NombreSerie);
        timeChrono = root.findViewById(R.id.timeChrono);
        btnpause_reprendre = root.findViewById(R.id.Pause_Rependre);
        btnLock = root.findViewById(R.id.Lock);
        btnArreter = root.findViewById(R.id.Stop);
        progressBar = root.findViewById(R.id.progress_bar_seance);
        progressBar.setProgress(1);
        nameSeance.setText("Préparation");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            seanceEntrainement = bundle.getParcelable("SeanceEntrainement");
            nombreRepet.setText("1/" + seanceEntrainement.getCycles());
            int secondes = seanceEntrainement.getTotaltabata() % 60;
            int minutes = (seanceEntrainement.getTotaltabata() / 60) % 60;
            int heures = (seanceEntrainement.getTotaltabata() / 3600) % 3600;
            nombreRestant.setText(heures + ":" + minutes + ":" + secondes);
            nombreSeries.setText("1/" + seanceEntrainement.getTabatas());
        }

        LancementGeneral(seanceEntrainement);
        btnpause_reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnpause_reprendre.getText().toString().equals("Pause")) {
                    btnpause_reprendre.setText("Reprendre");
                    timerGeneral.cancel();
                    timerLocal.cancel();
                } else if (btnpause_reprendre.getText().toString().equals("Reprendre")) {
                    btnpause_reprendre.setText("Pause");
                    startTimerGeneral(timeGeneral);
                    i = i-1;
                    startTimerLocal(timeLocal,timerProgressBar,etat_courant);

                }
            }
        });
        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnpause_reprendre.isEnabled() && btnArreter.isEnabled()) {
                    btnpause_reprendre.setEnabled(false);
                    btnArreter.setEnabled(false);
                    btnLock.setBackgroundResource(R.drawable.border_lock_on);
                } else {
                    btnpause_reprendre.setEnabled(true);
                    btnArreter.setEnabled(true);
                    btnLock.setBackgroundResource(R.drawable.border);

                }
            }
        });
        btnArreter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerLocal.cancel();
                timerGeneral.cancel();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return root;
    }

    private void startTimerGeneral(long timerStartFrom) {
        timerTest = new CountDownTimer(5000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerGeneral = new CountDownTimer(timerStartFrom, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //updating the global variable
                timeGeneral = millisUntilFinished;
                int secondes = ((int) millisUntilFinished / 1000) % 60;
                int minutes = (((int) millisUntilFinished / 1000) / 60) % 60;
                int heures = (seanceEntrainement.getTotaltabata() / 3600) % 3600;
                nombreRestant.setText(String.format("%02d", heures) + ":" + String.format("%02d", minutes) + ":"
                        + String.format("%02d", secondes));

            }

            @Override
            public void onFinish() {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }.start();
    }
    private void startTimerLocal(final long timerStartFrom,final long timerprogressBar, final int etatcourant) {
        if(timerprogressBar != 0){
            timerProgressBar = timerprogressBar;
        }else{
            timerProgressBar = timerStartFrom;
        }
        timerLocal = new CountDownTimer(timerStartFrom, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //updating the global variable
                if(timerProgressBar != 0){
                    progressBar.setProgress((int)i*100/ ((int) timerProgressBar /1000));
                }else{
                    progressBar.setProgress((int)i*100/ ((int)timerStartFrom/1000));
                }
                i++;
                timeLocal = millisUntilFinished;
                int secondes = ((int) millisUntilFinished / 1000) % 60;
                int minutes = (((int) millisUntilFinished / 1000) / 60) % 60;
                timeChrono.setText(String.format("%02d", minutes) + ":"
                        + String.format("%02d", secondes));
            }

            @Override
            public void onFinish() {
                i = 0;
                progressBar.setProgress(i);
                if (nombreseries <= seanceEntrainement.getTabatas()) {
                    if (etatcourant == 0) {
                        Drawable drawable = getResources().getDrawable(R.drawable.drawable_round_chrono_work);
                        progressBar.setProgressDrawable(drawable);
                        etat_courant = 1;
                        startTimerLocal(seanceEntrainement.getTimeWork() * 1000, 0, etat_courant);
                        nameSeance.setText("Travail");
                        nombrerepet += 1;
                        if (nombrerepet > seanceEntrainement.getCycles()) {
                            nombrerepet = 1;
                            nombreseries += 1;
                        }
                        nombreRepet.setText(nombrerepet + "/" + seanceEntrainement.getCycles());
                        nombreSeries.setText(nombreseries + "/" + seanceEntrainement.getTabatas());
                    } else if (etatcourant == 1) {
                        Drawable drawable = getResources().getDrawable(R.drawable.drawable_round_chrono_rest);
                        progressBar.setProgressDrawable(drawable);
                        etat_courant = 0;
                        startTimerLocal((seanceEntrainement.getTimeRest() * 1000) + 1000, 0, etat_courant);
                        nameSeance.setText("Repos");

                    }

                }else{

                }
            }
        }.start();
    }

    private void LancementGeneral(SeanceEntrainement seanceEntrainement){
        startTimerGeneral((seanceEntrainement.getTotaltabata()*1000)+1000);
        LancementLocal(seanceEntrainement);
    }
    private void LancementLocal(SeanceEntrainement seanceEntrainement){
        startTimerLocal((seanceEntrainement.getTimePrepare()*1000)+1000,0,etat_courant);
    }
}
