package com.elancier.healthzone;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Adapter.CustomerListAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Customers extends AppCompatActivity {

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;

    ListView listView;
    ArrayList<ReportsPojo> data;
    CustomerListAdapter adapter;
    ReportsPojo pojo;
    TextView search;
    String Store = "";
    String Stores = "";
    int start = 0;
    int limit = 20;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    Utils utils;
    String Uname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Customers");
        utils = new Utils(this);
        Uname = utils.loadName();
        //  Store = getIntent().getExtras().getString("Email");

        init();

        start = 0;
        limit = 20;
        scrollNeed = true;
        progress_lay.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        GetUsersTask task = new GetUsersTask();
        task.execute(Uname);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lay.setVisibility(View.GONE);
                progress_lay.setVisibility(View.VISIBLE);
                adapter = null;
                data = new ArrayList<>();
                GetUsersTask task = new GetUsersTask();
                task.execute(Uname);
            }
        });
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollValue && scrollNeed && (data.size() > 0)) {
                    scrollNeed = false;
                    listView.setPadding(0, 0, 0, px);
                    paging_lay.setVisibility(View.VISIBLE);

                    GetUsersTask task = new GetUsersTask();
                    task.execute(Uname);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = firstVisibleItem + visibleItemCount;
                if (totalItemCount == count)
                    scrollValue = true;
                else
                    scrollValue = false;
            }
        });

    }

    private void init() {

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);
        listView = (ListView) findViewById(R.id.listView);

        data = new ArrayList<>();
    }


    private class GetUsersTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progress_lay.setVisibility(View.GONE);
            Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                 jobj.put("uname", param[0]);
                jobj.put("start", start + "");
                jobj.put("limit", limit + "");
                result = con.sendHttpPostjson2(Appconstants.CUSTOMER_LIST, jobj, "");
                Log.i("utilsssssInput", Appconstants.CUSTOMER_LIST + "    " + jobj.toString());
                //result = con.sendHttpPostjson2(Appconstants.USERS, jobj, "");
                // Log.i("result", result + " ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("userlist", resp + "");
            progress_lay.setVisibility(View.GONE);
            listView.setPadding(0, 0, 0, 0);
            paging_lay.setVisibility(View.GONE);
            scrollNeed = true;


            try {
                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {

                        JSONArray jsonArray1 = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray1.length(); i++) {

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                            pojo = new ReportsPojo();

                            pojo.setUserid(jsonObject1.getString("cusid").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("cusid"));
                            pojo.setDate(jsonObject1.getString("doj").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("doj"));
                            pojo.setName(jsonObject1.getString("name").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("name"));
                            pojo.setMobile(jsonObject1.getString("mobile").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("mobile"));

                            data.add(pojo);
                        }
                       /* adapter = new UserListAdapter(Users.this, R.layout.users_list_item, data);
                        listView.setAdapter(adapter);*/

                        if (adapter == null) {
                            adapter = new CustomerListAdapter(Customers.this, R.layout.cutomer_list_item, data);
                            listView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (data.size() > 0) {
                            nodata.setVisibility(View.GONE);
                            start = start + 20;
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (data.size() > 0) {

                            nodata.setVisibility(View.GONE);

                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                        retry_lay.setVisibility(View.GONE);
                    }
                } else {
                    if (start == 0) {
                        retry_lay.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                        /*Something went wrong please try again.*/
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (start == 0) {
                    //retry_lay.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No More Data.", Toast.LENGTH_SHORT).show();

                }

            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
