package com.elancier.healthzone;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.CategorySpinnerAdapter;
import com.elancier.healthzone.Adapter.PurchaseLoyalityListAdapter;
import com.elancier.healthzone.Adapter.SalesPointAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ProductBo;
import com.elancier.healthzone.Pojo.ReportsPojo;
import com.elancier.healthzone.Pojo.SalesPointPojo;
import com.elancier.healthzone.Pojo.SpinnerPojo;
import com.elancier.healthzone.Pojo.SubCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SalesPointNew extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, sales_point_lay;
    ListView listView;
    ArrayList<SalesPointPojo> data;
    SalesPointPojo pojo;
    SalesPointAdapter adapter;
    String[] name = {"Apple", "Orange", "Grapes"};
    Integer[] quantity = {2, 4, 3};
    Double[] rate = {123.00, 110.50, 95.45};
    FloatingActionButton fab;

    int menuvalue=0;
    Dialog dialog,product_dialog;
    ImageView back, pencil, edcancel;
    TextView title, submit, cat_error, subcat_error, pname_error, grand_total;
    EditText customer_name, mobile, mrp, stock, qty, sub_total,product;
    // Spinner cat_spin, subcat_spin, pname_spin;


    //ArrayList<SpinnerPojo> cat_data, subcat_data, pname_data;
    //CategorySpinnerAdapter cat_adap;
    boolean emailcheck = true;
    String MobilePattern = "[0-9]{10}";
    String NamePattern = "[a-zA-Z ]+";
    String RelationPattern = "[a-zA-Z -]+";
    //String email1 = email.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    static double total, bal_amt, pay_amt;
    static double totalvalue;

    Utils utils;
    Dialog retry;
    TextView nodata;
    LinearLayout proglay;
    LinearLayout retrylay;
    ImageView retrybut;
    String categoryname="";
    String subcatname="";
    String productname="";
    String pidd="";
    // ArrayList<SubCategory> subcatlist;
    // ArrayList<ProductBo> productlist;
    TextView send;
    EditText customerid;
    int mfocus=0;
    String cusidvalue="";
    String cusname="";
    String cusmob="";

    ArrayList<ReportsPojo> datas;
    PurchaseLoyalityListAdapter adapters;
    ReportsPojo pojos;

    TextView nodatas, retrys;
    LinearLayout retry_lays, progress_lays, paging_lays;
    int start = 0;
    int limit = 2;
    int px;
    private boolean scrollValue;
    private boolean scrollNeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_point);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils=new Utils(getApplicationContext());

        init();
        Total();
        loadprogress();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSalesTask task=new AddSalesTask();
                task.execute();
            }
        });



    }

    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer_layout = (LinearLayout) findViewById(R.id.drawer_layout);
        sales_point_lay = (LinearLayout) findViewById(R.id.sales_point_lay);
        nodata = (TextView) findViewById(R.id.nodata);
        grand_total = (TextView) findViewById(R.id.total);
        // fab = (FloatingActionButton) findViewById(R.id.fab);
        setclick(drawer_layout, drawerLayout, SalesPointNew.this);
        sales_point_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        listView = (ListView) findViewById(R.id.listView);
        send=(TextView)findViewById(R.id.submit);

        data = new ArrayList<SalesPointPojo>();
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                menuvalue=1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                menuvalue=0;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.setDrawerListener(mDrawerToggle);


    }



    private double Total() {
        total = 0;
        totalvalue = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                total = 0;
                for (int j = i; j <= i; j++) {

                    total = data.get(i).getMrp() * data.get(i).getQty();
                   // Log.i("totvals", data.get(i).getMrp() * data.get(i).getQty() + "");
                }
                totalvalue = totalvalue + total;
                //Log.i("totval", totalvalue + "");
            }
        }
        double vall = totalvalue;
        grand_total.setText("Rs. " + String.format("%.2f", vall) + "");
        //Log.i("carttot", totalvalue + "");
        return vall;
    }

    private void AddSalesPoint() {

        dialog = new Dialog(SalesPointNew.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.add_sales_point, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        submit = (TextView) popup.findViewById(R.id.submit);

        customer_name = (EditText) popup.findViewById(R.id.customer_name);
        mobile = (EditText) popup.findViewById(R.id.mobile);
        mrp = (EditText) popup.findViewById(R.id.product_mrp);
        stock = (EditText) popup.findViewById(R.id.stock_in_hand);
        qty = (EditText) popup.findViewById(R.id.no_of_qty);
        sub_total = (EditText) popup.findViewById(R.id.total_amt);
        product = (EditText) popup.findViewById(R.id.product);
        /*cat_error = (TextView) popup.findViewById(R.id.cat_error);
        subcat_error = (TextView) popup.findViewById(R.id.subcat_error);
        pname_error = (TextView) popup.findViewById(R.id.pname_error);*/
        proglay=(LinearLayout)popup.findViewById(R.id.proglayy);
        retrylay=(LinearLayout)popup.findViewById(R.id.retrylay);
        retrybut=(ImageView) popup.findViewById(R.id.retry);
        customerid=(EditText)popup.findViewById(R.id.customer_id);

        if(cusidvalue.equals("")){
            customerid.setText("");
            customer_name.setText("");
            mobile.setText("");
        }
        else{
            customerid.setText(cusidvalue+"");
            customer_name.setText(cusname+"");
            mobile.setText(cusmob+"");
            customerid.setEnabled(false);
            customerid.setBackgroundResource(R.drawable.grey_bg1);
            mfocus=1;
        }



        proglay.setVisibility(View.VISIBLE);
        GetInfoTask task=new GetInfoTask();
        task.execute();

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrylay.setVisibility(View.GONE);
                proglay.setVisibility(View.VISIBLE);
                GetInfoTask task=new GetInfoTask();
                task.execute();

            }
        });
        customerid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    mfocus=0;
                    final EditText Caption = (EditText) v;
                    //Log.i("valuessss",Caption.getText().toString().trim().length()+"    "+Caption.getText().toString().trim());
                    if(Caption.getText().toString().trim().length()>0){
                        startprogress();
                        CheckMobileTask task=new CheckMobileTask();
                        task.execute(Caption.getText().toString().trim());
                    }
                    // items.get(position).setPronotes( Caption.getText().toString());
                    // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                    // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
                }
                else{
                    mfocus=1;
                }
            }
        });
        dialog.setContentView(popup);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  AddSalesPoint();*/
                dialog.dismiss();
            }
        });

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductPopup();
            }
        });


        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str=s.toString().trim();
                if(str.length()>0&&Integer.parseInt(stock.getText().toString().trim())<Integer.parseInt(str)){
                    qty.setText("");
                    Toast.makeText(SalesPointNew.this,"Out of stock",Toast.LENGTH_SHORT).show();
                    sub_total.setText("");
                }
                else{
                    if(str.length()>0)
                        sub_total.setText(Integer.parseInt(mrp.getText().toString().trim())*Integer.parseInt(str)+"");
                    else
                        sub_total.setText("");
                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int proval=2;

                if (customerid.getText().toString().length() <= 0) {
                    customerid.setError("Enter Customer id");
                }

               /* if (customer_name.getText().toString().length() > 0 && !customer_name.getText().toString().matches(NamePattern)) {
                    customer_name.setError("Enter characters only");
                }

                if (mobile.getText().toString().length() <= 0) {
                    mobile.setError("Enter mobile number");
                }

                if (mobile.getText().toString().length() > 0 && !mobile.getText().toString().matches(MobilePattern)) {
                    mobile.setError("Enter valid  mobile number");
                }*/

                /*if (mrp.getText().toString().length() <= 0) {
                    mrp.setError("Mrp should not be empty");
                }
                else {
                    mrp.setError(null);
                }*/

              /*  if (stock.getText().toString().length() <= 0) {
                    stock.setError("Stock should not be empty");
                }
                else{
                    stock.setError(null);
                }*/

                if (qty.getText().toString().trim().length() <= 0) {
                    qty.setError("Enter Quantity ");
                }
                else{
                    qty.setError(null);
                }

                /*if (qty.getText().toString().trim().length()>0&&stock.getText().toString().trim().length() > 0 && Integer.parseInt(stock.getText().toString().trim()) <= Integer.parseInt(qty.getText().toString().trim())) {
                    qty.setError("Enter Valid Quantity ");
                }*/



                if ((customer_name.getText().toString().trim().length() > 0) && customer_name.getText().toString().matches(NamePattern)
                        && (mobile.getText().toString().trim().length() > 0) && mobile.getText().toString().matches(MobilePattern)
                        && (mrp.getText().toString().trim().length() > 0) && (stock.getText().toString().trim().length() > 0)
                        && (qty.getText().toString().trim().length() > 0) && (sub_total.getText().toString().trim().length() > 0)
                        /*&& (cat_spin.getSelectedItemPosition() != 0) && (subcat_spin.getSelectedItemPosition() != 0)
                        && pname_spin.getSelectedItemPosition() != 0&&proval==2*/) {

                    Toast.makeText(SalesPointNew.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();
                    cusidvalue=customerid.getText().toString().trim();
                    cusname=customer_name.getText().toString().trim();
                    cusmob=mobile.getText().toString().trim();
                    pojo = new SalesPointPojo();

                    pojo.setProduct_name(productname);
                    pojo.setPid(pidd);

                    pojo.setQty(Integer.parseInt(qty.getText().toString()));
                    pojo.setMrp(Double.parseDouble(mrp.getText().toString()));
                    pojo.setTotal(Integer.parseInt(qty.getText().toString())*Double.parseDouble(mrp.getText().toString()));
                    data.add(pojo);

                    adapter = new SalesPointAdapter(SalesPointNew.this, R.layout.sales_point_list_item, data);
                    listView.setAdapter(adapter);
                    if(data.size()>0){
                        send.setVisibility(View.VISIBLE);
                    }
                    Total();
                    dialog.dismiss();
                } else {
                    if(proval==2) {
                        Toast.makeText(SalesPointNew.this, "Please Fill Above Details", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SalesPointNew.this,"Product already exist",Toast.LENGTH_SHORT).show();
                        // cat_spin.setSelection(0);
                    }
                }

            }
        });

    }

    private void ProductPopup() {


        product_dialog = new Dialog(SalesPointNew.this);
        product_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.product_list_popup, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        submit = (TextView) popup.findViewById(R.id.submit);



       /* start = 0;
        limit = 20;
        scrollNeed = true;

        progress_lays.setVisibility(View.VISIBLE);
        datas = new ArrayList<>();
        GetUsersTask task = new GetUsersTask();
        task.execute("");

        retrys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retry_lays.setVisibility(View.GONE);
                progress_lays.setVisibility(View.VISIBLE);
                adapters = null;
                start = 0;
                limit = 20;
                datas = new ArrayList<>();
                GetUsersTask task = new GetUsersTask();
                task.execute("");
            }
        });

        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollValue && scrollNeed && (datas.size() > 0)) {
                    scrollNeed = false;
                    listView.setPadding(0, 0, 0, px);
                    paging_lays.setVisibility(View.VISIBLE);

                    if (searchname.trim().length() > 0) {
                        paging_lays.setVisibility(View.VISIBLE);
                        GetUsersTask task = new GetUsersTask();
                        task.execute(searchname);

                    } else {
                        paging_lays.setVisibility(View.VISIBLE);
                        GetUsersTask task = new GetUsersTask();
                        task.execute("");
                    }

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


        product_dialog.setContentView(popup);
        product_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        product_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        product_dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        product_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        product_dialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_dialog.dismiss();
            }
        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_point, menu);
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
            if(menuvalue==0)
                onBackPressed();
            return true;
        }
        if (id == R.id.add) {
            AddSalesPoint();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void Refresh(ArrayList<SalesPointPojo> items) {

        adapter.notifyDataSetChanged();
        if(data.size()==0){
            cusidvalue="";
            cusmob="";
            cusname="";
            send.setVisibility(View.GONE);
        }
        else{
            send.setVisibility(View.VISIBLE);
        }

    }

    public double setTotal() {

        total = 0;
        totalvalue = 0;
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                total = 0;
                for (int j = i; j <= i; j++) {

                    total = data.get(i).getMrp() * data.get(i).getQty();
                    //Log.i("totvals", data.get(i).getMrp() * data.get(i).getQty() + "");
                }
                totalvalue = totalvalue + total;
                //Log.i("totval", totalvalue + "");
            }
        }
        double vall = totalvalue;
        grand_total.setText("Rs. " + String.format("%.2f", vall) + "");

        //Log.i("carttot", totalvalue + "");
        return vall;

    }


    private class GetInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());


                //Log.i("check Input", Appconstants.GET_MY_Sales+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_MY_Sales,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
           // Log.i("tabresp", resp + "");
            proglay.setVisibility(View.GONE);

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        SpinnerPojo pojo1 = new SpinnerPojo();
                        pojo1.setCategory("0");
                        pojo1.setCategoryname("Select");
                        for(int i=0;i<jarr.length();i++) {
                            JSONObject jobj = jarr.getJSONObject(i);
                            SpinnerPojo pojo = new SpinnerPojo();
                            pojo.setCategory(jobj.getString("catid").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("catid"));
                            pojo.setCategoryname(jobj.getString("catname").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("catname"));
                            ArrayList<SubCategory> scatlist=new ArrayList<>();
                            if(!jobj.isNull("subcategory")&&!jobj.getString("subcategory").toString().trim().equalsIgnoreCase("")) {
                                JSONArray array = jobj.getJSONArray("subcategory");

                                SubCategory cat=new SubCategory();
                                cat.setSid("0");
                                cat.setSname("Select");
                                scatlist.add(cat);
                                for(int k=0;k<array.length();k++){
                                    JSONObject catobj=array.getJSONObject(k);
                                    SubCategory scat=new SubCategory();
                                    scat.setSid(catobj.getString("subid").toString().trim().equalsIgnoreCase("null")?"":catobj.getString("subid"));
                                    scat.setSname(catobj.getString("subname").toString().trim().equalsIgnoreCase("null")?"":catobj.getString("subname"));
                                    ArrayList<ProductBo> prolist=new ArrayList<>();
                                    if(!catobj.isNull("product")&&!catobj.getString("product").toString().trim().equalsIgnoreCase("")){
                                        ProductBo pbo=new ProductBo();
                                        pbo.setPid("0");
                                        pbo.setPname("Select");
                                        pbo.setPmrp("0");
                                        pbo.setPstock("0");
                                        prolist.add(pbo);
                                        JSONArray parray=catobj.getJSONArray("product");
                                        for(int m=0;m<parray.length();m++){
                                            JSONObject pobj=parray.getJSONObject(m);
                                            ProductBo prbo=new ProductBo();
                                            prbo.setPid(pobj.getString("pid").toString().trim().equalsIgnoreCase("null")?"":pobj.getString("pid"));
                                            prbo.setPname(pobj.getString("pname").toString().trim().equalsIgnoreCase("null")?"":pobj.getString("pname"));
                                            prbo.setPmrp(pobj.getString("mrp").toString().trim().equalsIgnoreCase("null")?"0":pobj.getString("mrp"));
                                            prbo.setPstock(pobj.getString("stock").toString().trim().equalsIgnoreCase("null")?"0":pobj.getString("stock"));
                                            prolist.add(prbo);
                                        }
                                    }
                                    else{
                                        ProductBo pbo=new ProductBo();
                                        pbo.setPid("0");
                                        pbo.setPname("Select");
                                        pbo.setPmrp("0");
                                        pbo.setPstock("0");
                                        prolist.add(pbo);
                                    }
                                    scat.prolist=prolist;
                                    scatlist.add(scat);
                                }
                            }
                            else{
                                SubCategory cat=new SubCategory();
                                cat.setSid("0");
                                cat.setSname("Select");
                                scatlist.add(cat);
                            }
                            pojo.subcatlist=scatlist;
                           // cat_data.add(pojo);
                        }
                        /*cat_adap = new CategorySpinnerAdapter(SalesPointNew.this, R.layout.sales_point_spinner_list_item, cat_data);
                        cat_spin.setAdapter(cat_adap);
*/
                    }
                    else{
                        SpinnerPojo pojo1 = new SpinnerPojo();
                        pojo1.setCategory("0");
                        pojo1.setCategoryname("Select");
                       /* cat_data.add(pojo1);
                        cat_adap = new CategorySpinnerAdapter(SalesPointNew.this, R.layout.sales_point_spinner_list_item, cat_data);
                        cat_spin.setAdapter(cat_adap);*/
                    }
                } else {
                    retrylay.setVisibility(View.VISIBLE);
                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                retrylay.setVisibility(View.VISIBLE);
                e.printStackTrace();

                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class CheckMobileTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckMobileTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("type","old");
                jobj.put("cusid",param[0]);

               // Log.i("check Input", Appconstants.ADD_NEW_CUSTOMER+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.ADD_NEW_CUSTOMER,jobj,"");
			/*JSONObject json = new JSONObject();
			json.put("mobile",param[0]);
			json.put("otp", param[1]);
			result=con.sendHttpPostjson2(, json, "");*/


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
           // Log.i("mobresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        JSONObject jobj=jarr.getJSONObject(0);
                        customerid.clearFocus();
                        customer_name.setText(jobj.getString("name"));
                        mobile.setText(jobj.getString("mobile"));


                    } else {
                        customerid.requestFocus();
                        customerid.setText("");
                        customer_name.setText("");
                        mobile.setText("");

                        Toast.makeText(SalesPointNew.this,"Enter valid customer id.",Toast.LENGTH_LONG).show();


                    }
                } else {
                    customerid.requestFocus();
                    customerid.setText("");
                    customer_name.setText("");
                    mobile.setText("");
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                customerid.requestFocus();
                customerid.setText("");
                customer_name.setText("");
                mobile.setText("");
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class AddSalesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("AddSalesTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());
                jobj.put("cusid",cusidvalue);
                jobj.put("total",totalvalue);
                JSONArray jsonArray=new JSONArray();
                if(data.size()>0) {
                    JSONObject obj = null;
                    for (int i = 0; i < data.size(); i++) {
                        obj = new JSONObject();
                        obj.put("pname", data.get(i).getPid());
                        obj.put("price", data.get(i).getMrp());
                        obj.put("qty", data.get(i).getQty());
                        obj.put("total", data.get(i).getTotal());
                        jsonArray.put(obj);
                    }
                    jobj.put("details",jsonArray);
                }
                else{
                    jobj.put("details",jsonArray);
                }

               // Log.i("saveinput", Appconstants.ADD_SALES+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.ADD_SALES,jobj,"");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
          //  Log.i("mobresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        Toast.makeText(SalesPointNew.this,"Successfully Save",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(SalesPointNew.this,"Failed to save.",Toast.LENGTH_LONG).show();


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            super.onBackPressed();
        }
    }


}



