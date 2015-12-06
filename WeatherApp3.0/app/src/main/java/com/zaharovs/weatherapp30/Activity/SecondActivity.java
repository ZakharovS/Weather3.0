package com.zaharovs.weatherapp30.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zaharovs.weatherapp30.Fragment.Fragment2;
import com.zaharovs.weatherapp30.R;

public class SecondActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        boolean landscapeOrientation = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
        boolean displayLargeSize = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= (Configuration.SCREENLAYOUT_SIZE_LARGE);

        if (landscapeOrientation && displayLargeSize) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            int itemPosition = getIntent().getIntExtra("itemPosition", 0);
            Fragment2 fragment2 = Fragment2.newInstance(itemPosition);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(android.R.id.content, fragment2).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
