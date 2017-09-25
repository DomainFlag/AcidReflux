package com.example.cchiv.acidreflux;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher, LoaderCallbacks<Cursor> {
    ArrayAdapter<String> arrayAdapter;
    LinearLayout linearLayout;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete);
        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setAdapter(arrayAdapter);

        getLoaderManager().initLoader(1, null, this);

        linearLayout = (LinearLayout) findViewById(R.id.acidity_levels);
        linearLayout.setTag(-1);

        final TextView textView1 = (TextView) findViewById(R.id.acidic_none);
        final TextView textView2 = (TextView) findViewById(R.id.acidic_low);
        final TextView textView3 = (TextView) findViewById(R.id.acidic_high);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int) linearLayout.getTag() == 0) {
                    linearLayout.setTag(-1);
                    v.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    linearLayout.setTag(0);
                    v.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.accent));
                    textView2.setBackgroundColor(Color.TRANSPARENT);
                    textView3.setBackgroundColor(Color.TRANSPARENT);
                }

            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int) linearLayout.getTag() == 1) {
                    linearLayout.setTag(-1);
                    v.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    linearLayout.setTag(1);
                    v.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.accent));
                    textView1.setBackgroundColor(Color.TRANSPARENT);
                    textView3.setBackgroundColor(Color.TRANSPARENT);
                }
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((int) linearLayout.getTag() == 2) {
                    linearLayout.setTag(-1);
                    v.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    linearLayout.setTag(2);
                    v.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.accent));
                    textView1.setBackgroundColor(Color.TRANSPARENT);
                    textView2.setBackgroundColor(Color.TRANSPARENT);
                }

            }
        });

        Button button = (Button) findViewById(R.id.submit_ingredient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ingredientName  = autoCompleteTextView.getEditableText().toString();
                int ingredientAcidity = (int) linearLayout.getTag();

                ContentValues contentValues = new ContentValues();
                contentValues.put(IngredientEntry.COL_ING_NAME, ingredientName);
                contentValues.put(IngredientEntry.COL_ING_ACIDITY, ingredientAcidity);
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
