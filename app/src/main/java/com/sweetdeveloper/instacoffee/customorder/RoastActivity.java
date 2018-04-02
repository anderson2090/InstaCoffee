package com.sweetdeveloper.instacoffee.customorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;

public class RoastActivity extends AppCompatActivity {

    TextView percentageTextView;
    TextView labelTextView;
    ImageView imageView;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roast);

        percentageTextView = findViewById(R.id.roast_percentage_text_view);
        labelTextView = findViewById(R.id.roast_label_text_view);
        imageView = findViewById(R.id.roast_image_view);
        seekBar = findViewById(R.id.roast_seek_bar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentageTextView.setText(String.valueOf(i) + "%");
                if (i >= 50) {
                    labelTextView.setText(getString(R.string.dark));
                } else if (i < 50) {
                    labelTextView.setText(getString(R.string.light));
                }
                if (i < 25) {
                    imageView.setImageResource(R.drawable.roast_0);
                } else if (i > 25 && i < 50) {
                    imageView.setImageResource(R.drawable.roast_25);
                } else if (i > 50 && i < 75) {
                    imageView.setImageResource(R.drawable.roast_75);
                } else if (i > 75) {
                    imageView.setImageResource(R.drawable.roast_100);
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
