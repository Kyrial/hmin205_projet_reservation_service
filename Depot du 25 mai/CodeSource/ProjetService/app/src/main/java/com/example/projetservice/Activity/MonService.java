package com.example.projetservice.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetservice.R;
import com.example.projetservice.ViewModels.MainActivityModel;
import com.example.projetservice.ViewModels.MonServiceModel;
import com.example.projetservice.database.DateEntity;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MonService extends MainLayoutMenu {


    private ServiceEntity service;
    Activity context = this;
    MonServiceModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_service);

        restaureDataAndInitToolsBarForceUser();
        initNavBar(R.id.itemMonService);


        model = ViewModelProviders.of(this).get(MonServiceModel.class);
        model.setContext(this);
        model.initDatabase();




        final Observer<DateEntity> dateObserver = new Observer<DateEntity>() {
            @Override
            public void onChanged(@Nullable final DateEntity da) {
                CheckBox lundi = findViewById(R.id.lundicheckbox);
                CheckBox mardi = findViewById(R.id.mardicheckbox);
                CheckBox mercredi = findViewById(R.id.mercredicheckbox);
                CheckBox jeudi = findViewById(R.id.jeudicheckbox);
                CheckBox vendredi = findViewById(R.id.vendredicheckbox);
                CheckBox samedi = findViewById(R.id.samedicheckbox);
                CheckBox dimanche = findViewById(R.id.dimanchecheckbox);

                TextView lunditxt = findViewById(R.id.lundiVotreService);
                TextView marditxt = findViewById(R.id.mardiVotreService);
                TextView mercreditxt = findViewById(R.id.mercrediVotreService);
                TextView jeuditxt = findViewById(R.id.jeudiVotreService);
                TextView vendreditxt = findViewById(R.id.vendrediVotreService);
                TextView sameditxt = findViewById(R.id.samediVotreService);
                TextView dimanchetxt = findViewById(R.id.dimancheVotreService);

                if (da != null) {
                    lundi.setChecked(da.lundi);
                    mardi.setChecked(da.mardi);
                    mercredi.setChecked(da.mercredi);
                    jeudi.setChecked(da.jeudi);
                    vendredi.setChecked(da.vendredi);
                    samedi.setChecked(da.samedi);
                    dimanche.setChecked(da.dimanche);

                    if (da.lundi)
                        lunditxt.setText(R.string.disponible);
                    else
                        lunditxt.setText(R.string.NonDisponible);
                    if (da.mardi)
                        marditxt.setText(R.string.disponible);
                    else
                        marditxt.setText(R.string.NonDisponible);
                    if (da.mercredi)
                        mercreditxt.setText(R.string.disponible);
                    else
                        mercreditxt.setText(R.string.NonDisponible);
                    if (da.jeudi)
                        jeuditxt.setText(R.string.disponible);
                    else
                        jeuditxt.setText(R.string.NonDisponible);
                    if (da.vendredi)
                        vendreditxt.setText(R.string.disponible);
                    else
                        vendreditxt.setText(R.string.NonDisponible);
                    if (da.samedi)
                        sameditxt.setText(R.string.disponible);
                    else
                        sameditxt.setText(R.string.NonDisponible);
                    if (da.dimanche)
                        dimanchetxt.setText(R.string.disponible);
                    else
                        dimanchetxt.setText(R.string.NonDisponible);




                }
            }
        };
        model.getDate().observe(this, dateObserver);

        final Observer<ServiceEntity> serviceObserver = new Observer<ServiceEntity>() {
            @Override
            public void onChanged(@Nullable final ServiceEntity serv) {// Update the UI, in thiscase, a TextView.name
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                LinearLayout layoutCreerService = findViewById(R.id.layoutCreerService);
                LinearLayout layoutVotreService = findViewById(R.id.layoutVotreService);
                if (serv == null) {
                    layoutVotreService.setVisibility(View.GONE);
                    layoutCreerService.setVisibility(View.VISIBLE);
                    MaterialButton creerServiceBtn = findViewById(R.id.creerServiceBtn);
                    creerServiceBtn.setText(R.string.creer_le_service);
                } else {
                    service = serv;

                    layoutVotreService.setVisibility(View.VISIBLE);
                    layoutCreerService.setVisibility(View.GONE);
                    model.getDate(service.dateId);
                    remplirTextView();
                }
            }
        };
        model.getService().observe(this, serviceObserver);
        model.recupService(user._id);


//        inscription_button

        MaterialButton creerServiceBtn = findViewById(R.id.creerServiceBtn);
        creerServiceBtn.setOnClickListener(v -> {
            //service:
            TextInputEditText nom = findViewById(R.id.serviceName);
            TextInputEditText desc = findViewById(R.id.serviceDescription);
            TextInputEditText ville = findViewById(R.id.serviceVille);
            TextInputEditText adr = findViewById(R.id.serviceAdresse);
            TextInputEditText tarif = findViewById(R.id.serviceTarif);
            ServiceEntity se = new ServiceEntity();
            se.nom = nom.getText().toString();
            se.description = desc.getText().toString();
            se.ville = ville.getText().toString();
            //se.adresse = adr.getText().toString();
            se.tarif = tarif.getText().toString();
            se.userId = user._id;

            //date
            CheckBox lundi = findViewById(R.id.lundicheckbox);
            CheckBox mardi = findViewById(R.id.mardicheckbox);
            CheckBox mercredi = findViewById(R.id.mercredicheckbox);
            CheckBox jeudi = findViewById(R.id.jeudicheckbox);
            CheckBox vendredi = findViewById(R.id.vendredicheckbox);
            CheckBox samedi = findViewById(R.id.samedicheckbox);
            CheckBox dimanche = findViewById(R.id.dimanchecheckbox);

            DateEntity date = new DateEntity();
            date.lundi = lundi.isChecked();
            date.mardi = mardi.isChecked();
            date.mercredi = mercredi.isChecked();
            date.jeudi = jeudi.isChecked();
            date.vendredi = vendredi.isChecked();
            date.samedi = samedi.isChecked();
            date.dimanche = dimanche.isChecked();
            //date._id =




            model.inscriptionService(user, se, date);
        });


        MaterialButton modifierServiceBtn = findViewById(R.id.modifierServiceBtn);
        LinearLayout layoutCreerService = findViewById(R.id.layoutCreerService);
        LinearLayout layoutVotreService = findViewById(R.id.layoutVotreService);
        modifierServiceBtn.setOnClickListener(v -> {
            layoutVotreService.setVisibility(View.GONE);
            layoutCreerService.setVisibility(View.VISIBLE);

            creerServiceBtn.setText(R.string.btnModifService);
        });
        MaterialButton supprServiceBtn = findViewById(R.id.supprServiceBtn);
        supprServiceBtn.setOnClickListener(v -> {
            model.supprimerService(service);
            service = null;
            //model.supprimerService(service);
        });

    }


    public void remplirTextView(){
        if(service != null) {
            TextView Nom =  findViewById(R.id.NomVotreService);
            TextView desc =  findViewById(R.id.DescrVotreService);
            TextView ville =  findViewById(R.id.villeVotreService);
            TextView tarif =  findViewById(R.id.tarifVotreService);
            TextView adr =  findViewById(R.id.adresseVotreService);
            Nom.setText(service.nom);
            desc.setText(service.description);
            ville.setText(service.ville);
            tarif.setText(service.tarif);
            adr.setText(service.Adresse);
        }
        }
    public void remplirEditText(){
        if(service != null) {
            TextInputEditText Nom =  findViewById(R.id.serviceName);
            TextInputEditText desc =  findViewById(R.id.serviceDescription);
            TextInputEditText ville =  findViewById(R.id.serviceVille);
            TextInputEditText tarif =  findViewById(R.id.serviceAdresse);
            Nom.setHint(service.nom);
            desc.setHint(service.description);
            ville.setHint(service.ville);
            tarif.setHint(service.tarif);
        }
    }

}
