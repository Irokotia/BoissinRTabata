package com.example.boissinrtabata.db;



import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = SeanceEntrainement.class, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SeanceEntrainementDao seanceEntrainementDao();
}