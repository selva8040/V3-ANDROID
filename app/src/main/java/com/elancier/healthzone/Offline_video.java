package com.elancier.healthzone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.CheckNetwork;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.AssetDataSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Offline_video extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    Boolean dash;
    String clickupdate="";
    String respupdate="";
    int ALL_PERMISSIONS = 101;
    Utils utils;
    Dialog retry, update;
     SharedPreferences pref;
    SharedPreferences.Editor editor;


    ImageView retrybut;
    Button retryreport;
    TextView textView35;
    DatePickerDialog picker;
    TableLayout tb1,tb2;
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
    Integer countpos;

    TextView skip;
    Dialog progbar;
    String responseval="";
    String video_responseval="";
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    Button button4;
    Button button5;
    List<JSONObject> jsonobjarr;
    ProgressDialog pdialog;

    String vid="";
    String uname="";
    String type="";
    String appversion="";
    String mobileos="";
    String deviceid="";
    String mobile_model="";


    private static CountDownTimer countDownTimer;
    Uri videoURL = Uri.parse("file:///assets/videoprmos.mp4");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_video);
        progbar =new  Dialog(Offline_video.this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
        exoPlayerView = findViewById(R.id.exo_player_view);
        //String videoURL = "http://blueappsoftware.in/layout_design_android_blog.mp4";
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        pdialog=new ProgressDialog(Offline_video.this);
        pdialog.setMessage("Video Submission...");
        pdialog.setCancelable(false);
        utils=new Utils(getApplicationContext());
        textView35= findViewById(R.id.textView35);
        button4= findViewById(R.id.button4);
        button5= findViewById(R.id.button5);
        pref = Offline_video.this.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        exoPlayerView.hideController();
        exoPlayerView.setControllerVisibilityListener(new PlaybackControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int i) {
                if(i == 0) {
                    exoPlayerView.hideController();
                }
            }
        });

        try{
            vid=getIntent().getExtras().getString("vid");
            uname=getIntent().getExtras().getString("uname");
            type=getIntent().getExtras().getString("type");
            appversion=getIntent().getExtras().getString("appversion");
            mobileos=getIntent().getExtras().getString("mobileos");
           deviceid=getIntent().getExtras().getString("deviceid");
            mobile_model=getIntent().getExtras().getString("mobile_model");
            System.out.println("vidoff"+vid);
        }
        catch (Exception e)
        {

        }

        //prepareExoPlayerFromAssetResourceFile(0);
        int noOfMinutes   = Integer.parseInt("33")*1000;

        startTimer(noOfMinutes,0,"0");

        /*try {

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DefaultTrackSelector trackSelector =  new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            Uri videoURI = Uri.parse(videoURL);
            DefaultHttpDataSourceFactory dataSourceFactory =  new DefaultHttpDataSourceFactory("exoplayer_video");
            DefaultExtractorsFactory extractorsFactory =  new DefaultExtractorsFactory();
            ExtractorMediaSource mediaSource =  new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
            exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            int noOfMinutes   = Integer.parseInt("0.5")*1000;

            startTimer(noOfMinutes,0,"0");

        }catch (Exception e){
            Log.e("MainAcvtivity"," exoplayer error "+ e.toString());
        }*/

        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

                pdialog.show();
                Videosubmit submit = new Videosubmit();
                submit.execute("completed");
               /* AlertDialog.Builder builder = new AlertDialog.Builder(Offline_video.this);
                builder.setMessage("Username :  "+username+"\n\n"+"Mobile      :  "+mobile+"\n\n"+"Time         :  "+currentTime.toString())
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                retry.cancel();

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
                alert.show();*/
            }
        });

        retry.setContentView(v);
        retry.setCancelable(false);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setEnabled(false);
                button4.setTextColor(getResources().getColor(R.color.lightgreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                 //   button4.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_videodis, 0);
                }
                pdialog.show();
                Videosubmit submit = new Videosubmit();
                submit.execute("completed");

                // close this activity
             //   finish();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Offline_video.this, HomePage.class);
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
                exoPlayer.stop();

                // close this activity
                finish();
            }
        });

    }



    private class ReportTask extends AsyncTask<Void, String ,String> {

        String resp="";
        // private String name;


        protected void onPreExecute() {
            progbar.show();
            Log.i("PerformVersionTask", "started");
            //logger.info("PerformVersionTask started");
        }

        @Override
        protected String doInBackground(final Void... arg0) {
            Log.i("PerformVersionTask", "center");

            try{
                String result = null;
                Connection con = new Connection();

                try {
                    JSONObject jobj = new JSONObject();
                    jobj.put("uname", utils.loadName());
                    jobj.put("mobile", utils.loadmob());
                    jobj.put("view_time", currentTime);
                    jobj.put("response", resp);

                    Log.i("HomePage Input", Appconstants.GET_MY_report + "    " + jobj.toString());
                    result = con.sendHttpPostjson2(Appconstants.GET_MY_report, jobj, "");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }catch(Exception e1){
                e1.printStackTrace();
                Log.e("CATCh",e1.toString());
                //logger.info("PerformVersionTask resp"+e1.getMessage());
                //resp="false";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {

            //Log.i("final",resp);

            progbar.dismiss();
            try {
                if (resp != null) {
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        Toast t=Toast.makeText(getApplicationContext(),"Report has been sent successfully",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER,0,0);
                        t.show();
                        onBackPressed();

                    }
                    else{
                        Toast t=Toast.makeText(getApplicationContext(),"Report not send",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.CENTER,0,0);
                        t.show();
                        onBackPressed();

                    }
                }
                else{
                    Toast t=Toast.makeText(getApplicationContext(),"Report not send",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                    onBackPressed();
                }
            }
            catch (Exception e){
                Toast t=Toast.makeText(getApplicationContext(),"Report not send",Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
                onBackPressed();

            }

        }
    }



    /*private void prepareExoPlayerFromAssetResourceFile(int current_file) {

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector((TrackSelection.Factory) null), new DefaultLoadControl());
        //exoPlayer.addListener(eventListener);


        //DataSpec dataSpec = new DataSpec(Uri.parse("asset:///001.mp3"));
        //DataSpec dataSpec = new DataSpec(Uri.parse("assets:///videoprmos.mp4"));
        final AssetDataSource assetDataSource = new AssetDataSource(this);
        try {
            assetDataSource.open(dataSpec);
        } catch (AssetDataSource.AssetDataSourceException e) {
            e.printStackTrace();
        }

        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                //return rawResourceDataSource;
                return assetDataSource;
            }
        };

        MediaSource audioSource = new ExtractorMediaSource(assetDataSource.getUri(),
                factory, new DefaultExtractorsFactory(), null, null);

        exoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare(audioSource);
        exoPlayer.setPlayWhenReady(true);

    }*/


    //Start Countodwn method
    private void startTimer(final int noOfMinutes, final int nomiddle, String count) {
        final String countval = count;

        Log.e("countvalue",countval);
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {

            public void onTick(long millisUntilFinished) {
                //Log.e("noOfMinutes",String.valueOf(noOfMinutes));
                //Log.e("noOfMinutesmid",String.valueOf(nomiddle));
                //Log.e("middle",String.valueOf(middle));

                long millis = millisUntilFinished;
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                //textView35.setText(hms);//set text

                //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                Log.e("noOfMinutes",String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis)));
                Log.e("noOfMinutesmid",String.valueOf(formattedDate));

                int currentapiVersion = android.os.Build.VERSION.SDK_INT;


                if (currentapiVersion <= 26) {
                    // Do something for 14 and above versions

                    if(Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis)))==30){

                        /*Handler handler=new Handler();
                        handler.postDelayed(new Runnable(){
                            public void run(){

                                GetInfoTask task=new GetInfoTask();
                                task.execute();
                            }
                        },1000);*/

                        Handler handler1=new Handler();
                        handler1.postDelayed(new Runnable(){
                            public void run(){
                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                            }
                        },1000);
                    }


                } else {
                    if(Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis)))==20){
                       /* Handler handler=new Handler();
                        handler.postDelayed(new Runnable(){
                            public void run(){

                                GetInfoTask task=new GetInfoTask();
                                task.execute();
                            }
                        },1000);*/

                        Handler handler1=new Handler();
                        handler1.postDelayed(new Runnable(){
                            public void run(){
                                   Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                            }
                        },1000);
                    }
                    // do something for phones running an SDK before 14

                }


            }
            public void onFinish() {

                //videocomplete = "complete";

                button4.setEnabled(true);
                button4.setTextColor(getResources().getColor(R.color.lred));
                button4.setText("Try Again");

                button5.setEnabled(true);
                button5.setTextColor(getResources().getColor(R.color.blue));
                button5.setText("GO TO DASHBOARD");
                //.putExtra("draw",responseval).putExtra("video",video_responseval));
                    //utils.savePreferences("drawresp",responseval);
                //utils.savePreferences("videoresp",video_responseval);



               /* startActivity(new Intent(Offline_video.this,Tableview.class));
                finish();*/
                /*countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text*/
            }
        }.start();
    }



    public class VideosImages extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
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

                Log.i("Videosinput", Appconstants.VIDEOVIEWneew + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOVIEWneew, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String resp) {
            try {

                if (resp != null) {
                    jsonobjarr=new ArrayList<JSONObject>();
                    System.out.println("resp vid : " + resp);
                    video_responseval = resp;
                    utils.savePreferences("videoresp", video_responseval);
                    button4.setEnabled(true);
                    button4.setTextColor(getResources().getColor(R.color.lred));
                    button4.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_video, 0);

                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {

                        JSONArray jarr=new JSONArray();
                        jarr=jobj.getJSONArray("Response");
                        for(int j=0;j<jarr.length();j++){
                            JSONObject jobject = jarr.getJSONObject(j);
                            jsonobjarr.add(jobject);
                            System.out.println("jsonobj "+jsonobjarr);
                        }
                        utils.savePreferences("jsonobj",jsonobjarr.toString());
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());
                        System.out.println("today"+today);
                        utils.savePreferences("vdate",today);
                        utils.savePreferences("version","");

                   /* Videoimage.Recyclerlistvalues recycler = new Videoimage.Recyclerlistvalues();
                    recycler.execute();

                    layoutlay.setVisibility(View.VISIBLE);
                    points.setVisibility(View.GONE);

                    JSONArray jarray = jobj.getJSONArray("Response");
                    for(int j=0; j<jarray.length(); j++)
                    {
                        JSONObject jobject = jarray.getJSONObject(j);
                        id = jobject.getString("id");
                        String type = jobject.getString("type");
                        String url ="";
                        //if(jobject.getString(url).isEmpty()){
                        //    url = "";//https://drive.google.com/file/d/1LoZHD6dvyJvSREd1Cnj3LVITfQJzbdFS/preview
                        //}else {
                        url = jobject.getString("url");
                        //}
                        String point = jobject.getString("point");
                        points.setText("Reward Points : "+point+" Points");
                        sdate=jobject.getString("sdate");
                        edate=jobject.getString("edate");
                        final String seconds = jobject.getString("seconds");
                        try {
                            middle = jobject.getString("middle");
                        }
                        catch (Exception e){
                            middle="";
                        }
                        try {
                            middle1 = jobject.getString("middle1");
                        }
                        catch (Exception e){
                            middle1="";
                        }
                        try {
                            contlan = jobject.getString("content");
                        }
                        catch (Exception e){
                            contlan="";
                        }
                        try {
                            contlang1 = jobject.getString("content1");
                        }
                        catch (Exception e){
                            contlang1="";
                        }



                        final String count = jobject.getString("count");

                        if(type.equals("Video"))
                        {
                            getMinutes = seconds;
                            videoView.setVisibility(View.VISIBLE);
                            String str= url+"?autoplay=1&modestbranding=1&controls=0&fs=0\"\n" +
                                    "        width=\"100%\">";

                            String frameVideo = "<html>\n" +
                                    "<head>\n" +
                                    "</head>\n" +
                                    "<body>\n" +
                                    "<div>\n" +
                                    "<iframe id=\"div1\" allowfullscreen=\"\" frameborder=\"0\" height=\"180\"\n" +
                                    "        src=\"$VIDEO_URL$" +
                                    "</iframe>\n" +
                                    "</div>\n" +
                                    "</body>\n" +
                                    "</html>";
                            videoView.setWebViewClient(new WebViewClient(){
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    return false;
                                }
                            });
                            videoView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageFinished(WebView view, String url) {
                                    super.onPageFinished(view, url);
                                    emulateClick(view);
                                    int noOfMinutes   = Integer.parseInt(getMinutes)*1000;

                                    startTimer(noOfMinutes,0,count);

                                }
                            });
                            WebSettings webSettings = videoView.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            frameVideo = frameVideo.replace("$VIDEO_URL$", str);

                            videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);

                            *//*videoView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            videoView.getSettings().setPluginState(WebSettings.PluginState.ON);
                            videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);*//*

                            videoView.loadData(frameVideo, "text/html", "utf-8");


                            *//*Uri uri = Uri.parse(String.valueOf(Uri.parse((arrayList.get(index)))));
                            videoView.setVideoURI(Uri.parse(url));
                            videoView.start();*//*

                         *//*videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    getMinutes = seconds;
                                    //Check validation over edittext
                                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                        int noOfMinutes = Integer.parseInt(getMinutes)*1000;
                                        Log.e("noOfMinutes", String.valueOf(noOfMinutes));
                                        startTimer(noOfMinutes);
                                    }
                                    skippedvalue = "1";
                                }
                            });

                            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mediaPlayer) {
                                    videocomplete = "complete";

                                    if(quizcomplete.equals(""))
                                    {
                                        startActivity(new Intent(Videoimage.this, HomePage.class));
                                    }
                                    else if(quizcomplete.equals("complete"))
                                    {

                                    }

                                    //startActivity(new Intent(Videoimage.this, HomePage.class));
                                    if(CheckNetwork.isInternetAvailable(Videoimage.this))
                                    {
                                        if(count.equals("0"))
                                        {
                                            Videosubmit submit = new Videosubmit();
                                            submit.execute("completed");
                                        }
                                        else if(count.equals("1"))
                                        {

                                        }
                                        else
                                        {

                                        }
                                    }
                                    else
                                    {
                                        linearbody.setVisibility(View.GONE);
                                        retry_lay.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                                @Override
                                public boolean onError(MediaPlayer mp, int what, int extra) {
                                    Log.e("IssueListener", "What " + what + " extra " + extra);
                                    return false;
                                }
                            });*//*
                        }
                        else if(type.equals("Image"))
                        {
                            img.setVisibility(View.VISIBLE);
                            Picasso.with(Videoimage.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                            getMinutes = seconds;
                            //Check validation over edittext
                            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                imgnoofminutes = Integer.parseInt(getMinutes)*1000;
                                Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                                imgstarttimer(imgnoofminutes, count);
                            }
                        }
                    }*/
                    } else if (jobj.getString("Status").equals("Failure")) {
                   /* Intent i=new Intent(Videoimage.this, HomePage.class);
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
                }
                else{

                    retry.show();
                }
            }

            catch (JSONException e)
            {
                e.printStackTrace();
                retry.show();

            }
        }
    }

    public class Videosubmit extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            int versionCode = com.elancier.healthzone.BuildConfig.VERSION_CODE;
            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("vid", vid);
                jobj.put("uname", utils.loadName());
                jobj.put("type", param[0]);
                jobj.put("appversion",versionCode);
                jobj.put("mobileos",mobileos);
                jobj.put("deviceid",deviceid);
                jobj.put("mobile_model", android.os.Build.MODEL);
                jobj.put("network",getNetworkClass(getApplicationContext()));

                Log.i("Videossubmitinput", Appconstants.VIDEOSUBMIT + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOSUBMIT, jobj, "");
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
                //Log.e("Videossubmitresp", resp);

                if (!resp.equals("null")) {
                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        JSONArray jarr = jobj.getJSONArray("Response");
                        try {

                            utils.savePreferences("countvalue", "1");
                            jsonobjarr.set(countpos, jarr.getJSONObject(0));
                            utils.savePreferences("jsonobj", jsonobjarr.toString());
                        } catch (Exception e) {

                        }
                        pdialog.dismiss();

                        Toast.makeText(Offline_video.this, "Congratulations! you are now eligible for reward points", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Offline_video.this, HomePage.class);
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

                    } else {

                        if(pdialog.isShowing()) {
                            pdialog.dismiss();
                        }
                        Toast.makeText(Offline_video.this, jobj.getString("Response"), Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    button4.setEnabled(true);
                    button4.setTextColor(getResources().getColor(R.color.lred));
                    button4.setText("Try Again");

                    button5.setEnabled(true);
                    button5.setTextColor(getResources().getColor(R.color.blue));
                    button5.setText("GO TO DASHBOARD");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";

            default:
                return "WIFI";
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        //finish();
    }


}
