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

public class CursorAdapteurRDV extends CursorAdapter {
    static Context context= null;
    static public void setContext(Context ctx){
        context = ctx;
    }
    static AcceuilFModel model;

    public static void setModel(AcceuilFModel model) {
        CursorAdapteurRDV.model = model;
    }

    public CursorAdapteurRDV(Context context, Cursor c) {
        super(context, c,0);
    }
    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row_rdv, parent, false);
    }

    // The bindView method is used to bind all data to a given view
// such as setting the text on a TextView
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
// Find fields to populate in inflated template
        if(cursor.getCount() >=1) {
            TextView ns = (TextView) view.findViewById(R.id.NomClientRdv);
            TextView ds = (TextView) view.findViewById(R.id.DateRdv);
            TextView np = (TextView) view.findViewById(R.id.nomPrenomRdv);
            // Extract properties from cursor
            String pseudo = cursor.getString(cursor.getColumnIndexOrThrow("pseudo"));
            String dates = cursor.getString(cursor.getColumnIndexOrThrow("jour"));
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));


            // Populate fields with extracted properties
            ns.setText(String.valueOf(pseudo));
            ds.setText(String.valueOf(dates));
            if(nom == null )
                nom = "";
            if(prenom == null)
                prenom ="";
            np.setText(nom+" "+prenom);


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
                            });
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
