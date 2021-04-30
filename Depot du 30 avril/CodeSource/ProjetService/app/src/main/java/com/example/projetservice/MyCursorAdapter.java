package com.example.projetservice;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }
    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }

// The bindView method is used to bind all data to a given view
// such as setting the text on a TextView
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
// Find fields to populate in inflated template
        if(cursor.getCount() >=1) {
            TextView ns = (TextView) view.findViewById(R.id.nomService);
            TextView ds = (TextView) view.findViewById(R.id.descriptionService);
            TextView vs = (TextView) view.findViewById(R.id.villeService);
            // Extract properties from cursor
            String nom = cursor.getString(cursor.getColumnIndexOrThrow("Nom"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
            String ville = cursor.getString(cursor.getColumnIndexOrThrow("Ville"));
            // Populate fields with extracted properties
            ns.setText(nom);
            ds.setText(String.valueOf(description));
            if(ville == null || ville.equals(""))
                vs.setText("");
            else vs.setText(ville);
        }
    }
}
