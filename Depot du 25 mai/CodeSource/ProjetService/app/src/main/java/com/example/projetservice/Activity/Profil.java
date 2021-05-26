package com.example.projetservice.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetservice.CursorAdapteurClientRDV;
import com.example.projetservice.CursorAdapteurRDV;
import com.example.projetservice.R;
import com.example.projetservice.ViewModels.MonServiceModel;
import com.example.projetservice.ViewModels.ProfilModel;
import com.example.projetservice.database.DateEntity;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

public class Profil extends MainLayoutMenu {
    Activity context = this;

    ProfilModel model;
    CursorAdapteurClientRDV AdapterRdv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        CursorAdapteurClientRDV.setContext(this);

        restaureDataAndInitToolsBarForceUser();
        initNavBar(R.id.itemprofil);

        selectView();

        model = ViewModelProviders.of(this).get(ProfilModel.class);
        model.setContext(this);
        model.initDatabase();

        model.setUser(user);
        insertDataView();
        CursorAdapteurClientRDV.setModel(model);
        eventCursor();
    }


    public void selectView() {
        TableLayout layoutmodif = findViewById(R.id.vueModifProfil);
        TableLayout layoutapercu = findViewById(R.id.vueApercuProfil);
        MaterialButton modifier = findViewById(R.id.modifierProfilBtn);
        MaterialButton valider = findViewById(R.id.validerModifProfileBtn);
        MaterialButton annuler = findViewById(R.id.annulerModif);

        modifier.setOnClickListener(v -> {
            layoutapercu.setVisibility(View.GONE);
            layoutmodif.setVisibility(View.VISIBLE);
            modifier.setVisibility(View.GONE);
            valider.setVisibility(View.VISIBLE);
            annuler.setVisibility(View.VISIBLE);
                }

        );
        valider.setOnClickListener(v -> {
            layoutapercu.setVisibility(View.VISIBLE);
            layoutmodif.setVisibility(View.GONE);
            valider.setVisibility(View.GONE);
            modifier.setVisibility(View.VISIBLE);
            annuler.setVisibility(View.GONE);
            recupViewData();
        });
        annuler.setOnClickListener(v -> {
            layoutapercu.setVisibility(View.VISIBLE);
            layoutmodif.setVisibility(View.GONE);
            valider.setVisibility(View.GONE);
            modifier.setVisibility(View.VISIBLE);
            annuler.setVisibility(View.GONE);
        });
    }



    public void insertDataView(){
        final Observer<UtilisateurEntity> userObserver = new Observer<UtilisateurEntity>() {
            @Override
            public void onChanged(@Nullable final UtilisateurEntity ue) {
                insertViewData(ue);



            }
        };
        model.getLiveUser().observe(this, userObserver);
    }



    public void recupViewData(){


        TextInputEditText nom = findViewById(R.id.ModifierNom);
        TextInputEditText prenom = findViewById(R.id.ModifierPrenom);
        TextInputEditText pseudo = findViewById(R.id.ModifierPseudo);
        TextInputEditText email = findViewById(R.id.ModifierEmail);
        TextInputEditText mdp = findViewById(R.id.ModifierMdp);

        UtilisateurEntity eu = new UtilisateurEntity();
        eu.prenom = prenom.getText().toString();
        eu.nom = nom.getText().toString();
        eu.pseudo = pseudo.getText().toString();
        eu.email = email.getText().toString();
        eu.mdp = mdp.getText().toString();


        model.modifUser(eu);
    }

    public void insertViewData(UtilisateurEntity eu){
        if(eu !=null) {
            TextView nom = findViewById(R.id.nomProfil);
            TextView prenom = findViewById(R.id.prenomProfil);
            TextView email = findViewById(R.id.emailProfil);
            TextView pseudo = findViewById(R.id.pseudoProfil);

            if(eu.nom !=null)
                nom.setText(eu.nom);
            if(eu.prenom !=null)
            prenom.setText(eu.prenom);
            if(eu.email !=null)
            email.setText(eu.email);
            if(eu.pseudo !=null)
            pseudo.setText(eu.pseudo);
        }
    }


    public void eventCursor(){
        final Observer<Cursor> CursorObserver = new Observer<Cursor>() {
            @Override
            public void onChanged(@Nullable final Cursor cursorRdv) {// Update the UI, in thiscase, a TextView.name
                if (cursorRdv.moveToFirst() && cursorRdv.getCount() >= 0) {
                    if (AdapterRdv == null) {
                        AdapterRdv = new CursorAdapteurClientRDV(context, cursorRdv);
                        ListView lvItems = (ListView) findViewById(R.id.listrdvclient);
                        lvItems.setAdapter(AdapterRdv);
                        // Log.v("1_acceuilF", "miseajour " + cursorRdv.getString(cursorRdv.getColumnIndexOrThrow("Nom")));
                    } else {
                        AdapterRdv.changeCursor(cursorRdv);
                        //  Log.v("1_acceuilF", "changement de cursor " + cursorRdv.getString(cursorRdv.getColumnIndexOrThrow("Nom")));

                    }
                }
                else{
                    if (AdapterRdv != null)
                        AdapterRdv.changeCursor(null);
                }
            }
        };
        model.getCursorRdv().observe(this, CursorObserver);





    }

}