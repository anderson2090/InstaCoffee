package com.sweetdeveloper.instacoffee.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.broadcastrevievers.NewsObservable;
import com.sweetdeveloper.instacoffee.models.News;
import com.sweetdeveloper.instacoffee.services.NewsIntentService;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class NewsFragment extends Fragment implements Observer {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    NewsRecyclerViewAdapter adapter;
    ArrayList<News> newsList = new ArrayList<>();
    TextView newsFragmentTextView;
    ProgressBar progressBar;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.news_fragment, container, false);




        progressBar = view.findViewById(R.id.news_fragment_progress_bar);
        Intent intent = new Intent(getActivity(), NewsIntentService.class);
        getActivity().startService(intent);
        NewsObservable.getInstance().addObserver(this);
        return view;
    }

    @Override
    public void update(Observable observable, Object o) {
        Intent intent = (Intent) o;
        // String news = intent.getExtras().getString("news");
        newsList = intent.getParcelableArrayListExtra("newsList");

        recyclerView = view.findViewById(R.id.news_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new NewsRecyclerViewAdapter(newsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        // newsFragmentTextView.setText(news);
        progressBar.setVisibility(View.INVISIBLE);

    }

    class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {


        ArrayList<News> newsList = new ArrayList<>();
        NewsRecyclerViewAdapter(ArrayList<News> newsList){
            this.newsList = newsList;
        }

        @NonNull
        @Override
        public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.newsHeadingTextView.setText(newsList.get(position).getHeading());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsHeadingTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                newsHeadingTextView = itemView.findViewById(R.id.news_heading_text_view);
            }
        }
    }
}
