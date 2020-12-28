package com.elancier.healthzone

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.Adapter.Rewardfeedadap
import com.elancier.healthzone.Adapter.Rewardhistoryadap
import com.elancier.healthzone.Adapter.Rewardreduceadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.Rewardreducebo
import kotlinx.android.synthetic.main.callentry_header.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList



class Reward_reduce : AppCompatActivity() {
    internal lateinit var progress_lay: LinearLayout
    internal lateinit var retry_lay:LinearLayout
    internal lateinit var recyclerlist: RecyclerView
    internal lateinit var prog: ProgressBar
    internal lateinit var nodata: TextView
    internal lateinit var retry:TextView
    internal var nodatas:TextView? = null
    internal lateinit var utils: Utils
    internal lateinit var itemsAdapter: Rewardreduceadap
    internal lateinit var mLayoutManager: LinearLayoutManager
    private var productItems: MutableList<Rewardreducebo>? = null
    private val mRecyclerListitems = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_reduce)

     /*   supportActionBar!!.title = "V3 Online Tv"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)*/
        utils = Utils(applicationContext)
        progress_lay = findViewById(R.id.progress_lay) as LinearLayout
        retry_lay = findViewById(R.id.retry_lay) as LinearLayout
        prog = findViewById(R.id.progressBar) as ProgressBar
        recyclerlist = findViewById(R.id.recyclerlist) as RecyclerView
        recyclerlist.layoutManager = LinearLayoutManager(this)
        recyclerlist.itemAnimator = DefaultItemAnimator()
        nodata = findViewById(R.id.nodata) as TextView
        retry = findViewById(R.id.retry) as TextView
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.layoutManager = mLayoutManager

        productItems = ArrayList()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }

        itemsAdapter = Rewardreduceadap(mRecyclerListitems, this, Rewardreduceadap.OnItemClickListener { view, position, viewType ->
            val item = mRecyclerListitems.get(position) as Rewardpointsbo
            Log.e("clickresp", "value")

            //clikffed();
        })
        recyclerlist.adapter = itemsAdapter
        val reward = RewardpointsAsync()
        reward.execute()
        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })
        menu_img.setOnClickListener {
            onBackPressed()
        }
    }
    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progress_lay.visibility = View.VISIBLE
            recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())

                Log.i("rewardinput", Appconstants.REWARDREDUCE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARDREDUCE, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                //Log.e("rewardresp", resp)
            } catch (e: Exception) {
               // Log.e("rewardrespcatch", e.toString())

            }

            prog.visibility = View.GONE
            recyclerlist.visibility = View.VISIBLE
            progress_lay.visibility = View.GONE


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONObject(resp)

                    val arrayLanguage = obj.getJSONArray("Response")

                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage.get(i) as JSONObject
                        val visual_time = JO.getString("open_point")
                        //String username = jobject.getString("username");
                        val name = JO.getString("dtime")
                        val uname = JO.getString("uname")

                        val whome = JO.getString("reduced_point")
                        val whomec = JO.getString("remarks")


                        //String whomename = jobject.getString("whomename");
                        //String points = jobject.getString("points");

                        productItems!!.add(Rewardreducebo(name,visual_time,uname,whome,whomec))




                    }
                    mRecyclerListitems.clear()
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter.notifyDataSetChanged()
                    /*JSONArray jarr = new JSONArray(resp);
                for(int i=0; i<jarr.length(); i++)
                {
                    JSONObject jobj = jarr.getJSONObject(i);
                    if(jobj.getString("Status").equals("Success"))
                    {
                        JSONArray jarray = jobj.getJSONArray("Response");
                        for(int j=0; j<jarray.length(); j++)
                        {
                            Log.e("Value green","nn");

                            JSONObject jobject = jarray.getJSONObject(j);
                            //String id = jobject.getString("id");
                            //String date = jobject.getString("date");
                            Log.e("Value green","nn");

                            String visual_time = jobject.getString("visual");
                            //String username = jobject.getString("username");
                            String name = jobject.getString("dat");
                            String whome = jobject.getString("point");
                            //String whomename = jobject.getString("whomename");
                            //String points = jobject.getString("points");
                            String type = jobject.getString("status");

                            Log.e("Value green",type);




                        }
                    }
                    else
                    {
                        recyclerlist.setVisibility(View.GONE);
                        nodata.setVisibility(View.VISIBLE);
                    }
                }*/
                } else {

                    val reward = RewardpointsAsync()
                    reward.execute()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter.notifyDataSetChanged()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my_portal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        /*if (id == R.id.action_settings) {

            user_id.setEnabled(true);
            user_img.setEnabled(true);
            doj.setEnabled(true);
            designaton.setEnabled(true);
            receivable_amt.setEnabled(true);
            save.setVisibility(View.VISIBLE);

            return true;
        }*/

        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Reward_reduce, HomePage::class.java)
        startActivity(intent)
    }
}
