package com.elancier.healthzone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class PrimeTreeActivity extends Activity {

    LinearLayout first_user_lay,second_user_lay,third_user_lay,fourth_user_lay,fifth_user_lay,sixth_user_lay,seventh_user_lay;
    LinearLayout first_line_lay,second_line_lay,second_emp_lay,third_emp_lay,fourth_emp_lay,fifth_emp_lay,sixth_emp_lay,seventh_emp_lay;
    LinearLayout eight_user_lay,eight_emp_lay,nine_emp_lay,nine_user_lay,ten_emp_lay,ten_user_lay,eleven_emp_lay,eleven_user_lay,twelve_emp_lay,twelve_user_lay,thirteen_emp_lay,thirteen_user_lay;
    LinearLayout secone_left_lay,second_right_lay,second_center_lay;
    ImageView first_flow_img,second_left_flow_img,second_right_flow_img;
    TextView first_name,first_lcount,second_name,second_lcount,third_name,third_lcount;
    TextView fourth_name,fourth_lcount,fifth_name,fifth_lcount,sixth_name,sixth_lcount,seventh_name,seventh_lcount;
    TextView eightth_name,eightth_lcount,nineth_name,nineth_lcount,tenth_name,tenth_lcount,eleventh_name,eleventh_lcount,twelveth_name,twelveth_lcount,thirteenth_name,thirteenth_lcount;
    String head_name="";
    Dialog detail_dialog;
    Dialog retry;
    ImageView retrybut;
    String searchnamestr="";
    LinearLayout second_flow_lay;
    String strcolor;
    Dialog progbar;
    //EditText search_edit;
    //ImageView search_but;
    LinearLayout treelay;
    String API="";
    Utils utils;
    String first_username,second_username,third_username,fourth_username,fifth_username,sixth_username,seventh_username,eightth_username,nineth_username,tenth_username,eleventh_username,twelveth_username,thirteenth_username;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_tree);
        utils=new Utils(getApplicationContext());
        first_user_lay=(LinearLayout)findViewById(R.id.firstlay);
        second_user_lay=(LinearLayout)findViewById(R.id.secondlay);
        third_user_lay=(LinearLayout)findViewById(R.id.thirdlay);
        fourth_user_lay=(LinearLayout)findViewById(R.id.fourthlay);
        fifth_user_lay=(LinearLayout)findViewById(R.id.fifthlay);
        sixth_user_lay=(LinearLayout)findViewById(R.id.sixthlay);
        seventh_user_lay=(LinearLayout)findViewById(R.id.seventhlay);
        second_emp_lay=(LinearLayout)findViewById(R.id.secondemplay);
        third_emp_lay=(LinearLayout)findViewById(R.id.thirdemplay);
        fourth_emp_lay=(LinearLayout)findViewById(R.id.fourthemplay);
        fifth_emp_lay=(LinearLayout)findViewById(R.id.fifthemplay);
        sixth_emp_lay=(LinearLayout)findViewById(R.id.sixthemplay);
        seventh_emp_lay=(LinearLayout)findViewById(R.id.seventhemplay);
        secone_left_lay=(LinearLayout)findViewById(R.id.secondleftline);
        second_right_lay=(LinearLayout)findViewById(R.id.secondrightline);
        second_center_lay=(LinearLayout)findViewById(R.id.secondcenterline);
        eight_emp_lay=(LinearLayout)findViewById(R.id.eightthemplay);
        eight_user_lay=(LinearLayout)findViewById(R.id.eightthlay);
        nine_emp_lay=(LinearLayout)findViewById(R.id.ninethemplay);
        nine_user_lay=(LinearLayout)findViewById(R.id.ninethlay);
        ten_emp_lay=(LinearLayout)findViewById(R.id.tenthemplay);
        ten_user_lay=(LinearLayout)findViewById(R.id.tenthlay);
        eleven_emp_lay=(LinearLayout)findViewById(R.id.eleventhemplay);
        eleven_user_lay=(LinearLayout)findViewById(R.id.eleventhlay);
        twelve_user_lay=(LinearLayout)findViewById(R.id.twelvethlay);
        twelve_emp_lay=(LinearLayout)findViewById(R.id.twelvethemplay);
        thirteen_emp_lay=(LinearLayout)findViewById(R.id.thirteenthemplay);
        thirteen_user_lay=(LinearLayout)findViewById(R.id.thirteenthlay);
        first_name=(TextView)findViewById(R.id.firstname);
        first_lcount=(TextView)findViewById(R.id.firstlcount);
        second_name=(TextView)findViewById(R.id.secondname);
        second_lcount=(TextView)findViewById(R.id.secondlcount);
        third_name=(TextView)findViewById(R.id.thirdname);
        third_lcount=(TextView)findViewById(R.id.thirdlcount);
        fourth_name=(TextView)findViewById(R.id.fourthname);
        fourth_lcount=(TextView)findViewById(R.id.fourthlcount);
        fifth_name=(TextView)findViewById(R.id.fifthname);
        fifth_lcount=(TextView)findViewById(R.id.fifthlcount);
        sixth_name=(TextView)findViewById(R.id.sixthname);
        sixth_lcount=(TextView)findViewById(R.id.sixthlcount);
        seventh_name=(TextView)findViewById(R.id.seventhname);
        seventh_lcount=(TextView)findViewById(R.id.seventhlcount);
        eightth_name=(TextView)findViewById(R.id.eightthname);
        eightth_lcount=(TextView)findViewById(R.id.eightthlcount);
        nineth_lcount=(TextView)findViewById(R.id.ninethlcount);
        nineth_name=(TextView)findViewById(R.id.ninethname);
        tenth_lcount=(TextView)findViewById(R.id.tenthlcount);
        tenth_name=(TextView)findViewById(R.id.tenthname);
        eleventh_lcount=(TextView)findViewById(R.id.eleventhlcount);
        eleventh_name=(TextView)findViewById(R.id.eleventhname);
        twelveth_lcount=(TextView)findViewById(R.id.twelvethlcount);
        twelveth_name=(TextView)findViewById(R.id.twelvethname);
        thirteenth_lcount=(TextView)findViewById(R.id.thirteenthlcount);
        thirteenth_name=(TextView)findViewById(R.id.thirteenthname);
        second_flow_lay=(LinearLayout)findViewById(R.id.secondflowlay);
        treelay=(LinearLayout)findViewById(R.id.treelay);
       // search_edit=(EditText) findViewById(R.id.searchtxt);
        //search_but=(ImageView) findViewById(R.id.search_img);
        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v=getLayoutInflater().inflate(R.layout.retrylay,null);
        retrybut=(ImageView)v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        if(getIntent().getExtras().getInt("flag")==1){
             API=Appconstants.FAST_TREE;
        }
        else if(getIntent().getExtras().getInt("flag")==2){
            API=Appconstants.PRIME_TREE;
        }
        else if(getIntent().getExtras().getInt("flag")==3){
             API=Appconstants.PRO_TREE;
        }
        searchnamestr=utils.loadName();
        second_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(first_username,first_name.getText().toString().trim());
            }
        });

        third_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(first_username,first_name.getText().toString().trim());
            }
        });

        fourth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(first_username,first_name.getText().toString().trim());
            }
        });
        fifth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(second_username,second_name.getText().toString().trim());
            }
        });
        sixth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(second_username,second_name.getText().toString().trim());
            }
        });
        seventh_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(second_username,second_name.getText().toString().trim());
            }
        });
        eight_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(third_username,third_name.getText().toString().trim());
            }
        });
        nine_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(third_username,third_name.getText().toString().trim());
            }
        });
        ten_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(third_username,third_name.getText().toString().trim());
            }
        });
        eleven_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(fourth_username,fourth_name.getText().toString().trim());
            }
        });
        twelve_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(fourth_username,fourth_name.getText().toString().trim());
            }
        });
        thirteen_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser(fourth_username,fourth_name.getText().toString().trim());
            }
        });

        first_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchnamestr=first_name.getText().toString().trim();
               starttree();
            }
        });
        second_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=second_name.getText().toString().trim();
                starttree();

            }
        });

        third_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=third_name.getText().toString().trim();
                starttree();

            }
        });

        fourth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=fourth_name.getText().toString().trim();
                starttree();
            }
        });

        fifth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=fifth_name.getText().toString().trim();
                starttree();
            }
        });

        sixth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=sixth_name.getText().toString().trim();
                starttree();
            }
        });

        seventh_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=seventh_name.getText().toString().trim();
                starttree();
            }
        });
        eight_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=eightth_name.getText().toString().trim();
                starttree();
            }
        });
        nine_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=nineth_name.getText().toString().trim();
                starttree();
            }
        });
        ten_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=tenth_name.getText().toString().trim();
                starttree();
            }
        });
        eleven_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=eleventh_name.getText().toString().trim();
                starttree();
            }
        });
        twelve_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=twelveth_name.getText().toString().trim();
                starttree();
            }
        });
        thirteen_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchnamestr=thirteenth_name.getText().toString().trim();
                starttree();
            }
        });

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttree();
            }
        });

        /*search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(search_edit.getText().toString().trim().length()>0) {
                        searchnamestr=search_edit.getText().toString().trim();
                        starttree();
                    }

                    return true;
                }
                return false;
            }
        });

        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_edit.getText().toString().trim().length()==0){
                    search_edit.setError("Username should not be empty");
                }
                else{
                    searchnamestr=search_edit.getText().toString().trim();
                    starttree();
                }
            }
        });*/


    }

    public void starttree(){
        treelay.setVisibility(View.GONE);
        first_user_lay.setVisibility(View.VISIBLE);
        second_user_lay.setVisibility(View.GONE);
        third_user_lay.setVisibility(View.GONE);
        fourth_user_lay.setVisibility(View.GONE);
        fifth_user_lay.setVisibility(View.GONE);
        sixth_user_lay.setVisibility(View.GONE);
        seventh_user_lay.setVisibility(View.GONE);
        second_emp_lay.setVisibility(View.VISIBLE);
        third_emp_lay.setVisibility(View.VISIBLE);
        fourth_emp_lay.setVisibility(View.GONE);
        fifth_emp_lay.setVisibility(View.GONE);
        sixth_emp_lay.setVisibility(View.GONE);
        seventh_emp_lay.setVisibility(View.GONE);
        secone_left_lay.setVisibility(View.GONE);
        second_right_lay.setVisibility(View.GONE);
        second_center_lay.setVisibility(View.GONE);
        eight_user_lay.setVisibility(View.GONE);
        eight_emp_lay.setVisibility(View.GONE);
        nine_user_lay.setVisibility(View.GONE);
        nine_emp_lay.setVisibility(View.GONE);
        ten_user_lay.setVisibility(View.GONE);
        ten_emp_lay.setVisibility(View.GONE);
        eleven_user_lay.setVisibility(View.GONE);
        eleven_emp_lay.setVisibility(View.GONE);
        twelve_user_lay.setVisibility(View.GONE);
        twelve_emp_lay.setVisibility(View.GONE);
        thirteen_user_lay.setVisibility(View.GONE);
        thirteen_emp_lay.setVisibility(View.GONE);
        retry.dismiss();
        progbar.show();
        GetInfoTask task=new GetInfoTask();
        task.execute();
    }

    public void addnewuser(String position,String uname){
        Intent intent=new Intent(PrimeTreeActivity.this,AddPrimeTreeUser.class);
        Bundle bun=new Bundle();
        bun.putString("position",position);
        bun.putString("headname",uname);
        bun.putInt("flag",getIntent().getExtras().getInt("flag"));
        intent.putExtras(bun);
        startActivity(intent);
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
                JSONObject jobj=new JSONObject();
                jobj.put("uname",searchnamestr);

               // Log.i("utilsInput", API+"    "+jobj.toString());
                result = con.sendHttpPostjson2(API,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("utilsresp", resp + "");
            progbar.dismiss();



            try {
                if (resp != null) {

                    int posvalue=0;
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        if (!obj1.isNull("Response") && obj1.getString("Response").length() > 0) {
                            treelay.setVisibility(View.VISIBLE);
                            JSONArray jarr = obj1.getJSONArray("Response");
                            for (int i = 0; i < jarr.length(); i++) {

                                JSONObject jobj = jarr.getJSONObject(i);
                                first_name.setText(jobj.getString("pqid"));
                                first_lcount.setText("Count: " + jobj.getString("count"));
                                first_username=jobj.getString("username");
                                if (!jobj.isNull("child") && jobj.getString("child").trim().length() > 0) {
                                    JSONArray childarray = jobj.getJSONArray("child");
                                    second_user_lay.setVisibility(View.GONE);
                                    second_emp_lay.setVisibility(View.VISIBLE);
                                    third_user_lay.setVisibility(View.GONE);
                                    third_emp_lay.setVisibility(View.VISIBLE);
                                    fourth_user_lay.setVisibility(View.GONE);
                                    fourth_emp_lay.setVisibility(View.VISIBLE);
                                    for (int j = 0; j < childarray.length(); j++) {
                                        JSONObject childobj = childarray.getJSONObject(j);
                                        if (j==0) {
                                            second_user_lay.setVisibility(View.VISIBLE);
                                            second_emp_lay.setVisibility(View.GONE);
                                            second_name.setText(childobj.getString("pqid"));
                                            second_lcount.setText("Count: " + childobj.getString("count"));
                                            second_username=childobj.getString("username");
                                        } else if (j==1) {
                                            third_user_lay.setVisibility(View.VISIBLE);
                                            third_emp_lay.setVisibility(View.GONE);
                                            third_name.setText(childobj.getString("pqid"));
                                            third_lcount.setText("Count: " + childobj.getString("count"));
                                            third_username=childobj.getString("username");
                                        }
                                        else if (j==2) {
                                            fourth_user_lay.setVisibility(View.VISIBLE);
                                            fourth_emp_lay.setVisibility(View.GONE);
                                            fourth_name.setText(childobj.getString("pqid"));
                                            fourth_lcount.setText("Count: " + childobj.getString("count"));
                                            fourth_username=childobj.getString("username");
                                        }
                                        if (!childobj.isNull("subchild") && childobj.getString("subchild").length() > 0) {
                                            JSONArray schildarray = childobj.getJSONArray("subchild");
                                            if (j==0) {
                                                secone_left_lay.setVisibility(View.VISIBLE);
                                                fifth_user_lay.setVisibility(View.GONE);
                                                fifth_emp_lay.setVisibility(View.VISIBLE);
                                                sixth_user_lay.setVisibility(View.GONE);
                                                sixth_emp_lay.setVisibility(View.VISIBLE);
                                                seventh_user_lay.setVisibility(View.GONE);
                                                seventh_emp_lay.setVisibility(View.VISIBLE);
                                                for (int k = 0; k < schildarray.length(); k++) {
                                                    JSONObject schobj = schildarray.getJSONObject(k);
                                                     if (k==0) {
                                                        fifth_user_lay.setVisibility(View.VISIBLE);
                                                        fifth_emp_lay.setVisibility(View.GONE);
                                                        fifth_name.setText(schobj.getString("pqid"));
                                                        fifth_lcount.setText("Count: " + schobj.getString("count"));
                                                        fifth_username=schobj.getString("username");
                                                    }
                                                    else if (k==1) {
                                                         sixth_user_lay.setVisibility(View.VISIBLE);
                                                         sixth_emp_lay.setVisibility(View.GONE);
                                                         sixth_name.setText(schobj.getString("pqid"));
                                                         sixth_lcount.setText("Count: " + schobj.getString("count"));
                                                         sixth_username=schobj.getString("username");
                                                    }
                                                    else if (k==2) {
                                                         seventh_user_lay.setVisibility(View.VISIBLE);
                                                         seventh_emp_lay.setVisibility(View.GONE);
                                                         seventh_name.setText(schobj.getString("pqid"));
                                                         seventh_lcount.setText("Count: " + schobj.getString("count"));
                                                         seventh_username=schobj.getString("username");
                                                     }

                                                }
                                            } else if (j==1) {
                                                second_center_lay.setVisibility(View.VISIBLE);
                                                eight_user_lay.setVisibility(View.GONE);
                                                eight_emp_lay.setVisibility(View.VISIBLE);
                                                nine_user_lay.setVisibility(View.GONE);
                                                nine_emp_lay.setVisibility(View.VISIBLE);
                                                ten_user_lay.setVisibility(View.GONE);
                                                ten_emp_lay.setVisibility(View.VISIBLE);
                                                for (int k = 0; k < schildarray.length(); k++) {
                                                    JSONObject schobj = schildarray.getJSONObject(k);
                                                    if (k==0) {
                                                        eight_user_lay.setVisibility(View.VISIBLE);
                                                        eight_emp_lay.setVisibility(View.GONE);
                                                        eightth_name.setText(schobj.getString("pqid"));
                                                        eightth_lcount.setText("Count: " + schobj.getString("count"));
                                                        eightth_username=schobj.getString("username");
                                                    }
                                                    else if (k==1) {
                                                        nine_user_lay.setVisibility(View.VISIBLE);
                                                        nine_emp_lay.setVisibility(View.GONE);
                                                        nineth_name.setText(schobj.getString("pqid"));
                                                        nineth_lcount.setText("Count: " + schobj.getString("count"));
                                                        nineth_username=schobj.getString("username");
                                                    }
                                                    else if (k==2) {
                                                        ten_user_lay.setVisibility(View.VISIBLE);
                                                        ten_emp_lay.setVisibility(View.GONE);
                                                        tenth_name.setText(schobj.getString("pqid"));
                                                        tenth_lcount.setText("Count: " + schobj.getString("count"));
                                                        tenth_username=schobj.getString("username");
                                                    }

                                                }
                                            }
                                            else if (j==2) {
                                                second_right_lay.setVisibility(View.VISIBLE);
                                                eleven_user_lay.setVisibility(View.GONE);
                                                eleven_emp_lay.setVisibility(View.VISIBLE);
                                                twelve_user_lay.setVisibility(View.GONE);
                                                twelve_emp_lay.setVisibility(View.VISIBLE);
                                                thirteen_user_lay.setVisibility(View.GONE);
                                                thirteen_emp_lay.setVisibility(View.VISIBLE);
                                                for (int k = 0; k < schildarray.length(); k++) {
                                                    JSONObject schobj = schildarray.getJSONObject(k);
                                                    if (k==0) {
                                                        eleven_user_lay.setVisibility(View.VISIBLE);
                                                        eleven_emp_lay.setVisibility(View.GONE);
                                                        eleventh_name.setText(schobj.getString("pqid"));
                                                        eleventh_lcount.setText("Count: " + schobj.getString("count"));
                                                        eleventh_username=schobj.getString("username");
                                                    }
                                                    else if (k==1) {
                                                        twelve_user_lay.setVisibility(View.VISIBLE);
                                                        twelve_emp_lay.setVisibility(View.GONE);
                                                        twelveth_name.setText(schobj.getString("pqid"));
                                                        twelveth_lcount.setText("Count: " + schobj.getString("count"));
                                                        twelveth_username=schobj.getString("username");
                                                    }
                                                    else if (k==2) {
                                                        thirteen_user_lay.setVisibility(View.VISIBLE);
                                                        thirteen_emp_lay.setVisibility(View.GONE);
                                                        thirteenth_name.setText(schobj.getString("pqid"));
                                                        thirteenth_lcount.setText("Count: " + schobj.getString("count"));
                                                        thirteenth_username=schobj.getString("username");
                                                    }

                                                }
                                            }

                                        } else {
                                            second_flow_lay.setVisibility(View.VISIBLE);
                                            if (j==0) {
                                                secone_left_lay.setVisibility(View.VISIBLE);
                                                fifth_emp_lay.setVisibility(View.VISIBLE);
                                                sixth_emp_lay.setVisibility(View.VISIBLE);
                                                seventh_emp_lay.setVisibility(View.VISIBLE);
                                                fifth_user_lay.setVisibility(View.GONE);
                                                sixth_user_lay.setVisibility(View.GONE);
                                                seventh_user_lay.setVisibility(View.GONE);
                                            } else if (j==1) {
                                                second_center_lay.setVisibility(View.VISIBLE);
                                                eight_emp_lay.setVisibility(View.VISIBLE);
                                                nine_emp_lay.setVisibility(View.VISIBLE);
                                                ten_emp_lay.setVisibility(View.VISIBLE);
                                                eight_user_lay.setVisibility(View.GONE);
                                                nine_user_lay.setVisibility(View.GONE);
                                                ten_user_lay.setVisibility(View.GONE);
                                            }
                                            else if (j==2) {
                                                second_right_lay.setVisibility(View.VISIBLE);
                                                eleven_emp_lay.setVisibility(View.VISIBLE);
                                                twelve_emp_lay.setVisibility(View.VISIBLE);
                                                thirteen_emp_lay.setVisibility(View.VISIBLE);
                                                eleven_user_lay.setVisibility(View.GONE);
                                                twelve_user_lay.setVisibility(View.GONE);
                                                thirteen_user_lay.setVisibility(View.GONE);
                                            }
                                        }

                                    }
                                } else {
                                    second_flow_lay.setVisibility(View.GONE);
                                    second_emp_lay.setVisibility(View.VISIBLE);
                                    third_emp_lay.setVisibility(View.VISIBLE);
                                    fourth_emp_lay.setVisibility(View.VISIBLE);
                                    second_user_lay.setVisibility(View.GONE);
                                    third_user_lay.setVisibility(View.GONE);
                                    fourth_user_lay.setVisibility(View.GONE);
                                }


                            }


                        }
                        else {
                            treelay.setVisibility(View.GONE);
                            Toast.makeText(PrimeTreeActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        treelay.setVisibility(View.GONE);
                        Toast.makeText(PrimeTreeActivity.this, "No data found.", Toast.LENGTH_SHORT).show();
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

    public String changecolor(String colorcode){
        if(colorcode.equalsIgnoreCase("Blue")){
            strcolor="#0000ff";
        }
        else if(colorcode.equalsIgnoreCase("Green")){
            strcolor="#008000";
        }
        else if(colorcode.equalsIgnoreCase("Red")){
            strcolor="#ff0000";
        }
        else{
            strcolor=colorcode;
        }
        return strcolor;
    }
    @Override
    protected void onResume() {
        super.onResume();
        starttree();

    }
}
