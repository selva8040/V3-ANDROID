package com.elancier.healthzone;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddBinaryTreeUser extends MainView {

    EditText edit_sponser_id,edit_sponser_name,edit_upline,edit_position,edit_username,edit_name,edit_pinno,edit_store_name,edit_pin_type;
    TextView submit;
    int sponsor_check=0,username_check=0;
    LinearLayout pinlay;
    String strsponserid,strusername,strpinno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_binary_tree_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        edit_sponser_id=(EditText)findViewById(R.id.sponsorid);
        edit_sponser_name=(EditText)findViewById(R.id.sponsorname);
        edit_upline=(EditText)findViewById(R.id.upline);
        edit_position=(EditText)findViewById(R.id.position);
        edit_username=(EditText)findViewById(R.id.username);
        edit_name=(EditText)findViewById(R.id.name);
        edit_pinno=(EditText)findViewById(R.id.pinno);
        edit_store_name=(EditText)findViewById(R.id.storename);
        edit_pin_type=(EditText)findViewById(R.id.pintype);
        submit=(TextView)findViewById(R.id.submit);
        pinlay=(LinearLayout)findViewById(R.id.pinlay);
        submit.setEnabled(false);
        submit.setBackgroundResource(R.drawable.disable_but);
        edit_upline.setText(getIntent().getExtras().getString("headname"));
        edit_position.setText(getIntent().getExtras().getString("position"));
        loadprogress();

        edit_sponser_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //  final int pos = v.getId();
                    final EditText Caption = (EditText) v;
                    //Log.i("valuessss",Caption.getText().toString().trim().length()+"    "+Caption.getText().toString().trim());
                    if(Caption.getText().toString().trim().length()>0){
                        sponsor_check=0;
                        startprogress();
                        CheckSponsorTask task=new CheckSponsorTask();
                        task.execute(Caption.getText().toString().trim());
                    }
                    else{
                        edit_sponser_name.setText("");
                    }
                }
                else{
                    sponsor_check=0;
                    submit.setEnabled(false);
                    submit.setBackgroundResource(R.drawable.disable_but);
                }

            }
        });

        edit_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //  final int pos = v.getId();
                    final EditText Caption = (EditText) v;
                  //  Log.i("valuessss",Caption.getText().toString().trim().length()+"    "+Caption.getText().toString().trim());
                    if(Caption.getText().toString().trim().length()>0){
                        username_check=0;
                        startprogress();
                        CheckUsernameTask task=new CheckUsernameTask();
                        task.execute(Caption.getText().toString().trim());
                    }
                    else{
                        edit_name.setText("");
                    }
                }
                else{
                    username_check=0;
                    submit.setEnabled(false);
                    submit.setBackgroundResource(R.drawable.disable_but);
                }

            }
        });
        edit_pinno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    //  final int pos = v.getId();
                    final EditText Caption = (EditText) v;
                    //Log.i("valuessss",Caption.getText().toString().trim().length()+"    "+Caption.getText().toString().trim());
                    if(Caption.getText().toString().trim().length()>0){
                        startprogress();
                        CheckPinTypeTask task=new CheckPinTypeTask();
                        task.execute(Caption.getText().toString().trim());
                    }
                    else{
                        pinlay.setVisibility(View.GONE);
                        edit_pin_type.setText("");
                        edit_store_name.setText("");
                    }
                }


            }
        });

        edit_pinno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(edit_pinno.getText().toString().trim().length()>0) {
                       /* startprogress();
                        CheckPinTypeTask task=new CheckPinTypeTask();
                        task.execute(edit_pinno.getText().toString().trim());*/
                       edit_pinno.clearFocus();
                    }

                    return true;
                }
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_pinno.getText().toString().trim().length()>0&&edit_pin_type.getText().toString().trim().length()==0){
                    edit_pinno.clearFocus();
                    startprogress();
                    CheckPinTypeTask task=new CheckPinTypeTask();
                    task.execute(edit_pinno.getText().toString().trim());
                }
                else {
                    if (edit_sponser_id.getText().toString().trim().length() == 0) {
                        edit_sponser_id.setError("Sponsor Unique Id should not be empty.");
                    }
                    else if(sponsor_check==0){
                        edit_sponser_id.setText("");
                        edit_sponser_name.setText("");
                        edit_sponser_id.setError("Enter valid sponser id.");

                    }
                    else {
                        edit_sponser_id.setError(null);
                    }
                    if (edit_sponser_name.getText().toString().trim().length() == 0) {
                        edit_sponser_name.setError("Sponsor Name should not be empty.");
                    } else {
                        edit_sponser_name.setError(null);
                    }
                    if (edit_upline.getText().toString().trim().length() == 0) {
                        edit_upline.setError("Upline should not be empty.");
                    } else {
                        edit_upline.setError(null);
                    }
                    if (edit_position.getText().toString().trim().length() == 0) {
                        edit_position.setError("Position should not be empty.");
                    } else {
                        edit_position.setError(null);
                    }
                    if (edit_username.getText().toString().trim().length() == 0) {
                        edit_username.setError("Usename should not be empty.");
                    }
                    else if(username_check==0){
                        edit_username.setText("");
                        edit_name.setText("");
                        edit_username.setError("Enter valid username.");
                    }
                    else {
                        edit_username.setError(null);
                    }
                    if (edit_name.getText().toString().trim().length() == 0) {
                        edit_name.setError("Name should not be empty.");
                    } else {
                        edit_name.setError(null);
                    }

                   /* if (edit_pinno.getText().toString().trim().length() == 0) {
                        edit_pinno.setError("Pin No should not be empty.");
                    } else {
                        edit_pinno.setError(null);
                    }
                    if (edit_store_name.getText().toString().trim().length() == 0) {
                        edit_store_name.setError("Store Name should not be empty.");
                    } else {
                        edit_store_name.setError(null);
                    }
                    if (edit_pin_type.getText().toString().trim().length() == 0) {
                        edit_pin_type.setError("Pin Type should not be empty.");
                    } else {
                        edit_pin_type.setError(null);
                    }*/
                    if (edit_sponser_id.getText().toString().trim().length() > 0 && edit_sponser_name.getText().toString().trim().length() > 0 &&
                            edit_upline.getText().toString().trim().length() > 0 & edit_position.getText().toString().trim().length() > 0 &&
                            edit_username.getText().toString().trim().length() > 0 && edit_name.getText().toString().trim().length() > 0 &&
                            sponsor_check==1&&username_check==1) {
                        startprogress();
                        AddUserTask task=new AddUserTask();
                        task.execute();
                    }

                }
            }
        });

    }

    private class CheckSponsorTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckSponsorTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",param[0]);

                //Log.i("check Input", Appconstants.CHECK_SPONSOR+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.CHECK_SPONSOR,jobj,"");
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
                        JSONObject object=jarr.getJSONObject(0);
                        edit_sponser_id.clearFocus();
                        if(edit_username.getText().toString().trim().length()==0) {
                            edit_username.requestFocus();
                        }
                        else{
                            username_check=1;
                        }
                        strsponserid=edit_sponser_id.getText().toString().trim();
                        edit_sponser_name.setText(object.getString("name"));
                        sponsor_check=1;
                        if(sponsor_check==1&&username_check==1){
                            submit.setEnabled(true);
                            submit.setBackgroundResource(R.drawable.theme_solid_back);
                        }

                    } else {
                        sponsor_check=0;
                        edit_sponser_id.requestFocus();
                        edit_sponser_id.setText("");
                        edit_sponser_name.setText("");
                        strsponserid=edit_sponser_id.getText().toString().trim();
                        Toast.makeText(AddBinaryTreeUser.this,"Enter correct sponsor id.",Toast.LENGTH_LONG).show();


                    }
                } else {
                    sponsor_check=0;
                    edit_sponser_id.requestFocus();
                    edit_sponser_id.setText("");
                    edit_sponser_name.setText("");
                    strsponserid=edit_sponser_id.getText().toString().trim();
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                sponsor_check=0;
                edit_sponser_id.requestFocus();
                edit_sponser_id.setText("");
                edit_sponser_name.setText("");
                strsponserid=edit_sponser_id.getText().toString().trim();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class CheckUsernameTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckSponsorTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",param[0]);

               // Log.i("check Input", Appconstants.CHECK_USERNAME_ID+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.CHECK_USERNAME_ID,jobj,"");
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
                        JSONObject object=jarr.getJSONObject(0);
                        edit_username.clearFocus();
                        if(edit_pin_type.getText().toString().trim().length()==0)
                        edit_pin_type.requestFocus();
                        strusername=edit_username.getText().toString().trim();
                        edit_name.setText(object.getString("name"));
                        username_check=1;
                        if(sponsor_check==1&&username_check==1){
                            submit.setEnabled(true);
                            submit.setBackgroundResource(R.drawable.theme_solid_back);
                        }

                    } else {
                        username_check=0;
                        edit_username.requestFocus();
                        edit_username.setText("");
                        edit_name.setText("");
                        strusername=edit_username.getText().toString().trim();
                        Toast.makeText(AddBinaryTreeUser.this,"Enter correct username.",Toast.LENGTH_LONG).show();


                    }
                } else {
                    username_check=0;
                    edit_username.requestFocus();
                    edit_username.setText("");
                    edit_name.setText("");
                    strusername=edit_username.getText().toString().trim();
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                username_check=0;
                edit_username.requestFocus();
                edit_username.setText("");
                edit_name.setText("");
                strusername=edit_username.getText().toString().trim();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }


    private class CheckPinTypeTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckPinTypeTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("pinno",param[0]);

                //Log.i("check Input", Appconstants.CHECK_PIN+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.CHECK_PIN,jobj,"");
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
                        JSONObject object=jarr.getJSONObject(0);
                        edit_pinno.clearFocus();
                        //submit.requestFocus();
                        //edit_username.requestFocus();
                        strpinno=edit_pinno.getText().toString().trim();
                        edit_store_name.setText(object.getString("store"));
                        edit_pin_type.setText(object.getString("type"));
                        pinlay.setVisibility(View.VISIBLE);
                        //sponsor_check=1;
                        if(sponsor_check==1&&username_check==1){
                            submit.setEnabled(true);
                            submit.setBackgroundResource(R.drawable.theme_solid_back);
                        }

                    } else {
                       // sponsor_check=0;
                        edit_pinno.requestFocus();
                        edit_store_name.setText("");
                        edit_pin_type.setText("");
                        edit_pinno.setText("");
                        strpinno=edit_pinno.getText().toString().trim();
                        pinlay.setVisibility(View.GONE);
                        Toast.makeText(AddBinaryTreeUser.this,obj1.getString("Response"),Toast.LENGTH_LONG).show();


                    }
                } else {
                    edit_pinno.requestFocus();
                    edit_store_name.setText("");
                    edit_pin_type.setText("");
                    edit_pinno.setText("");
                    strpinno=edit_pinno.getText().toString().trim();
                    pinlay.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                edit_pinno.requestFocus();
                edit_store_name.setText("");
                edit_pin_type.setText("");
                edit_pinno.setText("");
                strpinno=edit_pinno.getText().toString().trim();
                e.printStackTrace();
                pinlay.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class AddUserTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("AddUserTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",strusername);
                jobj.put("sponsor",edit_upline.getText().toString().trim());
                jobj.put("position",edit_position.getText().toString().trim());
                jobj.put("pinno",strpinno);
                jobj.put("sponser",strsponserid);
                jobj.put("store",edit_store_name.getText().toString().trim());

              //  Log.i("check Input", Appconstants.ADD_USER+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.ADD_USER,jobj,"");
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
                        Toast.makeText(AddBinaryTreeUser.this, "Sucessfully added.", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    } else {
                        Toast.makeText(AddBinaryTreeUser.this,"Failed to add.",Toast.LENGTH_LONG).show();


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
        finish();
    }
}
