package com.example.boissinrtabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.boissinrtabata.db.DataBaseClient;
import com.example.boissinrtabata.db.SeanceEntrainement;

public class AddSeanceEntrainementActivity extends AppCompatActivity {

    private DataBaseClient mDb;
    private static final int DIALOG_ALERT = 10;
    // VIEW
    private EditText editTextNomSeance;
    private EditText editTextTimePrepare;
    private EditText editTextTimeWork;
    private EditText editTextTimeRest;
    private EditText editTextNumberCycles;
    private EditText editTextNumberTabatas;
    private EditText editTextTimeLongRest;
    private Button saveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seance_entrainement);
        // Récupération du DatabaseClient
        mDb = DataBaseClient.getInstance(getApplicationContext());

        // Récupérer les vues
        editTextNomSeance = findViewById(R.id.editTextNomSeance);
        editTextTimePrepare = findViewById(R.id.editTextTimePrepare);
        editTextTimeWork = findViewById(R.id.editTextTimeWork);
        editTextTimeRest = findViewById(R.id.editTextTimeRest);
        editTextNumberCycles = findViewById(R.id.editTextNumberCycles);
        editTextNumberTabatas = findViewById(R.id.editTextNumberTabatas);
        editTextTimeLongRest = findViewById(R.id.editTextTimeLongRest);
        saveView = findViewById(R.id.button_save);

        /*
        //Importer la photo
        btnImport = findViewById(R.id.import_img);
        selectedImg = findViewById(R.id.selected);
        // ouvre la galerie photo
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });*/
        // Associer un événement au bouton save
        saveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSeanceEntrainement();
            }
        });
    }








    /* //Charger la photo dans la vue
     protected void onActivityResult(int reqCode, int resultCode, Intent data) {
         super.onActivityResult(reqCode, resultCode, data);


         if (resultCode == RESULT_OK) {
             try {
                 // récupére image
                 final Uri imageUri = data.getData();
                 final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                 final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                 // CALL THIS METHOD TO GET THE ACTUAL PATH
                 File finalFile = new File(getRealPathFromURI(imageUri));

                 Image= String.valueOf(finalFile);
                 selectedImg.setImageBitmap(selectedImage);
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }
         }
     } */
    private void saveSeanceEntrainement() {

        // Récupérer les informations contenues dans les vues
        final String sNomSeance = editTextNomSeance.getText().toString().trim();
        final String sTimePrepare = editTextTimePrepare.getText().toString().trim();
        final String sTimeWork = editTextTimeWork.getText().toString().trim();
        final String sTimeRest = editTextTimeRest.getText().toString().trim();
        final String sNumberCycles = editTextNumberCycles.getText().toString().trim();
        final String sNumberTabatas = editTextNumberTabatas.getText().toString().trim();
        final String sTimeLongRest = editTextTimeLongRest.getText().toString().trim();

        // Vérifier les informations fournies par l'utilisateur
        if (sNomSeance.isEmpty()) {
            editTextNomSeance.setError("Nom Seance requis");
            editTextNomSeance.requestFocus();
            return;
        }

        if (sTimePrepare.isEmpty()) {
            editTextTimePrepare.setError("Temps Preparation Requis");
            editTextTimePrepare.requestFocus();
            return;
        }

        if(sTimeWork.isEmpty()){
            editTextTimeWork.setError("Temps Travail Requis");
            editTextTimeWork.requestFocus();
            return;
        }
        if (sTimeRest.isEmpty()) {
            editTextTimeRest.setError("Temps Repos Requis");
            editTextTimeRest.requestFocus();
            return;
        }

        if(sNumberCycles.isEmpty()){
            editTextNumberCycles.setError("Nombre Cycles Requis");
            editTextNumberCycles.requestFocus();
            return;
        }
        if(sNumberTabatas.isEmpty()){
            editTextNumberCycles.setError("Nombre Séquences Requis");
            editTextNumberCycles.requestFocus();
            return;
        }
        /**
         * Création d'une classe asynchrone pour sauvegarder la tache donnée par l'utilisateur
         */

        class SaveSeanceEntrainement extends AsyncTask<Void, Void, SeanceEntrainement> {

            @Override
            protected SeanceEntrainement doInBackground(Void... voids) {

                // creating a task
                SeanceEntrainement seanceEntrainement = new SeanceEntrainement();
                seanceEntrainement.setNomSéance(sNomSeance);
                seanceEntrainement.setTimePrepare(Integer.parseInt(sTimePrepare));
                seanceEntrainement.setTimeWork(Integer.parseInt(sTimeWork));
                seanceEntrainement.setTimeRest(Integer.parseInt(sTimeRest));
                seanceEntrainement.setCycles(Integer.parseInt(sNumberCycles));
                seanceEntrainement.setTabatas(Integer.parseInt(sNumberTabatas));
                seanceEntrainement.setTimeLongRest(Integer.parseInt(sTimeLongRest));



                // adding to database
                mDb.getAppDatabase()
                        .seanceEntrainementDao()
                        .insert(seanceEntrainement);

                return seanceEntrainement;
            }

            @Override
            protected void onPostExecute(SeanceEntrainement task) {
                super.onPostExecute(task);

                // Quand la tache est créée, on arrête l'activité AddTaskActivity (on l'enleve de la pile d'activités)
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Sauvegardé", Toast.LENGTH_LONG).show();
            }
        }
        SaveSeanceEntrainement st = new SaveSeanceEntrainement();
        st.execute();


    }

    public void onAddTest(View view) {
        switch (view.getId()) {
            case R.id.button_moins_prepare:
                if (!editTextTimePrepare.getText().toString().equals("0")) {
                    editTextTimePrepare.setText(String.valueOf(Integer.valueOf(editTextTimePrepare.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_prepare:
                editTextTimePrepare.setText(String.valueOf(Integer.valueOf(editTextTimePrepare.getText().toString()) + 1));
                break;
            case R.id.button_moins_work:
                if (!editTextTimeWork.getText().toString().equals("0")) {
                    editTextTimeWork.setText(String.valueOf(Integer.valueOf(editTextTimeWork.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_work:
                editTextTimeWork.setText(String.valueOf(Integer.valueOf(editTextTimeWork.getText().toString()) + 1));
                break;
            case R.id.button_moins_rest:
                if (!editTextTimeRest.getText().toString().equals("0")) {
                    editTextTimeRest.setText(String.valueOf(Integer.valueOf(editTextTimeRest.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_rest:
                editTextTimeRest.setText(String.valueOf(Integer.valueOf(editTextTimeRest.getText().toString()) + 1));
                break;
            case R.id.button_moins_cycles:
                if (!editTextNumberCycles.getText().toString().equals("1")) {
                    editTextNumberCycles.setText(String.valueOf(Integer.valueOf(editTextNumberCycles.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_cycles:
                editTextNumberCycles.setText(String.valueOf(Integer.valueOf(editTextNumberCycles.getText().toString()) + 1));
                break;
            case R.id.button_moins_tabatas:
                if (!editTextNumberTabatas.getText().toString().equals("1")) {
                    editTextNumberTabatas.setText(String.valueOf(Integer.valueOf(editTextNumberTabatas.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_tabatas:
                editTextNumberTabatas.setText(String.valueOf(Integer.valueOf(editTextNumberTabatas.getText().toString()) + 1));
                break;

            case R.id.button_moins_longrest:
                if (!editTextTimeLongRest.getText().toString().equals("1")) {
                    editTextTimeLongRest.setText(String.valueOf(Integer.valueOf(editTextTimeLongRest.getText().toString()) - 1));
                }else{
                    Toast.makeText(getApplicationContext(),"This is the minimum value !", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.button_plus_longrest:
                editTextTimeLongRest.setText(String.valueOf(Integer.valueOf(editTextNumberTabatas.getText().toString()) + 1));
                break;
        }
        RecalculTabata();
    }

    private void RecalculTabata() {
    }
}
