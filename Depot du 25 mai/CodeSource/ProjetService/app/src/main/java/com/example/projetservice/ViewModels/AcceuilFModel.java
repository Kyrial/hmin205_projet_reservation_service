package com.example.projetservice.ViewModels;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;

public class AcceuilFModel extends  MyViewModel {

    private MutableLiveData<Cursor> cursorRdv = new MutableLiveData<Cursor>();
    private MutableLiveData<ServiceEntity> service = new MutableLiveData<>();
    UtilisateurEntity Fournisseur = null;

    public void setFournisseur(UtilisateurEntity fournisseur) {
        this.Fournisseur = fournisseur;
    }
//////////////////CURSOR
    public void updateCursor() {

        if (referenciel != null &&  service != null && service.getValue() != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                Cursor Cursor = referenciel.rdvFournisseur(service.getValue()._id);
                Log.v("1_recherchemodel", "avant");
                if (Cursor != null) {
                    cursorRdv.postValue(Cursor);
                    Log.v("1_recherchemodel", "apres");
                }
            });
    }
    public MutableLiveData<Cursor> getCursorRdv() {
        if (cursorRdv.getValue() == null)
            updateCursor();
        return cursorRdv;
    }



    //////////////////ServiceEntity
    public void updateservice() {
        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                ServiceEntity serv = referenciel.getServiceByUser(Fournisseur._id);
                Log.v("1_recherchemodel", "avant");
                if (serv != null) {
                    service.postValue(serv);
                    Log.v("1_recherchemodel", "apres");
                }
            });
    }
    public MutableLiveData<ServiceEntity> getService() {
        if (service.getValue() == null)
            updateservice();
        return service;
    }

    public void supprimerRdv(int id){
        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {
                referenciel.supprimerRdv(id);

                updateCursor();
            });

    }

}
