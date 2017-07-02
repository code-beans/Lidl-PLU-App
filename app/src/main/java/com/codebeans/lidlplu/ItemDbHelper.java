package com.codebeans.lidlplu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class ItemDbHelper extends SQLiteAssetHelper {

        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "lidl.db";

        public ItemDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }



    }
