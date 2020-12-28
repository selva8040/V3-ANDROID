package com.elancier.healthzone;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.elancier.healthzone.Adapter.GeneiologyAdapter;
import com.elancier.healthzone.Common.Appconstants;
import com.elancier.healthzone.Common.Connection;
import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.GeneologyBo;
import com.elancier.healthzone.Pojo.Genosubbo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP PC on 24-07-2017.
 */

public class GeneologyTree extends Activity {

    LinearLayout treelay;
    ArrayList<GeneologyBo> geniolist;
    ArrayList<Genosubbo> gensublist;
    Dialog retry;
    ImageView retrybut;
    String username="";
    Utils utils;
    LinearLayout back;
    TextView toptext;
    Dialog progbar;
    LinearLayout menulay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genilogy_tree);
        utils=new Utils(getApplicationContext());
        treelay=(LinearLayout)findViewById(R.id.treelay);
        back=(LinearLayout)findViewById(R.id.backlay);
        toptext=(TextView)findViewById(R.id.toptext);
        menulay=(LinearLayout)findViewById(R.id.menulay);
        geniolist=new ArrayList<>();
        loadprogress();
        retry = new Dialog(this);
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE);
        retry.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View v=getLayoutInflater().inflate(R.layout.retrylay,null);
        retrybut=(ImageView)v.findViewById(R.id.retry);
        retry.setContentView(v);
        retry.setCancelable(false);
        username=utils.loadName();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        });
    }

    public void additems(final int pos){
        View convertView = getLayoutInflater().inflate(R.layout.tree_item,null);
        LinearLayout rootlay=(LinearLayout)convertView.findViewById(R.id.rootlay);
        ListView treelistvw=(ListView)convertView.findViewById(R.id.treelist);
        gensublist=new ArrayList<>();
        gensublist=geniolist.get(pos).gensublist;
        if(pos==0||pos==4||pos==8||pos==12||pos==16||pos==20||pos==24||pos==28||pos==32||pos==36||pos==40||pos==44){
            menulay.setBackgroundColor(Color.parseColor("#00662F"));
        }
        else if(pos==1||pos==5||pos==9||pos==13||pos==17||pos==21||pos==25||pos==29||pos==33||pos==37||pos==41||pos==45){
            menulay.setBackgroundColor(Color.parseColor("#F56902"));
        }
        else if(pos==2||pos==6||pos==10||pos==14||pos==18||pos==22||pos==26||pos==30||pos==34||pos==38||pos==42||pos==46){
            menulay.setBackgroundColor(Color.parseColor("#ff1d1d"));
        }
        else if(pos==3||pos==7||pos==11||pos==15||pos==19||pos==23||pos==27||pos==31||pos==35||pos==39||pos==43||pos==47){
            menulay.setBackgroundColor(Color.parseColor("#009788"));
        }
        toptext.setText(Html.fromHtml("<font><b>" +"User: "+ "</b></font>" +geniolist.get(pos).getTusername()+"<font><b>" +"    Name: "+ "</b></font>" +geniolist.get(pos).getTname()+"<font><b>" +"    Desg: "+ "</b></font>" +geniolist.get(pos).getTdesign()
                +"<font><b>" +"    IBV: "+ "</b></font>" +geniolist.get(pos).getTibv()+"<font><b>" +"    GBV: "+ "</b></font>" +geniolist.get(pos).getTgbv()));
        GeneiologyAdapter adapter=new GeneiologyAdapter(GeneologyTree.this,R.layout.tree_text,gensublist);
        treelistvw.setAdapter(adapter);
        treelistvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                username=gensublist.get(position).getTsusername();
                startprogress();
                GetInfoTask task=new GetInfoTask();
                task.execute();
            }
        });

     treelay.addView(rootlay);

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
                jobj.put("uname",username);
                //Log.i("utilsInput", Appconstants.GET_TREE+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.GET_TREE,jobj,"");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
           // Log.i("utilsresp", resp + "");
            stopprogress();


            try {
                if (resp != null) {


                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr= obj1.getJSONArray("Response");
                        JSONObject cobj=jarr.getJSONObject(0);
                        GeneologyBo gbo=new GeneologyBo();
                        gbo.setTusername(cobj.getString("username").toString().trim().equalsIgnoreCase("null") ? "" : cobj.getString("username"));
                        gbo.setTname(cobj.getString("name").toString().trim().equalsIgnoreCase("null") ? "" : cobj.getString("name"));
                        gbo.setTdesign(cobj.getString("designation").toString().trim().equalsIgnoreCase("null") ? "0" : cobj.getString("designation"));
                        gbo.setTgbv(cobj.getString("gbv").toString().trim().equalsIgnoreCase("null") ? "" : cobj.getString("gbv"));
                        gbo.setTibv(cobj.getString("ibv").toString().trim().equalsIgnoreCase("null") ? "0" : cobj.getString("ibv"));
                        ArrayList<Genosubbo> gsublist=new ArrayList<>();
                        if(!cobj.isNull("sponser")) {
                            JSONArray jarray = cobj.getJSONArray("sponser");
                            for (int i = 0; i < jarray.length(); i++) {
                                JSONObject jobj = jarray.getJSONObject(i);
                                Genosubbo bo = new Genosubbo();
                                bo.setTsusername(jobj.getString("username").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("username"));
                                bo.setTsname(jobj.getString("name").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("name"));
                                bo.setTsdesign(jobj.getString("designation").toString().trim().equalsIgnoreCase("null") ? "0" : jobj.getString("designation"));
                                bo.setTsgbv(jobj.getString("gbv").toString().trim().equalsIgnoreCase("null") ? "" : jobj.getString("gbv"));
                                bo.setTsibv(jobj.getString("ibv").toString().trim().equalsIgnoreCase("null") ? "0" : jobj.getString("ibv"));
                                gsublist.add(bo);

                            }
                            gbo.gensublist=gsublist;
                            geniolist.add(gbo);
                            treelay.removeAllViews();
                            additems(geniolist.size()-1);
                        }
                        else{
                            if(geniolist.size()>0){
                                toptext.setText(Html.fromHtml("<font><b>" +"User: "+ "</b></font>" +geniolist.get(geniolist.size()-1).getTusername()+"<font><b>" +"    Name: "+ "</b></font>" +geniolist.get(geniolist.size()-1).getTname()+"<font><b>" +"    Desg: "+ "</b></font>" +geniolist.get(geniolist.size()-1).getTdesign()
                                        +"<font><b>" +"    IBV: "+ "</b></font>" +geniolist.get(geniolist.size()-1).getTibv()+"<font><b>" +"    GBV: "+ "</b></font>" +geniolist.get(geniolist.size()-1).getTgbv()));
                            }
                            Toast.makeText(GeneologyTree.this,"No downlines found.",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

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

    public void loadprogress() {
        progbar = new Dialog(this);
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progbar.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progbar.setContentView(R.layout.load);
        progbar.setCancelable(false);
    }

    public void startprogress() {
        progbar.show();
    }

    public void stopprogress() {
        progbar.dismiss();
    }

    @Override
    public void onBackPressed() {
        if(geniolist!=null&&geniolist.size()==0||geniolist.size()==1){
            super.onBackPressed();
        }
        else{
            geniolist.remove(geniolist.size()-1);
            treelay.removeAllViews();
            additems(geniolist.size()-1);
        }
    }
}
