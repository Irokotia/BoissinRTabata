package com.example.boissinrtabata.ui.selection;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.boissinrtabata.AddSeanceEntrainementActivity;
import com.example.boissinrtabata.GestionSeanceEntrainementActivity;
import com.example.boissinrtabata.R;
import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;
import com.example.boissinrtabata.model.TasksAdapter;
import com.example.boissinrtabata.ui.lancement.LancementFragment;

import java.util.ArrayList;
import java.util.List;

public class SelectionFragment extends Fragment {

    private SelectionViewModel selectionViewModel;
    //
    private static final int REQUEST_CODE_ADD = 0;
    private static final int REQUEST_CODE_UPDATE = 0;
    private static final int RESULT_OK = -1;


    // DATA
    private DataBaseClient mDb;
    private TasksAdapter adapter;

    // VIEW
    private Button buttonAdd;
    private ListView listSeancesEntrainement;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectionViewModel =
                ViewModelProviders.of(this).get(SelectionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_selection, container, false);
        // Récupération du DatabaseClient
        mDb = DataBaseClient.getInstance(getActivity().getApplicationContext());

        // Récupérer les vues
        listSeancesEntrainement = root.findViewById(R.id.listSeancesEntrainement);
        buttonAdd = root.findViewById(R.id.button_ajout);

        // Lier l'adapter au listView
        adapter = new TasksAdapter(getActivity(), new ArrayList<SeanceEntrainement>());
        listSeancesEntrainement.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), AddSeanceEntrainementActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });

        listSeancesEntrainement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Récupération de la tâche cliquée à l'aide de l'adapter
                SeanceEntrainement seanceEntrainement = adapter.getItem(position);

                // Message
                Toast.makeText(getActivity().getApplication(), "Seance Courante : " + seanceEntrainement.getNomSéance(), Toast.LENGTH_SHORT).show();

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
                FragmentManager fm = getFragmentManager();
                assert fm != null;
                FragmentTransaction ft = fm.beginTransaction();
                LancementFragment llf = new LancementFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable("SeanceEntrainement",seanceEntrainement);
                llf.setArguments(bundle);
                getActivity().getFragmentManager().popBackStack();
                ft.addToBackStack(null);
                ft.replace(R.id.nav_host_fragment,llf);
                ft.commit();

            }
        });
        listSeancesEntrainement.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                SeanceEntrainement seanceEntrainement = adapter.getItem(position);
                Toast.makeText(getActivity().getApplication(), "Séance Courante : " + seanceEntrainement.getNomSéance(), Toast.LENGTH_SHORT).show();

                Intent GestionCompteActivity = new Intent(getActivity().getApplication(), GestionSeanceEntrainementActivity.class);

                GestionCompteActivity.putExtra("seanceEntrainement", seanceEntrainement);

                startActivityForResult(GestionCompteActivity, REQUEST_CODE_UPDATE);

                return true;
            }
        });


        getSeancesEntrainement();
        return root;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {

            // Mise à jour des taches
            getSeancesEntrainement();
        }
    }
}