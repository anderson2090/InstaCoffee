package com.sweetdeveloper.instacoffee.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.broadcastrevievers.NewsObservable;
import com.sweetdeveloper.instacoffee.services.NewsIntentService;

import java.util.Observable;
import java.util.Observer;

public class NewsFragment extends Fragment implements Observer {

    TextView newsFragmentTextView;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_fragment, container, false);
        newsFragmentTextView = view.findViewById(R.id.news_fragment_news_text_view);
        progressBar = view.findViewById(R.id.news_fragment_progress_bar);
        Intent intent = new Intent(getActivity(), NewsIntentService.class);
        getActivity().startService(intent);
        NewsObservable.getInstance().addObserver(this);
        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        Intent intent = (Intent) o;
        String news = intent.getExtras().getString("news");
        newsFragmentTextView.setText(news);
        progressBar.setVisibility(View.INVISIBLE);

    }
}
