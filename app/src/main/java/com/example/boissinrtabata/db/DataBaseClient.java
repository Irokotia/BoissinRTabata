package com.example.boissinrtabata.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DataBaseClient {
    // Instance unique permettant de faire le lien avec la base de données
    private static DataBaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDataBase appDatabase;

    // Constructeur
    private DataBaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        //appDatabase = Room.databaseBuilder(context, AppDatabase.class, "EcoleDesLoustics").build();

        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDataBase.class, "TabataTimer").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DataBaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDataBase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //insert user
            db.execSQL("INSERT INTO seanceEntrainement (nomSeance,timePrepare,timeWork,timeRest,cycles,tabatas,timeLongRest,totaltabata) VALUES(\"Test1\",10,10,10,10,1,0,40);");
            db.execSQL("INSERT INTO seanceEntrainement (nomSeance,timePrepare,timeWork,timeRest,cycles,tabatas,timeLongRest,totaltabata) VALUES(\"Test2\",10,10,10,10,1,0,40);");
        }
    };

}
