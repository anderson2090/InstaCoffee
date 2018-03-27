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
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String name = child.child("itemName").getValue(String.class);
                    String price = child.child("price").getValue(String.class);
                    String quantity = child.child("quantity").getValue(String.class);

                    Log.i("NAME", name);
                    Log.i("Price", price);
                    Log.i("Quantity", quantity);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
