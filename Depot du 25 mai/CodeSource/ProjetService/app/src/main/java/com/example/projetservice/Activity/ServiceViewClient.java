package com.example.projetservice.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetservice.MyCursorAdapter;
import com.example.projetservice.R;
import com.example.projetservice.SaveUser;
import com.example.projetservice.ViewModels.RechercheModel;
import com.example.projetservice.ViewModels.ServiceViewClientModel;
import com.example.projetservice.database.ServiceEntity;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceViewClient extends MainLayoutMenu {

    int sid = 0;
    ServiceViewClientModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_view_client);

        Intent intent = getIntent();
        Integer sid = (Integer) intent.getSerializableExtra("SERVICE_ID");
        if (sid != null)
            this.sid = sid;
        else
            finish();

        user = SaveUser.user;
        InitToolsBar();



        // //restaureDataAndInitToolsBarForceUser();
        initNavBar(R.id.itemconnexion);

        model = ViewModelProviders.of(this).get(ServiceViewClientModel.class);
        model.setContext(this);
        model.initDatabase();
        model.setsId(sid);


        ArrayList<MaterialButton> mb = new ArrayList<>(Arrays.asList(
                ((MaterialButton) findViewById(R.id.lundiReservation)),
                ((MaterialButton) findViewById(R.id.mardiReservation)),
                ((MaterialButton) findViewById(R.id.mercrediReservation)),
                ((MaterialButton) findViewById(R.id.jeudiReservation)),
                ((MaterialButton) findViewById(R.id.vendrediReservation)),
                ((MaterialButton) findViewById(R.id.samediReservation)),
                ((MaterialButton) findViewById(R.id.dimancheReservation))));
        eventOnClick(mb);

        final Observer<ArrayList<Boolean>> disponibiliteObserver = new Observer<ArrayList<Boolean>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Boolean> dispList) {// Update the UI, in thiscase, a TextView.name
                for (int i = 0; i < dispList.size(); i++) {
                    mb.get(i).setEnabled(dispList.get(i));

                }
            }
        };
        model.getLiveDisponibilite().observe(this, disponibiliteObserver);

        final Observer<UtilisateurEntity> fournisseurObserver = new Observer<UtilisateurEntity>() {
            @Override
            public void onChanged(@Nullable final UtilisateurEntity fournisseur) {// Update the UI, in thiscase, a TextView.name

                TextView tv = findViewById(R.id.auteurServiceClient);
                //     tv.setText(fournisseur.nom+" "+fournisseur.prenom);
                if (fournisseur.prenom == null && fournisseur.prenom == null)
                    tv.setText(fournisseur.pseudo);
                else {
                    if (fournisseur.prenom == null)
                        tv.setText(fournisseur.nom);
                    else {
                        if (fournisseur.nom == null)
                            tv.setText(fournisseur.prenom);
                        else
                            tv.setText(fournisseur.nom + fournisseur.prenom);
                    }
                }
            }
        };

        model.getLivefournisseur().observe(this, fournisseurObserver);


        final Observer<ServiceEntity> serviceObserver = new Observer<ServiceEntity>() {
            @Override
            public void onChanged(@Nullable final ServiceEntity service) {
                TextView tserv = findViewById(R.id.nomServiceClient);
                tserv.setText(service.nom);

                TextView tville = findViewById(R.id.villeServiceClient);
                tville.setText(service.ville);

                TextView tdesc = findViewById(R.id.descServiceClient);
                tdesc.setText(service.description);
            }
        };
        model.getLiveService().observe(this, serviceObserver);

    }


    public void eventOnClick(ArrayList<MaterialButton> mbList) {
        for (MaterialButton mb : mbList) {
            mb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    TextView tv = (TextView) arg0;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ServiceViewClient.this);

                    //set title
                    alertDialogBuilder.setTitle("Confirmation de Reservation");
                    alertDialogBuilder
                            .setMessage("Confirmez la reservation du" + tv.getText())
                            .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (SaveUser.user != null) {
                                        model.creerRdv(sid, SaveUser.user._id, tv.getText().toString());
                                        //Requete au serveur
                                        model.getDisponibilite(model.getLiveService().getValue().dateId);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "vous n'etes pas connecté ", Toast.LENGTH_LONG).show();
                                        dialog.cancel();
                                    }
                                }
                            })
                            .setNegativeButton(R.string.retour, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(getApplicationContext(), "reservation annulé ", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            });
                    //create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    //show it
                    alertDialog.show();
                }
            });

        }
    }
}





