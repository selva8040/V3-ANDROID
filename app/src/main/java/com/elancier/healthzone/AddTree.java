package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddTree extends AppCompatActivity {

    Utils utils;
    String Uname = "";
    String Store = "";
    String Type = "";
    String Pcode_Status = "0";
    String Pin_Status = "0";

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;

    EditText name, mobile, sponsor_unique, sponsor_uname, uname, sponsor_name, unique, pin,store,pin_type;
    TextView submit, pin_error, pcode_error;
    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView cancel, select, save, gender_error;
    Dialog progbar;

    String Spon_Unique = "";
    String Spon_Uname = "";
    String Spon_Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tree);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());
        Uname = utils.loadName();
        getSupportActionBar().setTitle("Add New User");

        Spon_Unique = getIntent().getExtras().getString("spon_unique");
        Spon_Uname = getIntent().getExtras().getString("spon_uname");
        Spon_Name = getIntent().getExtras().getString("spon_name");

        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);

        inits();

        onclick();
    }

    private void inits() {

       /* progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);*/

        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        sponsor_unique = (EditText) findViewById(R.id.sponsor_unique);
        sponsor_uname = (EditText) findViewById(R.id.sponsor_uname);
        uname = (EditText) findViewById(R.id.uname);
        sponsor_name = (EditText) findViewById(R.id.sponsor_name);
        unique = (EditText) findViewById(R.id.unique);
        pin = (EditText) findViewById(R.id.pin);
        store = (EditText) findViewById(R.id.store);
        pin_type = (EditText) findViewById(R.id.pin_type);
        submit = (TextView) findViewById(R.id.submit);
        pin_error = (TextView) findViewById(R.id.pin_error);
        pcode_error = (TextView) findViewById(R.id.pcode_error);

        sponsor_unique.setText(Spon_Unique);
        sponsor_uname.setText(Spon_Uname);
        sponsor_name.setText(Spon_Name);


    }

    private void onclick() {


        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (uname.getText().toString().trim().length() > 0) {

                        progbar.show();
                        GetPcodeTask task = new GetPcodeTask();
                        task.execute(uname.getText().toString().trim());

                    }
                }
            }
        });
        pin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (pin.getText().toString().trim().length() > 0) {

                        progbar.show();
                        GetPinTask task = new GetPinTask();
                        task.execute(pin.getText().toString().trim());

                    }
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submit.getText().toString().trim().equalsIgnoreCase("Submit")) {


                    if (uname.getText().toString().trim().length() <= 0) {
                        pcode_error.setVisibility(View.VISIBLE);
                    }else {
                        pcode_error.setVisibility(View.VISIBLE);
                    }

                    if (pin.getText().toString().trim().length() <= 0) {
                        pin_error.setVisibility(View.VISIBLE);
                    }else {
                        pin_error.setVisibility(View.VISIBLE);
                    }


                    if (uname.getText().toString().trim().length() > 0 && pin.getText().toString().trim().length() > 0) {

                        progbar.show();
                        GetPcodeTask task = new GetPcodeTask();
                        task.execute(uname.getText().toString().trim());
                        progbar.show();
                        GetPinTask task1 = new GetPinTask();
                        task1.execute(pin.getText().toString().trim());

                       // Log.i("VIP","Boolean Check   "+Pin_Status+"    "+Pin_Status);

                        if (Pin_Status.equalsIgnoreCase("1") && Pin_Status.equalsIgnoreCase("1")) {

                            progbar.show();
                            submit.setText("Processing");
                            AddVipTask task2 = new AddVipTask();
                            task2.execute(pin.getText().toString().trim(), unique.getText().toString().trim(), Store, uname.getText().toString().trim(),
                                    sponsor_unique.getText().toString().trim(), name.getText().toString().trim(), mobile.getText().toString().trim());

                        }else {

                            if (Pcode_Status.equalsIgnoreCase("0")) {
                                pcode_error.setVisibility(View.VISIBLE);
                            } else {
                                pcode_error.setVisibility(View.GONE);

                            }

                            if (Pin_Status.equalsIgnoreCase("0")) {
                                pin_error.setVisibility(View.VISIBLE);
                            } else {
                                pin_error.setVisibility(View.GONE);

                            }
                        }

                       /* Log.i("VIP",sponsor_uname.getText().toString().trim()+"  "+name.getText().toString().trim()+"  "+mobile.getText().toString().trim()
                                +"  "+sponsor_unique.getText().toString().trim()+"  "+sponsor_name.getText().toString().trim());*/

                    }
                }
            }
        });

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

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

       /* if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {*/
        super.onBackPressed();
        //}
    }

    private class GetPcodeTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            //Log.i("VIP Task", "started");
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            Connection con = new Connection();

            try {
                JSONObject json = new JSONObject();
                json.put("type", "Username");
                json.put("username", params[0]);

                //Log.i("VIP Input", Appconstants.STARTUP + "    " + json.toString());
                result = con.sendHttpPostjson2(Appconstants.STARTUP, json, "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
           // Log.i("VIP Resp", resp + "");
            // progress_lay.setVisibility(View.GONE);
            progbar.dismiss();

            try {

                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {

                        if (!jsonObject.isNull("Response") && !jsonObject.getString("Response").trim().equalsIgnoreCase("")) {

                            JSONArray jsonArray1 = jsonObject.getJSONArray("Response");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                            name.setText(jsonObject1.getString("name"));
                            unique.setText(jsonObject1.getString("unique"));
                            mobile.setText(jsonObject1.getString("mobile"));
                            Pcode_Status = "1";


                        } else {
                            //progress_lay.setVisibility(View.GONE);
                            progbar.dismiss();
                            name.setText("");
                            mobile.setText("");
                            unique.setText("");
                            Pcode_Status = "0";
                            pcode_error.setVisibility(View.VISIBLE);
                        }

                    } else {
                        //progress_lay.setVisibility(View.GONE);
                        progbar.dismiss();
                        name.setText("");
                        mobile.setText("");
                        unique.setText("");
                        Pcode_Status = "0";
                        pcode_error.setVisibility(View.VISIBLE);
                    }

                } else {
                    //progress_lay.setVisibility(View.GONE);
                    progbar.dismiss();
                    name.setText("");
                    mobile.setText("");
                    unique.setText("");
                    Pcode_Status = "0";
                    pcode_error.setVisibility(View.VISIBLE);
                    Toast.makeText(AddTree.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                //progress_lay.setVisibility(View.GONE);
                progbar.dismiss();
                name.setText("");
                mobile.setText("");
                unique.setText("");
                Pcode_Status = "0";
                pcode_error.setVisibility(View.VISIBLE);
                Toast.makeText(AddTree.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetPinTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
           // Log.i("VIP Task", "started");
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            Connection con = new Connection();

            try {
                JSONObject json = new JSONObject();
                json.put("type", "Pin Check");
                json.put("pinno", params[0]);

               // Log.i("VIP Input", Appconstants.STARTUP + "    " + json.toString());
                result = con.sendHttpPostjson2(Appconstants.STARTUP, json, "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            //Log.i("VIP Resp", resp + "");
            // progress_lay.setVisibility(View.GONE);
            progbar.dismiss();

            try {

                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {

                        if (!jsonObject.isNull("Response") && !jsonObject.getString("Response").trim().equalsIgnoreCase("")) {

                            JSONArray jsonArray1 = jsonObject.getJSONArray("Response");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                            Store = (jsonObject1.getString("store"));
                            Type = (jsonObject1.getString("type"));
                            store.setText(jsonObject1.getString("store"));
                            pin_type.setText(jsonObject1.getString("type"));
                            Pin_Status = "1";


                        } else {
                            //progress_lay.setVisibility(View.GONE);
                            progbar.dismiss();
                            pin_error.setVisibility(View.VISIBLE);
                            Pin_Status = "0";
                            store.setText("");
                            pin_type.setText("");
                        }

                    } else {
                        //progress_lay.setVisibility(View.GONE);
                        progbar.dismiss();
                        Pin_Status = "0";
                        store.setText("");
                        pin_type.setText("");
                        pin_error.setVisibility(View.VISIBLE);
                    }

                } else {
                    //progress_lay.setVisibility(View.GONE);
                    progbar.dismiss();
                    pin_error.setVisibility(View.VISIBLE);
                    Pin_Status = "0";
                    store.setText("");
                    pin_type.setText("");
                    Toast.makeText(AddTree.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                //progress_lay.setVisibility(View.GONE);
                progbar.dismiss();
                Pin_Status = "0";
                store.setText("");
                pin_type.setText("");
                pin_error.setVisibility(View.VISIBLE);
                Toast.makeText(AddTree.this, "Please check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class AddVipTask extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
           // Log.i("OrderSave", "started");
        }

        @Override
        protected String doInBackground(final String... arg0) {
            String resp = null;


            try {
                JSONObject obj = new JSONObject();
                obj.put("type", "Add New");
                obj.put("pinno", arg0[0]);
                obj.put("unique", arg0[1]);
                obj.put("store", arg0[2]);
                obj.put("username", arg0[3]);
                obj.put("sponsor", arg0[4]);
                obj.put("name", arg0[5]);
                obj.put("mobile", arg0[6]);

                Connection con = new Connection();
                //Log.i("VIP Input", Appconstants.STARTUP + "     " + obj.toString());
                resp = con.sendJsonHttpPost(Appconstants.STARTUP, obj).toString();


                return resp;
            } catch (Exception e1) {
                e1.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resp) {
            //Log.i("VIP Res", "ended" + resp);
            progbar.dismiss();
            // progress_lay.setVisibility(View.GONE);
            submit.setText("Submit");
            try {
                if (resp != null) {
                    JSONArray array = new JSONArray(resp);
                    JSONObject obj = array.getJSONObject(0);

                    if (obj.getString("Status").equals("Success")) {
                        String Resp = obj.getString("Response");
                        Toast.makeText(AddTree.this, Resp, Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        progbar.dismiss();
                        Toast.makeText(AddTree.this, "Failed to create VIP member", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progbar.dismiss();
                    Toast.makeText(AddTree.this, "Oops something went wrong,please try again", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progbar.dismiss();
                Toast.makeText(AddTree.this, "Please check your network connection", Toast.LENGTH_SHORT).show();
            }
            // resp is: {"name":"-KTryoFeoZ4Sv8CcPB_A"}
        }
    }
}
