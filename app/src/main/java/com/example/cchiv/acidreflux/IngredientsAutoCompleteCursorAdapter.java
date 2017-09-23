package com.example.cchiv.acidreflux;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

/**
 * Created by Cchiv on 23/09/2017.
 */

public class IngredientsAutoCompleteCursorAdapter extends CursorAdapter {

    public IngredientsAutoCompleteCursorAdapter(Context context) {
        super(context, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int indexName = cursor.getColumnIndex(IngredientEntry.COL_ING_NAME);
        String textName = cursor.getString(indexName);

        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        Log.v("TextView", textName);
        textView.setText(textName);
    }
}
