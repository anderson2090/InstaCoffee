package com.sweetdeveloper.instacoffee.recyclerviews;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sweetdeveloper.instacoffee.DetailActivity;
import com.sweetdeveloper.instacoffee.R;
import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;

import java.util.ArrayList;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {


    private ArrayList<CoffeeMenuItem> menuItems = new ArrayList<>();


    public MenuRecyclerAdapter(ArrayList<CoffeeMenuItem> menuItems) {

        this.menuItems = menuItems;

    }

    @NonNull
    @Override
    public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_card, parent, false);
        MenuRecyclerAdapter.ViewHolder viewHolder = new MenuRecyclerAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecyclerAdapter.ViewHolder holder, int position) {
        holder.itemNameTextView.setText(menuItems.get(position).getName());
        Picasso.get().load(menuItems.get(position).getImage())
                .error(R.drawable.img_placeholder)
                .into(holder.coffeeImage);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Context context;

        public TextView itemNameTextView;
        public ImageView coffeeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();

            itemNameTextView = itemView.findViewById(R.id.menu_card_item_name_text_view);
            coffeeImage = itemView.findViewById(R.id.menu_item_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("name", menuItems.get(getAdapterPosition()).getName());
                    intent.putExtra("image", menuItems.get(getAdapterPosition()).getImage());
                    intent.putExtra("key", menuItems.get(getAdapterPosition()).getKey());
                    intent.putExtra("price", menuItems.get(getAdapterPosition()).getPrice());
                    intent.putExtra("description",menuItems.get(getAdapterPosition()).getDescription());
                    context.startActivity(intent);

                }
            });
        }
    }
}
