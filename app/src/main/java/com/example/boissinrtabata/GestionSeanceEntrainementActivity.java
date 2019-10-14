package com.example.boissinrtabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;

public class GestionSeanceEntrainementActivity extends AppCompatActivity {


    // DATA
    private DataBaseClient mDb;

    // VIEW
    private EditText nouveauNomSeance;
    private EditText nouveauTimePrepare;
    private EditText nouveauTimeWork;
    private EditText nouveauTimeRest;
    private EditText nouveauCycles;
    private EditText nouveauTabatas;
    private TextView nouveauTotalTabata;
    private EditText nouveauTimeLongRest;
    private Button sauver;
    private Button button_supp;
    private SeanceEntrainement seanceEntrainement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_seance_entrainement);
        mDb = DataBaseClient.getInstance(getApplicationContext());

        nouveauNomSeance = findViewById(R.id.editTextNomSeance);
        nouveauTimePrepare = findViewById(R.id.editTextTimePrepare);
        nouveauTimeWork = findViewById(R.id.editTextTimeWork);
        nouveauTimeRest = findViewById(R.id.editTextTimeRest);
        nouveauCycles = findViewById(R.id.editTextNumberCycles);
        button_supp = findViewById(R.id.DeleteSeance);
        nouveauTimeLongRest = findViewById(R.id.editTextTimeLongRest);
        sauver = findViewById(R.id.ChangeSave);
        nouveauTabatas = findViewById(R.id.editTextNumberTabatas);
        nouveauTotalTabata = findViewById(R.id.total_tabata);

        Bundle data = getIntent().getExtras();
        seanceEntrainement = (SeanceEntrainement) data.getParcelable("seanceEntrainement");


        nouveauNomSeance.setText(seanceEntrainement.getNomSéance());
        nouveauTimePrepare.setText(String.valueOf(seanceEntrainement.getTimePrepare()));
        nouveauTimeWork.setText(String.valueOf(seanceEntrainement.getTimeWork()));
        nouveauTimeRest.setText(String.valueOf(seanceEntrainement.getTimeRest()));
        nouveauCycles.setText(String.valueOf(seanceEntrainement.getCycles()));
        nouveauTabatas.setText(String.valueOf(seanceEntrainement.getTabatas()));


        sauver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSeanceEntrainement();
            }
        });
        button_supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteSeanceEntrainement();
            }
        });
    }

    private void UpdateSeanceEntrainement() {

        // Récupérer les informations contenues dans les vues
        final String sNomSeance = nouveauNomSeance.getText().toString().trim();
        final String sTimePrepare = nouveauTimePrepare.getText().toString().trim();
        final String sTimeWork = nouveauTimeWork.getText().toString().trim();
        final String sTimeRest = nouveauTimeRest.getText().toString().trim();
        final String sNumberCycles = nouveauCycles.getText().toString().trim();
        final String sNumberTabatas = nouveauTabatas.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (sNomSeance.isEmpty()) {
            nouveauNomSeance.setError("Nom Seance requis");
            nouveauNomSeance.requestFocus();
            return;
        }

        if (sTimePrepare.isEmpty()) {
            nouveauTimePrepare.setError("Temps Preparation Requis");
            nouveauTimePrepare.requestFocus();
            return;
        }

        if (sTimeWork.isEmpty()) {
            nouveauTimeWork.setError("Temps Travail Requis");
            nouveauTimeWork.requestFocus();
            return;
        }
        if (sTimeRest.isEmpty()) {
            nouveauTimeRest.setError("Temps Repos Requis");
            nouveauTimeRest.requestFocus();
            return;
        }

        if (sNumberCycles.isEmpty()) {
            nouveauCycles.setError("Nombre Cycles Requis");
            nouveauCycles.requestFocus();
            return;
        }
        if (sNumberTabatas.isEmpty()) {
            nouveauTabatas.setError("Nombre Sequences Requis");
            nouveauTabatas.requestFocus();
            return;
        }
        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */

        class UpdateSeanceEntrainement extends AsyncTask<Void, Void, SeanceEntrainement> {

            @Override
            protected SeanceEntrainement doInBackground(Void... voids) {

                // creating a task
                seanceEntrainement.setNomSéance(sNomSeance);
                seanceEntrainement.setTimePrepare(Integer.parseInt(sTimePrepare));
                seanceEntrainement.setTimeWork(Integer.parseInt(sTimeWork));
                seanceEntrainement.setTimeRest(Integer.parseInt(sTimeRest));
                seanceEntrainement.setCycles(Integer.parseInt(sNumberCycles));
                seanceEntrainement.setTabatas(Integer.parseInt(sNumberTabatas));


                // adding to database
                mDb.getAppDatabase()
                        .seanceEntrainementDao()
                        .update(seanceEntrainement);

                return seanceEntrainement;
            }

            @Override
            protected void onPostExecute(SeanceEntrainement task) {
                super.onPostExecute(task);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Modifié", Toast.LENGTH_LONG).show();
            }
        }
        UpdateSeanceEntrainement st = new UpdateSeanceEntrainement();
        st.execute();


    }


    private void DeleteSeanceEntrainement() {
        /**
         * CrÃ©ation d'une classe asynchrone pour modifier le compte
         */
        class DeleteSeanceEntrainement extends AsyncTask<Void, Void, SeanceEntrainement> {

            @Override
            protected SeanceEntrainement doInBackground(Void... voids) {

                // adding to database
                mDb.getAppDatabase()
                        .seanceEntrainementDao()
                        .delete(seanceEntrainement);

                return seanceEntrainement;
            }

            @Override
            protected void onPostExecute(SeanceEntrainement seanceEntrainement) {
                super.onPostExecute(seanceEntrainement);
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Supprimé", Toast.LENGTH_LONG).show();
            }
        }

        DeleteSeanceEntrainement st = new DeleteSeanceEntrainement();
        st.execute();
    }

    public void onAddTest(View view) {
        switch (view.getId()) {
            case R.id.button_moins_prepare:
                if (!nouveauTimePrepare.getText().toString().equals("0")) {
                    nouveauTimePrepare.setText(String.valueOf(Integer.valueOf(nouveauTimePrepare.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_prepare:
                nouveauTimePrepare.setText(String.valueOf(Integer.valueOf(nouveauTimePrepare.getText().toString()) + 1));
                break;
            case R.id.button_moins_work:
                if (!nouveauTimeWork.getText().toString().equals("0")) {
                    nouveauTimeWork.setText(String.valueOf(Integer.valueOf(nouveauTimeWork.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_work:
                nouveauTimeWork.setText(String.valueOf(Integer.valueOf(nouveauTimeWork.getText().toString()) + 1));
                break;
            case R.id.button_moins_rest:
                if (!nouveauTimeRest.getText().toString().equals("0")) {
                    nouveauTimeRest.setText(String.valueOf(Integer.valueOf(nouveauTimeRest.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_rest:
                nouveauTimeRest.setText(String.valueOf(Integer.valueOf(nouveauTimeRest.getText().toString()) + 1));
                break;
            case R.id.button_moins_cycles:
                if (!nouveauCycles.getText().toString().equals("1")) {
                    nouveauCycles.setText(String.valueOf(Integer.valueOf(nouveauCycles.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_cycles:
                nouveauCycles.setText(String.valueOf(Integer.valueOf(nouveauCycles.getText().toString()) + 1));
                break;
            case R.id.button_moins_tabatas:
                if (!nouveauTabatas.getText().toString().equals("1")) {
                    nouveauTabatas.setText(String.valueOf(Integer.valueOf(nouveauTabatas.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_tabatas:
                nouveauTabatas.setText(String.valueOf(Integer.valueOf(nouveauTabatas.getText().toString()) + 1));
                break;

            case R.id.button_moins_longrest:
                if (!nouveauTimeLongRest.getText().toString().equals("1")) {
                    nouveauTimeLongRest.setText(String.valueOf(Integer.valueOf(nouveauTimeLongRest.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_longrest:
                nouveauTimeLongRest.setText(String.valueOf(Integer.valueOf(nouveauTimeLongRest.getText().toString()) + 1));
                break;
        }
        //RecalculTabata();
    }
}
