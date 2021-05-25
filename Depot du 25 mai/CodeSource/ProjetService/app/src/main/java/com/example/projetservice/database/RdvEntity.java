package com.example.projetservice.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.projetservice.database.DateEntity;

import java.util.Date;

@Entity(
        foreignKeys = {@ForeignKey(
        entity = UtilisateurEntity.class,
        parentColumns = "_id",
        childColumns = "cId",
        onDelete = ForeignKey.CASCADE),

@ForeignKey(
        entity = ServiceEntity.class,
        parentColumns = "_id",
        childColumns = "sId",
        onDelete = ForeignKey.CASCADE)
                 }
)
public class RdvEntity{
    @PrimaryKey(autoGenerate = true)
    public int _id=0;

    @ColumnInfo(name = "sId")
    public int  sId;

    @ColumnInfo(name = "cId")
    public int  cId;

    @ColumnInfo(name = "dates")
    public int dates;

    @ColumnInfo(name = "jour")
    public String jour;
}
