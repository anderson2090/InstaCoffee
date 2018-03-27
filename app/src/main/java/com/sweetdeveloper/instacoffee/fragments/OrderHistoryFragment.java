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
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;

public class OrderHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HistoryTimeRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_history_fragment, container, false);
        recyclerView = view.findViewById(R.id.history_time_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryTimeRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        return view;

    }

    class HistoryTimeRecyclerViewAdapter extends RecyclerView.Adapter<HistoryTimeRecyclerViewAdapter.ViewHolder> {


        String[] times = {"One", "Two", "Three"};

        @NonNull
        @Override
        public HistoryTimeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_history_time, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryTimeRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.timeStampTextView.setText(times[position]);
        }

        @Override
        public int getItemCount() {
            return times.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView timeStampTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                timeStampTextView = itemView.findViewById(R.id.history_timestamp_text_view);
            }
        }
    }
}
