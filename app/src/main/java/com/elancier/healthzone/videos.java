package com.elancier.healthzone;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class videos extends AppCompatActivity {

    LinearLayout progress_lay;
    Utils utils;
    String amount="";
    String gpv="";
    String ibv="";
    String purchase="";
    String sales="";
    String target="";
    String achieve="";
    String balance="";
    String wallet_amt="";
    String todayreward="";
    String totalreward="";
    String available_reward="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        getSupportActionBar().setTitle("V3 Online TV");
        try {

        amount=getIntent().getStringExtra("amount");
         gpv=getIntent().getStringExtra("gpv");
        ibv=getIntent().getStringExtra("ibv");
        purchase=getIntent().getStringExtra("purchase");
        sales=getIntent().getStringExtra("sales");
        target=getIntent().getStringExtra("target");
        achieve=getIntent().getStringExtra("achieve");
        balance=getIntent().getStringExtra("balance");
        wallet_amt=getIntent().getStringExtra("wallet_amt");
        todayreward=getIntent().getStringExtra("todayreward");
        totalreward=getIntent().getStringExtra("totalreward");
        available_reward=getIntent().getStringExtra("available_reward");

        }
        catch (Exception e){

        }
        progress_lay = (LinearLayout) findViewById(R.id.progress_lay);
        utils = new Utils(getApplicationContext());

        VideosImage videos = new VideosImage();
        videos.execute();
    }

    public class VideosImage extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            Connection con = new Connection();
            try {
                JSONObject jobj = new JSONObject();
                jobj.put("uname", utils.loadName());

                Log.i("Videosinput", Appconstants.VIDEOVIEW + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOVIEW, jobj, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String resp) {
            try {
                //Log.e("Videosresp", resp);
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Please turn on your connection",Toast.LENGTH_LONG).show();
            }
            try
            {
                JSONObject jobj = new JSONObject(resp);
                if(jobj.getString("Status").equals("Success"))
                {
                    Intent i=new Intent(videos.this, Videoimage.class);

                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);
                }
                else if(jobj.getString("Status").equals("Failure"))
                {
                    Intent i=new Intent(videos.this, HomePage.class);
                    i.putExtra("amount",amount);
                    i.putExtra("gpv",gpv);
                    i.putExtra("ibv",ibv);
                    i.putExtra("purchase",purchase);
                    i.putExtra("sales",sales);
                    i.putExtra("target",target);
                    i.putExtra("achieve",achieve);
                    i.putExtra("balance",balance);
                    i.putExtra("wallet_amt",wallet_amt);
                    i.putExtra("todayreward",todayreward);
                    i.putExtra("totalreward",totalreward);
                    i.putExtra("available_reward",available_reward);
                    startActivity(i);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
