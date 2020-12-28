package com.elancier.healthzone;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.SalesReportAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ProductBo;
import com.elancier.healthzone.Pojo.SalesBo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP PC on 25-07-2017.
 */

public class Sales_report extends MainView {

    TextView nodata, retry;
    LinearLayout retry_lay, progress_lay, paging_lay;

    ListView listView;
    // TextView nodata;
    DrawerLayout drawerLayout;
    LinearLayout drawer_layout;
    // Dialog retry;
    ImageView retrybut;
    ArrayList<SalesBo> data;
    int start = 0;
    int limit = 20;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;
    Utils utils;
    String Uname = "";
    SalesReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        utils = new Utils(this);
        // getSupportActionBar().setHomeAsUpIndicator();
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        listView = (ListView) findViewById(R.id.listView);
        nodata = (TextView) findViewById(R.id.nodata);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        setclick(drawer_layout, drawerLayout, Sales_report.this);
        /*LinearLayout reportlay=(LinearLayout)findViewById(R.id.sales_lay);
        reportlay.setBackgroundColor(getResources().getColor(R.color.eee));
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
        });*/


        init();

        start = 0;
        limit = 20;
        scrollNeed = true;
        progress_lay.setVisibility(View.VISIBLE);
        data = new ArrayList<>();
        //startprogress();
        GetInfoTask task = new GetInfoTask();
        task.execute(Uname);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lay.setVisibility(View.GONE);
                progress_lay.setVisibility(View.VISIBLE);
                adapter = null;
                data = new ArrayList<>();
                //startprogress();
                GetInfoTask task = new GetInfoTask();
                task.execute(Uname);
            }
        });
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollValue && scrollNeed && (data.size() > 0)) {
                    scrollNeed = false;
                    listView.setPadding(0, 0, 0, px);
                    paging_lay.setVisibility(View.VISIBLE);

                    GetInfoTask task = new GetInfoTask();
                    task.execute(Uname);

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = firstVisibleItem + visibleItemCount;
                if (totalItemCount == count)
                    scrollValue = true;
                else
                    scrollValue = false;
            }
        });


    }

    private void init() {

        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        retry_lay = (LinearLayout) findViewById(R.id.retry_lay);
        paging_lay = (LinearLayout) findViewById(R.id.paging_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        retry = (TextView) findViewById(R.id.retry);
        listView = (ListView) findViewById(R.id.listView);

        data = new ArrayList<>();
    }


    private class GetInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());
                jobj.put("start", start + "");
                jobj.put("limit", limit + "");


                Log.i("utilsInput", Appconstants.SALES_DETAIL + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.SALES_DETAIL, jobj, "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("utilsresp", resp + "");
            //stopprogress();
            progress_lay.setVisibility(View.GONE);
            //listView.setPadding(0, 0, 0, 0);
            paging_lay.setVisibility(View.GONE);
            scrollNeed = true;
            data = new ArrayList<>();


            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            SalesBo bo = new SalesBo();
                            bo.setPqid(jobj.getString("pqid").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("pqid"));
                            bo.setCusid(jobj.getString("cusid").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("cusid"));
                            bo.setCustomer(jobj.getString("customer").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("customer"));
                            // bo.setSmobile("");
                            // bo.setBv(jobj.getString("bv").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("bv"));
                            bo.setSdate(jobj.getString("date").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("date"));
                            bo.setAmount(jobj.getString("amount").toString().trim().equalsIgnoreCase("null") ? "0.00" : jobj.getString("amount"));
                            ArrayList<ProductBo> prolist = new ArrayList<>();
                            if (!jobj.isNull("details") && jobj.getString("details").toString().trim().length() != 0) {
                                JSONArray jarrary = jobj.getJSONArray("details");
                                for (int k = 0; k < jarrary.length(); k++) {
                                    JSONObject object = jarrary.getJSONObject(k);
                                    ProductBo pbo = new ProductBo();
                                    pbo.setPname(object.getString("pname").toString().trim().equalsIgnoreCase("null") ? "" : object.getString("pname"));
                                    pbo.setPmrp(object.getString("mrp").toString().trim().equalsIgnoreCase("null") ? "" : object.getString("mrp"));
                                    pbo.setPqty(object.getString("qty").toString().trim().equalsIgnoreCase("null") ? "" : object.getString("qty"));
                                    pbo.setPtotal(object.getString("total").toString().trim().equalsIgnoreCase("null") ? "0.00" : object.getString("total"));
                                    prolist.add(pbo);
                                }
                                bo.salesprodicts = prolist;
                            } else {
                                bo.salesprodicts = prolist;
                            }
                            data.add(bo);

                        }
                        if (adapter == null) {
                            adapter = new SalesReportAdapter(Sales_report.this, R.layout.sales_report_item, data);
                            listView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        if (data.size() > 0) {
                            nodata.setVisibility(View.GONE);
                            start = start + 20;
                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }

                    } else {
                        if (data.size() > 0) {
                            nodata.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "No More Reports.", Toast.LENGTH_SHORT).show();

                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }
                        retry_lay.setVisibility(View.GONE);
                    }
                } else {
                    if (start == 0) {
                        retry_lay.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                        /*Something went wrong please try again.*/
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (start == 0) {
                    //retry_lay.setVisibility(View.VISIBLE);
                    nodata.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Data Found.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No More Data.", Toast.LENGTH_SHORT).show();

                }

            }

        }
    }
                         /*SalesReportAdapter adapter = new SalesReportAdapter(Sales_report.this, R.layout.sales_report_item, data);
                            listView.setAdapter(adapter);

                        if(data.size()>0){
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
              // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();




            }


        }
    }*/

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

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}
