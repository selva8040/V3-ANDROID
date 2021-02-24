package com.elancier.healthzone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.BuildConfig;
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
import com.elancier.healthzone.Pojo.ReportsPojo;
import com.elancier.healthzone.netlistener.services.netlisten;
import com.squareup.picasso.Picasso;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Videoimage extends AppCompatActivity implements netlisten.NetworkStateReceiverListener, BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    RecyclerView recyclerlist;
    LinearLayout linearbody;
    LinearLayout progress_lay, retry_lay, layoutlay;
    WebView videoView;
    VideoView videoViewfull;
    Button fullsize, previous, play, next;
    String comp = "";
    private netlisten networkStateReceiver;
    String vidval = "";
    List<String> countarray;
    String urls = "";
    ImageView img;
    String cls = "";
    String cls1 = "";
    String cls2 = "";
    String cls3 = "";
    String cls4 = "";
    String cls5 = "";
    String cls6 = "";
    String origin_domain = "";
    String origin_domain1 = "http://v3onlinetv.com/v3app/";
    String video_responseval = "";
    String cutoffstime = "";
    TextView videotitle, videodescription, countdownText, nodata, retry, skip;
    private static CountDownTimer countDownTimer;
    String getMinutes;
    //ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("https://drive.google.com/file/d/1IYu1uepy2V7k9tMGwQ2CBkvmPPs1YYZ8/view"));
    int index = 0;
    AlertDialog m;
    Integer countpos;
    Utils utils;
    Dialog progbar;
    String id = "";
    TextView points;
    int imgnoofminutes;
    AudioManager audioManager;
    String skippedvalue = "0";
    String insidethis = "";
    String middle = "";
    String middle1 = "";
    String middle2 = "";
    String middle3 = "";
    String middle4 = "";
    String middle5 = "";
    String sdate = "";
    List<JSONObject> jsonobjarr;
    String edate = "";
    String contlan = "";
    String contlan_end = "";

    String contlan1 = "நீங்கள் வீடியோ பார்த்து கொண்டிருக்கீர்களா?";
    String contlang11 = "வீடியோவை முழுமையாக பார்த்துவிட்டீர்களா?";

    String contlan2 = "Are you still watching the video ?";
    String contlang22 = "Have you watched the entire video ?";

    String contlan3 = "നിങ്ങൾ വീഡിയോ കാണുന്നുണ്ടോ?";
    String contlang33 = "നിങ്ങൾ വീഡിയോ പൂർണ്ണമായും കണ്ടിട്ടുണ്ടോ?";

    String contlan4 = "ನೀವು ವೀಡಿಯೊ ನೋಡುತ್ತೀರಾ ?";
    String contlang44 = "ನೀವು ವೀಡಿಯೊವನ್ನು ಸಂಪೂರ್ಣವಾಗಿ ನೋಡಿದ್ದೀರಾ?";

    String contlan5 = "మీరు వీడియో చూస్తున్నారా?";
    String contlang55 = "మీరు వీడియోను పూర్తిగా చూశారా?";

    String contlan6 = "क्या आप वीडियो देख रहे हैं ?";
    String contlang66 = "क्या आपने इसकी संपूर्णता में वीडियो देखा है?";


    RecyclerAdapter itemsAdapter;
    private List<Object> mRecyclerItems = new ArrayList<>();
    private List<Recyclerbo> productItems;
    LinearLayout quiz_lay;
    TextView quizquestion;
    RadioGroup quizgroup;
    RadioButton ansone, anstwo, ansthree, ansfour;
    Button submit_btn;
    String videocomplete = "";
    private final int REQ_CODE = 100;

    SliderLayout sliderLayout;
    ConstraintLayout cos;
    ConstraintLayout adview;
    CircleImageView circleImageView;
    ImageButton imageButton5;
    CardView cardad;
    CardView opncard;
    ImageView contimg;
    TextView desc;
    TextView shortcont;
    SharedPreferences shp;
    SharedPreferences.Editor edit;
    String popupstatus;
    String quizcomplete = "";
    String fname;
    String s = "";
    List<String> HashMapForURL;
    IntentFilter intentFilter = null;

    String amount = "";
    String gpv = "";
    String ibv = "";
    String purchase = "";
    String sales = "";
    String target = "";
    String achieve = "";
    String balance = "";
    String wallet_amt = "";
    String todayreward = "";
    String totalreward = "";
    String available_reward = "";
    TextView unamtext, timetext;
    String origin_count = "";
    CardView visit;
    static String realtime = "";
    static String realtimedt = "";
    String dmlang;
    TextView feedrad, compradio, suggradio, permradio, tittext, tittextstar, noticnt;
    EditText feededit;
    Button submitfeed;
    CircleImageView speak;
    String str = "";
    final String[] fradcheck = {""};

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
//        Log.e("Custname", utils.loadName());

        networkStateReceiver = new netlisten();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

//        getSupportActionBar().setTitle("V3 Online TV");


        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        shp = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        popupstatus = shp.getString("Popupstatus", "");

        utils = new Utils(getApplicationContext());
        utils = new Utils(getApplicationContext());

        Log.e("Custname", utils.loadName());
        fname = utils.loadName();

        recyclerlist = (RecyclerView) findViewById(R.id.recyclerlist);

        recyclerlist.setLayoutManager(new LinearLayoutManager(this));
        recyclerlist.setItemAnimator(new DefaultItemAnimator());

        sliderLayout = (SliderLayout) findViewById(R.id.slider);

        linearbody = (LinearLayout) findViewById(R.id.linearbody);
        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        layoutlay = (LinearLayout) findViewById(R.id.layoutlay);
        layoutlay.setVisibility(View.INVISIBLE);

        adview = (ConstraintLayout) findViewById(R.id.adview);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        cardad = (CardView) findViewById(R.id.cardView2);
        opncard = (CardView) findViewById(R.id.opn);
        contimg = (ImageView) findViewById(R.id.contimg);
        desc = (TextView) findViewById(R.id.desc);
        circleImageView = (CircleImageView) findViewById(R.id.circleImageView);
        shortcont = (TextView) findViewById(R.id.textView96);
        videoView = (WebView) findViewById(R.id.webView);
        videoViewfull = (VideoView) findViewById(R.id.videoViewfull);

        fullsize = (Button) findViewById(R.id.fullsize);
        previous = (Button) findViewById(R.id.previous);
        play = (Button) findViewById(R.id.play);
        next = (Button) findViewById(R.id.next);
        unamtext = (TextView) findViewById(R.id.textView37);
        timetext = (TextView) findViewById(R.id.textView38);
        img = (ImageView) findViewById(R.id.img);
        visit = (CardView) findViewById(R.id.cardView4);
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
        cos = (ConstraintLayout) findViewById(R.id.cos);
        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
        HashMapForURL = new ArrayList<String>();


        cardad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opncard.getVisibility() == View.GONE) {
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.sliddown);
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slidup);
                    opncard.startAnimation(slide_down);
                    opncard.setVisibility(View.VISIBLE);
                    imageButton5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24px));
                } else if (opncard.getVisibility() == View.VISIBLE) {
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.sliddown);
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slidup);
                    opncard.startAnimation(slide_up);
                    opncard.setVisibility(View.GONE);

                    imageButton5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24px));
                }
            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opncard.getVisibility() == View.GONE) {
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.sliddown);
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slidup);
                    opncard.startAnimation(slide_down);
                    opncard.setVisibility(View.VISIBLE);
                    imageButton5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24px));
                } else if (opncard.getVisibility() == View.VISIBLE) {
                    Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.sliddown);
                    Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slidup);
                    opncard.startAnimation(slide_up);
                    opncard.setVisibility(View.GONE);

                    imageButton5.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24px));
                }
            }
        });


        try {
            origin_domain = getIntent().getExtras().getString("orgdomain");
            Log.e("origin_domain", origin_domain);
        } catch (Exception e) {
            Log.e("origin_domain", origin_domain);

        }

        try {
            origin_count = getIntent().getExtras().getString("orgcount");
            Log.e("origin_domain", origin_count);
            if (origin_count.equals("1")) {
                getSupportActionBar().setTitle("V3 Online TV .");

            } else if (origin_count.equals("2")) {
                getSupportActionBar().setTitle("V3 Online TV ..");

            } else if (origin_count.equals("3")) {
                getSupportActionBar().setTitle("V3 Online TV ...");

            } else if (origin_count.equals("4")) {
                getSupportActionBar().setTitle("V3 Online TV ....");

            }
        } catch (Exception e) {
            Log.e("origin_domain", origin_count);

        }

        try {

            amount = getIntent().getStringExtra("amount");
            gpv = getIntent().getStringExtra("gpv");
            ibv = getIntent().getStringExtra("ibv");
            purchase = getIntent().getStringExtra("purchase");
            sales = getIntent().getStringExtra("sales");
            target = getIntent().getStringExtra("target");
            achieve = getIntent().getStringExtra("achieve");
            balance = getIntent().getStringExtra("balance");
            wallet_amt = getIntent().getStringExtra("wallet_amt");
            todayreward = getIntent().getStringExtra("todayreward");
            totalreward = getIntent().getStringExtra("totalreward");
            available_reward = getIntent().getStringExtra("available_reward");

        } catch (Exception e) {

        }
        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        s = "OS API " + Build.VERSION.SDK_INT + "- Version " + System.getProperty("os.version");


        //vidval=utils.loadvideourl();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        System.out.println("video" + "njs");
        if (CheckNetwork.isInternetAvailable(Videoimage.this)) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    VideosImage videos = new VideosImage();
                    videos.execute();
                }
            }, 1000);

               /* }
                else{
                    VideosImage videos = new VideosImage();
                    videos.execute();

                    *//*try {
                        printTimes();d
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("excep",e.toString());
                    }*//*
             *//*

                    System.out.println("realtimes"+realtime);
                    playvideo task = new playvideo();
                    task.execute();*//*
                }

             }
              },2000);*/
            /*if(vidval.isEmpty()||vidval.equals("null")||vidval==null) {

                //VideosImage videos = new VideosImage();
                //videos.execute();

            }
            else{*/
                /*try
                {
                    System.out.println("resp vid : "+utils.loadjsonval());

                    if(!utils.loadjsonval().isEmpty()){
                        jsonobjarr=new ArrayList<JSONObject>();
                        String exc=utils.loadjsonval();
                        JSONArray jsonobjarrval=new JSONArray(exc);
                        System.out.println("jsonval "+jsonobjarrval);

                        for(int j=0;j<jsonobjarrval.length();j++){
                            JSONObject jobject = jsonobjarrval.getJSONObject(j);
                            jsonobjarr.add(jobject);
                            System.out.println("jsonobj "+jsonobjarr);

                        }

                        countarray=new ArrayList<String>();
                        for(int m=0;m<jsonobjarr.size();m++) {
                            JSONObject jso = jsonobjarr.get(m);
                            String count=jso.getString("count");
                            countarray.add(count);
                        }

                        for(int k=0;k<jsonobjarr.size();k++){
                            JSONObject jso=jsonobjarr.get(k);
                            String stime=jso.getString("sdate");
                            String etime=jso.getString("edate");
                            Log.e("stime",stime);
                            Log.e("etime",stime);
                            String inputPattern = "HH:mm:ss";
                            String inputPatterns = "YYYY-MM-DD";

                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

                            String sstr = null;
                            String estr = null;
                            String gtime = null;
                            Date times;
                            Date timee;
                            Date timeg;
                            DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                            times=df.parse(stime);
                            timee= df.parse(etime);
                            timeg= df.parse(realtime);

                            sstr = inputFormat.format(times);
                            estr = inputFormat.format(timee);
                            gtime = inputFormat.format(timeg);

                            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(sstr);
                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.setTime(time1);
                            calendar1.add(Calendar.DATE, 1);

                            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(estr);
                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.setTime(time2);
                            calendar2.add(Calendar.DATE, 1);



                            Date d = new SimpleDateFormat("HH:mm:ss").parse(gtime);
                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.setTime(d);
                            calendar3.add(Calendar.DATE, 1);

                            Date x = calendar3.getTime();

                            DateFormat dfs = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");


                            System.out.println("realtime  "+realtime);
                           //



                            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                //checkes whether the current time is between 14:49:00 and 20:11:13.
                                System.out.println(true);
                                vidval=jso.toString();
                                countpos=k;
                                Log.e("vidSystem",vidval);

                            }
                            else{
                                //vidval=jsonobjarr.get(jsonobjarr.size()-1).toString();
                            }


                        }
                        Log.e("jsonobjarral",String.valueOf(jsonobjarr.size()));
                        Log.e("vidval",String.valueOf(vidval));

                        if(vidval.isEmpty()&&jsonobjarr.size()==7){
                            vidval=jsonobjarr.get(jsonobjarr.size()-1).toString();
                        }
                        else if(vidval.isEmpty()&&jsonobjarr.size()==1){
                            vidval=jsonobjarr.get(jsonobjarr.size()-1).toString();

                        }
                        else if(vidval.isEmpty()&&(jsonobjarr.size()<=6&&jsonobjarr.size()>1)){
                            Log.e("insidejson",String.valueOf(jsonobjarr.size()));
                            AlertDialog.Builder alert=new AlertDialog.Builder(Videoimage.this);
                            alert.setCancelable(true);
                            alert.setTitle("Refresh");
                            alert.setMessage("Your current video is unavailable.\nKindly click refresh button.");
                            alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    VideosImages tasks=new VideosImages();
                                    tasks.execute();
                                }
                            });
                            alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                    Intent i=new Intent(Videoimage.this, HomePage.class);
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
                                    finish();
                                }
                            });
                            android.app.AlertDialog alerts = alert.create();
                            alert.show();

                        }



                    }


                    *//*JSONObject jobj = new JSONObject(vidval);
                    if(jobj.getString("Status").equals("Success"))
                    {*//*

                    try {

                                Recyclerlistvalues recycler = new Recyclerlistvalues();
                                recycler.execute();


                    }
                    catch (Exception e){

                    }

                        layoutlay.setVisibility(View.VISIBLE);
                        points.setVisibility(View.GONE);

                       // JSONArray jarray = jobj.getJSONArray("Response");
                       // for(int j=0; j<jarray.length(); j++)
                       // {


                            Log.e("vidvalues",vidval);
                            JSONObject jobject = new JSONObject(vidval);
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


                    String inputPattern = "HH:mm:ss";
                    String inputPatterns = "YYYY-MM-DD";

                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

                    String sstr = null;
                    String estr = null;
                    String gtime = null;
                    Date times;
                    Date timee;
                    Date timeg;
                    DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                    times=df.parse(sdate);
                    timee= df.parse(edate);
                    sstr = inputFormat.format(times);
                    estr = inputFormat.format(timee);
                    unamtext.setText(utils.loadName());
                    timetext.setText(sstr+" - "+estr);

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

                                        //GetInfoTask tasks=new GetInfoTask();
                                        //tasks.execute();

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
                        }
                   *//* }
                    else if(jobj.getString("Status").equals("Failure"))
                    {
                        Intent i=new Intent(Videoimage.this, HomePage.class);
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
                    }*//*

                catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.e("jvaexc",e.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e("jvexcee",e.toString());

                }

        }*/
        } else {
            Toast.makeText(getApplicationContext(), "Please turn on your network1", Toast.LENGTH_LONG).show();

            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }


        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //receiver = new NetworkChangeReceiver();

        /*if(NetworkUtil.INSTANCE.getConnectivityStatus(Videoimage.this) > 0)
        {

        }
        else{

        }*/

        linearbody = (LinearLayout) findViewById(R.id.linearbody);

        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);


        //addLogText(NetworkUtil.INSTANCE.getConnectivityStatusString(Videoimage.this));


        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    linearbody.setVisibility(View.VISIBLE);
                    retry_lay.setVisibility(View.GONE);

                    Intent i = new Intent(Videoimage.this, HomePage.class);
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

                } else {
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
                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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


    public class playvideo extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            final String[] result = {null};
            System.out.println("resp vid : " + utils.loadjsonval());
            Videoimage.this.runOnUiThread(new Runnable() {
                public void run() {
                    try {

                        if (!utils.loadjsonval().isEmpty()) {
                            jsonobjarr = new ArrayList<JSONObject>();
                            String exc = utils.loadjsonval();
                            JSONArray jsonobjarrval = new JSONArray(exc);
                            System.out.println("jsonval " + jsonobjarrval);
                            System.out.println("usertime " + realtime);

                            for (int j = 0; j < jsonobjarrval.length(); j++) {
                                JSONObject jobject = jsonobjarrval.getJSONObject(j);
                                jsonobjarr.add(jobject);
                                System.out.println("jsonobj " + jsonobjarr);

                            }

                            countarray = new ArrayList<String>();
                            for (int m = 0; m < jsonobjarr.size(); m++) {
                                JSONObject jso = jsonobjarr.get(m);
                                String count = jso.getString("count");
                                countarray.add(count);
                            }

                            for (int k = 0; k < jsonobjarr.size(); k++) {
                                JSONObject jso = jsonobjarr.get(k);
                                String stime = jso.getString("sdate");
                                String etime = jso.getString("edate");
                                Log.e("stime", stime);
                                Log.e("etime", stime);
                                String inputPattern = "HH:mm:ss";
                                String inputPatterns = "YYYY-MM-DD";

                                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

                                String sstr = null;
                                String estr = null;
                                String gtime = null;
                                Date times;
                                Date timee;
                                Date timeg;
                                DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                                times = df.parse(stime);
                                timee = df.parse(etime);
                                timeg = df.parse(realtime);

                                sstr = inputFormat.format(times);
                                estr = inputFormat.format(timee);
                                gtime = inputFormat.format(timeg);

                                Date time1 = new SimpleDateFormat("HH:mm:ss").parse(sstr);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(time1);
                                calendar1.add(Calendar.DATE, 1);

                                Date time2 = new SimpleDateFormat("HH:mm:ss").parse(estr);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(time2);
                                calendar2.add(Calendar.DATE, 1);


                                Date d = new SimpleDateFormat("HH:mm:ss").parse(gtime);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(d);
                                calendar3.add(Calendar.DATE, 1);

                                Date x = calendar3.getTime();

                                DateFormat dfs = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");


                                System.out.println("realtime  " + realtime);
                                //


                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                    //checkes whether the current time is between 14:49:00 and 20:11:13.
                                    System.out.println(true);
                                    vidval = jso.toString();
                                    countpos = k;
                                    result[0] = vidval;

                                    //Log.e("vidSystem", vidval);

                                } else {
                                    //vidval=jsonobjarr.get(jsonobjarr.size()-1).toString();
                                }


                            }
                            //Log.e("jsonobjarral", String.valueOf(jsonobjarr.size()));
                            //Log.e("vidval", String.valueOf(vidval));

                            if (utils.loadtype().equals("0")) {
                                if (vidval.isEmpty() && jsonobjarr.size() == 7) {
                                    vidval = jsonobjarr.get(jsonobjarr.size() - 1).toString();
                                    result[0] = vidval;
                                } else if (vidval.isEmpty() && jsonobjarr.size() == 1) {
                                    vidval = jsonobjarr.get(jsonobjarr.size() - 1).toString();
                                    result[0] = vidval;

                                } else if (vidval.isEmpty() && (jsonobjarr.size() <= 6 && jsonobjarr.size() > 1)) {
                                    //Log.e("insidejson", String.valueOf(jsonobjarr.size()));
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);
                                    alert.setCancelable(true);
                                    alert.setTitle("Refresh");
                                    alert.setMessage("Your current video is unavailable.\nKindly click refresh button.");
                                    alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            VideosImages tasks = new VideosImages();
                                            tasks.execute();
                                        }
                                    });
                                    alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                    });
                                    android.app.AlertDialog alerts = alert.create();
                                    alert.show();

                                }
                            } else {
                                if (vidval.isEmpty() && jsonobjarr.size() == 1) {
                                    vidval = jsonobjarr.get(jsonobjarr.size() - 1).toString();
                                    result[0] = vidval;

                                }
                            }


                        }
                    } catch (Exception e) {

                    }

                }
            });


            return result[0];
        }

        @Override
        protected void onPostExecute(String resp) {
            try {

                Recyclerlistvalues recycler = new Recyclerlistvalues();
                recycler.execute();


            } catch (Exception e) {

            }

            //layoutlay.setVisibility(View.VISIBLE);
            points.setVisibility(View.GONE);

            // JSONArray jarray = jobj.getJSONArray("Response");
            // for(int j=0; j<jarray.length(); j++)
            // {


            try {
                //Log.e("vidvalues", vidval);
                JSONObject jobject = new JSONObject(vidval);
                id = jobject.getString("id");
                String type = jobject.getString("type");
                String url = "";
                //if(jobject.getString(url).isEmpty()){
                //    url = "";//https://drive.google.com/file/d/1LoZHD6dvyJvSREd1Cnj3LVITfQJzbdFS/preview
                //}else {
                url = jobject.getString("url");
                //}
                String point = jobject.getString("point");
                points.setText("Reward Points : " + point + " Points");
                sdate = jobject.getString("sdate");
                edate = jobject.getString("edate");


                try {
                    String inputPattern = "HH:mm:ss";
                    String inputPatterns = "YYYY-MM-DD";

                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                    SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

                    String sstr = null;
                    String estr = null;
                    String gtime = null;
                    Date times;
                    Date timee;
                    Date timeg;
                    DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                    times = df.parse(sdate);
                    timee = df.parse(edate);
                    sstr = inputFormat.format(times);
                    estr = inputFormat.format(timee);
                    cos.setVisibility(View.VISIBLE);
                    int currentapiVersion = android.os.Build.VERSION.SDK_INT;

                    if (currentapiVersion > 24) {
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat format = new SimpleDateFormat("dd MMM YYYY   HH:mm");
                        String formats = format.format(currentTime);
                        unamtext.setText(utils.loadName() + "\n" + formats);
                    } else {
                        Date currentTime = Calendar.getInstance().getTime();
                        unamtext.setText(utils.loadName() + "\n" + currentTime);
                    }

                    timetext.setText(sstr + " - " + estr);
                } catch (Exception e) {
                    cos.setVisibility(View.GONE);
                    unamtext.setVisibility(View.INVISIBLE);
                    timetext.setVisibility(View.INVISIBLE);
                    Log.e("evalue", e.toString());

                }
                String lannguage = "";
                final String seconds = jobject.getString("seconds");
                try {
                    middle = jobject.getString("middle");
                } catch (Exception e) {
                    middle = "";
                }
                try {
                    middle1 = jobject.getString("middle1");
                } catch (Exception e) {
                    middle1 = "";
                }
                try {
                    middle2 = jobject.getString("middle2");
                } catch (Exception e) {
                    middle2 = "";
                }
                try {
                    middle3 = jobject.getString("middle3");
                } catch (Exception e) {
                    middle3 = "";
                }
                try {
                    middle4 = jobject.getString("middle4");
                } catch (Exception e) {
                    middle4 = "";
                }
                try {
                    middle5 = jobject.getString("middle5");
                } catch (Exception e) {
                    middle5 = "";
                }
                try {
                    lannguage = jobject.getString("language");
                } catch (Exception e) {

                }

                final String count = jobject.getString("count");

                if (type.equals("Video")) {
                    getMinutes = seconds;
                    videoView.setVisibility(View.VISIBLE);
                    String str = url + "?autoplay=1&modestbranding=1&controls=0&fs=0\"\n" +
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
                    videoView.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            return false;
                        }
                    });
                    String finalLannguage = lannguage;
                    videoView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            emulateClick(view);
                            int noOfMinutes = Integer.parseInt(getMinutes) * 1000;

                            startTimer(noOfMinutes, 0, utils.loadcount(), finalLannguage);

                            //GetInfoTask tasks=new GetInfoTask();
                            //tasks.execute();

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
                            });*/
                } else if (type.equals("Image")) {
                    img.setVisibility(View.VISIBLE);
                    Picasso.with(Videoimage.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                    getMinutes = seconds;
                    //Check validation over edittext
                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                        imgnoofminutes = Integer.parseInt(getMinutes) * 1000;
                        Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                        imgstarttimer(imgnoofminutes, utils.loadcount());
                    }
                }
            } catch (Exception e) {

            }
        }
                   /* }
                    else if(jobj.getString("Status").equals("Failure"))
                    {
                        Intent i=new Intent(Videoimage.this, HomePage.class);
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
                    }*/


    }


    public class VideosImage extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progbar.show();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                try {
                    result = getIntent().getExtras().getString("videoresp");
                    Log.e("respom", result);
                } catch (Exception e) {

                }
                //Log.i("Videosinput", Appconstants.VIDEOVIEW + "    " + jobj.toString() + "");
                // result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            try {
                Log.e("respval", resp);
                if (!resp.isEmpty() && (resp != null)) {

                } else {
                    Log.e("respvaldup", resp);

                    VideosImagedup vdo = new VideosImagedup();
                    vdo.execute();
                }
                //System.out.println("resp vid : "+resp);
                JSONObject jobj = new JSONObject(resp);


                if (jobj.getString("Status").equals("Success")) {
                    progbar.dismiss();


                    //layoutlay.setVisibility(View.VISIBLE);
                    points.setVisibility(View.GONE);

                    JSONArray jarray = jobj.getJSONArray("Response");

                    if (jarray.length() == 0) {


                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                        alert.setCancelable(false);
                        alert.setTitle("Video Not Available");
                        alert.setMessage(jobj.getString("Msg"));
                        alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        });
                        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });*/
                        android.app.AlertDialog alerts = alert.create();
                        alert.show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        }, 5000);


                    }


                    if (jobj.getString("apperror").equals("0")) {
                        Recyclerlistvalues recycler = new Recyclerlistvalues();
                        recycler.execute();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms
                                //Toast.makeText(c, "check", Toast.LENGTH_SHORT).show();
                                Adview ads = new Adview();
                                ads.execute();

                            }
                        }, 10000);

                        for (int j = 0; j < jarray.length(); j++) {
                            JSONObject jobject = jarray.getJSONObject(j);
                            id = jobject.getString("id");
                            if (utils.loadid().isEmpty()) {
                                utils.savePreferences("idvalue", id);
                            } else if (!id.equals(utils.loadid())) {
                                utils.savePreferences("idvalue", id);
                                utils.savePreferences("countvalue", "");

                            }

                            if (!utils.loadseenuser().equals(utils.loadName())) {
                                utils.savePreferences("countvalue", "");

                            }
                            String type = jobject.getString("type");
                            String url = "";
                            //if(jobject.getString(url).isEmpty()){
                            //    url = "";//https://drive.google.com/file/d/1LoZHD6dvyJvSREd1Cnj3LVITfQJzbdFS/preview
                            //}else {
                            url = jobject.getString("url");
                            //}
                            //String point = jobject.getString("point");
                            //points.setText("Reward Points : "+point+" Points");
                            sdate = jobject.getString("sdate");
                            edate = jobject.getString("edate");
                            String inputPattern = "HH:mm:ss";
                            String inputPatterns1 = "HH:mm";
                            String inputPatterns = "yyyy-MM-DD";
                            String sstr = null;
                            String estr = null;
                            String estrend = null;
                            String currstr = null;
                            String currstrend = null;
                            try {

                                int currentapiVersion = android.os.Build.VERSION.SDK_INT;

                                if (currentapiVersion > 20) {
                                    SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                                    SimpleDateFormat inputFormats1 = new SimpleDateFormat(inputPatterns1);
                                    SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);


                                    String gtime = null;
                                    Date times;
                                    Date timee;
                                    Date timeend;
                                    String timeg;
                                    Date timegs;
                                    Date timegsend;
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
                                    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
                                    times = df.parse(sdate);
                                    timee = df.parse(edate);
                                    timeg = dfs.format(Calendar.getInstance().getTime());
                                    timegs = df.parse(timeg);
                                    timegsend = df.parse(timeg);
                                    timeend = df.parse(edate);
                                    sstr = inputFormat.format(times);
                                    estr = inputFormat.format(timee);
                                    estrend = inputFormats1.format(timeend);
                                    currstr = inputFormat.format(timegs);
                                    currstrend = inputFormats1.format(timegsend);
                                    Log.e("currtime", currstr);
                                    Log.e("estrend", estrend);
                                    Log.e("currstrend", currstrend);

                                    cos.setVisibility(View.VISIBLE);
                                    //  int currentapiVersion = android.os.Build.VERSION.SDK_INT;

                                    if (currentapiVersion > 24) {
                                        Date currentTime = Calendar.getInstance().getTime();
                                        DateFormat formats = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                        String formatss = formats.format(currentTime);
                                        if (origin_count.equals("1")) {
                                            unamtext.setText(utils.loadName() + "." + "\n" + formatss);
                                        } else if (origin_count.equals("2")) {
                                            unamtext.setText(utils.loadName() + ".." + "\n" + formatss);

                                        } else if (origin_count.equals("3")) {
                                            unamtext.setText(utils.loadName() + "..." + "\n" + formatss);

                                        } else if (origin_count.equals("4")) {
                                            unamtext.setText(utils.loadName() + "...." + "\n" + formatss);

                                        }
                                        else  {
                                            try {
                                                unamtext.setText(utils.loadName() + "...." + "\n" + formatss);
                                            }
                                            catch (Exception e){
                                                unamtext.setText(utils.loadName());

                                            }

                                        }

                                    } else {
                                        Date currentTimes = Calendar.getInstance().getTime();
                                        if (origin_count.equals("1")) {
                                            unamtext.setText(utils.loadName() + "." + " - " + currentTimes);

                                        } else if (origin_count.equals("2")) {
                                            unamtext.setText(utils.loadName() + ".." + " - " + currentTimes);

                                        } else if (origin_count.equals("3")) {
                                            unamtext.setText(utils.loadName() + "..." + " - " + currentTimes);

                                        } else if (origin_count.equals("4")) {
                                            unamtext.setText(utils.loadName() + "...." + "\n" + currentTimes);

                                        }
                                         else  {
                                            try {
                                                unamtext.setText(utils.loadName() + "...." + "\n" + currentTimes);
                                            }
                                            catch (Exception e){
                                                unamtext.setText(utils.loadName());

                                            }

                                        }

                                    }
                                    timetext.setText(sstr + " - " + estr);
                                } else {
                                    cos.setVisibility(View.VISIBLE);
                                    if (currentapiVersion > 24) {
                                        Date currentTime = Calendar.getInstance().getTime();
                                        DateFormat formats = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                        String formatss = formats.format(currentTime);
                                        if (origin_count.equals("1")) {
                                            unamtext.setText(utils.loadName() + "." + "\n" + formatss);
                                        } else if (origin_count.equals("2")) {
                                            unamtext.setText(utils.loadName() + ".." + "\n" + formatss);

                                        } else if (origin_count.equals("3")) {
                                            unamtext.setText(utils.loadName() + "..." + "\n" + formatss);

                                        } else if (origin_count.equals("4")) {
                                            unamtext.setText(utils.loadName() + "...." + "\n" + formatss);

                                        }
                                         else  {
                                            try {
                                                unamtext.setText(utils.loadName() + "...." + "\n" + formatss);
                                            }
                                            catch (Exception e){
                                                unamtext.setText(utils.loadName());

                                            }

                                        }


                                    } else {
                                        Date currentTimes = Calendar.getInstance().getTime();

                                        if (origin_count.equals("1")) {
                                            unamtext.setText(utils.loadName() + "." + " - " + currentTimes);

                                        } else if (origin_count.equals("2")) {
                                            unamtext.setText(utils.loadName() + ".." + " - " + currentTimes);

                                        } else if (origin_count.equals("3")) {
                                            unamtext.setText(utils.loadName() + "..." + " - " + currentTimes);

                                        } else if (origin_count.equals("4")) {
                                            unamtext.setText(utils.loadName() + "...." + "\n" + currentTimes);

                                        }
                                         else  {
                                            try {
                                                unamtext.setText(utils.loadName() + "...." + "\n" + currentTimes);
                                            }
                                            catch (Exception e){
                                                unamtext.setText(utils.loadName());

                                            }

                                        }

                                    }
                                    timetext.setText(sdate + " - " + edate);
                                }
                            } catch (Exception e) {
                                Log.e("excep", e.toString());
                                cos.setVisibility(View.GONE);
                                unamtext.setVisibility(View.INVISIBLE);
                                timetext.setVisibility(View.INVISIBLE);

                            }

                        /*try{
                            if(estrend.equals(currstrend)){
                                Intent i=new Intent(Videoimage.this, HomePage.class);
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
                            else{

                            }
                        }
                        catch (Exception e){

                        }*/

                            /*try {
                                String string1 =sstr;
                                Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.setTime(time1);
                                calendar1.add(Calendar.DATE, 1);


                                String string2 = estr;
                                Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.setTime(time2);
                                calendar2.add(Calendar.DATE, 1);

                                String someRandomTime = currstr;
                                Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.setTime(d);
                                calendar3.add(Calendar.DATE, 1);

                                Date x = calendar3.getTime();
                                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                                    //checkes whether the current time is between 14:49:00 and 20:11:13.
                                    System.out.println(true);
                                }
                                else
                                {
                                    Intent i=new Intent(Videoimage.this, HomePage.class);
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
                            catch (Exception e){

                            }*/
                            final String seconds = jobject.getString("seconds");
                            String lannguage = "";

                            try {
                                middle = jobject.getString("middle");
                            } catch (Exception e) {
                                middle = "";
                            }
                            try {
                                middle1 = jobject.getString("middle1");
                            } catch (Exception e) {
                                middle1 = "";
                            }
                            try {
                                middle2 = jobject.getString("middle2");
                            } catch (Exception e) {
                                middle2 = "";
                            }
                            try {
                                middle3 = jobject.getString("middle3");
                            } catch (Exception e) {
                                middle3 = "";
                            }
                            try {
                                middle4 = jobject.getString("middle4");
                            } catch (Exception e) {
                                middle4 = "";
                            }
                            try {
                                middle5 = jobject.getString("middle5");
                            } catch (Exception e) {
                                middle5 = "";
                            }

                            try {
                                cutoffstime = jobject.getString("cutoff");
                            } catch (Exception e) {
                                cutoffstime = "";
                            }


                            try {
                                lannguage = jobject.getString("language");
                                dmlang = lannguage;
                            } catch (Exception e) {

                            }

                            final String count = "0";

                            if (utils.loadcount().isEmpty()) {
                                utils.savePreferences("countvalue", "0");
                            }
                            String finalLannguage = lannguage;

                            System.out.println("locaalcnt  " + utils.loadcount());
                            if (type.equals("Video")) {
                                getMinutes = seconds;
                                videoView.setVisibility(View.VISIBLE);
                                String str = url + "?autoplay=1&modestbranding=1&controls=0&fs=0\"\n" +
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
                                videoView.setWebViewClient(new WebViewClient() {
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
                                        int noOfMinutes = Integer.parseInt(getMinutes) * 1000;

                                        startTimer(noOfMinutes, 0, utils.loadcount()/*count*/, finalLannguage);

                                    }
                                });
                                WebSettings webSettings = videoView.getSettings();
                                webSettings.setJavaScriptEnabled(true);
                                frameVideo = frameVideo.replace("$VIDEO_URL$", str);

                                videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);
                                videoView.loadData(frameVideo, "text/html", "utf-8");

                            } else if (type.equals("Image")) {
                                img.setVisibility(View.VISIBLE);
                                Picasso.with(Videoimage.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                                getMinutes = seconds;
                                //Check validation over edittext
                                if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                    imgnoofminutes = Integer.parseInt(getMinutes) * 1000;
                                    Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                                    imgstarttimer(imgnoofminutes, count);
                                }
                            }
                        }
                    } else if (jobj.getString("apperror").equals("1")) {
                        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                        alert.setCancelable(false);
                        alert.setTitle("Video Not Available.");
                        alert.setMessage("Failure to get your video.\nCheck your internet connection.");

                        alert.setPositiveButton("Retry.", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                                Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });


                        /*alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();

                            }
                        });*/
                        android.app.AlertDialog alerts = alert.create();
                        alert.show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        }, 5000);

                    }
                } else if (jobj.getString("Status").equals("Failure")) {
                    progbar.dismiss();


                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Failure to get your video.\nCheck your internet connection.");
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
            } catch (JSONException e) {

                Log.e("waterr", e.toString());
                progbar.dismiss();

                if (e.toString().contains("Value Too")) {
                    VideosImage video = new VideosImage();
                    video.execute();
                } else {

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
            }
        }
    }

    public class VideosImagedup extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                /*try {
                    result = getIntent().getExtras().getString("videoresp");
                    if(!result.isEmpty()&&(result!=null)){

                    }
                    else{

                    }
                }
                catch (Exception e){

                }*/
                // Log.i("Videosinput", Appconstants.VIDEOVIEW + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            try {
                progbar.dismiss();

                //System.out.println("resp vid : "+resp);
                JSONObject jobj = new JSONObject(resp);
                if (jobj.getString("Status").equals("Success")) {
                    Recyclerlistvalues recycler = new Recyclerlistvalues();
                    recycler.execute();

                    // layoutlay.setVisibility(View.VISIBLE);
                    points.setVisibility(View.GONE);

                    JSONArray jarray = jobj.getJSONArray("Response");
                    for (int j = 0; j < jarray.length(); j++) {
                        JSONObject jobject = jarray.getJSONObject(j);
                        id = jobject.getString("id");
                        if (utils.loadid().isEmpty()) {
                            utils.savePreferences("idvalue", id);
                        } else if (!id.equals(utils.loadid())) {
                            utils.savePreferences("idvalue", id);
                            utils.savePreferences("countvalue", "");

                        }
                        String type = jobject.getString("type");
                        String url = "";
                        //if(jobject.getString(url).isEmpty()){
                        //    url = "";//https://drive.google.com/file/d/1LoZHD6dvyJvSREd1Cnj3LVITfQJzbdFS/preview
                        //}else {
                        url = jobject.getString("url");
                        //}
                        //String point = jobject.getString("point");
                        //points.setText("Reward Points : "+point+" Points");
                        sdate = jobject.getString("sdate");
                        edate = jobject.getString("edate");
                        String inputPattern = "HH:mm:ss";
                        String inputPatterns = "YYYY-MM-DD";

                        try {
                            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                            SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

                            String sstr = null;
                            String estr = null;
                            String gtime = null;
                            Date times;
                            Date timee;
                            Date timeg;
                            DateFormat df = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
                            times = df.parse(sdate);
                            timee = df.parse(edate);
                            sstr = inputFormat.format(times);
                            estr = inputFormat.format(timee);
                            cos.setVisibility(View.VISIBLE);
                            Date currentTime = Calendar.getInstance().getTime();
                            SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY HH:mm");
                            String formats = format.format(currentTime);
                            unamtext.setText(utils.loadName() + "\n" + formats);
                            timetext.setText(sstr + " - " + estr);
                        } catch (Exception e) {
                            cos.setVisibility(View.GONE);
                            unamtext.setVisibility(View.INVISIBLE);
                            timetext.setVisibility(View.INVISIBLE);

                        }
                        final String seconds = jobject.getString("seconds");
                        String lannguage = "";

                        try {
                            middle = jobject.getString("middle");
                        } catch (Exception e) {
                            middle = "";
                        }
                        try {
                            middle1 = jobject.getString("middle1");
                        } catch (Exception e) {
                            middle1 = "";
                        }
                        try {
                            middle2 = jobject.getString("middle2");
                        } catch (Exception e) {
                            middle2 = "";
                        }
                        try {
                            middle3 = jobject.getString("middle3");
                        } catch (Exception e) {
                            middle3 = "";
                        }
                        try {
                            middle4 = jobject.getString("middle4");
                        } catch (Exception e) {
                            middle4 = "";
                        }
                        try {
                            middle5 = jobject.getString("middle5");
                        } catch (Exception e) {
                            middle5 = "";
                        }

                        try {
                            lannguage = jobject.getString("language");
                        } catch (Exception e) {

                        }

                        final String count = "0";

                        if (utils.loadcount().isEmpty()) {
                            utils.savePreferences("countvalue", "0");
                        }
                        String finalLannguage = lannguage;

                        System.out.println("locaalcnt  " + utils.loadcount());
                        if (type.equals("Video")) {
                            getMinutes = seconds;
                            videoView.setVisibility(View.VISIBLE);
                            String str = url + "?autoplay=1&modestbranding=1&controls=0&fs=0\"\n" +
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
                            videoView.setWebViewClient(new WebViewClient() {
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
                                    int noOfMinutes = Integer.parseInt(getMinutes) * 1000;

                                    startTimer(noOfMinutes, 0, utils.loadcount()/*count*/, finalLannguage);

                                }
                            });
                            WebSettings webSettings = videoView.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            frameVideo = frameVideo.replace("$VIDEO_URL$", str);

                            videoView.getSettings().setMediaPlaybackRequiresUserGesture(false);
                            videoView.loadData(frameVideo, "text/html", "utf-8");

                        } else if (type.equals("Image")) {
                            img.setVisibility(View.VISIBLE);
                            Picasso.with(Videoimage.this).load(url).placeholder(R.mipmap.v3).noFade().into(img);

                            getMinutes = seconds;
                            //Check validation over edittext
                            if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                imgnoofminutes = Integer.parseInt(getMinutes) * 1000;
                                Log.e("noOfMinutes", String.valueOf(imgnoofminutes));
                                imgstarttimer(imgnoofminutes, count);
                            }
                        }
                    }
                } else if (jobj.getString("Status").equals("Failure")) {
                    progbar.dismiss();

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Try Again");
                    alert.setMessage("Failure to get your video.\nCheck your internet connection.");
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
            } catch (JSONException e) {

                Log.e("waterr", e.toString());
                progbar.dismiss();

                if (e.toString().contains("Value Too")) {
                    VideosImage video = new VideosImage();
                    video.execute();
                } else {

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
            }
        }
    }

    public class Recyclerlistvalues extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("Recyclerlist", "Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                // Log.i("recyclerlistinput", Appconstants.RECYCLER_VALUES + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.RECYCLER_VALUES, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            productItems = new ArrayList<>();
            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                //Log.e("recycleval",resp);
                if (resp != null) {

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
                            Log.e("urls", urls);
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

                                    TextSliderView textSliderView = new TextSliderView(Videoimage.this);

                                    textSliderView
                                            .description(String.valueOf(j + 1))
                                            .image(HashMapForURL.get(j))
                                            .setScaleType(BaseSliderView.ScaleType.Fit)
                                            .setOnSliderClickListener(Videoimage.this);

                                    textSliderView.bundle(new Bundle());

                                    textSliderView.getBundle()
                                            .putString("extra", String.valueOf(j + 1));

                                    sliderLayout.addSlider(textSliderView);
                                    sliderLayout.setVisibility(View.VISIBLE);
                                }
                                sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);

                                //sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                                //sliderLayout.setCustomAnimation(new DescriptionAnimation());

                                sliderLayout.setDuration(30000);

                                sliderLayout.setCurrentPosition(0);
                                sliderLayout.stopAutoCycle();
                                sliderLayout.startAutoCycle(30000, 30000, true);


                                sliderLayout.addOnPageChangeListener(Videoimage.this);
                            }


                        }
                        try {

                            Quizasync quiz = new Quizasync();
                            quiz.execute();


                        } catch (Exception e) {

                        }
                    } else if (jobj.getString("Status").equals("Failure")) {
                        String Response = jobj.getString("Response");
                        //Log.e("Response", Response);

                        try {

                            Quizasync quiz = new Quizasync();
                            quiz.execute();

                        } catch (Exception e) {

                        }
                    }
                    mRecyclerItems.addAll(productItems);
                    itemsAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                //Log.e("catchbval",e.toString());
                e.printStackTrace();
            }
        }
    }

    public class Adview extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("Recyclerlist", "Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                // Log.i("recyclerlistinput", Appconstants.RECYCLER_VALUES + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.ADVIEW, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                Log.e("recycleval", resp);
                if (resp != null) {

                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        JSONArray jarr = new JSONArray(jobj.getString("Response"));

                        if (jarr.length() > 0) {
                            JSONObject jobjs = jarr.getJSONObject(0);
                            String img = jobjs.getString("url");
                            String short_description = jobjs.getString("short_description");
                            String description = jobjs.getString("description");
                            //  if(jobj.getString("edate").equals(edate)) {
                            adview.setVisibility(View.VISIBLE);
                            opncard.setVisibility(View.VISIBLE);
                            String href = jobjs.getString("href");

                            if (!href.isEmpty() && (href != null)) {
                                visit.setVisibility(View.VISIBLE);
                            }
                            visit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        i.setPackage("com.android.chrome");
                                        startActivity(i);
                                    } catch (ActivityNotFoundException e) {
                                        // Chrome is not installed
                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(href));
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        //i.setPackage("com.android.chrome");
                                        startActivity(i);

                                    }
                                }
                            });
                            // }
                            Picasso.with(Videoimage.this).load(img).into(contimg);
                            Picasso.with(Videoimage.this).load(img).placeholder(R.mipmap.v3).into(circleImageView);
                            desc.setText(description);
                            desc.setText(description);
                            shortcont.setText(short_description);
                        } else {

                        }
                    } else {

                    }
                } else {

                }
            } catch (Exception e) {


            }
        }
    }


    public class Quizasync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            Log.e("Recyclerquiz", "Started");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                //Log.i("recyclerquesinput", Appconstants.RECYCLER_QUES + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.RECYCLER_QUES, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {

            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                //Log.e("recyclerquesresp", resp);

                if (resp != null) {
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
                                public void onClick(View v) {
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
                                        Toast.makeText(Videoimage.this, "Select any one option", Toast.LENGTH_SHORT).show();
                                    } else if (videocomplete.equals("")) {
                                        Toast.makeText(Videoimage.this, "Once video concluded .., Press OK", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Questionsubmit question = new Questionsubmit();
                                        question.execute(id, popupstatus);
                                    }
                                }
                            });
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static final String TIME_SERVER = "time-a.nist.gov";

    public static void printTimes() throws IOException {
        Log.e("times", "inside");
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        //long returnTime = timeInfo.getReturnTime();   //local device time
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

        Date time = new Date(returnTime);
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String inputPatterns = "yyyy-MM-dd";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat inputFormats = new SimpleDateFormat(inputPatterns);

        String str = null;
        String strdt = null;

        str = inputFormat.format(time);
        strdt = inputFormats.format(time);

        realtime = str;
        realtimedt = strdt;

        Log.e("getCurrentNetworkTime", "Time from " + TIME_SERVER + ": " + realtime + " , " + realtimedt);


    }

    public class Questionsubmit extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            progbar.show();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                jobj.put("qid", param[0]);
                jobj.put("crt", param[1]);

                //Log.i("questioninput", Appconstants.QUESTION_SUBMIT + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.QUESTION_SUBMIT, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            // Log.e("questionresp", resp);

            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                JSONObject jobj = new JSONObject(resp);
                if (jobj.getString("Status").equals("Success")) {
                    quizcomplete = "complete";
                    quiz_lay.setVisibility(View.VISIBLE);
                    edit = shp.edit();
                    edit.putString("Popupstatus", "");
                    edit.commit();
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Start Countodwn method
    private void startTimer(final int noOfMinutes, final int nomiddle, String count, String lang) {
        final String countval = count;

        Log.e("countvalue", countval);

        if (lang.equals("1")) {
            contlan = contlan1;
            contlan_end = contlang11;
        } else if (lang.equals("2")) {
            contlan = contlan2;
            contlan_end = contlang22;
        } else if (lang.equals("3")) {
            contlan = contlan3;
            contlan_end = contlang33;
        } else if (lang.equals("4")) {
            contlan = contlan4;
            contlan_end = contlang44;
        } else if (lang.equals("5")) {
            contlan = contlan5;
            contlan_end = contlang55;
        } else if (lang.equals("6")) {
            contlan = contlan6;
            contlan_end = contlang66;
        }
        else{
            contlan = contlan2;
            contlan_end = contlang22;
        }

        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("noOfMinutes", String.valueOf(noOfMinutes));
                Log.e("noOfMinutesmid", String.valueOf(nomiddle));
                Log.e("middle", String.valueOf(middle));

                long millis = millisUntilFinished;
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                countdownText.setText(hms);//set text

                //String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                Calendar c = Calendar.getInstance();
                System.out.println("Current time =&gt; " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                Log.e("noOfMinutes", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis)));
                Log.e("noOfMinutesmid", String.valueOf(formattedDate));

                if (edate.equals(formattedDate)) {
                    skip.setEnabled(false);
                    stopCountdown();
                    if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                    } else {
                        linearbody.setVisibility(View.GONE);
                        retry_lay.setVisibility(View.VISIBLE);
                    }
                }


                if (!middle.isEmpty() && !middle.equals("null")) {
                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle.isEmpty() && !middle.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls.isEmpty()) {
                                    Log.e("inside", "this");
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if (!middle1.isEmpty() && !middle1.equals("null")) {
                    //cls="";
                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle1)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls1 = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle1.isEmpty() && !middle1.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle1) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls1.isEmpty()) {
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if (!middle2.isEmpty() && !middle2.equals("null")) {
                    //cls="";

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle2)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls2 = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle2.isEmpty() && !middle2.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle2) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls2.isEmpty()) {
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if (!middle3.isEmpty() && !middle3.equals("null")) {
                    //cls="";

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle3)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls3 = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle3.isEmpty() && !middle3.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle3) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls3.isEmpty()) {
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }

                if (!middle4.isEmpty() && !middle4.equals("null")) {
                    // cls="";

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle4)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls4 = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle4.isEmpty() && !middle4.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle4) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls4.isEmpty()) {
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                }


                if (!middle5.isEmpty() && !middle5.equals("null")) {
                    //cls="";

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle5)) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);

                        alert.setCancelable(false);
                        alert.setMessage(contlan);
                        alert.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        m.dismiss();
                                        cls5 = "cls";
                                        //startActivity(new Intent(Videoimage.this, HomePage.class));

                                    }
                                });
                        m = alert.create();

                        m.show();


                        //Toast.makeText(getApplicationContext(),"Are you still watching",Toast.LENGTH_SHORT).show();

                    }
                }

                if (!middle5.isEmpty() && !middle5.equals("null")) {

                    if (Integer.parseInt(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis))) == Integer.parseInt(middle5) - 9) {
                        try {
                            if (m != null) {
                                m.dismiss();
                                if (cls5.isEmpty()) {
                                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                }


                //Convert milliseconds into hour,minute and seconds

                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    if (countval.equals("0") && (countdownText.getText().toString().equals("00:00:01")) && (comp.equals(""))) {
                        comp = "in";

                        popup();


                    }
                }
            }

            public void onFinish() {

                videocomplete = "complete";

                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    if (countval.equals("0") && (countdownText.getText().toString().equals("00:00:01"))) {

                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                    } else if (countval.equals("1")) {
                        // Log.i("inside counts val","jkj");
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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

                    } else {

                    }
                } else {

                }

                if (quizcomplete.equals("")) {
                    //startActivity(new Intent(Videoimage.this, HomePage.class));
                } else if (quizcomplete.equals("complete")) {

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


                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    if (countval.equals("0")) {
                        //Log.i("inside count","jkj");

                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                    } else if (countval.equals("1")) {

                    } else {

                    }
                } else {

                }
                if (quizcomplete.equals("")) {

                    //Log.i("inside quiz","jj");
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                } else if (quizcomplete.equals("complete")) {

                }
                finish();
            }
        }.start();
    }

    private class GetInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //progbar.show();
            //Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                //Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_AMOUNT, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //stopprogress();
            /*PerformVersionTask ptask = new PerformVersionTask();
            ptask.execute();*/
            //progbar.dismiss();
            try {
                //Log.i("HomePage Res", resp + "");
                //responseval=resp;
                // utils.savePreferences("drawresp",responseval);


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
                            //amountvals = (String.format("%.2f", Double.parseDouble(utils.loadCommition())));
                            //gpvvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadgbv())) + "");
                            //ibvvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadIbv())) + "");
                            //purchasevals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadPurchase())) + "");
                            //salesvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadSales())) + "");
                            //planvals = jobj.getString("plan").toString().trim().equalsIgnoreCase("null") || jobj.getString("plan").toString().trim().length() == 0 ? "0" : jobj.getString("plan");
                            String logout = jobj.getString("logout").toString().trim().equalsIgnoreCase("null") || jobj.getString("logout").toString().trim().length() == 0 ? "0" : jobj.getString("logout");
                            //Log.i("plan", planvals);

                            if (logout.equals("0") && (logout != null)) {
                                //logout task=new logout();
                                // task.execute();


                            }

                            //targetvals  = (utils.loadTarget());
                            //achievevals  = (utils.loadAchieve());
                            //balancevals  = (utils.loadBalance());
                            //wallet_amtvals  = (utils.loadWallet());
                            //todayrewardvals  = (utils.today_reward());
                            //totalrewardvals  = (utils.total_reward());
                            //available_rewardvals  = (utils.available_reward());
                            //newvideo = (jobj.getString("newvideo"));
                        }


            /*amountvals=getIntent().getStringExtra("amount");
            gpvvals=getIntent().getStringExtra("gpv");
            ibvvals=getIntent().getStringExtra("ibv");
            purchasevals=getIntent().getStringExtra("purchase");
            salesvals=getIntent().getStringExtra("sales");
            targetvals=getIntent().getStringExtra("target");
            achievevals=getIntent().getStringExtra("achieve");
            balancevals=getIntent().getStringExtra("balance");
            wallet_amtvals=getIntent().getStringExtra("wallet_amt");
            todayrewardvals=getIntent().getStringExtra("todayreward");
            totalrewardvals=getIntent().getStringExtra("totalreward");
            available_rewardvals=getIntent().getStringExtra("available_reward");*/


                        /*try {
                            amount.setText(amountvals);
                            gpv.setText(gpvvals);
                            ibv.setText(ibvvals);
                            purchase.setText(purchasevals);
                            sales.setText(salesvals);
                            target.setText(targetvals);
                            achieve.setText(achievevals);
                            balance.setText(balancevals);
                            wallet_amt.setText(wallet_amtvals);
                            todayreward.setText(todayrewardvals);
                            totalreward.setText(totalrewardvals);
                            available_reward.setText(available_rewardvals);

                            todayreward.setVisibility(View.VISIBLE);
                            totalreward.setVisibility(View.VISIBLE);
                            available_reward.setVisibility(View.VISIBLE);
                            progressBar2.setVisibility(View.GONE);
                            progressBar3.setVisibility(View.GONE);
                            progressBar4.setVisibility(View.GONE);
                        }
                        catch (Exception e){

                        }*/
                    }
                        /*for (int i = 0; i < jarr.length(); i++) {
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
                        }*/
                        /*new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity

                            }
                        }, SPLASH_TIME_OUT);*/
                       /* if (dash){
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
                        }*/

                    else {
                       /* Toast.makeText(Tableview.this, "Username Mismatch.Please login.", Toast.LENGTH_SHORT).show();
                        utils.savePreferences("name", "");
                        utils.savePreferences("image", "");
                        utils.savePreferences("doj", "");
                        utils.savePreferences("designation", "");
                        utils.savePreferences("ibv", "");
                        utils.savePreferences("gbv", "");
                        utils.savePreferences("commition", "");
                        startActivity(new Intent(Tableview.this, Login.class));*/
                    }
                } else {
                    //retry.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("err", e.toString());
                //retry.show();
            }


        }
    }

    public class logout extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            int versionCode = BuildConfig.VERSION_CODE;

            try {
                JSONObject jobj = new JSONObject();

                jobj.put("uname", utils.loadName());


                //Log.i("Videossubmitinput", Appconstants.logout + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.logout, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            //Log.e("Videossubmitresp", resp);

            try {
                JSONObject jobj = new JSONObject(resp);
                if (jobj.getString("Status").equals("Success")) {

                    //prog.dismiss();
                    utils.savePreferences("name", "");
                    utils.savePreferences("image", "");
                    utils.savePreferences("doj", "");
                    utils.savePreferences("designation", "");
                    utils.savePreferences("ibv", "");
                    utils.savePreferences("gbv", "");
                    utils.savePreferences("commition", "");
                    utils.savePreferences("jsonobj", "");

                    Toast.makeText(Videoimage.this, "Logout for auto upgrade.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Videoimage.this, Login.class));

                    //JSONArray jarr=jobj.getJSONArray("Response");
                    //jsonobjarr.set(countpos,jarr.getJSONObject(0));
                    //utils.savePreferences("jsonobj",jsonobjarr.toString());

                    //Toast.makeText(Videoimage.this,"Congratulations! you are now eligible for reward points",Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void popup() {

        Log.e("loadingpop", "logging");
        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);
        alert.setCancelable(false);
        alert.setMessage(contlan_end);
        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        cls6 = "cls";

                        Videosubmit submit = new Videosubmit();
                        submit.execute("completed");
                        int versionCode = com.elancier.healthzone.BuildConfig.VERSION_CODE;

                        /*Intent i=new Intent(Videoimage.this,Offline_video.class);
                        i.putExtra("vid", id);
                        i.putExtra("uname", utils.loadName());
                        i.putExtra("type", "completed");
                        i.putExtra("appversion",versionCode);
                        i.putExtra("mobileos",s);
                        i.putExtra("deviceid",deviceId);
                        i.putExtra("mobile_model", android.os.Build.MODEL);
                        i.putExtra("network",getNetworkClass(getApplicationContext()));
                        startActivity(i);
                        finish();*/

                        /*try {
                            final Dialog update = new Dialog(Videoimage.this,R.style.DialogTheme);
                            update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            update.getWindow().setBackgroundDrawable(
                                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            View v=getLayoutInflater().inflate(R.layout.activity_feedback_lay,null);
                             speak = v.findViewById(R.id.imageButton2);
                            feedrad= v.findViewById(R.id.fdradio);
                            compradio= v.findViewById(R.id.cmpradio);
                            suggradio= v.findViewById(R.id.suggradio);
                            permradio= v.findViewById(R.id.permradio);
                            feededit= v.findViewById(R.id.feededit);
                            submitfeed= v.findViewById(R.id.submit);
                            update.setContentView(v);
                            Window window = update.getWindow();
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            update.setCancelable(false);
                            update.show();

                            try {
                                speak.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        final String[] colors = {"தமிழ்", "English", "हिंदी", "తెలుగు", "മലയാളം", "ಕನ್ನಡಂ"};

                                        AlertDialog.Builder builder = new AlertDialog.Builder(Videoimage.this);
                                        builder.setTitle("Select your language");
                                        builder.setItems(colors, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                                                if (which == 0) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "உங்கள் கருத்து");

                                                } else if (which == 1) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your feedback");

                                                } else if (which == 2) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi_IN");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "आपकी राय");

                                                } else if (which == 3) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "te_IN");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "మీ అభిప్రాయం");

                                                } else if (which == 4) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ml_IN");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "നിങ്ങളുടെ അഭിപ്രായം");

                                                } else if (which == 5) {
                                                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "kn_IN");
                                                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "ನಿಮ್ಮ ಅಭಿಪ್ರಾಯ");

                                                }

                                                try {
                                                    startActivityForResult(intent, REQ_CODE);
                                                } catch (ActivityNotFoundException a) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Sorry your device not supported",
                                                            Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                        });
                                        builder.show();


                                    }
                                });
                            }
                            catch (Exception e){

                            }



                            try {
                                feededit.setScroller(new Scroller(Videoimage.this));
                                feededit.setMaxLines(2);
                                feededit.setVerticalScrollBarEnabled(true);
                                feededit.setMovementMethod(new ScrollingMovementMethod());
                            }
                            catch (Exception e){

                            }

                            try {
                                feedrad.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                *//*feedrad.setChecked(true);
                compradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(false);*//*
                                        str = "Feedback";
                                        change_back();
                                        fradcheck[0] = "check";
                                        //feededit.setVisibility(View.VISIBLE);
                                        //submitfeed.setVisibility(View.VISIBLE);

                                    }
                                });

                                compradio.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                *//*feedrad.setChecked(false);
                compradio.setChecked(true);
                suggradio.setChecked(false);
                permradio.setChecked(false);*//*

                                        str = "Complaint";
                                        change_back();
                                        fradcheck[0] = "check";
                                        //feededit.setVisibility(View.VISIBLE);
                                        //submitfeed.setVisibility(View.VISIBLE);


                                    }
                                });
                            }
                            catch (Exception e){

                            }

                            try{
                                suggradio.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                *//*feedrad.setChecked(false);
                compradio.setChecked(false);
                suggradio.setChecked(true);
                permradio.setChecked(false);*//*
                                        str = "Suggestion";
                                        change_back();
                                        fradcheck[0] ="check";
                                        //feededit.setVisibility(View.VISIBLE);
                                        //submitfeed.setVisibility(View.VISIBLE);


                                    }
                                });
                            }
                            catch (Exception e){

                            }

                            try{
                                permradio.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                *//*feedrad.setChecked(false);
                compradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(true);*//*
                                        str = "Permission";
                                        change_back();
                                        fradcheck[0] ="check";
                                        //feededit.setVisibility(View.VISIBLE);
                                        //submitfeed.setVisibility(View.VISIBLE);


                                    }
                                });
                            }
                            catch (Exception e){

                            }

                            submitfeed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    submitfeed.setEnabled(false);
                                    if (feededit.getText().toString().length() > 0&& !fradcheck[0].isEmpty()) {

                                        String feedoption="";
                                        progbar.show();
                                        update.dismiss();

                                       *//* HomePage.feedback task = new HomePage.feedback();
                                        task.execute(fname, feededit.getText().toString().trim(),str);*//*


                                    }
                                    else{
                                        submitfeed.setEnabled(true);

                                        if(feededit.getText().toString().length()==0) {
                                            Toast.makeText(getApplicationContext(), "Please enter your feedback", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Please Check feedback category", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                }
                            });*/


                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                        /*}catch(Exception e){
                            Log.e("err",e.toString());
                            //logger.info("PerformVersionTask error" + e.getMessage());
                        }*/

                    }
                });
        m = alert.create();

        m.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
//ToDo your function
//hide your popup here
                try {
                    if (m != null) {
                        m.dismiss();
                    }

                } catch (Exception e) {

                }
                if (cls6.isEmpty()) {
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                }

            }
        }, 9000);
    }

    public void change_back() {
        if (str.equals("Feedback")) {
            selected(feedrad);
            unselected(compradio);
            unselected(suggradio);
            unselected(permradio);
        } else if (str.equals("Complaint")) {
            unselected(feedrad);
            selected(compradio);
            unselected(suggradio);
            unselected(permradio);
        } else if (str.equals("Suggestion")) {
            unselected(feedrad);
            unselected(compradio);
            selected(suggradio);
            unselected(permradio);

        } else if (str.equals("Permission")) {
            unselected(feedrad);
            unselected(compradio);
            unselected(suggradio);
            selected(permradio);
        }
    }

    public void unselected(TextView tabTextView) {
        tabTextView.setTextColor(ContextCompat.getColor(Videoimage.this, R.color.black));
        tabTextView.setBackgroundResource(/*context.resources.getDrawable(*/R.drawable.product_cat_unselected);
    }

    public void selected(TextView tabTextView) {
        tabTextView.setTextColor(ContextCompat.getColor(Videoimage.this, R.color.white));
        tabTextView.setBackgroundResource(/*context.resources.getDrawable(*/R.drawable.product_cat_selected);
    }


    public class VideosImages extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            //progbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                //Log.i("Videosinput", Appconstants.VIDEOVIEWneew + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOVIEWneew, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        protected void onPostExecute(String resp) {
            try {

                if (resp != null) {
                    jsonobjarr = new ArrayList<JSONObject>();
                    System.out.println("resp vid : " + resp);
                    video_responseval = resp;
                    utils.savePreferences("videoresp", video_responseval);
                    //button4.setEnabled(true);
                    //button4.setTextColor(getResources().getColor(R.color.lred));
                    //button4.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_video, 0);

                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {

                        JSONArray jarr = new JSONArray();
                        jarr = jobj.getJSONArray("Response");
                        for (int j = 0; j < jarr.length(); j++) {
                            JSONObject jobject = jarr.getJSONObject(j);
                            jsonobjarr.add(jobject);
                            System.out.println("jsonobj " + jsonobjarr);
                        }
                        utils.savePreferences("jsonobj", jsonobjarr.toString());
                        String today = new SimpleDateFormat("dd/MM/yyyy").format(System.currentTimeMillis());

                        finish();
                        startActivity(new Intent(Videoimage.this, Videoimage.class));

                        //utils.savePreferences("vdate",today);

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

                        Log.e("insidejson", String.valueOf(jsonobjarr.size()));
                        AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);
                        alert.setCancelable(true);
                        alert.setTitle("Refresh");
                        alert.setMessage("Your current video is unavailable.\nKindly click refresh button.");
                        alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                VideosImages tasks = new VideosImages();
                                tasks.execute();
                            }
                        });
                        alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        });
                        android.app.AlertDialog alerts = alert.create();
                        alert.show();

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
                } else {
                    Log.e("insidejson", String.valueOf(jsonobjarr.size()));
                    AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);
                    alert.setCancelable(true);
                    alert.setTitle("Refresh");
                    alert.setMessage("Your current video is unavailable.\nKindly click refresh button.");
                    alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            VideosImages tasks = new VideosImages();
                            tasks.execute();
                        }
                    });
                    alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            Intent i = new Intent(Videoimage.this, HomePage.class);
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
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();
                    //retry.show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //retry.show();
                Log.e("insidejson", String.valueOf(jsonobjarr.size()));
                AlertDialog.Builder alert = new AlertDialog.Builder(Videoimage.this);
                alert.setCancelable(true);
                alert.setTitle("Refresh");
                alert.setMessage("Your current video is unavailable.\nKindly click refresh button.");
                alert.setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        VideosImages tasks = new VideosImages();
                        tasks.execute();
                    }
                });
                alert.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                });
                android.app.AlertDialog alerts = alert.create();
                alert.show();
            }
        }
    }


    public class Videosubmit_1 extends AsyncTask<String, String, String> {
        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            pdialog = new ProgressDialog(Videoimage.this);
            pdialog.setMessage("Video Submission...");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            int versionCode = com.elancier.healthzone.BuildConfig.VERSION_CODE;
            try {

                String duplicateuser = "";
                if (!utils.loadseenuser().isEmpty()) {
                    if (!utils.loadseenuser().equals(utils.loadName())) {

                        if (utils.loadseenvideo().equals(id)) {
                            duplicateuser = "2";
                        } else {
                            duplicateuser = "0";

                        }

                    } else {
                        duplicateuser = "0";
                    }
                } else {
                    duplicateuser = "0";

                }
                JSONObject jobj = new JSONObject();
                jobj.put("vid", id);
                jobj.put("uname", utils.loadName());
                jobj.put("utype", utils.loadtype());
                jobj.put("type", param[0]);
                jobj.put("time", cutoffstime);
                jobj.put("appversion", versionCode);
                jobj.put("mobileos", s);
                jobj.put("deviceid", deviceId);
                jobj.put("status", duplicateuser);
                jobj.put("mobile_model", android.os.Build.MODEL);
                jobj.put("language", dmlang);
                jobj.put("network", getNetworkClass(getApplicationContext()));


                if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 23) {
                    Log.i("Videossubmitinput", origin_domain1 + Appconstants.VIDEOSUBMIT4raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain1 + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                } else if (Build.VERSION.SDK_INT > 23 && Build.VERSION.SDK_INT <= 25) {
                    Log.i("Videossubmitinput", origin_domain1 + Appconstants.VIDEOSUBMIT2raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain1 + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                } else if (Build.VERSION.SDK_INT > 25 && Build.VERSION.SDK_INT <= 27) {
                    Log.i("Videossubmitinput", origin_domain1 + Appconstants.VIDEOSUBMIT3raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain1 + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                } else if (Build.VERSION.SDK_INT == 28) {
                    Log.i("Videossubmitinput", origin_domain1 + Appconstants.VIDEOSUBMIT4raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain1 + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                } else if (Build.VERSION.SDK_INT >= 29) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT5raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("errorsub", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {

            try {
                // Log.e("Videossubmitresp", resp);

                if (resp != null) {
                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        //JSONArray jarr=jobj.getJSONArray("Response");
                        try {
                            utils.savePreferences("seenuser", utils.loadName());
                            utils.savePreferences("countvalue", "1");
                            utils.savePreferences("seenvideo", id);

                            // jsonobjarr.set(countpos, jarr.getJSONObject(0));
                            // utils.savePreferences("jsonobj", jsonobjarr.toString());
                        } catch (Exception e) {

                        }
                        pdialog.dismiss();

                        Toast.makeText(Videoimage.this, "Congratulations! you are now eligible for reward points", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        pdialog.dismiss();
                        Toast.makeText(Videoimage.this, jobj.getString("Response"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(Videoimage.this, "Congratulations! you are now eligible for reward points", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                } else {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class Videosubmit extends AsyncTask<String, String, String> {
        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            pdialog = new ProgressDialog(Videoimage.this);
            pdialog.setMessage("Video Submission...");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            int versionCode = com.elancier.healthzone.BuildConfig.VERSION_CODE;
            try {

                String duplicateuser = "";
                if (!utils.loadseenuser().isEmpty()) {
                    if (!utils.loadseenuser().equals(utils.loadName())) {
                        if (utils.loadseenvideo().equals(id)) {
                            duplicateuser = "2";
                        } else {
                            duplicateuser = "0";

                        }
                    } else {
                        duplicateuser = "0";
                    }
                } else {
                    duplicateuser = "0";

                }
                JSONObject jobj = new JSONObject();
                jobj.put("vid", id);
                jobj.put("uname", utils.loadName());
                jobj.put("utype", utils.loadtype());
                jobj.put("type", param[0]);
                jobj.put("time", cutoffstime);
                jobj.put("appversion", versionCode);
                jobj.put("mobileos", s);
                jobj.put("deviceid", deviceId);
                jobj.put("status", duplicateuser);
                jobj.put("mobile_model", android.os.Build.MODEL);
                jobj.put("language", dmlang);
                jobj.put("network", getNetworkClass(getApplicationContext()));

                if (Build.VERSION.SDK_INT >= 21 && Build.VERSION.SDK_INT <= 23) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT4raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT1raw, jobj, "");

                } else if (Build.VERSION.SDK_INT > 23 && Build.VERSION.SDK_INT <= 25) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT2raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT2raw, jobj, "");

                } else if (Build.VERSION.SDK_INT > 25 && Build.VERSION.SDK_INT <= 27) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT3raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT3raw, jobj, "");

                } else if (Build.VERSION.SDK_INT == 28) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT4raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT4raw, jobj, "");

                } else if (Build.VERSION.SDK_INT >= 29) {
                    Log.i("Videossubmitinput", origin_domain + Appconstants.VIDEOSUBMIT5raw + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(origin_domain + Appconstants.VIDEOSUBMIT5raw, jobj, "");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {

            try {
                // Log.e("Videossubmitresp", resp);

                if (resp != null) {
                    JSONObject jobj = new JSONObject(resp);
                    if (jobj.getString("Status").equals("Success")) {
                        //JSONArray jarr=jobj.getJSONArray("Response");
                        try {
                            utils.savePreferences("seenuser", utils.loadName());
                            utils.savePreferences("seenvideo", id);
                            utils.savePreferences("countvalue", "1");
                            // jsonobjarr.set(countpos, jarr.getJSONObject(0));
                            // utils.savePreferences("jsonobj", jsonobjarr.toString());
                        } catch (Exception e) {

                        }
                        pdialog.dismiss();

                        Toast.makeText(Videoimage.this, "Congratulations! you are now eligible for reward points", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                        pdialog.dismiss();
                        Toast.makeText(Videoimage.this, jobj.getString("Response"), Toast.LENGTH_LONG).show();
                        //Toast.makeText(Videoimage.this, "Congratulations! you are now eligible for reward points", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Videoimage.this, HomePage.class);
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
                } else {
                    //Toast.makeText(getApplicationContext(),"Try Again",Toast.LENGTH_SHORT).show();

                    Videosubmit_1 tasks = new Videosubmit_1();
                    tasks.execute("completed");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class feedback extends AsyncTask<String, String, String> {
        ProgressDialog pdialog;

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("feedbackTask", "started");
            pdialog = new ProgressDialog(Videoimage.this);
            pdialog.setMessage("Feedback Submission...");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("user", param[0]);
                jobj.put("comment", param[1]);
                jobj.put("type", param[2]);


                //Log.i("checkInput feedback", Appconstants.FEEDBACK_API+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.FEEDBACK_API, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("tabresp", resp + "");
            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                if (resp != null) {
                    feededit.setText(null);
                    Toast.makeText(Videoimage.this, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show();
                    pdialog.dismiss();

                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                    pdialog.dismiss();

                    Toast.makeText(Videoimage.this, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show();
                    submitfeed.setEnabled(true);
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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

            } catch (Exception e) {
                pdialog.dismiss();

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Videoimage.this, HomePage.class);
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


        }
    }


    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;

        }
    }

    /*@Override
    protected void onUserLeaveHint() {
        skip.setEnabled(false);
        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage.this))
        {
            Intent i=new Intent(Videoimage.this, HomePage.class);
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
            finish();

        }
        else
        {
            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }

       *//* Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*//*
    }*/

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
                    Toast.makeText(getApplicationContext(), "Couldn't Reduce Volume", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                skip.setEnabled(false);
                stopCountdown();
                if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                    Intent i = new Intent(Videoimage.this, HomePage.class);
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
                } else {
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


        if (skippedvalue.equals("1")) {
            stopCountdown();
            if (CheckNetwork.isInternetAvailable(Videoimage.this)) {
                Intent i = new Intent(Videoimage.this, HomePage.class);
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
            } else {
                ///Toast.makeText(getApplicationContext(),"Please turn on your network3",Toast.LENGTH_LONG).show();

                linearbody.setVisibility(View.GONE);
                retry_lay.setVisibility(View.VISIBLE);
            }
        } else {

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
                    startActivity(new Intent(Videoimage.this, HomePage.class));

                }
            }

            }
*/


    //comment close
   /* @Override
    protected void onRestart() {
        super.onRestart();

        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage.this))
        {
            startActivity(new Intent(Videoimage.this, HomePage.class));
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

        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }*/
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    feededit.setText((CharSequence) result.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        skip.setEnabled(false);
        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage.this))
        {
            Intent i=new Intent(Videoimage.this, HomePage.class);
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

        /*Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);*/
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
