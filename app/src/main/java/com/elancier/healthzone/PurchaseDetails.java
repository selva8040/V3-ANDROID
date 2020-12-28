package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.PurchaseDetailListAdapter;
import com.elancier.healthzone.Adapter.PurchaseDetailPopupListAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.AutofillPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PurchaseDetails extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, reports_lay, ibv_reports_lay;
    ArrayList<AutofillPojo> data;
    ArrayList<AutofillPojo> subdata;
    AutofillPojo pojo;
    ListView listView,sublist;
    PurchaseDetailListAdapter adapter;
    PurchaseDetailPopupListAdapter adap;

    ImageView reports_img, search_img;
    boolean filtercheck = false;
    EditText from_date, to_date;
    LinearLayout filter_lay;

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView select, cancel;

    Dialog dialog;
    ImageView back_arrow;
    TextView add_title, hsn,  uname, pan_no, name, store, amount,
            bv, tax, method;

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;
    int start = 0;
    int limit = 2;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    Utils utils;
    String Uname = "";
    String Details = "";
    String fromdate = "", todate = "";
    int menuvalue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());
        Uname = utils.loadName();
        init();

        Log.i("INTENT",getIntent().toString());

        start = 0;
        limit = 20;
        scrollNeed = true;

        progress_lay.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        GetinfoTask task = new GetinfoTask();
        task.execute();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lay.setVisibility(View.GONE);
                progress_lay.setVisibility(View.VISIBLE);
                adapter = null;
                start = 0;
                limit = 20;
                data = new ArrayList<>();
                GetinfoTask task = new GetinfoTask();
                task.execute();
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

                  /* if (searchname.trim().length() > 0) {
                        paging_lay.setVisibility(View.VISIBLE);
                        GetUsersTask task = new GetUsersTask();
                        task.execute(searchname,From,To);

                    } else {*/
                    paging_lay.setVisibility(View.VISIBLE);
                    GetinfoTask task = new GetinfoTask();
                    task.execute();
                   /* }*/

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

        onclick();

    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        setclick(drawer_layout, drawerLayout, PurchaseDetails.this);

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);

        auto_fill_lay.setVisibility(View.VISIBLE);
        autofill_img.setImageResource(R.mipmap.up_arrow);
        purchase_detail_lay.setBackgroundColor(getResources().getColor(R.color.eee));

        listView = (ListView) findViewById(R.id.listView);
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

    private void onclick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Detail(data.get(position));
            }
        });

    }

    private void Detail(AutofillPojo pojo) {

        dialog = new Dialog(PurchaseDetails.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.purchase_detail_popup, null);

        back_arrow = (ImageView) popup.findViewById(R.id.back_arrow);
        add_title = (TextView) popup.findViewById(R.id.title);
        sublist = (ListView) popup.findViewById(R.id.listView);

        Details = pojo.getDetails();
        subdata = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(Details);
            Log.i("All", Details);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                AutofillPojo bo = new AutofillPojo();

                //pojo.setId(jsonObject1.getString("id").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("id"));
                bo.setPname(jsonObject1.getString("pname").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("pname"));
                bo.setMrp(jsonObject1.getString("price").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("price"));
                bo.setQty(jsonObject1.getString("qty").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("qty"));
                bo.setTotal(jsonObject1.getString("total").toString().trim().equalsIgnoreCase("null") ? "0" : jsonObject1.getString("total"));

                subdata.add(bo);
            }

            adap = new PurchaseDetailPopupListAdapter(PurchaseDetails.this, R.layout.purchase_detail_list_items, subdata);
            sublist.setAdapter(adap);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.setContentView(popup);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.WHITE));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);
        return true;
    }*/

    private class GetinfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            Log.i("GetInfoTask", "started");
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            Connection con = new Connection();

            try {
                JSONObject json = new JSONObject();

                json.put("uname", Uname);
                json.put("start", start);
                json.put("limit", limit);

                Log.i("utilsInput", Appconstants.PURCHASE_DETAILS + "    " + json.toString());
                result = con.sendHttpPostjson2(Appconstants.PURCHASE_DETAILS, json, "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            Log.i("utilsresp", resp + "");
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
                            pojo = new AutofillPojo();

                            // pojo.setData(jsonObject1.toString());
                            pojo.setDate(jsonObject1.getString("dat").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("dat"));
                            pojo.setSid(jsonObject1.getString("sid").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("sid"));
                            //pojo.setBid(jsonObject1.getString("name").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("name"));
                            pojo.setBname(jsonObject1.getString("store").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("store"));
                            pojo.setAmount(jsonObject1.getString("total").toString().trim().equalsIgnoreCase("") || jsonObject1.getString("total").toString().trim().equalsIgnoreCase("null") ? "0" : jsonObject1.getString("total"));
                            pojo.setDetails(jsonObject1.getString("details").toString().trim().equalsIgnoreCase("")  ? "0" : jsonObject1.getString("details"));
                            data.add(pojo);
                        }

                        if (adapter == null) {
                            adapter = new PurchaseDetailListAdapter(PurchaseDetails.this, R.layout.purchasedetail_list_items, data);
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
                            Toast.makeText(getApplicationContext(), "No More Purchase.", Toast.LENGTH_SHORT).show();
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                            //Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                        }
                        retry_lay.setVisibility(View.GONE);
                    }

                } else {
                    if (start == 0) {
                        retry_lay.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                if (start == 0) {
                    retry_lay.setVisibility(View.GONE);
                    nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No More Purchase.", Toast.LENGTH_SHORT).show();

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

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
