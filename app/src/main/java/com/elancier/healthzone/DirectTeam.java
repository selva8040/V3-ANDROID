package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.DirectTeamAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.DirectTeamPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DirectTeam extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout,tree_lay,direct_team_lay;
    ArrayList<DirectTeamPojo> data;
    DirectTeamPojo pojo;
    ListView listView;
    DirectTeamAdapter adapter;
    String[] name = {"RamKumar", "Senthil", "Prabakaran", "Rahavan", "Sathis", "Sonthosh","RamKumar", "Senthil", "Prabakaran", "Rahavan"};

    ImageView tree_img;
    Utils utils;
    Dialog retry;
    ImageView retrybut;
    TextView nodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_team);
        utils=new Utils(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);

        init();

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
        tree_lay = (LinearLayout) findViewById(R.id.tree_lay);
        tree_img = (ImageView) findViewById(R.id.tree_img);
        direct_team_lay = (LinearLayout) findViewById(R.id.direct_team_lay);
        setclick(drawer_layout, drawerLayout, DirectTeam.this);
        nodata=(TextView)findViewById(R.id.nodata);

        tree_lay.setVisibility(View.VISIBLE);
        tree_img.setImageResource(R.mipmap.up_arrow);
        direct_team_lay.setBackgroundColor(getResources().getColor(R.color.eee));

        listView = (ListView) findViewById(R.id.listView);
        //data = new ArrayList<DirectTeamPojo>();


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


                //Log.i("check Input", Appconstants.GET_MY_DOWNLINE+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_DOWNLINE,jobj,"");
                //Log.i("result", result + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("tabresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {

                    data=new ArrayList<DirectTeamPojo>();
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        for(int i=0;i<jarr.length();i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            pojo = new DirectTeamPojo();
                            pojo.setName(jobj.getString("name").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("name"));
                            pojo.setUsername(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                            pojo.setDate(jobj.getString("dob").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("dob"));
                            pojo.setDesignation(jobj.getString("designation").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("designation"));
                            pojo.setMobile(jobj.getString("mobile").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("mobile"));
                            data.add(pojo);
                        }
                        if(data.size()>0) {
                            adapter = new DirectTeamAdapter(DirectTeam.this, R.layout.direct_team_list_items, data);
                            listView.setAdapter(adapter);
                            nodata.setVisibility(View.GONE);
                        }
                        else{
                            nodata.setVisibility(View.VISIBLE);
                        }

                    }
                    else{
                        nodata.setVisibility(View.VISIBLE);
                    }
                } else {
                    retry.show();
                    nodata.setVisibility(View.GONE);
                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                retry.show();
                e.printStackTrace();
                nodata.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

       super.onBackPressed();

    }
}
