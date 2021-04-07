package com.elancier.healthzone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.contacts.Contact;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    TextView login,signup,forgetpass;
    EditText username,password;
    int tag=1;

    Dialog progbar;
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=1;
    ArrayList<Contact> listContacts;
    Utils utils;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 100;
    ImageView pswdvisible;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        utils=new Utils(getApplicationContext());
        listContacts=new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.theme));
        }

        init();
        onclick();

        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);


    }

    private void init()
    {
        pswdvisible=findViewById(R.id.imageView25);

        login = (TextView)findViewById(R.id.login);
        signup = (TextView)findViewById(R.id.reg);
        forgetpass = (TextView)findViewById(R.id.forgetpass);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

    }

    private void onclick() {

        pswdvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tag==1)
                {

                    tag=0;
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                    pswdvisible.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye));

                }
                else
                {
                    tag=1;
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().toString().length());
                    pswdvisible.setImageDrawable(getResources().getDrawable(R.drawable.ic_eye_not));
                }

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().length() <= 0) {
                    username.setError("Enter Username");
                }

                if (password.getText().toString().length()  <= 0) {
                    password.setError("Enter Password");
                }
                if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {

                    progbar.show();
                    LoginTask task = new LoginTask();
                    task.execute(username.getText().toString().trim(), password.getText().toString().trim());

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, NewSignup.class);
                intent.putExtra("frm","login");
                startActivity(intent);

            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Forgot_Activity.class);
                startActivity(intent);

            }
        });

    }

    protected void checkPermission(){
        if(/*ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                + */ContextCompat.checkSelfPermission(
                getApplicationContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(/*ActivityCompat.shouldShowRequestPermissionRationale(
                    Login.this,Manifest.permission.CAMERA)

                    || */ActivityCompat.shouldShowRequestPermissionRationale(
                    Login.this,Manifest.permission.READ_EXTERNAL_STORAGE)
            )
            {
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Login.this);
                builder.setMessage("Read External Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                Login.this,
                                new String[]{
                                        /*Manifest.permission.CAMERA,*/
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        /*Manifest.permission.ACCESS_FINE_LOCATION*/
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        Login.this,
                        new String[]{
                                /*Manifest.permission.CAMERA,*/
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                /*Manifest.permission.ACCESS_FINE_LOCATION*/
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            // Toast.makeText(WelcomeActivity.this,"Permissions already granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        /*+ grantResults[1]
                                        + grantResults[2]*/
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                }

                else {
                }

                return;
            }
        }
    }

    public boolean checkPermission2()
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.READ_CONTACTS)) {
                    android.app.AlertDialog.Builder alertBuilder = new android.app.AlertDialog.Builder(this);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("To allow required permission");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                        }
                    });
                    android.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                 }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }


    private class SaveContactstask extends AsyncTask<String, String ,String> {


        protected void onPreExecute() {

            Log.i("SaveContactstask", "started");
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(final String... arg0) {
            String resp = null;

            Connection con=new Connection();
            try{
                JSONObject obj=new JSONObject();
                obj.put("owner",username.getText().toString().trim());
                JSONArray jarr=new JSONArray();
                for(int i=0;i<listContacts.size();i++){
                    JSONObject jobj=new JSONObject();
                    jobj.put("name",listContacts.get(i).name);
                    if (listContacts.get(i).numbers.size() > 0 && listContacts.get(i).numbers.get(0) != null) {
                        jobj.put("mobile",listContacts.get(i).numbers.get(0).number);
                    }
                    else{
                        jobj.put("mobile","");
                    }
                    jarr.put(jobj);

                }
                obj.put("details",jarr);
                resp = con.sendHttpPostjson2(Appconstants.SAVE_CONTACTS,obj,"");
                //Log.i("utilssinput",obj.toString());
                return resp;
            } catch (Exception uee) {
               // Log.i("respppppppppppppp", uee.getMessage()+"");
                // uee.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String resp) {
            //Log.i("user resp",resp+"");


            if(resp!=null) {
                try {
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj=json.getJSONObject(0);
                    if(obj.getString("Status").equals("Success")){
                        utils.savePreferences("contact",1+"");
                    }
                    LoginTask task = new LoginTask();
                    task.execute(username.getText().toString().trim(), password.getText().toString().trim());

                } catch (Exception e) {
                    e.printStackTrace();
                    progbar.dismiss();

                    Toast.makeText(Login.this,"Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                progbar.dismiss();
                Toast.makeText(Login.this,"Please check your internet connection and try again",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("LoginTask", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();
            String token="";
            String deviceId = Settings.Secure.getString(Login.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            try {
                token= FirebaseInstanceId.getInstance().getToken().toString();
            }
            catch (Exception e){

            }
            System.out.println("token"+token);
            try {
                JSONObject jobj=new JSONObject();
                jobj.put("uname",param[0]);
                jobj.put("password",param[1]);
                jobj.put("deviceid",deviceId);
                jobj.put("token",token);
                //Log.e("devid",deviceId);

                Log.i("checkInput", Appconstants.LOGIN_API+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.LOGIN_API,jobj,"");

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
                        JSONArray jarr= obj1.getJSONArray("Response");
                        JSONObject jobj=jarr.getJSONObject(0);
                        utils.savePreferences("id",jobj.getString("id"));
                        utils.savePreferences("name",jobj.getString("username"));
                        utils.savePreferences("image",jobj.getString("image"));
                        utils.savePreferences("doj",jobj.getString("doj"));
                        utils.savePreferences("mobile",jobj.getString("mobile"));
                        utils.savePreferences("fname",jobj.getString("firstname"));
                        utils.savePreferences("type",jobj.getString("type"));
                        utils.savePreferences("lname",jobj.getString("lastname"));
                        utils.savePreferences("country",jobj.getString("country"));
                        utils.savePreferences("language",jobj.getString("language"));
                        /*utils.savePreferences("purchase",jobj.getString("purchase"));
                        utils.savePreferences("sales",jobj.getString("sales"));*/

                        Toast.makeText(Login.this,"Login Successfully.",Toast.LENGTH_LONG).show();
                            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                            int currentapiVersions = android.os.Build.VERSION.SDK_INT;


                            if (currentapiVersion < 30) {
                                utils.savePreferences("jsonobj","");
                                Intent i = new Intent(Login.this,Tableview.class);
                                startActivity(i);
                            }
                            else{
                                Intent i = new Intent(Login.this,Tableview.class);
                                startActivity(i);
                            }

                    } else {
                        username.setText("");
                        username.requestFocus();
                        password.setText("");
                        Toast.makeText(Login.this,"Invalid Credentials.",Toast.LENGTH_LONG).show();


                    }
                } else {
                     Toast.makeText(getApplicationContext(), "Error Response.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

            }


        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
