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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Super_salhistoryadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.salarypo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import kotlinx.android.synthetic.main.supersalary_list_header.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Super_Salry_history : AppCompatActivity() {
    internal lateinit var itemsAdapter: Super_salhistoryadap
    private val mRecyclerListitems = ArrayList<Any>()
    private val statusarr = ArrayList<String>()
    private var productItems: MutableList<salarypo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils
    var intenttype="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super__salry_history)
        utils = Utils(applicationContext)

       /* supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)*/
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        val intents=intent.extras
        intenttype=intents!!.getString("frm").toString()

        if(intenttype=="normal") {
            head!!.text = "Month Request"
        }
        if(intenttype=="star") {
            head!!.text = "Super Premium Month Request"
        }
        if(intenttype=="super") {
            head!!.text = "Super Salary Achiever Month Request"
        }

        val currentapiVersion = Build.VERSION.SDK_INT

        if (currentapiVersion > 24) {
            val currentTime = Calendar.getInstance().time
            val format: DateFormat = SimpleDateFormat("dd MMM yyyy   HH:mm")
            val formats = format.format(currentTime)
            subhead.setText(utils.loadName()+" "+formats)
        } else {
            val currentTime = Calendar.getInstance().time
            subhead.setText(utils.loadName()+" "+currentTime)

        }

        menu_img.setOnClickListener {
            finish()
        }

        productItems = ArrayList()

        itemsAdapter = Super_salhistoryadap(
            mRecyclerListitems,
            this,
            Super_salhistoryadap.OnItemClickListener { view,position,viewType ->
                val item = mRecyclerListitems[position] as Rewardpointsbo
                Log.e("clickresp", "value")

                //clikffed();
            })
        recyclerlist.adapter = itemsAdapter

        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })


        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                finish()
                startActivity(
                    Intent(this@Super_Salry_history, Super_Salry_history::class.java).putExtra(
                        "frm",
                        intenttype
                    )
                )
                swipeToRefresh.setRefreshing(false)
            }
        })

        addsalary.setOnClickListener{
            val k= Intent(this@Super_Salry_history, Super_Salary::class.java)
            k.putExtra("frm", intenttype)
            startActivity(k)

        }

        val reward = RewardpointsAsync()
        reward.execute()

    }

    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            statusarr.clear()
            progress_lay.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "Select")
                if(intenttype=="normal"){
                    jobj.put("types", "0")

                }
                if(intenttype=="star"){
                    jobj.put("types", "1")

                }
                if(intenttype=="super"){
                    jobj.put("types", "2")

                }


                Log.i("super_salary", Appconstants.super_salary + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.super_salary, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                //Log.e("rewardresp", resp)
            } catch (e: Exception) {
                //Log.e("rewardrespcatch", e.toString())

            }

            //prog.setVisibility(View.GONE)
            recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONObject(resp)

                    val arrayLanguage = obj.getJSONArray("Response")

                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage.get(i) as JSONObject
                        val frmdate = JO.getString("fdate")
                        val todate = JO.getString("tdate")

                        val name = JO.getString("played")
                        val uname = JO.getString("viewed")

                        val whome = JO.getString("view_duplicate")
                        val notview_without=JO.getString("not_seen")
                        val feedback_present=JO.getString("feedback_below4")
                        val feedback_absent=JO.getString("feedback_above3")
                        val feedback_not=JO.getString("feedback_not")
                        val permission_below4=JO.getString("permission_below4")
                        val permission_above3=JO.getString("permission_above3")
                        val splayed=JO.getString("splayed")
                        val sviewed=JO.getString("sviewed")
                        val sview_duplicate=JO.getString("sview_duplicate")
                        val snot_seen=JO.getString("snot_seen")
                        val sfeedback_below4=JO.getString("sfeedback_below4")
                        val sfeedback_above3=JO.getString("sfeedback_above3")
                        val sfeedback_not=JO.getString("sfeedback_not")
                        val spermission_below4=JO.getString("spermission_below4")
                        val spermission_above3=JO.getString("spermission_above3")
                        val frombank=JO.getString("frombank");
                        val tid=JO.getString("tid");
                        val adate=JO.getString("adate");
                        val reject=JO.getString("reject");
                        val status=JO.getString("status");
                        val count=JO.getString("count");
                        val reward=JO.getString("reward");
                        statusarr.add(status)
                        //String whomename = jobject.getString("whomename");
                        //String points = jobject.getString("points");




                        try {


                            productItems!!.add(
                                salarypo(
                                    i.toString(),
                                    frmdate,
                                    todate,
                                    name,
                                    uname,
                                    whome,
                                    notview_without,
                                    feedback_present,
                                    feedback_absent,
                                    feedback_not,
                                    permission_below4,
                                    permission_above3,
                                    splayed,
                                    sviewed,
                                    sview_duplicate,
                                    snot_seen,
                                    sfeedback_below4,
                                    sfeedback_above3,
                                    sfeedback_not,
                                    spermission_below4,
                                    spermission_above3,
                                    frombank,
                                    tid,
                                    adate,
                                    reject,
                                    status,
                                    count,
                                    reward
                                )
                            )


                        } catch (e: Exception) {
                            //Log.e("rewardrespnw", e.toString())
                            productItems!!.add(
                                salarypo(
                                    i.toString(),
                                    frmdate,
                                    todate,
                                    name,
                                    uname,
                                    whome,
                                    notview_without,
                                    feedback_present,
                                    feedback_absent,
                                    feedback_not,
                                    permission_below4,
                                    permission_above3,
                                    splayed,
                                    sviewed,
                                    sview_duplicate,
                                    snot_seen,
                                    sfeedback_below4,
                                    sfeedback_above3,
                                    sfeedback_not,
                                    spermission_below4,
                                    spermission_above3,
                                    frombank,
                                    tid,
                                    adate,
                                    reject,
                                    status,
                                    count,
                                    reward
                                )
                            )


                        }


                    }
                    if(statusarr.contains("0")){
                        addsalary.visibility=View.GONE
                    }
                    else{
                        addsalary.visibility=View.VISIBLE

                    }
                    mRecyclerListitems.clear()
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter.notifyDataSetChanged()

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
            val update = Dialog(this@Super_Salry_history)
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

            if(productItems!!.get(pos.toInt()).getstatus().equals("2")){
                textView15.setText("Reject")
                textView56.setText("Reject : " + productItems!!.get(pos.toInt()).getreject())
                textView56.setTextColor(resources.getColor(R.color.red))
                textView58.visibility=View.INVISIBLE
                nofeed.visibility=View.INVISIBLE
            }
            else if(productItems!!.get(pos.toInt()).getstatus().equals("1")){
                textView15.setText("Approved")
                textView58.visibility=View.VISIBLE
                nofeed.visibility=View.VISIBLE
                textView56.setText(
                    "From bank      : " + productItems!!.get(pos.toInt()).getfrombank()
                )
                textView58.setText("Transaction Id : " + productItems!!.get(pos.toInt()).gettid())
                nofeed.setText("Approved date      : " + productItems!!.get(pos.toInt()).getadate())
            }



            ///titlename.setText("New Version 1."+Number);

            laterbut.setOnClickListener {
                update.dismiss()

            }

        } catch (e: Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }

    }

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
    }*/

}
