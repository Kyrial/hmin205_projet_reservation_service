package com.example.projetservice.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projetservice.MyCursorAdapter;
import com.example.projetservice.R;
import com.example.projetservice.SaveUser;
import com.example.projetservice.ViewModels.MainActivityModel;
import com.example.projetservice.ViewModels.RechercheModel;
import com.example.projetservice.database.Repository;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    Activity context = this;
    MainActivityModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Connexion");
        setContentView(R.layout.activity_main);



        model = ViewModelProviders.of(this).get(MainActivityModel.class);
        model.setContext(this);
        model.initDatabase();
        //Repository referenciel = new Repository(this);
        Log.v("1_connexion", "test_1");


        livedataInit();
        boutonSeconnecter_sinscrire();


        LinearLayout layoutConnexion = findViewById(R.id.connexion);
        LinearLayout layoutInscription = findViewById(R.id.inscription);
        MaterialButton inscription = findViewById(R.id.inscription_button);
        MaterialButton connexion = findViewById(R.id.annuler);


        inscription.setOnClickListener(v -> {
            layoutConnexion.setVisibility(View.GONE);
            layoutInscription.setVisibility(View.VISIBLE);
        }

        );
        connexion.setOnClickListener(v -> {
            layoutConnexion.setVisibility(View.VISIBLE);
            layoutInscription.setVisibility(View.GONE);
        });


        //mode inviter
        MaterialButton inviter = findViewById(R.id.inviter_button);
        inviter.setOnClickListener(v -> {
            Intent myIntent = new Intent(MainActivity.this, Recherche.class);
            MainActivity.this.startActivity(myIntent);
        });



    }


    public void livedataInit(){
        final Observer<String> messageUIobserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String message) {// Update the UI, in thiscase, a TextView.name
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        };
        model.getMessagUI().observe(this, messageUIobserver);



        final Observer<UtilisateurEntity> UserObserver = new Observer<UtilisateurEntity>() {
            @Override
            public void onChanged(@Nullable final UtilisateurEntity User) {// Update the UI, in thiscase, a TextView.name
                //Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
                SaveUser.user = User;
                if (User.type.equals("Fournisseur")) {
                    //lancement de l'activity fournisseur
                    Intent myIntent = new Intent(MainActivity.this, AcceuilFournisseur.class);
                    // myIntent.putExtra(User.);
                    myIntent.putExtra("USER", User);
                    MainActivity.this.startActivity(myIntent);

                }
                if (User.type.equals("Client")) {
                    Intent myIntent = new Intent(MainActivity.this, Recherche.class);

                   // myIntent.putExtra(User.);
                    myIntent.putExtra("USER", User);
                    MainActivity.this.startActivity(myIntent);

                }
            }
        };
        model.getUtilisateur().observe(this,UserObserver );
    }


    public void boutonSeconnecter_sinscrire(){
        MaterialButton seConnecter = findViewById(R.id.se_connecter);
        seConnecter.setOnClickListener(v -> {
            TextInputEditText utilisateur = findViewById(R.id.user_edit_text);
            TextInputEditText motdepasse = findViewById(R.id.password_edit_text);
            model.se_connecter(utilisateur.getText().toString(),motdepasse.getText().toString());
        });

        MaterialButton sinscrire = findViewById(R.id.sinscrire);
        sinscrire.setOnClickListener(v -> {
            TextInputEditText utilisateur = findViewById(R.id.utilisateur);
            TextInputEditText motdepasse = findViewById(R.id.password);
            TextInputEditText mail = findViewById(R.id.mail);
            Spinner type = findViewById(R.id.spinnerType);

            model.sinscrire(utilisateur.getText().toString(),motdepasse.getText().toString(), mail.getText().toString(), type.getSelectedItem().toString());
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        SaveUser.user = null;
    }

}
