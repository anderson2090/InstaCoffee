package com.sweetdeveloper.instacoffee.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.contentprovider.ItemProvider;
import com.sweetdeveloper.instacoffee.database.DBAdapter;
import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;
import com.sweetdeveloper.instacoffee.recyclerviews.MenuRecyclerAdapter;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MenuRecyclerAdapter adapter;

    DBAdapter dbAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favourites_fragment, container, false);

        Cursor cursor = getActivity().getContentResolver().query(ItemProvider.CONTENT_URI,
                null, null, null, null, null);

        // dbAdapter = DBAdapter.getDbAdapterInstance(getActivity());
        //ArrayList<CoffeeMenuItem> coffeeMenuItems = dbAdapter.getAllRows();
        ArrayList<CoffeeMenuItem> coffeeMenuItems = cursor2items(cursor);

        recyclerView = view.findViewById(R.id.favourites_menu_items_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MenuRecyclerAdapter(coffeeMenuItems);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private ArrayList<CoffeeMenuItem> cursor2items(Cursor cursor) {
        ArrayList<CoffeeMenuItem> coffeeMenuItems = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String itemName = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_NAME));
                String price = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_PRICE));
                String image = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_IMAGE));
                String description = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_DESCRIPTION));


                CoffeeMenuItem item = new CoffeeMenuItem(itemName, image, description, price);

                coffeeMenuItems.add(item);
                cursor.moveToNext();
            }
            cursor.close();
            // sqLiteDatabase.close();
        }
        return coffeeMenuItems;
    }
}
