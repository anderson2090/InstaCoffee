package com.sweetdeveloper.instacoffee.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favourites.db";
    public static final String TABLE_FAVOURITES = "favourites";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " +
                TABLE_FAVOURITES + "("
                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT" + ")";


        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
        onCreate(sqLiteDatabase);
    }

    public void addItem(String name, String image, String price, String description) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_DESCRIPTION, description);

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(TABLE_FAVOURITES, null, values);
        database.close();

    }

    public boolean IsDataAlreadyInDB(String itemName) {
        SQLiteDatabase database = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_FAVOURITES + " where " + COLUMN_NAME + " = " + "'" + itemName + "'";
        Cursor cursor = database.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteItem(String itemName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_FAVOURITES, COLUMN_NAME + "=" + "'" + itemName + "'", null);
    }

    public ArrayList<CoffeeMenuItem> getAllRows() {

        ArrayList<CoffeeMenuItem> coffeeMenuItems = new ArrayList<>();


        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITES;

        SQLiteDatabase db = this.getReadableDatabase();
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
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
                    cursor.close();
                } catch (Exception ignore) {
                }
            }

        } finally {
            try {
                db.close();
            } catch (Exception ignore) {
            }
        }

        return coffeeMenuItems;
    }
}
