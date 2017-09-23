package com.example.cchiv.acidreflux.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cchiv.acidreflux.data.IngredientContract.IngredientEntry;

/**
 * Created by Cchiv on 22/09/2017.
 */

public class IngredientDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ingredients.db";

    public IngredientDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + IngredientEntry.TABLE_NAME + " (" +
                IngredientEntry.COL_ING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IngredientEntry.COL_ING_NAME + " TEXT NOT NULL, " +
                IngredientEntry.COL_ING_ACIDITY + " INTEGER NOT NULL);";

        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}