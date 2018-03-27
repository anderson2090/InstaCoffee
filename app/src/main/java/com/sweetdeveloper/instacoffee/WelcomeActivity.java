package com.sweetdeveloper.instacoffee;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.sweetdeveloper.instacoffee.fragments.ChangePasswordDialogFragment;
import com.sweetdeveloper.instacoffee.fragments.OrderHistoryFragment;
import com.sweetdeveloper.instacoffee.interfaces.ProgressBarListener;
import com.sweetdeveloper.instacoffee.models.CoffeeMenuItem;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProgressBarListener {

    TextView userName, userEmail;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseDatbaseReference;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    Parcelable listState;
    final String CHANGE_PASSWORD_FRAGMENT_TAG = "CHANGE PASSWORD FRAGMENT";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatbaseReference = firebaseDatabase.getReference("coffeeList");
        firebaseDatbaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CoffeeMenuItem> coffeeMenuItems = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    //  CoffeeMenuItem coffeeMenuItem = child.getValue(CoffeeMenuItem.class);
                    String itemName = child.child("name").getValue(String.class);
                    String itemImage = child.child("image").getValue(String.class);
                    String itemPrice = child.child(("price")).getValue(String.class);
                    String key = child.getKey();
                    CoffeeMenuItem coffeeMenuItem = new CoffeeMenuItem(itemName, itemImage, key, itemPrice);
                    coffeeMenuItems.add(coffeeMenuItem);
                }
                recyclerView = findViewById(R.id.menu_items_recycler_view);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);

                adapter = new MenuRecyclerAdapter(coffeeMenuItems);
                recyclerView.setAdapter(adapter);
                if (savedInstanceState != null) {
                    layoutManager.onRestoreInstanceState(listState);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

        progressBar = findViewById(R.id.welcome_activity_progress_bar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.drawer_header_user_name_text_view);
        userEmail = headerView.findViewById(R.id.drawer_header_user_email_text_view);
        if (user != null) {
            userName.setText(user.getDisplayName());
            userEmail.setText(user.getEmail());

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.welcome_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_sign_out) {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_change_password) {
            new ChangePasswordDialogFragment().show(getSupportFragmentManager(), CHANGE_PASSWORD_FRAGMENT_TAG);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction()
                    .remove(getSupportFragmentManager().findFragmentById(R.id.welcome_activity_root_layout))
                    .commit();
        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.welcome_activity_root_layout, new OrderHistoryFragment(),"orderHistoryFragment").commit();
        } else if (id == R.id.nav_change_pw) {

        } else if (id == R.id.nav_sign_out) {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (layoutManager != null) {
            listState = layoutManager.onSaveInstanceState();
            outState.putParcelable("state", listState);
        }
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

    @Override
    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {


        private ArrayList<CoffeeMenuItem> menuItems = new ArrayList<>();

        public MenuRecyclerAdapter(ArrayList<CoffeeMenuItem> menuItems) {

            this.menuItems = menuItems;
        }

        @NonNull
        @Override
        public MenuRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_card, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
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
            public TextView itemNameTextView;
            public ImageView coffeeImage;

            public ViewHolder(View itemView) {
                super(itemView);
                itemNameTextView = itemView.findViewById(R.id.menu_card_item_name_text_view);
                coffeeImage = itemView.findViewById(R.id.menu_item_image);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("name", menuItems.get(getAdapterPosition()).getName());
                        intent.putExtra("image", menuItems.get(getAdapterPosition()).getImage());
                        intent.putExtra("key", menuItems.get(getAdapterPosition()).getKey());
                        intent.putExtra("price", menuItems.get(getAdapterPosition()).getPrice());
                        // intent.putExtra("description", menuItems.get(getAdapterPosition()).getDescription());
                        startActivity(intent);
                    }
                });
            }
        }
    }
}
