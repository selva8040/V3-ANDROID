package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class MySponsor extends MainView {

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView cancel, select, save,my_sponcer;

    ImageView profile_img;

   // EditText name,username,dob,address,mobile;
    DrawerLayout drawerLayout;
    LinearLayout drawer_layout,profile_lay,my_sponsor_lay;
    String MobilePattern = "[0-9]{10}";
    String NamePattern = "[a-zA-Z ]+";
    String RelationPattern = "[a-zA-Z -]+";
    //String email1 = email.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Utils utils;
    Dialog retry;
    ImageView retrybut;
    TextView name,username,dob,mobile,address;
    int menuvalue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sponsor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils=new Utils(getApplicationContext());
        init();
        loadprogress();
        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v=getLayoutInflater().inflate(R.layout.retrylay,null);
        retrybut=(ImageView)v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        startprogress();
        GetInfoTask task=new GetInfoTask();
        task.execute();

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();
                startprogress();
                GetInfoTask task=new GetInfoTask();
                task.execute();
            }
        });

    }

    private void init() {

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout)findViewById(R.id.drawer_layout);
        profile_lay = (LinearLayout)findViewById(R.id.profile_lay);
        my_sponsor_lay = (LinearLayout)findViewById(R.id.my_sponsor_lay);
        setclick(drawer_layout,drawerLayout,MySponsor.this);


        save = (TextView) findViewById(R.id.save);

       name = (TextView)findViewById(R.id.name);
        username = (TextView)findViewById(R.id.username);
        dob = (TextView)findViewById(R.id.dob);
        mobile = (TextView)findViewById(R.id.mobile);
        address = (TextView)findViewById(R.id.address);
        profile_img = (ImageView) findViewById(R.id.profile_img);

       /* my_sponcer = (TextView) findViewById(R.id.my_sponsor);
        my_sponcer.setBackgroundColor(0xFFFFFFFF);*/
        profile_lay.setVisibility(View.VISIBLE);
        profile_img.setImageResource(R.mipmap.up_arrow);
        my_sponsor_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                menuvalue=1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                menuvalue=0;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(mDrawerToggle);

    }

    private class GetInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());


                Log.i("check Input", Appconstants.GET_MY_SPONSER+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_SPONSER,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("tabresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        JSONObject jobj=jarr.getJSONObject(0);
                        username.setText(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                        name.setText(jobj.getString("name").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("name"));
                        dob.setText(jobj.getString("dob").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("dob"));
                        mobile.setText(jobj.getString("mobile").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("mobile"));
                        address.setText(jobj.getString("address").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("address"));

                    }
                } else {
                    retry.show();
                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                retry.show();
                e.printStackTrace();
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

      /*private boolean isValidMobile(EditText mobile) {

        return Patterns.PHONE.matcher(mobile.getText().toString().trim()).matches();
    }

    private boolean isValidEmail(EditText username) {

        return Patterns.EMAIL_ADDRESS.matcher(username.getText().toString().trim()).matches();
    }*/


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
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
            if(menuvalue==0)
            onBackPressed();
            return true;
        }

        if (id == R.id.action_settings) {
           // EnableTrue();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   /* private void EnableTrue() {

        name.setEnabled(true);
        username.setEnabled(true);
        dob.setEnabled(true);
        mobile.setEnabled(true);
        address.setEnabled(true);
        save.setVisibility(View.VISIBLE);
    }*/

}
