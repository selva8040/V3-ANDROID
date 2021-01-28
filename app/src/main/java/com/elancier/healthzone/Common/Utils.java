package com.elancier.healthzone.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;

public class Utils {

    Context _context;
    SharedPreferences sharedPreferences;

    public Utils(Context context) {
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void savePreferences(String string, String id) {
        // TODO Auto-generated method stub
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(string, id);
        editor.commit();
    }


    public String loadTarget() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("target", "");
        return data;
    }

    public String loadSign() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sign", "");
        return data;
    }

    public String loadbasewallet() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("base_wallet", "");
        return data;
    }

    public String loadfirstversion() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("clearversion", "");
        return data;
    }

    public String loadfirstversion1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("clearversion1", "");
        return data;
    }

    public String loadfirstversion2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("clearversion2", "");
        return data;
    }


    public String loadAchieve() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("achieve", "");
        return data;
    }

    public String pinName() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pinName", "");
        return data;
    }

    public String loadpinchange() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pin_change", "");
        return data;
    }

    public String loadproedit() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("proedit", "");
        return data;
    }

    public String loadBalance() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("balance", "");
        return data;
    }

    public String loadlang() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("language", "");
        return data;
    }

    public String loadseenuser() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("seenuser", "");
        return data;
    }

    public String loadseenvideo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("seenvideo", "");
        return data;
    }

    public String front_url(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("front_url", "");
        return data;
    }

    public String front_type(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("front_type", "");
        return data;

    }

    public String front_seconds(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("front_seconds", "");
        return data;
    }

    public String front_linkUrl(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("front_linkUrl", "");
        return data;
     }


    public String middle_url(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("middle_url", "");
        return data;
    }

    public String middle_type(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("middle_type", "");
        return data;

    }

    public String middle_seconds(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("middle_seconds", "");
        return data;
    }

    public String middlelinkUrl(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("middle_linkUrl", "");
        return data;
    }

    public String back_url(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("back_url", "");
        return data;
    }

    public String back_type(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("back_type", "");
        return data;

    }
   public String  back_seconds(){
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
       String data = sharedPreferences.getString("back_seconds", "");
       return data;
   }

   public String  back_linkUrl(){
       SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
       String data = sharedPreferences.getString("back_linkUrl", "");
       return data;

    }


    public String loadnotif() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("noti", "");
        return data;
    }
    public String loadsupport() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("support", "");
        return data;
    }

    public String loadnotiftime() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("notitime", "");
        return data;
    }

    public String loadjsonval() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("jsonobj", "");
        return data;
    }
    public String loadversion() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("version", "");
        return data;
    }

    public String loadmpin() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
            String data = sharedPreferences.getString("mpin", "");
        return data;
    }
    public String loadmpin_first() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("mpin_first", "");
        return data;
    }

    public String loadcount() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("countvalue", "");
        return data;
    }

    public String nr_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname", "");
        return data;
    }
    public String gd_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname", "");
        return data;
    }
    public String cs_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname", "");
        return data;
    }
    public String pb_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pb_uname", "");
        return data;
    }
    public String sr_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname", "");
        return data;
    }
    public String tl_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname", "");
        return data;
    }

    public String loadnr_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname1", "");
        return data;
    }
    public String loadnr_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname2", "");
        return data;
    }
    public String loadnr_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname3", "");
        return data;
    }
    public String loadnr_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname4", "");
        return data;
    }
    public String loadnr_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname5", "");
        return data;
    }
    public String loadnr_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("nr_uname6", "");
        return data;
    }

    public String loadgd_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname1", "");
        return data;
    }
    public String loadgd_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname2", "");
        return data;
    }
    public String loadgd_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname3", "");
        return data;
    }


    public String loadcs_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname1", "");
        return data;
    }
    public String loadcs_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname2", "");
        return data;
    }
    public String loadcs_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname3", "");
        return data;
    }
    public String loadcs_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname4", "");
        return data;
    }
    public String loadcs_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname5", "");
        return data;
    }
    public String loadcs_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("cs_uname6", "");
        return data;
    }

    public String loadpb_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pb_uname1", "");
        return data;
    }
    public String loadpb_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pb_uname2", "");
        return data;
    }
    public String loadpb_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pb_uname3", "");
        return data;
    }
    public String loadgd_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname4", "");
        return data;
    }
    public String loadgd_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname5", "");
        return data;
    }
    public String loadgd_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gd_uname6", "");
        return data;
    }

    public String loadbm_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname1", "");
        return data;
    }
    public String loadbm_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname2", "");
        return data;
    }
    public String loadbm_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname3", "");
        return data;
    }
    public String loadbm_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname4", "");
        return data;
    }
    public String loadbm_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname5", "");
        return data;
    }
    public String loadbm_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname6", "");
        return data;
    }
    public String loadbm_uname7() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname7", "");
        return data;
    }
    public String loadbm_uname8() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname8", "");
        return data;
    }
    public String loadbm_uname9() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname9", "");
        return data;
    }
    public String loadbm_uname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bm_uname", "");
        return data;
    }

    public String loadsr_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname1", "");
        return data;
    }
    public String loadsr_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname2", "");
        return data;
    }
    public String loadsr_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname3", "");
        return data;
    }
    public String loadsr_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname4", "");
        return data;
    }
    public String loadsr_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname5", "");
        return data;
    }
    public String loadsr_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sr_uname6", "");
        return data;
    }


    public String loadtl_uname1() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname1", "");
        return data;
    }
    public String loadtl_uname2() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname2", "");
        return data;
    }
    public String loadtl_uname3() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname3", "");
        return data;
    }
    public String loadtl_uname4() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname4", "");
        return data;
    }
    public String loadtl_uname5() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname5", "");
        return data;
    }
    public String loadtl_uname6() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("tl_uname6", "");
        return data;
    }

    public String loadcountry() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("country", "");
        return data;
    }

    public String loadid() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("idvalue", "");
        return data;
    }

    public String loadvideourl() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("videoresp", "");
        return data;
    }

    public String loaddraw() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("drawresp", "");
        return data;
    }

    public String loadWallet() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("wallet", "");
        return data;
    }

    public String today_reward() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("today_reward", "");
        return data;
    }

    public String total_reward() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("total_reward", "");
        return data;
    }

    public String available_reward() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("available_reward", "");
        return data;
    }

    public String loadId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("id", "");
        return data;
    }
    public String loadtype() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("type", "");
        return data;
    }

    public String loadbinary() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("binary", "");
        return data;
    }

    public String loadtime() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("datetime", "");
        return data;
    }

    public String loadfast() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("fast", "");
        return data;
    }

    public String loadpro() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("pro", "");
        return data;
    }

    public String loadprime() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("prime", "");
        return data;
    }

    public String loadbonus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("bonus", "");
        return data;
    }

    public String loadauto() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("auto", "");
        return data;
    }

    public String loadAccount() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("account", "");
        return data;
    }

    public String loadReport() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("report_child", "");
        return data;
    }

    public String loadVIP() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("vip", "");
        return data;
    }

    public String loadImage() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("image", "");
        return data;
    }

    public String loadWithdraw() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("withdraw", "");
        return data;
    }

    public String loadName() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("name", "");
        return data;
    }

    public String loaddate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("date", "");
        return data;
    }

    public String loadcheckdt() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("vdate", "");
        return data;
    }

    public String loadalreadydate() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("datealready", "");
        return data;
    }
    public String loadSubusers() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("subuser", "");
        return data;
    }
    public String loadPurchase() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("purchase", "");
        return data;
    }
    public String loadSales() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("sales", "");
        return data;
    }

    public String loadDoj() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("doj", "");
        return data;
    }

    public String loadDesignation() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("designation", "");
        return data;
    }

    public String loadIbv() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("ibv", "");
        return data;
    }
    public String loadgbv() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("gbv", "");
        return data;
    }
    public String loadCommition() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("commition", "");
        return data;
    }
    public String loadFname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("fname", "");
        return data;
    }
    public String loadLname() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("lname", "");
        return data;
    }
    public String loadmob() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("mobile", "");
        return data;
    }

    public String loadplan() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("plantype", "");
        return data;
    }

    public String selectedquiz() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        String data = sharedPreferences.getString("selectedquiz", "");
        return data;
    }

    public String getIMEI(Context context) {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        return telephonyManager.getDeviceId();
    }


}
