package com.example.projetservice.database;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

//Gère les requetes envoyé a la BDD
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
    public void insertDate(DateEntity date) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(date);
        });
    }

    public void insertService(ServiceEntity service) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            dao.insert(service);
        });
    }

    public void modifUser(UtilisateurEntity ue, UtilisateurEntity newUser){


        if (ue.nom != null && !ue.nom.equals(""))
            newUser.nom = ue.nom;
        if (ue.prenom != null && !ue.prenom.equals(""))
            newUser.prenom = ue.prenom;
        if (ue.pseudo != null && !ue.pseudo.equals(""))
            newUser.pseudo = ue.pseudo;
        if (ue.email != null && !ue.email.equals(""))
            newUser.email = ue.email;
        if (ue.mdp != null && !ue.mdp.equals(""))
            newUser.mdp = ue.mdp;

        dao.update(newUser);
    }

    public UtilisateurEntity connexion(String identifiant, String motdepasse) {
        return dao.seConnecter(identifiant, motdepasse);
    }

    public UtilisateurEntity getUser(String identifiant) {
        return dao.getUser(identifiant);
    }


    public DateEntity getDateById(int id) {
        return dao.getDatebyId(id);
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

            //newUser._id = dao.getDataCountUser() + 1;
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


    public ServiceEntity getServiceById(Integer id) {
        return dao.getService(id);
    }

    public ServiceEntity getServiceByUser(Integer userId) {
        return dao.getServiceByUser(userId);
    }

    public DateEntity getDateByAttribute( DateEntity da) {
        return dao.getDateByAttribute(da.lundi, da.mardi, da.mercredi, da.jeudi,
                da.vendredi, da.samedi, da.dimanche);
    }


    public ServiceEntity ajoutService(UtilisateurEntity ue, ServiceEntity se) {
        ServiceEntity alreadyService = getServiceByUser(ue._id);
        if (alreadyService == null) {
            //se._id = dao.getDataCountService() + 1;
            insertService(se);

            return se;
        } else
            return updateService(se, alreadyService);
    }

    public ServiceEntity ajoutService(UtilisateurEntity ue, ServiceEntity se, DateEntity da) {
        ServiceEntity alreadyService = getServiceByUser(ue._id);
        if (alreadyService == null) {
            //se._id = dao.getDataCountService() + 1;

            if(getDateByAttribute(da) == null){
                //da._id = dao.getDataCountDate() + 1;
                dao.insert(da);
            }
            da = getDateByAttribute(da);


            se.dateId= da._id;

            insertService(se);

            return se;
        } else
            return updateService(se, alreadyService, da);
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

      /*  if (newS.dateId != null  )
            lastS.dateId = newS.dateId;*/

        dao.update(lastS);
        return lastS;
    }

    public ServiceEntity updateService(ServiceEntity newS, ServiceEntity lastS, DateEntity da) {

        if(getDateByAttribute(da) == null){
            dao.insert(da);
        }
        da = getDateByAttribute(da);
        lastS.dateId= da._id;

        ServiceEntity serv = updateService(newS, lastS);
        return serv;
    }

    public UtilisateurEntity getUserById(int id){
        return dao.getUserbyId(id);
    }


    public void delete(ServiceEntity se) {
        dao.delete(se);
    }

    public Cursor rdvFournisseur(int sid){

        Calendar c = Calendar.getInstance();
        int dateNow = (int)(c.getTimeInMillis()/1000); //pour etre en seconde

        ArrayList<String> rdvList= new ArrayList<>();
        Cursor rdvCursor = dao.getAllRdv(sid, dateNow);
        return rdvCursor;
    }
    public Cursor rdvClient(int cid){

        Calendar c = Calendar.getInstance();
        int dateNow = (int)(c.getTimeInMillis()/1000); //pour etre en seconde

        ArrayList<String> rdvList= new ArrayList<>();
        Cursor rdvCursor = dao.getRdvClient(cid, dateNow);
        return rdvCursor;
    }



    public ArrayList<Boolean> getDisponibilite(int did, int sid){
        //récupère les disponibilités de l'utilisateur
        DateEntity date = dao.getDatebyId(did);


        ArrayList<Boolean>disponible = new ArrayList<>(Arrays.asList(date.lundi,date.mardi, date.mercredi, date.jeudi, date.vendredi, date.samedi, date.dimanche));



        Calendar c = Calendar.getInstance();
        int dateNow = (int)(c.getTimeInMillis()/1000); //pour etre en seconde

        ArrayList<String> rdvList= new ArrayList<>();
        Cursor rdvCursor = dao.getAllRdv(sid, dateNow);


        for(rdvCursor.moveToFirst(); !rdvCursor.isAfterLast(); rdvCursor.moveToNext()) {
            // The Cursor is now set to the right position
            rdvList.add(rdvCursor.getString(rdvCursor.getColumnIndexOrThrow("jour")));
        }
        for(String rdv : rdvList){
            switch (rdv){
                case "lundi": {
                    disponible.set(0, false);
                    break;
                }
                case "mardi": {
                    disponible.set(1, false);
                    break;
                }
                case "mercredi": {
                    disponible.set(2, false);
                    break;
                }
                case "jeudi": {
                    disponible.set(3, false);
                    break;
                }
                case "vendredi": {
                    disponible.set(4, false);
                    break;
                }
                case "samedi": {
                    disponible.set(5, false);
                    break;
                }
                case "dimanche": {
                    disponible.set(6, false);
                    break;
                }
            }
        }
    return disponible;
    }



    public void creerRdv(int sid, int cid, String jour){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);

        RdvEntity rdv = new RdvEntity();
        rdv.jour = jour;
        rdv.sId = sid;
        rdv.cId = cid;
        rdv.dates= (int)(c.getTimeInMillis()/1000); //pour etre en seconde
        dao.insertRdv(rdv);

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
                //newUser._id = dao.getDataCountUser() + 1;

                dao.insert(newUser);
            }

            for (int i = 1; i < 30; i++) {
                Integer val = (i * 1234);
                ServiceEntity se = new ServiceEntity();
                //se._id = dao.getDataCountService() + 1;
                //se._id =0;
                se.nom = "Service Exemple " + i;
                se.description = "description exemple";
                se.ville = "Montpellier";

                se.userId = connexion(val.toString(), val.toString())._id;


                Random rd = new Random();
                DateEntity da = new DateEntity();
                da.lundi = rd.nextBoolean();
                da.mardi = rd.nextBoolean();
                da.mercredi = rd.nextBoolean();
                da.jeudi = rd.nextBoolean();
                da.vendredi = rd.nextBoolean();
                da.samedi = rd.nextBoolean();
                da.dimanche = rd.nextBoolean();

                if(getDateByAttribute(da) == null){
                    //da._id = dao.getDataCountDate() + 1;
                    dao.insert(da);
                }
                da = getDateByAttribute(da);

                se.dateId= da._id;

                dao.insert(se);
            }
        }
        });
    }
    public void supprimerRdv(int id){
        RdvEntity rdv= dao.getRdvById(id);
        if (rdv != null)
            dao.delete(rdv);
    };



}



