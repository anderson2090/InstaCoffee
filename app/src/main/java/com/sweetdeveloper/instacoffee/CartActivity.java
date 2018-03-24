package com.sweetdeveloper.instacoffee;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.sweetdeveloper.instacoffee.utils.Cart.orders;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartItemRecyclerAdapter adapter;
    Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CartItemRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            layoutManager.onRestoreInstanceState(listState);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState = layoutManager.onSaveInstanceState();
        outState.putParcelable("state", listState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable("state");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (layoutManager != null) {
            if (listState != null) {
                layoutManager.onRestoreInstanceState(listState);
            }
        }
    }

    class CartItemRecyclerAdapter extends RecyclerView.Adapter<CartItemRecyclerAdapter.ViewHolder> {

        @NonNull
        @Override
        public CartItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CartItemRecyclerAdapter.ViewHolder holder, int position) {
            if (orders.isEmpty() || orders == null || orders.size() == 0) {

                Toast.makeText(CartActivity.this, getString(R.string.no_items_cart), Toast.LENGTH_LONG).show();
            } else {
                holder.itemName.setText(orders.get(position).getItemName());
                holder.itemQuantity.setText(orders.get(position).getQuantity());
                holder.itemPrice.setText(orders.get(position).getPrice());

            }

        }

        @Override
        public int getItemCount() {
            return orders.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView itemName;
            TextView itemPrice;
            TextView itemQuantity;
            ImageView removeCartImageView;

            public ViewHolder(View itemView) {
                super(itemView);

                itemName = itemView.findViewById(R.id.cart_item_name_text_view);
                itemPrice = itemView.findViewById(R.id.cart_item_price_text_view);
                itemQuantity = itemView.findViewById(R.id.cart_item_quantity_text_view);
                removeCartImageView = itemView.findViewById(R.id.remove_cart_image_view);
                removeCartImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orders.remove(getAdapterPosition());
                        Toast.makeText(getApplicationContext(), getString(R.string.removed), Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
