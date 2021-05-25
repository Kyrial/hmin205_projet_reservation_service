package com.example.projetservice.ViewModels;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetservice.database.Repository;

public abstract class MyViewModel extends ViewModel{
    protected Activity context = null;
    protected Repository referenciel=null;
    protected MutableLiveData<String> messageUI =  new MutableLiveData<>();

    public void setContext(Activity Context){
        context = Context;
    }

    public void initDatabase(){
        if(context != null) {
            referenciel = new Repository(context);
            //referenciel.deletesAllService();
        }
    }

    public MutableLiveData<String> getMessagUI() {
        return messageUI;
    }

}
