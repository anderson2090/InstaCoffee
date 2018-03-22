package com.sweetdeveloper.instacoffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sweetdeveloper.instacoffee.fragments.ChangePasswordDialogFragment;
import com.sweetdeveloper.instacoffee.interfaces.ProgressBarListener;

public class WelcomeActivity extends RootActivity implements ProgressBarListener {

    FirebaseAuth firebaseAuth;
    TextView welcomeTextView;
    FirebaseUser user;
    ProgressBar progressBar;
    final String CHANGE_PASSWORD_FRAGMENT_TAG = "CHANGE PASSWORD FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar = findViewById(R.id.welcome_activity_progress_bar);
        firebaseAuth = FirebaseAuth.getInstance();
        welcomeTextView = findViewById(R.id.welcome_text_view);
        user = firebaseAuth.getCurrentUser();
        if ((user != null)) {
            if (user.getDisplayName() != null) {

                welcomeTextView.setText("Welcome " + user.getDisplayName());
            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    @Override
    public void displayProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
