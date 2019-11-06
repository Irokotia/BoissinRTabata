package com.example.boissinrtabata.ui.lancement;



import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.boissinrtabata.R;

import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;
import com.example.boissinrtabata.ui.seance.SeanceFragment;


public class LancementFragment extends Fragment {
    private static final int REQUEST_CODE_UPDATE = 0;
    private LancementViewModel lancementViewModel;
    private SeanceEntrainement seanceEntrainement1;
    private DataBaseClient mDb;
    private TextView nameSeance;
    private TextView nombreRepet;
    private TextView nombreRestant;
    private TextView nombreSeries;
    private Button btnDemarrer;
    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final int RESULT_OK = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        lancementViewModel =
                ViewModelProviders.of(this).get(LancementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_lancement, container, false);
        // Récupération du DatabaseClient
        mDb = DataBaseClient.getInstance(getContext());

        // Récupération des views
        nameSeance = root.findViewById(R.id.nameSeance);
        nombreRepet = root.findViewById(R.id.NombreRepet);
        nombreRestant = root.findViewById(R.id.NombreRestant);
        nombreSeries = root.findViewById(R.id.NombreSerie);
        btnDemarrer = root.findViewById(R.id.Demarrer);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            seanceEntrainement1 = bundle.getParcelable("SeanceEntrainement");
            nameSeance.setText(seanceEntrainement1.getNomSéance());
            nombreRepet.setText("1/" + seanceEntrainement1.getCycles());
            int secondes = seanceEntrainement1.getTotaltabata() % 60;
            int minutes = (seanceEntrainement1.getTotaltabata() / 60) % 60;
            int hours = (seanceEntrainement1.getTotaltabata() / 3600) % 3600;
            nombreRestant.setText(String.format("%02d",hours)+ ":" + String.format("%02d",minutes) + ":" + String.format("%02d",secondes));
            nombreSeries.setText("1/" + seanceEntrainement1.getTabatas());

        }else{
           getFirstSeanceEntrainement();
        }
        btnDemarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity().getApplication(), "Lancement Seance : " + seanceEntrainement1.getNomSéance(), Toast.LENGTH_SHORT).show();S
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                SeanceFragment llf = new SeanceFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable("SeanceEntrainement",seanceEntrainement1);
                llf.setArguments(bundle);
                getActivity().getFragmentManager().popBackStack();
                ft.addToBackStack(null);
                ft.replace(R.id.nav_host_fragment,llf);
                ft.commit();
            }
        });


        return root;
    }
    private void getFirstSeanceEntrainement(){

        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class getFirstSeanceEntrainement extends AsyncTask<Void, Void, SeanceEntrainement> {

            @Override
            protected SeanceEntrainement doInBackground(Void... voids) {
                SeanceEntrainement firstSeanceEntrainement = mDb.getAppDatabase()
                        .seanceEntrainementDao()
                        .getFirst();
                return firstSeanceEntrainement;
            }

            @Override
            protected void onPostExecute(SeanceEntrainement seanceEntrainement) {
                seanceEntrainement1 = seanceEntrainement;
                super.onPostExecute(seanceEntrainement);
                nameSeance.setText(seanceEntrainement.getNomSéance());
                nombreRepet.setText("1/" + seanceEntrainement.getCycles());

                int secondes = seanceEntrainement.getTotaltabata() % 60;
                int minutes = (seanceEntrainement.getTotaltabata() / 60) % 60;
                nombreRestant.setText(minutes + ":" + secondes);
                nombreSeries.setText("1/" + seanceEntrainement.getTabatas());


            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        getFirstSeanceEntrainement gu = new getFirstSeanceEntrainement();
        gu.execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            // Mise à jour des taches
            getFirstSeanceEntrainement();
        }
    }

}
