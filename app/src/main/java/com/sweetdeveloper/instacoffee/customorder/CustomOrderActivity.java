package com.sweetdeveloper.instacoffee.customorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;

public class CustomOrderActivity extends AppCompatActivity {

    TextView percentageTextView;
    TextView labelTextView;
    ImageView imageView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_order);

        percentageTextView = findViewById(R.id.acidity_percentage_text_view);
        labelTextView = findViewById(R.id.acidity_label_text_view);
        imageView = findViewById(R.id.acidity_image_view);
        seekBar = findViewById(R.id.acidity_seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentageTextView.setText(String.valueOf(i) + "%");
                if (i >= 50) {
                    labelTextView.setText("Bright");
                } else if (i < 50) {
                    labelTextView.setText("Smooth");
                }
                if (i < 25) {
                    imageView.setImageResource(R.drawable.acidity_0);
                } else if (i > 25 && i < 50) {
                    imageView.setImageResource(R.drawable.acidity_25);
                } else if (i > 50 && i < 75) {
                    imageView.setImageResource(R.drawable.acidity_75);
                } else if (i > 75) {
                    imageView.setImageResource(R.drawable.acidity_100);
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
