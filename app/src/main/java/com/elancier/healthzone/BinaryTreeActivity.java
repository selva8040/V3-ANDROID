package com.elancier.healthzone;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Pojo.BInaryTreeBo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BinaryTreeActivity extends Activity {

    LinearLayout first_user_lay,second_user_lay,third_user_lay,fourth_user_lay,fifth_user_lay,sixth_user_lay,seventh_user_lay;
    LinearLayout first_line_lay,second_line_lay,second_emp_lay,third_emp_lay,fourth_emp_lay,fifth_emp_lay,sixth_emp_lay,seventh_emp_lay;
    LinearLayout secone_left_lay,second_right_lay;
    ImageView first_flow_img,second_left_flow_img,second_right_flow_img;
    TextView first_name,first_lcount,first_rcount,second_name,second_lcount,second_rcount,third_name,third_lcount,third_rcount;
    TextView fourth_name,fourth_lcount,fourth_rcount,fifth_name,fifth_lcount,fifth_rcount,sixth_name,sixth_lcount,sixth_rcount,seventh_name,seventh_lcount,seventh_rcount;
    String head_name="";
    Dialog detail_dialog;
    ArrayList<BInaryTreeBo> binarytreelist;
    Dialog retry;
    ImageView retrybut;
    String searchnamestr="";
    LinearLayout second_flow_lay;
    String strcolor;
    Dialog progbar;
    EditText search_edit;
    ImageView search_but;
    LinearLayout treelay;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_binary_tree);
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
        first_name=(TextView)findViewById(R.id.firstname);
        first_lcount=(TextView)findViewById(R.id.firstlcount);
        first_rcount=(TextView)findViewById(R.id.firstrcount);
        second_name=(TextView)findViewById(R.id.secondname);
        second_lcount=(TextView)findViewById(R.id.secondlcount);
        second_rcount=(TextView)findViewById(R.id.secondrcount);
        third_name=(TextView)findViewById(R.id.thirdname);
        third_lcount=(TextView)findViewById(R.id.thirdlcount);
        third_rcount=(TextView)findViewById(R.id.thirdrcount);
        fourth_name=(TextView)findViewById(R.id.fourthname);
        fourth_lcount=(TextView)findViewById(R.id.fourthlcount);
        fourth_rcount=(TextView)findViewById(R.id.fourthrcount);
        fifth_name=(TextView)findViewById(R.id.fifthname);
        fifth_lcount=(TextView)findViewById(R.id.fifthlcount);
        fifth_rcount=(TextView)findViewById(R.id.fifthrcount);
        sixth_name=(TextView)findViewById(R.id.sixthname);
        sixth_lcount=(TextView)findViewById(R.id.sixthlcount);
        sixth_rcount=(TextView)findViewById(R.id.sixthrcount);
        seventh_name=(TextView)findViewById(R.id.seventhname);
        seventh_lcount=(TextView)findViewById(R.id.seventhlcount);
        seventh_rcount=(TextView)findViewById(R.id.seventhrcount);
        second_flow_lay=(LinearLayout)findViewById(R.id.secondflowlay);
        treelay=(LinearLayout)findViewById(R.id.treelay);
        search_edit=(EditText) findViewById(R.id.searchtxt);
        search_but=(ImageView) findViewById(R.id.search_img);
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
        searchnamestr=getIntent().getExtras().getString("name");
        second_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("Left",first_name.getText().toString().trim());
            }
        });

        third_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("Right",first_name.getText().toString().trim());
            }
        });

        fourth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("Left",second_name.getText().toString().trim());
            }
        });
        fifth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("Right",second_name.getText().toString().trim());
            }
        });
        sixth_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("Left",third_name.getText().toString().trim());
            }
        });
        seventh_emp_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addnewuser("right",third_name.getText().toString().trim());
            }
        });

        first_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(0);
            }
        });
        second_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(1);
            }
        });

        third_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(2);
            }
        });

        fourth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(3);
            }
        });

        fifth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(4);
            }
        });

        sixth_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(5);
            }
        });

        seventh_user_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdetaildialog(6);
            }
        });

        retrybut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttree();
            }
        });

        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        });


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
        retry.dismiss();
        progbar.show();
        GetInfoTask task=new GetInfoTask();
        task.execute();
    }

    public void addnewuser(String position,String uname){
        Intent intent=new Intent(BinaryTreeActivity.this,AddBinaryTreeUser.class);
        Bundle bun=new Bundle();
        bun.putString("position",position);
        bun.putString("headname",uname);
        intent.putExtras(bun);
        startActivity(intent);
    }

    public void showdetaildialog(final int value){
        detail_dialog = new Dialog(this);
        detail_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        detail_dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v=getLayoutInflater().inflate(R.layout.sponsor_dialog,null);
        LinearLayout cancel=(LinearLayout) v.findViewById(R.id.cancel);
        TextView sponsername=(TextView)v.findViewById(R.id.sponsorname);
        TextView username=(TextView)v.findViewById(R.id.username);
        TextView name=(TextView)v.findViewById(R.id.name);
        TextView totleftpoint=(TextView)v.findViewById(R.id.totleftpoint);
        TextView totrightpoint=(TextView)v.findViewById(R.id.totrightpoint);
        TextView leftpoint=(TextView)v.findViewById(R.id.leftpoint);
        TextView rightpoint=(TextView)v.findViewById(R.id.rightpoint);
        TextView pintype=(TextView)v.findViewById(R.id.pintype);
        TextView doa=(TextView)v.findViewById(R.id.doa);
        TextView title=(TextView)v.findViewById(R.id.titlename);
        ImageView usericon=(ImageView)v.findViewById(R.id.user_img);
        for(int i=0;i<binarytreelist.size();i++){
            if(binarytreelist.get(i).getChildposition()==value){
                sponsername.setText(binarytreelist.get(i).getSponsor());
                username.setText(binarytreelist.get(i).getUsername());
                name.setText(binarytreelist.get(i).getName());
                totleftpoint.setText(binarytreelist.get(i).getTotleftpoint());
                totrightpoint.setText(binarytreelist.get(i).getTotrightpoint());
                leftpoint.setText(binarytreelist.get(i).getLeftpoint());
                rightpoint.setText(binarytreelist.get(i).getRightpoint());
                pintype.setText(binarytreelist.get(i).getPintype());
                doa.setText(binarytreelist.get(i).getDoa());
                title.setText(binarytreelist.get(i).getUsername());
                searchnamestr=binarytreelist.get(i).getUsername();
            }
        }

        detail_dialog.setContentView(v);
        detail_dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_dialog.dismiss();
            }
        });

        usericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail_dialog.dismiss();
                starttree();
            }
        });
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

              //  Log.i("utilsInput", Appconstants.BINARY_TREE+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.BINARY_TREE,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            //Log.i("utilsresp", resp + "");
            progbar.dismiss();


           binarytreelist=new ArrayList<>();
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
                                BInaryTreeBo bo = new BInaryTreeBo();
                                bo.setChildposition(0);
                                bo.setUsername(jobj.getString("username"));
                                bo.setName(jobj.getString("name"));
                                bo.setSponsor(jobj.getString("sponser"));
                                bo.setTotleftpoint(jobj.getString("total_left_point"));
                                bo.setTotrightpoint(jobj.getString("total_right_point"));
                                bo.setLeftpoint(jobj.getString("left_point"));
                                bo.setRightpoint(jobj.getString("right_point"));
                                bo.setLcount(jobj.getString("lcount"));
                                bo.setRcount(jobj.getString("rcount"));
                                bo.setColour(jobj.getString("color"));
                                bo.setPintype(jobj.getString("pintype"));
                                bo.setDoa(jobj.getString("doa"));
                                binarytreelist.add(bo);
                                first_name.setText(jobj.getString("username"));
                                first_lcount.setText("LCount: " + jobj.getString("lcount"));
                                first_rcount.setText("RCount: " + jobj.getString("rcount"));
                                GradientDrawable drawable = (GradientDrawable) first_user_lay.getBackground();
                                drawable.setColor(Color.parseColor(changecolor(jobj.getString("color"))));
                                if (!jobj.isNull("child") && jobj.getString("child").trim().length() > 0) {
                                    JSONArray childarray = jobj.getJSONArray("child");
                                    for (int j = 0; j < childarray.length(); j++) {
                                        JSONObject childobj = childarray.getJSONObject(j);
                                        BInaryTreeBo chbo = new BInaryTreeBo();
                                        if (childobj.getString("position").equalsIgnoreCase("Left")) {
                                            second_user_lay.setVisibility(View.VISIBLE);
                                            second_emp_lay.setVisibility(View.GONE);
                                            second_name.setText(childobj.getString("username"));
                                            second_lcount.setText("LCount: " + childobj.getString("lcount"));
                                            second_rcount.setText("RCount: " + childobj.getString("rcount"));
                                            GradientDrawable drawable1 = (GradientDrawable) second_user_lay.getBackground();
                                            drawable1.setColor(Color.parseColor(changecolor(childobj.getString("color"))));
                                            chbo.setChildposition(1);
                                        } else if (childobj.getString("position").equalsIgnoreCase("Right")) {
                                            third_user_lay.setVisibility(View.VISIBLE);
                                            third_emp_lay.setVisibility(View.GONE);
                                            third_name.setText(childobj.getString("username"));
                                            third_lcount.setText("LCount: " + childobj.getString("lcount"));
                                            third_rcount.setText("RCount: " + childobj.getString("rcount"));
                                            GradientDrawable drawable2 = (GradientDrawable) third_user_lay.getBackground();
                                            drawable2.setColor(Color.parseColor(changecolor(childobj.getString("color"))));
                                            chbo.setChildposition(2);
                                        }
                                        chbo.setUsername(childobj.getString("username"));
                                        chbo.setName(childobj.getString("name"));
                                        chbo.setSponsor(childobj.getString("sponser"));
                                        chbo.setTotleftpoint(childobj.getString("total_left_point"));
                                        chbo.setTotrightpoint(childobj.getString("total_right_point"));
                                        chbo.setLeftpoint(childobj.getString("left_point"));
                                        chbo.setRightpoint(childobj.getString("right_point"));
                                        chbo.setLcount(childobj.getString("lcount"));
                                        chbo.setRcount(childobj.getString("rcount"));
                                        chbo.setColour(childobj.getString("color"));
                                        chbo.setPintype(childobj.getString("pintype"));
                                        chbo.setDoa(childobj.getString("doa"));
                                        chbo.setPosition(childobj.getString("position"));
                                        binarytreelist.add(chbo);
                                        if (!childobj.isNull("subchild") && childobj.getString("subchild").length() > 0) {
                                            JSONArray schildarray = childobj.getJSONArray("subchild");
                                            if (chbo.getPosition().equalsIgnoreCase("Left")) {
                                                secone_left_lay.setVisibility(View.VISIBLE);
                                                fourth_user_lay.setVisibility(View.GONE);
                                                fourth_emp_lay.setVisibility(View.VISIBLE);
                                                fifth_user_lay.setVisibility(View.GONE);
                                                fifth_emp_lay.setVisibility(View.VISIBLE);
                                                for (int k = 0; k < schildarray.length(); k++) {
                                                    JSONObject schobj = schildarray.getJSONObject(k);
                                                    BInaryTreeBo schbo = new BInaryTreeBo();
                                                    //Log.i("utilssposss", childobj.getString("position"));
                                                    if (schobj.getString("position").equalsIgnoreCase("Left")) {
                                                        fourth_user_lay.setVisibility(View.VISIBLE);
                                                        fourth_emp_lay.setVisibility(View.GONE);
                                                        fourth_name.setText(schobj.getString("username"));
                                                        fourth_lcount.setText("LCount: " + schobj.getString("lcount"));
                                                        fourth_rcount.setText("RCount: " + schobj.getString("rcount"));
                                                        GradientDrawable drawable1 = (GradientDrawable) fourth_user_lay.getBackground();
                                                        drawable1.setColor(Color.parseColor(changecolor(schobj.getString("color"))));
                                                        schbo.setChildposition(3);
                                                    } else if (schobj.getString("position").equalsIgnoreCase("Right")) {
                                                        fifth_user_lay.setVisibility(View.VISIBLE);
                                                        fifth_emp_lay.setVisibility(View.GONE);
                                                        fifth_name.setText(schobj.getString("username"));
                                                        fifth_lcount.setText("LCount: " + schobj.getString("lcount"));
                                                        fifth_rcount.setText("RCount: " + schobj.getString("rcount"));
                                                        GradientDrawable drawable2 = (GradientDrawable) fifth_user_lay.getBackground();
                                                        drawable2.setColor(Color.parseColor(changecolor(schobj.getString("color"))));
                                                        schbo.setChildposition(4);
                                                    }
                                                    schbo.setUsername(schobj.getString("username"));
                                                    schbo.setName(schobj.getString("name"));
                                                    schbo.setSponsor(schobj.getString("sponser"));
                                                    schbo.setTotleftpoint(schobj.getString("total_left_point"));
                                                    schbo.setTotrightpoint(schobj.getString("total_right_point"));
                                                    schbo.setLeftpoint(schobj.getString("left_point"));
                                                    schbo.setRightpoint(schobj.getString("right_point"));
                                                    schbo.setLcount(schobj.getString("lcount"));
                                                    schbo.setRcount(schobj.getString("rcount"));
                                                    schbo.setColour(schobj.getString("color"));
                                                    schbo.setPintype(schobj.getString("pintype"));
                                                    schbo.setDoa(schobj.getString("doa"));
                                                    schbo.setPosition(schobj.getString("position"));
                                                    binarytreelist.add(schbo);
                                                }
                                            } else if (chbo.getPosition().equalsIgnoreCase("Right")) {
                                                second_right_lay.setVisibility(View.VISIBLE);
                                                sixth_user_lay.setVisibility(View.GONE);
                                                sixth_emp_lay.setVisibility(View.VISIBLE);
                                                seventh_user_lay.setVisibility(View.GONE);
                                                seventh_emp_lay.setVisibility(View.VISIBLE);
                                                for (int k = 0; k < schildarray.length(); k++) {
                                                    JSONObject schobj = schildarray.getJSONObject(k);
                                                    BInaryTreeBo schbo = new BInaryTreeBo();
                                                    //Log.i("utilssposss", childobj.getString("position"));
                                                    if (schobj.getString("position").equalsIgnoreCase("Left")) {
                                                        sixth_user_lay.setVisibility(View.VISIBLE);
                                                        sixth_emp_lay.setVisibility(View.GONE);
                                                        sixth_name.setText(schobj.getString("username"));
                                                        sixth_lcount.setText("LCount: " + schobj.getString("lcount"));
                                                        sixth_rcount.setText("RCount: " + schobj.getString("rcount"));
                                                        GradientDrawable drawable1 = (GradientDrawable) sixth_user_lay.getBackground();
                                                        drawable1.setColor(Color.parseColor(changecolor(schobj.getString("color"))));
                                                        schbo.setChildposition(5);
                                                    } else if (schobj.getString("position").equalsIgnoreCase("Right")) {
                                                        seventh_user_lay.setVisibility(View.VISIBLE);
                                                        seventh_emp_lay.setVisibility(View.GONE);
                                                        seventh_name.setText(schobj.getString("username"));
                                                        seventh_lcount.setText("LCount: " + schobj.getString("lcount"));
                                                        seventh_rcount.setText("RCount: " + schobj.getString("rcount"));
                                                        GradientDrawable drawable2 = (GradientDrawable) seventh_user_lay.getBackground();
                                                        drawable2.setColor(Color.parseColor(changecolor(schobj.getString("color"))));
                                                        schbo.setChildposition(6);
                                                    }
                                                    schbo.setUsername(schobj.getString("username"));
                                                    schbo.setName(schobj.getString("name"));
                                                    schbo.setSponsor(schobj.getString("sponser"));
                                                    schbo.setTotleftpoint(schobj.getString("total_left_point"));
                                                    schbo.setTotrightpoint(schobj.getString("total_right_point"));
                                                    schbo.setLeftpoint(schobj.getString("left_point"));
                                                    schbo.setRightpoint(schobj.getString("right_point"));
                                                    schbo.setLcount(schobj.getString("lcount"));
                                                    schbo.setRcount(schobj.getString("rcount"));
                                                    schbo.setColour(schobj.getString("color"));
                                                    schbo.setPintype(schobj.getString("pintype"));
                                                    schbo.setDoa(schobj.getString("doa"));
                                                    schbo.setPosition(schobj.getString("position"));
                                                    binarytreelist.add(schbo);
                                                }
                                            }

                                        } else {
                                            //Log.i("utilsspositionval",chbo.getPosition()+"  posss");
                                            second_flow_lay.setVisibility(View.VISIBLE);
                                            if (chbo.getPosition().equalsIgnoreCase("Left")) {
                                                secone_left_lay.setVisibility(View.VISIBLE);
                                                fourth_emp_lay.setVisibility(View.VISIBLE);
                                                fifth_emp_lay.setVisibility(View.VISIBLE);
                                                fourth_user_lay.setVisibility(View.GONE);
                                                fifth_user_lay.setVisibility(View.GONE);
                                            } else if (chbo.getPosition().equalsIgnoreCase("Right")) {
                                                second_right_lay.setVisibility(View.VISIBLE);
                                                sixth_emp_lay.setVisibility(View.VISIBLE);
                                                seventh_emp_lay.setVisibility(View.VISIBLE);
                                                sixth_user_lay.setVisibility(View.GONE);
                                                seventh_user_lay.setVisibility(View.GONE);
                                            }
                                        }

                                    }
                                } else {
                                    second_flow_lay.setVisibility(View.GONE);
                                    second_emp_lay.setVisibility(View.VISIBLE);
                                    third_emp_lay.setVisibility(View.VISIBLE);
                                    second_user_lay.setVisibility(View.GONE);
                                    third_user_lay.setVisibility(View.GONE);
                                }


                            }


                        }
                        else {
                            treelay.setVisibility(View.GONE);
                            Toast.makeText(BinaryTreeActivity.this, "Enter valid user name.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                       treelay.setVisibility(View.GONE);
                        Toast.makeText(BinaryTreeActivity.this, "Enter valid user name.", Toast.LENGTH_SHORT).show();
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
