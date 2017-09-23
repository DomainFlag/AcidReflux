package com.example.cchiv.acidreflux;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher, LoaderCallbacks<Cursor>{


    CursorAdapter cursorAdapter;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete);
        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setAdapter(arrayAdapter);

        getLoaderManager().initLoader(1, null, this);

        Button button = (Button) findViewById(R.id.submit_ingredient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName  = autoCompleteTextView.getEditableText().toString();

                ContentValues contentValues = new ContentValues();
                contentValues.put(IngredientEntry.COL_ING_NAME, ingredientName);
                contentValues.put(IngredientEntry.COL_ING_ACIDITY, 2);
                getContentResolver().insert(Uri.parse("content://com.example.android.items/ingredients"), contentValues);

                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                IngredientEntry.COL_ING_NAME
        };
        return new CursorLoader(this, Uri.parse("content://com.example.android.items/ingredients"), projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        arrayAdapter.clear();
        int indexName = data.getColumnIndex(IngredientEntry.COL_ING_NAME);
        while(data.moveToNext()) {
            arrayAdapter.add(data.getString(indexName));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        arrayAdapter.clear();
    }
}
