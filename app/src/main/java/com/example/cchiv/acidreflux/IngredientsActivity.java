package com.example.cchiv.acidreflux;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

public class IngredientsActivity extends AppCompatActivity implements LoaderCallbacks<Cursor>{

    IngredientsCursorAdapter ingredientsCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ingredientsCursorAdapter = new IngredientsCursorAdapter(this);

        ListView listView = (ListView) findViewById(R.id.list_items);
        listView.setAdapter(ingredientsCursorAdapter);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_ingredients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_back : {
                finish();
                break;
            }
            case R.id.menu_delete_item : {
                break;
            }
            case R.id.menu_save_item : {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                IngredientEntry._ID,
                IngredientEntry.COL_ING_NAME,
                IngredientEntry.COL_ING_ACIDITY
        };
        return new CursorLoader(this, Uri.parse("content://com.example.android.items_reflux/ingredients/acidity"), projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ingredientsCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ingredientsCursorAdapter.swapCursor(null);
    }
}
