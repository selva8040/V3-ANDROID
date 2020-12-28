package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.ReportsAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class IbvReports extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, reports_lay, ibv_reports_lay;
    ArrayList<ReportsPojo> data;
    ReportsPojo pojo;
    ListView listView;
    ReportsAdapter adapter;
    String[] name = {"RamKumar", "Senthil", "Prabakaran", "Rahavan", "Sathis", "Sonthosh"};

    ImageView reports_img, search_img;
    boolean filtercheck = false;
    EditText from_date, to_date;
    LinearLayout filter_lay;

    Dialog picker;
    DatePicker datep;
    String date, mobilenum, cdate, time, date1, time1;
    Integer hour, minute, month, day, year, hour1, minute1, month1, day1, year1;
    TextView select, cancel;
    Utils utils;
    int start;
    int limit;
    int dateval = 0;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    LinearLayout loadlay;
    Dialog retry;
    ImageView retrybut;
    TextView nodata;
    String fromdate = "", todate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibv_reports);
        utils = new Utils(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);

        init();

        onclick();

        scrollNeed = true;
        start = 0;
        limit = 20;
        loadprogress();
        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v = getLayoutInflater().inflate(R.layout.retrylay, null);
        retrybut = (ImageView) v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        startprogress();
        GetInfoTask task = new GetInfoTask();
        task.execute(fromdate, todate);

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();
                startprogress();
                GetInfoTask task = new GetInfoTask();
                task.execute(fromdate, todate);
            }
        });

        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (scrollValue && scrollNeed && (data.size() > 0)) {
                    scrollNeed = false;
                    listView.setPadding(0, 0, 0, px);


                    if (from_date.getText().toString().trim().length() > 0 && to_date.getText().toString().trim().length() > 0) {
                        loadlay.setVisibility(View.VISIBLE);
                        GetInfoTask task = new GetInfoTask();
                        task.execute(fromdate, todate);

                    } else {
                        if (dateval > 0) {
                            adapter = null;
                            data = new ArrayList<ReportsPojo>();
                            adapter = new ReportsAdapter(IbvReports.this, R.layout.ibv_items, data);
                            listView.setAdapter(adapter);
                            start = 0;
                            limit = 20;
                            fromdate = from_date.getText().toString().trim();
                            todate = to_date.getText().toString().trim();
                            startprogress();
                            dateval = 0;
                        } else {
                            loadlay.setVisibility(View.VISIBLE);
                        }
                        GetInfoTask task = new GetInfoTask();
                        task.execute(fromdate, todate);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
                int count = arg1 + arg2;
                if (arg3 == count)
                    scrollValue = true;
                else
                    scrollValue = false;
            }
        });
    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        setclick(drawer_layout, drawerLayout, IbvReports.this);
        reports_lay = (LinearLayout) findViewById(R.id.reports_lay);

        filter_lay = (LinearLayout) findViewById(R.id.filter_lay);
        reports_img = (ImageView) findViewById(R.id.reports_img);
        search_img = (ImageView) findViewById(R.id.search_img);
        from_date = (EditText) findViewById(R.id.from_date);
        to_date = (EditText) findViewById(R.id.to_date);
        loadlay = (LinearLayout) findViewById(R.id.play);
        nodata = (TextView) findViewById(R.id.nodata);


        ibv_reports_lay = (LinearLayout) findViewById(R.id.ibv_reports_lay);
        reports_lay.setVisibility(View.VISIBLE);
        reports_img.setImageResource(R.mipmap.up_arrow);
        ibv_reports_lay.setBackgroundColor(getResources().getColor(R.color.eee));

        listView = (ListView) findViewById(R.id.listView);
        data = new ArrayList<ReportsPojo>();
       /* for (int i = 0; i< name.length; i++){

            pojo = new ReportsPojo();

            Log.i("itemnameqqqqq",name[i]+"");

            pojo.setUsername(name[i]);

            data.add(pojo);
        }*/

    }

    private void onclick() {

        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new Dialog(IbvReports.this);
                picker.setContentView(R.layout.datepicker);
                picker.setTitle("Select Date ");
                datep = (DatePicker) picker.findViewById(R.id.datePicker);
                datep.setSpinnersShown(true);
                datep.setCalendarViewShown(false);
                datep.setMaxDate(System.currentTimeMillis());
                cancel = (TextView) picker.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        picker.dismiss();
                    }
                });

                if (from_date.getText().toString().trim().length() > 0) {
                    String s[] = from_date.getText().toString().trim().split("-");
                  //  Log.i("validddddd date", Integer.parseInt(s[2]) + "     " + Integer.parseInt(s[1]) + "    " + Integer.parseInt(s[0]));
                    datep.updateDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]) - 1, Integer.parseInt(s[1]));

                }

                select = (TextView) picker.findViewById(R.id.select);
                select.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        month = datep.getMonth() + 1;
                        day = datep.getDayOfMonth();
                        year = datep.getYear();
                        // hour = timep.getCurrentHour();
                        // minute = timep.getCurrentMinute();
                        date = year + "-" + month + "-" + day;
                        // time = hour + ":" + minute;
                        // Toast.makeText(view.getContext(), date , Toast.LENGTH_LONG).show();
                        from_date.setText(date);
                        picker.dismiss();

                    }
                });
                picker.show();

            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new Dialog(IbvReports.this);
                picker.setContentView(R.layout.datepicker);
                picker.setTitle("Select Date ");
                datep = (DatePicker) picker.findViewById(R.id.datePicker);
                datep.setSpinnersShown(true);
                datep.setCalendarViewShown(false);
                datep.setMaxDate(System.currentTimeMillis());
                cancel = (TextView) picker.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        picker.dismiss();
                    }
                });
                if (to_date.getText().toString().trim().length() > 0) {
                    String s[] = to_date.getText().toString().trim().split("-");
                    //Log.i("validddddd date", Integer.parseInt(s[2]) + "     " + Integer.parseInt(s[1]) + "    " + Integer.parseInt(s[0]));
                    datep.updateDate(Integer.parseInt(s[0]), Integer.parseInt(s[1]) - 1, Integer.parseInt(s[2]));

                }

                select = (TextView) picker.findViewById(R.id.select);
                select.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        month = datep.getMonth() + 1;
                        day = datep.getDayOfMonth();
                        year = datep.getYear();
                        // hour = timep.getCurrentHour();
                        // minute = timep.getCurrentMinute();
                        date = year + "-" + month + "-" + day;
                        // time = hour + ":" + minute;
                        // Toast.makeText(view.getContext(), date , Toast.LENGTH_LONG).show();
                        to_date.setText(date);
                        picker.dismiss();

                    }
                });
                picker.show();

            }
        });

        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from_date.getText().toString().trim().length() > 0 && to_date.getText().toString().trim().length() > 0) {
                    adapter = null;
                    data = new ArrayList<ReportsPojo>();
                    adapter = new ReportsAdapter(IbvReports.this, R.layout.ibv_items, data);
                    listView.setAdapter(adapter);
                    start = 0;
                    limit = 20;
                    fromdate = from_date.getText().toString().trim();
                    todate = to_date.getText().toString().trim();
                    startprogress();
                    nodata.setVisibility(View.GONE);
                    dateval = dateval + 1;
                    GetInfoTask task = new GetInfoTask();
                    task.execute(fromdate, todate);

                } else {
                    Toast.makeText(IbvReports.this, "From and To date fields should not be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private class GetInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                jobj.put("start", start + "");
                jobj.put("limit", limit + "");
                /*jobj.put("sdate",param[0]);
                jobj.put("edate",param[1]);*/

                //Log.i("utilsInput", Appconstants.GET_MY_IBV_REPORTS + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_IBV_REPORTS, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("utilsresp", resp + "");
            stopprogress();
            listView.setPadding(0, 0, 0, 0);
            loadlay.setVisibility(View.GONE);
            scrollNeed = true;


            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            ReportsPojo bo = new ReportsPojo();
                            // bo.setUserid(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                            bo.setPlan(jobj.getString("plan").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("plan"));
                            bo.setPercentage(jobj.getString("percen").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("percen"));
                            bo.setDate(jobj.getString("date").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("date"));
                            bo.setAmount(jobj.getString("amount").toString().trim().equalsIgnoreCase("null") ? "0.00" : jobj.getString("amount"));
                            bo.setIbv(jobj.getString("bv").toString().trim().equalsIgnoreCase("null") ? "0.00" : jobj.getString("bv"));
                            data.add(bo);

                        }
                        if (adapter == null) {
                            adapter = new ReportsAdapter(IbvReports.this, R.layout.ibv_items, data);
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
                    }
                } else {
                    if (start == 0) {
                        retry.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                if (start == 0) {
                    retry.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

                }


            }


        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reports, menu);
        return true;
    }*/

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

            Fillter();
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    private void Fillter() {

        if (!filtercheck) {
            filter_lay.setVisibility(View.VISIBLE);
            filtercheck = true;
            fromdate = "";
            todate = "";
            from_date.setText("");
            to_date.setText("");
        } else {
            filter_lay.setVisibility(View.GONE);
            filtercheck = false;
            fromdate = "";
            todate = "";
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
