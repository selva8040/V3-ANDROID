package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Common.Utils;
import com.squareup.picasso.Picasso;

public class MyPortal extends MainView {

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView cancel, select, save, gender_error, my_portal;
    EditText user_id, doj, designaton, ibv, gbv, receivable_amt;
    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, profile_lay, my_portal_lay;
    ImageView user_img;
    String MobilePattern = "[0-9]{10}";
    String NamePattern = "[a-zA-Z ]+";
    String RelationPattern = "[a-zA-Z -]+";
    //String email1 = email.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    ImageView profile_img;
    Utils utils;
    int menuvalue=0;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_portal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        // getSupportActionBar().setHomeAsUpIndicator();
       // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
         utils=new Utils(getApplicationContext());
        init();

        onclick();

        user_id.setText(utils.loadName()+"");
        doj.setText(utils.loadDoj()+"");
        designaton.setText(utils.loadDesignation()+"");
        ibv.setText(utils.loadIbv()+"");
        gbv.setText(utils.loadgbv()+"");
        receivable_amt.setText(utils.loadCommition()+"");
       // Log.i("tabresp",utils.loadImage()+"");
        if(utils.loadImage().toString().trim().length()>0) {
            Picasso.with(MyPortal.this).load(utils.loadImage()).resize(200,250).placeholder(R.mipmap.userplaceholder).into(user_img);
        }
        else{
            Picasso.with(MyPortal.this).load(R.mipmap.userplaceholder).into(user_img);

        }



    }


    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        profile_lay = (LinearLayout) findViewById(R.id.profile_lay);
        my_portal_lay = (LinearLayout) findViewById(R.id.my_portal_lay);
        setclick(drawer_layout, drawerLayout, MyPortal.this);

        user_id = (EditText) findViewById(R.id.user_id);
        doj = (EditText) findViewById(R.id.doj);
        user_img=(ImageView)findViewById(R.id.user_img);
        designaton = (EditText) findViewById(R.id.designation);
        gbv = (EditText) findViewById(R.id.gbv);
        ibv = (EditText) findViewById(R.id.ibv);
        receivable_amt = (EditText) findViewById(R.id.receivable_amt);
        save = (TextView) findViewById(R.id.save);
        profile_img = (ImageView) findViewById(R.id.profile_img);
        // my_portal = (TextView) findViewById(R.id.my_portal);
        // my_portal.setBackgroundColor(0xFF849F2A);
        profile_lay.setVisibility(View.VISIBLE);
        profile_img.setImageResource(R.mipmap.up_arrow);
        my_portal_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        // my_portal_lay.setBackgroundColor(0xFFEEEEEE);


        /*{

            *//** Called when a drawer has settled in a completely open state. *//*
            public void onDrawerOpened(View drawerView) {
                menuvalue=1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            *//** Called when a drawer has settled in a completely closed state. *//*
            public void onDrawerClosed(View view) {
                menuvalue=0;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
            }
        };*/
       // mDrawerToggle.setDrawerIndicatorEnabled(false);



    }

    private void onclick() {

       /* doj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new Dialog(MyPortal.this);
                picker.setContentView(R.layout.datepicker);
                picker.setTitle("Select Date and Time");
                datep = (DatePicker) picker.findViewById(R.id.datePicker);
                datep.setMaxDate(System.currentTimeMillis());
                cancel = (TextView) picker.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        picker.dismiss();
                    }
                });

                select = (TextView) picker.findViewById(R.id.select);
                select.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        month = datep.getMonth() + 1;
                        day = datep.getDayOfMonth();
                        year = datep.getYear();
                        // hour = timep.getCurrentHour();
                        // minute = timep.getCurrentMinute();
                        date = day + "/" + month + "/" + year;
                        // time = hour + ":" + minute;
                        // Toast.makeText(view.getContext(), date , Toast.LENGTH_LONG).show();
                        doj.setText(date);
                        picker.dismiss();

                    }
                });
                picker.show();

            }
        });*/

       /* save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user_id.getText().toString().length() <= 0) {
                    user_id.setError("Enter Username");
                }

                if (doj.getText().toString().length() <= 0) {
                    doj.setError("Enter Valid Date");
                }

                if (designaton.getText().toString().length() <= 0) {
                    designaton.setError("Enter Firstname");
                }
                if (designaton.getText().toString().length() > 0 &&! designaton .getText().toString().matches(NamePattern)) {
                    designaton.setError("Enter Characters Only");
                }
*//*
                if (ibv.getText().toString().trim().length() <= 0 ) {
                    ibv.setError("Enter Pincode");
                }

                if (ibv.getText().toString().trim().length() <= 0 ) {
                    ibv.setError("Enter Pincode");
                }*//*

                if (receivable_amt.getText().toString().trim().length() <= 0 ) {
                    receivable_amt.setError("Enter Pincode");
                }

                if((user_id.getText().toString().length() > 0) && (doj.getText().toString().length() > 0) && (designaton.getText().toString().length() > 0)
                        && designaton .getText().toString().matches(NamePattern) && (receivable_amt.getText().toString().length() > 0) ){


                Toast.makeText(MyPortal.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyPortal.this,MyInfo.class);
                startActivity(intent);

                }else {
                    Toast.makeText(MyPortal.this, "Please Fill Above Details", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
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
            onBackPressed();
            return true;
        }

        /*if (id == R.id.action_settings) {

            user_id.setEnabled(true);
            user_img.setEnabled(true);
            doj.setEnabled(true);
            designaton.setEnabled(true);
            receivable_amt.setEnabled(true);
            save.setVisibility(View.VISIBLE);

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
