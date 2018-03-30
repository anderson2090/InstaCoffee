package com.sweetdeveloper.instacoffee.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;

import java.util.ArrayList;

public class DBAdapter {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favourites.db";
    public static final String TABLE_FAVOURITES = "favourites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";

    public static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_FAVOURITES + "("
            + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PRICE + " TEXT,"
            + COLUMN_IMAGE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT" + ")";

    public Context context;
    public SQLiteDatabase sqLiteDatabase;
    public static DBAdapter dbAdapterInstance;

    private DBAdapter(Context context) {
        this.context = context;
        sqLiteDatabase = new SQLiteDBHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION).getWritableDatabase();

    }

    public static DBAdapter getDbAdapterInstance(Context context) {
        if (dbAdapterInstance == null) {
            dbAdapterInstance = new DBAdapter(context);
        }
        return dbAdapterInstance;
    }

    public void addItem(String name, String image, String price, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_DESCRIPTION, description);


        sqLiteDatabase.insert(TABLE_FAVOURITES, null, values);
        sqLiteDatabase.close();

    }

    public boolean IsDataAlreadyInDB(String itemName) {

        String Query = "Select * from " + TABLE_FAVOURITES + " where " + COLUMN_NAME + " = " + "'" + itemName + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            // cursor.close();
            return false;
        }
        // cursor.close();
        return true;
    }

    public void deleteItem(String itemName) {

        sqLiteDatabase.delete(TABLE_FAVOURITES, COLUMN_NAME + "=" + "'" + itemName + "'", null);
    }

    public ArrayList<CoffeeMenuItem> getAllRows() {
        SQLiteDatabase database = new SQLiteDBHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION).getWritableDatabase();

        ArrayList<CoffeeMenuItem> coffeeMenuItems = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITES;


        try {

            Cursor cursor = database.rawQuery(selectQuery, null);
            try {


                if (cursor.moveToFirst()) {
                    do {
                        CoffeeMenuItem menuItem = new CoffeeMenuItem();

                        menuItem.setName(cursor.getString(1));
                        menuItem.setPrice(cursor.getString(2));
                        menuItem.setImage(cursor.getString(3));
                        menuItem.setDescription(cursor.getString(4));


                        coffeeMenuItems.add(menuItem);
                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    // cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                //  sqLiteDatabase.close();
            } catch (Exception ignore) {
            }
        }

        return coffeeMenuItems;
    }

    public StringBuilder getAllNames() {
        SQLiteDatabase database = new SQLiteDBHelper(this.context, DATABASE_NAME, null, DATABASE_VERSION).getWritableDatabase();

        StringBuilder coffeeMenuItemsNames = new StringBuilder();


        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITES;


        try {

            Cursor cursor = database.rawQuery(selectQuery, null);
            try {


                if (cursor.moveToFirst()) {
                    do {


                        coffeeMenuItemsNames.append(cursor.getString(1));
                        coffeeMenuItemsNames.append("\n");


                    } while (cursor.moveToNext());
                }

            } finally {
                try {
                    // cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                //  sqLiteDatabase.close();
            } catch (Exception ignore) {
            }
        }

        return coffeeMenuItemsNames;
    }

    //Content provider methods
    public long insert(ContentValues contentValues) {
        return sqLiteDatabase.insert(TABLE_FAVOURITES, null, contentValues);
    }

    public int delete(String whereClause, String[] whereValues) {
        return sqLiteDatabase.delete(TABLE_FAVOURITES, whereClause, whereValues);
    }

    public Cursor getCursorsForAllItems() {
        return sqLiteDatabase.query(TABLE_FAVOURITES, new String[]{COLUMN_NAME,
                        COLUMN_IMAGE, COLUMN_DESCRIPTION,
                        COLUMN_PRICE},
                null, null, null,
                null, null, null);
    }

    private static class SQLiteDBHelper extends SQLiteOpenHelper {
        public SQLiteDBHelper(Context context, String databaseName,
                              SQLiteDatabase.CursorFactory factory, int dbVersion) {
            super(context, databaseName, factory, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
            onCreate(sqLiteDatabase);
        }


    }

}
