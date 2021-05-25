package com.example.projetservice.ViewModels;


import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;

import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;

import java.util.ArrayList;

public class ServiceViewClientModel extends MyViewModel {

    private MutableLiveData<ArrayList<Boolean>> disponibilite = new MutableLiveData<>();
    private MutableLiveData<UtilisateurEntity> fournisseur = new MutableLiveData<>();
    private MutableLiveData<ServiceEntity> service = new MutableLiveData<>();
    int fId = 0;
    int dateId = 0;
    int sid = 0;

    public void setsId(int sId) {
        this.sid = sId;
    }


    public void getDisponibilite(int dateid) {

        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                ArrayList<Boolean> mb = referenciel.getDisponibilite(dateid, sid);
                if (mb != null)
                    disponibilite.postValue(mb);
                    /*for( Boolean b : mb){

                    }*/

            });
    }

    public MutableLiveData<UtilisateurEntity> getLivefournisseur() {

        return fournisseur;
    }

    public MutableLiveData<ArrayList<Boolean>> getLiveDisponibilite() {
        /*if (disponibilite.getValue() == null)
            getDisponibilite(dateId);*/
        return disponibilite;

    }

    public MutableLiveData<ServiceEntity> getLiveService() {
        if (service.getValue() == null)
            getService();
        return service;

    }


    public void getfournisseur(int fid) {
        UtilisateurEntity user = referenciel.getUserById(fid);
        if (user != null) {
            fournisseur.postValue(user);
        }

    }

    public void getService() {
        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {
                ServiceEntity serv = referenciel.getServiceById(sid);
                if (serv != null) {
                    sid = serv._id;
                    dateId = serv.dateId;

                    getfournisseur(serv.userId);

                    service.postValue(serv);

                    //if(dateId != null)
                    getDisponibilite(dateId);
                }

            });
    }

    public void creerRdv(int sid, int cid, String jour){
        if (referenciel != null){

            AppDataBase.databaseWriteExecutor.execute(() -> {

                referenciel.creerRdv( sid,  cid,  jour);


            });

        }
    }
}





