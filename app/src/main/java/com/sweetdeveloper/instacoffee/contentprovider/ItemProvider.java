package com.sweetdeveloper.instacoffee.contentprovider;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sweetdeveloper.instacoffee.database.DBAdapter;

public class ItemProvider extends ContentProvider {

    public static final String AUTHORITY = "com.sweetdeveloper.instacoffee.contentprovider.ItemProvider";
    private static final String TABLE_ITEMS = "favourites";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ITEMS);

    public static final int ITEMS = 1;
    public static final int ITEMS_NAME = 2;

    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, TABLE_ITEMS, ITEMS);

        URI_MATCHER.addURI(AUTHORITY, TABLE_ITEMS + "/#", +ITEMS_NAME);
    }

    private DBAdapter dbAdapter;

    public ItemProvider() {
    }

    @Override
    public boolean onCreate() {
        dbAdapter = DBAdapter.getDbAdapterInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case ITEMS:
                cursor = dbAdapter.getCursorsForAllItems();
                break;
            default:
                cursor = null;
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri returnUri = null;
        switch (URI_MATCHER.match(uri)) {
            case ITEMS:
                returnUri = insertItem(uri, values);
                break;
            default:
                throw new UnsupportedOperationException("insert operation not supported");
        }
        return returnUri;
    }

    private Uri insertItem(Uri uri, ContentValues values) {
        long id = dbAdapter.insert(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse("content://" + AUTHORITY + "/" + TABLE_ITEMS + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount = -1;
        switch (URI_MATCHER.match(uri)) {
            case ITEMS:
                deleteCount = deleteItem(selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("delete operation not supported");
        }
        return deleteCount;
    }

    private int deleteItem(String selection, String[] selectionArgs) {
        return dbAdapter.delete(selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


}
