package com.elancier.healthzone;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HP PC on 21-07-2017.
 */

public class AddCustomerActivity extends MainView {

    LinearLayout newuser;
    LinearLayout olduser;
    DrawerLayout drawerLayout;
    Dialog dialog, dialog1;
    LinearLayout drawer_layout;
    EditText name;
    EditText address;
    EditText mobile;
    EditText city;
    EditText pincode;
    TextView submit;
    String MobilePattern = "[0-9]{10}";
    String NamePattern = "[a-zA-Z. ]+";
    TextView desctext;
    TextView ok;
    int menuvalue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        init();
        loadprogress();
        olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCustomerActivity.this, SalesPoint.class);
                startActivity(intent);
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addcustomer();
            }
        });
    }

    private void Addcustomer() {

        dialog = new Dialog(AddCustomerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.new_customer, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        submit = (TextView) popup.findViewById(R.id.submit);

        name = (EditText) popup.findViewById(R.id.customer_name);
        mobile = (EditText) popup.findViewById(R.id.mobile);
        address = (EditText) popup.findViewById(R.id.customer_address);
        city = (EditText) popup.findViewById(R.id.customer_city);
        pincode = (EditText) popup.findViewById(R.id.customer_pin);
        submit = (TextView) popup.findViewById(R.id.submit);

        dialog.setContentView(popup);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() <= 0) {
                    name.setError("Enter name");
                }

                if (name.getText().toString().length() > 0 && !name.getText().toString().matches(NamePattern)) {
                    name.setError("Enter characters only");
                }

                if (mobile.getText().toString().length() <= 0) {
                    mobile.setError("Enter mobile number");
                }

                if (mobile.getText().toString().length() > 0 && !mobile.getText().toString().matches(MobilePattern)) {
                    mobile.setError("Enter valid  mobile number");
                }
                if (address.getText().toString().length() <= 0) {
                    address.setError("Enter address");
                }

                if (city.getText().toString().length() <= 0) {
                    city.setError("Enter city");
                }

                if (pincode.getText().toString().length() <= 0) {
                    pincode.setError("Enter pincode");
                }

                if (pincode.getText().toString().length() > 0 && pincode.getText().toString().trim().length() != 6) {
                    pincode.setError("Enter valid pincode");
                }
                if (name.getText().toString().length() > 0 && mobile.getText().toString().matches(MobilePattern) && address.getText().toString().length() > 0 &&
                        city.getText().toString().length() > 0 && pincode.getText().toString().trim().length() == 6) {
                    startprogress();
                    AddTask task = new AddTask();
                    task.execute(name.getText().toString().trim(), mobile.getText().toString().trim(), address.getText().toString().trim(), city.getText().toString().trim(), pincode.getText().toString().trim());
                } else {

                    Toast.makeText(AddCustomerActivity.this, "Please Fill Above Details", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        olduser = (LinearLayout) findViewById(R.id.oldcus);
        newuser = (LinearLayout) findViewById(R.id.newcus);

        setclick(drawer_layout, drawerLayout, AddCustomerActivity.this);
        //sales_point_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                menuvalue = 1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                menuvalue = 0;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void successdialog(String s) {

        dialog1 = new Dialog(AddCustomerActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.success, null);

        desctext = (TextView) popup.findViewById(R.id.desctext);
        ok = (TextView) popup.findViewById(R.id.ok);


        dialog1.setContentView(popup);
        dialog1.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog1.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog1.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        dialog1.getWindow().setLayout(500, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog1.show();

        desctext.setText("Your Customer id is " + s);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                //dialog.dismiss();
                startActivity(new Intent(AddCustomerActivity.this, SalesPoint.class));
            }
        });

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
            if (menuvalue == 0)
                onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private class AddTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("AddTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("type", "new");
                jobj.put("name", param[0]);
                jobj.put("mobile", param[1]);
                jobj.put("address", param[2]);
                jobj.put("city", param[3]);
                jobj.put("pincode", param[4]);
                jobj.put("user", utils.loadName());


                Log.i("check Input", Appconstants.ADD_NEW_CUSTOMER + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.ADD_NEW_CUSTOMER, jobj, "");

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

                        Toast.makeText(AddCustomerActivity.this, obj1.getString("Response"), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        //JSONArray jarr = obj1.getJSONArray("Response");
                        //JSONObject jobj = jarr.getJSONObject(0);
                        //successdialog(jobj.getString("id"));


                    } else {
                        if (obj1.getString("Response").equalsIgnoreCase("Mobile No already Exits")) {
                            Toast.makeText(getApplicationContext(), "Mobile No already Exits.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Faile to add.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    // retry.show();
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                // retry.show();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


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
