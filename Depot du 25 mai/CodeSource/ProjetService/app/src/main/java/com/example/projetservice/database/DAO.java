package com.example.projetservice.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

@Dao
public interface DAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(UtilisateurEntity... utilisateurEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ServiceEntity... ServiceEntities);


    @Delete
    void delete(UtilisateurEntity user);

    @Delete
    void delete(ServiceEntity service);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UtilisateurEntity user);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ServiceEntity service);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(DateEntity date);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(DateEntity date);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ServiceEntity service);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(UtilisateurEntity date);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRdv(RdvEntity rdv);




    @Query("SELECT * FROM UtilisateurEntity where pseudo LIKE :identifiant AND mdp LIKE :motdepasse LIMIT 1")
    UtilisateurEntity seConnecter(String identifiant, String motdepasse);

    @Query("SELECT * FROM UtilisateurEntity where pseudo LIKE :identifiant LIMIT 1")
    UtilisateurEntity getUser(String identifiant);

    @Query("SELECT * FROM UtilisateurEntity where _id LIKE :id LIMIT 1")
    UtilisateurEntity getUserbyId(int id);

    @Query("SELECT count(*) FROM UtilisateurEntity")
    int getDataCountUser();

    @Query("SELECT count(*) FROM ServiceEntity")
    int getDataCountService();

    @Query("SELECT count(*) FROM DateEntity")
    int getDataCountDate();

    @Query("SELECT * FROM ServiceEntity where  _id like :id LIMIT 1")
    ServiceEntity getService(int id);

    @Query("SELECT * FROM ServiceEntity where  userId like :userid LIMIT 1")
    ServiceEntity getServiceByUser(int userid);


    //pour lier la BDD a l'affichage dans la listView
    //   @Query("SELECT * FROM ServiceEntity")
    //  Cursor getServiceCursor();


    @Query("SELECT _id, Nom, Description, ville FROM ServiceEntity")
    Cursor getServiceCursor();

    @Query("SELECT _id, Nom, Description, ville FROM ServiceEntity where Nom LIKE :str OR Description LIKE :str ")
    Cursor getServiceCursor(String str);

    @Query("DELETE FROM ServiceEntity")
    void deleteAllservice();


    @Query("SELECT * FROM DateEntity where Lundi like :lun and Mardi like :mar and Mercredi like :mer and Jeudi like :jeu " +
            "and Vendredi like :ven and Samedi like :sam and Dimanche like :dim LIMIT 1")
    DateEntity getDateByAttribute(boolean lun, boolean mar, boolean mer,
                                  boolean jeu, boolean ven, boolean sam, boolean dim);

    @Query("SELECT * FROM DateEntity where _id = :id")
    DateEntity getDatebyId(int id);


    @Query("SELECT * FROM RdvEntity where sId = :fid and cId = :cid and dates > :after")
    Cursor getRdv(int fid, int cid, int after);

    @Query("SELECT RdvEntity._id , jour, dates, sId,UtilisateurEntity._id as userId, pseudo,nom,prenom " +
            " FROM RdvEntity  INNER JOIN UtilisateurEntity where " +
            "cId =UtilisateurEntity._id and sId = :sid and dates > :after")
    Cursor getAllRdv(int sid, int after);


    @Query("SELECT * FROM RdvEntity where sId = :sid")
    Cursor getAllRdv(int sid);



    @Query("SELECT * FROM RdvEntity where _Id = :id")
    RdvEntity getRdvById(int id);

    @Delete
    void delete(RdvEntity user);

    @Query("SELECT RdvEntity._id , jour, dates, sId,UtilisateurEntity._id as userId, pseudo,UtilisateurEntity.nom as userName,prenom, " +
            "ServiceEntity._id as servId, ServiceEntity.nom as servName" +
            " FROM  UtilisateurEntity INNER JOIN RdvEntity INNER JOIN ServiceEntity where " +
            "cId =UtilisateurEntity._id  and sId = ServiceEntity._id "+
            "and cId = :cid and dates > :after")
    Cursor getRdvClient(int cid, int after);
}