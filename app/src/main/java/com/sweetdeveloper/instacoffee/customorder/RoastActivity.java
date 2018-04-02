package com.sweetdeveloper.instacoffee.customorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.WelcomeActivity;

import static com.sweetdeveloper.instacoffee.utils.Cart.orders;
import static com.sweetdeveloper.instacoffee.utils.MakeCustomOrder.customOrder;

public class RoastActivity extends AppCompatActivity {

    TextView percentageTextView;
    TextView labelTextView;
    ImageView imageView;
    SeekBar seekBar;
    String roast;
    Button addToCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roast);

        percentageTextView = findViewById(R.id.roast_percentage_text_view);
        labelTextView = findViewById(R.id.roast_label_text_view);
        imageView = findViewById(R.id.roast_image_view);
        addToCart = findViewById(R.id.roast_add_to_cart_button);
        seekBar = findViewById(R.id.roast_seek_bar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                roast = String.valueOf(i);
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

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customOrder.setRoast(roast);
                customOrder.setItemName(
                        "Custom Coffee \n"+
                        "Acidity "+customOrder.getAcidity()+"%\n"
                        +"Weight "+customOrder.getWeight()+"%\n"
                        +"Flavor "+customOrder.getFlavor()+"%\n"
                        +"Roast "+customOrder.getRoast()+"%"
                );
                customOrder.setPrice("25");
                customOrder.setQuantity("1");
                orders.add(customOrder);
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            }
        });
    }
}
