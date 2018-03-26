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
import com.sweetdeveloper.instacoffee.models.Order;

import static com.sweetdeveloper.instacoffee.utils.Cart.orders;

public class DetailActivity extends RootActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton cartAddOrderFloatingActionButton;
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
        cartAddOrderFloatingActionButton = findViewById(R.id.detail_activity_cart_button);
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

        cartAddOrderFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orders.add(new Order(nameTextView.getText().toString(),
                        priceTextView.getText().toString(),
                        numberButton.getNumber() + ""));
                informUserViaToast(getString(R.string.added_to_cart));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", nameTextView.getText().toString());
        outState.putString("quantity", numberButton.getNumber());
        outState.putString("price", priceTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            nameTextView.setText(savedInstanceState.getString("name"));
            numberButton.setNumber(savedInstanceState.get("quantity") + "");
            priceTextView.setText(savedInstanceState.getString("price"));
        }
    }
}
