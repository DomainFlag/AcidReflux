package com.example.cchiv.acidreflux;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

import org.apmem.tools.layouts.FlowLayout;

/**
 * Created by Cchiv on 03/10/2017.
 */

public class RecipeActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        final FlowLayout flowLayout = (FlowLayout) findViewById(R.id.layout_contents);
        Button button = (Button) findViewById(R.id.click_recipe);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editable_recipe);
                String recipe = editText.getEditableText().toString();

                View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.ingredient, null);
                ((TextView) view.findViewById(R.id.ingredient_name_input)).setText(recipe);
                flowLayout.addView(view);
            }
        });

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                IngredientEntry.COL_ING_ID,
                IngredientEntry.COL_ING_NAME,
                IngredientEntry.COL_ING_ACIDITY
        };
        return new CursorLoader(this, Uri.parse("content://com.example.android.items_reflux/ingredients/all"), projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
