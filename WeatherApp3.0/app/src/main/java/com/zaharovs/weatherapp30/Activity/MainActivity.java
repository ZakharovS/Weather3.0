package com.zaharovs.weatherapp30.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.zaharovs.weatherapp30.Fragment.Fragment2;
import com.zaharovs.weatherapp30.ClickInterface;
import com.zaharovs.weatherapp30.R;

public class MainActivity extends AppCompatActivity implements ClickInterface {
    private int itemPosition = 0;
    private boolean withContent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }

        if (savedInstanceState != null) {
            this.itemPosition = savedInstanceState.getInt("itemPosition");
        }

        this.withContent = (findViewById(R.id.main_fragment_content) != null);

        if (withContent) {
            showContent(itemPosition);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("itemPosition", itemPosition);
    }

    @Override
    public void clickItem(int position) {
        this.itemPosition = position;
        showContent(position);
    }

    private void showContent(int position) {
        if (withContent) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment2 fragment2 = (Fragment2) fragmentManager.findFragmentById(R.id.main_fragment_content);

            if (fragment2 == null || fragment2.getPosition() != position) {
                fragment2 = Fragment2.newInstance(position);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_fragment_content, fragment2);
                fragmentTransaction.commit();
            }
        } else {
            startActivity(new Intent(this, SecondActivity.class).putExtra("itemPosition", position));
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