package com.example.cchiv.acidreflux;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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

    final private static int redHex = Integer.parseInt("EC1C4B", 16);
    final private static int blueHex = Integer.parseInt("2F395", 16);

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
        double acidity = cursor.getDouble(acidityID);
        if(Double.isNaN(acidity)) {
            ((GradientDrawable)textView.getBackground()).setColor(Color.parseColor("#83AE9B"));
            textView.setText("N");

        } else {
            int colorHex = (int) (blueHex + (redHex-blueHex)/2*(acidity+1.0));
            ((GradientDrawable)textView.getBackground()).setColor(Color.parseColor("#"+Integer.toHexString(colorHex)));
            textView.setText(String.format("%.01f", cursor.getDouble(acidityID)));
        }
    }
}
