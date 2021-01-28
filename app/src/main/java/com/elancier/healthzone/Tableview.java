package com.elancier.healthzone;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.CheckNetwork;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;
import com.elancier.healthzone.R;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.view.Gravity.*;
import static com.elancier.healthzone.Common.Appconstants.domain;

public class Tableview extends AppCompatActivity
{
    private static int SPLASH_TIME_OUT = 3000;

    Boolean dash;
    String vdeoresp="";
    String clickupdate="";
    String respupdate="";
    int ALL_PERMISSIONS = 101;
    Utils utils;
    ImageView retrybut;
    Button retryreport;
    String versupdate="";
    Dialog progbar,prog;

    DatePickerDialog picker;
    TableLayout tb1,tb2,tb3;
    String amount="";
    String plan="";
    Date currentTime;
    String gpv="";
    String ibv="";
    String  purchase="";
    String sales="";
    String target="";
    String achieve="";
     String  balance="";
    String wallet_amt="";
    String   todayreward="";
    String totalreward="";
    String   available_reward="";
    String   newvideo="";
    Dialog retry, update;
    TextView skip;
    Integer countres=0;
    String responseval="";
    ScrollView scr;
    String respval="";
    String vidval="";
    TextView blue;
    TextView green;
    TextView brown;
    List<JSONObject> jsonobjarr;
    String videoresp="";
    String origin_domain="";
    String origin_count="";
    Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableview);
        progbar =new  Dialog(Tableview.this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progbar.setContentView(R.layout.loadversion);
        progbar.setCancelable(false);
        utils=new Utils(getApplicationContext());

        tb1=(TableLayout) findViewById(R.id.tb1);
        skip=(TextView) findViewById(R.id.skip);
        tb2=(TableLayout) findViewById(R.id.tb2);
        tb3=(TableLayout) findViewById(R.id.tb3);
        scr=(ScrollView) findViewById(R.id.scr);
        blue=(TextView) findViewById(R.id.textView40);
        green=(TextView) findViewById(R.id.textView44);
        brown=(TextView) findViewById(R.id.textView45);
        button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        /*{"id":"1","name":"Tamil"},
        {"id":"2","name":"English"},
        {"id":"3","name":"Malayalam"},
        {"id":"4","name":"Kannadam"},
        {"id":"5","name":"Telugu"},
        {"id":"6","name":"Hindi"}*/



        if(utils.loadlang().equals("1")){
            FirebaseMessaging.getInstance().subscribeToTopic("Tamil");
        }
        else if(utils.loadlang().equals("2")){
            FirebaseMessaging.getInstance().subscribeToTopic("English");
        }
        else if(utils.loadlang().equals("3")){
            FirebaseMessaging.getInstance().subscribeToTopic("Malayalam");
        }
        else if(utils.loadlang().equals("4")){
            FirebaseMessaging.getInstance().subscribeToTopic("Kannadam");
        }
        else if(utils.loadlang().equals("5")){
            FirebaseMessaging.getInstance().subscribeToTopic("Telugu");
        }
        else if(utils.loadlang().equals("6")){
            FirebaseMessaging.getInstance().subscribeToTopic("Hindi");
        }


        try {
            new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int currentapiVersion = android.os.Build.VERSION.SDK_INT;

                            if(currentapiVersion>24) {
                                Date currentTimes = Calendar.getInstance().getTime();
                                Log.e("times",String.valueOf(currentTimes));

                                DateFormat format = new SimpleDateFormat("dd MMM yyyy  HH:mm");
                                String formats = format.format(currentTimes);
                                Log.e("time",formats);

                                blue.setText (utils.loadName() + " - " + formats);
                                green.setText(utils.loadName() + " - " + formats);
                                brown.setText(utils.loadName() + " - " + formats);
                            }
                            else{
                                Date currentTimes = Calendar.getInstance().getTime();
                                blue.setText(utils.loadName()+" - "+currentTimes);
                                green.setText(utils.loadName()+" - "+currentTimes);
                                brown.setText(utils.loadName() + " - " + currentTimes);


                            }
                        }
                    });
                }
            }.start();
        }
        catch (Exception e){


        }

         respval=utils.loaddraw();
         vidval=utils.loadvideourl();
        System.out.println("draw"+respval);
        System.out.println("vidval"+vidval);

        if(CheckNetwork.isInternetAvailable(Tableview.this))
        {

            if(utils.loadtype().equals("0")){

                tb1.setVisibility(View.VISIBLE);
                tb2.setVisibility(View.GONE);
                green.setVisibility(View.GONE);
                blue.setVisibility(View.VISIBLE);


            }
            else if(utils.loadtype().equals("1")){
                tb1.setVisibility(View.GONE);
                green.setVisibility(View.VISIBLE);
                blue.setVisibility(View.GONE);
                tb2.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    scr.setBackground(getResources().getDrawable(R.drawable.gradient_background_two));
                }


            }
            else if(utils.loadtype().equals("2")){
                tb1.setVisibility(View.GONE);
                tb2.setVisibility(View.GONE);
                brown.setVisibility(View.VISIBLE);
                blue.setVisibility(View.GONE);
                green.setVisibility(View.GONE);
                tb3.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    scr.setBackground(getResources().getDrawable(R.drawable.gradient_background_brown));
                }


            }


            String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Write whatever to want to do after delay specified (1 sec)

                    if(utils.loadcountry().isEmpty()){

                        Videosubmit task=new Videosubmit();
                        task.execute();

                    }
                    else{

                        new Thread() {
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            VideosImage task = new VideosImage();
                                            task.execute();
                                        }
                                        catch (Exception e){

                                        }
                                    }
                                });
                            }
                            }.start();
                    }

                }
            }, 1000);



        }


        /*Handler handler1=new Handler();
        handler1.postDelayed(new Runnable(){
            public void run(){
                Intent a=new Intent(Tableview.this,Videoimage.class);
                a.putExtra("amount", amount);
                a.putExtra("gpv", gpv);
                a.putExtra("ibv", ibv);
                a.putExtra("purchase", purchase);
                a.putExtra("sales", sales);
                a.putExtra("target", target);
                a.putExtra("achieve", achieve);
                a.putExtra("balance", balance);
                a.putExtra("wallet_amt", wallet_amt);
                a.putExtra("todayreward", todayreward);
                a.putExtra("totalreward", totalreward);
                a.putExtra("available_reward", available_reward);
                startActivity(a);
                finish();
            }
        },2000);*/

        button4.setOnClickListener(new View.OnClickListener() {
        @Override
          public void onClick(View v) {

            button4.setEnabled(false);
            button4.setTextColor(getResources().getColor(R.color.lightgreen));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                button4.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_videodis, 0);
            }
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            Log.e("Front_Url",utils.front_url());

            if(!utils.front_url().isEmpty()&&utils.front_url()!=null) {
                if (currentapiVersion > 20) {
                    System.out.println("inside21");
                    Intent a = new Intent(Tableview.this, Front_VideoActivity.class);
                    a.putExtra("videoresp", videoresp);
                    a.putExtra("orgdomain", origin_domain);
                    a.putExtra("orgcount", origin_count);
                    a.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    System.out.println("inside20");
                    Intent a = new Intent(Tableview.this, Front_VideoActivity.class);
                    //a.putExtra("videoresp", videoresp);
                    startActivity(a);
                    finish();

                }
            }
            else{
                if (currentapiVersion > 20) {
                    System.out.println("inside21");
                    Intent a = new Intent(Tableview.this, Videoimage.class);
                    a.putExtra("videoresp", videoresp);
                    a.putExtra("orgdomain", origin_domain);
                    a.putExtra("orgcount", origin_count);
                    a.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    System.out.println("inside20");
                    Intent a = new Intent(Tableview.this, Videoimage.class);
                    //a.putExtra("videoresp", videoresp);
                    startActivity(a);
                    finish();

                }
            }

            }
            });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button5.setEnabled(false);
                Intent a=new Intent(Tableview.this,HomePage.class);
                startActivity(a);
                finish();

            }
        });

        try {
            if (!utils.loadjsonval().isEmpty()) {
                jsonobjarr = new ArrayList<JSONObject>();
                String exc = utils.loadjsonval();
                JSONArray jsonobjarrval = new JSONArray(exc);
                System.out.println("jsonval " + jsonobjarrval);

                for (int j = 0; j < jsonobjarrval.length(); j++) {
                    JSONObject jobject = jsonobjarrval.getJSONObject(j);
                    jsonobjarr.add(jobject);
                    System.out.println("jsonobj " + jsonobjarr);

                }

                if(jsonobjarr.size()==1){
                    android.app.AlertDialog.Builder alert=new android.app.AlertDialog.Builder(Tableview.this);
                    alert.setCancelable(true);
                    alert.setTitle("Get Today Videos");
                    alert.setMessage("If you want to get today's new videos.\nJust click 'Refresh' button.");
                    alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                           /* Intent i = new Intent(Tableview.this, Offline_video.class);
                            startActivity(i);
                            finish();*/

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();

                }
                }

        }
        catch (Exception e){
    }


        /*Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent i = new Intent(Tableview.this, Videoimage.class);
                i.putExtra("amount", amount);
                i.putExtra("gpv", gpv);
                i.putExtra("ibv", ibv);
                i.putExtra("purchase", purchase);
                i.putExtra("sales", sales);
                i.putExtra("target", target);
                i.putExtra("achieve", achieve);
                i.putExtra("balance", balance);
                i.putExtra("wallet_amt", wallet_amt);
                i.putExtra("todayreward", todayreward);
                i.putExtra("totalreward", totalreward);
                i.putExtra("available_reward", available_reward);

                startActivity(i);

                // close this activity
                finish();
            }
        },2000);*/
/*        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*if((clickupdate.equals("false")&& respupdate.equals("false"))||(respupdate.equals("true"))&&(clickupdate.equals("true"))) {
                    if (newvideo.equals("1")) {
                        Intent i = new Intent(Tableview.this, Videoimage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);

                        startActivity(i);

                        // close this activity
                        finish();
                    } else {
                        Intent i = new Intent(Tableview.this, Videoimage2.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);

                        startActivity(i);

                        // close this activity
                        finish();
                    }
                }*//*
                //if
                   // dash = false;

                    if(respval.isEmpty()||respval.equals("null")) {
                        GetInfoTask tasks = new GetInfoTask();
                        tasks.execute();
                    }
                    else{
                        try {
                            if (respval != null) {
                                JSONArray json = new JSONArray(respval);
                                JSONObject obj1 = json.getJSONObject(0);
                                if (obj1.getString("Status").equals("Success")) {
                                    JSONArray jarr = obj1.getJSONArray("Response");
                                    for (int i = 0; i < jarr.length(); i++) {
                                        JSONObject jobj = jarr.getJSONObject(i);
                                        ReportsPojo bo = new ReportsPojo();
                                        utils.savePreferences("ibv", jobj.getString("ibv").toString().trim().equalsIgnoreCase("null") || jobj.getString("ibv").toString().trim().length() == 0 ? "0" : jobj.getString("ibv"));
                                        utils.savePreferences("gbv", jobj.getString("gbv").toString().trim().equalsIgnoreCase("null") || jobj.getString("gbv").toString().trim().length() == 0 ? "0" : jobj.getString("gbv"));
                                        utils.savePreferences("commition", jobj.getString("p_commition").toString().trim().equalsIgnoreCase("null") || jobj.getString("p_commition").toString().trim().length() == 0 ? "0" : jobj.getString("p_commition"));
                                        utils.savePreferences("withdraw", jobj.getString("withdraw").toString().trim().equalsIgnoreCase("null") || jobj.getString("withdraw").toString().trim().length() == 0 ? "0" : jobj.getString("withdraw"));
                                        utils.savePreferences("purchase", jobj.getString("purchase").toString().trim().equalsIgnoreCase("null") || jobj.getString("purchase").toString().trim().length() == 0 ? "0" : jobj.getString("purchase"));
                                        utils.savePreferences("sales", jobj.getString("sales").toString().trim().equalsIgnoreCase("null") || jobj.getString("sales").toString().trim().length() == 0 ? "0" : jobj.getString("sales"));
                                        utils.savePreferences("target", jobj.getString("targetamount").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetamount").toString().trim().length() == 0 ? "0" : jobj.getString("targetamount"));
                                        utils.savePreferences("achieve", jobj.getString("targetachieved").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetachieved").toString().trim().length() == 0 ? "0" : jobj.getString("targetachieved"));
                                        utils.savePreferences("balance", jobj.getString("targetbalance").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetbalance").toString().trim().length() == 0 ? "0" : jobj.getString("targetbalance"));
                                        utils.savePreferences("wallet", jobj.getString("wallet").toString().trim().equalsIgnoreCase("null") || jobj.getString("wallet").toString().trim().length() == 0 ? "0" : jobj.getString("wallet"));
                                        utils.savePreferences("newvideo", jobj.getString("newvideo").toString().trim().equalsIgnoreCase("null") || jobj.getString("newvideo").toString().trim().length() == 0 ? "0" : jobj.getString("newvideo"));
                                        utils.savePreferences("today_reward", jobj.getString("today_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("today_reward").toString().trim().length() == 0 ? "0" : jobj.getString("today_reward"));
                                        utils.savePreferences("total_reward", jobj.getString("total_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("total_reward").toString().trim().length() == 0 ? "0" : jobj.getString("total_reward"));
                                        utils.savePreferences("available_reward", jobj.getString("available_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("available_reward").toString().trim().length() == 0 ? "0" : jobj.getString("available_reward"));
                                        amount=(String.format("%.2f", Double.parseDouble(utils.loadCommition())));
                                        gpv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadgbv())) + "");
                                        ibv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadIbv())) + "");
                                        purchase=(String.format("Rs.%.2f", Double.parseDouble(utils.loadPurchase())) + "");
                                        sales=(String.format("Rs.%.2f", Double.parseDouble(utils.loadSales())) + "");
                                        plan=jobj.getString("plan").toString().trim().equalsIgnoreCase("null") || jobj.getString("plan").toString().trim().length() == 0 ? "0" : jobj.getString("plan");
                                        Log.i("plan",plan);
                                        target=(utils.loadTarget());
                                        achieve=(utils.loadAchieve());
                                        balance=(utils.loadBalance());
                                        wallet_amt=(utils.loadWallet());
                                        todayreward=(utils.today_reward());
                                        totalreward=(utils.total_reward());
                                        available_reward=(utils.available_reward());
                                        newvideo=(jobj.getString("newvideo"));
                                    }
                        *//*new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity

                            }
                        }, SPLASH_TIME_OUT);*//*
                                    if (dash){
                                        Intent i=new Intent(Tableview.this, HomePage.class);
                                        i.putExtra("amount",amount);
                                        i.putExtra("gpv",gpv);
                                        i.putExtra("ibv",ibv);
                                        i.putExtra("plan",plan);
                                        i.putExtra("purchase",purchase);
                                        i.putExtra("sales",sales);
                                        i.putExtra("target",target);
                                        i.putExtra("achieve",achieve);
                                        i.putExtra("balance",balance);
                                        i.putExtra("wallet_amt",wallet_amt);
                                        i.putExtra("todayreward",todayreward);
                                        i.putExtra("totalreward",totalreward);
                                        i.putExtra("available_reward",available_reward);
                                        startActivity(i);

                                        // close this activity
                                        finish();
                                    }else {
                                        //if ((clickupdate.equals("false") && respupdate.equals("false")) || (respupdate.equals("true")) && (clickupdate.equals("true"))) {
                                        if (newvideo.equals("1")) {
                                            Intent i = new Intent(Tableview.this, Videoimage.class);
                                            i.putExtra("amount", amount);
                                            i.putExtra("gpv", gpv);
                                            i.putExtra("ibv", ibv);
                                            i.putExtra("purchase", purchase);
                                            i.putExtra("sales", sales);
                                            i.putExtra("target", target);
                                            i.putExtra("achieve", achieve);
                                            i.putExtra("balance", balance);
                                            i.putExtra("wallet_amt", wallet_amt);
                                            i.putExtra("todayreward", todayreward);
                                            i.putExtra("totalreward", totalreward);
                                            i.putExtra("available_reward", available_reward);

                                            startActivity(i);

                                            // close this activity
                                            finish();
                                        } else {
                                            Intent i = new Intent(Tableview.this, Videoimage2.class);
                                            i.putExtra("amount", amount);
                                            i.putExtra("gpv", gpv);
                                            i.putExtra("ibv", ibv);
                                            i.putExtra("purchase", purchase);
                                            i.putExtra("sales", sales);
                                            i.putExtra("target", target);
                                            i.putExtra("achieve", achieve);
                                            i.putExtra("balance", balance);
                                            i.putExtra("wallet_amt", wallet_amt);
                                            i.putExtra("todayreward", todayreward);
                                            i.putExtra("totalreward", totalreward);
                                            i.putExtra("available_reward", available_reward);

                                            startActivity(i);

                                            // close this activity
                                            finish();
                                        }
                            *//*} else {
                                new Handler().removeCallbacks(null);
                            }*//*
                                    }

                                } else {
                                    Toast.makeText(Tableview.this, "Username Mismatch.Please login.", Toast.LENGTH_SHORT).show();
                                    utils.savePreferences("name", "");
                                    utils.savePreferences("image", "");
                                    utils.savePreferences("doj", "");
                                    utils.savePreferences("designation", "");
                                    utils.savePreferences("ibv", "");
                                    utils.savePreferences("gbv", "");
                                    utils.savePreferences("commition", "");
                                    startActivity(new Intent(Tableview.this, Login.class));
                                }
                            } else {
                                retry.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("err",e.toString());
                            retry.show();
                        }
                    }
                }else {
                    Toast.makeText(Tableview.this,"No Internet",Toast.LENGTH_SHORT).show();
                }
            }
        });



        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckNetwork.isInternetAvailable(Tableview.this)) {
                    dash = true;
                    if(respval.isEmpty()||respval.equals("null")) {
                        GetInfoTask tasks = new GetInfoTask();
                        tasks.execute();
                    }
                    else{
                        try {
                            if (respval != null) {
                                JSONArray json = new JSONArray(respval);
                                JSONObject obj1 = json.getJSONObject(0);
                                if (obj1.getString("Status").equals("Success")) {
                                    JSONArray jarr = obj1.getJSONArray("Response");
                                    for (int i = 0; i < jarr.length(); i++) {
                                        JSONObject jobj = jarr.getJSONObject(i);
                                        ReportsPojo bo = new ReportsPojo();
                                        utils.savePreferences("ibv", jobj.getString("ibv").toString().trim().equalsIgnoreCase("null") || jobj.getString("ibv").toString().trim().length() == 0 ? "0" : jobj.getString("ibv"));
                                        utils.savePreferences("gbv", jobj.getString("gbv").toString().trim().equalsIgnoreCase("null") || jobj.getString("gbv").toString().trim().length() == 0 ? "0" : jobj.getString("gbv"));
                                        utils.savePreferences("commition", jobj.getString("p_commition").toString().trim().equalsIgnoreCase("null") || jobj.getString("p_commition").toString().trim().length() == 0 ? "0" : jobj.getString("p_commition"));
                                        utils.savePreferences("withdraw", jobj.getString("withdraw").toString().trim().equalsIgnoreCase("null") || jobj.getString("withdraw").toString().trim().length() == 0 ? "0" : jobj.getString("withdraw"));
                                        utils.savePreferences("purchase", jobj.getString("purchase").toString().trim().equalsIgnoreCase("null") || jobj.getString("purchase").toString().trim().length() == 0 ? "0" : jobj.getString("purchase"));
                                        utils.savePreferences("sales", jobj.getString("sales").toString().trim().equalsIgnoreCase("null") || jobj.getString("sales").toString().trim().length() == 0 ? "0" : jobj.getString("sales"));
                                        utils.savePreferences("target", jobj.getString("targetamount").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetamount").toString().trim().length() == 0 ? "0" : jobj.getString("targetamount"));
                                        utils.savePreferences("achieve", jobj.getString("targetachieved").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetachieved").toString().trim().length() == 0 ? "0" : jobj.getString("targetachieved"));
                                        utils.savePreferences("balance", jobj.getString("targetbalance").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetbalance").toString().trim().length() == 0 ? "0" : jobj.getString("targetbalance"));
                                        utils.savePreferences("wallet", jobj.getString("wallet").toString().trim().equalsIgnoreCase("null") || jobj.getString("wallet").toString().trim().length() == 0 ? "0" : jobj.getString("wallet"));
                                        utils.savePreferences("newvideo", jobj.getString("newvideo").toString().trim().equalsIgnoreCase("null") || jobj.getString("newvideo").toString().trim().length() == 0 ? "0" : jobj.getString("newvideo"));
                                        utils.savePreferences("today_reward", jobj.getString("today_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("today_reward").toString().trim().length() == 0 ? "0" : jobj.getString("today_reward"));
                                        utils.savePreferences("total_reward", jobj.getString("total_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("total_reward").toString().trim().length() == 0 ? "0" : jobj.getString("total_reward"));
                                        utils.savePreferences("available_reward", jobj.getString("available_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("available_reward").toString().trim().length() == 0 ? "0" : jobj.getString("available_reward"));
                                        amount=(String.format("%.2f", Double.parseDouble(utils.loadCommition())));
                                        gpv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadgbv())) + "");
                                        ibv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadIbv())) + "");
                                        purchase=(String.format("Rs.%.2f", Double.parseDouble(utils.loadPurchase())) + "");
                                        sales=(String.format("Rs.%.2f", Double.parseDouble(utils.loadSales())) + "");
                                        plan=jobj.getString("plan").toString().trim().equalsIgnoreCase("null") || jobj.getString("plan").toString().trim().length() == 0 ? "0" : jobj.getString("plan");
                                        Log.i("plan",plan);
                                        target=(utils.loadTarget());
                                        achieve=(utils.loadAchieve());
                                        balance=(utils.loadBalance());
                                        wallet_amt=(utils.loadWallet());
                                        todayreward=(utils.today_reward());
                                        totalreward=(utils.total_reward());
                                        available_reward=(utils.available_reward());
                                        newvideo=(jobj.getString("newvideo"));
                                    }
                        *//*new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity

                            }
                        }, SPLASH_TIME_OUT);*//*
                                    if (dash){
                                        Intent i=new Intent(Tableview.this, HomePage.class);
                                        i.putExtra("amount",amount);
                                        i.putExtra("gpv",gpv);
                                        i.putExtra("ibv",ibv);
                                        i.putExtra("plan",plan);
                                        i.putExtra("purchase",purchase);
                                        i.putExtra("sales",sales);
                                        i.putExtra("target",target);
                                        i.putExtra("achieve",achieve);
                                        i.putExtra("balance",balance);
                                        i.putExtra("wallet_amt",wallet_amt);
                                        i.putExtra("todayreward",todayreward);
                                        i.putExtra("totalreward",totalreward);
                                        i.putExtra("available_reward",available_reward);
                                        startActivity(i);

                                        // close this activity
                                        finish();
                                    }else {
                                        //if ((clickupdate.equals("false") && respupdate.equals("false")) || (respupdate.equals("true")) && (clickupdate.equals("true"))) {
                                        if (newvideo.equals("1")) {
                                            Intent i = new Intent(Tableview.this, Videoimage.class);
                                            i.putExtra("amount", amount);
                                            i.putExtra("gpv", gpv);
                                            i.putExtra("ibv", ibv);
                                            i.putExtra("purchase", purchase);
                                            i.putExtra("sales", sales);
                                            i.putExtra("target", target);
                                            i.putExtra("achieve", achieve);
                                            i.putExtra("balance", balance);
                                            i.putExtra("wallet_amt", wallet_amt);
                                            i.putExtra("todayreward", todayreward);
                                            i.putExtra("totalreward", totalreward);
                                            i.putExtra("available_reward", available_reward);

                                            startActivity(i);

                                            // close this activity
                                            finish();
                                        } else {
                                            Intent i = new Intent(Tableview.this, Videoimage2.class);
                                            i.putExtra("amount", amount);
                                            i.putExtra("gpv", gpv);
                                            i.putExtra("ibv", ibv);
                                            i.putExtra("purchase", purchase);
                                            i.putExtra("sales", sales);
                                            i.putExtra("target", target);
                                            i.putExtra("achieve", achieve);
                                            i.putExtra("balance", balance);
                                            i.putExtra("wallet_amt", wallet_amt);
                                            i.putExtra("todayreward", todayreward);
                                            i.putExtra("totalreward", totalreward);
                                            i.putExtra("available_reward", available_reward);

                                            startActivity(i);

                                            // close this activity
                                            finish();
                                        }
                            *//*} else {
                                new Handler().removeCallbacks(null);
                            }*//*
                                    }

                                } else {
                                    Toast.makeText(Tableview.this, "Username Mismatch.Please login.", Toast.LENGTH_SHORT).show();
                                    utils.savePreferences("name", "");
                                    utils.savePreferences("image", "");
                                    utils.savePreferences("doj", "");
                                    utils.savePreferences("designation", "");
                                    utils.savePreferences("ibv", "");
                                    utils.savePreferences("gbv", "");
                                    utils.savePreferences("commition", "");
                                    startActivity(new Intent(Tableview.this, Login.class));
                                }
                            } else {
                                retry.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("err",e.toString());
                            retry.show();
                        }
                    }
                }else {
                    Toast.makeText(Tableview.this,"No Internet",Toast.LENGTH_SHORT).show();
                }

                *//*Intent i=new Intent(Tableview.this, HomePage.class);
                i.putExtra("amount",amount);
                i.putExtra("gpv",gpv);
                i.putExtra("ibv",ibv);
                i.putExtra("purchase",purchase);
                i.putExtra("sales",sales);
                i.putExtra("target",target);
                i.putExtra("achieve",achieve);
                i.putExtra("balance",balance);
                i.putExtra("wallet_amt",wallet_amt);
                i.putExtra("todayreward",todayreward);
                i.putExtra("totalreward",totalreward);
                i.putExtra("available_reward",available_reward);
                startActivity(i);

                // close this activity
                finish();*//*
            }
        });*/
        /*skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        //final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        //ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);

        //getSupportActionBar().setTitle("V3 Online Tv");

        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        View v = getLayoutInflater().inflate(R.layout.retrylay_send, null);
        retrybut = (ImageView) v.findViewById(R.id.retry);
        retryreport = (Button) v.findViewById(R.id.button6);

        String mobile=utils.loadmob();
        String username=utils.loadName();
        //retry.show();

        retryreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTime = Calendar.getInstance().getTime();
                AlertDialog.Builder builder = new AlertDialog.Builder(Tableview.this);
                builder.setMessage("Username :  "+username+"\n\n"+"Mobile      :  "+mobile+"\n\n"+"Time         :  "+currentTime.toString())
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                retry.cancel();
                                //ReportTask tasks=new ReportTask();
                                //tasks.execute();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                //retry.cancel();

                                //Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                        //Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Report Details");
                alert.show();
            }
        });

        retry.setContentView(v);
        retry.setCancelable(false);


        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();

                GetInfoTask tasks = new GetInfoTask();
                tasks.execute();

                //GetInfoTask task = new GetInfoTask();
                //task.execute();
            }
        });

    }

    private class GetInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //progbar.show();
            Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
               // Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_AMOUNT, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("HomePage Res", resp + "");
            responseval=resp;
            //stopprogress();
            /*PerformVersionTask ptask = new PerformVersionTask();
            ptask.execute();*/
            //progbar.dismiss();
            try {
                if (resp != null) {
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            ReportsPojo bo = new ReportsPojo();
                            utils.savePreferences("ibv", jobj.getString("ibv").toString().trim().equalsIgnoreCase("null") || jobj.getString("ibv").toString().trim().length() == 0 ? "0" : jobj.getString("ibv"));
                            utils.savePreferences("gbv", jobj.getString("gbv").toString().trim().equalsIgnoreCase("null") || jobj.getString("gbv").toString().trim().length() == 0 ? "0" : jobj.getString("gbv"));
                            utils.savePreferences("commition", jobj.getString("p_commition").toString().trim().equalsIgnoreCase("null") || jobj.getString("p_commition").toString().trim().length() == 0 ? "0" : jobj.getString("p_commition"));
                            utils.savePreferences("withdraw", jobj.getString("withdraw").toString().trim().equalsIgnoreCase("null") || jobj.getString("withdraw").toString().trim().length() == 0 ? "0" : jobj.getString("withdraw"));
                            utils.savePreferences("purchase", jobj.getString("purchase").toString().trim().equalsIgnoreCase("null") || jobj.getString("purchase").toString().trim().length() == 0 ? "0" : jobj.getString("purchase"));
                            utils.savePreferences("sales", jobj.getString("sales").toString().trim().equalsIgnoreCase("null") || jobj.getString("sales").toString().trim().length() == 0 ? "0" : jobj.getString("sales"));
                            utils.savePreferences("target", jobj.getString("targetamount").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetamount").toString().trim().length() == 0 ? "0" : jobj.getString("targetamount"));
                            utils.savePreferences("achieve", jobj.getString("targetachieved").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetachieved").toString().trim().length() == 0 ? "0" : jobj.getString("targetachieved"));
                            utils.savePreferences("balance", jobj.getString("targetbalance").toString().trim().equalsIgnoreCase("null") || jobj.getString("targetbalance").toString().trim().length() == 0 ? "0" : jobj.getString("targetbalance"));
                            utils.savePreferences("wallet", jobj.getString("wallet").toString().trim().equalsIgnoreCase("null") || jobj.getString("wallet").toString().trim().length() == 0 ? "0" : jobj.getString("wallet"));
                            utils.savePreferences("newvideo", jobj.getString("newvideo").toString().trim().equalsIgnoreCase("null") || jobj.getString("newvideo").toString().trim().length() == 0 ? "0" : jobj.getString("newvideo"));
                            utils.savePreferences("today_reward", jobj.getString("today_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("today_reward").toString().trim().length() == 0 ? "0" : jobj.getString("today_reward"));
                            utils.savePreferences("total_reward", jobj.getString("total_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("total_reward").toString().trim().length() == 0 ? "0" : jobj.getString("total_reward"));
                            utils.savePreferences("available_reward", jobj.getString("available_reward").toString().trim().equalsIgnoreCase("null") || jobj.getString("available_reward").toString().trim().length() == 0 ? "0" : jobj.getString("available_reward"));
                            amount=(String.format("%.2f", Double.parseDouble(utils.loadCommition())));
                            gpv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadgbv())) + "");
                            ibv=(String.format("Rs.%.2f", Double.parseDouble(utils.loadIbv())) + "");
                            purchase=(String.format("Rs.%.2f", Double.parseDouble(utils.loadPurchase())) + "");
                            sales=(String.format("Rs.%.2f", Double.parseDouble(utils.loadSales())) + "");
                            plan=jobj.getString("plan").toString().trim().equalsIgnoreCase("null") || jobj.getString("plan").toString().trim().length() == 0 ? "0" : jobj.getString("plan");
                            Log.i("plan",plan);
                            target=(utils.loadTarget());
                            achieve=(utils.loadAchieve());
                            balance=(utils.loadBalance());
                            wallet_amt=(utils.loadWallet());
                            todayreward=(utils.today_reward());
                            totalreward=(utils.total_reward());
                            available_reward=(utils.available_reward());
                            newvideo=(jobj.getString("newvideo"));
                        }
                        /*new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity

                            }
                        }, SPLASH_TIME_OUT);*/
                        if (dash){
                            Intent i=new Intent(Tableview.this, HomePage.class);
                            i.putExtra("amount",amount);
                            i.putExtra("gpv",gpv);
                            i.putExtra("ibv",ibv);
                            i.putExtra("plan",plan);
                            i.putExtra("purchase",purchase);
                            i.putExtra("sales",sales);
                            i.putExtra("target",target);
                            i.putExtra("achieve",achieve);
                            i.putExtra("balance",balance);
                            i.putExtra("wallet_amt",wallet_amt);
                            i.putExtra("todayreward",todayreward);
                            i.putExtra("totalreward",totalreward);
                            i.putExtra("available_reward",available_reward);
                            startActivity(i);

                            // close this activity
                            finish();
                        }else {
                            //if ((clickupdate.equals("false") && respupdate.equals("false")) || (respupdate.equals("true")) && (clickupdate.equals("true"))) {
                                if (newvideo.equals("1")) {
                                    Intent i = new Intent(Tableview.this, Videoimage.class);
                                    i.putExtra("amount", amount);
                                    i.putExtra("gpv", gpv);
                                    i.putExtra("ibv", ibv);
                                    i.putExtra("purchase", purchase);
                                    i.putExtra("sales", sales);
                                    i.putExtra("target", target);
                                    i.putExtra("achieve", achieve);
                                    i.putExtra("balance", balance);
                                    i.putExtra("wallet_amt", wallet_amt);
                                    i.putExtra("todayreward", todayreward);
                                    i.putExtra("totalreward", totalreward);
                                    i.putExtra("available_reward", available_reward);

                                    startActivity(i);

                                    // close this activity
                                    finish();
                                } else {
                                    Intent i = new Intent(Tableview.this, Videoimage2.class);
                                    i.putExtra("amount", amount);
                                    i.putExtra("gpv", gpv);
                                    i.putExtra("ibv", ibv);
                                    i.putExtra("purchase", purchase);
                                    i.putExtra("sales", sales);
                                    i.putExtra("target", target);
                                    i.putExtra("achieve", achieve);
                                    i.putExtra("balance", balance);
                                    i.putExtra("wallet_amt", wallet_amt);
                                    i.putExtra("todayreward", todayreward);
                                    i.putExtra("totalreward", totalreward);
                                    i.putExtra("available_reward", available_reward);

                                    startActivity(i);

                                    // close this activity
                                    finish();
                                }
                            /*} else {
                                new Handler().removeCallbacks(null);
                            }*/
                        }

                    } else {
                        Toast.makeText(Tableview.this, "Username Mismatch.Please login.", Toast.LENGTH_SHORT).show();
                        utils.savePreferences("name", "");
                        utils.savePreferences("image", "");
                        utils.savePreferences("doj", "");
                        utils.savePreferences("designation", "");
                        utils.savePreferences("ibv", "");
                        utils.savePreferences("gbv", "");
                        utils.savePreferences("commition", "");
                        startActivity(new Intent(Tableview.this, Login.class));
                    }
                } else {
                    retry.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //Log.e("err",e.toString());
                retry.show();
            }
        }
    }



    private class PerformVersionTask extends AsyncTask<Void, String ,String> {

        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";

        protected void onPreExecute() {
            progbar.show();
            Log.i("PerformVersionTask", "started");
            //logger.info("PerformVersionTask started");
        }

        @Override
        protected String doInBackground(final Void... arg0) {
            Log.i("PerformVersionTask", "center");
            try{
                Connection con = new Connection();
                resp = con.connStringResponse(domain+"version.json");

               // Log.e("RESP VAl",resp);
                JSONArray array=new JSONArray(resp);

                JSONObject obj = array.getJSONObject(0);

            }catch(Exception e1){
                e1.printStackTrace();
                Log.e("CATCh",e1.toString());
                //logger.info("PerformVersionTask resp"+e1.getMessage());
                resp="false";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {
            //logger.info("PerformVersionTask final "+resp);
            progbar.dismiss();
            String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
            utils.savePreferences("vdate",today);
            //Log.e("Performversionresp", resp);
            if(resp.equals("false"))
            {
                clickupdate="false";

            }
            else if(resp.equals("true")){
                try {
                    final Dialog update = new Dialog(Tableview.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    View v=getLayoutInflater().inflate(R.layout.app_update_dialog,null);
                    TextView updatebut=(TextView) v.findViewById(R.id.update);
                    TextView laterbut=(TextView) v.findViewById(R.id.later);
                    TextView titlename=(TextView) v.findViewById(R.id.titlename);
                    update.setContentView(v);
                    update.setCancelable(false);
                    update.show();
                    titlename.setText("New Version 1."+Number);
                    TextView content=(TextView)v.findViewById(R.id.content);
                    content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickupdate="true";
                            update.dismiss();
                            Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                            startActivity(intt);
                        }
                    });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                }catch(Exception e){
                    //logger.info("PerformVersionTask error" + e.getMessage());
                }
            }

        }
    }

    public class VideosImage extends AsyncTask<String,String,String>
    {
        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                if(utils.loadcountry().equals("Asia/Kolkata")){

                    if(Build.VERSION.SDK_INT>=21&&Build.VERSION.SDK_INT<=23) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW1, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW1 + "    " + jobj.toString() + "");
                        vdeoresp="1";
                        countres=countres+1;
                        origin_count="4";
                        origin_domain=Appconstants.domain3.toString();
                    }
                    else if(Build.VERSION.SDK_INT>23&&Build.VERSION.SDK_INT<=25) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW2, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW2 + "    " + jobj.toString() + "");
                        vdeoresp="2";
                        countres=countres+1;
                        origin_domain=Appconstants.domain78.toString();
                        origin_count="3";

                    }
                    else if(Build.VERSION.SDK_INT>25&&Build.VERSION.SDK_INT<=27) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW3, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW3 + "    " + jobj.toString() + "");
                        vdeoresp="3";
                        countres=countres+1;
                        origin_domain=Appconstants.domain78.toString();
                        origin_count="3";
                    }
                    else if(Build.VERSION.SDK_INT==28) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW4, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW4 + "    " + jobj.toString() + "");
                        vdeoresp="4";
                        countres=countres+1;
                        origin_domain=Appconstants.domains.toString();
                        origin_count="2";
                    }
                    else if(Build.VERSION.SDK_INT>=29) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW5, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW5 + "    " + jobj.toString() + "");
                        vdeoresp="5";
                        countres=countres+1;
                        origin_domain=Appconstants.domain10.toString();
                        origin_count="1";
                    }

                }
                else{
                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
               e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {
                //System.out.println("resp vid : "+resps);

                if(resps!=null) {
                   // progbar.dismiss();
                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);
                    videoresp = resps;

                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));


                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }


                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);



                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else if (versionCode < Number){
                            resp = "true";
                            respupdate = "true";

                        }else {
                            resp = "false";
                            respupdate = "false";
                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });

                            } catch (Exception e) {
                                //logger.info("PerformVersionTask error" + e.getMessage());
                            }
                        }

                    } else if (jobj.getString("Status").equals("Failure")) {


                    }


                    /*Intent i=new Intent(Videoimage.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);*/
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                 /*  if(countres>=4) {
                       if (vdeoresp.equals("1")) {
                           VideosImage_2 task = new VideosImage_2();
                           task.execute();
                       } else if (vdeoresp.equals("2")) {
                           VideosImage_3 task = new VideosImage_3();
                           task.execute();
                       } else if (vdeoresp.equals("3")) {
                           VideosImage_4 task = new VideosImage_4();
                           task.execute();
                       } else if (vdeoresp.equals("4")) {
                           VideosImage_5 task = new VideosImage_5();
                           task.execute();
                       } else if (vdeoresp.equals("5")) {
                           VideosImage_1 task = new VideosImage_1();
                           task.execute();
                       }*/
                   }
                  /* else{
                       Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(Tableview.this, HomePage.class);
                       i.putExtra("amount", amount);
                       i.putExtra("gpv", gpv);
                       i.putExtra("ibv", ibv);
                       i.putExtra("purchase", purchase);
                       i.putExtra("sales", sales);
                       i.putExtra("target", target);
                       i.putExtra("achieve", achieve);
                       i.putExtra("balance", balance);
                       i.putExtra("wallet_amt", wallet_amt);
                       i.putExtra("todayreward", todayreward);
                       i.putExtra("totalreward", totalreward);
                       i.putExtra("available_reward", available_reward);
                       startActivity(i);
                       finish();
                   }*/

            } catch (JSONException e) {

                //Log.e("waterr", e.toString());

                /*if(e.toString().contains("Value Too")){
                    Videoimage.VideosImage video=new Videoimage.VideosImage();
                    video.execute();
                }
                else {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Unable to get your video.\nCheck your internet connection.");
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent i = new Intent(Videoimage.this, Tableview.class);
                            startActivity(i);
                            finish();

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();

                    e.printStackTrace();
                }
            }*/
            }
        }
    }


    public class VideosImage_1 extends AsyncTask<String, String, String>
    {
        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());


                if(utils.loadcountry().equals("Asia/Kolkata")){


                    //if(Build.VERSION.SDK_INT>=21&&Build.VERSION.SDK_INT<=23) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW1, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW1 + "    " + jobj.toString() + "");
                        vdeoresp="1";
                    countres=countres+1;
                    origin_domain=Appconstants.domain3.toString();
                    origin_count="4";

                    // }
                    /*else if(Build.VERSION.SDK_INT>23&&Build.VERSION.SDK_INT<=25) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW2, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW2 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT>25&&Build.VERSION.SDK_INT<=27) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW3, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW3 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT==28) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW4, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW4 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT==29) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW5, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW5 + "    " + jobj.toString() + "");

                    }*/

                }
                else{
                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {
                //System.out.println("resp vid : "+resps);

                if(resps!=null) {
                    // progbar.dismiss();
                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);

                    videoresp = resps;
                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));
                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }

                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);


                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else {
                            resp = "true";
                            respupdate = "true";

                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                            } catch (Exception e) {
                                //logger.info("PerformVersionTask error" + e.getMessage());
                            }
                        }


                    } else if (jobj.getString("Status").equals("Failure")) {


                    }




                    /*Intent i=new Intent(Videoimage.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);*/
                }
                else {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                    /*if(countres>=4) {

                    if(vdeoresp.equals("1")){
                        VideosImage_2 task = new VideosImage_2();
                        task.execute();
                    }
                    else if(vdeoresp.equals("2")){
                        VideosImage_3 task = new VideosImage_3();
                        task.execute();
                    }
                    else if(vdeoresp.equals("3")){
                        VideosImage_4 task = new VideosImage_4();
                        task.execute();
                    }
                    else if(vdeoresp.equals("4")){
                        VideosImage_5 task = new VideosImage_5();
                        task.execute();
                    }
                    else if(vdeoresp.equals("5")){
                        VideosImage_1 task = new VideosImage_1();
                        task.execute();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tableview.this, HomePage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);
                        startActivity(i);
                        finish();
                    }
                }*/
                }
            } catch (JSONException e) {

                //Log.e("waterr", e.toString());

                /*if(e.toString().contains("Value Too")){
                    Videoimage.VideosImage video=new Videoimage.VideosImage();
                    video.execute();
                }
                else {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Unable to get your video.\nCheck your internet connection.");
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent i = new Intent(Videoimage.this, Tableview.class);
                            startActivity(i);
                            finish();

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();

                    e.printStackTrace();
                }
            }*/
            }
        }
    }

    public class VideosImage_2 extends AsyncTask<String, String, String>
    {
        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());


                if(utils.loadcountry().equals("Asia/Kolkata")){

                    // if(Build.VERSION.SDK_INT>23&&Build.VERSION.SDK_INT<=25) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW2, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW2 + "    " + jobj.toString() + "");
                    vdeoresp="2";
                    countres=countres+1;
                    origin_domain=Appconstants.domain2.toString();

                    origin_count="3";



                   // }
                   /* if(Build.VERSION.SDK_INT>=21&&Build.VERSION.SDK_INT<=23) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW1, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW1 + "    " + jobj.toString() + "");

                    }*/
                    /*
                    else if(Build.VERSION.SDK_INT>25&&Build.VERSION.SDK_INT<=27) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW3, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW3 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT==28) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW4, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW4 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT==29) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW5, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW5 + "    " + jobj.toString() + "");

                    }*/

                }
                else{
                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {
                //System.out.println("resp vid : "+resps);

                if(resps!=null) {
                    // progbar.dismiss();
                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);

                    videoresp = resps;
                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));
                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }


                        //name = obj.getString("data");
                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);


                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else {
                            resp = "true";
                            respupdate = "true";

                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                            } catch (Exception e) {
                                //logger.info("PerformVersionTask error" + e.getMessage());
                            }
                        }


                    } else if (jobj.getString("Status").equals("Failure")) {


                    }




                    /*Intent i=new Intent(Videoimage.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);*/
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                    /*if(countres>=4) {

                    if(vdeoresp.equals("1")){
                        VideosImage_2 task = new VideosImage_2();
                        task.execute();
                    }
                    else if(vdeoresp.equals("2")){
                        VideosImage_3 task = new VideosImage_3();
                        task.execute();
                    }
                    else if(vdeoresp.equals("3")){
                        VideosImage_4 task = new VideosImage_4();
                        task.execute();
                    }
                    else if(vdeoresp.equals("4")){
                        VideosImage_5 task = new VideosImage_5();
                        task.execute();
                    }
                    else if(vdeoresp.equals("5")){
                        VideosImage_1 task = new VideosImage_1();
                        task.execute();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tableview.this, HomePage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);
                        startActivity(i);
                        finish();
                    }*/
                }
            } catch (JSONException e) {

                //Log.e("waterr", e.toString());

                /*if(e.toString().contains("Value Too")){
                    Videoimage.VideosImage video=new Videoimage.VideosImage();
                    video.execute();
                }
                else {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Unable to get your video.\nCheck your internet connection.");
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent i = new Intent(Videoimage.this, Tableview.class);
                            startActivity(i);
                            finish();

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();

                    e.printStackTrace();
                }
            }*/
            }
        }
    }

    public class VideosImage_3 extends AsyncTask<String, String, String>
    {
        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());


                if(utils.loadcountry().equals("Asia/Kolkata")){


                    result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW3, jobj, "");
                    Log.i("Videosinput", Appconstants.VIDEOVIEW3 + "    " + jobj.toString() + "");
                    vdeoresp="3";
                    countres=countres+1;
                    origin_domain=Appconstants.domain2.toString();
                    origin_count="3";

                    // if(Build.VERSION.SDK_INT>23&&Build.VERSION.SDK_INT<=25) {
                    /*result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW2, jobj, "");
                    Log.i("Videosinput", Appconstants.VIDEOVIEW2 + "    " + jobj.toString() + "");*/

                    // }
                   /* if(Build.VERSION.SDK_INT>=21&&Build.VERSION.SDK_INT<=23) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW1, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW1 + "    " + jobj.toString() + "");

                    }*/
                    /*
                    else if(Build.VERSION.SDK_INT>25&&Build.VERSION.SDK_INT<=27) {


                    }
                    else if(Build.VERSION.SDK_INT==28) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW4, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW4 + "    " + jobj.toString() + "");

                    }
                    else if(Build.VERSION.SDK_INT==29) {
                        result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW5, jobj, "");
                        Log.i("Videosinput", Appconstants.VIDEOVIEW5 + "    " + jobj.toString() + "");

                    }*/

                }
                else{
                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {
                //System.out.println("resp vid : "+resps);

                if(resps!=null) {
                    // progbar.dismiss();
                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);

                    videoresp = resps;
                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));
                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }


                        //name = obj.getString("data");
                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);


                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else {
                            resp = "true";
                            respupdate = "true";

                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                            } catch (Exception e) {
                                //logger.info("PerformVersionTask error" + e.getMessage());
                            }
                        }


                    } else if (jobj.getString("Status").equals("Failure")) {


                    }




                    /*Intent i=new Intent(Videoimage.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);*/
                }
                else {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_LONG).show();
                    /*if(countres>=4) {

                    if(vdeoresp.equals("1")){
                        VideosImage_2 task = new VideosImage_2();
                        task.execute();
                    }
                    else if(vdeoresp.equals("2")){
                        VideosImage_3 task = new VideosImage_3();
                        task.execute();
                    }
                    else if(vdeoresp.equals("3")){
                        VideosImage_4 task = new VideosImage_4();
                        task.execute();
                    }
                    else if(vdeoresp.equals("4")){
                        VideosImage_5 task = new VideosImage_5();
                        task.execute();
                    }
                    else if(vdeoresp.equals("5")){
                        VideosImage_1 task = new VideosImage_1();
                        task.execute();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tableview.this, HomePage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);
                        startActivity(i);
                        finish();
                    }
                }*/
                }
            } catch (JSONException e) {

                //Log.e("waterr", e.toString());

                /*if(e.toString().contains("Value Too")){
                    Videoimage.VideosImage video=new Videoimage.VideosImage();
                    video.execute();
                }
                else {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Unable to get your video.\nCheck your internet connection.");
                    alert.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent i = new Intent(Videoimage.this, Tableview.class);
                            startActivity(i);
                            finish();

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();

                    e.printStackTrace();
                }
            }*/
            }
        }
    }

    public class VideosImage_4 extends AsyncTask<String,String,String>
    {
        String resp="false";
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());


                if(utils.loadcountry().equals("Asia/Kolkata")){
                    vdeoresp="4";

                    result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW4, jobj, "");
                    Log.i("Videosinput", Appconstants.VIDEOVIEW4 + "    " + jobj.toString() + "");
                    countres=countres+1;
                    origin_domain=Appconstants.domain1.toString();
                    origin_count="2";

                }
                else{
                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {

                if(resps!=null) {
                    // progbar.dismiss();
                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);

                    videoresp = resps;
                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));
                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }

                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);


                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else {
                            resp = "true";
                            respupdate = "true";

                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });

                            } catch (Exception e) {
                            }
                        }


                    } else if (jobj.getString("Status").equals("Failure")) {


                    }

                }
                else{
                  Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                    /*if(countres>=4) {

                    if(vdeoresp.equals("1")){
                        VideosImage_2 task = new VideosImage_2();
                        task.execute();
                    }
                    else if(vdeoresp.equals("2")){
                        VideosImage_3 task = new VideosImage_3();
                        task.execute();
                    }
                    else if(vdeoresp.equals("3")){
                        VideosImage_4 task = new VideosImage_4();
                        task.execute();
                    }
                    else if(vdeoresp.equals("4")){
                        VideosImage_5 task = new VideosImage_5();
                        task.execute();
                    }
                    else if(vdeoresp.equals("5")){
                        VideosImage_1 task = new VideosImage_1();
                        task.execute();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tableview.this, HomePage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);
                        startActivity(i);
                        finish();
                    }*/
                }

            } catch (JSONException e) {

            }
        }
    }

    public class VideosImage_5 extends AsyncTask<String, String, String>
    {
        String resp="false";
        // private String name;
        private AlertDialog dia;
        int Number;
        private int versionNumber;
        String versionname="";
        @Override
        protected void onPreExecute() {
            button4.setTextColor(Color.parseColor("#DC143C"));
            button4.setText("Loading\n...");

            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String result = null;
            Connection con = new Connection();
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());


                if(utils.loadcountry().equals("Asia/Kolkata")){
                    vdeoresp="5";
                    result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW5, jobj, "");
                    Log.i("Videosinput", Appconstants.VIDEOVIEW5 + "    " + jobj.toString() + "");
                    countres=countres+1;
                    origin_domain=Appconstants.domain1.toString();
                    origin_count="1";

                }
                else{

                    result = con.sendHttpPostjson2(Appconstants.VIDEO, jobj, "");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resps) {
            try {

                if(resps!=null) {

                    button4.setEnabled(true);
                    button4.setTextColor(Color.parseColor("#DC143C"));
                    button4.setText("GO TO \nVIDEOPAGE");
                    button4.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_video,0);

                    videoresp = resps;
                    JSONObject jobj = new JSONObject(resps);
                    if (jobj.getString("Status").equals("Success")) {

                        Number = Integer.parseInt(jobj.getString("version"));
                        String logout = jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");

                        if (logout.equals("0") && (logout != null)) {
                            Videosubmit task = new Videosubmit();
                            task.execute();

                        } else {

                        }


                        //name = obj.getString("data");
                        PackageInfo pinfo = null;
                        try {
                            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        int versionCode = BuildConfig.VERSION_CODE;
                        versupdate = "update";
                        utils.savePreferences("version", versupdate);


                        versionNumber = pinfo.versionCode;
                        versionname = pinfo.versionName;

                        //Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                        if (versionCode == Number) {
                            resp = "false";
                            respupdate = "false";

                        } else {
                            resp = "true";
                            respupdate = "true";

                        }

                        progbar.dismiss();
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        utils.savePreferences("vdate", today);
                        // Log.e("Performversionresp", resp);
                        if (resp.equals("false")) {
                            clickupdate = "false";

                        } else if (resp.equals("true")) {
                            try {
                                final Dialog update = new Dialog(Tableview.this);
                                update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                update.getWindow().setBackgroundDrawable(
                                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                View v = getLayoutInflater().inflate(R.layout.app_update_dialog, null);
                                TextView updatebut = (TextView) v.findViewById(R.id.update);
                                TextView laterbut = (TextView) v.findViewById(R.id.later);
                                TextView titlename = (TextView) v.findViewById(R.id.titlename);
                                update.setContentView(v);
                                update.setCancelable(false);
                                update.show();
                                titlename.setText("New Version 1." + Number);
                                TextView content = (TextView) v.findViewById(R.id.content);
                                content.setText("You are using an older version of Online Tv ( version " + versionname + " ). Update now to get the latest features.");
                                updatebut.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        clickupdate = "true";
                                        update.dismiss();
                                        Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                                        startActivity(intt);
                                    }
                                });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                            } catch (Exception e) {
                                //logger.info("PerformVersionTask error" + e.getMessage());
                            }
                        }


                    } else if (jobj.getString("Status").equals("Failure")) {


                    }




                    /*Intent i=new Intent(Videoimage.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);*/
                }
                else{
                    Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_LONG).show();
                    /*if(countres>=4) {

                    if(vdeoresp.equals("1")){
                        VideosImage_2 task = new VideosImage_2();
                        task.execute();
                    }
                    else if(vdeoresp.equals("2")){
                        VideosImage_3 task = new VideosImage_3();
                        task.execute();
                    }
                    else if(vdeoresp.equals("3")){
                        VideosImage_4 task = new VideosImage_4();
                        task.execute();
                    }
                    else if(vdeoresp.equals("4")){
                        VideosImage_5 task = new VideosImage_5();
                        task.execute();
                    }
                    else if(vdeoresp.equals("5")){
                        VideosImage_1 task = new VideosImage_1();
                        task.execute();
                    }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Unable to load video",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Tableview.this, HomePage.class);
                        i.putExtra("amount", amount);
                        i.putExtra("gpv", gpv);
                        i.putExtra("ibv", ibv);
                        i.putExtra("purchase", purchase);
                        i.putExtra("sales", sales);
                        i.putExtra("target", target);
                        i.putExtra("achieve", achieve);
                        i.putExtra("balance", balance);
                        i.putExtra("wallet_amt", wallet_amt);
                        i.putExtra("todayreward", todayreward);
                        i.putExtra("totalreward", totalreward);
                        i.putExtra("available_reward", available_reward);
                        startActivity(i);
                        finish();
                    }*/
                }

            } catch (JSONException e) {


            }
        }
    }

    public class Videosubmit extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {

            prog = new Dialog(Tableview.this);
            prog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            prog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            prog.setContentView(R.layout.upgrade);
            prog.setCancelable(false);
            prog.show();

        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
           // int versionCode = BuildConfig.VERSION_CODE;

            try
            {
                JSONObject jobj = new JSONObject();

                jobj.put("uname", utils.loadName());


                //  Log.i("Videossubmitinput", Appconstants.logout + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.logout, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            // Log.e("Videossubmitresp",resp);

            try
            {
                JSONObject jobj = new JSONObject(resp);
                if(jobj.getString("Status").equals("Success"))
                {
                    prog.dismiss();

                    android.app.AlertDialog.Builder alert=new android.app.AlertDialog.Builder(Tableview.this);
                    alert.setCancelable(false);
                    alert.setTitle("Session Expired");
                    alert.setMessage("Your session expired.\nKindly login again.");
                    alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            utils.savePreferences("name", "");
                            utils.savePreferences("image", "");
                            utils.savePreferences("doj", "");
                            utils.savePreferences("designation", "");
                            utils.savePreferences("ibv", "");
                            utils.savePreferences("gbv", "");
                            utils.savePreferences("commition", "");
                            utils.savePreferences("jsonobj", "");

                            Toast.makeText(Tableview.this, "Logged Out.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Tableview.this, Login.class));
                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();



                    //JSONArray jarr=jobj.getJSONArray("Response");
                    //jsonobjarr.set(countpos,jarr.getJSONObject(0));
                    //utils.savePreferences("jsonobj",jsonobjarr.toString());

                    //Toast.makeText(Videoimage.this,"Congratulations! you are now eligible for reward points",Toast.LENGTH_LONG).show();

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }



}
