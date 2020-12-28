package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.StockListAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Pojo.StockPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StockList extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, stock_list_lay,play,retrylay;
    ListView listView;
    ArrayList<StockPojo> data;
    StockPojo pojo;
    StockListAdapter adapter;
   // String[] name = {"Apple", "Orange", "Grapes", "Bananna", "Stawbery", "Mango", "RamKumar", "Rahavan"};
    TextView nodata;
    Dialog retry;
    ImageView retrybut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);

        init();

        onclick();
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
        task.execute();

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry.dismiss();
                startprogress();
                GetInfoTask task=new GetInfoTask();
                task.execute();
            }
        });

    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        stock_list_lay = (LinearLayout) findViewById(R.id.stock_list_lay);
        play = (LinearLayout) findViewById(R.id.play);
        retrylay = (LinearLayout) findViewById(R.id.retrylay);
        setclick(drawer_layout, drawerLayout, StockList.this);
        nodata = (TextView) findViewById(R.id.nodata);
        stock_list_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        listView = (ListView) findViewById(R.id.listView);


    }

    private void onclick() {


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

        return super.onOptionsItemSelected(item);
    }



    private class GetInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
           // Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());
                //Log.i("utilsInput", Appconstants.GET_STOCKS_LIST+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_STOCKS_LIST,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
           // Log.i("utilsresp", resp + "");
            stopprogress();


              data=new ArrayList<>();
            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        for(int i=0;i<jarr.length();i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            StockPojo bo=new StockPojo();
                            // bo.setUserid(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                            bo.setProductname(jobj.getString("pname").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("pname"));
                            bo.setPurchase(jobj.getString("purchase").toString().trim().equalsIgnoreCase("null")?"0":jobj.getString("purchase"));
                            bo.setSales(jobj.getInt("sales")==0?0:jobj.getInt("sales"));
                            bo.setInhand(jobj.getInt("close")==0?0:jobj.getInt("close"));
                            data.add(bo);

                        }
                        if(adapter==null) {
                            adapter = new StockListAdapter(StockList.this,R.layout.stock_list_item,data);
                            listView.setAdapter(adapter);
                        }
                        else{
                            adapter.notifyDataSetChanged();
                        }
                        if(data.size()>0) {
                            nodata.setVisibility(View.GONE);

                        }
                        else{
                            nodata.setVisibility(View.VISIBLE);
                        }

                    }
                    else{
                        if(data.size()>0){
                            nodata.setVisibility(View.GONE);

                        }
                        else{
                            nodata.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                     retry.show();


                }
            } catch (Exception e) {
                e.printStackTrace();
               retry.show();



            }


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
