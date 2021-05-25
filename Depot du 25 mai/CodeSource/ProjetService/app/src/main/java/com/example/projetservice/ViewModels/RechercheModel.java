package com.example.projetservice.ViewModels;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.ColumnInfo;

import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.Repository;
import com.example.projetservice.database.ServiceEntity;

public class RechercheModel extends MyViewModel {
    private MutableLiveData<Cursor> cursorService = new MutableLiveData<Cursor>();
    ;


    public void updateCursor() {

        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                Cursor Cursor = referenciel.getCursorService();
                Log.v("1_recherchemodel", "avant");
                if (Cursor != null) {
                    cursorService.postValue(Cursor);
                    Log.v("1_recherchemodel", "apres");
                }
            });
    }

    public void updateCursor(String str) {

        if (referenciel != null)

            AppDataBase.databaseWriteExecutor.execute(() -> {

                Cursor Cursor = referenciel.getCursorService(str);
                Log.v("1_recherchemodel", "avant");
                if (Cursor != null) {
                    cursorService.postValue(Cursor);
                    Log.v("1_recherchemodel", "preès");
                }
            });
    }

    public MutableLiveData<Cursor> getCursorService() {
        if (cursorService.getValue() == null)
            updateCursor();
        return cursorService;

    }


    public void test() {
        referenciel.genereData();
        /*for (int i = 1; i < 30; i++) {

            ServiceEntity se = new ServiceEntity();
            se._id = i;
            se.nom = "Service Exemple " + i;
            se.description = "Test ";
            se.userId = 1;
            if (referenciel != null)
                referenciel.insertService(se);
        }*/
    }


}
