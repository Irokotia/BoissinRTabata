package com.example.boissinrtabata;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;
import com.example.boissinrtabata.model.TasksAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChoixSeanceEntrainementActivity extends AppCompatActivity {

    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final int REQUEST_CODE_UPDATE = 0;


    // DATA
    private DataBaseClient mDb;
    private TasksAdapter adapter;

    // VIEW
    private Button buttonAdd;
    private ListView listSeancesEntrainement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_seance_entrainement);

        // Récupération du DatabaseClient
        mDb = DataBaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        listSeancesEntrainement = findViewById(R.id.listSeancesEntrainement);
        buttonAdd = findViewById(R.id.button_ajout);

        // Lier l'adapter au listView
        adapter = new TasksAdapter(this, new ArrayList<SeanceEntrainement>());
        listSeancesEntrainement.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoixSeanceEntrainementActivity.this, AddSeanceEntrainementActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listSeancesEntrainement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                SeanceEntrainement seanceEntrainement = adapter.getItem(position);

                // Message
                Toast.makeText(ChoixSeanceEntrainementActivity.this, "Seance Courante : " + seanceEntrainement.getNomSéance(), Toast.LENGTH_SHORT).show();

                Intent ExeMenuPViewActivityIntent = new Intent(ChoixSeanceEntrainementActivity.this, RecapitulatifSeanceEntrainementActivity.class);

               /* // récupération des scores et utilisateur courant
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setNomCourant(user.getNom());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setPrenomCourant(user.getPrénom());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setImageCourante(user.getImage());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreAdd(user.getScoreAdd());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreSous(user.getScoreSous());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreDiv(user.getScoreDiv());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreMul(user.getScoreMul());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreChro(user.getScoreChr());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreGeo(user.getScoreGeo());
                ((MyApplication) ChoixCompteActivity.this.getApplication()).setScoreFr(user.getScoreFr());
                // Lancement de la demande de changement d'activité*/
                startActivity(ExeMenuPViewActivityIntent);


            }
        });
        listSeancesEntrainement.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                SeanceEntrainement seanceEntrainement = adapter.getItem(position);
                Toast.makeText(ChoixSeanceEntrainementActivity.this, "Séance Courante : " + seanceEntrainement.getNomSéance(), Toast.LENGTH_SHORT).show();

                Intent GestionCompteActivity = new Intent(ChoixSeanceEntrainementActivity.this, GestionSeanceEntrainementActivity.class);

                GestionCompteActivity.putExtra("seanceEntrainement", seanceEntrainement);

                startActivityForResult(GestionCompteActivity, REQUEST_CODE_UPDATE);

                return true;
            }
        });


        getSeancesEntrainement();
    }

    private void getSeancesEntrainement(){

        ///////////////////////
        // Classe asynchrone permettant de récupérer des taches et de mettre à jour le listView de l'activité
        class getUsers extends AsyncTask<Void, Void, List<SeanceEntrainement>> {

            @Override
            protected List<SeanceEntrainement> doInBackground(Void... voids) {
                List<SeanceEntrainement> userlist = mDb.getAppDatabase()
                        .seanceEntrainementDao()
                        .getAll();
                return userlist;
            }

            @Override
            protected void onPostExecute(List<SeanceEntrainement> users) {
                super.onPostExecute(users);

                // Mettre à jour l'adapter avec la liste de taches
                adapter.clear();
                adapter.addAll(users);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        //////////////////////////
        // IMPORTANT bien penser à executer la demande asynchrone
        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        getUsers gu = new getUsers();
        gu.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            // Mise à jour des taches
            getSeancesEntrainement();
        }
    }
}
