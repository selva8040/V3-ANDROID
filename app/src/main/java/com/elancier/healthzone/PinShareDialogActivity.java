package com.elancier.healthzone;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Pojo.PinShareBo;

import org.json.JSONArray;
import org.json.JSONObject;

public class PinShareDialogActivity extends AppCompatActivity {

    PinShareBo pinShareBo;
    TextView check,save;
    EditText sponcer_name,sponcer_fname;
    Dialog progbar;
    String Type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_share_dialog);

        inits();

        getBundel();

        onclick();
    }

    private void inits() {

        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
        Type = "pin_transfer";
        check = (TextView) findViewById(R.id.check);
        save = (TextView) findViewById(R.id.save);
        sponcer_name = (EditText) findViewById(R.id.sponcer_name);
        sponcer_fname = (EditText) findViewById(R.id.sponcer_fullname);
    }

    void getBundel() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle) {
            if (bundle.containsKey("SHARE_PIN")) {
                 pinShareBo = (PinShareBo) bundle.getSerializable("SHARE_PIN");
                pinShareBo.setUsername("Editest");
                try {
                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray = new JSONArray();

                    jsonObject.put("username", pinShareBo.getUsername());

                    for (String s : pinShareBo.getPin_array()) {
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("pin", s);
                        jsonArray.put(jsonObject2);
                    }

                    jsonObject.put("pinlist", jsonArray);

                    Log.d("POST JSON", jsonObject.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private void onclick() {

        check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                check.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sponcer_name.getText().toString().trim().length()>0){
                    progbar.show();
                    GetSponsername task = new GetSponsername();
                    task.execute(sponcer_name.getText().toString().trim());
                }else {
                    Toast.makeText(PinShareDialogActivity.this, "please enter username", Toast.LENGTH_SHORT).show();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sponcer_name.getText().toString().trim().length()>0){
                    progbar.show();
                    SharePinTask task = new SharePinTask();
                    task.execute(sponcer_name.getText().toString().trim(),Type);
                }else {
                    Toast.makeText(PinShareDialogActivity.this, "please enter username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private class GetSponsername extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetSponsername", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jobj = new JSONObject();
                jobj.put("sponsor", param[0]);
                jobj.put("type", "sponsor");

                Log.i("check Input", Appconstants.CORE + "    " + jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("tabresp", resp + "");

            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                       /*JSONArray jarr = obj1.getJSONArray("Response");
                        JSONObject obj=jarr.getJSONObject(0);*/
                        sponcer_fname.setText(obj1.getString("Response"));
                        check.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);



                    } else {
                        sponcer_name.setText("");
                        sponcer_fname.setText("");
                        sponcer_name.requestFocus();
                        Toast.makeText(getApplicationContext(), "Enter correct sponsor name.", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    sponcer_name.setText("");
                    sponcer_fname.setText("");
                    sponcer_name.requestFocus();
                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                sponcer_name.setText("");
                sponcer_fname.setText("");
                sponcer_name.requestFocus();
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SharePinTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("ShareTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();

                jsonObject.put("username", param[0]);
                jsonObject.put("type", param[1]);

                for (String s : pinShareBo.getPin_array()) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("pin", s);
                    jsonArray.put(jsonObject2);
                }

                jsonObject.put("pinlist", jsonArray);

                Log.d("POST JSON", jsonObject.toString());

                Log.i("check Input", Appconstants.CORE+"    "+jsonObject.toString());
                result = con.sendHttpPostjson2(Appconstants.CORE,jsonObject,"");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("tabresp", resp + "");
            if(progbar.isShowing()&&progbar!=null){
                progbar.dismiss();
            }

            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {

                        Toast.makeText(PinShareDialogActivity.this,obj1.getString("Response"),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PinShareDialogActivity.this,HomePage.class));

                    } else {

                        Toast.makeText(PinShareDialogActivity.this,"failed to share pin.",Toast.LENGTH_LONG).show();


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
}
