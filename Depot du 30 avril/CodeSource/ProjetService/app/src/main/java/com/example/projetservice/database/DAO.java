package com.example.projetservice.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ServiceEntity service);


    @Query("SELECT * FROM UtilisateurEntity where pseudo LIKE :identifiant AND mdp LIKE :motdepasse LIMIT 1")
    UtilisateurEntity seConnecter(String identifiant, String motdepasse);

    @Query("SELECT * FROM UtilisateurEntity where pseudo LIKE :identifiant LIMIT 1")
    UtilisateurEntity getUser(String identifiant);


    @Query("SELECT count(*) FROM UtilisateurEntity")
    int getDataCountUser();
    @Query("SELECT count(*) FROM ServiceEntity")
    int getDataCountService();

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

}