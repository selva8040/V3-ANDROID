package com.elancier.healthzone;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ReportsPojo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.elancier.healthzone.Common.Appconstants.domain;

public class HomePage extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout,home_header;
    TextView uname;
    TextView amount;
    TextView purchase, todayreward, totalreward,available_reward;
    TextView sales;
    TextView gpv;
    TextView ibv;
    TextView target,achieve,balance,wallet_amt;
    Utils utils;
    Dialog retry, update;
    ImageView retrybut;
    int menuvalue = 0;
    ActionBarDrawerToggle mDrawerToggle;
    String notification;
    String dtime;
    ProgressBar progressBar2,progressBar3,progressBar4;
    ImageView imageView9;
    ImageButton imageButton3;
    EditText feededit;
    Button submitfeed;
    TextView feedrad,compradio,suggradio,permradio,tittext,tittextstar,noticnt,timedate;
    String   newvideo="";
    SharedPreferences shp;
    SharedPreferences.Editor edit;
    String popupstatus;
    String fname;
    List<String> list1;
    Spinner feedspinner;
    private final int REQ_CODE = 100;
    ArrayAdapter<String> dataAdapter2;
    String star="";
    String amountvals="";
    String planvals="";
    String gpvvals="";
    String ibvvals="";
    String purchasevals="";
    String salesvals="";
    String targetvals="";
    String achievevals="";
    String balancevals="";
    String wallet_amtvals="";
    String todayrewardvals="";
    String totalrewardvals="";
    String available_rewardvals="";
    ConstraintLayout constprf;
    String str = "";
    String reqdate = "";
    Dialog Adialog;
    String back_imageurl="";
    String back_gifurl="";
    String back_videourl="";

    String middle_imageurl="";
    String middle_gifurl="";
    String middle_videourl="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home_page_new);
        Toolbar tool = findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        //getSupportActionBar().setBackgroundDrawable(d);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.new_sts));
        }*/
        CircleImageView speak = findViewById(R.id.imageButton2);
        shp = this.getSharedPreferences("UserInfo", MODE_PRIVATE);
        popupstatus = shp.getString("Popupstatus", "");

        init();

        if(popupstatus.equals("Yes"))
        {
            showthankspopup();
        }
        else if(popupstatus.equals("No"))
        {
            failurepopup();
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_more_vert_24px);
        utils = new Utils(getApplicationContext());
        fname=utils.loadName();
        feedspinner= findViewById(R.id.feedspin);
        timedate= findViewById(R.id.textView32);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar3.setVisibility(View.VISIBLE);
        progressBar4.setVisibility(View.VISIBLE);
        imageButton3=findViewById(R.id.imageButton3);
        noticnt=findViewById(R.id.textView43);
        constprf=findViewById(R.id.constprf);
        list1=new ArrayList<String>();
        list1.add("Feedback");
        list1.add("Complaint");
        list1.add("Suggestion");
        list1.add("Suggestion");
        try {
            amountvals=(utils.loadCommition());
            gpvvals=(utils.loadgbv());
            ibvvals=(utils.loadIbv());
            planvals=getIntent().getStringExtra("plan");
            purchasevals=(utils.loadPurchase());
            salesvals=(utils.loadSales());
            targetvals=(utils.loadTarget());
            achievevals=(utils.loadAchieve());
            balancevals=(utils.loadBalance());
            wallet_amtvals=(utils.loadWallet());
            todayrewardvals=(utils.today_reward());
            totalrewardvals=(utils.total_reward());
            available_rewardvals=(utils.available_reward());

            try{
                if(planvals.equals("Super Premium")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.superpremiumnew);
                    tittext.setText(planvals);
                }
                else if(planvals.equals("Star Premium")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.starpremiumnew);
                    tittext.setText(planvals);

                }
                else if(planvals.equals("Premium")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.premiumicon);
                    tittext.setText(planvals);

                }
                else if(planvals.equals("Free")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.freeicon);
                    tittext.setText(planvals);

                }
                else if(planvals.equals("Pro Premium")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.propremium);
                    tittext.setText(planvals);

                }
                else if(planvals.equals("Entrance")||planvals.equals("entrance")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.entryicon);
                    tittext.setText(planvals);

                }
                else if(planvals.equals("Trial")||planvals.equals("trial")){
                    imageView9.setVisibility(View.VISIBLE);
                    imageView9.setImageResource(R.mipmap.trialicon);
                    tittext.setText(planvals);

                }


            }
            catch (Exception e){

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

        }
        catch (Exception e){

        }

        try {
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

        }


        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if(currentapiVersion>24) {
            Date currentTime = Calendar.getInstance().getTime();
            DateFormat format=new SimpleDateFormat("dd MMM yyyy   HH:mm");
            String formats=format.format(currentTime);
            timedate.setText(utils.loadName()+"\n"+formats);
        }
        else{
            Date currentTime = Calendar.getInstance().getTime();
            timedate.setText(utils.loadName()+"\n"+currentTime);

        }


        dataAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,list1);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedspinner.setAdapter(dataAdapter2);


        loadprogress();

        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v = getLayoutInflater().inflate(R.layout.retrylay, null);
        retrybut = v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        final String[] fradcheck = {""};
        //list1=new ArrayList<String>();


        uname.setText(utils.loadFname() + " " + utils.loadLname());

        ScrollView scroll = findViewById(R.id.scroll);
        String type = utils.loadtype();
        if (type.equals("0")){
            scroll.setBackgroundResource(R.drawable.top_corner_round_blue);
        }
        else if(type.equals("1")) {
            scroll.setBackgroundResource(R.drawable.top_corner_round_green);
        }
        else if(type.equals("2")){
            scroll.setBackgroundResource(R.drawable.top_corner_round_brown);

        }

        feededit.setScroller(new Scroller(HomePage.this));
        feededit.setMaxLines(2);
        feededit.setVerticalScrollBarEnabled(true);
        feededit.setLongClickable(false);
        feededit.setMovementMethod(new ScrollingMovementMethod());
        feededit.setTextIsSelectable(false);
        View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // prevent context menu from being popped up, so that user
                // cannot copy/paste from/into any EditText fields.
                return true;
            }
        };

        feededit.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                return true;
            }
        });

        feededit.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });

        feededit.cancelLongPress();


        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] colors = {"தமிழ்", "English", "हिंदी", "తెలుగు","മലയാളം","ಕನ್ನಡಂ"};

                AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
                builder.setTitle("Select your language");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                        if(which==0){
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "உங்கள் கருத்து");

                        }
                        else if(which==1){
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your feedback");

                        }
                        else if(which==2){
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi_IN");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "आपकी राय");

                        }
                        else if(which==3){
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "te_IN");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "మీ అభిప్రాయం");

                        }
                        else if(which==4){
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ml_IN");
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "നിങ്ങളുടെ അഭിപ്രായം");

                        }
                        else if(which==5){
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

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,Notifications_List.class));
            }
        });

        //fradcheck[0] ="check";
        feedrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*feedrad.setChecked(true);
                compradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(false);*/
                str = "Feedback";
                change_back();
                fradcheck[0] ="check";
                //feededit.setVisibility(View.VISIBLE);
                //submitfeed.setVisibility(View.VISIBLE);

            }
        });

        compradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*feedrad.setChecked(false);
                compradio.setChecked(true);
                suggradio.setChecked(false);
                permradio.setChecked(false);*/

                str = "Complaint";
                change_back();
                fradcheck[0] ="check";
                //feededit.setVisibility(View.VISIBLE);
                //submitfeed.setVisibility(View.VISIBLE);


            }
        });

        suggradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*feedrad.setChecked(false);
                compradio.setChecked(false);
                suggradio.setChecked(true);
                permradio.setChecked(false);*/
                str = "Suggestion";
                change_back();
                fradcheck[0] ="check";
                //feededit.setVisibility(View.VISIBLE);
                //submitfeed.setVisibility(View.VISIBLE);


            }
        });

        permradio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*feedrad.setChecked(false);
                compradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(true);*/
                str = "Permission";
                change_back();
                fradcheck[0] ="check";
                //feededit.setVisibility(View.VISIBLE);
                //submitfeed.setVisibility(View.VISIBLE);


            }
        });

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();
                startprogress();
                //GetInfoTask task = new GetInfoTask();
                //task.execute();
            }
        });

        submitfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitfeed.setEnabled(false);
                if (feededit.getText().toString().length() > 0&& !fradcheck[0].isEmpty()) {

                    String feedoption="";
                    progbar.show();

                    try {
                        if (!utils.back_url().isEmpty() && utils.back_url() != null) {
                            openad();
                        } else {
                            HomePage.feedback task = new HomePage.feedback();
                            task.execute(fname, feededit.getText().toString().trim(), str);
                        }
                    }
                    catch (Exception e){
                        HomePage.feedback task = new HomePage.feedback();
                        task.execute(fname, feededit.getText().toString().trim(), str);
                    }


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
        });
    }


    public void openad(){
        Adialog=new Dialog(this);
        Adialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Adialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Adialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View vs = getLayoutInflater().inflate(R.layout.activity_video_ad, null);
        ImageView wb = vs.findViewById(R.id.wb);
        VideoView videoView = vs.findViewById(R.id.videoView);
        CardView card = vs.findViewById(R.id.card);
        TextView textView2 = vs.findViewById(R.id.textView2);


        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(utils.back_linkUrl()));
                    startActivity(browserIntent);
                }
                catch(Exception e){

                }
            }
        }); {

        }

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(utils.back_linkUrl()));
                startActivity(browserIntent);
            }
            catch(Exception e){

            }
            }
        });


        try {
            String frm=utils.back_type();
            String seconds=utils.back_seconds();
            Log.e("seconds",seconds);

            if(frm.equals("Image")){
                back_imageurl = utils.back_url();
                loadimage(wb,videoView);
                startTimer(Integer.parseInt(seconds),card,
                        textView2);

            }

            else if(frm.equals("Gif")){
                back_gifurl = utils.back_url();
                loadgif(wb,videoView);
                startTimer(Integer.parseInt(seconds),card,
                        textView2);

            }

            else if(frm.equals("Video")){
                back_videourl = utils.back_url();
                //wb.visibility= View.INVISIBLE;
                loadvideo(wb,videoView);
                startTimer(Integer.parseInt(utils.back_seconds()),card,
                        textView2);

            }
            else{
                int currentapiVersion = Build.VERSION.SDK_INT;
                HomePage.feedback task = new HomePage.feedback();
                task.execute(fname, feededit.getText().toString().trim(), str);

            }

            Adialog.setContentView(vs);
            Adialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            Adialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            Adialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = (int) (displaymetrics.widthPixels * 1);
            int height = (int) (displaymetrics.heightPixels * 1);
            Adialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Adialog.setCancelable(false);
            Adialog.show();
        }
        catch (Exception e){
            Log.e("error",e.toString());
            HomePage.feedback task = new HomePage.feedback();
            task.execute(fname, feededit.getText().toString().trim(), str);
        }

    }

    public void middlead(){
        Adialog=new Dialog(this);
        Adialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Adialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        Adialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View vs = getLayoutInflater().inflate(R.layout.activity_video_ad, null);
        ImageView wb = vs.findViewById(R.id.wb);
        VideoView videoView = vs.findViewById(R.id.videoView);
        CardView card = vs.findViewById(R.id.card);
        TextView textView2 = vs.findViewById(R.id.textView2);


        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(utils.middlelinkUrl()));
                    startActivity(browserIntent);
                }
                catch(Exception e){

                }
            }
        }); {

        }

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(utils.back_linkUrl()));
                    startActivity(browserIntent);
                }
                catch(Exception e){

                }
            }
        });


        try {
            String frm=utils.middle_type();
            String seconds=utils.middle_seconds();
            Log.e("seconds",seconds);

            if(frm.equals("Image")){
                middle_imageurl = utils.middle_url();
                loadimage(wb,videoView);
                startTimer_middle(Integer.parseInt(seconds),card,
                        textView2);

            }

            else if(frm.equals("Gif")){
                middle_gifurl = utils.middle_url();
                loadgif(wb,videoView);
                startTimer_middle(Integer.parseInt(seconds),card,
                        textView2);

            }

            else if(frm.equals("Video")){
                middle_videourl = utils.middle_url();
                //wb.visibility= View.INVISIBLE;
                loadvideo(wb,videoView);
                startTimer_middle(Integer.parseInt(utils.middle_seconds()),card,
                        textView2);

            }
            else{
                int currentapiVersion = Build.VERSION.SDK_INT;

                /*HomePage.feedback task = new HomePage.feedback();
                task.execute(fname, feededit.getText().toString().trim(), str);*/

            }

            Adialog.setContentView(vs);
            Adialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            Adialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            Adialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = (int) (displaymetrics.widthPixels * 1);
            int height = (int) (displaymetrics.heightPixels * 1);
            Adialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            Adialog.setCancelable(false);
            Adialog.show();
        }
        catch (Exception e){
            Log.e("error",e.toString());
            /*HomePage.feedback task = new HomePage.feedback();
            task.execute(fname, feededit.getText().toString().trim(), str);*/
        }

    }

    public void startTimer_middle(int mins,CardView card,TextView textView2){
        new CountDownTimer(mins*1000, 1000) {

            public void onTick(long millisUntilFinished) {
                // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                card.setVisibility(View.VISIBLE);
                // }
                textView2.setText("Ad ends in : " + millisUntilFinished / 1000 + " sec");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //imageButton.isEnabled=true;
                card.setEnabled(true);
                Adialog.dismiss();
                /*HomePage.feedback task = new HomePage.feedback();
                task.execute(fname, feededit.getText().toString().trim(), str);*/
            }/**/

        }.start();
    }

    public void startTimer(int mins,CardView card,TextView textView2){
        new CountDownTimer(mins*1000, 1000) {

            public void onTick(long millisUntilFinished) {
               // mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                card.setVisibility(View.VISIBLE);
                // }
                textView2.setText("Ad ends in : " + millisUntilFinished / 1000 + " sec");
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                //imageButton.isEnabled=true;
                card.setEnabled(true);
                Adialog.dismiss();
                HomePage.feedback task = new HomePage.feedback();
                task.execute(fname, feededit.getText().toString().trim(), str);
            }/**/

        }.start();
    }

    public void loadvideo(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.VISIBLE);
        wb.setVisibility(View.INVISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        //params.width = Integer.parseInt(String.valueOf((300 * metrics.density)));
        ///params.height = Integer.parseInt(String.valueOf((250 * metrics.density)));
        //videoView.setLayoutParams(params);
        videoView.setMediaController(null);
        videoView.setVideoPath(back_videourl);
        videoView.start();
    }

    public void loadgif(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.INVISIBLE);
        wb.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(back_gifurl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(wb);
        //wb.loadUrl()

    }

    public void loadimage(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.INVISIBLE);
        wb.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(back_imageurl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(wb);
        //wb.loadUrl()

    }


    public void middle_loadvideo(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.VISIBLE);
        wb.setVisibility(View.INVISIBLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        //params.width = Integer.parseInt(String.valueOf((300 * metrics.density)));
        ///params.height = Integer.parseInt(String.valueOf((250 * metrics.density)));
        //videoView.setLayoutParams(params);
        videoView.setMediaController(null);
        videoView.setVideoPath(middle_videourl);
        videoView.start();
    }

    public void middle_loadgif(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.INVISIBLE);
        wb.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(middle_gifurl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(wb);
        //wb.loadUrl()

    }

    public void middle_loadimage(ImageView wb,VideoView videoView){
        videoView.setVisibility(View.INVISIBLE);
        wb.setVisibility(View.VISIBLE);
        Glide.with(this)
                .load(middle_imageurl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(wb);
        //wb.loadUrl()

    }


    public void change_back(){
        if (str.equals("Feedback")){
            selected(feedrad);
            unselected(compradio);
            unselected(suggradio);
            unselected(permradio);
        }
        else if (str.equals("Complaint")){
            unselected(feedrad);
            selected(compradio);
            unselected(suggradio);
            unselected(permradio);
        }
        else if (str.equals("Suggestion")){
            unselected(feedrad);
            unselected(compradio);
            selected(suggradio);
            unselected(permradio);

        }
        else if (str.equals("Permission")){
            unselected(feedrad);
            unselected(compradio);
            unselected(suggradio);
            selected(permradio);
        }
    }

    public void selected(TextView tabTextView){
        tabTextView.setTextColor(ContextCompat.getColor(HomePage.this, R.color.white));
        tabTextView.setBackgroundResource(/*context.resources.getDrawable(*/R.drawable.product_cat_selected);
    }

    public void unselected(TextView tabTextView){
        tabTextView.setTextColor(ContextCompat.getColor(HomePage.this, R.color.black));
        tabTextView.setBackgroundResource(/*context.resources.getDrawable(*/R.drawable.product_cat_unselected);
    }

    private void init() {

        drawerLayout = findViewById(R.id.drawerLayout);
        drawer_layout = findViewById(R.id.drawer_layout);
        home_header = findViewById(R.id.home_header);
        setclick(drawer_layout,drawerLayout,HomePage.this);
        uname = findViewById(R.id.name);
        ibv = findViewById(R.id.ipv);
        gpv = findViewById(R.id.gpv);
        amount = findViewById(R.id.amt);
        purchase = findViewById(R.id.purchase);
        todayreward = findViewById(R.id.todayreward);
        totalreward = findViewById(R.id.totalreward);
        available_reward = findViewById(R.id.totalavail);
        progressBar2= findViewById(R.id.progressBar2);
        progressBar3= findViewById(R.id.progressBar3);
        progressBar4= findViewById(R.id.progressBar4);
        sales = findViewById(R.id.sales);
        target = findViewById(R.id.target);
        achieve = findViewById(R.id.achieve);
        balance = findViewById(R.id.balance);
        wallet_amt = findViewById(R.id.wallet_amt);
        home_header.setBackgroundColor(getResources().getColor(R.color.eee));
        feededit= findViewById(R.id.feededit);

        submitfeed= findViewById(R.id.submit);
        imageView9= findViewById(R.id.imageView9);

        feedrad= findViewById(R.id.fdradio);
        compradio= findViewById(R.id.cmpradio);
        suggradio= findViewById(R.id.suggradio);
        permradio= findViewById(R.id.permradio);
        tittext= findViewById(R.id.tittext);
        tittextstar= findViewById(R.id.tittextstar);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                menuvalue = 1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                menuvalue = 0;
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_more_vert_24px);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(mDrawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });



        //home_header.setBackgroundColor(0xFFEEEEEE);
    }


    private class feedback extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("feedbackTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("user",param[0]);
                jobj.put("comment",param[1]);
                jobj.put("type",param[2]);


                if(Build.VERSION.SDK_INT>=21&&Build.VERSION.SDK_INT<=23) {
                    Log.i("Videossubmitinput", Appconstants.domain3+Appconstants.FEEDBACK_APIs + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(Appconstants.domain3+Appconstants.FEEDBACK_APIs, jobj, "");
                }

                else if(Build.VERSION.SDK_INT>23&&Build.VERSION.SDK_INT<=25) {
                    Log.i("Videossubmitinput", Appconstants.domain78+Appconstants.FEEDBACK_APIs + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(Appconstants.domain78+Appconstants.FEEDBACK_APIs, jobj, "");
                }

                else if(Build.VERSION.SDK_INT>25&&Build.VERSION.SDK_INT<=27) {
                    Log.i("Videossubmitinput", Appconstants.domain78+Appconstants.FEEDBACK_APIs + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(Appconstants.domain78+Appconstants.FEEDBACK_APIs, jobj, "");
                }

                else if(Build.VERSION.SDK_INT==28) {
                    Log.i("Videossubmitinput", Appconstants.domains+Appconstants.FEEDBACK_APIs + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(Appconstants.domains+Appconstants.FEEDBACK_APIs, jobj, "");
                }

                else if(Build.VERSION.SDK_INT>=29) {
                    Log.i("Videossubmitinput", Appconstants.domain10+Appconstants.FEEDBACK_APIs + "    " + jobj.toString() + "");
                    result = con.sendHttpPostjson2(Appconstants.domain10+Appconstants.FEEDBACK_APIs, jobj, "");
                }

                //Log.i("checkInput feedback", Appconstants.FEEDBACK_API+"    "+jobj.toString());
                //result = con.sendHttpPostjson2(Appconstants.FEEDBACK_API,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("tabresp", resp + "");
            if(progbar.isShowing()&&progbar!=null){
                progbar.dismiss();
            }

            try {
                if (resp != null) {

                    feededit.setText(null);
                    Toast.makeText(HomePage.this,"Thanks for your valuable feedback!.",Toast.LENGTH_LONG).show();


                } else {

                    Toast.makeText(HomePage.this,"Failed to upload feedback.",Toast.LENGTH_LONG).show();
                    submitfeed.setEnabled(true);

                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

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
            //Log.i("PerformVersionTask", "started");
            //logger.info("PerformVersionTask started");
        }

        @Override
        protected String doInBackground(final Void... arg0) {
            //Log.i("PerformVersionTask", "center");
            try{
                Connection con = new Connection();
                resp = con.connStringResponse(domain+"version.php?");
                JSONArray array=new JSONArray(resp);
                JSONObject obj = array.getJSONObject(0);
                Number =Integer.parseInt(obj.getString("version"));
                //name = obj.getString("data");
                PackageInfo pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionNumber = pinfo.versionCode;
                versionname=pinfo.versionName;

                Log.e("Versionchecker", versionname +",   "+ versionNumber+",   "+Number);

                if (versionNumber == Number) {
                    resp = "false";
                } else {
                    resp = "true";
                }
            }catch(Exception e1){
                e1.printStackTrace();
                //logger.info("PerformVersionTask resp"+e1.getMessage());
                resp="false";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String resp) {
            //logger.info("PerformVersionTask final "+resp);
            //Log.e("Performversionresp", resp);
            if(resp.equals("false"))
            {

            }
            else if(resp.equals("true")){
                try {
                    final Dialog update = new Dialog(HomePage.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    View v=getLayoutInflater().inflate(R.layout.app_update_dialog,null);
                    TextView updatebut= v.findViewById(R.id.update);
                    TextView laterbut= v.findViewById(R.id.later);
                    TextView titlename= v.findViewById(R.id.titlename);
                    update.setContentView(v);
                    update.setCancelable(false);
                    update.show();
                    titlename.setText("New Version 1."+Number);
                    TextView content= v.findViewById(R.id.content);
                    content.setText("You are using an older version of Health Zone ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                            Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en"));
                            startActivity(intt);
                        }
                    });
                    laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });
                }catch(Exception e){
                    //logger.info("PerformVersionTask error" + e.getMessage());
                }
            }
        }
    }

    private class GetSubusersTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("GetSubusersTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                //Log.i("check Input", Appconstants.BINARY_USERS_LIST + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.BINARY_USERS_LIST, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
           // Log.i("tabresp", resp + "");
            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        utils.savePreferences("subuser", jarr.toString());
                       // Log.i("utilssssnameee", utils.loadSubusers() + "   dds");
                        setclick(drawer_layout, drawerLayout, HomePage.this);
                    } else {
                        utils.savePreferences("subuser", "");
                    }

                } else {
                    stopprogress();
                    retry.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                stopprogress();
                retry.show();
            }
        }
    }



    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
        {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else if(Adialog.isShowing()) {
            Toast.makeText(getApplicationContext(),"Can't skip AD",Toast.LENGTH_SHORT).show();
        }
        else
            {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_portal, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (update != null && update.isShowing()) {
            update.dismiss();
        }

        //startprogress();
        GetInfoTask task=new GetInfoTask();
        task.execute();


    }

    public class Videosubmit extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {

            prog = new Dialog(HomePage.this);
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
            int versionCode = BuildConfig.VERSION_CODE;

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

                    android.app.AlertDialog.Builder alert=new android.app.AlertDialog.Builder(HomePage.this);
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
                            utils.savePreferences("countvalue","");

                            Toast.makeText(HomePage.this, "Logged Out.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(HomePage.this, Login.class));
                        }
                    });
                    android.app.AlertDialog alerts = alert.create();
                    alert.show();
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class GetInfoTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //progbar.show();
            //Log.i("GetInfoTask", "started");
            Adialog=new Dialog(HomePage.this);

        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_AMOUNT, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            try {
                if (resp != null) {
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            ReportsPojo bo = new ReportsPojo();
                            utils.savePreferences("ibv", jobj.getString("ibv").trim().equalsIgnoreCase("null") || jobj.getString("ibv").trim().length() == 0 ? "0" : jobj.getString("ibv"));
                            utils.savePreferences("gbv", jobj.getString("gbv").trim().equalsIgnoreCase("null") || jobj.getString("gbv").trim().length() == 0 ? "0" : jobj.getString("gbv"));
                            utils.savePreferences("commition", jobj.getString("p_commition").trim().equalsIgnoreCase("null") || jobj.getString("p_commition").trim().length() == 0 ? "0" : jobj.getString("p_commition"));
                            utils.savePreferences("withdraw", jobj.getString("withdraw").trim().equalsIgnoreCase("null") || jobj.getString("withdraw").trim().length() == 0 ? "0" : jobj.getString("withdraw"));
                            utils.savePreferences("purchase", jobj.getString("purchase").trim().equalsIgnoreCase("null") || jobj.getString("purchase").trim().length() == 0 ? "0" : jobj.getString("purchase"));
                            utils.savePreferences("sales", jobj.getString("sales").trim().equalsIgnoreCase("null") || jobj.getString("sales").trim().length() == 0 ? "0" : jobj.getString("sales"));
                            utils.savePreferences("target", jobj.getString("targetamount").trim().equalsIgnoreCase("null") || jobj.getString("targetamount").trim().length() == 0 ? "0" : jobj.getString("targetamount"));
                            utils.savePreferences("achieve", jobj.getString("targetachieved").trim().equalsIgnoreCase("null") || jobj.getString("targetachieved").trim().length() == 0 ? "0" : jobj.getString("targetachieved"));
                            utils.savePreferences("balance", jobj.getString("targetbalance").trim().equalsIgnoreCase("null") || jobj.getString("targetbalance").trim().length() == 0 ? "0" : jobj.getString("targetbalance"));
                            utils.savePreferences("wallet", jobj.getString("wallet").trim().equalsIgnoreCase("null") || jobj.getString("wallet").trim().length() == 0 ? "0" : jobj.getString("wallet"));
                            utils.savePreferences("newvideo", jobj.getString("newvideo").trim().equalsIgnoreCase("null") || jobj.getString("newvideo").trim().length() == 0 ? "0" : jobj.getString("newvideo"));
                            utils.savePreferences("today_reward", jobj.getString("today_reward").trim().equalsIgnoreCase("null") || jobj.getString("today_reward").trim().length() == 0 ? "0" : jobj.getString("today_reward"));
                            utils.savePreferences("total_reward", jobj.getString("total_reward").trim().equalsIgnoreCase("null") || jobj.getString("total_reward").trim().length() == 0 ? "0" : jobj.getString("total_reward"));
                            utils.savePreferences("available_reward", jobj.getString("available_reward").trim().equalsIgnoreCase("null") || jobj.getString("available_reward").trim().length() == 0 ? "0" : jobj.getString("available_reward"));
                            utils.savePreferences("proedit", jobj.getString("profile_verify").trim().equalsIgnoreCase("null") || jobj.getString("profile_verify").trim().length() == 0 ? "0" : jobj.getString("profile_verify"));
                            utils.savePreferences("image", jobj.getString("image").trim().equalsIgnoreCase("null") || jobj.getString("image").trim().length() == 0 ? "0" : jobj.getString("image"));
                            utils.savePreferences("cmd_silver", jobj.getString("cmd_silver").trim().equalsIgnoreCase("null") || jobj.getString("cmd_silver").trim().length() == 0 ? "0" : jobj.getString("cmd_silver"));
                            utils.savePreferences("cmd_gold", jobj.getString("cmd_gold").trim().equalsIgnoreCase("null") || jobj.getString("cmd_gold").trim().length() == 0 ? "0" : jobj.getString("cmd_gold"));
                            utils.savePreferences("support", jobj.getString("support").trim().equalsIgnoreCase("null") || jobj.getString("support").trim().length() == 0 ? "0" : jobj.getString("support"));

                            if(!jobj.getString("pinName").isEmpty()&&(jobj.getString("pinName")!=null)){
                                utils.savePreferences("pinName", jobj.getString("pinName").trim());
                            }
                            if(!jobj.getString("base_wallet").isEmpty()&&(jobj.getString("base_wallet")!=null)){
                                utils.savePreferences("base_wallet", jobj.getString("base_wallet").trim());
                            }


                            reqdate=jobj.getString("regDate");

                            if(!jobj.getString("mpin").isEmpty()){
                                utils.savePreferences("mpin",jobj.getString("mpin").trim());
                            }
                            else if(jobj.getString("mpin").isEmpty()){
                                utils.savePreferences("mpin","");
                                utils.savePreferences("mpin_first","");

                            }

                            try {
                                if (utils.loadImage().toString().trim().length() > 0) {
                                    Picasso.with(HomePage.this).load(utils.loadImage()).placeholder(R.mipmap.male).resize(200, 200).into(pimg);
                                } else {
                                    Picasso.with(HomePage.this).load(R.mipmap.male).into(pimg);
                                }
                            }
                            catch (Exception e){

                            }

                            amountvals = (String.format("%.2f", Double.parseDouble(utils.loadCommition())));
                            gpvvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadgbv())) + "");
                            ibvvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadIbv())) + "");
                            purchasevals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadPurchase())) + "");
                            salesvals  = (String.format("Rs.%.2f", Double.parseDouble(utils.loadSales())) + "");
                            planvals = jobj.getString("plan").trim().equalsIgnoreCase("null") || jobj.getString("plan").trim().length() == 0 ? "0" : jobj.getString("plan");
                            utils.savePreferences("plantype",planvals);
                            String logout= jobj.getString("logout").trim().equalsIgnoreCase("null") || jobj.getString("logout").trim().length() == 0 ? "0" : jobj.getString("logout");
                            star= jobj.getString("star_performer").trim().equalsIgnoreCase("null") || jobj.getString("star_performer").trim().length() == 0 ? "0" : jobj.getString("star_performer");
                            String cmd_silver= jobj.getString("cmd_silver").trim().equalsIgnoreCase("null") || jobj.getString("cmd_silver").trim().length() == 0 ? "0" : jobj.getString("cmd_silver");
                            String cmd_gold= jobj.getString("cmd_gold").trim().equalsIgnoreCase("null") || jobj.getString("cmd_gold").trim().length() == 0 ? "0" : jobj.getString("cmd_gold");

                            String point=(utils.available_reward().toString().split("\\.",2)[0]);
                            System.out.println("point"+point);


                            if(star.equals("1"))
                            {
                                constprf.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                constprf.setVisibility(View.GONE);
                            }
                                TextView silver = findViewById(R.id.cmd1);
                                TextView gold = findViewById(R.id.cmd2);
                                LinearLayout cmdlin = findViewById(R.id.cmd_silver);
                            if(cmd_silver.equals("1")){
                               // cmdlin.setVisibility(View.VISIBLE);
                                silver.setVisibility(View.VISIBLE);
                                if(!cmd_gold.equals("1")||(cmd_gold.isEmpty())){
                                    gold.setVisibility(View.GONE);

                                }
                                else if(cmd_gold.equals("1")){
                                    gold.setVisibility(View.VISIBLE);

                                }
                                /*ImageView imageView30 = findViewById(R.id.imageView30);
                                TextView textView90 = findViewById(R.id.textView90);
                                ImageView imageView31 = findViewById(R.id.imageView31);
                                imageView30.setImageResource(R.mipmap.silver);
                                textView90.setText("CMD SLIVER");
                                imageView31.setImageResource(R.mipmap.silver);*/

                            }else {
                                cmdlin.setVisibility(View.GONE);
                            }


                           /* if(!utils.pinName().isEmpty()&&utils.pinName().equals("Base Pin"))
                            {
                                cr_frame.setVisibility(View.VISIBLE);
                                redeemwallet.setVisibility(View.GONE);
                                terms_frame.setVisibility(View.GONE);
                                wallet.setVisibility(View.GONE);
                            }*/

                            if(cmd_gold.equals("1")){
                               // cmdlin.setVisibility(View.VISIBLE);
                                gold.setVisibility(View.VISIBLE);
                                if(!cmd_silver.equals("1")||(cmd_silver.isEmpty())){
                                    silver.setVisibility(View.GONE);
                                }
                                else if(cmd_silver.equals("1")){
                                    silver.setVisibility(View.VISIBLE);

                                }

                                /*ImageView imageView30 = findViewById(R.id.imageView30);
                                TextView textView90 = findViewById(R.id.textView90);
                                ImageView imageView31 = findViewById(R.id.imageView31);
                                imageView30.setImageResource(R.mipmap.gold);
                                textView90.setText("CMD GOLD");
                                imageView31.setImageResource(R.mipmap.gold);*/

                            }else {
                                cmdlin.setVisibility(View.GONE);
                            }

                            dtime=jobj.getString("dtime");
                             notification=jobj.getString("notification");
                            Log.e("dtime",dtime);
                            Log.e("notification",utils.loadnotiftime());

                             if(utils.loadnotiftime().isEmpty()) {
                                 utils.savePreferences("notitime", dtime);
                             }

                            if(utils.loadnotif().isEmpty()){
                                noticnt.setVisibility(View.VISIBLE);
                                noticnt.setText(notification);

                            }
                            else if(utils.loadnotif().equals("seen")&&(!dtime.equals(utils.loadnotiftime()))){
                                noticnt.setVisibility(View.VISIBLE);
                                noticnt.setText(notification);
                                Log.e("notification1",utils.loadnotiftime());

                            }
                            else if(utils.loadnotif().equals("seen")&&(dtime.equals(utils.loadnotiftime()))){
                                noticnt.setVisibility(View.GONE);
                                Log.e("notification2",utils.loadnotiftime());

                                //noticnt.setText(notification);
                            }

                            if(logout.equals("0")&&(logout != null)){
                                Videosubmit task=new Videosubmit();
                               task.execute();

                            }

                            targetvals  = (utils.loadTarget());
                            achievevals  = (utils.loadAchieve());
                            balancevals  = (utils.loadBalance());
                            wallet_amtvals  = (utils.loadWallet());
                            todayrewardvals  = (utils.today_reward());
                            totalrewardvals  = (utils.total_reward());
                            available_rewardvals  = (utils.available_reward());
                            newvideo = (jobj.getString("newvideo"));
                        }
                        try{
                            if(planvals.equals("Super Premium")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.superpremiumnew);
                                tittext.setText(planvals);
                                try{
                                starreq_lay.setVisibility(View.VISIBLE);
                                starmonthly_lay.setVisibility(View.VISIBLE);
                                rechargewallet1.setVisibility(View.VISIBLE);
                            }
                                catch (Exception e){

                            }
                            }
                            else if(planvals.equals("Star Premium")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.starpremiumnew);
                                tittext.setText(planvals);


                            }
                            else if(planvals.equals("Premium")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.premiumicon);
                                tittext.setText(planvals);

                            }
                            else if(planvals.equals("Entrance")||planvals.equals("entrance")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.entryicon);
                                tittext.setText(planvals);

                            }
                            else if(planvals.equals("Free")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.freeicon);
                                tittext.setText(planvals);

                            }
                            else if(planvals.equals("Super Salary Achiever")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.supersalary);
                                tittext.setText(planvals);
                                try {
                                    superreq_lay.setVisibility(View.VISIBLE);
                                    supermonthly_lay.setVisibility(View.VISIBLE);
                                }
                                catch (Exception e){

                                }

                            }

                            else if(planvals.equals("Trial")||planvals.equals("trial")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.trialicon);
                                tittext.setText(planvals);

                            }

                            else if(planvals.equals("Crorepati Plan")||planvals.equals("Crorepati")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.crorepng);
                                tittext.setText(planvals);

                            }
                            else if(planvals.equals("Welcome Pin")){
                                imageView9.setVisibility(View.VISIBLE);
                                imageView9.setImageResource(R.mipmap.welcome);
                                tittext.setText(planvals);

                            }
                            else{
                                imageView9.setVisibility(View.INVISIBLE);
                                //imageView9.setImageResource(R.mipmap.basepins_img_foreground);
                                tittext.setText(planvals);
                            }
                            try{
                                if(!reqdate.isEmpty()&&reqdate!=null){
                                    String[] arr=reqdate.split("-");
                                    if(Integer.parseInt(arr[0])<2021){

                                    }
                                    else if(Integer.parseInt(arr[0])>=2021){
                                        if(Integer.parseInt(arr[1])>=2){
                                            if(Integer.parseInt(arr[2])>=16){
                                                //starreq_lay.setVisibility(View.GONE);
                                                //starmonthly_lay.setVisibility(View.GONE);
                                            }
                                        }
                                        else{

                                        }
                                    }
                                }
                            }
                            catch (Exception e){


                            }
                        }
                        catch (Exception e){

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

                        welc_frame.setVisibility(View.VISIBLE);
                       // welc_child_layred.setVisibility(View.VISIBLE);

                    try {
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

                    }
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
                Log.e("err",e.toString());
                //retry.show();
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
            if (menuvalue == 0)
                drawerLayout.openDrawer(Gravity.LEFT);
            else
                drawerLayout.closeDrawers();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showthankspopup() {
        final Dialog open = new Dialog(HomePage.this);
        open.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.success_popup1, null);
        TextView ok= popup.findViewById(R.id.yes);

        //open.getWindow().getAttributes().windowAnimations = R.style.MyCustomTheme;
        open.setContentView(popup);
        open.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        open.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lparam = new WindowManager.LayoutParams();
        lparam.copyFrom(open.getWindow().getAttributes());
        open.setCancelable(true);
        open.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        open.show();

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                open.dismiss();
                edit = shp.edit();
                edit.putString("Popupstatus", "");
                edit.commit();
            }
        });
    }

   /* public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
                char c = source.charAt(index);
                if (isCharAllowed(c))
                    sb.append(c);
                else
                    keepOriginal = false;
            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };*/

    private static boolean isCharAllowed(char c) {
        return Character.isLetterOrDigit(c) || Character.isSpaceChar(c);
    }


    public void failurepopup() {
        final Dialog open = new Dialog(HomePage.this);
        open.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.failure_popup, null);
        TextView ok= popup.findViewById(R.id.yes);

        //open.getWindow().getAttributes().windowAnimations = R.style.MyCustomTheme;
        open.setContentView(popup);
        open.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        open.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        WindowManager.LayoutParams lparam = new WindowManager.LayoutParams();
        lparam.copyFrom(open.getWindow().getAttributes());
        open.setCancelable(true);
        open.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        open.show();

        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                open.dismiss();
                edit = shp.edit();
                edit.putString("Popupstatus", "");
                edit.commit();
            }
        });
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

}
