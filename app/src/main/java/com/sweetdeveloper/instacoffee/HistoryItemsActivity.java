package com.sweetdeveloper.instacoffee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
