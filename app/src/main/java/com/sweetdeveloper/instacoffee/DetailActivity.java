package com.sweetdeveloper.instacoffee;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton cartFloatingActionButton;
    ElegantNumberButton numberButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Bundle bundle;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("coffeeList");

        bundle = getIntent().getExtras();
        nameTextView = findViewById(R.id.detail_activity_coffee_name_text_view);
        descriptionTextView = findViewById(R.id.detail_activity_coffee_description_text_view);
        priceTextView = findViewById(R.id.detail_activity_coffee_price_text_view);
        imageView = findViewById(R.id.detail_collapsing_tb_image_view);
        numberButton = findViewById(R.id.detail_activity_number_button);
        cartFloatingActionButton = findViewById(R.id.detail_activity_cart_button);
        collapsingToolbarLayout = findViewById(R.id.detail_activity_collapsing_toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expanded_app_bar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsed_app_bar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.green));
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));

        if (bundle != null) {
            nameTextView.setText(bundle.getString("name"));
            collapsingToolbarLayout.setTitle(bundle.getString("name"));
            Picasso.get().load(bundle.getString("image"))
                    .error(R.drawable.img_placeholder)
                    .into(imageView);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String key = bundle.getString("key");
                    String description = null;
                    price = null;
                    if (key != null) {
                        description = dataSnapshot.child(key).child("description").getValue(String.class);
                        price = dataSnapshot.child(key).child("price").getValue(String.class);
                    }
                    descriptionTextView.setText(description);
                    priceTextView.setText(price);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                double newPrice = Double.parseDouble(price) * newValue;
                priceTextView.setText(newPrice + "");
            }
        });
    }
}
