package com.example.projetservice.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = @ForeignKey(
        entity = UtilisateurEntity.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE
))
public class ServiceEntity {
    @PrimaryKey(autoGenerate = true)
    public Integer _id=0;

    @ColumnInfo(name = "Nom")
    public String nom;

    @ColumnInfo(name = "Description")
    public String description;

    @ColumnInfo(name = "Categorie")
    public String categorie;

    @ColumnInfo(name = "Adresse")
    public String Adresse;

    @ColumnInfo(name = "Ville")
    public String ville;

    @ColumnInfo(name = "Tarif")
    public String tarif;

    @ColumnInfo(name ="userId")
    public Integer userId;


}

