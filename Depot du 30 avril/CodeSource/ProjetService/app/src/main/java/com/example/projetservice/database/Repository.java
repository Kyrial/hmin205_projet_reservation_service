package com.example.projetservice.database;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import java.io.Serializable;
import java.util.ArrayList;

//Gère les requete envoyer a la BDD
public class Repository {
    private DAO dao;


    public Repository(Context context) {
        AppDataBase db = AppDataBase.getDatabase(context);
        dao = db.dao();
        Log.v("1_repesitory", "bdd chargé");
    }

    public void insertUser(UtilisateurEntity user) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(user);
        });
    }

    public void insertService(ServiceEntity service) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(service);
        });
    }


    public UtilisateurEntity connexion(String identifiant, String motdepasse) {
        return dao.seConnecter(identifiant, motdepasse);
    }

    public UtilisateurEntity getUser(String identifiant) {
        return dao.getUser(identifiant);
    }


    public UtilisateurEntity inscription(String identifiant, String motdepasse, String mail, String type) {
        UtilisateurEntity user = dao.getUser(identifiant);
        if (user != null)
            return null;
        else {
            UtilisateurEntity newUser = new UtilisateurEntity();
            newUser.pseudo = identifiant;
            newUser.mdp = motdepasse;
            newUser.email = mail;
            newUser.type = type;

            newUser.id = dao.getDataCountUser() + 1;
            dao.insert(newUser);
            return newUser;
        }
    }


    public void deletesAllService() {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            dao.deleteAllservice();
        });
    }


    public Cursor getCursorService() {
        return dao.getServiceCursor();
    }

    public Cursor getCursorService(String str) {
        if (str != "" || str != null) {
            str = "%" + str + "%";
            return dao.getServiceCursor(str);
        }
        return dao.getServiceCursor();
    }


  /*  public ServiceEntity getService(Integer id) {
        return dao.getService(id);
    }*/
    public ServiceEntity getServiceByUser(Integer userId) {
        return dao.getServiceByUser(userId);
    }


    public ServiceEntity ajoutService(UtilisateurEntity ue, ServiceEntity se) {
        ServiceEntity alreadyService = getServiceByUser(ue.id);
        if (alreadyService == null) {
            se._id = dao.getDataCountService() + 1;
            insertService(se);

            return se;
        } else
            return updateService(se, alreadyService);
    }


    public ServiceEntity updateService(ServiceEntity newS, ServiceEntity lastS) {
        if (newS.nom != null && !newS.nom.equals(""))
            lastS.nom = newS.nom;
        if (newS.description != null && !newS.description.equals(""))
            lastS.description = newS.description;
        if (newS.ville != null && !newS.ville.equals(""))
            lastS.ville = newS.ville;
        if (newS.Adresse != null && !newS.Adresse.equals(""))
            lastS.Adresse = newS.Adresse;
        if (newS.tarif != null && !newS.tarif.equals(""))
            lastS.tarif = newS.tarif;

        dao.update(lastS);
        return lastS;
    }

    public void delete(ServiceEntity se) {
        dao.delete(se);
    }







    public void genereData(){
        AppDataBase.databaseWriteExecutor.execute(() -> {
        if (dao.getDataCountUser() <30) {
            for (int i = 1; i < 30; i++) {
                UtilisateurEntity newUser = new UtilisateurEntity();
                Integer val = (i * 1234);
                newUser.pseudo = val.toString();
                newUser.mdp = val.toString();
                newUser.email = val.toString();
                newUser.type = "Fournisseur";
                newUser.id = dao.getDataCountUser() + 1;
                dao.insert(newUser);
            }

            for (int i = 1; i < 30; i++) {
                Integer val = (i * 1234);
                ServiceEntity se = new ServiceEntity();
                se._id = dao.getDataCountService() + 1;
                se.nom = "Service Exemple " + i;
                se.description = "Test ";
                se.ville = "Montpellier";
                se.userId = connexion(val.toString(), val.toString()).id;

                dao.insert(se);
            }
        }
        });
    }
}



