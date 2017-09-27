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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher, LoaderCallbacks<Cursor> {
    ArrayAdapter<String> arrayAdapter;
    LinearLayout linearLayout;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete);
        autoCompleteTextView.addTextChangedListener(this);
        autoCompleteTextView.setAdapter(arrayAdapter);

        getLoaderManager().initLoader(1, null, this);

        linearLayout = (LinearLayout) findViewById(R.id.acidity_levels);
        linearLayout.setTag(-1);

        final TextView textView1 = (TextView) findViewById(R.id.acidic_none);
        final TextView textView2 = (TextView) findViewById(R.id.acidic_true);

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
                }
            }
        });

        final LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout_ingredients);

        Button button;
        final ArrayList<String> arrayListInput = new ArrayList<>();

        button = (Button) findViewById(R.id.submit_ingredient);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ingredientName  = autoCompleteTextView.getEditableText().toString();
                arrayListInput.add(ingredientName);

                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.ingredient, null);
                TextView textView = (TextView) linearLayout.findViewById(R.id.ingredient_name_input);
                textView.setText(ingredientName);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayListInput.remove(ingredientName);
                        linearLayout1.removeView(v);
                    }
                });
                linearLayout1.addView(linearLayout);

                autoCompleteTextView.setText(null);
            }
        });

        button = (Button) findViewById(R.id.submit_ingredients);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ingredientAcidity = (int) linearLayout.getTag();

                if(ingredientAcidity == -1)
                    Toast.makeText(MainActivity.this, "Insert an acidity level", Toast.LENGTH_SHORT).show();
                else {
                    String ingredientName  = autoCompleteTextView.getEditableText().toString();
                    if(!ingredientName.isEmpty())
                        arrayListInput.add(ingredientName);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(IngredientEntry.COL_ING_NAME, (new JSONArray(arrayListInput)).toString());
                    contentValues.put(IngredientEntry.COL_ING_ACIDITY, ingredientAcidity);
                    getContentResolver().insert(Uri.parse("content://com.example.android.items_reflux/ingredients"), contentValues);

                    autoCompleteTextView.setText(null);
                    arrayListInput.clear();

                    Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_item : {
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
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
        return new CursorLoader(this, Uri.parse("content://com.example.android.items_reflux/ingredients/names"), projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        arrayAdapter.clear();
        int indexName = data.getColumnIndex(IngredientEntry.COL_ING_NAME);
        while(data.moveToNext()) {
            try {
                JSONArray jsonArray = new JSONArray(data.getString(indexName));
                for(int i = 0; i < jsonArray.length(); i++) {
                    arrayAdapter.add(jsonArray.getString(i));
                }
            } catch (JSONException j) {
                Log.v("JSON", j.toString());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        arrayAdapter.clear();
    }
}
