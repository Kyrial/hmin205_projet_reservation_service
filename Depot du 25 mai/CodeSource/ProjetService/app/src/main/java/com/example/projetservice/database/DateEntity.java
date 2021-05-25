package com.example.projetservice.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DateEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int _id=0;

    @ColumnInfo(name = "lundi")
    public boolean lundi;

    @ColumnInfo(name = "mardi")
    public boolean mardi;

    @ColumnInfo(name = "mercredi")
    public boolean mercredi;

    @ColumnInfo(name = "jeudi")
    public boolean jeudi;



    @ColumnInfo(name = "vendredi")
    public boolean vendredi;

    @ColumnInfo(name = "samedi")
    public boolean samedi;

    @ColumnInfo(name = "dimanche")
    public boolean dimanche;



}