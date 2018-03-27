package com.sweetdeveloper.instacoffee;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.sweetdeveloper.instacoffee.models.Order;

import java.util.ArrayList;

public class HistoryItemsActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Bundle bundle;
    String currentTimeStamp;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    HistoryItemsRecyclerViewAdapter adapter;
    Parcelable listState;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_items);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        bundle = getIntent().getExtras();

        if (bundle != null) {
            currentTimeStamp = bundle.getString("timeStamp");
        }

        databaseReference = firebaseDatabase.getReference("order_history")
                .child(firebaseUser.getUid()).child(currentTimeStamp);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Order> orders = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Order order = child.getValue(Order.class);
                    orders.add(order);
                }

                recyclerView = findViewById(R.id.history_items_recycler_view);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                adapter = new HistoryItemsRecyclerViewAdapter(orders);
                recyclerView.setAdapter(adapter);

                if (savedInstanceState != null) {
                    layoutManager.onRestoreInstanceState(listState);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager != null) {
            listState = layoutManager.onSaveInstanceState();
            outState.putParcelable("state", listState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("state");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layoutManager != null) {
            if (listState != null) {
                layoutManager.onRestoreInstanceState(listState);
            }
        }
    }

    class HistoryItemsRecyclerViewAdapter extends RecyclerView.Adapter<HistoryItemsRecyclerViewAdapter.ViewHolder> {


        ArrayList<Order> orders = new ArrayList<>();

        HistoryItemsRecyclerViewAdapter(ArrayList<Order> orders) {
            this.orders = orders;
        }

        @NonNull
        @Override
        public HistoryItemsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.order_history_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryItemsRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.itemPriceTextView.setText("$"+orders.get(position).getPrice());
            holder.itemQuantityTextView.setText(orders.get(position).getQuantity());
            holder.itemNameTextView.setText(orders.get(position).getItemName());
        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView itemNameTextView;
            TextView itemQuantityTextView;
            TextView itemPriceTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                itemNameTextView = itemView.findViewById(R.id.history_item_name_text_view);
                itemQuantityTextView = itemView.findViewById(R.id.history_item_quantity_text_view);
                itemPriceTextView = itemView.findViewById(R.id.history_item_price_text_view);
            }
        }
    }
}
