package com.example.projetservice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetservice.CursorAdapteurRDV;
import com.example.projetservice.MyCursorAdapter;
import com.example.projetservice.R;
import com.example.projetservice.ViewModels.AcceuilFModel;
import com.example.projetservice.ViewModels.RechercheModel;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class AcceuilFournisseur extends MainLayoutMenu {
    Activity context = this;

    CursorAdapteurRDV AdapterRdv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil_fournisseur);

        CursorAdapteurRDV.setContext(this);

        restaureDataAndInitToolsBarForceUser();
        initNavBarCollapsing(R.id.itemAccueilF);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(" Dodo Service ");
        AcceuilFModel model = ViewModelProviders.of(this).get(AcceuilFModel.class);
        model.setContext(this);
        model.initDatabase();
        model.setFournisseur(user);
        CursorAdapteurRDV.setModel(model);
        MaterialButton monService = findViewById(R.id.MonServicebtn);
        monService.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, MonService.class);

            // myIntent.putExtra(User.);
            myIntent.putExtra("USER", user);
            this.startActivity(myIntent);
        });


        final Observer<Cursor> CursorObserver = new Observer<Cursor>() {
            @Override
            public void onChanged(@Nullable final Cursor cursorRdv) {// Update the UI, in thiscase, a TextView.name
                if (cursorRdv.moveToFirst() && cursorRdv.getCount() >= 0) {
                    if (AdapterRdv == null) {
                        AdapterRdv = new CursorAdapteurRDV(context, cursorRdv);
                        ListView lvItems = (ListView) findViewById(R.id.listrdvFournisseur);
                        lvItems.setAdapter(AdapterRdv);
                       // Log.v("1_acceuilF", "miseajour " + cursorRdv.getString(cursorRdv.getColumnIndexOrThrow("Nom")));
                    } else {
                        AdapterRdv.changeCursor(cursorRdv);
                      //  Log.v("1_acceuilF", "changement de cursor " + cursorRdv.getString(cursorRdv.getColumnIndexOrThrow("Nom")));

                    }
                }
                else {
                    if (AdapterRdv != null)
                        AdapterRdv.changeCursor(null);
                }
            }
        };
        model.getCursorRdv().observe(this, CursorObserver);

        final Observer<ServiceEntity> serviceObserver = new Observer<ServiceEntity>() {
            @Override
            public void onChanged(@Nullable final ServiceEntity service) {// Update the UI, in thiscase, a TextView.name
                if(service != null){
                    model.updateCursor();
                }
            }
        };
        model.getService().observe(this, serviceObserver);

    }



}