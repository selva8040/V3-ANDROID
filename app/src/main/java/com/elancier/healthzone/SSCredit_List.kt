package com.elancier.healthzone

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.Adapter.SS_credit_adap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.SScredits_bo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class SSCredit_List : AppCompatActivity() {
    internal lateinit var progress_lay: LinearLayout
    internal lateinit var retry_lay:LinearLayout
    internal lateinit var recyclerlist: RecyclerView
    internal lateinit var prog: ProgressBar
    internal lateinit var nodata: TextView
    internal lateinit var retry:TextView
    internal var nodatas:TextView? = null
    internal lateinit var utils: Utils
    internal lateinit var itemsAdapter: SS_credit_adap
    internal lateinit var mLayoutManager: LinearLayoutManager
    private var productItems: MutableList<SScredits_bo>? = null
    private val mRecyclerListitems = ArrayList<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_s_credit__list)

           supportActionBar!!.title = "SS Credit Benefits"
           supportActionBar!!.setDisplayHomeAsUpEnabled(true)
           supportActionBar!!.setDisplayShowHomeEnabled(true)
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)

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
            getWindow().setStatusBarColor(
                ContextCompat.getColor(
                    getApplicationContext(),
                    R.color.theme
                )
            );
        }

        itemsAdapter = SS_credit_adap(
            mRecyclerListitems,
            this,
            SS_credit_adap.OnItemClickListener { view, position, viewType ->
                val item = mRecyclerListitems.get(position) as Rewardpointsbo
                Log.e("clickresp", "value")

                //clikffed();
            })
        recyclerlist.adapter = itemsAdapter
        val reward = RewardpointsAsync()
        reward.execute()
        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })
  /*      menu_img.setOnClickListener {
            onBackPressed()
        }*/
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
                jobj.put("type", "sscredit")

                Log.i("rewardinput", Appconstants.nsp_activation + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.nsp_activation, jobj, "")
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
                        val visual_time = JO.getString("designation")
                        //String username = jobject.getString("username");
                        val name = JO.getString("month")
                        val uname = JO.getString("username")

                        val whome = JO.getString("amount")
                        val whomec = JO.getString("status")
                        val tid = JO.getString("tid")
                        val frombank = JO.getString("frombank")
                        val adtime = JO.getString("adtime")


                        //String whomename = jobject.getString("whomename");
                        //String points = jobject.getString("points");

                        productItems!!.add(
                            SScredits_bo(
                                name,
                                visual_time,
                                uname,
                                whome,
                                whomec,
                                tid,
                                adtime,
                                frombank
                            )
                        )




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

    fun clikffed(pos: Int) {
        try {
            val update = Dialog(this)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            val v = layoutInflater.inflate(R.layout.displaysupersal_popup, null)

            val textView56 = v.findViewById<View>(R.id.textView56) as TextView
            val nofeed = v.findViewById<View>(R.id.textView59) as TextView
            val textView58 = v.findViewById<View>(R.id.textView58) as TextView
            val laterbut = v.findViewById<View>(R.id.agree) as Button
            val textView15= v.findViewById<View>(R.id.textView15) as TextView
            update.setContentView(v)
            update.setCancelable(true)
            update.show()

                textView15.setText("Approved")
                textView58.visibility=View.VISIBLE
                nofeed.visibility=View.VISIBLE
                textView56.setText(
                    "From bank      : " + productItems!!.get(pos.toInt()).getbank()
                )
                textView58.setText("Transaction Id : " + productItems!!.get(pos.toInt()).gettid())
                nofeed.setText(
                    "Approved date      : " + productItems!!.get(pos.toInt()).gettransaction()
                )


            laterbut.setOnClickListener {
                update.dismiss()

            }

        } catch (e: Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
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
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
}
