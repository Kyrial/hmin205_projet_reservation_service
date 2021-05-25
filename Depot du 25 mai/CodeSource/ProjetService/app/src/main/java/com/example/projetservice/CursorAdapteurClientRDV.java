package com.example.projetservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.projetservice.Activity.ServiceViewClient;
import com.example.projetservice.ViewModels.AcceuilFModel;
import com.example.projetservice.ViewModels.ProfilModel;

public class CursorAdapteurClientRDV extends CursorAdapter {
    static Context context = null;

    public CursorAdapteurClientRDV(Context context, Cursor c) {
        super(context, c, 0);
    }
    static ProfilModel model;
    static public void setContext(Context ctx) {
        context = ctx;
    }
    public static void setModel( ProfilModel model) {
        CursorAdapteurClientRDV.model = model;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_rdv, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
// Find fields to populate in inflated template
        if(cursor.getCount() >=1) {
            TextView ns = (TextView) view.findViewById(R.id.NomClientRdv);
            TextView ds = (TextView) view.findViewById(R.id.DateRdv);
            TextView np = (TextView) view.findViewById(R.id.nomPrenomRdv);
            // Extract properties from cursor
            String servNom = cursor.getString(cursor.getColumnIndexOrThrow("servName"));
            String pseudo = cursor.getString(cursor.getColumnIndexOrThrow("pseudo"));
            String dates = cursor.getString(cursor.getColumnIndexOrThrow("jour"));
            String userNom = cursor.getString(cursor.getColumnIndexOrThrow("userName"));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));


            // Populate fields with extracted properties
            if(servNom == null )
                servNom = "";
            ns.setText(String.valueOf(servNom));
            ds.setText(String.valueOf(dates));
            if(pseudo != null && !pseudo.equals("")){
                pseudo= pseudo+"     ";
            }

            if(userNom == null )
                userNom = "";
            if(prenom == null)
                prenom ="";
            np.setText(userNom+" "+prenom);


            LinearLayout ll = view.findViewById(R.id.layoutCursorRDV);
            ll.setTag(cursor.getInt(cursor.getColumnIndex("_id")));

            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("row clicked", "the id: "+v.getTag());


                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    //set title
                    alertDialogBuilder.setTitle("Annuler la Reservation");
                    alertDialogBuilder
                            .setMessage("Confirmer la suppression du rendez vous")
                            .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    model.supprimerRdv((Integer)v.getTag());
                                }
                            })
                            .setNegativeButton(R.string.retour, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(context, "Annuler ", Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            })
                            .setNeutralButton("Aller au Service", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.v("row clicked", "the id: " + v.getTag());
                                    Intent myIntent = new Intent(context, ServiceViewClient.class);
                                    // myIntent.putExtra(User.);
                                    myIntent.putExtra("SERVICE_ID", (Integer) v.getTag());
                                    context.startActivity(myIntent);
                                }
                            } );
                    //create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    //show it
                    alertDialog.show();


                }
            });
            if(cursor.getPosition()%2==0)
                ll.setBackgroundColor(ContextCompat.getColor(context,R.color.purple_50));
            else
                ll.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }
    }

}
