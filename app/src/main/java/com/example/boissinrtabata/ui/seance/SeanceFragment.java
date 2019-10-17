package com.example.boissinrtabata.ui.seance;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.boissinrtabata.R;
import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;
import com.example.boissinrtabata.ui.lancement.LancementViewModel;

public class SeanceFragment extends Fragment {

    private SeanceViewModel seanceViewModel;
    private SeanceEntrainement seanceEntrainement;
    private DataBaseClient mDb;
    private TextView nameSeance;
    private TextView nombreRepet;
    private TextView nombreRestant;
    private TextView nombreSeries;
    private TextView timeChrono;
    private Button btnpause_reprendre;
    private Button btnLock;
    private Button btnArreter;
    private long time_restant;
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

        // Récupération des views
        nameSeance = root.findViewById(R.id.nameSeance);
        nombreRepet = root.findViewById(R.id.NombreRepet);
        nombreRestant = root.findViewById(R.id.NombreRestant);
        nombreSeries = root.findViewById(R.id.NombreSerie);
        timeChrono = root.findViewById(R.id.timeChrono);
        btnpause_reprendre = root.findViewById(R.id.Pause_Rependre);
        btnLock = root.findViewById(R.id.Lock);
        btnArreter = root.findViewById(R.id.Stop);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            seanceEntrainement = bundle.getParcelable("SeanceEntrainement");
            nameSeance.setText(seanceEntrainement.getNomSéance());
            nombreRepet.setText("1/" + seanceEntrainement.getCycles());
            int secondes = seanceEntrainement.getTotaltabata() % 60;
            int minutes = (seanceEntrainement.getTotaltabata() / 60) % 60;
            nombreRestant.setText(minutes + ":" + secondes);
            nombreSeries.setText("1/" + seanceEntrainement.getTabatas());
        }
        final CountDownTimer countDownTimer = new CountDownTimer(seanceEntrainement.getTotaltabata()*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                time_restant = millisUntilFinished;
                int secondes = ( (int) millisUntilFinished / 1000) % 60;
                int minutes = (((int) millisUntilFinished / 1000) / 60) % 60;
                timeChrono.setText(minutes +":" + secondes);
                nombreRestant.setText(minutes +":" + secondes);
            }

            public void onFinish() {
                timeChrono.setText("done!");
            }
        };
        countDownTimer.start();
        btnpause_reprendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnpause_reprendre.getText().toString().equals("Pause")){
                    btnpause_reprendre.setText("Reprendre");
                    countDownTimer.cancel();
                }else if(btnpause_reprendre.getText().toString().equals("Reprendre")){
                    btnpause_reprendre.setText("Pause");
                   countDownTimer.onTick(time_restant);
                }
            }
        });

        btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnpause_reprendre.isEnabled() && btnArreter.isEnabled()){
                    btnpause_reprendre.setEnabled(false);
                    btnArreter.setEnabled(false);
                    btnLock.setBackgroundResource(R.drawable.border_lock_on);
                }else{
                    btnpause_reprendre.setEnabled(true);
                    btnArreter.setEnabled(true);
                    btnLock.setBackgroundResource(R.drawable.border);
                    
                }
            }
        });







        return root;
    }
}
