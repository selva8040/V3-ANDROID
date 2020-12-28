package com.elancier.healthzone;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by HP PC on 02-08-2017.
 */

public class ChangePassword extends MainView {

    EditText curpswd;
    EditText newpswd;
    EditText cnfrmpswd;
    EditText vpin;
    TextView save;
    Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pswdlay);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable d=getResources().getDrawable(R.drawable.menu_bar_bg);
        getSupportActionBar().setBackgroundDrawable(d);
        utils=new Utils(getApplicationContext());
      //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        curpswd=(EditText)findViewById(R.id.curpswd);
        newpswd=(EditText)findViewById(R.id.newpswd);
        cnfrmpswd=(EditText)findViewById(R.id.cnfrmpswd);
        vpin=(EditText)findViewById(R.id.pin);
        save=(TextView) findViewById(R.id.save);
        loadprogress();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curpswd.getText().toString().trim().length()==0){
                    curpswd.setError("Enter Current password.");
                }
                if(newpswd.getText().toString().trim().length()==0){
                    newpswd.setError("Enter New password.");
                }
                if(!newpswd.getText().toString().trim().equals(cnfrmpswd.getText().toString().trim())){
                    cnfrmpswd.setError("Password Mismatch.");
                }
                if(!vpin.getText().toString().trim().equals(vpin.getText().toString().trim())){
                    vpin.setError("Enter Your V3 Pin.");
                }
                if(curpswd.getText().toString().trim().length()>0&&newpswd.getText().toString().trim().length()>0
                        &&newpswd.getText().toString().trim().equals(cnfrmpswd.getText().toString().trim())
                        &&vpin.getText().toString().trim().equals(utils.loadmpin())){
                    startprogress();
                    ChangePasswordTask task=new ChangePasswordTask();
                    task.execute();
                }
                else{
                    if(!vpin.getText().toString().isEmpty()) {
                        if (!vpin.getText().toString().equals(utils.loadmpin())) {
                            toast("Incorrect V3 Pin");
                            vpin.setText(null);
                        }
                    }
                    else{
                        toast("Enter your v3 pin");
                        vpin.setError("Enter your v3 pin");
                    }
                }

            }
        });
    }

    private class ChangePasswordTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
           // Log.i("ChangePasswordTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",utils.loadName());
                jobj.put("old_pass",curpswd.getText().toString().trim());
                jobj.put("new_pass",newpswd.getText().toString().trim());

                //Log.i("saveinput", Appconstants.CHANGE_PASSWORD+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.CHANGE_PASSWORD,jobj,"");
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
                        Toast.makeText(ChangePassword.this,"Successfully changed.",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(ChangePassword.this,obj1.getString("Response"),Toast.LENGTH_LONG).show();


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();

            }


        }
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
