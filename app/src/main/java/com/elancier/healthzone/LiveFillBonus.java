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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.LiveBonusListAdapter;
import com.elancier.healthzone.Adapter.PurchaseDetailPopupListAdapter;
import com.elancier.healthzone.Adapter.TypeSpinnerAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.AutofillPojo;
import com.elancier.healthzone.Pojo.SpinnerPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LiveFillBonus extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, reports_lay, ibv_reports_lay;
    ArrayList<AutofillPojo> data;
    ArrayList<AutofillPojo> subdata;
    AutofillPojo pojo;
    ListView listView;
    LiveBonusListAdapter adapter;
    PurchaseDetailPopupListAdapter adap;

    ImageView reports_img, search_img;
    boolean filtercheck = false;
    EditText from_date, to_date;
    int menuvalue=0;
    LinearLayout filter_lay;
    Spinner type_spin;
    ArrayList<SpinnerPojo> type_data;
    TypeSpinnerAdapter type_adap;

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView select, cancel;

    Dialog dialog;
    ImageView back_arrow;
    TextView add_title, submit, type_error;
    LinearLayout bank_lay;
    EditText bank_name, branch, acc_num, ifsc;
    String[] types = {"Type", "Online", "Bank"};

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;
    int start = 0;
    int limit = 2;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    Utils utils;
    String Uname = "";
    String Update = "";
    String ID = "";
    String Details = "";
    String fromdate = "", todate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_fill_bonus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());
        Uname = utils.loadName();

        Log.i("INTENT",getIntent().toString());

        init();

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
        setclick(drawer_layout, drawerLayout, LiveFillBonus.this);

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);

        auto_fill_lay.setVisibility(View.VISIBLE);
        autofill_img.setImageResource(R.mipmap.up_arrow);
        livefill_bonus_lay.setBackgroundColor(getResources().getColor(R.color.eee));

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

        dialog = new Dialog(LiveFillBonus.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.live_fill_popup, null);

        back_arrow = (ImageView) popup.findViewById(R.id.back_arrow);
        add_title = (TextView) popup.findViewById(R.id.title);
        type_error = (TextView) popup.findViewById(R.id.type_error);
        bank_lay = (LinearLayout) popup.findViewById(R.id.bank_lay);
        submit = (TextView) popup.findViewById(R.id.submit);
        bank_name = (EditText) popup.findViewById(R.id.bank_name);
        branch = (EditText) popup.findViewById(R.id.branch);
        acc_num = (EditText) popup.findViewById(R.id.acc_num);
        ifsc = (EditText) popup.findViewById(R.id.ifsc);
        type_spin = (Spinner) popup.findViewById(R.id.type_spin);

        if (pojo.getStatus().equals("0")){
            type_spin.setEnabled(true);
            type_spin.setBackgroundResource(R.drawable.grey_stroke_bg);
            submit.setVisibility(View.VISIBLE);
        }else{
            type_spin.setEnabled(false);
            type_spin.setBackgroundResource(R.drawable.grey_bg1);
            submit.setVisibility(View.GONE);

        }

        type_data = new ArrayList<>();

        for (int i = 0; i < types.length; i++) {
            SpinnerPojo spin_pojo = new SpinnerPojo();
            spin_pojo.setType(types[i]);
            type_data.add(spin_pojo);
        }
        type_adap = new TypeSpinnerAdapter(LiveFillBonus.this, R.layout.type_spinner_list_item, type_data);
        type_spin.setAdapter(type_adap);

        type_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (type_spin.getSelectedItemPosition() == 0) {
                    //Toast.makeText(LiveFillBonus.this, "0", Toast.LENGTH_SHORT).show();
                    bank_lay.setVisibility(View.GONE);
                } else if (type_spin.getSelectedItemPosition() == 1) {
                    // Toast.makeText(LiveFillBonus.this, "1", Toast.LENGTH_SHORT).show();
                    bank_lay.setVisibility(View.GONE);
                } else if (type_spin.getSelectedItemPosition() == 2) {
                    // Toast.makeText(LiveFillBonus.this, "2", Toast.LENGTH_SHORT).show();
                    bank_lay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bank_name.setText(pojo.getBankname());
        branch.setText(pojo.getBranch());
        acc_num.setText(pojo.getAccno());
        ifsc.setText(pojo.getIfsc());
        ID = pojo.getId();
        Update = "Update";

        for (int i = 0; i < type_data.size(); i++) {
            Log.i("UOMspin", type_data.get(i).getType() + "  " + pojo.getType());
            if (type_data.get(i).getType().equals(pojo.getType())) {
                type_spin.setSelection(i);
            }
        }

        dialog.setContentView(popup);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submit.getText().toString().trim().equalsIgnoreCase("Submit")) {
                    if (type_data.get(type_spin.getSelectedItemPosition()).getType().equals("Type")) {

                        type_spin.setBackgroundResource(R.drawable.red_stroke_bg);
                        type_error.setVisibility(View.VISIBLE);
                    } else {
                        type_spin.setBackgroundResource(R.drawable.grey_stroke_bg);
                        type_error.setVisibility(View.GONE);
                    }

                    if (!type_data.get(type_spin.getSelectedItemPosition()).getType().equals("Type")) {
                        submit.setText("Processing . . .");
                        UpdateTask task = new UpdateTask();
                        task.execute(Uname, type_data.get(type_spin.getSelectedItemPosition()).getType(), ID, Update);
                    }
                } else {

                }

            }
        });

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
                json.put("action", "Select");
                json.put("uname", Uname);
                json.put("start", start);
                json.put("limit", limit);

                Log.i("utilsInput", Appconstants.LIVE_BONUS + "    " + json.toString());
                result = con.sendHttpPostjson2(Appconstants.LIVE_BONUS, json, "");
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
                            pojo.setId(jsonObject1.getString("id").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("id"));
                            pojo.setDate(jsonObject1.getString("date").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("date"));
                            pojo.setDatetime(jsonObject1.getString("datetime").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("datetime"));
                            pojo.setLavel(jsonObject1.getString("level").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("level"));
                            pojo.setBonus(jsonObject1.getString("bonus").toString().trim().equalsIgnoreCase("") || jsonObject1.getString("bonus").toString().trim().equalsIgnoreCase("null") ? "0" : jsonObject1.getString("bonus"));
                            pojo.setNetamt(jsonObject1.getString("netamt").toString().trim().equalsIgnoreCase("") || jsonObject1.getString("netamt").toString().trim().equalsIgnoreCase("null") ? "0" : jsonObject1.getString("netamt"));
                            pojo.setTds(jsonObject1.getString("tds").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("tds"));
                            pojo.setBankname(jsonObject1.getString("bank").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("bank"));
                            pojo.setBranch(jsonObject1.getString("branch").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("branch"));
                            pojo.setAccno(jsonObject1.getString("accountno").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("accountno"));
                            pojo.setIfsc(jsonObject1.getString("ifsccode").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("ifsccode"));
                            pojo.setType(jsonObject1.getString("type").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("type"));
                            pojo.setStatus(jsonObject1.getString("status").toString().trim().equalsIgnoreCase("") ? "" : jsonObject1.getString("status"));
                            data.add(pojo);
                        }

                        if (adapter == null) {
                            adapter = new LiveBonusListAdapter(LiveFillBonus.this, R.layout.live_fill_list_items, data);
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
                            Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
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

    private class UpdateTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("AddProductTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", param[0]);
                jobj.put("type", param[1]);
                jobj.put("id", param[2]);
                jobj.put("action", param[3]);

                result = con.sendHttpPostjson2(Appconstants.LIVE_BONUS, jobj, "");
                Log.i("updateResult", Appconstants.LIVE_BONUS + "    " + jobj.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("utilssaddcat", resp + "");
            submit.setText("Submit");


            try {
                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Updated successfully..", Toast.LENGTH_SHORT).show();
                        start = 0;
                        limit = 20;
                        data = new ArrayList<>();
                        adapter = null;
                       // startprogress();
                        progress_lay.setVisibility(View.VISIBLE);
                        GetinfoTask task = new GetinfoTask();
                        task.execute();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed Update.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();

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
