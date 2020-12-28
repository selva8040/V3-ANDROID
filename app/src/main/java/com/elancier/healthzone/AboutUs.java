package com.elancier.healthzone;

import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.drawerlayout.widget.DrawerLayout;

public class AboutUs extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, profile_lay, my_sponsor_lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);

        init();

        onclick();

    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        profile_lay = (LinearLayout) findViewById(R.id.profile_lay);
        my_sponsor_lay = (LinearLayout) findViewById(R.id.my_sponsor_lay);
        setclick(drawer_layout, drawerLayout, AboutUs.this);

    }

    private void onclick() {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_portal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.LEFT);
            return true;
        }

        if (id == R.id.action_settings) {
            // EnableTrue();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}