package com.sweetdeveloper.instacoffee;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    ImageView imageView;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton cartFloatingActionButton;
    ElegantNumberButton numberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        nameTextView = findViewById(R.id.detail_activity_coffee_name_text_view);
        descriptionTextView = findViewById(R.id.detail_activity_coffee_description_text_view);
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
            descriptionTextView.setText(bundle.getString("description"));
            collapsingToolbarLayout.setTitle(bundle.getString("name"));
            Picasso.get().load(bundle.getString("image"))
                    .error(R.drawable.img_placeholder)
                    .into(imageView);
        }


    }
}
