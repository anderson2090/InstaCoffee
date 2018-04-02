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

import static com.sweetdeveloper.instacoffee.utils.MakeCustomOrder.customOrder;

public class WeightActivity extends AppCompatActivity {

    TextView percentageTextView;
    TextView labelTextView;
    ImageView imageView;
    SeekBar seekBar;
    Button nextButton;
    String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        percentageTextView = findViewById(R.id.body_percentage_text_view);
        labelTextView = findViewById(R.id.body_label_text_view);
        imageView = findViewById(R.id.body_image_view);
        seekBar = findViewById(R.id.body_seek_bar);
        nextButton = findViewById(R.id.body_next_button);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                weight  = String.valueOf(i);
                percentageTextView.setText(String.valueOf(i) + "%");
                if (i >= 50) {
                    labelTextView.setText(getString(R.string.light));
                } else if (i < 50) {
                    labelTextView.setText(getString(R.string.rich));
                }
                if (i < 25) {
                    imageView.setImageResource(R.drawable.weight_0);
                } else if (i > 25 && i < 50) {
                    imageView.setImageResource(R.drawable.weight_25);
                } else if (i > 50 && i < 75) {
                    imageView.setImageResource(R.drawable.weight_75);
                } else if (i > 75) {
                    imageView.setImageResource(R.drawable.weight_100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customOrder.setWeight(weight);
                startActivity(new Intent(getApplicationContext(), FlavorActivity.class));
            }
        });
    }
}
