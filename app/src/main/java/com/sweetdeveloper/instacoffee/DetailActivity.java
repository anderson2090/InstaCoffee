package com.sweetdeveloper.instacoffee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    TextView nameTextView;
    TextView descriptionTextView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();
        nameTextView = findViewById(R.id.detail_activity_coffee_name_text_view);
        descriptionTextView = findViewById(R.id.detail_activity_coffee_description_text_view);
        imageView = findViewById(R.id.detail_collapsing_tb_image_view);
        if (bundle != null) {
            nameTextView.setText(bundle.getString("name"));
            descriptionTextView.setText(bundle.getString("description"));
            Picasso.get().load(bundle.getString("image"))
                    .error(R.drawable.img_placeholder)
                    .into(imageView);
        }


    }
}
