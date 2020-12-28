package com.elancier.healthzone;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class Live extends MainView {

    Utils utils;
    String Uname = "";
    String Res = "";

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;
    TextView plan, posi, run_posi;
    LinearLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());
        Uname = utils.loadName();

        //Log.i("INTENT", getIntent().toString());

        init();

        progress_lay.setVisibility(View.VISIBLE);
        GetDashTask task = new GetDashTask();
        task.execute();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lay.setVisibility(View.GONE);
                progress_lay.setVisibility(View.VISIBLE);
                // adapter = null;
                // start = 0;
                //limit = 20;
                // data = new ArrayList<>();
                GetDashTask task = new GetDashTask();
                task.execute();
            }
        });
    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        setclick(drawer_layout, drawerLayout, Live.this);

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);

        auto_fill_lay.setVisibility(View.VISIBLE);
        autofill_img.setImageResource(R.mipmap.up_arrow);
        live_lay.setBackgroundColor(getResources().getColor(R.color.eee));

        plan = (TextView) findViewById(R.id.plan);
        posi = (TextView) findViewById(R.id.posi);
        run_posi = (TextView) findViewById(R.id.run_posi);

    }

    private class GetDashTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progress_lay.setVisibility(View.GONE);
            //Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", Uname);

                result = con.sendHttpPostjson2(Appconstants.LIVE_DETAIL, jobj, "");
                //Log.i("LiveInput", Appconstants.LIVE_DETAIL + "    " + jobj.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
          //  Log.i("Dash", resp + "");
            progress_lay.setVisibility(View.GONE);


            try {
                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    // JSONObject jsonObject = new JSONObject();

                    if (jsonObject.getString("Status").equals("Success")) {

                        if (!jsonObject.isNull("Response")) {


                                JSONArray jsonArray1 = jsonObject.getJSONArray("Response");
                                for (int i = 0; i < jsonArray1.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                                    plan.setText(jsonObject1.getString("plan").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("plan"));
                                    posi.setText(jsonObject1.getString("Yourpositions").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("Yourpositions"));
                                    run_posi.setText(jsonObject1.getString("Runningpositions").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("Runningpositions"));
                                    //completed.setText(jsonObject1.getString("complete").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("complete"));

                                }
                        }else {
                            //nodata.setVisibility(View.VISIBLE);
                        }

                    } else {

                    }
                } else {
                    retry_lay.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }

        }
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

         super.onBackPressed();

    }
}

