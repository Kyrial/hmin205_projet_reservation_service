package com.example.projetservice.ViewModels;

import android.app.Activity;
import android.database.Cursor;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetservice.R;
import com.example.projetservice.database.AppDataBase;
import com.example.projetservice.database.Repository;
import com.example.projetservice.database.UtilisateurEntity;

public class MainActivityModel extends MyViewModel {
        private MutableLiveData<UtilisateurEntity> utilisateur =  new MutableLiveData<>();




        public void se_connecter(String identitfiant, String motdepasse){
            if(referenciel!= null)

                AppDataBase.databaseWriteExecutor.execute(() -> {
                    UtilisateurEntity user = referenciel.connexion(identitfiant,motdepasse);
                    if (user == null) { // aucun utilisateur avec ce pseudo et mdp
                        if (context != null)
                            messageUI.postValue(context.getString(R.string.false_user));
                    }
                    else {
                            messageUI.postValue(context.getString(R.string.goodUser));
                            utilisateur.postValue(user);
                        }
                });
        }

        public void sinscrire(String identitfiant, String motdepasse, String mail, String type){
            if(referenciel!= null)
                AppDataBase.databaseWriteExecutor.execute(() -> {
                    UtilisateurEntity user = referenciel.inscription(identitfiant,motdepasse,mail, type);

                    if (user == null) { // aucun utilisateur avec ce pseudo et mdp
                        if (context != null)
                            messageUI.postValue(context.getString(R.string.alreadyExist));
                    }
                    else {
                        //user.type;
                        messageUI.postValue(context.getString(R.string.inscriptionValide)+" "+user.type);
                        utilisateur.postValue(user);
                    }
                });
        }





    public MutableLiveData<UtilisateurEntity> getUtilisateur() {
        return utilisateur;
    }

}
