package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.ReportsAdapter;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;

import java.util.ArrayList;

public class PurchaseQuotation extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout,reports_lay,ibv_reports_lay;
    ArrayList<ReportsPojo> data;
    ReportsPojo pojo;
    ListView listView;
    ReportsAdapter adapter;
    String[] name = {"RamKumar", "Senthil", "Prabakaran", "Rahavan", "Sathis", "Sonthosh"};

    ImageView reports_img,search_img;
    boolean filtercheck = false;
    EditText from_date,to_date;
    LinearLayout filter_lay;

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView select,cancel;
    Utils utils;
    int start;
    int limit;
    int dateval=0;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    LinearLayout loadlay;
    Dialog retry;
    ImageView retrybut;
    TextView nodata;
    String fromdate="",todate="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_quotation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);

        init();


    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        setclick(drawer_layout, drawerLayout, PurchaseQuotation.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);
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

            Fillter();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Fillter() {

        if (!filtercheck) {
            filter_lay.setVisibility(View.VISIBLE);
            filtercheck = true;
            fromdate="";
            todate="";
            from_date.setText("");
            to_date.setText("");
        }else{
            filter_lay.setVisibility(View.GONE);
            filtercheck = false;
            fromdate="";
            todate="";
            from_date.setText("");
            to_date.setText("");
        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}
