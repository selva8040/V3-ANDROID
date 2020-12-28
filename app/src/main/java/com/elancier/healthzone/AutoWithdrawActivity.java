package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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

import com.elancier.healthzone.Adapter.AutoWithdrawAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HP PC on 09-03-2018.
 */

public class AutoWithdrawActivity extends MainView {

    ArrayList<ReportsPojo> sponsorlist;
    ReportsPojo pojo;
    ListView listView;
    AutoWithdrawAdapter adapter;
    ImageView search_img;
    boolean filtercheck = false;
    EditText from_date,to_date;
    LinearLayout filter_lay;
    LinearLayout totallay;
    TextView totbonusamt,totalnetamt;
    double totbonusval=0,totnetval=0;
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
        setContentView(R.layout.activity_auto_withdraw);
        utils=new Utils(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        filter_lay = (LinearLayout) findViewById(R.id.filter_lay);
        search_img = (ImageView) findViewById(R.id.search_img);
        from_date = (EditText) findViewById(R.id.from_date);
        to_date = (EditText) findViewById(R.id.to_date);
        loadlay=(LinearLayout)findViewById(R.id.play);
        totallay=(LinearLayout)findViewById(R.id.totallay);
        nodata=(TextView)findViewById(R.id.nodata);
        totbonusamt=(TextView)findViewById(R.id.bonusamt);
        totalnetamt=(TextView)findViewById(R.id.netamt);

        listView = (ListView) findViewById(R.id.listView);
        sponsorlist = new ArrayList<ReportsPojo>();
        scrollNeed = true;
        start=0;
        limit=20;
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
        task.execute(fromdate,todate);

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();
                startprogress();
                GetInfoTask task=new GetInfoTask();
                task.execute(fromdate,todate);
            }
        });

        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                if (scrollValue && scrollNeed && (sponsorlist.size()>0)) {
                    scrollNeed = false;
                    listView.setPadding(0,0,0,px);


                    if(from_date.getText().toString().trim().length()>0&&to_date.getText().toString().trim().length()>0){
                        loadlay.setVisibility(View.VISIBLE);
                        GetInfoTask task=new GetInfoTask();
                        task.execute(fromdate,todate);

                    }
                    else{
                        if(dateval>0) {
                            adapter = null;
                            sponsorlist = new ArrayList<ReportsPojo>();
                            adapter = new AutoWithdrawAdapter(AutoWithdrawActivity.this, R.layout.auto_withdraw_item, sponsorlist);
                            listView.setAdapter(adapter);
                            start = 0;
                            limit = 20;
                            fromdate = from_date.getText().toString().trim();
                            todate = to_date.getText().toString().trim();
                            startprogress();
                            dateval=0;
                        }
                        else{
                            loadlay.setVisibility(View.VISIBLE);
                        }
                        GetInfoTask task=new GetInfoTask();
                        task.execute(fromdate,todate);
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



        from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new Dialog(AutoWithdrawActivity.this);
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

                if(from_date.getText().toString().trim().length()>0){
                    String s[]=from_date.getText().toString().trim().split("-");
                 //   Log.i("validddddd date",Integer.parseInt(s[2])+"     "+Integer.parseInt(s[1])+"    "+Integer.parseInt(s[0]));
                    datep.updateDate(Integer.parseInt(s[0]),Integer.parseInt(s[1])-1,Integer.parseInt(s[2]));

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

                        date = year + "-" +( month<10?("0"+month):month) + "-" +(day<10?("0"+day):day);
                        // time = hour + ":" + minute;
                        // Toast.makeText(view.getContext(), date , Toast.LENGTH_LONG).show();
                        from_date.setText(date);
                        to_date.setText("");
                        picker.dismiss();

                    }
                });
                picker.show();

            }
        });

        to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picker = new Dialog(AutoWithdrawActivity.this);
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
                long startDate = 0;
                try {
                    String dateString = from_date.getText().toString().trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = sdf.parse(dateString);
                    //bo.setDate(new SimpleDateFormat("dd-MM").format(date));
                    startDate = date.getTime();
                    //bo.setHeaddate(startDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                datep.setMinDate(startDate);
                if(to_date.getText().toString().trim().length()>0){
                    String s[]=to_date.getText().toString().trim().split("-");
                    //Log.i("validddddd date",Integer.parseInt(s[2])+"     "+Integer.parseInt(s[1])+"    "+Integer.parseInt(s[0]));
                    datep.updateDate(Integer.parseInt(s[0]),Integer.parseInt(s[1])-1,Integer.parseInt(s[2]));

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
                        date = year + "-" +( month<10?("0"+month):month) + "-" +(day<10?("0"+day):day);                        // time = hour + ":" + minute;
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
                if(from_date.getText().toString().trim().length()>0&&to_date.getText().toString().trim().length()>0){
                    adapter=null;
                    sponsorlist=new ArrayList<ReportsPojo>();
                    adapter = new AutoWithdrawAdapter(AutoWithdrawActivity.this, R.layout.auto_withdraw_item, sponsorlist);
                    listView.setAdapter(adapter);
                    start=0;
                    limit=20;
                    fromdate=from_date.getText().toString().trim();
                    todate=to_date.getText().toString().trim();
                    startprogress();
                    nodata.setVisibility(View.GONE);
                    dateval=dateval+1;
                    GetInfoTask task=new GetInfoTask();
                    task.execute(fromdate,todate);

                }
                else{
                    Toast.makeText(AutoWithdrawActivity.this,"From and To date fields should not be empty",Toast.LENGTH_SHORT).show();
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
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());
                jobj.put("start",start+"");
                jobj.put("limit",limit+"");
                jobj.put("from",param[0]);
                jobj.put("to",param[1]);

                //Log.i("utilsInput", Appconstants.AUTO_WITHDRAW+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.AUTO_WITHDRAW,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("utilsresp", resp + "");
            stopprogress();
            listView.setPadding(0,0,0,0);
            loadlay.setVisibility(View.GONE);
            scrollNeed = true;


            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        totnetval=Double.parseDouble(obj1.getString("Net Amt")==null&&obj1.getString("Net Amt").equalsIgnoreCase("")?"0":obj1.getString("Net Amt"));
                        JSONArray jarr= obj1.getJSONArray("Response");
                        for(int i=0;i<jarr.length();i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            ReportsPojo bo=new ReportsPojo();
                            bo.setDate(jobj.getString("date").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("date"));
                            bo.setNetamt(jobj.getDouble("net"));
                            sponsorlist.add(bo);
                        }
                        if(adapter==null) {
                            adapter = new AutoWithdrawAdapter(AutoWithdrawActivity.this, R.layout.auto_withdraw_item, sponsorlist);
                            listView.setAdapter(adapter);
                        }
                        else{
                            adapter.notifyDataSetChanged();
                        }
                        totalnetamt.setText(String.format("Rs.%.2f",totnetval));
                       // totbonusamt.setText(String.format("Rs.%.2f",totbonusval));
                        if(sponsorlist.size()>0){
                            nodata.setVisibility(View.GONE);
                            start=start+20;
                            totallay.setVisibility(View.VISIBLE);
                        }
                        else{
                            totallay.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }

                    }
                    else{
                        if(sponsorlist.size()>0){
                            totallay.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.GONE);

                        }
                        else{
                            totallay.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    if(start==0) {
                        retry.show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                if(start==0) {
                    retry.show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

                }


            }


        }
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
            onBackPressed();
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

}
