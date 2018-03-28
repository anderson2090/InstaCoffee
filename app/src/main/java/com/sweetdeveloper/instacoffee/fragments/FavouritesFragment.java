package com.sweetdeveloper.instacoffee.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.database.DBHandler;
import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;

import java.util.ArrayList;


public class FavouritesFragment extends Fragment {

    DBHandler dbHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.favourites_fragment, container, false);
        dbHandler = new DBHandler(getActivity(), null, null, 1);
        ArrayList<CoffeeMenuItem> coffeeMenuItems = dbHandler.getAllRows();
        Log.i("asdas","dasda");
        return view;
    }
}
