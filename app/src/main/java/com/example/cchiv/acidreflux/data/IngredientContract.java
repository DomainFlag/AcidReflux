package com.example.cchiv.acidreflux.data;

import android.provider.BaseColumns;

/**
 * Created by Cchiv on 22/09/2017.
 */

public class IngredientContract {

    IngredientContract() {

    }

    public class IngredientEntry implements BaseColumns {
        public static final String TABLE_NAME = "Ingredients";
        public static final String COL_ING_ID = BaseColumns._ID;
        public static final String COL_ING_NAME = "Ingredients";
        public static final String COL_ING_ACIDITY = "Acidity";
    }
}
