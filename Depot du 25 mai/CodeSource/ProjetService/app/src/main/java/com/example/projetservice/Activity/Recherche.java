package com.example.projetservice.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


import com.example.projetservice.MyCursorAdapter;
import com.example.projetservice.R;
import com.example.projetservice.ViewModels.RechercheModel;
import com.example.projetservice.database.RdvEntity;
import com.example.projetservice.database.UtilisateurEntity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Recherche extends MainLayoutMenu {
    Activity context = this;
    MyCursorAdapter myAdapter = null;
    UtilisateurEntity user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        MyCursorAdapter.setContext(this);

        restaureDataAndInitToolsBar();

        initNavBarCollapsing(R.id.itemrecherche);

        //viewModel
        RechercheModel model = ViewModelProviders.of(this).get(RechercheModel.class);
        model.setContext(this);
        model.initDatabase();
        model.test();
        final Observer<Cursor> CursorObserver = new Observer<Cursor>() {
            @Override
            public void onChanged(@Nullable final Cursor cursorService) {// Update the UI, in thiscase, a TextView.name
                if (cursorService.moveToFirst() && cursorService.getCount() >= 1) {
                    if (myAdapter == null) {
                        myAdapter = new MyCursorAdapter(context, cursorService);
                        ListView lvItems = (ListView) findViewById(R.id.listService);
                        lvItems.setAdapter(myAdapter);
                        Log.v("1_recherche", "miseajour " + cursorService.getString(cursorService.getColumnIndexOrThrow("Nom")));
                    } else {
                        myAdapter.changeCursor(cursorService);
                        Log.v("1_recherche", "changement de cursor " + cursorService.getString(cursorService.getColumnIndexOrThrow("Nom")));

                    }
                }
            }
        };
        model.getCursorService().observe(this, CursorObserver);


        //effectue une recherche
        MaterialButton rechercher = findViewById(R.id.rechercher);
        rechercher.setOnClickListener(v -> {
            TextInputEditText recherche = findViewById(R.id.textRecherche);
            String str = recherche.getText().toString();
            model.updateCursor(str);
        });


      /*  ListView lstV = findViewById(R.id.listService);

        lstV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(), "miaou",
                        Toast.LENGTH_SHORT).show();
            }
        });*/

    }
    public void launchService(int id){
        Intent myIntent = new Intent(Recherche.this, RdvEntity.class);
        // myIntent.putExtra(User.);
        myIntent.putExtra("SERVICE_ID", id);
        Recherche.this.startActivity(myIntent);

    }


}






