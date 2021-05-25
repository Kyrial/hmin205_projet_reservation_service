package com.example.projetservice.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(

        foreignKeys = {@ForeignKey(
        entity = UtilisateurEntity.class,
        parentColumns = "_id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE),

         @ForeignKey(
        entity = DateEntity.class,
        parentColumns = "_id",
        childColumns = "dateId",
        onDelete = ForeignKey.CASCADE)
                 }
)
public class ServiceEntity {
    @PrimaryKey(autoGenerate = true)
    public int _id=0;

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

    @ColumnInfo(name ="dateId")
    public Integer dateId;

  /*  @Ignore
    public ServiceEntity(){
        this._id = _id;

    }*/

}

