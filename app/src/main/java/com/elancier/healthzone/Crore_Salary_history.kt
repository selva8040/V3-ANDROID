package com.elancier.healthzone

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Star_perfadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.salarypo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Crore_Salary_history : AppCompatActivity() {
    internal lateinit var itemsAdapter: Star_perfadap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<salarypo>? = null
    internal lateinit var mLayoutManager:LinearLayoutManager
    internal lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super__perf_history)
        utils = Utils(applicationContext)

        supportActionBar!!.title = "Super Salary"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.newdashboard_gradient
            )
        )
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()

        itemsAdapter = Star_perfadap(
            mRecyclerListitems,
            this,
            Star_perfadap.OnItemClickListener { view, position, viewType ->
                val item = mRecyclerListitems[position] as Rewardpointsbo
                Log.e("clickresp", "value")

                //clikffed();
            })
        recyclerlist.adapter = itemsAdapter

        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })

        addsalary.setOnClickListener{
            val k= Intent(this@Crore_Salary_history, Crore_Salary_FORM::class.java)
            startActivity(k)

        }

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                onResume()
                //  finish()
                // startActivity(Intent(this@Crore_Salary_history,Crore_Salary_history::class.java))
                swipeToRefresh.setRefreshing(false)
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
                jobj.put("type", "List")


                Log.i("rewardinput", Appconstants.cr_supersal + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.cr_supersal, jobj, "")
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
                        val objs = obj.getString("Total Record")
                        println("Total Record : " + objs)
                        if(objs.equals("0")){
                        addsalary.visibility=View.VISIBLE
                        }
                        else if(objs.equals("1")){
                            addsalary.visibility=View.GONE

                        }
                        val arrayLanguage = obj.getJSONArray("Response")

                        for (i in 0 until arrayLanguage.length()) {
                            val JO = arrayLanguage.get(i) as JSONObject
                            val frmdate = JO.getString("uname")
                            val todate = JO.getString("user1")
                            val name = JO.getString("user2")
                            val uname = JO.getString("user3")
                            val whome = JO.getString("user4")
                            val notview_without = JO.getString("user5")
                            val feedback_present = JO.getString("user6")
                            val feedback_absent = JO.getString("dtime")
                            //val feedback_absents=JO.getString("dtime")

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
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
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
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        ""
                                    )
                                )

                            }

                        }
                        nodata.visibility=View.GONE
                        recyclerlist.visibility=View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()
                    }
                    else{
                        val objs = obj.getString("Total Record")
                        println("Total Record : " + objs)
                        if(objs.equals("0")){
                            addsalary.visibility=View.VISIBLE
                        }
                        else if(objs.equals("1")){
                            addsalary.visibility=View.GONE

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
