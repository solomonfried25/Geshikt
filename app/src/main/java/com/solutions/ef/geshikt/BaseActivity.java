package com.solutions.ef.geshikt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        navigationView = (BottomNavigationView) findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_maps) {

                if (!this.getClass().getName().equals("com.solutions.ef.geshikt.MapsActivity")) {
                    startActivity(new Intent(this, MapsActivity.class));

                } else {
                    return;
                }

            } else if (itemId == R.id.navigation_jobs) {

                if (!this.getClass().getName().equals("com.solutions.ef.geshikt.JobsActivity")) {
                    startActivity(new Intent(this, JobsActivity.class));

                } else {
                    return;
                }
            } else if (itemId == R.id.navigation_drivers) {
                if (!this.getClass().getName().equals("com.solutions.ef.geshikt.DriversActivity")) {
                    startActivity(new Intent(this, DriversActivity.class));

                } else {
                    return;
                }
            } else if (itemId == R.id.navigation_new) {

                if (!this.getClass().getName().equals("com.solutions.ef.geshikt.NewActivity")) {
                    startActivity(new Intent(this, NewActivity.class));

                } else {
                    return;
                }
            } else if (itemId == R.id.google_maps) {
                if (!this.getClass().getName().equals("com.solutions.ef.geshikt.GoogleMaps")) {
                    startActivity(new Intent(this, GoogleMaps.class));

                } else {
                    return;
                }
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    abstract int getContentViewId();

    abstract int getNavigationMenuItemId();

}
