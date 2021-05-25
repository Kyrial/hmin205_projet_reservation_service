package com.example.projetservice.database;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class UtilisateurEntity implements Serializable{
    @PrimaryKey (autoGenerate = true)
    public int _id=0;

    @ColumnInfo(name = "pseudo")
    public String pseudo;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "mdp")
    public String mdp;

    @ColumnInfo(name = "type")
    public String type;



    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "prenom")
    public String prenom;


}
