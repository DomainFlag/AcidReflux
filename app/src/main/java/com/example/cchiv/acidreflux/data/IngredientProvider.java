package com.example.cchiv.acidreflux.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Cchiv on 22/09/2017.
 */

public class IngredientProvider extends ContentProvider{

    IngredientDbHelper ingredientDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ACID = 1;
    private static final int NAMES = 0;
    static {
        sUriMatcher.addURI("com.example.android.items_reflux", "ingredients/acidity", ACID);
        sUriMatcher.addURI("com.example.android.items_reflux", "ingredients/names", NAMES);
    }

    @Override
    public boolean onCreate() {
        ingredientDbHelper = new IngredientDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String
            selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = ingredientDbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(IngredientEntry.TABLE_NAME, projection, null, null, null, null, null);

        Log.v("Matcher", String.valueOf(sUriMatcher.match(uri)));
        switch (sUriMatcher.match(uri)) {
            case ACID : {
                int indexCursorName = cursor.getColumnIndex(IngredientEntry.COL_ING_NAME);
                int indexCursorAcidity = cursor.getColumnIndex(IngredientEntry.COL_ING_ACIDITY);

                ArrayList<String> arrayList = new ArrayList<>();
                while(cursor.moveToNext()) {
                    String cursorName = cursor.getString(indexCursorName);
                    JSONArray jsonArray;
                    try {
                        jsonArray = new JSONArray(cursorName);
                        for(int g = 0; g < jsonArray.length(); g++) {
                            int indexElement = arrayList.lastIndexOf(jsonArray.getString(g));
                            if(indexElement == -1) {
                                arrayList.add(jsonArray.getString(g));
                            }
                        }
                    } catch (JSONException j) {
                        Log.v("LOL except", j.toString());
                    }
                }

                MatrixCursor matrixCursor = new MatrixCursor(new String[] { IngredientEntry._ID, IngredientEntry.COL_ING_NAME, IngredientEntry.COL_ING_ACIDITY});

                for(int i = 0; i < arrayList.size(); i++) {
                    String ingredient = arrayList.get(i);

                    int[] n = {0, 0, 0, 0};
                    cursor.moveToFirst();
                    while(cursor.moveToNext()) {
                        String cursorName = cursor.getString(indexCursorName);
                        Log.v("Array", cursorName);
                        try{
                            JSONArray jsonArray = new JSONArray(cursorName);
                            int acidity = cursor.getInt(indexCursorAcidity);
                            Log.v("Acidity", String.valueOf(acidity));
                            for(int g = 0; g < jsonArray.length(); g++) {
                                if(jsonArray.getString(g).compareTo(ingredient) == 0) {
                                    if(acidity == 0) {
                                        n[1]++;
                                    } else {
                                        n[3]++;
                                    }
                                } else {
                                    if(acidity == 0) {
                                        n[0]++;
                                    } else {
                                        n[2]++;
                                    }
                                }
                            }
                        } catch (JSONException j) {
                            Log.v("LOL", j.toString());
                        }
                    }

                    matrixCursor.addRow(new Object[] { i, ingredient,
                            (Double) ((n[3]*n[0]-n[2]*n[1])/Math.sqrt(((n[0]+n[1])*(n[1]+n[3])*(n[2]+n[3])*(n[0]+n[2]))))
                    });
                }

                return matrixCursor;
            }
            case NAMES : {
                Log.v("LOL", "LOL");
                return cursor;
            }
            default: {
                Log.v("LOL", "Fuck");
                return null;
            }
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = ingredientDbHelper.getWritableDatabase();
        sqLiteDatabase.insert(IngredientEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[]
            selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
