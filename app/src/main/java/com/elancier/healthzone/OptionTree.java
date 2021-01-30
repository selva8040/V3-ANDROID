package com.elancier.healthzone;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.elancier.healthzone.Adapter.OptionTreeListAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.AutofillPojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OptionTree extends AppCompatActivity {

    Utils utils;
    String Uname = "";
    String Unique = "";

    ArrayList<AutofillPojo> data;
    AutofillPojo pojo;

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay,desiglay;

    TextView uname, name, unique, doj, sub_user,add_user,search,uname1,
    uname2;
    OptionTreeListAdapter adapter;
    ListView listView;

    String Spon_Unique = "";
    String Spon_Uname = "";
    String Spon_Name = "";
    String First_Name = "";
    String Last_Name = "";

    ImageView edcancel;
    LinearLayout search_lay;
    CardView cardView7;
    EditText unique_search;
    boolean search_check = false;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_tree);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg_red);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());
       // Uname = getIntent().getExtras().getString("name");
       // Unique = getIntent().getExtras().getString("name");
        Uname = utils.loadName();
        Unique = utils.loadName();
        getSupportActionBar().setTitle(Uname);

        inits();

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lay.setVisibility(View.GONE);
                cardView7.setVisibility(View.GONE);
                progress_lay.setVisibility(View.VISIBLE);
                data = new ArrayList<>();
                GetinfoTask task = new GetinfoTask();
                task.execute(Uname);
            }
        });


        onclick();
    }

    private void inits() {

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        cardView7 = (CardView) findViewById(R.id.cardView7);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);
        listView = (ListView) findViewById(R.id.listView);
        uname1=(TextView) findViewById(R.id.uname1);

        uname2= (TextView) findViewById(R.id.uname2);
        uname = (TextView) findViewById(R.id.uname);
        desiglay = (LinearLayout) findViewById(R.id.linearLayout8);
         sub_user = (TextView) findViewById(R.id.sub_users);
        add_user = (TextView) findViewById(R.id.add_user);
        name = (TextView) findViewById(R.id.name);
        unique = (TextView) findViewById(R.id.unique);
        doj = (TextView) findViewById(R.id.doj);
        unique_search = (EditText) findViewById(R.id.unique_search);
        search = (TextView) findViewById(R.id.search);
        edcancel = (ImageView) findViewById(R.id.edcancel);
        search_lay = (LinearLayout) findViewById(R.id.search_lay);

    }

    private void onclick() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (data.size()>0) {
                    Uname = data.get(position).getUname();
                    getSupportActionBar().setTitle(Uname);
                    Log.i("UNAME", Uname);
                    progress_lay.setVisibility(View.VISIBLE);
                    data = new ArrayList<>();
                    GetinfoTask task = new GetinfoTask();
                    task.execute(Uname);
                }

            }
        });

        edcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress_lay.setVisibility(View.VISIBLE);
                data = new ArrayList<>();
                Uname = Unique;
                GetinfoTask task = new GetinfoTask();
                task.execute(Uname);
                getSupportActionBar().setTitle(Uname);

                /*getSupportActionBar().setTitle(Unique);
                Log.i("UNAME",Unique);
                progress_lay.setVisibility(View.VISIBLE);
                data = new ArrayList<>();
                GetinfoTask task = new GetinfoTask();
                task.execute(Unique);*/
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (unique_search.getText().toString().trim().length()>0) {
                    Uname = unique_search.getText().toString().trim();

                    Log.i("UNAME", Uname);
                    progress_lay.setVisibility(View.VISIBLE);
                    data = new ArrayList<>();
                    GetinfoTask task = new GetinfoTask();
                    task.execute(Uname);
                }
            }
        });

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(OptionTree.this,SignUp.class);
                Bundle bun = new Bundle();
                bun.putString("spon_uname", Spon_Uname);
                bun.putString("spon_unique", Spon_Unique);
                bun.putString("spon_name", Spon_Name);
                in.putExtras(bun);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progress_lay.setVisibility(View.VISIBLE);
        cardView7.setVisibility(View.GONE);

        data = new ArrayList<>();
        GetinfoTask task = new GetinfoTask();
        task.execute(Uname);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
        } if (id == R.id.search) {

            if (!search_check){
                search_lay.setVisibility(View.VISIBLE);
                search_check = true;
            }else {
                search_lay.setVisibility(View.GONE);
                search_check = false;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (Uname.equalsIgnoreCase(Unique)){
            super.onBackPressed();
        }else {
            progress_lay.setVisibility(View.VISIBLE);
            data = new ArrayList<>();
            Uname = Unique;
            GetinfoTask task = new GetinfoTask();
            task.execute(Uname);
            getSupportActionBar().setTitle(Uname);
        }
       /* if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        progress_lay.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        GetinfoTask task = new GetinfoTask();
        task.execute(Unique);
        getSupportActionBar().setTitle(Unique);*/

        /*new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/
    }

    /*@Override
    public void onBackPressed() {

       *//* if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {*//*
            //super.onBackPressed();
        //}

        progress_lay.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        GetinfoTask task = new GetinfoTask();
        task.execute(Unique);
        getSupportActionBar().setTitle(Unique);

    }*/


    private class GetinfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            Log.i("GetInfoTask", "started");
        }

        @Override
        protected String doInBackground(String... params) {

            String result = null;
            Connection con = new Connection();

            try {
                JSONObject json = new JSONObject();
                json.put("uname", params[0]);

                Log.i("utilsInput", Appconstants.STARTUP_TREE + "    " + json.toString());
                result = con.sendHttpPostjson2(Appconstants.STARTUP_TREE, json, "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            Log.i("utilsresp", resp + "");
            progress_lay.setVisibility(View.GONE);
            cardView7.setVisibility(View.VISIBLE);

            //listView.setPadding(0, 0, 0, 0);
            paging_lay.setVisibility(View.GONE);
            // scrollNeed = true;
            listView.setVisibility(View.VISIBLE);
            try {

                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {


                        JSONArray jsonArray1 = jsonObject.getJSONArray("Response");

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

                        getSupportActionBar().setTitle(Uname);

                        doj.setText(Uname);
                        //uname.setText(jsonObject1.getString("username").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("username"));
                       // Spon_Uname = (jsonObject1.getString("username").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("username"));
                        unique.setText(jsonObject1.getString("last_name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("last_name"));
                        Last_Name = (jsonObject1.getString("last_name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("last_name"));
                       // Spon_Unique = (jsonObject1.getString("unique").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("unique"));
                        First_Name = (jsonObject1.getString("first_name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("first_name"));
                        name.setText(jsonObject1.getString("first_name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("first_name"));
                      //  Spon_Name = (jsonObject1.getString("name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("name"));

                        if(!jsonObject1.getString("designation").trim().isEmpty())
                        {
                            desiglay.setVisibility(View.VISIBLE);
                            desiglay.setVisibility(View.VISIBLE);
                            uname.setText(jsonObject1.getString("designation"));
                        }
                        else{
                            uname.setText("None");

                        }

                        uname1.setText(jsonObject1.getString("super1"));
                        uname2.setText(jsonObject1.getString("super2"));
                        sub_user.setText(jsonObject1.getString("plan").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("plan"));
                        //sub_user.setText(jsonObject1.getString("child_count").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("child_count"));
                        Spon_Uname = Uname;
                        Spon_Unique = Uname;
                        Spon_Name = First_Name+Last_Name;

                        if (!jsonObject1.isNull("child") && !jsonObject1.getString("child").equalsIgnoreCase("")&& !jsonObject1.getString("child").equalsIgnoreCase("Null")) {

                            JSONArray jsonArray2 = jsonObject1.getJSONArray("child");
                            for (int i = 0; i < jsonArray2.length(); i++) {

                                JSONObject jsonObject2 = jsonArray2.getJSONObject(i);

                                pojo = new AutofillPojo();

                                // pojo.setData(jsonObject1.toString());
                                pojo.setUname(jsonObject2.getString("username").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("username"));
                                pojo.setName(jsonObject2.getString("name").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("name"));
                                pojo.setMobile(jsonObject2.getString("designation").trim().equalsIgnoreCase("null") ? "None" : jsonObject2.getString("designation"));
                                pojo.setSub_users(jsonObject2.getString("utype").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("utype"));
                                pojo.setProduct(jsonObject2.getString("color").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("color"));
                                pojo.setMrp(jsonObject2.getString("super1").trim().equalsIgnoreCase("null") ? "None" : jsonObject2.getString("super1"));
                                pojo.setQty(jsonObject2.getString("super2").trim().equalsIgnoreCase("null") ? "None" : jsonObject2.getString("super2"));
                                pojo.setDoj(jsonObject2.getString("welcome").trim().equalsIgnoreCase("null") ? "None" : jsonObject2.getString("welcome"));
                                pojo.setUnique(jsonObject2.getString("welcome_up").trim().equalsIgnoreCase("null") ? "None" : jsonObject2.getString("welcome_up"));

                                // pojo.setUnique(jsonObject2.getString("unique").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("unique"));
                                //pojo.setSub_users(jsonObject2.getString("child_count").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("child_count"));
                                //pojo.setDoj(jsonObject2.getString("doj").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("doj"));

                                data.add(pojo);
                            }

                        }else {
                            listView.setVisibility(View.GONE);
                            nodata.setVisibility(View.VISIBLE);
                            nodata.setText("No Sub Users ");
                            adapter = new OptionTreeListAdapter(OptionTree.this, R.layout.option_tree_list_items, data);
                            listView.setAdapter(adapter);
                        }

                        nodata.setVisibility(View.GONE);
                        adapter = new OptionTreeListAdapter(OptionTree.this, R.layout.option_tree_list_items, data);
                        listView.setAdapter(adapter);

                    } else {

                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                        retry_lay.setVisibility(View.GONE);
                    }

                } else {
                    retry_lay.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
