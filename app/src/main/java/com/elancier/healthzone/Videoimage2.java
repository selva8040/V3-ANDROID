package com.elancier.healthzone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.elancier.healthzone.Adapter.RecyclerAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.CheckNetwork;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.Recyclerbo;
import com.elancier.healthzone.netlistener.services.netlisten;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Videoimage2 extends AppCompatActivity implements netlisten.NetworkStateReceiverListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    RecyclerView recyclerlist;
    LinearLayout linearbody;
    LinearLayout progress_lay, retry_lay, layoutlay;
    WebView videoView;
    VideoView videoViewfull;
    Button fullsize, previous, play, next;
    String comp="";
    private netlisten networkStateReceiver;
    String vidval="";

    String urls="";

    ImageView img;
    String cls="";
    TextView videotitle, videodescription, countdownText, nodata, retry, skip;
    private static CountDownTimer countDownTimer;
    String getMinutes;
    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("https://drive.google.com/file/d/1IYu1uepy2V7k9tMGwQ2CBkvmPPs1YYZ8/view"));
    int index = 0;
    AlertDialog m;

    Utils utils;
    Dialog progbar;
    String id = "";
    TextView points;
    int imgnoofminutes;
    AudioManager audioManager;
    String skippedvalue = "0";
    String insidethis="";
    String middle="";
    String middle1="";
    String sdate="";

    String edate="";


    String contlan="";
    String contlang1="";

    RecyclerAdapter itemsAdapter;
    private List<Object> mRecyclerItems = new ArrayList<>();
    private List<Recyclerbo> productItems;


    LinearLayout quiz_lay;
    TextView quizquestion;
    RadioGroup quizgroup;
    RadioButton ansone, anstwo, ansthree, ansfour;
    Button submit_btn;
    String videocomplete = "";

    SliderLayout sliderLayout ;

    SharedPreferences shp;
    SharedPreferences.Editor edit;
    String popupstatus;
    String quizcomplete = "";
    String fname;
    String s="";
    List<String> HashMapForURL ;
    IntentFilter intentFilter=null;

    String amount="";
    String gpv="";
    String ibv="";
    String purchase="";
    String sales="";
    String target="";
    String achieve="";
    String balance="";
    String wallet_amt="";
    String todayreward="";
    String totalreward="";
    String available_reward="";



    //NetworkChangeReceiver receiver=null;
/*
    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(receiver);


    }*/

    public void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }
    String deviceId = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoimage);

        networkStateReceiver = new netlisten();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        getSupportActionBar().setTitle("V3 Online TV");


        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        shp = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        popupstatus = shp.getString("Popupstatus", "");

        utils = new Utils(getApplicationContext());
        utils = new Utils(getApplicationContext());

        Log.e("Custname", utils.loadName());
        fname=utils.loadName();

        recyclerlist = (RecyclerView) findViewById(R.id.recyclerlist);

        recyclerlist.setLayoutManager(new LinearLayoutManager(this));
        recyclerlist.setItemAnimator(new DefaultItemAnimator());

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        linearbody = (LinearLayout) findViewById(R.id.linearbody);
        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        layoutlay = (LinearLayout) findViewById(R.id.layoutlay);
        layoutlay.setVisibility(View.GONE);

        videoView = (WebView) findViewById(R.id.webView);
        videoViewfull = (VideoView) findViewById(R.id.videoViewfull);

        fullsize = (Button) findViewById(R.id.fullsize);
        previous = (Button) findViewById(R.id.previous);
        play = (Button) findViewById(R.id.play);
        next = (Button) findViewById(R.id.next);

        img = (ImageView) findViewById(R.id.img);

        countdownText = (TextView) findViewById(R.id.countdownText);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);
        skip = (TextView) findViewById(R.id.skip);
        points = (TextView) findViewById(R.id.points);
        points.setVisibility(View.GONE);

        quiz_lay = (LinearLayout) findViewById(R.id.quiz_lay);
        quizgroup = (RadioGroup) findViewById(R.id.quizgroup);
        quizquestion = (TextView) findViewById(R.id.quizquestion);
        ansone = (RadioButton) findViewById(R.id.ansone);
        anstwo = (RadioButton) findViewById(R.id.anstwo);
        ansthree = (RadioButton) findViewById(R.id.ansthree);
        ansfour = (RadioButton) findViewById(R.id.ansfour);
        submit_btn = (Button) findViewById(R.id.submit_btn);

        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
        HashMapForURL = new ArrayList<String>();
        try {

            amount=getIntent().getStringExtra("amount");
            gpv=getIntent().getStringExtra("gpv");
            ibv=getIntent().getStringExtra("ibv");
            purchase=getIntent().getStringExtra("purchase");
            sales=getIntent().getStringExtra("sales");
            target=getIntent().getStringExtra("target");
            achieve=getIntent().getStringExtra("achieve");
            balance=getIntent().getStringExtra("balance");
            wallet_amt=getIntent().getStringExtra("wallet_amt");
            todayreward=getIntent().getStringExtra("todayreward");
            totalreward=getIntent().getStringExtra("totalreward");
            available_reward=getIntent().getStringExtra("available_reward");

        }
        catch (Exception e){

        }
        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        s="OS API "+android.os.Build.VERSION.SDK_INT+"- Version "+System.getProperty("os.version");


        vidval=utils.loadvideourl();


        if(CheckNetwork.isInternetAvailable(Videoimage2.this))
        {
            if(vidval.isEmpty()||vidval.equals("null")||vidval==null) {

                //VideosImage videos = new VideosImage();
                //videos.execute();

            }
            else{
                try
                {
                    //System.out.println("resp vid : "+resp);
                    JSONObject jobj = new JSONObject(vidval);
                    if(jobj.getString("Status").equals("Success"))
                    {
                        Recyclerlistvalues recycler = new Recyclerlistvalues();
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

                            /*videoView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            videoView.getSettings().setPluginState(WebSettings.PluginState.ON);
                            videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);*/

                                videoView.loadData(frameVideo, "text/html", "utf-8");


                            /*Uri uri = Uri.parse(String.valueOf(Uri.parse((arrayList.get(index)))));
                            videoView.setVideoURI(Uri.parse(url));
                            videoView.start();*/

                            /*videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
                                        startActivity(new Intent(Videoimage2.this, HomePage.class));
                                    }
                                    else if(quizcomplete.equals("complete"))
                                    {

                                    }

                                    //startActivity(new Intent(Videoimage2.this, HomePage.class));
                                    if(CheckNetwork.isInternetAvailable(Videoimage2.this))
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
                            });*/
                            }
                            else if(type.equals("Image"))
                            {
                                img.setVisibility(View.VISIBLE);
                                Picasso.with(Videoimage2.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                                getMinutes = seconds;
                                //Check validation over edittext
                                if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                    imgnoofminutes = Integer.parseInt(getMinutes)*1000;
                                    Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                                    imgstarttimer(imgnoofminutes, count);
                                }
                            }
                        }
                    }
                    else if(jobj.getString("Status").equals("Failure"))
                    {
                        Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please turn on your network1",Toast.LENGTH_LONG).show();

            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }


        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //receiver = new NetworkChangeReceiver();

        /*if(NetworkUtil.INSTANCE.getConnectivityStatus(Videoimage2.this) > 0)
        {

        }
        else{

        }*/

        linearbody = (LinearLayout) findViewById(R.id.linearbody);

        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);


        //addLogText(NetworkUtil.INSTANCE.getConnectivityStatusString(Videoimage2.this));



        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                {
                    linearbody.setVisibility(View.VISIBLE);
                    retry_lay.setVisibility(View.GONE);

                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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

                }
                else
                {
                    linearbody.setVisibility(View.GONE);
                    retry_lay.setVisibility(View.VISIBLE);
                }
            }
        });




        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip.setEnabled(false);
                stopCountdown();
                if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                {
                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                }
                else
                {
                    linearbody.setVisibility(View.GONE);
                    retry_lay.setVisibility(View.VISIBLE);
                }
            }
        });

        itemsAdapter = new RecyclerAdapter(mRecyclerItems, getApplicationContext(), new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(final View view, final int position, int viewType) {
                final Recyclerbo item = (Recyclerbo) mRecyclerItems.get(position);
            }
        });
        recyclerlist.setAdapter(itemsAdapter);



    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }




    public class VideosImage extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            progbar.show();
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

                Log.i("Videosinput", Appconstants.VIDEOVIEW + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            try
            {
                //System.out.println("resp vid : "+resp);
                JSONObject jobj = new JSONObject(resp);
                if(jobj.getString("Status").equals("Success"))
                {
                    Recyclerlistvalues recycler = new Recyclerlistvalues();
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

                            /*videoView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                            videoView.getSettings().setPluginState(WebSettings.PluginState.ON);
                            videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);*/

                            videoView.loadData(frameVideo, "text/html", "utf-8");


                            /*Uri uri = Uri.parse(String.valueOf(Uri.parse((arrayList.get(index)))));
                            videoView.setVideoURI(Uri.parse(url));
                            videoView.start();*/

                            /*videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
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
                                        startActivity(new Intent(Videoimage2.this, HomePage.class));
                                    }
                                    else if(quizcomplete.equals("complete"))
                                    {

                                    }

                                    //startActivity(new Intent(Videoimage2.this, HomePage.class));
                                    if(CheckNetwork.isInternetAvailable(Videoimage2.this))
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
                            });*/
                        }
                        else if(type.equals("Image"))
                        {
                            img.setVisibility(View.VISIBLE);
                            Picasso.with(Videoimage2.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                            getMinutes = seconds;
                            //Check validation over edittext
                            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                imgnoofminutes = Integer.parseInt(getMinutes)*1000;
                                Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                                imgstarttimer(imgnoofminutes, count);
                            }
                        }
                    }
                }
                else if(jobj.getString("Status").equals("Failure"))
                {
                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public class Recyclerlistvalues extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            Log.e("Recyclerlist", "Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                Log.i("recyclerlistinput", Appconstants.RECYCLER_VALUES + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.RECYCLER_VALUES, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            productItems = new ArrayList<>();
            if(progbar.isShowing()&&progbar!=null){
                progbar.dismiss();
            }

            try {
                //Log.e("recycleval",resp);
                if (resp != null || (!resp.equals("null"))) {

                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        recyclerlist.setVisibility(View.VISIBLE);
                        JSONArray jarray = jobj.getJSONArray("Response");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobject = jarray.getJSONObject(i);
                            String id = jobject.getString("id");
                            String type = jobject.getString("type");
                            String url = jobject.getString("url");
                            String desc = jobject.getString("desc");
                            String total_reward = jobject.getString("total_reward");
                            String count = jobject.getString("count");
                            urls = url;
                           // Log.e("urls", urls);
                            productItems.add(new Recyclerbo(id, type, url, desc, total_reward, count));
                        }


                        if (!urls.isEmpty()) {

                            final List<String> imglist = Arrays.asList(urls.split(","));

                            Log.e("imglist_val", String.valueOf(imglist.size()));
                            for (int i = 0; i < imglist.size(); i++) {

                                if (!imglist.get(i).isEmpty()) {
                                    HashMapForURL.add(imglist.get(i));
                                    Log.e("valhash", HashMapForURL.toString());
                                } else {
                                    Log.e("valelse", HashMapForURL.toString());

                                }
                            }

                            if (!HashMapForURL.isEmpty()) {
                                Log.e("inside slide", "inside slide");
                                for (int j = 0; j < HashMapForURL.size(); j++) {

                                    TextSliderView textSliderView = new TextSliderView(Videoimage2.this);

                                    textSliderView
                                            .description(String.valueOf(j + 1))
                                            .image(HashMapForURL.get(j))
                                            .setScaleType(BaseSliderView.ScaleType.Fit)
                                            .setOnSliderClickListener(Videoimage2.this);

                                    textSliderView.bundle(new Bundle());

                                    textSliderView.getBundle()
                                            .putString("extra", String.valueOf(j + 1));

                                    sliderLayout.addSlider(textSliderView);
                                }
                                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);

                                //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                                //sliderLayout.setCustomAnimation(new DescriptionAnimation());

                                sliderLayout.setDuration(30000);

                                sliderLayout.setCurrentPosition(0);
                                sliderLayout.stopAutoCycle();
                                sliderLayout.startAutoCycle(30000, 30000, true);


                                sliderLayout.addOnPageChangeListener(Videoimage2.this);
                            }


                        }
                        Quizasync quiz = new Quizasync();
                        quiz.execute();
                    } else if (jobj.getString("Status").equals("Failure")) {
                        String Response = jobj.getString("Response");
                       // Log.e("Response", Response);

                        Quizasync quiz = new Quizasync();
                        quiz.execute();
                    }
                    mRecyclerItems.addAll(productItems);
                    itemsAdapter.notifyDataSetChanged();
                }
            }
            catch (JSONException e)
            {
                Log.e("catchbval",e.toString());
                e.printStackTrace();
            }
        }
    }

    public class Quizasync extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            Log.e("Recyclerquiz", "Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                Log.i("recyclerquesinput", Appconstants.RECYCLER_QUES + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.RECYCLER_QUES, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {

            if(progbar.isShowing()&&progbar!=null){
                progbar.dismiss();
            }

            try {
                //Log.e("recyclerquesresp", resp);

                if (resp != null||(!resp.equals("null"))) {
                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        JSONArray jarray = jobj.getJSONArray("Response");
                        for (int i = 0; i < jarray.length(); i++) {
                            JSONObject jobject = jarray.getJSONObject(i);
                            final String id = jobject.getString("id");
                            String type = jobject.getString("type");
                            String ques = jobject.getString("ques");
                            String ans1 = jobject.getString("ans1");
                            String ans2 = jobject.getString("ans2");
                            String ans3 = jobject.getString("ans3");
                            String ans4 = jobject.getString("ans4");
                            String crt = jobject.getString("crt");
                            String total_reward = jobject.getString("total_reward");
                            String count = jobject.getString("count");

                            quizquestion.setText(ques);
                            ansone.setText(ans1);
                            anstwo.setText(ans2);
                            ansthree.setText(ans3);
                            ansfour.setText(ans4);

                            ansone.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    edit = shp.edit();
                                    edit.putString("Popupstatus", ansone.getText().toString().trim());
                                    edit.commit();
                                }
                            });

                            anstwo.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    edit = shp.edit();
                                    edit.putString("Popupstatus", anstwo.getText().toString().trim());
                                    edit.commit();
                                }
                            });

                            ansthree.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    edit = shp.edit();
                                    edit.putString("Popupstatus", ansthree.getText().toString().trim());
                                    edit.commit();
                                }
                            });

                            ansfour.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    edit = shp.edit();
                                    edit.putString("Popupstatus", ansthree.getText().toString().trim());
                                    edit.commit();
                                }
                            });

                            submit_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (quizgroup.getCheckedRadioButtonId() == -1) {
                                        Toast.makeText(Videoimage2.this, "Select any one option", Toast.LENGTH_SHORT).show();
                                    } else if (videocomplete.equals("")) {
                                        Toast.makeText(Videoimage2.this, "Once video concluded .., Press OK", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Questionsubmit question = new Questionsubmit();
                                        question.execute(id, popupstatus);
                                    }
                                }
                            });
                        }
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public class Questionsubmit extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            progbar.show();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                jobj.put("qid", param[0]);
                jobj.put("crt", param[1]);

                Log.i("questioninput", Appconstants.QUESTION_SUBMIT + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.QUESTION_SUBMIT, jobj, "");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            // Log.e("questionresp", resp);

            if(progbar.isShowing()&&progbar!=null){
                progbar.dismiss();
            }

            try
            {
                JSONObject jobj = new JSONObject(resp);
                if(jobj.getString("Status").equals("Success"))
                {
                    quizcomplete = "complete";
                    quiz_lay.setVisibility(View.VISIBLE);
                    edit = shp.edit();
                    edit.putString("Popupstatus","");
                    edit.commit();
                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    //Start Countodwn method
    private void startTimer(final int noOfMinutes, final int nomiddle, String count) {
        final String countval = count;

        Log.e("countvalue",countval);
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("noOfMinutes",String.valueOf(noOfMinutes));
                Log.e("noOfMinutesmid",String.valueOf(nomiddle));
                Log.e("middle",String.valueOf(middle));

                long millis = millisUntilFinished;
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownText.setText(hms);//set text

                //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; "+c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                Log.e("noOfMinutes",String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis)));
                Log.e("noOfMinutesmid",String.valueOf(formattedDate));

                if(edate.equals(formattedDate)){
                    skip.setEnabled(false);
                    stopCountdown();
                    if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                    {
                        Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                    }
                    else
                    {
                        linearbody.setVisibility(View.GONE);
                        retry_lay.setVisibility(View.VISIBLE);
                    }
                }



                if(!middle.isEmpty()&&!middle.equals("null")) {
                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage2.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls="cls";
                                        //startActivity(new Intent(Videoimage2.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if(!middle.isEmpty()&&!middle.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if(cls.isEmpty()) {
                                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if(!middle1.isEmpty()&&!middle1.equals("null")) {
                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle1)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage2.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls="cls";
                                        //startActivity(new Intent(Videoimage2.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if(!middle1.isEmpty()&&!middle1.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle1) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if(cls.isEmpty()) {
                                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }


                //Convert milliseconds into hour,minute and seconds

                if(CheckNetwork.isInternetAvailable(Videoimage2.this)) {
                    if (countval.equals("0") && (countdownText.getText().toString().equals("00:00:01"))&&(comp.equals(""))) {
                        comp="in";
                        Log.i("inside count", "video page");
                        popup();



                    }
                }
            }
            public void onFinish() {

                videocomplete = "complete";

                if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                {
                    if(countval.equals("0")&&(countdownText.getText().toString().equals("00:00:01")))
                    {
                        Log.i("inside count","jkj");


                        /*Videosubmit submit = new Videosubmit();
                        submit.execute("completed");*/
                    }
                    else if(countval.equals("1"))
                    {
                        Log.i("inside counts val","jkj");
                        Intent i=new Intent(Videoimage2.this, HomePage.class);
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

                    }
                    else
                    {

                    }
                }
                else
                {

                }

                if(quizcomplete.equals(""))
                {
                    //startActivity(new Intent(Videoimage2.this, HomePage.class));
                }
                else if(quizcomplete.equals("complete"))
                {

                }
                /*countdownTimerText.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                startTimer.setText(getString(R.string.start_timer));//Change button text*/
            }
        }.start();
    }

    private void imgstarttimer(int noOfMinutes, String count) {
        final String countval = count;
        Log.e("countval", countval);
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownText.setText(hms);
            }
            public void onFinish() {


                if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                {
                    if(countval.equals("0"))
                    {
                        Log.i("inside count","jkj");

                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                    }
                    else if(countval.equals("1"))
                    {

                    }
                    else
                    {

                    }
                }
                else
                {

                }
                if(quizcomplete.equals(""))
                {

                    Log.i("inside quiz","jj");
                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                }
                else if(quizcomplete.equals("complete"))
                {

                }
                finish();
            }
        }.start();
    }


    public void  popup(){

        Log.e("loadingpop","logging");
        AlertDialog.Builder alert=new AlertDialog.Builder(Videoimage2.this);
        alert.setCancelable(false);
        alert.setMessage(contlang1);
        alert.setPositiveButton( "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                    }
                });
        m=alert.create();

        m.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
//ToDo your function
//hide your popup here
                try{
                    if(m!=null){
                        m.dismiss();
                    }

                }
                catch (Exception e){

                }
                Intent i=new Intent(Videoimage2.this, HomePage.class);
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

            }
        },9000);
    }

    public class Videosubmit extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            int versionCode = BuildConfig.VERSION_CODE;

            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("vid", id);
                jobj.put("uname", utils.loadName());
                jobj.put("type", param[0]);
                jobj.put("appversion",versionCode);
                jobj.put("mobileos",s);
                jobj.put("deviceid",deviceId);
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
            Log.e("Videossubmitresp", resp);

            try
            {
                JSONObject jobj = new JSONObject(resp);
                if(jobj.getString("Status").equals("Success"))
                {

                    Toast.makeText(Videoimage2.this,"Congratulations! you are now eligible for reward points",Toast.LENGTH_LONG).show();

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            /*linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);*/
            /*Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);*/
            //finish();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        skip.setEnabled(false);
        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage2.this))
        {
            Intent i=new Intent(Videoimage2.this, HomePage.class);
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
        }
        else
        {
            //Toast.makeText(getApplicationContext(),"Please turn on your network connection",Toast.LENGTH_LONG).show();

            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }

        /*Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    //audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
                    Toast.makeText(getApplicationContext(),"Couldn't Reduce Volume",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                skip.setEnabled(false);
                stopCountdown();
                if(CheckNetwork.isInternetAvailable(Videoimage2.this))
                {
                    Intent i=new Intent(Videoimage2.this, HomePage.class);
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
                }
                else
                {
                    // Toast.makeText(getApplicationContext(),"Please turn on your network2",Toast.LENGTH_LONG).show();

                    linearbody.setVisibility(View.GONE);
                    retry_lay.setVisibility(View.VISIBLE);
                }

                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(receiver, intentFilter);


        if(skippedvalue.equals("1"))
        {
            stopCountdown();
            if(CheckNetwork.isInternetAvailable(Videoimage2.this))
            {
                Intent i=new Intent(Videoimage2.this, HomePage.class);
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
            }
            else
            {
                ///Toast.makeText(getApplicationContext(),"Please turn on your network3",Toast.LENGTH_LONG).show();

                linearbody.setVisibility(View.GONE);
                retry_lay.setVisibility(View.VISIBLE);
            }
        }
        else
        {

        }
    }



    //Listens internet status whether net is on/off.



        /*public void addLogText(String log) {
            //linearbody = (LinearLayout) findViewById(R.id.linearbody);

            //retry_lay = (LinearLayout) findViewById(R.id.retry_lay);

            if(log.equals("NOT_CONNECT")){

                Log.e("inside noc",log);

                /// if connection is off then all views becomes disable
                insidethis="nc";
                stopCountdown();
                //Toast.makeText(getA,"Please turn on your network",Toast.LENGTH_LONG).show();




            }
            else
            {
                /// if connection is off then all views becomes enabled

                if(!(insidethis =="")) {
                    insidethis="";
                    startActivity(new Intent(Videoimage2.this, HomePage.class));

                }
            }

            }
*/



    //comment close
   /* @Override
    protected void onRestart() {
        super.onRestart();

        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage2.this))
        {
            startActivity(new Intent(Videoimage2.this, HomePage.class));
        }
        else
        {
            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }
    }*/

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
        skip.setEnabled(false);
        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage2.this))
        {
            Intent i=new Intent(Videoimage2.this, HomePage.class);
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
        }
        else
        {
            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    private void emulateClick(final WebView webview) {
        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = webview.getLeft() + webview.getWidth()/2; //in the middle of the webview
        float y = webview.getTop() + webview.getHeight()/2;

        final MotionEvent downEvent = MotionEvent.obtain( downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0 );
        // change the position of touch event, otherwise, it'll show the menu.
        final MotionEvent upEvent = MotionEvent.obtain( downTime, downTime+ delta, MotionEvent.ACTION_UP, x+10, y+10, 0 );

        webview.post(new Runnable() {
            @Override
            public void run() {
                if (webview != null) {
                    webview.dispatchTouchEvent(downEvent);
                    webview.dispatchTouchEvent(upEvent);
                }
            }
        });
    }
    @Override
    public void networkAvailable() {
        Log.d("tommydevall", "I'm in, baby!");



        /* TODO: Your connection-oriented stuff here */
    }



    @Override
    public void networkUnavailable() {

        stopCountdown();
        //onPause();
        linearbody.setVisibility(View.GONE);
        retry_lay.setVisibility(View.VISIBLE);
        Log.d("tommydevall", "I'm dancing with myself");
        /* TODO: Your disconnection-oriented stuff here */
    }
    /*public void onPause() {
        super.onPause();
        //pause anything else
    }*/

}
