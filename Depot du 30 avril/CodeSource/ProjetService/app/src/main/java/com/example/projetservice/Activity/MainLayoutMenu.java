package com.example.projetservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.projetservice.R;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public abstract class MainLayoutMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected UtilisateurEntity user = null;
    private DrawerLayout drawerLayout;
    private NavigationView nav;

    private int name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public void restaureDataAndInitToolsBarForceUser() {
        Intent intent = getIntent();
        UtilisateurEntity User = (UtilisateurEntity) intent.getSerializableExtra("USER");
        if (User != null) {
            user = User;
            InitToolsBar();
        } else {
            Toast.makeText(this, R.string.erreur_donnees_user, Toast.LENGTH_LONG).show();
            finish();
        }

    }


    public void restaureDataAndInitToolsBar() {

        Intent intent = getIntent();
        UtilisateurEntity User = (UtilisateurEntity) intent.getSerializableExtra("USER");
        if (User != null)
            user = User;
        InitToolsBar();
    }


    public void InitToolsBar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        nav = findViewById(R.id.NavigationView);
        Menu menu = nav.getMenu();
        MenuItem itemconnexion = menu.findItem(R.id.itemconnexion);
        MenuItem itemdeconnexion = menu.findItem(R.id.itemDeconnexion);
        MenuItem itemprofil = menu.findItem(R.id.itemprofil);
        if (user != null) {

            itemprofil.setVisible(true);
            itemconnexion.setVisible(false);
            itemdeconnexion.setVisible(true);
            Log.i("1_recherche", "user Recuperer ");

        } else {
            Log.i("1_recherche", "aucune donne récupéré");
            itemconnexion.setVisible(true);
            itemdeconnexion.setVisible(false);
            itemprofil.setVisible(false);

        }
    }

    public void initNavBarCollapsing(int itemName) {
        name = itemName;

        Toolbar topAppBar = findViewById(R.id.toolbar);
        topAppBar.setNavigationOnClickListener(v -> {
            drawerLayout.open();
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.bouton_retour: {
                    finish();
                    break;
                }}
            return false;
        });
        navBar();
    }








    public void initNavBar(int itemName) {
        name = itemName;

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> {
            drawerLayout.open();
        });
        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.bouton_retour: {
                    finish();
                    break;
                }}
            return false;
        });

        navBar();
    }

    public void navBar() {
        nav.setNavigationItemSelectedListener(this);
        Menu menu = nav.getMenu();
        menu.removeItem(name);
        if (user != null) {
            if (user.type.equals("Client")) {
                menu.setGroupVisible(R.id.menuclient, true);
                menu.setGroupVisible(R.id.menufournisseur, false);
            }
            if (user.type.equals("Fournisseur")) {
                menu.setGroupVisible(R.id.menuclient, false);
                menu.setGroupVisible(R.id.menufournisseur, true);
            }

        } else {
            menu.setGroupVisible(R.id.menuclient, false);
            menu.setGroupVisible(R.id.menufournisseur, false);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemconnexion:
            case R.id.itemDeconnexion: {
                finish();
                break;
            }
            case R.id.itemprofil: {
                Intent myIntent = new Intent(this, Profil.class);

                // myIntent.putExtra(User.);
                myIntent.putExtra("USER", user);
                this.startActivity(myIntent);
                break;
            }
            case R.id.itemoption: {
                break;
            }

            case R.id.itemrecherche: {
                Intent myIntent = new Intent(this, Recherche.class);

                // myIntent.putExtra(User.);
                myIntent.putExtra("USER", user);
                this.startActivity(myIntent);
                break;
            }
            case R.id.itemAccueilF: {
                Intent myIntent = new Intent(this, AcceuilFournisseur.class);

                // myIntent.putExtra(User.);
                myIntent.putExtra("USER", user);
                this.startActivity(myIntent);
                break;
            }
            case R.id.itemMonService: {
                Intent myIntent = new Intent(this, MonService.class);

                // myIntent.putExtra(User.);
                myIntent.putExtra("USER", user);
                this.startActivity(myIntent);
                break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
