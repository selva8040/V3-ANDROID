package com.elancier.healthzone

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.*
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.salarypo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class PROM_BOX_history : AppCompatActivity() {
    internal lateinit var itemsAdapter: PRBOX_perfadap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<salarypo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super__perf_history)
        utils = Utils(applicationContext)

        supportActionBar!!.title = "Promotion Request"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()

        itemsAdapter = PRBOX_perfadap(mRecyclerListitems, this, PRBOX_perfadap.OnItemClickListener { view, position, viewType ->
            val item = mRecyclerListitems[position] as Rewardpointsbo
            Log.e("clickresp", "value")

            //clikffed();
        })
        recyclerlist.adapter = itemsAdapter

        addsalary.setOnClickListener{
            val k= Intent(this@PROM_BOX_history,Prom_BOX_FORM::class.java)
            startActivity(k)
            finish()

        }

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {

                productItems!!.clear()
                val reward = RewardpointsAsync()
                reward.execute()
                swipeToRefresh.setRefreshing(false)

                /*finish()
                startActivity(Intent(this@PROM_BOX_history,PROM_BOX_history::class.java))
                swipeToRefresh.setRefreshing(false)*/
            }
        })
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
                jobj.put("type", "list")


                Log.i("rewardinput", Appconstants.promo_box + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.promo_box, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                //Log.e("rewardresp", resp)
            } catch (e: Exception) {
                Log.e("rewardrespcatch", e.toString())

            }

            //prog.setVisibility(View.GONE)
            recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONObject(resp)

                    if(obj.getString("Status").equals("Success")) {

                        val arrayLanguage = obj.getJSONArray("Response")

                        for (i in 0 until arrayLanguage.length()) {
                            val JO = arrayLanguage.get(i) as JSONObject
                            val frmdate = JO.getString("username")
                            val todate =  JO.getString("user1")
                            val name =    JO.getString("user2")
                            val uname =   JO.getString("user3")
                            val whome =   JO.getString("status")
                            val notview_without =   JO.getString("details")
                            val feedback_present =   JO.getString("adtime")
                            val adtime =   JO.getString("adtime")

                            val feedback_absent = JO.getString("dtime")

                            if(whome=="0"){
                                addsalary.visibility=View.GONE
                            }
                            else if(whome=="2"||whome=="1"){
                                addsalary.visibility=View.VISIBLE
                                utils.savePreferences("pb_uname1","")
                                utils.savePreferences("pb_uname2","")
                                utils.savePreferences("pb_uname3","")
                                utils.savePreferences("pb_uname","")
                            }

                            try {
                                productItems!!.add(salarypo(i.toString(), frmdate, todate, name, uname, whome, notview_without, feedback_present, feedback_absent, adtime, "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "", "", "", "", "", "","",""))

                            } catch (e: Exception) {
                                //Log.e("rewardrespnw", e.toString())
                                productItems!!.add(salarypo(i.toString(), frmdate, todate, name, uname, whome, notview_without, feedback_present, feedback_absent, adtime , "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "", "", "", "", "", "","",""))
                            }
                        }
                        nodata.visibility=View.GONE
                        recyclerlist.visibility=View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()
                    }
                    else{
                        if(productItems!!.isEmpty()){
                            addsalary.visibility=View.VISIBLE
                        }

                        nodata.visibility=View.VISIBLE
                        recyclerlist.visibility=View.GONE
                    }

                } else {
                    nodata.visibility=View.VISIBLE
                    recyclerlist.visibility=View.GONE
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter.notifyDataSetChanged()
                nodata.visibility=View.VISIBLE
                recyclerlist.visibility=View.GONE
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

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        productItems!!.clear()
        val reward = RewardpointsAsync()
        reward.execute()
    }

}
