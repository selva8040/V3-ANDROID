package com.elancier.healthzone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.MenuItemAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.GifImageView;
import com.elancier.healthzone.Common.Helper;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.MenuItemBo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by Manikandan on 6/20/2017.
 */
public class MainView extends AppCompatActivity {

    LinearLayout profile_lay, reports_lay, tree_lay,more_lay,autofill_header, profile_header, reports_header, tree_header, home_header,more_header,
            auto_fill_lay,vip_lay,cr_superbuis,cr_superlead,cr_rech,cr_super;
    TextView profile, tree, reports,titlename, my_portal, my_info, my_sponcer, my_bank, direct_team, genealogy_tree,reward_hist,
            ibv_reports, gbv_reports, bonus_reports, withdraw_reports,purchase_detail,purchase_quotation,livefill_bonus, reward_points;
    LinearLayout my_portal_lay, my_info_lay, my_sponcer_lay, my_bank_lay, direct_team_lay, genealogy_tree_lay,prime_fill_bonus_lay,super_fill_bonus_lay,
            ibv_reports_lay, gbv_reports_lay,marketing_frame,marketing_frame1,marketing_frame2,marketing_frame3,marketing_frame4, bonus_reports_lay, withdraw_reports_lay, stock_list_lay,auto_withdraw_lay,
            about_lay, contact_lay,feedback_lay, share_lay, rating_lay, logout_lay,purchase_detail_lay,purchase_quotation_lay,binary_pin_lay,over_all_bonus_lay,
            livefill_bonus_lay,live_lay,binary_tree_lay,binary_tree_item_lay,binary_sponsor_lay,binary_sponsor_item_lay,binary_pair_lay,account_child_layred,reward_reduce,
            binary_pair_item_lay,binary_point_lay,binary_point_item_lay,ibv_lay,gbv_lay,reports_child_lay,stock_frame, reward_points_lay,reward_history,alreadyvip_lay,newvip_lay,
            support_layred,supports_lay,support_history,support_reduce,bank_history,plan_history,recharge_history
            ,request_child_lay,normalreq_lay,starreq_lay,superreq_lay,reward_wallet,
            monthly_child_lay,normalmonthly_lay,starmonthly_lay,supermonthly_lay,
            coupon_child_lay,coup_monthly_lay,coupmonthly_lay,cr_child_layred,welc_child_layred,cr_terms,
            cr_wallet,cr_redeem,cr_prbox,cr_rech2,crore_lay,welcome_terms,welcome_wallet,
            welcome_redeem,welcome_prbox,
            benefit_layred,grivplan_history,benefit_lay,benefit_history,benefit_reduce,benefitplan_history;

    FrameLayout wallet,sales_point_lay,terms_frame,rechargewallet1,cmds_frame,pin_frame_service,cmdg_frame,rechargewallet,redeemwallet,
                benefit_frame,onlinepay,support_frame,saver_frame,compframe,admin_frame,v3pinlay,online_lay,welcome_report,rewardred_lay,
                datereq,supersalry_frame,cr_frame,welc_frame,monthreport,logout_frame,vip_frame,pin_frame,commission_frame,tree_frame,coupon_frame,reports_frame;

    ImageView profile_img,autofill_img,  laterbut,tree_img, reports_img,more_arrow,binary_tree_arrow,binary_sponsor_arrow,binary_pair_arrow,binary_point_arrow;

    ListView binary_tree_listvw,binary_sponsor_listvw,binary_pair_listvw,binary_point_listvw;

    ArrayList<MenuItemBo> binary_tree_list,binary_sponsor_list,binary_pair_list,binary_point_list;
    boolean autofill_check = false;
    boolean profile_check = false;
    boolean tree_check = false;
    boolean reports_check = false;
    boolean more_check = false;
    DrawerLayout drawerLayout;
    CircleImageView pimg;
    ImageView pimg2;
    Dialog progbar,prog;
    Utils utils;
    TextView chngpass,version,download;
    DownloadManager downloadManager;

    Dialog about, editcat;
    TextView title, submit, save, choose_file, nodata;
    ImageView back, pencil, category_img, edit_img;
    ImageButton prochange;
    LinearLayout phone_lay, email_lay;
    LinearLayout saleslay,purchaselay;
    EditText otp_edit_box1;
    Button button14;
    EditText  otp_edit_box2;
    EditText  otp_edit_box3;
    EditText  otp_edit_box4;
    EditText  editTextmob;
    EditText  editTextmob1;
    EditText  editTextotp;
    EditText  editTextotp1;
    Button  getotp;
    ProgressBar progressBar6;
    Button  verifyotp;
    GifImageView gifImageView;

    LinearLayout homebut;
    LinearLayout moblays;
    LinearLayout otplay;
    String itsupport="";
    String langway="";
    String pindetails="";
    String upgradepin="";
    String recharge="";
    String marketing="";
    String marketing1="";
    String marketing2="";
    String marketing3="";
    String marketing4="";
    String corection="";
    String bankcorection="";
    private static final int PERMISSION_REQUEST_CODE = 200;

    String star="";
    String grieve="";
    String croreben="";
    String supers="";
    String salary="";
    String coupon="";
    String Respotp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    public void loadprogress() {
        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
    }

    public void startprogress() {
        progbar.show();
    }

    public void stopprogress() {
        progbar.dismiss();
    }

    public void setclick(LinearLayout view, final DrawerLayout lay, final Context context) {

     /*   DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        view.getLayoutParams().width = (int) (displaymetrics.widthPixels * 0.8);*/


        auto_fill_lay = (LinearLayout) findViewById(R.id.auto_fill_lay);
        profile_lay = (LinearLayout) findViewById(R.id.profile_lay);
        reports_lay = (LinearLayout) findViewById(R.id.reports_lay);
        tree_lay = (LinearLayout) findViewById(R.id.tree_lay);
        newvip_lay = (LinearLayout) findViewById(R.id.newvip_lay);
        alreadyvip_lay = (LinearLayout) findViewById(R.id.vip_lay);
        online_lay= (FrameLayout) findViewById(R.id.online_frame);
        v3pinlay= (FrameLayout) findViewById(R.id.v3pinlay);
        welcome_report= (FrameLayout) findViewById(R.id.welcome_report);
        supersalry_frame= (FrameLayout) findViewById(R.id.supersalry_frame);
        more_lay = (LinearLayout) findViewById(R.id.more_lay);
        autofill_header = (LinearLayout) findViewById(R.id.autofill_header);
        profile_header = (LinearLayout) findViewById(R.id.profile_header);
        tree_header = (LinearLayout) findViewById(R.id.tree_header);
        reports_header = (LinearLayout) findViewById(R.id.reports_header);
        home_header = (LinearLayout) findViewById(R.id.home_header);
        more_header = (LinearLayout) findViewById(R.id.more_header);
        wallet = (FrameLayout) findViewById(R.id.wallet);
        logout_frame = (FrameLayout) findViewById(R.id.logout_frame);
        commission_frame = (FrameLayout) findViewById(R.id.commission_frame);
        stock_frame = (LinearLayout) findViewById(R.id.stock_lay);
        tree_frame = (FrameLayout) findViewById(R.id.tree_frame);
        vip_frame = (FrameLayout) findViewById(R.id.vip_frame);
        datereq = (FrameLayout) findViewById(R.id.dtaereq_frame);
        monthreport = (FrameLayout) findViewById(R.id.month_frame);
        rewardred_lay= (FrameLayout) findViewById(R.id.reduce_frame);
        reports_frame = (FrameLayout) findViewById(R.id.reports_frame);
        pin_frame = (FrameLayout) findViewById(R.id.pin_frame);
        stock_list_lay = (LinearLayout) findViewById(R.id.stock_list_lay);
        compframe= (FrameLayout) findViewById(R.id.compframe);
        sales_point_lay = (FrameLayout) findViewById(R.id.sales_frame);
        admin_frame= (FrameLayout) findViewById(R.id.admin_frame);
        terms_frame= (FrameLayout) findViewById(R.id.terms_frame);
        redeemwallet= (FrameLayout) findViewById(R.id.redeemwallet);
        rechargewallet= (FrameLayout) findViewById(R.id.rechargewallet);
        about_lay = (LinearLayout) findViewById(R.id.about_lay);
        contact_lay = (LinearLayout) findViewById(R.id.contact_lay);
        feedback_lay = (LinearLayout) findViewById(R.id.feedback_lay);
        share_lay = (LinearLayout) findViewById(R.id.share_lay);
        rating_lay = (LinearLayout) findViewById(R.id.rating_lay);
        logout_lay = (LinearLayout) findViewById(R.id.logout);
        utils = new Utils(getApplicationContext());
        pimg = (CircleImageView) findViewById(R.id.pimg);
        pimg2 = (ImageView) findViewById(R.id.pimg2);
        saleslay=(LinearLayout)findViewById(R.id.sales_lay);
        purchaselay=(LinearLayout)findViewById(R.id.purchase_lay);
        binary_tree_lay=(LinearLayout)findViewById(R.id.binarytree_header);
        binary_tree_item_lay=(LinearLayout)findViewById(R.id.binarytreelay);
        binary_sponsor_lay=(LinearLayout)findViewById(R.id.binarysponsor_header);
        binary_sponsor_item_lay=(LinearLayout)findViewById(R.id.binarysponsorlay);
        binary_pair_lay=(LinearLayout)findViewById(R.id.binarypair_header);
        binary_pair_item_lay=(LinearLayout)findViewById(R.id.binarypairlay);
        binary_point_lay=(LinearLayout)findViewById(R.id.binarypoint_header);
        binary_point_item_lay=(LinearLayout)findViewById(R.id.binarypointlay);
        binary_pin_lay=(LinearLayout)findViewById(R.id.binary_pin_lay);
        coupon_child_lay=(LinearLayout)findViewById(R.id.coupon_child_lay);
        coup_monthly_lay=(LinearLayout)findViewById(R.id.coup_monthly_lay);
        coupmonthly_lay=(LinearLayout)findViewById(R.id.coupmonthly_lay);
        over_all_bonus_lay=(LinearLayout)findViewById(R.id.over_all_bonus_lay);
        auto_withdraw_lay=(LinearLayout)findViewById(R.id.auto_withdraw);
        prime_fill_bonus_lay=(LinearLayout)findViewById(R.id.primefill_bonus_lay);
        super_fill_bonus_lay=(LinearLayout)findViewById(R.id.superfill_bonus_lay);
        homebut=(LinearLayout) findViewById(R.id.homelay);
        ibv_lay=(LinearLayout) findViewById(R.id.ibv_lay);
        gbv_lay=(LinearLayout) findViewById(R.id.gbv_lay);
        reports_child_lay=(LinearLayout) findViewById(R.id.reports_child_lay);
        vip_lay = (LinearLayout) findViewById(R.id.vip_child_lay);
        onlinepay= (FrameLayout) findViewById(R.id.onlinepay);
        reward_points_lay=(LinearLayout) findViewById(R.id.reward_points_lay);
        reward_history=(LinearLayout) findViewById(R.id.reward_history);
        account_child_layred=(LinearLayout) findViewById(R.id.account_child_layred);
        reward_reduce=(LinearLayout) findViewById(R.id.reward_reduce);
        reward_points=(TextView) findViewById(R.id.reward_points);
        reward_hist=(TextView) findViewById(R.id.reward_hist);
        saver_frame=(FrameLayout) findViewById(R.id.saver_frame);
        coupon_frame=(FrameLayout) findViewById(R.id.coupon_frame);
        chngpass=(TextView) findViewById(R.id.chgpass);
        version=(TextView) findViewById(R.id.version);
        support_frame=(FrameLayout) findViewById(R.id.support_frame);
        marketing_frame=(LinearLayout) findViewById(R.id.market_history);
        marketing_frame1=(LinearLayout) findViewById(R.id.market_history1);
        marketing_frame2=(LinearLayout) findViewById(R.id.market_history2);
        marketing_frame3=(LinearLayout) findViewById(R.id.market_history3);
        marketing_frame4=(LinearLayout) findViewById(R.id.market_history4);
        autofill_img = (ImageView) findViewById(R.id.autofill_img);
        profile_img = (ImageView) findViewById(R.id.profile_img);
        tree_img = (ImageView) findViewById(R.id.tree_img);
        reports_img = (ImageView) findViewById(R.id.reports_img);
        more_arrow = (ImageView) findViewById(R.id.more_arrow);
        binary_tree_arrow = (ImageView) findViewById(R.id.binarytree_img);
        binary_sponsor_arrow = (ImageView) findViewById(R.id.binarysponsor_img);
        binary_pair_arrow = (ImageView) findViewById(R.id.binarypair_img);
        binary_point_arrow = (ImageView) findViewById(R.id.binarypoint_img);
        gifImageView = (GifImageView) findViewById(R.id.GifImageView);
        cmds_frame=(FrameLayout) findViewById(R.id.cmds_frame);
        cmdg_frame=(FrameLayout) findViewById(R.id.cmdg_frame);
        pin_frame_service=(FrameLayout) findViewById(R.id.pin_frame_service);
        rechargewallet1=(FrameLayout) findViewById(R.id.rechargewallet1);
        //Benefits
        benefit_layred=(LinearLayout) findViewById(R.id.benefit_layred);
        grivplan_history=(LinearLayout) findViewById(R.id.grivplan_history);
        benefit_lay=(LinearLayout) findViewById(R.id.benefit_lay);
        benefit_history=(LinearLayout) findViewById(R.id.benefit_history);
        benefit_reduce=(LinearLayout) findViewById(R.id.benefit_reduce);
        benefitplan_history=(LinearLayout) findViewById(R.id.benefitplan_history);
        benefit_frame=(FrameLayout) findViewById(R.id.benefit_frame);
        crore_lay=(LinearLayout) findViewById(R.id.crore_lay);
        cr_child_layred=findViewById(R.id.cr_child_layred);
        welc_child_layred=findViewById(R.id.welcome_child_layred);
        cr_terms=findViewById(R.id.cr_terms);
        welcome_terms=findViewById(R.id.welcome_terms);
        cr_rech2=findViewById(R.id.cr_rech2);
        cr_wallet=findViewById(R.id.cr_wallet);
        welcome_wallet=findViewById(R.id.welc_wallet);
        cr_redeem=findViewById(R.id.cr_redeem);
        welcome_redeem=findViewById(R.id.welc_redeem);
        cr_prbox=findViewById(R.id.cr_prbox);
        welcome_prbox=findViewById(R.id.welc_prbox);
        cr_frame=findViewById(R.id.cr_frame);
        welc_frame=findViewById(R.id.welcome_frame);
        cr_superbuis=findViewById(R.id.cr_superbuis);
        cr_superlead=findViewById(R.id.cr_superlead);
        cr_rech=findViewById(R.id.cr_rech);
        cr_super=findViewById(R.id.cr_super);

        cr_prbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,PROM_BOX_history.class));

            }
        });

        welcome_prbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,PROM_BOX_history.class).putExtra("from","welcome"));

            }
        });

        cr_rech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,Crorepathi_recharge.class).putExtra("from","one"));

            }
        });

        cr_super.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,Crore_Salary_history.class).putExtra("from","crore"));

            }
        });

        cr_rech2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainView.this,Crorepathi_recharge.class).putExtra("from","two"));

            }
        });

        binary_tree_listvw=(ListView)findViewById(R.id.binarytreelistvw);
        binary_sponsor_listvw=(ListView)findViewById(R.id.binarysponsorlistvw);
        binary_pair_listvw=(ListView)findViewById(R.id.binarypairlistvw);
        binary_point_listvw=(ListView)findViewById(R.id.binarypointlistvw);
        profile = (TextView) findViewById(R.id.profile);
        tree = (TextView) findViewById(R.id.tree);
        reports = (TextView) findViewById(R.id.reports);
        purchase_detail = (TextView) findViewById(R.id.purchase_detail);
        purchase_quotation = (TextView) findViewById(R.id.purchase_quotation);
        livefill_bonus = (TextView) findViewById(R.id.livefill_bonus);
        my_portal = (TextView) findViewById(R.id.my_portal);
        my_info = (TextView) findViewById(R.id.my_info);
        my_sponcer = (TextView) findViewById(R.id.my_sponsor);
        my_bank = (TextView) findViewById(R.id.my_bank);
        direct_team = (TextView) findViewById(R.id.direct_team);
        genealogy_tree = (TextView) findViewById(R.id.genealogy_tree);
        ibv_reports = (TextView) findViewById(R.id.ibv_reports);
        gbv_reports = (TextView) findViewById(R.id.gbv_reports);
        bonus_reports = (TextView) findViewById(R.id.bonus_reports);
        withdraw_reports = (TextView) findViewById(R.id.withdraw_reports);
        purchase_detail_lay = (LinearLayout) findViewById(R.id.purchase_detail_lay);
        purchase_quotation_lay = (LinearLayout) findViewById(R.id.purchase_quotation_lay);
        livefill_bonus_lay = (LinearLayout) findViewById(R.id.livefill_bonus_lay);
        live_lay = (LinearLayout) findViewById(R.id.live_lay);
        request_child_lay= (LinearLayout) findViewById(R.id.request_child_lay);
        normalreq_lay= (LinearLayout) findViewById(R.id.normalreq_lay);
        starreq_lay= (LinearLayout) findViewById(R.id.starreq_lay);
        superreq_lay= (LinearLayout) findViewById(R.id.superreq_lay);
        reward_wallet= (LinearLayout) findViewById(R.id.reward_wallet);
        monthly_child_lay= (LinearLayout) findViewById(R.id.monthly_child_lay);
        normalmonthly_lay= (LinearLayout) findViewById(R.id.normalmonthly_lay);
        starmonthly_lay= (LinearLayout) findViewById(R.id.starmonthly_lay);
        supermonthly_lay= (LinearLayout) findViewById(R.id.supermonthly_lay);
        my_portal_lay = (LinearLayout) findViewById(R.id.my_portal_lay);
        my_info_lay = (LinearLayout) findViewById(R.id.my_info_lay);
        my_sponcer_lay = (LinearLayout) findViewById(R.id.my_sponsor_lay);
        my_bank_lay = (LinearLayout) findViewById(R.id.my_bank_lay);
        direct_team_lay = (LinearLayout) findViewById(R.id.direct_team_lay);
        genealogy_tree_lay = (LinearLayout) findViewById(R.id.genealogy_tree_lay);
        ibv_reports_lay = (LinearLayout) findViewById(R.id.ibv_reports_lay);
        gbv_reports_lay = (LinearLayout) findViewById(R.id.gbv_reports_lay);
        bonus_reports_lay = (LinearLayout) findViewById(R.id.bonus_reports_lay);
        withdraw_reports_lay = (LinearLayout) findViewById(R.id.withdraw_reports_lay);
        LinearLayout changepswdlay=(LinearLayout)findViewById(R.id.changepswdlay);
        LinearLayout withdarw=(LinearLayout)findViewById(R.id.withdrawlay);
        LinearLayout epassbook=(LinearLayout)findViewById(R.id.passbooklay);
        FrameLayout binary_frame=(FrameLayout)findViewById(R.id.binarytitle);
        FrameLayout auto_frame=(FrameLayout)findViewById(R.id.autotitle);
        FrameLayout account_frame=(FrameLayout)findViewById(R.id.accounttitle);
        FrameLayout bonus_frame=(FrameLayout)findViewById(R.id.bonustitle);
        FrameLayout fast_frame=(FrameLayout)findViewById(R.id.fasttitle);
        FrameLayout prime_frame=(FrameLayout)findViewById(R.id.primetitle);
        FrameLayout pro_frame=(FrameLayout)findViewById(R.id.protitle);
        final LinearLayout binary_child=(LinearLayout) findViewById(R.id.binary_child_lay);
        final LinearLayout auto_child=(LinearLayout) findViewById(R.id.auto_child_lay);
        final LinearLayout account_child=(LinearLayout) findViewById(R.id.account_child_lay);
        final LinearLayout bonus_child=(LinearLayout) findViewById(R.id.bonus_child_lay);
        final LinearLayout fast_child=(LinearLayout) findViewById(R.id.fast_child_lay);
        final LinearLayout prime_child=(LinearLayout) findViewById(R.id.prime_child_lay);
        final LinearLayout pro_child=(LinearLayout) findViewById(R.id.pro_child_lay);
        final LinearLayout fast_pin_lay=(LinearLayout) findViewById(R.id.fast_pin_lay);
        final LinearLayout pro_pin_lay=(LinearLayout) findViewById(R.id.pro_pin_lay);
        final LinearLayout prime_pin_lay=(LinearLayout) findViewById(R.id.prime_pin_lay);
        final LinearLayout fast_tree_lay=(LinearLayout) findViewById(R.id.fast_tree_lay);
        final LinearLayout pro_tree_lay=(LinearLayout) findViewById(R.id.pro_tree_lay);
        final LinearLayout prime_tree_lay=(LinearLayout) findViewById(R.id.prime_tree_lay);
        final LinearLayout fast_bonus_lay=(LinearLayout) findViewById(R.id.fast_bonus_lay);
        final LinearLayout pro_bonus_lay=(LinearLayout) findViewById(R.id.pro_bonus_lay);
        final LinearLayout prime_bonus_lay=(LinearLayout) findViewById(R.id.prime_bonus_lay);
        support_layred=(LinearLayout) findViewById(R.id.support_layred);
        supports_lay=(LinearLayout) findViewById(R.id.supports_lay);
        support_history=(LinearLayout) findViewById(R.id.support_history);
        support_reduce=(LinearLayout) findViewById(R.id.support_reduce);
        bank_history=(LinearLayout) findViewById(R.id.bank_history);
        plan_history=(LinearLayout) findViewById(R.id.plan_history);
        recharge_history=(LinearLayout) findViewById(R.id.recharge_history);
        gifImageView.setGifImageResource(R.mipmap.live);

        TextView uname = (TextView) findViewById(R.id.uname);
        TextView uid = (TextView) findViewById(R.id.uid);
        prochange = (ImageButton) findViewById(R.id.prochange);
        uname.setText(utils.loadFname() + " " + utils.loadLname());
        uid.setText(utils.loadName());
        //Log.i("tabresp", utils.loadImage() + "");

        if(!utils.loadproedit().isEmpty()){
            if(utils.loadproedit().equals("0")){
                prochange.setVisibility(View.VISIBLE);
            }
            else if(utils.loadproedit().equals("1")){
                prochange.setVisibility(View.GONE);

            }
        }

        if (utils.loadImage().toString().trim().length() > 0) {
            Picasso.with(context).load(utils.loadImage()).placeholder(R.mipmap.v3).resize(200, 200).into(pimg);
           // Picasso.with(context).load(R.mipmap.v3).placeholder(R.mipmap.v3).into(pimg2);
        } else {
            Picasso.with(context).load(R.mipmap.male).into(pimg);
            //Picasso.with(context).load(R.mipmap.v3).into(pimg2);

        }
        binary_tree_list=new ArrayList<MenuItemBo>();
        //Log.i("utilsssssubuservalue","val    "+utils.loadSubusers().trim()+"");
        if(utils.loadSubusers().trim().length()>0){
            try {
                JSONArray jarr = new JSONArray(utils.loadSubusers());
                for(int i=0;i<jarr.length();i++){
                    JSONObject obj=jarr.getJSONObject(i);
                    MenuItemBo bo=new MenuItemBo();
                    bo.setMenuname(obj.getString("username"));
                    binary_tree_list.add(bo);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        cmds_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainView.this,CMD_SILVER_history.class));
            }
        });

        cmdg_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this,CMD_GOLD_history.class));

            }
        });

        pin_frame_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this,Pin_Service_Board.class));

            }
        });

        crore_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    download=(TextView) vs.findViewById(R.id.download);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Crorepati Benefits");

                    if(croreben.isEmpty()||croreben.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("crore");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(croreben).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);
                    }


                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (checkPermission()) {
                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(croreben);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Crorepathi Benefits"+String.valueOf(num)+".jpg");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = downloadManager.enqueue(request);
                                // . write your main code to execute, It will execute if the permission is already given.

                            } else {
                                requestPermission();
                            }

                        }
                    });



                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        cr_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cr_child_layred.getVisibility()==View.GONE){
                   // utils.savePreferences("crframe","1");
                    expand(cr_child_layred);
                }
                else{
                    //utils.savePreferences("crframe","0");
                    collapse(cr_child_layred);
                }

            }
        });

        welc_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(welc_child_layred.getVisibility()==View.GONE){
                    // utils.savePreferences("crframe","1");
                    expand(welc_child_layred);
                }
                else{
                    //utils.savePreferences("crframe","0");
                    collapse(welc_child_layred);
                }

            }
        });


        MenuItemAdapter itemadap=new MenuItemAdapter(context,R.layout.binary_tree_item,binary_tree_list);
        binary_tree_listvw.setAdapter(itemadap);
        Helper.getListViewSize(binary_tree_listvw);

        binary_sponsor_listvw.setAdapter(itemadap);
        Helper.getListViewSize(binary_sponsor_listvw);

        binary_pair_listvw.setAdapter(itemadap);
        Helper.getListViewSize(binary_pair_listvw);

        binary_point_listvw.setAdapter(itemadap);
        Helper.getListViewSize(binary_point_listvw);

        if(utils.loadbinary().equalsIgnoreCase("1")){
            binary_child.setVisibility(View.VISIBLE);
        }
        else{
            binary_child.setVisibility(View.GONE);
        }

        if(utils.loadauto().equalsIgnoreCase("1")){
            auto_child.setVisibility(View.VISIBLE);
        }
        else{
            auto_child.setVisibility(View.GONE);
        }

        if(utils.loadAccount().equalsIgnoreCase("1")){
            account_child.setVisibility(View.VISIBLE);
        }
        else{
            account_child.setVisibility(View.GONE);
        }

        if(utils.loadVIP().equalsIgnoreCase("1")){
            vip_lay.setVisibility(View.VISIBLE);
        }
        else{
            vip_lay.setVisibility(View.GONE);
        }

        if(utils.loadReport().equalsIgnoreCase("1")){
            reports_child_lay.setVisibility(View.VISIBLE);
        }
        else{
            reports_child_lay.setVisibility(View.GONE);
        }

        if(utils.loadbonus().equalsIgnoreCase("1")){
            bonus_child.setVisibility(View.VISIBLE);
        }
        else{
            bonus_child.setVisibility(View.GONE);
        }

        if(utils.loadprime().equalsIgnoreCase("1")){
            prime_child.setVisibility(View.VISIBLE);
        }
        else{
            prime_child.setVisibility(View.GONE);
        }

        if(utils.loadpro().equalsIgnoreCase("1")){
            pro_child.setVisibility(View.VISIBLE);
        }
        else{
            pro_child.setVisibility(View.GONE);
        }


        reward_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, Point_List.class));
            }
        });

        redeemwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, New_wallet_List.class));
            }
        });

        cr_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, New_wallet_List.class).putExtra("from","crore"));
            }
        });

        welcome_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, New_wallet_List.class).putExtra("from","welcome"));
            }
        });


        rechargewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, New_recharge_List.class));
            }
        });

        rechargewallet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this, New_recharge_List_Dup.class));
            }
        });

        if(utils.loadfast().equalsIgnoreCase("1")){
            fast_child.setVisibility(View.VISIBLE);
        }
        else{
            fast_child.setVisibility(View.GONE);
        }


        prochange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, MyInfo.class);
                intent.putExtra("frm","img");
                startActivity(intent);
            }
        });

        bonus_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bonus_child.getVisibility()==View.GONE){
                    utils.savePreferences("bonus","1");
                    expand(bonus_child);
                }
                else{
                    utils.savePreferences("bonus","0");
                    collapse(bonus_child);
                }
            }
        });

        onlinepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainView.this,Payment_Activity.class));
            }
        });

        binary_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binary_child.getVisibility()==View.GONE){
                    utils.savePreferences("binary","1");
                    expand(binary_child);
                }
                else{
                    utils.savePreferences("binary","0");
                    collapse(binary_child);
                }
            }
        });
        auto_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auto_child.getVisibility()==View.GONE){
                    utils.savePreferences("auto","1");
                    expand(auto_child);
                }
                else{
                    utils.savePreferences("auto","0");
                    collapse(auto_child);
                }
            }
        });
        reports_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reports_child_lay.getVisibility()==View.GONE){
                    utils.savePreferences("report_child","1");
                    expand(reports_child_lay);
                }
                else{
                    utils.savePreferences("report_child","0");
                    collapse(reports_child_lay);
                }
            }
        });


        grivplan_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    download=(TextView) vs.findViewById(R.id.download);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Grievance Committee");

                    if(grieve.isEmpty()||grieve.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("else");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(grieve).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);
                    }


                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkPermission()) {

                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(grieve);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Grievance Committee"+String.valueOf(num)+".jpg");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                                Long reference = downloadManager.enqueue(request);
                                // . write your main code to execute, It will execute if the permission is already given.

                            } else {
                                requestPermission();
                            }

                        }
                    });



                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });


        benefit_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    download=(TextView) vs.findViewById(R.id.download);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Star Premium Benefits");

                    if(star.isEmpty()||star.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("star");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(star).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);
                    }


                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkPermission()) {
                                //main logic or main code
                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(star);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Star Premium Benefits"+String.valueOf(num)+".jpg");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                                Long reference = downloadManager.enqueue(request);
                                // . write your main code to execute, It will execute if the permission is already given.

                            } else {
                                requestPermission();
                            }

                        }
                    });



                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        benefit_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    download=(TextView) vs.findViewById(R.id.download);

                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Super Premium Benefits");

                    if(supers.isEmpty()||supers.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("super");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(supers).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);

                    }
                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkPermission()) {
                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(supers);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Super Premium Benefits"+String.valueOf(num)+".jpg");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = downloadManager.enqueue(request);
                            } else {
                                requestPermission();
                            }

                        }
                    });

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        benefit_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    download=(TextView) vs.findViewById(R.id.download);

                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Super Salary Benefits");

                    if(salary.isEmpty()||salary.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("salary");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(salary).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);

                    }
                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkPermission()) {
                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(salary);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Super Salary Benefits"+String.valueOf(num)+".jpg");;
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = downloadManager.enqueue(request);
                            } else {
                                requestPermission();
                            }

                        }
                    });

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        benefitplan_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    download=(TextView) vs.findViewById(R.id.download);

                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText(" Lucky Draw Benefits");

                    if(coupon.isEmpty()||coupon.equals("null")){
                        GetInfoTask_New task=new GetInfoTask_New();
                        task.execute("coupon");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(coupon).placeholder(R.mipmap.loading).into(laterbut);
                        download.setVisibility(View.VISIBLE);
                    }
                    download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkPermission()) {
                                Random inte=new Random();
                                int num=inte.nextInt(1000);
                                downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                Uri uri = Uri.parse(coupon);
                                DownloadManager.Request request = new DownloadManager.Request(uri).setDestinationInExternalPublicDir("/V3 Online TV/Benefits", "Lucky Draw Benefits"+String.valueOf(num)+".jpg");;
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long reference = downloadManager.enqueue(request);
                            } else {
                                requestPermission();
                            }

                        }
                    });

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        benefit_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(benefit_layred.getVisibility()==View.GONE){
                    expand(benefit_layred);
                }
                else{
                    collapse(benefit_layred);

                }
            }
        });




        terms_frame.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent k=new Intent(MainView.this,Termsconditions.class);
            startActivity(k);
         }
        });

        cr_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent k=new Intent(MainView.this,Termsconditions.class);
                k.putExtra("term","crore");
                startActivity(k);
            }
        });

        welcome_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent k=new Intent(MainView.this,Termsconditions.class);
                k.putExtra("term","welcome");
                startActivity(k);
            }
        });



        support_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url =utils.loadsupport();
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setPackage("com.android.chrome");
                    startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    // Chrome is not installed
                    toast("Install chrome for better experience");

                    Intent intt = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.android.chrome"));
                    startActivity(intt);

                }


            }
        });

        cr_superbuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(MainView.this,Super_business_Man_history.class);
                startActivity(k);


            }
        });

        cr_superlead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(MainView.this,Super_Teamleader_history.class);
                startActivity(k);


            }
        });




        marketing_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Business plan / Terms Clarifications");

                    if(marketing.isEmpty()||marketing.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("Marketing");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(marketing).placeholder(R.mipmap.loading).into(laterbut);

                    }


                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        marketing_frame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Verify Request");

                    if(marketing1.isEmpty()||marketing1.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("Marketing1");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(marketing1).placeholder(R.mipmap.loading).into(laterbut);

                    }


                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });
                marketing_frame2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final Dialog update = new Dialog(MainView.this);
                            update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            update.getWindow().setBackgroundDrawable(
                                    new ColorDrawable(android.graphics.Color.WHITE));
                            View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                            TextView updatebut=(TextView) vs.findViewById(R.id.update);
                            laterbut=(ImageView) vs.findViewById(R.id.img);
                            TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                            update.setContentView(vs);
                            Window window = update.getWindow();
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            update.setCancelable(true);
                            update.show();
                            titlename.setText("Monthly Report guidance");

                            if(marketing2.isEmpty()||marketing2.equals("null")){
                                GetInfoTask task=new GetInfoTask();
                                task.execute("Marketing2");
                            }
                            else
                            {
                                Picasso.with(MainView.this).load(marketing2).placeholder(R.mipmap.loading).into(laterbut);

                            }


                            // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                            updatebut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // clickupdate="true";
                                    update.dismiss();

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
                });
        marketing_frame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                    laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Monthly Report status");

                    if(marketing3.isEmpty()||marketing3.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("Marketing3");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(marketing3).placeholder(R.mipmap.loading).into(laterbut);

                    }


                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });
                marketing_frame4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final Dialog update = new Dialog(MainView.this);
                            update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            update.getWindow().setBackgroundDrawable(
                                    new ColorDrawable(android.graphics.Color.WHITE));
                            View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                            TextView updatebut=(TextView) vs.findViewById(R.id.update);
                            laterbut=(ImageView) vs.findViewById(R.id.img);
                            TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                            update.setContentView(vs);
                            Window window = update.getWindow();
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            update.setCancelable(true);
                            update.show();
                            titlename.setText("Reward points related");

                            if(marketing4.isEmpty()||marketing4.equals("null")){
                                GetInfoTask task=new GetInfoTask();
                                task.execute("Marketing4");
                            }
                            else
                            {
                                Picasso.with(MainView.this).load(marketing4).placeholder(R.mipmap.loading).into(laterbut);

                            }


                            // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                            updatebut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // clickupdate="true";
                                    update.dismiss();

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
                });

        v3pinlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.pin_popup,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.textView81);
                    // laterbut=(ImageView) vs.findViewById(R.id.img);
                    //TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                     otp_edit_box1=(EditText) vs.findViewById(R.id.otp_edit_box1);
                      otp_edit_box2=(EditText) vs.findViewById(R.id.otp_edit_box2);
                      otp_edit_box3=(EditText) vs.findViewById(R.id.otp_edit_box3);
                      otp_edit_box4=(EditText) vs.findViewById(R.id.otp_edit_box4);
                     button14=(Button) vs.findViewById(R.id.button14);
                    editTextmob=(EditText) vs.findViewById(R.id.editTextmob);
                    editTextmob1=(EditText) vs.findViewById(R.id.editTextmob1);
                  //  editTextotp=(EditText) vs.findViewById(R.id.editTextotp);
                    //getotp=(Button) vs.findViewById(R.id.button15);
                    progressBar6=(ProgressBar) vs.findViewById(R.id.progressBar6);
                    verifyotp=(Button) vs.findViewById(R.id.button16);
                    moblays=(LinearLayout) vs.findViewById(R.id.moblay);
                    otplay=(LinearLayout) vs.findViewById(R.id.otplay);


                    if(!utils.loadpinchange().isEmpty()){
                        otp_edit_box1.setEnabled(false);
                        otp_edit_box2.setEnabled(false);
                        otp_edit_box3.setEnabled(false);
                        otp_edit_box4.setEnabled(false);
                        button14.setVisibility(View.INVISIBLE);
                    }

                  //  editTextmob.setText(utils.loadmob());

                    try {
                        if (!utils.loadmpin().isEmpty()) {
                            char one = utils.loadmpin().charAt(0);
                            char two = utils.loadmpin().charAt(1);
                            char three = utils.loadmpin().charAt(2);
                            char four = utils.loadmpin().charAt(3);
                            otp_edit_box1.setText(String.valueOf(one));
                            otp_edit_box2.setText(String.valueOf(two));
                            otp_edit_box3.setText(String.valueOf(three));
                            otp_edit_box4.setText(String.valueOf(four));
                        } else {

                        }
                    }
                    catch (Exception e){

                    }

                    if(utils.loadmpin_first().isEmpty()){
                        button14.setVisibility(View.VISIBLE);
                     /*   otp_edit_box1.setEnabled(true);
                        otp_edit_box2.setEnabled(true);
                        otp_edit_box3.setEnabled(true);
                        otp_edit_box4.setEnabled(true);*/
                        otp_edit_box1.setFocusable(true);
                        otp_edit_box2.setFocusable(true);
                        otp_edit_box3.setFocusable(true);
                        otp_edit_box4.setFocusable(true);

                    }
                    else{
                        button14.setVisibility(View.GONE);
                        otp_edit_box1.setFocusable(false);
                        otp_edit_box2.setFocusable(false);
                        otp_edit_box3.setFocusable(false);
                        otp_edit_box4.setFocusable(false);

                    }

                    button14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("click","cli");
                            if(!otp_edit_box1.getText().toString().isEmpty()&&
                                !otp_edit_box2.getText().toString().isEmpty()&&
                                    !otp_edit_box3.getText().toString().isEmpty()&&
                                    !otp_edit_box4.getText().toString().isEmpty()) {
                                moblays.setVisibility(View.VISIBLE);
                                otplay.setVisibility(View.VISIBLE);
                                button14.setVisibility(View.INVISIBLE);

                            }
                            else{
                                toast("Please enter valid pin");
                            }
                        }
                    });

                 /*   getotp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!editTextmob.getText().toString().isEmpty()&&editTextmob.getText().toString().length()==10) {
                                GetInfoTask_otp tasks = new GetInfoTask_otp();
                                tasks.execute(editTextmob.getText().toString().trim());
                            }
                            else{
                                if(editTextmob.getText().toString().isEmpty()){
                                    toast("Enter Mobile number");

                                }
                                else if(editTextmob.getText().toString().length()<10){
                                    toast("Enter Valid Mobile Number");

                                }
                            }
                        }
                    });*/


                    verifyotp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!editTextmob.getText().toString().isEmpty()&&(!editTextmob1.getText().toString().isEmpty())) {
                                if (editTextmob.getText().toString().equals(editTextmob1.getText().toString())) {

                                    if(utils.loadmob().equals(editTextmob1.getText().toString())) {
                                        ChangepinTask task = new ChangepinTask();
                                        task.execute();
                                    }
                                    else{
                                        toast("Please Enter Registered Mobile Number");
                                        editTextmob.setText(null);
                                        editTextmob1.setText(null);
                                    }
                                } else {
                                    toast("Mobile number isn't match.");

                                }
                            }
                            else{
                                toast("Please fill all fields.");
                                if(editTextmob.getText().length()==0){
                                    editTextmob.setError("Required Field");
                                }
                                if(editTextmob1.getText().length()==0){
                                    editTextmob1.setError("Required Field");

                                }

                            }
                        }
                    });

                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    otp_edit_box1.addTextChangedListener(new GenericTextWatcher(otp_edit_box1));
                    otp_edit_box2.addTextChangedListener(new GenericTextWatcher(otp_edit_box2));
                    otp_edit_box3.addTextChangedListener(new GenericTextWatcher(otp_edit_box3));
                    otp_edit_box4.addTextChangedListener(new GenericTextWatcher(otp_edit_box4));

                    if(utils.loadmpin().isEmpty()) {
                        otp_edit_box1.setEnabled(true);
                        otp_edit_box2.setEnabled(true);
                        otp_edit_box3.setEnabled(true);
                        otp_edit_box4.setEnabled(true);
                        otp_edit_box1.setFocusable(true);
                        otp_edit_box2.setFocusable(true);
                        otp_edit_box3.setFocusable(true);
                        otp_edit_box4.setFocusable(true);
                        button14.setVisibility(View.VISIBLE);
                    }
                    else if(!utils.loadmpin().isEmpty()){
                        otp_edit_box1.setEnabled(false);
                        otp_edit_box2.setEnabled(false);
                        otp_edit_box3.setEnabled(false);
                        otp_edit_box4.setEnabled(false);
                        button14.setVisibility(View.INVISIBLE);

                    }




                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

                        }
                    });
                    /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
                }catch(Exception e){
                    Log.e("er",e.toString());

                }
            }
        });


        supports_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Blueway - Greenway Language - Country");

                    if(langway.isEmpty()||langway.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("langway");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(langway).placeholder(R.mipmap.loading).into(laterbut);

                    }

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });


        support_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("IT Support / Complaint");
                    if(itsupport.isEmpty()||itsupport.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("itsupport");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(itsupport).placeholder(R.mipmap.loading).into(laterbut);

                    }
                    //Picasso.with(MainView.this).load(Appconstants.itsupport).placeholder(R.mipmap.loading).into(laterbut);

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });


        support_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Profile Corrections");

                    if(corection.isEmpty()||corection.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("profilecorection");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(corection).placeholder(R.mipmap.loading).into(laterbut);

                    }
                   // Picasso.with(MainView.this).load(Appconstants.corection).placeholder(R.mipmap.loading).into(laterbut);

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });


        bank_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Bank Account Details");

                    if(bankcorection.isEmpty()||bankcorection.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("bankcorection");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(bankcorection).placeholder(R.mipmap.loading).into(laterbut);

                    }

                    //Picasso.with(MainView.this).load(Appconstants.pindetails).placeholder(R.mipmap.loading).into(laterbut);

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });




        plan_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Super premium plan upgrade");

                    if(upgradepin.isEmpty()||upgradepin.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("upgradepin");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(upgradepin).placeholder(R.mipmap.loading).into(laterbut);

                    }
                   // Picasso.with(MainView.this).load(Appconstants.upgradepin).placeholder(R.mipmap.loading).into(laterbut);

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        recharge_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final Dialog update = new Dialog(MainView.this);
                    update.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    update.getWindow().setBackgroundDrawable(
                            new ColorDrawable(Color.WHITE));
                    View vs=getLayoutInflater().inflate(R.layout.app_image_dialog,null);
                    TextView updatebut=(TextView) vs.findViewById(R.id.update);
                     laterbut=(ImageView) vs.findViewById(R.id.img);
                    TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                    update.setContentView(vs);
                    Window window = update.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    update.setCancelable(true);
                    update.show();
                    titlename.setText("Reward fund / Recharge fund/ Mobile fund");

                    if(recharge.isEmpty()||recharge.equals("null")){
                        GetInfoTask task=new GetInfoTask();
                        task.execute("recharge");
                    }
                    else
                    {
                        Picasso.with(MainView.this).load(recharge).placeholder(R.mipmap.loading).into(laterbut);

                    }

                    //Picasso.with(MainView.this).load(Appconstants.recharge).placeholder(R.mipmap.loading).into(laterbut);

                    // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                    updatebut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // clickupdate="true";
                            update.dismiss();

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
        });

        vip_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vip_lay.getVisibility()==View.GONE){
                    utils.savePreferences("vip","1");
                    expand(vip_lay);
                }
                else{
                    utils.savePreferences("vip","0");
                    collapse(vip_lay);
                }
            }
        });

        coupon_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lay.closeDrawers();
                if(coupon_child_lay.getVisibility()==View.GONE){
                    coupon_child_lay.setVisibility(View.VISIBLE);
                }
                else{
                    coupon_child_lay.setVisibility(View.GONE);

                }

            }
        });

        coup_monthly_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,coupon_list.class));

            }
        });
                coupmonthly_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lay.closeDrawers();
                        startActivity(new Intent(MainView.this,Updatehist_List.class));

                    }
                });

        rewardred_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(account_child_layred.getVisibility()==View.GONE){
                    utils.savePreferences("reward_child","1");
                    expand(account_child_layred);
                }
                else{
                    utils.savePreferences("reward_child","0");
                    collapse(account_child_layred);
                }


            }


        });

        compframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();

                startActivity(new Intent(MainView.this,Complaint.class));
            }
        });

        reward_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Reward_reduce.class);
                startActivity(intent);
            }
        });

        saver_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, star_perf_history.class);
                startActivity(intent);
            }
        });

        online_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Online_training.class);
                startActivity(intent);
            }
        });


        welcome_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Welcome_report.class);
                startActivity(intent);
            }
        });


        datereq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(request_child_lay.getVisibility()==View.GONE){
                    expand(request_child_lay);
                }
                else{
                    collapse(request_child_lay);
                }


            }
        });

        normalreq_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Daterequest_Activity.class);
                intent.putExtra("frm","normal");
                startActivity(intent);
            }
        });
        starreq_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Daterequest_Activity.class);
                intent.putExtra("frm","star");

                startActivity(intent);
            }
        });
        superreq_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Daterequest_Activity.class);
                intent.putExtra("frm","super");

                startActivity(intent);
            }
        });

        normalmonthly_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Super_Salry_history.class);
                intent.putExtra("frm","normal");
                startActivity(intent);
            }
        });
        starmonthly_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Super_Salry_history.class);
                intent.putExtra("frm","star");
                startActivity(intent);
            }
        });

        supermonthly_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Super_Salry_history.class);
                intent.putExtra("frm","super");
                startActivity(intent);
            }
        });






        supersalry_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(monthly_child_lay.getVisibility()==View.GONE){
                    expand(monthly_child_lay);
                }
                else{
                    collapse(monthly_child_lay);
                }


            }
        });

        monthreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Monthly_report.class);
                startActivity(intent);
            }
        });

        reward_points_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Rewardpoints.class);
                startActivity(intent);
            }
        });

        alreadyvip_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Vip_ClientAdd.class);
                startActivity(intent);
            }
        });

        newvip_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Vip_new_activity.class);
                startActivity(intent);
            }
        });

        reward_hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, Reward_history.class);
                startActivity(intent);
            }
        });


        chngpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay.closeDrawers();
                Intent intent = new Intent(MainView.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        account_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_child.getVisibility()==View.GONE){
                    utils.savePreferences("account","1");
                    expand(account_child);
                }
                else{
                    utils.savePreferences("account","0");
                    collapse(account_child);
                }
            }
        });

        prime_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prime_child.getVisibility()==View.GONE){
                    utils.savePreferences("prime","1");
                    expand(prime_child);
                }
                else{
                    utils.savePreferences("prime","0");
                    collapse(prime_child);
                }
            }
        });

        pro_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pro_child.getVisibility()==View.GONE){
                    utils.savePreferences("pro","1");
                    expand(pro_child);
                }
                else{
                    utils.savePreferences("pro","0");
                    collapse(pro_child);
                }
            }
        });

        fast_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fast_child.getVisibility()==View.GONE){
                    utils.savePreferences("fast","1");
                    expand(fast_child);
                }
                else{
                    utils.savePreferences("fast","0");
                    collapse(fast_child);
                }
            }
        });


        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,Wallet.class));
            }
        });

        cr_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,Wallet.class).putExtra("from","crore"));
            }
        });

        welcome_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,Wallet.class).putExtra("from","welcome"));
            }
        });


        pin_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeSuperPinActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",1);
                intent.putExtras(bun);
                startActivity(intent);
                //startActivity(new Intent(MainView.this,PrimeSuperPinActivity.class));
            }
        });


        commission_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,Commission.class));
            }
        });

        try {
            version.setText("Version "+BuildConfig.VERSION_NAME.toString());
        }
        catch(Exception e){

        }

        stock_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,StockList.class));
            }
        });


        tree_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,OptionTree.class));
            }
        });


        over_all_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,OverAllBonusActivity.class));
            }
        });

        auto_withdraw_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,AutoWithdrawActivity.class));
            }
        });

        changepswdlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,ChangePassword.class));
            }
        });

        binary_pin_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,BinaryPinActivity.class));
            }
        });

       /* withdarw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,WithdrawActivity.class));
            }
        });*/

        epassbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                startActivity(new Intent(MainView.this,PassbookActivity.class));
            }
        });

        fast_pin_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeSuperPinActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",1);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        prime_pin_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeSuperPinActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",2);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        pro_pin_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeSuperPinActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",3);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        fast_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",1);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        prime_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",2);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        pro_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",3);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        fast_tree_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeTreeActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",1);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
        prime_tree_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeTreeActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",2);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
        pro_tree_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PrimeTreeActivity.class);
                Bundle bun=new Bundle();
                bun.putInt("flag",3);
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        binary_tree_listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lay.closeDrawers();
                Intent intent=new Intent(context,BinaryTreeActivity.class);
                Bundle bun=new Bundle();
                bun.putString("name",binary_tree_list.get(position).getMenuname());
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        binary_sponsor_listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lay.closeDrawers();
                Intent intent=new Intent(context,SponsorBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putString("name",binary_tree_list.get(position).getMenuname());
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        prime_fill_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,FillBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putString("value","1");
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        super_fill_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay.closeDrawers();
                Intent intent=new Intent(context,FillBonusActivity.class);
                Bundle bun=new Bundle();
                bun.putString("value","2");
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        binary_pair_listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lay.closeDrawers();
                Intent intent=new Intent(context,PairMatchBonus.class);
                Bundle bun=new Bundle();
                bun.putString("name",binary_tree_list.get(position).getMenuname());
                intent.putExtras(bun);
                startActivity(intent);
            }
        });

        binary_point_listvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lay.closeDrawers();
                Intent intent=new Intent(context,BinaryPointsActivity.class);
                Bundle bun=new Bundle();
                bun.putString("name",binary_tree_list.get(position).getMenuname());
                intent.putExtras(bun);
                startActivity(intent);
            }
        });
        autofill_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autofill_check == false) {
                    auto_fill_lay.setVisibility(View.VISIBLE);
                    autofill_img.setImageResource(R.mipmap.up_arrow);
                    autofill_check = true;
                } else {
                    auto_fill_lay.setVisibility(View.GONE);
                    autofill_img.setImageResource(R.mipmap.down_arrow);
                    autofill_check = false;
                }

            }
        });

        binary_tree_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binary_tree_item_lay.getVisibility()==View.GONE) {
                    binary_tree_item_lay.setVisibility(View.VISIBLE);
                    binary_tree_arrow.setImageResource(R.mipmap.up_arrow);

                } else {
                    binary_tree_item_lay.setVisibility(View.GONE);
                    binary_tree_arrow.setImageResource(R.mipmap.down_arrow);

                }

            }
        });

        binary_sponsor_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binary_sponsor_item_lay.getVisibility()==View.GONE) {
                    binary_sponsor_item_lay.setVisibility(View.VISIBLE);
                    binary_sponsor_arrow.setImageResource(R.mipmap.up_arrow);

                } else {
                    binary_sponsor_item_lay.setVisibility(View.GONE);
                    binary_sponsor_arrow.setImageResource(R.mipmap.down_arrow);

                }

            }
        });

        binary_pair_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binary_pair_item_lay.getVisibility()==View.GONE) {
                    binary_pair_item_lay.setVisibility(View.VISIBLE);
                    binary_pair_arrow.setImageResource(R.mipmap.up_arrow);

                } else {
                    binary_pair_item_lay.setVisibility(View.GONE);
                    binary_pair_arrow.setImageResource(R.mipmap.down_arrow);

                }

            }
        });

        binary_point_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binary_point_item_lay.getVisibility()==View.GONE) {
                    binary_point_item_lay.setVisibility(View.VISIBLE);
                    binary_point_arrow.setImageResource(R.mipmap.up_arrow);

                } else {
                    binary_point_item_lay.setVisibility(View.GONE);
                    binary_point_arrow.setImageResource(R.mipmap.down_arrow);

                }

            }
        });


        profile_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (profile_check == false) {
                    profile_lay.setVisibility(View.VISIBLE);
                    profile_img.setImageResource(R.mipmap.up_arrow);
                    profile_check = true;
                } else {
                    profile_lay.setVisibility(View.GONE);
                    profile_img.setImageResource(R.mipmap.down_arrow);
                    profile_check = false;
                }

            }
        });

        logout_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        logout_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(MainView.this);
                alert.setTitle("Logout?");
                alert.setMessage("Are you sure want to logout?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        utils.savePreferences("name", "");
                        utils.savePreferences("image", "");
                        utils.savePreferences("doj", "");
                        utils.savePreferences("designation", "");
                        utils.savePreferences("ibv", "");
                        utils.savePreferences("gbv", "");
                        utils.savePreferences("commition", "");
                        utils.savePreferences("jsonobj", "");
                        utils.savePreferences("mpin", "");
                        utils.savePreferences("mpin_first", "");
                        utils.savePreferences("notitime", "");
                        utils.savePreferences("noti", "");


                        Toast.makeText(MainView.this, "Logout Successfully.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainView.this, Login.class));
                    }
                });


                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog pop=alert.create();
                pop.show();

            }
        });

        tree_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tree_check == false) {
                    tree_lay.setVisibility(View.VISIBLE);
                    tree_img.setImageResource(R.mipmap.up_arrow);
                    tree_check = true;
                } else {
                    tree_lay.setVisibility(View.GONE);
                    tree_img.setImageResource(R.mipmap.down_arrow);
                    tree_check = false;
                }


            }
        });

        reports_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reports_check == false) {
                    reports_lay.setVisibility(View.VISIBLE);
                    reports_img.setImageResource(R.mipmap.up_arrow);
                    reports_check = true;
                } else {
                    reports_lay.setVisibility(View.GONE);
                    reports_img.setImageResource(R.mipmap.down_arrow);
                    reports_check = false;
                }


            }
        });

        more_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (more_check == false) {
                    more_lay.setVisibility(View.VISIBLE);
                    more_arrow.setImageResource(R.mipmap.up_arrow);
                    more_check = true;
                } else {
                    more_lay.setVisibility(View.GONE);
                    more_arrow.setImageResource(R.mipmap.down_arrow);
                    more_check = false;
                }


            }
        });

        homebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("HomePage")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, HomePage.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        purchase_detail_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("PurchaseDetails")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, PurchaseDetails.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        purchase_quotation_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("PurchaseQuotation")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, PurchaseQuotation.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        livefill_bonus_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("LiveFillBonus")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, LiveFillBonus.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        live_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().equalsIgnoreCase("Live")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, Live.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        my_portal_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("MyPortal")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, MyPortal.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        my_info_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("MyInfo")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, MyInfo.class);
                    intent.putExtra("frm","info");
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        my_bank_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("BankDetails")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, BankDetails.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        my_sponcer_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();

                if (getIntent().toString().contains("MySponsor")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, MySponsor.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
            }
        });

        ibv_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("IbvReports")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, IbvReports.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        gbv_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("GbvReports")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, GbvReports.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        bonus_reports_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("BonusReports")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, BonusReports.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        genealogy_tree_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainView.this, GeneologyTree.class);
                startActivity(intent);
                lay.closeDrawers();
            }
        });

        withdraw_reports_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("WithdrawReports")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, WithdrawReports.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        direct_team_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("DirectTeam")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, DirectTeam.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        stock_list_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("StockList")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, StockList.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        sales_point_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("AddCustomerActivity")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, AddCustomerActivity.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        admin_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainView.this, Admin_reports.class);
                startActivity(intent);
                lay.closeDrawers();

                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        saleslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("Sales_report")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, Sales_report.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        purchaselay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().toString().contains("PurchaseReport")) {
                    lay.closeDrawers();
                } else {
                    Intent intent = new Intent(MainView.this, PurchaseReport.class);
                    startActivity(intent);
                    lay.closeDrawers();
                }
                //Toast.makeText(context,"Coming soon.",Toast.LENGTH_SHORT).show();
            }
        });

        about_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lay.closeDrawers();
                About();
            }
        });

        contact_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lay.closeDrawers();
                Contact();

            }
        });

        feedback_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","v3onlinetvhelp@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                //emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        share_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "V3 Health Zone\n\n https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        rating_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

    }

    public class GenericTextWatcher implements TextWatcher
    {
        private View view;
        private GenericTextWatcher(View view)
        {
            this.view = view;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String text = editable.toString();
            switch(view.getId())
            {

                case R.id.otp_edit_box1:
                    if(text.length()==1)
                        otp_edit_box2.requestFocus();
                    break;
                case R.id.otp_edit_box2:
                    if(text.length()==1)
                        otp_edit_box3.requestFocus();
                    else if(text.length()==0)
                        otp_edit_box1.requestFocus();
                    break;
                case R.id.otp_edit_box3:
                    if(text.length()==1)
                        otp_edit_box4.requestFocus();
                    else if(text.length()==0)
                        otp_edit_box2.requestFocus();
                    break;
                case R.id.otp_edit_box4:
                    if(text.length()==0)
                        otp_edit_box3.requestFocus();
                    break;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
        }
    }


    private void About() {

        about = new Dialog(MainView.this);
        about.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.activity_about_us, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        about.setContentView(popup);
        about.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        about.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        about.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1)-80;
        int height = (int) (displaymetrics.heightPixels * 1)-80;
        about.getWindow().setLayout(width, height);

        about.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                about.dismiss();
            }
        });
    }

    private void Contact() {

        about = new Dialog(MainView.this);
        about.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.activity_contact_us, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        phone_lay = (LinearLayout) popup.findViewById(R.id.phone_lay);
        email_lay = (LinearLayout) popup.findViewById(R.id.email_lay);

        title.setText("Contact Us");
        about.setContentView(popup);
        about.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        about.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        about.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1)-80;
        int height = (int) (displaymetrics.heightPixels * 1)-80;
        about.getWindow().setLayout(width, height);

        about.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                about.dismiss();
            }
        });

        /*phone_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:7708825000"));

                    if (ActivityCompat.checkSelfPermission(MainView.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                }
                startActivity(callIntent);
            }
        });*/

        email_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","support@virtuewin.com", null));
                // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });


    }





    private class GetInfoTask extends AsyncTask<String, Void, String> {
        String value="";
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
                value=param[0];
                //   Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.support, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {

            try {

                if (resp != null) {
                    JSONObject json = new JSONObject(resp);

                    if (json.getString("Status").equals("Success")) {
                        JSONObject jarr = json.getJSONObject("Response");

                        try {
                            itsupport=jarr.getString("url2");
                            langway=jarr.getString("url1");
                            upgradepin=jarr.getString("url5");
                            recharge=jarr.getString("url6");
                            marketing=jarr.getString("url");
                            corection=jarr.getString("url3");
                            bankcorection=jarr.getString("url4");
                            marketing1=jarr.getString("extra1");
                            marketing2=jarr.getString("extra2");
                            marketing3=jarr.getString("extra3");
                            marketing4=jarr.getString("extra4");

                            if(value.equals("Marketing")){
                                Picasso.with(MainView.this).load(marketing).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("Marketing1")){
                                Picasso.with(MainView.this).load(marketing1).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("Marketing2")){
                                Picasso.with(MainView.this).load(marketing2).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("Marketing3")){
                                Picasso.with(MainView.this).load(marketing3).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("Marketing4")){
                                Picasso.with(MainView.this).load(marketing4).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("langway")){
                                Picasso.with(MainView.this).load(langway).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("itsupport")){
                                Picasso.with(MainView.this).load(itsupport).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("corection")){
                                Picasso.with(MainView.this).load(corection).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("bankcorection")){
                                Picasso.with(MainView.this).load(bankcorection).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("upgradepin")){
                                Picasso.with(MainView.this).load(upgradepin).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("recharge")){
                                Picasso.with(MainView.this).load(recharge).placeholder(R.mipmap.loading).into(laterbut);
                            }

                        }
                        catch (Exception e){

                        }
                    }


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

    private class GetInfoTask_otp extends AsyncTask<String, Void, String> {
        String value="";
        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //progbar.show();
            //Log.i("GetInfoTask", "started");]
            getotp.setVisibility(View.GONE);
            progressBar6.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("mobile", param[0]);
                jobj.put("type", "OTP");

                //   Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                Log.i("check Input", Appconstants.VIP_API + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {

            try {

                if (resp != null) {
                    JSONArray json = new JSONArray(resp);

                    if (json.getJSONObject(0).getString("Status").equals("Success")) {
                        String jarr = json.getJSONObject(0).getString("OTP");
                        Respotp=jarr;
                        System.out.println("respotp"+Respotp);
                        toast("OTP has been sent.");
                        getotp.setText("GET OTP");
                        button14.setVisibility(View.GONE);
                        moblays.setVisibility(View.GONE);
                        otplay.setVisibility(View.VISIBLE);
                        try {

                        }
                        catch (Exception e){

                        }
                    }


                    else {

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



    private class GetInfoTask_New extends AsyncTask<String, Void, String> {
        String value="";
        @Override
        protected void onPreExecute() {

        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                value=param[0];
                  Log.i("HomePage Input", Appconstants.GET_MY_AMOUNT + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.benefit,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {

            try {

                if (resp != null) {
                    JSONObject json = new JSONObject(resp);

                    if (json.getString("Status").equals("Success")) {
                        JSONObject jarr = json.getJSONObject("Response");

                        try {

                            star=jarr.getString("star");
                            supers=jarr.getString("super");
                            salary=jarr.getString("salary");
                             coupon=jarr.getString("coupon");
                             grieve=jarr.getString("grievance");
                            croreben=jarr.getString("corepathi");
                            download.setVisibility(View.VISIBLE);

                            if(value.equals("star")){
                                Picasso.with(MainView.this).load(star).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("super")){
                                Picasso.with(MainView.this).load(supers).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("salary")){
                                Picasso.with(MainView.this).load(salary).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("coupon")){
                                Picasso.with(MainView.this).load(coupon).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("else")){
                                Picasso.with(MainView.this).load(grieve).placeholder(R.mipmap.loading).into(laterbut);
                            }
                            if(value.equals("crore")){
                                Picasso.with(MainView.this).load(croreben).placeholder(R.mipmap.loading).into(laterbut);
                            }


                        }
                        catch (Exception e){

                        }
                    }


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



    private class ChangepinTask extends AsyncTask<String, Void, String> {
        String value="";
        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            loadprogress();
            progbar.show();
            //Log.i("GetInfoTask", "started");
        }

        @SuppressLint("WrongThread")
        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {

                String value="";
                value=otp_edit_box1.getText().toString()+
                        otp_edit_box2.getText().toString()+
                        otp_edit_box3.getText().toString()+
                        otp_edit_box4.getText().toString();
                String deviceId = Settings.Secure.getString(MainView.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                jobj.put("mpin", utils.loadmpin());
                jobj.put("device_id", deviceId);
                jobj.put("newmpin", String.valueOf(value));
                jobj.put("mobile", editTextmob1.getText().toString());
                jobj.put("type", "Change");
                //  value=param[0];
                Log.i("HomePage Input", Appconstants.changepin + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.changepin, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.toString());
            }
            return result;
        }

        protected void onPostExecute(String resp) {

            try {

                if (resp != null) {
                    JSONArray jsonarr = new JSONArray(resp);
                    JSONObject json = jsonarr.getJSONObject(0);
                    if (json.getString("Status").equals("Success")) {
                        //JSONObject jarr = json.getJSONObject("Response");
                        progbar.dismiss();
                        try {
                            String value="";
                            value=otp_edit_box1.getText().toString()+
                                    otp_edit_box2.getText().toString()+
                                    otp_edit_box3.getText().toString()+
                                    otp_edit_box4.getText().toString();
                            utils.savePreferences("mpin",value);
                            utils.savePreferences("mpin_first","first");
                            button14.setVisibility(View.GONE);
                            otplay.setVisibility(View.GONE);
                            moblays.setVisibility(View.GONE);
                            toast("Pin Changed.");
                            utils.savePreferences("pin_change","changed");

                            if(!utils.loadmpin().isEmpty()){
                                otp_edit_box1.setEnabled(false);
                                otp_edit_box2.setEnabled(false);
                                otp_edit_box3.setEnabled(false);
                                 otp_edit_box4.setEnabled(false);
                                otp_edit_box1.setFocusable(false);
                                otp_edit_box2.setFocusable(false);
                                otp_edit_box3.setFocusable(false);
                                otp_edit_box4.setFocusable(false);
                            }

                        }
                        catch (Exception e){
                            progbar.dismiss();

                        }
                    }
                    else{
                        progbar.dismiss();
                        toast(json.getString("Response"));
                    }


                } else {
                    //retry.show();
                    progbar.dismiss();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("err",e.toString());
                progbar.dismiss();

                //retry.show();
            }
        }
    }


    public void toast(String msg)
    {
        Toast toast=Toast.makeText(MainView.this,msg,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }


    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private boolean checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED)) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainView.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}


