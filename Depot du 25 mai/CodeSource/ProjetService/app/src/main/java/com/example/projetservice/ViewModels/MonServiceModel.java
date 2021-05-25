package com.example.projetservice.ViewModels;

import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetservice.R;
import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.DateEntity;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;

import java.util.Date;

public class MonServiceModel extends MyViewModel {
    private MutableLiveData<ServiceEntity> service =  new MutableLiveData<>();
    private MutableLiveData<DateEntity> date =  new MutableLiveData<>();
    public void recupService(int userId){
        if(referenciel!= null)

            AppDataBase.databaseWriteExecutor.execute(() -> {
                ServiceEntity serv = referenciel.getServiceByUser(userId);
                if (serv == null) { // aucun utilisateur avec ce pseudo et mdp
                    if (context != null) {
                        messageUI.postValue(context.getString(R.string.noservice));


                    }
                    service.postValue(null);
                }
                else {
                    if (context != null)
                    messageUI.postValue(context.getString(R.string.service_existant));

                    service.postValue(serv);
                }
            });
    }

    
    public void inscriptionService(UtilisateurEntity ue , ServiceEntity se){
        if(referenciel!= null) {
            AppDataBase.databaseWriteExecutor.execute(() -> {
                ServiceEntity serv = referenciel.ajoutService(ue, se);
                if (serv != null)
                    service.postValue(serv);
            });
        }
    }

    public void inscriptionService(UtilisateurEntity ue , ServiceEntity se, DateEntity da){
        if(referenciel!= null) {
            AppDataBase.databaseWriteExecutor.execute(() -> {
                ServiceEntity serv = referenciel.ajoutService(ue, se, da);
                if (serv != null)
                    service.postValue(serv);
            });
        }
    }



    public void supprimerService(ServiceEntity se){
        if(referenciel!= null) {
            AppDataBase.databaseWriteExecutor.execute(() -> {
                referenciel.delete(se);
                service.postValue(null);
            });
        }
    }
    public void getDate(int id){
        if(referenciel!= null) {
            AppDataBase.databaseWriteExecutor.execute(() -> {
                DateEntity da =  referenciel.getDateById(id);
                if (da != null)
                    date.postValue(da);
            });

        }
    }
    public MutableLiveData<DateEntity> getDate() {

        return date;

    }





    public MutableLiveData<ServiceEntity> getService() {

        return service;

    }



}
