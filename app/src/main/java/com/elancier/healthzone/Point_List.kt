package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Pointsadap_dup
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Point_List : AppCompatActivity() {
    internal lateinit var itemsAdapter: Pointsadap_dup
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils
    var progbar: Dialog? = null
    var prog:android.app.Dialog? = null
    var otp_edit_box1: EditText? = null
    var update:Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point__list)
        utils = Utils(applicationContext)

        supportActionBar!!.title = "Reward Wallet"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()

        itemsAdapter = Pointsadap_dup(mRecyclerListitems, this, object :
            Pointsadap_dup.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int, viewType: Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo

            }
        })
        recyclerlist.adapter = itemsAdapter

        if (!utils.available_reward().isEmpty()) {
            val point =
                utils.available_reward().toString().split("\\.".toRegex(), 2).toTypedArray()[0]
            println("point$point")
            if (!point.isEmpty()) {
                val d = point.replace(",".toRegex(), "").toInt()
                if (d >= 3000) {
                    addsalary.setVisibility(View.VISIBLE)
                } else {
                    addsalary.setVisibility(View.GONE)
                }
            }
        }

        loadprogress()

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                finish()
                startActivity(Intent(this@Point_List,Point_List::class.java))
                swipeToRefresh.setRefreshing(false)
            }
        })

        addsalary.setOnClickListener{
            try {
                 update = Dialog(this@Point_List)
                update!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                update!!.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE)
                )
                val vs = layoutInflater.inflate(R.layout.point_popup, null)
                val updatebut =
                    vs.findViewById<View>(R.id.textView81) as TextView
                // laterbut=(ImageView) vs.findViewById(R.id.img);
                //TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                 otp_edit_box1 = vs.findViewById<View>(R.id.otp_edit_box1) as EditText
                otp_edit_box1!!.setText(utils.available_reward())
                val button14 =
                    vs.findViewById<View>(R.id.button14) as Button

                button14.setOnClickListener(View.OnClickListener {
                    Log.e("click", "cli")
                    if (!otp_edit_box1!!.getText().toString().isEmpty()&&BuildConfig.VERSION_CODE>=43
                    ) {
                        val task = ChangepinTask()
                        task.execute()
                        button14.isEnabled=false
                    } else {
                            if(BuildConfig.VERSION_CODE<43){
                                toast("Please update the latest version of V3 Online TV.")
                            }
                    }
                })
                update!!.setContentView(vs)
                update!!.setCancelable(true)
                update!!.show()

                // content.setText("You are using an older version of Online Tv ( version "+versionname+" ). Update now to get the latest features.");
                updatebut.setOnClickListener { // clickupdate="true";
                    update!!.dismiss()
                }
                /*laterbut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            update.dismiss();
                        }
                    });*/
            } catch (e: java.lang.Exception) {
                Log.e("er", e.toString())
                //logger.info("PerformVersionTask error" + e.getMessage());
            }
        }

       /* val reward = RewardpointsAsync()
        reward.execute()*/

    }
    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progress_lay.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "List")


                Log.i("rewardinput", Appconstants.point_request + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.point_request, jobj, "")
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

            //prog.setVisibility(View.GONE)
            recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)

            progbar!!.dismiss()

            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)

                    if(obj.getJSONObject(0).getString("Status").equals("Success")) {

                        val arrayLanguage = obj.getJSONObject(0).getJSONArray("Response")

                        for (i in 0 until arrayLanguage.length()) {
                            val JO = arrayLanguage.get(i) as JSONObject
                            val frmdate = JO.getString("uname")
                            val todate = JO.getString("status")
                            val feedback_absent = JO.getString("dtime")
                            val frombank=  JO.getString("frombank")
                            val tid=  JO.getString("tid")
                            val vdtime= JO.getString("vdtime")
                            val reject= JO.getString("reason")
                            //val feedback_absents=JO.getString("dtime")
                            if(todate=="0"){
                                addsalary.visibility=View.GONE
                            }
                            try {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid ,"", reject, ""))
                            } catch (e: Exception) {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid , "", reject, ""))
                            }
                        }
                        nodata.visibility=View.GONE
                        recyclerlist.visibility=View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()
                    }
                    else{

                        nodata.visibility=View.VISIBLE
                        recyclerlist.visibility=View.GONE
                        progbar!!.dismiss()

                    }

                } else {
                    nodata.visibility=View.VISIBLE
                    recyclerlist.visibility=View.GONE
                    progbar!!.dismiss()

                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter.notifyDataSetChanged()
                nodata.visibility=View.VISIBLE
                recyclerlist.visibility=View.GONE
                progbar!!.dismiss()

            }

        }
    }

    fun loadprogress() {
        progbar = Dialog(this)
        progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progbar!!.getWindow()!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        progbar!!.setContentView(R.layout.load)
        progbar!!.setCancelable(false)
        progbar!!.show()
    }

    inner class ChangepinTask :
        AsyncTask<String?, Void?, String?>() {
        var value = ""
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            loadprogress()
            progbar!!.show()
            //Log.i("GetInfoTask", "started");
        }

        @SuppressLint("WrongThread")
         override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val d = otp_edit_box1!!.text.toString().replace(",".toRegex(), "")
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "Add")
                jobj.put("point", d)

                Log.i(
                    "HomePage Input",
                    Appconstants.point_request + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.point_request, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("error", e.toString())
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                if (resp != null) {
                    val jsonarr = JSONArray(resp)
                    val json = jsonarr.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        //JSONObject jarr = json.getJSONObject("Response");
                        progbar!!.dismiss()
                        try {

                            toast("Requested successfully.")
                            update!!.dismiss()
                            onResume()
                        } catch (e: java.lang.Exception) {
                            progbar!!.dismiss()
                        }
                    } else {
                        progbar!!.dismiss()
                        update!!.dismiss()
                        toast(json.getString("Response"))
                    }
                } else {
                    //retry.show();
                    progbar!!.dismiss()
                    update!!.dismiss()

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("err", e.toString())
                progbar!!.dismiss()
                update!!.dismiss()


                //retry.show();
            }
        }
    }


    fun clikffed(pos: String, time: String,time1:String ,time2:String) {
        try {
            val update = Dialog(this@Point_List)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            val v = layoutInflater.inflate(R.layout.fill_point_dialog, null)

            val nofeed = v.findViewById<View>(R.id.frombank) as TextView
            val feed = v.findViewById<View>(R.id.tid) as TextView
            val feedvt =
                v.findViewById<View>(R.id.approvedate) as TextView
            val laterbut =
                v.findViewById<View>(R.id.textView87) as TextView
            nofeed.setText(time)
            feed.setText(time1)
            feedvt.setText(time2)
            update.setContentView(v)
            update.setCancelable(true)
            update.show()
            laterbut.setOnClickListener {
                update.dismiss()
            }
        } catch (e: java.lang.Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }
    }
    fun clikffed_rej(pos: String, time: String,time1:String ,time2:String) {
        try {
            val update = Dialog(this@Point_List)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            val v = layoutInflater.inflate(R.layout.fill_reject_dialog, null)

            val nofeed = v.findViewById<View>(R.id.frombank) as TextView
            val feed = v.findViewById<View>(R.id.tid) as TextView
            val feedvt =
                v.findViewById<View>(R.id.approvedate) as TextView
            val laterbut =
                v.findViewById<View>(R.id.textView87) as TextView
            nofeed.setText(time)
            feedvt.setText(time2)

            /*feed.setText(time1)
            feedvt.setText(time2)*/
            update.setContentView(v)
            update.setCancelable(true)
            update.show()
            laterbut.setOnClickListener {
                update.dismiss()
            }
        } catch (e: java.lang.Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }
    }

    fun toast(msg: String?) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
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

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        productItems!!.clear()
        mRecyclerListitems.clear()
        RewardpointsAsync().execute()
    }
}