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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.elancier.healthzone.Adapter.CategorySpinnerAdapter;
import com.elancier.healthzone.Adapter.PnameSpinnerAdapter;
import com.elancier.healthzone.Adapter.SalesPointAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.ProductBo;
import com.elancier.healthzone.Pojo.SalesPointPojo;
import com.elancier.healthzone.Pojo.SpinnerPojo;
import com.elancier.healthzone.Pojo.SubCategory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SalesPoint extends MainView {

    DrawerLayout drawerLayout;
    LinearLayout drawer_layout, sales_point_lay;
    ListView listView, product_list;
    ArrayList<SalesPointPojo> data;
    SalesPointPojo pojo;
    SalesPointAdapter adapter;
    PnameSpinnerAdapter pro_adapter;
    String[] name = {"Apple", "Orange", "Grapes"};
    Integer[] quantity = {2, 4, 3};
    Double[] rate = {123.00, 110.50, 95.45};
    FloatingActionButton fab;

    int menuvalue = 0;
    Dialog dialog, pro_dialog;
    ImageView back, pro_back, pencil, edcancel;
    TextView title, pro_title, submit, /*cat_error, subcat_error, pname_error,*/
            grand_total;
    EditText customer_name, mobile, my_price,mrp, stock, qty, sub_total, product, pname;
    //Spinner cat_spin, subcat_spin, pname_spin;


    ArrayList<SpinnerPojo> cat_data, subcat_data, pname_data;
    CategorySpinnerAdapter cat_adap;
    boolean emailcheck = true;
    String MobilePattern = "[0-9]{10}";
    String NamePattern = "[a-zA-Z. ]+";
    String RelationPattern = "[a-zA-Z -]+";
    //String email1 = email.getText().toString().trim();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    static double total, bal_amt, pay_amt;
    static double totalvalue;

    Utils utils;
    Dialog retry;
    TextView nodata, pro_nodata;
    LinearLayout proglay, pro_proglay;
    LinearLayout retrylay, pro_retrylay;
    ImageView retrybut, pro_retrybut;
    String categoryname = "";
    String subcatname = "";
    String productname = "";
    String pidd = "";
    ArrayList<SubCategory> subcatlist;
    ArrayList<ProductBo> productlist;
    TextView send;
    EditText customerid;
    int mfocus = 0;
    String cusidvalue = "";
    String cusname = "";
    String cusmob = "";
    String searchname = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_point);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        Drawable d = getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils = new Utils(getApplicationContext());

        init();
        Total();
        loadprogress();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSalesTask task = new AddSalesTask();
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
        setclick(drawer_layout, drawerLayout, SalesPoint.this);
        sales_point_lay.setBackgroundColor(getResources().getColor(R.color.eee));
        listView = (ListView) findViewById(R.id.listView);
        send = (TextView) findViewById(R.id.submit);

        data = new ArrayList<SalesPointPojo>();
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                menuvalue = 1;
                getSupportActionBar().setHomeAsUpIndicator(R.mipmap.back_arrow);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                menuvalue = 0;
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

                    total = data.get(i).getMyprice() * data.get(i).getQty();
                    //Log.i("totvals", data.get(i).getMyprice() * data.get(i).getQty() + "");
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

        dialog = new Dialog(SalesPoint.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.add_sales_point, null);

        back = (ImageView) popup.findViewById(R.id.back);
        title = (TextView) popup.findViewById(R.id.title);
        submit = (TextView) popup.findViewById(R.id.submit);

        customer_name = (EditText) popup.findViewById(R.id.customer_name);
        mobile = (EditText) popup.findViewById(R.id.mobile);
        mrp = (EditText) popup.findViewById(R.id.product_mrp);
        my_price = (EditText) popup.findViewById(R.id.my_price);
        stock = (EditText) popup.findViewById(R.id.stock_in_hand);
        qty = (EditText) popup.findViewById(R.id.no_of_qty);
        product = (EditText) popup.findViewById(R.id.product);
        sub_total = (EditText) popup.findViewById(R.id.total_amt);
       /* cat_error = (TextView) popup.findViewById(R.id.cat_error);
        subcat_error = (TextView) popup.findViewById(R.id.subcat_error);
        pname_error = (TextView) popup.findViewById(R.id.pname_error);*/
        proglay = (LinearLayout) popup.findViewById(R.id.proglayy);
        retrylay = (LinearLayout) popup.findViewById(R.id.retrylay);
        retrybut = (ImageView) popup.findViewById(R.id.retry);
        customerid = (EditText) popup.findViewById(R.id.customer_id);
        if (cusidvalue.equals("")) {
            customerid.setText("");
            customer_name.setText("");
            mobile.setText("");
        } else {
            customerid.setText(cusidvalue + "");
            customer_name.setText(cusname + "");
            mobile.setText(cusmob + "");
            customerid.setEnabled(false);
            customerid.setBackgroundResource(R.drawable.grey_bg1);
            mfocus = 1;
        }

        //cat_spin = (Spinner) popup.findViewById(R.id.category_spin);
        //subcat_spin = (Spinner) popup.findViewById(R.id.sub_cat_spin);
        // pname_spin = (Spinner) popup.findViewById(R.id.pname_spin);


        /*proglay.setVisibility(View.VISIBLE);
        GetInfoTask task = new GetInfoTask();
        task.execute();

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrylay.setVisibility(View.GONE);
                proglay.setVisibility(View.VISIBLE);
                GetInfoTask task = new GetInfoTask();
                task.execute();

            }
        });*/
        customerid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mfocus = 0;
                    final EditText Caption = (EditText) v;
                    //Log.i("valuessss", Caption.getText().toString().trim().length() + "    " + Caption.getText().toString().trim());
                    if (Caption.getText().toString().trim().length() > 0) {
                        startprogress();
                        CheckMobileTask task = new CheckMobileTask();
                        task.execute(Caption.getText().toString().trim());
                    }
                    // items.get(position).setPronotes( Caption.getText().toString());
                    // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                    // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
                } else {
                    mfocus = 1;
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

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductDialog();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  AddSalesPoint();*/
                dialog.dismiss();
            }
        });

        /*cat_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("focusss", mfocus + "");
                if (customerid.getText().toString().trim().length() > 0 && customer_name.getText().toString().trim().length() == 0) {
                    customerid.clearFocus();
                    mfocus = 1;
                }
                if (cat_data != null && cat_data.size() > 0) {
                    categoryname = cat_data.get(position).getCategoryname();
                    subcatlist = new ArrayList<SubCategory>();
                    if (!cat_data.get(position).getCategoryname().equalsIgnoreCase("Select")) {
                        subcatlist = cat_data.get(position).subcatlist;
                        SubcatSpinnerAdapter adapter = new SubcatSpinnerAdapter(SalesPoint.this, R.layout.sales_point_spinner_list_item, subcatlist);
                        subcat_spin.setAdapter(adapter);
                    } else {
                        SubCategory bo = new SubCategory();
                        bo.setSid("0");
                        bo.setSname("Select");
                        subcatlist.add(bo);
                        SubcatSpinnerAdapter adapter = new SubcatSpinnerAdapter(SalesPoint.this, R.layout.sales_point_spinner_list_item, subcatlist);
                        subcat_spin.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subcat_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(SalesPoint.this,"test",Toast.LENGTH_SHORT).show();

                if (customerid.getText().toString().trim().length() > 0 && customer_name.getText().toString().trim().length() == 0) {
                    customerid.clearFocus();
                    mfocus = 1;
                }
                if (subcatlist != null && subcatlist.size() > 0) {
                    productlist = new ArrayList<ProductBo>();
                    subcatname = subcatlist.get(position).getSname();
                    if (!subcatlist.get(position).getSname().equalsIgnoreCase("Select")) {
                        productlist = subcatlist.get(position).prolist;
                        PnameSpinnerAdapter adapter = new PnameSpinnerAdapter(SalesPoint.this, R.layout.sales_point_spinner_list_item, productlist);
                        pname_spin.setAdapter(adapter);
                    } else {
                        ProductBo bo = new ProductBo();
                        bo.setPid("0");
                        bo.setPname("Select");
                        bo.setPmrp("0");
                        bo.setPstock("0");
                        productlist.add(bo);
                        PnameSpinnerAdapter adapter = new PnameSpinnerAdapter(SalesPoint.this, R.layout.sales_point_spinner_list_item, productlist);
                        pname_spin.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pname_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (customerid.getText().toString().trim().length() > 0 && customer_name.getText().toString().trim().length() == 0) {
                    customerid.clearFocus();
                    mfocus = 1;
                }
                if (productlist != null && productlist.size() > 0) {
                    productname = productlist.get(position).getPname();
                    pidd = productlist.get(position).getPid();
                    if (!productname.equalsIgnoreCase("Select")) {
                        mrp.setText(productlist.get(position).getPmrp());
                        stock.setText(productlist.get(position).getPstock());
                        if (productlist.get(position).getPstock().equals("0")) {
                            qty.setEnabled(false);
                            qty.setBackgroundResource(R.drawable.grey_bg1);
                            qty.setText("");
                        } else {
                            qty.setEnabled(true);
                            qty.setBackgroundResource(R.drawable.grey_stroke_bg);
                            qty.setText("");
                        }
                        sub_total.setText("0");

                    } else {
                        mrp.setText("");
                        stock.setText("");
                        qty.setEnabled(false);
                        qty.setBackgroundResource(R.drawable.grey_bg1);
                        qty.setText("");
                        sub_total.setText("");
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();
                if (str.length() > 0 && Integer.parseInt(stock.getText().toString().trim()) < Integer.parseInt(str)) {
                    qty.setText("");
                    Toast.makeText(SalesPoint.this, "Out of stock", Toast.LENGTH_SHORT).show();
                    sub_total.setText("");
                } else {
                    if (str.length() > 0)
                        sub_total.setText(Integer.parseInt(my_price.getText().toString().trim()) * Integer.parseInt(str) + "");
                    else
                        sub_total.setText("");
                }

            }
        });

        my_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString().trim();


                if (qty.getText().toString().trim().length() > 0) {
                    if (str.length() > 0)
                        sub_total.setText(Integer.parseInt(qty.getText().toString().trim()) * Integer.parseInt(str) + "");
                    else
                        sub_total.setText("");
                }else {

                }

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int proval = 2;

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
                } else {
                    qty.setError(null);
                }

                /*if (qty.getText().toString().trim().length()>0&&stock.getText().toString().trim().length() > 0 && Integer.parseInt(stock.getText().toString().trim()) <= Integer.parseInt(qty.getText().toString().trim())) {
                    qty.setError("Enter Valid Quantity ");
                }*/


                /*if (cat_spin.getSelectedItemPosition() == 0) {

                    cat_spin.setBackgroundResource(R.drawable.red_stroke_bg);
                    cat_error.setVisibility(View.VISIBLE);
                } else {
                    cat_spin.setBackgroundResource(R.drawable.grey_stroke_bg);
                    cat_error.setVisibility(View.GONE);
                }

                if (subcat_spin.getSelectedItemPosition() == 0) {

                    subcat_spin.setBackgroundResource(R.drawable.red_stroke_bg);
                    subcat_error.setVisibility(View.VISIBLE);
                } else {
                    subcat_spin.setBackgroundResource(R.drawable.grey_stroke_bg);
                    subcat_error.setVisibility(View.GONE);
                }

                if (pname_spin.getSelectedItemPosition() == 0) {

                    pname_spin.setBackgroundResource(R.drawable.red_stroke_bg);
                    pname_error.setVisibility(View.VISIBLE);
                } else {
                    pname_spin.setBackgroundResource(R.drawable.grey_stroke_bg);
                    pname_error.setVisibility(View.GONE);
                }*/
                //Log.i("datasizeeeeee", data.size() + "          " + data.contains(productname) + "");
                if (data != null && data.size() > 0) {
                    for (int k = 0; k < data.size(); k++) {
                        if (data.get(k).getProduct_name().equals(productname)) {
                            proval = 0;
                            break;
                        }

                    }

                } else {
                    proval = 2;
                }


                if ((customer_name.getText().toString().trim().length() > 0) && customer_name.getText().toString().matches(NamePattern)
                        && (mobile.getText().toString().trim().length() > 0) && mobile.getText().toString().matches(MobilePattern)
                        && (mrp.getText().toString().trim().length() > 0) && (stock.getText().toString().trim().length() > 0)
                        && (qty.getText().toString().trim().length() > 0) && (sub_total.getText().toString().trim().length() > 0)
                        /*&& (cat_spin.getSelectedItemPosition() != 0) && (subcat_spin.getSelectedItemPosition() != 0)*/
                        && pname.getText().toString().trim().length() > 0 && proval == 2) {

                    Toast.makeText(SalesPoint.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();
                    cusidvalue = customerid.getText().toString().trim();
                    cusname = customer_name.getText().toString().trim();
                    cusmob = mobile.getText().toString().trim();
                    pojo = new SalesPointPojo();

                    pojo.setProduct_name(productname);
                    pojo.setPid(pidd);

                    pojo.setQty(Integer.parseInt(qty.getText().toString()));
                    pojo.setMrp(Double.parseDouble(mrp.getText().toString()));
                    pojo.setMyprice(Double.parseDouble(my_price.getText().toString()));
                    pojo.setTotal(Integer.parseInt(qty.getText().toString()) * Double.parseDouble(my_price.getText().toString()));
                    data.add(pojo);

                    adapter = new SalesPointAdapter(SalesPoint.this, R.layout.sales_point_list_item, data);
                    listView.setAdapter(adapter);
                    if (data.size() > 0) {
                        send.setVisibility(View.VISIBLE);
                    }
                    Total();
                    dialog.dismiss();
                } else {
                    if (proval == 2) {
                        Toast.makeText(SalesPoint.this, "Please Fill Above Details", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SalesPoint.this, "Product already exist", Toast.LENGTH_SHORT).show();
                        //cat_spin.setSelection(0);
                    }
                }

            }
        });

    }

    private void ProductDialog() {

        pro_dialog = new Dialog(SalesPoint.this);
        pro_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View popup = getLayoutInflater().inflate(R.layout.add_sales_product, null);

        pro_back = (ImageView) popup.findViewById(R.id.back);
        pro_title = (TextView) popup.findViewById(R.id.title);
        product_list = (ListView) popup.findViewById(R.id.product_list);

        pname = (EditText) popup.findViewById(R.id.pname);
        pro_proglay = (LinearLayout) popup.findViewById(R.id.proglayy);
        pro_retrylay = (LinearLayout) popup.findViewById(R.id.retrylay);
        pro_retrybut = (ImageView) popup.findViewById(R.id.retry);


        //cat_spin = (Spinner) popup.findViewById(R.id.category_spin);
        //subcat_spin = (Spinner) popup.findViewById(R.id.sub_cat_spin);
        // pname_spin = (Spinner) popup.findViewById(R.id.pname_spin);

        pname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                searchname = s.toString().trim();
                if (searchname.length() > 0) {
                    nodata.setVisibility(View.GONE);
                    pro_retrylay.setVisibility(View.GONE);
                    productlist = new ArrayList<>();
                    pro_proglay.setVisibility(View.VISIBLE);
                    GetUsersTask task = new GetUsersTask();
                    task.execute(searchname);
                }
            }

        });


        pro_retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro_retrylay.setVisibility(View.GONE);
                pro_proglay.setVisibility(View.VISIBLE);
                productlist = new ArrayList<>();
                pro_proglay.setVisibility(View.VISIBLE);
                GetUsersTask task = new GetUsersTask();
                task.execute(searchname);

            }
        });


        pro_dialog.setContentView(popup);
        pro_dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        pro_dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pro_dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = (int) (displaymetrics.widthPixels * 1);
        int height = (int) (displaymetrics.heightPixels * 1);
        pro_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        pro_dialog.show();

        product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                productname = productlist.get(i).getPname();
                pidd = productlist.get(i).getPid();
                product.setText(productlist.get(i).getPname());
                my_price.setText(productlist.get(i).getPmrp());
                mrp.setText(productlist.get(i).getPmrp());
                stock.setText(productlist.get(i).getPstock());
                sub_total.setText(productlist.get(i).getPmrp());
                pro_dialog.dismiss();
            }
        });

        pro_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*  AddSalesPoint();*/
                pro_dialog.dismiss();
            }
        });


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
            if (menuvalue == 0)
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
        if (data.size() == 0) {
            cusidvalue = "";
            cusmob = "";
            cusname = "";
            send.setVisibility(View.GONE);
        } else {
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

                    total = data.get(i).getMyprice() * data.get(i).getQty();
                    //Log.i("totvals", data.get(i).getMyprice() * data.get(i).getQty() + "");
                }
                totalvalue = totalvalue + total;
               // Log.i("totval", totalvalue + "");
            }
        }
        double vall = totalvalue;
        grand_total.setText("Rs. " + String.format("%.2f", vall) + "");

       // Log.i("carttot", totalvalue + "");
        return vall;

    }


    private class GetUsersTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progress_lay.setVisibility(View.GONE);
          //  Log.i("GetInfoTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("pname", param[0]);
                jobj.put("type", "product");
                jobj.put("user", utils.loadName());
                result = con.sendHttpPostjson2(Appconstants.SALES, jobj, "");
              //  Log.i("productlistInput", Appconstants.SALES + "    " + jobj.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("productlist", resp + "");
            pro_proglay.setVisibility(View.GONE);
            productlist = new ArrayList<>();

            try {
                if (resp != null) {

                    JSONArray jsonArray = new JSONArray(resp);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    if (jsonObject.getString("Status").equals("Success")) {

                        if (!jsonObject.isNull("Response") && !jsonObject.getString("Response").equals("")) {

                            JSONArray jsonArray1 = jsonObject.getJSONArray("Response");
                            for (int i = 0; i < jsonArray1.length(); i++) {

                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                ProductBo productBo = new ProductBo();

                                productBo.setPid(jsonObject1.getString("pid").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("pid"));
                                productBo.setPname(jsonObject1.getString("pname").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("pname"));
                                productBo.setPmrp(jsonObject1.getString("mrp").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("mrp"));
                                productBo.setPstock(jsonObject1.getString("stock").toString().trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("stock"));

                                productlist.add(productBo);
                            }
                            PnameSpinnerAdapter adapter = new PnameSpinnerAdapter(SalesPoint.this, R.layout.sales_point_spinner_list_item, productlist);
                            product_list.setAdapter(adapter);
                            //adapter = new UserListAdapter(Users.this, R.layout.users_list_item, data);
                            //listView.setAdapter(adapter);

                        /*if (adapter == null) {
                            adapter = new PurchaseLoyalityListAdapter(PurchaseLoyality.this, R.layout.purchase_loyality__list_items, data);
                            listView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }*/
                        } else {

                            nodata.setVisibility(View.GONE);
                            pro_retrylay.setVisibility(View.GONE);

                        }

                    } else {
                        /*if (data.size() > 0) {

                            nodata.setVisibility(View.GONE);

                        } else {
                            nodata.setVisibility(View.VISIBLE);
                        }*/
                        nodata.setVisibility(View.GONE);
                        pro_retrylay.setVisibility(View.GONE);
                    }
                } else {
                    //  if (start == 0) {
                    pro_retrylay.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    // } else {
                    Toast.makeText(getApplicationContext(), "Oops! Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                    /*Something went wrong please try again.*/
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
               /* if (start == 0) {
                    retry_lay.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Reports Found.", Toast.LENGTH_SHORT).show();
                } else {*/
                Toast.makeText(getApplicationContext(), "No Reports Found.", Toast.LENGTH_SHORT).show();

                // }

            }

        }
    }

    private class CheckMobileTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("CheckMobileTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("cusid", param[0]);
                jobj.put("type", "old");

               // Log.i("check Input", Appconstants.SALES + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.SALES, jobj, "");
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
            //Log.i("mobresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        JSONObject jobj = jarr.getJSONObject(0);
                        customerid.clearFocus();
                        customer_name.setText(jobj.getString("name"));
                        mobile.setText(jobj.getString("mobile"));


                    } else {
                        customerid.requestFocus();
                        customerid.setText("");
                        customer_name.setText("");
                        mobile.setText("");

                        Toast.makeText(SalesPoint.this, "Enter valid customer id.", Toast.LENGTH_LONG).show();


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
            //Log.i("AddSalesTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("type", "sales");
                jobj.put("username", utils.loadName());
                jobj.put("user", cusname);
                jobj.put("mobile", cusmob);
                jobj.put("cusid", cusidvalue);
                jobj.put("total", totalvalue);
                JSONArray jsonArray = new JSONArray();
                if (data.size() > 0) {
                    JSONObject obj = null;
                    for (int i = 0; i < data.size(); i++) {
                        obj = new JSONObject();
                        obj.put("pid", data.get(i).getPid());
                        obj.put("price", data.get(i).getMrp());
                        obj.put("oprice", data.get(i).getMyprice());
                        obj.put("qty", data.get(i).getQty());
                        obj.put("total", data.get(i).getTotal());
                        jsonArray.put(obj);
                    }
                    jobj.put("pdetails", jsonArray);
                } else {
                    jobj.put("pdetails", jsonArray);
                }

                //Log.i("saveinput", Appconstants.SALES + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.SALES, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("mobresp", resp + "");
            stopprogress();

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        Toast.makeText(SalesPoint.this, "Successfully Save", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(SalesPoint.this, "Failed to save.", Toast.LENGTH_LONG).show();


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

        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }


}



