package com.sweetdeveloper.instacoffee.fragments;

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
        dbAdapter = DBAdapter.getDbAdapterInstance(getActivity());
        ArrayList<CoffeeMenuItem> coffeeMenuItems = dbAdapter.getAllRows();

        recyclerView = view.findViewById(R.id.favourites_menu_items_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MenuRecyclerAdapter(coffeeMenuItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
