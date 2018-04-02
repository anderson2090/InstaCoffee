package com.sweetdeveloper.instacoffee.customorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;

public class FlavorActivity extends AppCompatActivity {
    Button nextButton;
    TextView percentageTextView;
    TextView labelTextView;
    ImageView imageView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavor);
        percentageTextView = findViewById(R.id.flavor_percentage_text_view);
        labelTextView = findViewById(R.id.flavor_label_text_view);
        imageView = findViewById(R.id.flavor_image_view);
        seekBar = findViewById(R.id.flavor_seek_bar);
        nextButton = findViewById(R.id.flavor_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RoastActivity.class));
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentageTextView.setText(String.valueOf(i) + "%");
                if (i >= 50) {
                    labelTextView.setText(getString(R.string.soft));
                } else if (i < 50) {
                    labelTextView.setText(getString(R.string.bold));
                }
                if (i < 25) {
                    imageView.setImageResource(R.drawable.flavor_0);
                } else if (i > 25 && i < 50) {
                    imageView.setImageResource(R.drawable.flavor_25);
                } else if (i > 50 && i < 75) {
                    imageView.setImageResource(R.drawable.flavor_75);
                } else if (i > 75) {
                    imageView.setImageResource(R.drawable.flavor_100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
