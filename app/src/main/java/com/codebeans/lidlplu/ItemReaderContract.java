package com.codebeans.lidlplu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Marvin.
 */

public final class ItemReaderContract {
    private ItemReaderContract() {}

    /* Inner class that defines the table contents */
    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "item";
        public static final String COLUMN_NAME_PLU = "plu";
        public static final String COLUMN_NAME_NAME = "name";
    }
}

