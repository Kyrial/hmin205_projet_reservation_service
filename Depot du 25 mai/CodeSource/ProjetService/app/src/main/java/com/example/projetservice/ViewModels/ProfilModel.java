package com.example.projetservice.ViewModels;

import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.projetservice.R;
import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;

public class ProfilModel extends MyViewModel {
    private MutableLiveData<UtilisateurEntity> user =  new MutableLiveData<>();
    private MutableLiveData<Cursor> cursorRdv = new MutableLiveData<Cursor>();
    public void setUser(UtilisateurEntity user) {
        this.user.setValue(user);
    }

    public void recupUser(){
        if(referenciel!= null)

            AppDataBase.databaseWriteExecutor.execute(() -> {
                UtilisateurEntity ue = referenciel.getUserById(user.getValue()._id);
                if (ue == null) { // aucun utilisateur avec cet id

                    user.postValue(null);
                }
                else {
                   /* if (context != null)
                        messageUI.postValue(context.getString());*/

                    user.postValue(ue);
                }
            });
    }

    public void modifUser(UtilisateurEntity ue){
        if(referenciel!= null)
            AppDataBase.databaseWriteExecutor.execute(() -> {
                referenciel.modifUser(ue, user.getValue());
                recupUser();
            });
    }

    public MutableLiveData<UtilisateurEntity> getLiveUser() {
        return user;
    }



    public void updateCursor() {

        if (referenciel != null &&  user != null && user.getValue() != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                Cursor Cursor = referenciel.rdvClient(user.getValue()._id);
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

    public void supprimerRdv(int id){
        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {
                referenciel.supprimerRdv(id);

                updateCursor();
            });

    }
}
