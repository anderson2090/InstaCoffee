package com.sweetdeveloper.instacoffee.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sweetdeveloper.instacoffee.HistoryItemsActivity;
import com.sweetdeveloper.instacoffee.R;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference historyReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HistoryTimeRecyclerViewAdapter adapter;
    Parcelable listState;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_history_fragment, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView = view.findViewById(R.id.history_time_recycler_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        historyReference = firebaseDatabase.getReference("order_history").child(user.getUid());
        historyReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> timeStamps = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String timeStamp = child.getKey();
                    timeStamps.add(timeStamp);
                }
                layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new HistoryTimeRecyclerViewAdapter(timeStamps);
                recyclerView.setAdapter(adapter);
                if (listState != null) {
                    layoutManager.onRestoreInstanceState(listState);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable("state", listState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("state");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (listState != null) {
            if (layoutManager != null) {
                layoutManager.onRestoreInstanceState(listState);
            }
        }
    }

    class HistoryTimeRecyclerViewAdapter extends RecyclerView.Adapter<HistoryTimeRecyclerViewAdapter.ViewHolder> {


        //String[] times = {"One", "Two", "Three"};
        ArrayList<String> timeStamps = new ArrayList<>();

        HistoryTimeRecyclerViewAdapter(ArrayList<String> timeStamps) {
            this.timeStamps = timeStamps;
        }

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
            holder.timeStampTextView.setText(timeStamps.get(position));
        }

        @Override
        public int getItemCount() {
            return timeStamps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView timeStampTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                timeStampTextView = itemView.findViewById(R.id.history_timestamp_text_view);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), HistoryItemsActivity.class);
                        intent.putExtra("timeStamp", timeStamps.get(getAdapterPosition()));
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
