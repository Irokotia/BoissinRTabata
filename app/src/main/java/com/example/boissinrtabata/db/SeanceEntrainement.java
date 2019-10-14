package com.example.boissinrtabata.db;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SeanceEntrainement implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nomSeance")
    private String nomSéance;

    @ColumnInfo(name = "timePrepare")
    private int timePrepare;

    @ColumnInfo(name = "timeWork")
    private int timeWork;

    @ColumnInfo(name = "timeRest")
    private int timeRest;

    @ColumnInfo(name = "cycles") // prepa + cycle * (work + rest )
    private int cycles;

    @ColumnInfo(name = "tabatas") // série x  temps cycles
    private int tabatas;

    @ColumnInfo(name = "timeLongRest")
    private int timeLongRest;

    @ColumnInfo(name = "totaltabata")
    private int totaltabata;


    public SeanceEntrainement(){}
    protected SeanceEntrainement(Parcel in) {
        id = in.readInt();
        nomSéance = in.readString();
        timePrepare = in.readInt();
        timeWork = in.readInt();
        timeRest = in.readInt();
        cycles = in.readInt();
        tabatas = in.readInt();
        timeLongRest = in.readInt();
        totaltabata = in.readInt();
    }

    public static final Creator<SeanceEntrainement> CREATOR = new Creator<SeanceEntrainement>() {
        @Override
        public SeanceEntrainement createFromParcel(Parcel in) {
            return new SeanceEntrainement(in);
        }

        @Override
        public SeanceEntrainement[] newArray(int size) {
            return new SeanceEntrainement[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSéance() {
        return nomSéance;
    }

    public void setNomSéance(String nomSéance) {
        this.nomSéance = nomSéance;
    }

    public int getTimePrepare() {
        return timePrepare;
    }

    public void setTimePrepare(int timePrepare) {
        this.timePrepare = timePrepare;
    }

    public int getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(int timeWork) {
        this.timeWork = timeWork;
    }

    public int getTimeRest() {
        return timeRest;
    }

    public void setTimeRest(int timeRest) {
        this.timeRest = timeRest;
    }

    public int getCycles() {
        return cycles;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public int getTabatas() {
        return tabatas;
    }

    public void setTabatas(int tabatas) {
        this.tabatas = tabatas;
    }


    public int getTimeLongRest() {
        return timeLongRest;
    }

    public void setTimeLongRest(int timeLongRest) {
        this.timeLongRest = timeLongRest;
    }

    public int getTotaltabata() {
        return totaltabata;
    }

    public void setTotaltabata(int totaltabata) {
        this.totaltabata = totaltabata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nomSéance);
        dest.writeInt(timePrepare);
        dest.writeInt(timeWork);
        dest.writeInt(timeRest);
        dest.writeInt(cycles);
        dest.writeInt(tabatas);
        dest.writeInt(timeLongRest);
        dest.writeInt(totaltabata);
    }
}

