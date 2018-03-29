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
import com.sweetdeveloper.instacoffee.database.DBHandler;
import com.sweetdeveloper.instacoffee.models.Order;

import static com.sweetdeveloper.instacoffee.utils.Cart.orders;

public class DetailActivity extends RootActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    TextView priceTextView;
    ImageView addFavouritesImageView;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton cartAddOrderFloatingActionButton;
    ElegantNumberButton numberButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DBHandler dbHandler;
    Bundle bundle;
    String name;
    String image;
    String price;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        addFavouritesImageView = findViewById(R.id.add_favourite_image_view);
        //Sqlite
        dbHandler = new DBHandler(getApplicationContext(), null, null, 1);


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
            name = bundle.getString("name");
            nameTextView.setText(name);

            price = bundle.getString("price");
            priceTextView.setText(price);
            collapsingToolbarLayout.setTitle(bundle.getString("name"));
            image = bundle.getString("image");
            Picasso.get().load(image)
                    .error(R.drawable.img_placeholder)
                    .into(imageView);
            description = bundle.getString("description");
            descriptionTextView.setText(description);
        }

        if (dbHandler.IsDataAlreadyInDB(name)) {
            addFavouritesImageView.setImageResource(R.drawable.ic_favorite_green);
        } else {
            addFavouritesImageView.setImageResource(R.drawable.ic_favorite_black);
        }

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                double newPrice = 0.00;
                if (bundle != null) {
                    newPrice = Double.parseDouble(bundle.getString("price")) * newValue;
                }
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

        addFavouritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dbHandler.IsDataAlreadyInDB(name)) {
                    dbHandler.addItem(name, image, price, description);
                    informUserViaToast(getString(R.string.added_to_favourites));
                    addFavouritesImageView.setImageResource(R.drawable.ic_favorite_green);
                } else {
                    dbHandler.deleteItem(name);
                    addFavouritesImageView.setImageResource(R.drawable.ic_favorite_black);
                    informUserViaToast(getString(R.string.removed_from_favourites));
                }


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
