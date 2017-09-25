package com.example.cchiv.acidreflux;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

/**
 * Created by Cchiv on 20/09/2017.
 */

public class IngredientsCursorAdapter extends CursorAdapter {

    public IngredientsCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.layout_ingredient, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int nameID = cursor.getColumnIndex(IngredientEntry.COL_ING_NAME);
        int acidityID = cursor.getColumnIndex(IngredientEntry.COL_ING_ACIDITY);

        TextView textView;

        textView = (TextView) view.findViewById(R.id.ingredient_name);
        textView.setText(cursor.getString(nameID));
        textView = (TextView) view.findViewById(R.id.ingredient_acidity);
        textView.setText(String.valueOf(cursor.getDouble(acidityID)));
    }
}
