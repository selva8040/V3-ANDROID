package com.elancier.healthzone;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Adapter.PrimarySuperPinAdapter;
import com.elancier.healthzone.Adapter.Rewardfeedadap;
import com.elancier.healthzone.Adapter.Rewardhistoryadap;
import com.elancier.healthzone.Adapter.WelcomeReportAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.CheckNetwork;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.Feedbackbo;
import com.elancier.healthzone.Pojo.PinBo;
import com.elancier.healthzone.Pojo.Rewardpointsbo;
import com.elancier.healthzone.Pojo.WelcomeReport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Welcome_report extends AppCompatActivity
{
    LinearLayout progress_lay, retry_lay;
    RecyclerView recyclerlist;
    ProgressBar prog;
    TextView nodata, retry,nodatas;
    Utils utils;
    DatePickerDialog picker;
    int start;
    private boolean scrollValue;
    private boolean scrollNeed;
    int limit;
    int px;

    TextView frdt,todt;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    WelcomeReportAdapter itemsAdapter;
    private List<WelcomeReport> mRecyclerListitems = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    Rewardfeedadap itemsAdapter1;
    private List<Object> mRecyclerListitems1 = new ArrayList<>();
    private List<WelcomeReport> productItems;
    private List<Feedbackbo> productItems1;
    private List<Feedbackbo> productItems2;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_report);

        getSupportActionBar().setTitle("V3 Online TV");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        utils = new Utils(getApplicationContext());

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        frdt=(TextView) findViewById(R.id.frdt);
        todt=(TextView) findViewById(R.id.todt);

        prog=(ProgressBar) findViewById(R.id.progressBar);
        recyclerlist = (RecyclerView) findViewById(R.id.recyclerlist);
        recyclerlist.setLayoutManager(new LinearLayoutManager(this));
        recyclerlist.setItemAnimator(new DefaultItemAnimator());

        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);
        scrollNeed = true;
        start = 0;
        limit = 20;

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        frdt.setText(formattedDate);
        todt.setText(formattedDate);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerlist.setLayoutManager(mLayoutManager);
        //if(!frdt.getText().toString().isEmpty())
        //{
            productItems = new ArrayList<>();
            productItems1 = new ArrayList<>();
            productItems2 = new ArrayList<>();

            productItems.clear();
            productItems1.clear();
            productItems2.clear();
            mRecyclerListitems.clear();
            RewardpointsAsync reward = new RewardpointsAsync();
            reward.execute();
        //}


        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());



        frdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Welcome_report.this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        frdt.setText(year+"-"+(monthOfYear + 1) + "-" +dayOfMonth);
                        if(!todt.getText().toString().isEmpty()){
                            if(CheckNetwork.isInternetAvailable(Welcome_report.this))
                            {

                                start=0;
                                limit=20;
                                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String inputString1 = frdt.getText().toString();
                                String inputString2 = todt.getText().toString();

                                try {
                                    Date date1 = myFormat.parse(inputString1);
                                    Date date2 = myFormat.parse(inputString2);
                                    long diff = date2.getTime() - date1.getTime();
                                    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                    int date=Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                    if(date<=7){
                                        productItems = new ArrayList<>();
                                        productItems1 = new ArrayList<>();
                                        productItems2 = new ArrayList<>();

                                        productItems.clear();
                                        productItems1.clear();
                                        productItems2.clear();
                                        mRecyclerListitems.clear();
                                        RewardpointsAsync reward = new RewardpointsAsync();
                                        reward.execute();
                                    }
                                    else{
                                        try {
                                            android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(Welcome_report.this);
                                            alertBuilder.setCancelable(true);
                                            alertBuilder.setTitle("Information");
                                            alertBuilder.setMessage(Html.fromHtml("Last 7 days History only Show this page.If you want more history\nLog on our website\n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"));
                                            alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            });
                                            android.app.AlertDialog alert = alertBuilder.create();
                                            alert.show();
                                            ((TextView)alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                                        }
                                        catch (Exception e){
                                            Toast.makeText(getApplicationContext(),"Date range should be in 7 days.",Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(),"Visit website for more datas.",Toast.LENGTH_LONG).show();

                                        }
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }
                            else
                            {
                                recyclerlist.setVisibility(View.GONE);
                                retry_lay.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            Toast.makeText(Welcome_report.this,"Select date",Toast.LENGTH_LONG).show();
                        }

                    }
                }, year, month, day);
                picker.show();
            }
        });
        todt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Welcome_report.this,
                new DatePickerDialog.OnDateSetListener() {


                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        todt.setText(year+"-"+(monthOfYear + 1) + "-" +dayOfMonth);

                        if(!frdt.getText().toString().isEmpty()){
                            if(CheckNetwork.isInternetAvailable(Welcome_report.this))
                            {

                                start=0;
                                limit=20;
                                SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String inputString1 = frdt.getText().toString();
                                String inputString2 = todt.getText().toString();

                                try {
                                    Date date1 = myFormat.parse(inputString1);
                                    Date date2 = myFormat.parse(inputString2);
                                    long diff = date2.getTime() - date1.getTime();
                                    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                    int date=Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                                    if(date<=7){

                                        productItems = new ArrayList<>();
                                        productItems1 = new ArrayList<>();
                                        productItems2 = new ArrayList<>();

                                        productItems.clear();
                                        productItems1.clear();
                                        productItems2.clear();
                                        mRecyclerListitems.clear();
                                        RewardpointsAsync reward = new RewardpointsAsync();
                                        reward.execute();
                                    }
                                    else{
                                        try {
                                            android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(Welcome_report.this);
                                            alertBuilder.setCancelable(true);
                                            alertBuilder.setTitle("Information");
                                            alertBuilder.setMessage(Html.fromHtml("Last 7 days History only Show this page.If you want more history\nLog on our website\n.<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"));
                                            alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            });
                                            android.app.AlertDialog alert = alertBuilder.create();
                                            alert.show();
                                            ((TextView)alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                                        }
                                        catch (Exception e){
                                            Toast.makeText(getApplicationContext(),"Date range should be in 7 days.",Toast.LENGTH_LONG).show();
                                            Toast.makeText(getApplicationContext(),"Visit website for more datas.",Toast.LENGTH_LONG).show();

                                        }
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                recyclerlist.setVisibility(View.GONE);
                                retry_lay.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            Toast.makeText(Welcome_report.this,"Select date",Toast.LENGTH_LONG).show();
                        }

                    }
                }, year, month, day);
                picker.show();
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckNetwork.isInternetAvailable(Welcome_report.this))
                {
                    recyclerlist.setVisibility(View.VISIBLE);
                    retry_lay.setVisibility(View.GONE);

                    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String inputString1 = frdt.getText().toString();
                    String inputString2 = todt.getText().toString();

                    try {
                        Date date1 = myFormat.parse(inputString1);
                        Date date2 = myFormat.parse(inputString2);
                        long diff = date2.getTime() - date1.getTime();
                        System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                        int date=Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));
                        if(date<=7){
                            productItems = new ArrayList<>();
                            productItems1 = new ArrayList<>();
                            productItems2 = new ArrayList<>();

                            productItems.clear();
                            productItems1.clear();
                            productItems2.clear();
                            mRecyclerListitems.clear();
                            RewardpointsAsync reward = new RewardpointsAsync();
                            reward.execute();
                        }
                        else{
                            try {

                                android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(Welcome_report.this);
                                alertBuilder.setCancelable(true);
                                alertBuilder.setTitle("Information");
                                alertBuilder.setMessage(Html.fromHtml("Last 7 days History only Show this page.If you want more history\nLog on our website\n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"));
                                alertBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                android.app.AlertDialog alert = alertBuilder.create();
                                alert.show();
                                ((TextView)alert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                            }
                            catch (Exception e){
                                Toast.makeText(getApplicationContext(),"Date range should be in 7 days.",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Visit website for more datas.",Toast.LENGTH_LONG).show();

                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    recyclerlist.setVisibility(View.GONE);
                    retry_lay.setVisibility(View.VISIBLE);
                }
            }
        });

        itemsAdapter = new WelcomeReportAdapter(mRecyclerListitems, this, new WelcomeReportAdapter.OnItemClickListener() {
        @Override
        public void OnItemClick(final View view, final int position, int viewType) {

            Log.e("clickresp", "value");

            //clikffed();
        }
    });
        recyclerlist.setAdapter(itemsAdapter);

        itemsAdapter1 = new Rewardfeedadap(mRecyclerListitems1, getApplicationContext(), new Rewardfeedadap.OnItemClickListener() {
        @Override
        public void OnItemClick(final View view, final int position, int viewType) {
            final Feedbackbo item = (Feedbackbo) mRecyclerListitems1.get(position);
        }
    });


        /* recyclerlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
 
                 if (!recyclerView.canScrollVertically(1)) {
 
                     if(productItems.size()>=limit){
 
                         start=limit+1;
                         limit+=20;
                         prog.setVisibility(View.VISIBLE);
 
                         Log.e("startval",String.valueOf(start));
                         Log.e("limit",String.valueOf(limit));
 
                         RewardpointsAsync reward = new RewardpointsAsync();
                         reward.execute();
 
 
                     }
                     else{
 
                     }
 
                     //Toast.makeText(Welcome_report.this, "Last"+productItems.size()+limit, Toast.LENGTH_LONG).show();
 
                 }
             }
         });*/

    }

    public class RewardpointsAsync extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {

            progress_lay.setVisibility(View.VISIBLE);
            recyclerlist.setVisibility(View.GONE);

        }

        @Override
        protected String doInBackground(String... strings) {
        String result = null;
        Connection con = new Connection();

        try
        {
            JSONObject jobj = new JSONObject();
            jobj.put("uname",utils.loadName());
            jobj.put("from",frdt.getText().toString());
            jobj.put("to",todt.getText().toString());








            //Log.i("rewardinput", Appconstants.welcome_report+"    "+jobj.toString());
            result = con.sendHttpPostjson2(Appconstants.welcome_report,jobj,"");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

        @Override
        protected void onPostExecute(String resp) {
            try {
               // Log.e("rewardresp", resp);
            } catch (Exception e) {
                Log.e("rewardrespcatch", e.toString());

            }
            prog.setVisibility(View.GONE);
            recyclerlist.setVisibility(View.VISIBLE);
            progress_lay.setVisibility(View.GONE);





            JSONArray feed = null;
            String feed1;
            try {
                if (resp != null) {
                    JSONArray j = new JSONArray(resp);

                    JSONObject obj = j.getJSONObject(0);//new JSONObject(resp);

                    JSONArray arrayLanguage = obj.getJSONArray("Response");

                    for (int i = 0; i < arrayLanguage.length(); i++) {
                        JSONObject JO = (JSONObject) arrayLanguage.get(i);
                        String visual_time = "";//JO.getString("visual");
                        //String username = jobject.getString("username");
                        String id = JO.getString("id");
                        String date = JO.getString("date");
                        String username = JO.getString("username");

                        String whome = "";//JO.getString("cutoff");

                        //String whomename = jobject.getString("whomename");
                        //String points = jobject.getString("points");
                        String type = JO.getString("type");

                        String fcolor = "";//JO.getString("fcolor");
                        String feedback = JO.getString("feedback");
                        WelcomeReport wel  = new WelcomeReport();
                        wel.setId(id);
                        wel.setUsername(username);
                        wel.setDate(date);
                        wel.setFeedback(feedback);
                        wel.setType(type);
                        productItems.add(wel);
                        /*try {
                            feed = JO.getJSONArray("feedback");
                            Log.e("feeed val", String.valueOf(i));

                            productItems.add(new Rewardpointsbo(String.valueOf(i), fcolor, visual_time, "", name,uname, whome, "", "feed", type));

                            if (!feed.toString().isEmpty() && !feed.toString().equals("null")) {
                                for (int j = 0; j < feed.length(); j++) {
                                    JSONObject feedobj = (JSONObject) feed.get(j);
                                    String id = feedobj.getString("id");
                                    String comment = feedobj.getString("comment");
                                    String types = feedobj.getString("type");
                                    String time = feedobj.getString("dtime");

                                    Log.e("comment val", String.valueOf(i));
                                    productItems2.add(new Feedbackbo(id, types, comment, String.valueOf(i), time));


                                }
                            }
                        } catch (Exception e) {
                            Log.e("rewardrespnw", e.toString());
                            productItems.add(new Rewardpointsbo(String.valueOf(i), fcolor, visual_time, "", name,uname, whome, "", "notfeed", type));

                        }*/

                    }
                    mRecyclerListitems.clear();
                    mRecyclerListitems.addAll(productItems);
                    itemsAdapter.notifyDataSetChanged();
                    /*JSONArray jarr = new JSONArray(resp);
                    for(int i=0; i<jarr.length(); i++)
                    {
                        JSONObject jobj = jarr.getJSONObject(i);
                        if(jobj.getString("Status").equals("Success"))
                        {
                            JSONArray jarray = jobj.getJSONArray("Response");
                            for(int j=0; j<jarray.length(); j++)
                            {
                                Log.e("Value green","nn");
    
                                JSONObject jobject = jarray.getJSONObject(j);
                                //String id = jobject.getString("id");
                                //String date = jobject.getString("date");
                                Log.e("Value green","nn");
    
                                String visual_time = jobject.getString("visual");
                                //String username = jobject.getString("username");
                                String name = jobject.getString("dat");
                                String whome = jobject.getString("point");
                                //String whomename = jobject.getString("whomename");
                                //String points = jobject.getString("points");
                                String type = jobject.getString("status");
    
                                Log.e("Value green",type);
    
    
    
    
                            }
                        }
                        else
                        {
                            recyclerlist.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }*/
                } else {

                    RewardpointsAsync reward = new RewardpointsAsync();
                    reward.execute();
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
                Log.e("E VALUE", e.toString());
                mRecyclerListitems.addAll(productItems);
                itemsAdapter.notifyDataSetChanged();
            }
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
            onBackPressed();
            return true;
        }

        /*if (id == R.id.action_settings) {

            user_id.setEnabled(true);
            user_img.setEnabled(true);
            doj.setEnabled(true);
            designaton.setEnabled(true);
            receivable_amt.setEnabled(true);
            save.setVisibility(View.VISIBLE);

            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Welcome_report.this, HomePage.class);
        startActivity(intent);
    }

    public void clikffed(String pos,String time){
        try {
            final Dialog update = new Dialog(Welcome_report.this);
            update.requestWindowFeature(Window.FEATURE_NO_TITLE);
            update.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.WHITE));

            View v=getLayoutInflater().inflate(R.layout.feedback_popup,null);
            final RecyclerView updatebut=(RecyclerView) v.findViewById(R.id.feedlist);
            ScrollView scroll=(ScrollView) v.findViewById(R.id.sroll);
            final TextView nofeed=(TextView) v.findViewById(R.id.textView18);
            final TextView feed   =(TextView) v.findViewById(R.id.textView15);

            final TextView feedvt =(TextView) v.findViewById(R.id.textView15vtime);

            Button laterbut=(Button) v.findViewById(R.id.agree);

            update.setContentView(v);
            Window window = update.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            feed.setText("Feedback - "+utils.loadName());
            feedvt.setText("Visual Time - "+time);
            update.setCancelable(true);
            update.show();


            for(int k=0;k<productItems2.size();k++){
                Log.e("VALUEGET",productItems2.get(k).getPoints());
                if(String.valueOf(pos).equals(productItems2.get(k).getPoints())){
                    productItems1.add(new Feedbackbo(productItems2.get(k).getId(),productItems2.get(k).gettype(),productItems2.get(k).getcomment(),"",productItems2.get(k).gettime()));
                }
            }


            if(productItems1.isEmpty()){
                nofeed.setVisibility(View.VISIBLE);
            }

            mRecyclerListitems1.addAll(productItems1);
            itemsAdapter1.notifyDataSetChanged();

            RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(Welcome_report.this, LinearLayoutManager.VERTICAL, false);
            updatebut.setLayoutManager(mLayoutManager3);
            scroll.smoothScrollBy(updatebut.computeHorizontalScrollOffset(), 0);

            updatebut.setAdapter(itemsAdapter1);
            ///titlename.setText("New Version 1."+Number);

            laterbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update.dismiss();
                    productItems1.clear();
                    mRecyclerListitems1.clear();
                    nofeed.setVisibility(View.GONE);


                }
            });

        }catch(Exception e){
            Log.e("Log val",e.toString());
            //logger.info("PerformVersionTask error" + e.getMessage());
        }
    }
}

