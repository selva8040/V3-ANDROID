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
import android.widget.ListView;
import android.widget.TextView;

import com.elancier.healthzone.Adapter.PassbookAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.PassbookBo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP PC on 04-08-2017.
 */

public class PassbookActivity extends MainView {

    Utils utils;
    Dialog retry;
    ImageView retrybut;
    ArrayList<PassbookBo> passlist;
    ListView plistvw;
    TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils=new Utils(getApplicationContext());
        loadprogress();
        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v=getLayoutInflater().inflate(R.layout.retrylay,null);
        retrybut=(ImageView)v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        plistvw=(ListView)findViewById(R.id.listView);
        nodata=(TextView)findViewById(R.id.nodata);

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
               //  Log.i("utilsInput", Appconstants.EPASSBOOK+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.EPASSBOOK,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("utilsresp", resp + "");
            stopprogress();
            passlist=new ArrayList<>();
            try {
                if (resp != null) {

                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        for(int i=0;i<jarr.length();i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            PassbookBo bo=new PassbookBo();
                            if(jobj.getString("debit").toString().equals("")){
                                   bo.setType1("");
                            }
                            else{
                                JSONArray array=jobj.getJSONArray("debit");
                                JSONObject object=array.getJSONObject(0);
                                bo.setType1("Debit");
                             //   Log.i("positionnnnnnn",i+"       "+object.toString());

                                bo.setDebit1(object.getString("wamount").toString().trim().equalsIgnoreCase("null")?"":object.getString("wamount"));
                                bo.setDate1(object.getString("wdate").toString().trim().equalsIgnoreCase("null")?"":object.getString("wdate"));
                                bo.setBalance1(object.getString("balance").toString().trim().equalsIgnoreCase("null")?"0.00":object.getString("balance"));
                                bo.setCredit1("");

                            }
                            if(jobj.getString("credit").toString().equals("")){
                                bo.setType("");
                            }
                            else{
                                JSONArray array=jobj.getJSONArray("credit");
                                JSONObject object=array.getJSONObject(0);
                                bo.setType("Credit");
                                bo.setCredit(object.getString("amount").toString().trim().equalsIgnoreCase("null")?"":object.getString("amount"));
                                bo.setDate(object.getString("date").toString().trim().equalsIgnoreCase("null")?"":object.getString("date"));
                                bo.setBalance(object.getString("total").toString().trim().equalsIgnoreCase("null")?"0.00":object.getString("total"));
                                bo.setDebit("");

                            }
                            // bo.setUserid(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                           passlist.add(bo);

                        }
                        PassbookAdapter adapter=new PassbookAdapter(PassbookActivity.this,R.layout.passbook_item,passlist);
                        plistvw.setAdapter(adapter);
                        scrollMyListViewToBottom(passlist.size()-1);

                    }
                    else{
                       nodata.setVisibility(View.GONE);
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

    private void scrollMyListViewToBottom(final int pos) {
        plistvw.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                plistvw.setSelection(pos);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
