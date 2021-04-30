package com.example.projetservice.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projetservice.R;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AcceuilFournisseur extends MainLayoutMenu {

    private UtilisateurEntity user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_fournisseur);


        restaureDataAndInitToolsBarForceUser();
        initNavBarCollapsing(R.id.itemAccueilF);
    }



}