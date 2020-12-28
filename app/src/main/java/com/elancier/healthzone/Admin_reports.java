package com.elancier.healthzone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Adapter.RewardpointsAdapter;
import com.elancier.healthzone.Adapter.ServerAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.CheckNetwork;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.Rewardpointsbo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Admin_reports extends AppCompatActivity
{
    LinearLayout progress_lay,retry_lay;
    RecyclerView recyclerlist;
    TextView nodata, retry;
    Utils utils;

    ServerAdapter itemsAdapter;
    private List<Object> mRecyclerListitems = new ArrayList<>();
    private List<Rewardpointsbo> productItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewardpoints);

        getSupportActionBar().setTitle("Admin Reports");

        utils = new Utils(getApplicationContext());

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);

        recyclerlist = (RecyclerView) findViewById(R.id.recyclerlist);
        recyclerlist.setLayoutManager(new LinearLayoutManager(this));
        recyclerlist.setItemAnimator(new DefaultItemAnimator());

        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);

        if(CheckNetwork.isInternetAvailable(Admin_reports.this))
        {
            RewardpointsAsync reward = new RewardpointsAsync();
            reward.execute();
        }
        else
        {
            recyclerlist.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckNetwork.isInternetAvailable(Admin_reports.this))
                {
                    recyclerlist.setVisibility(View.VISIBLE);
                    retry_lay.setVisibility(View.GONE);
                    RewardpointsAsync reward = new RewardpointsAsync();
                    reward.execute();
                }
                else
                {
                    recyclerlist.setVisibility(View.GONE);
                    retry_lay.setVisibility(View.VISIBLE);
                }
            }
        });

        itemsAdapter = new ServerAdapter(mRecyclerListitems, getApplicationContext(), new ServerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(final View view, final int position, int viewType) {
                final Rewardpointsbo item = (Rewardpointsbo) mRecyclerListitems.get(position);
            }
        });
        recyclerlist.setAdapter(itemsAdapter);
    }

    public class RewardpointsAsync extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            progress_lay.setVisibility(View.VISIBLE);
            recyclerlist.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();

            try
            {
                JSONObject jobj = new JSONObject();
                jobj.put("uname",utils.loadName());


                Log.i("rewardinput", Appconstants.server+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.server,jobj,"");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            Log.e("rewardresp", resp);
            recyclerlist.setVisibility(View.VISIBLE);
            progress_lay.setVisibility(View.GONE);

            productItems = new ArrayList<>();

            try
            {
                JSONObject jarr = new JSONObject(resp);


                    if(jarr.getString("Status").equals("Success"))
                    {
                        JSONArray jarray = jarr.getJSONArray("Response");
                        for(int j=0; j<jarray.length(); j++)
                        {
                            JSONObject jobject = jarray.getJSONObject(j);

                            String date = jobject.getString("dtime");
                            String visual_time = jobject.getString("downtime");
                            String username = jobject.getString("uname");
                            String name = jobject.getString("mobile");


                            productItems.add(new Rewardpointsbo("", date, visual_time, username, name,"", "", "", "", ""));
                        }
                    }
                    else
                    {
                        recyclerlist.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }

                mRecyclerListitems.addAll(productItems);
                itemsAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Log.e("excep",e.toString());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Admin_reports.this,HomePage.class);
        startActivity(intent);
    }
}
