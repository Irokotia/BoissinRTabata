package com.example.boissinrtabata.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SeanceEntrainementDao {

    @Query("SELECT * FROM SeanceEntrainement")
    List<SeanceEntrainement> getAll();

    @Insert
    void insert(SeanceEntrainement seanceEntrainement);

    @Insert
    long[] insertAll(SeanceEntrainement... seancesEntrainement);

    @Delete
    void delete(SeanceEntrainement seanceEntrainement);

    @Update
    void update(SeanceEntrainement seanceEntrainement);
}
