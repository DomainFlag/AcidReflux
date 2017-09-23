package com.example.cchiv.acidreflux.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

/**
 * Created by Cchiv on 22/09/2017.
 */

public class IngredientProvider extends ContentProvider{

    IngredientDbHelper ingredientDbHelper;

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
        return sqLiteDatabase.query(IngredientEntry.TABLE_NAME, projection, null, null, null, null, null);
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
        Log.v("values", values.toString());
        sqLiteDatabase.insert(IngredientEntry.TABLE_NAME, null, values);
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
