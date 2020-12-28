package com.elancier.healthzone

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elancier.healthzone.Adapter.*
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Pojo.salarypo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import kotlinx.android.synthetic.main.coupon_header.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class coupon_list : AppCompatActivity() {
    internal lateinit var itemsAdapter: coupon_adap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<salarypo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon__history)
        utils = Utils(applicationContext)

        /*supportActionBar!!.title = "Coupons"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)*/
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()

        itemsAdapter = coupon_adap(mRecyclerListitems, this, coupon_adap.OnItemClickListener { view, position, viewType ->
            val item = mRecyclerListitems[position] as Rewardpointsbo
            Log.e("clickresp", "value")

            //clikffed();
        })
        recyclerlist.adapter = itemsAdapter

        menu_img.setOnClickListener {
            onBackPressed()
            finish()
        }

        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })


        /*addsalary.setOnClickListener{
            val k= Intent(this@coupon_list,Super_saver::class.java)
            startActivity(k)

        }*/

        val reward = RewardpointsAsync()
        reward.execute()

        update.setOnClickListener{

            val rewards = RewardpointsUpdate()
            rewards.execute()

        }

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

               // Log.i("rewardinput", Appconstants.coupon + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.coupon, jobj, "")
            } catch (e:Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
               // Log.e("rewardresp", resp)
            } catch (e: Exception) {
               // Log.e("rewardrespcatch", e.toString())

            }

            //prog.setVisibility(View.GONE)
            recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)


                    val arrayLanguage = obj.getJSONObject(0).getJSONArray("Response")

                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage.get(i) as JSONObject
                        val frmdate = JO.getString("coupon")
                        val type = JO.getString("type")
                        /*val todate = JO.getString("user1")
                        val name = JO.getString("user2")
                        val uname = JO.getString("user3")
                        val whome = JO.getString("user4")
                        val notview_without=JO.getString("user5")
                        val feedback_present=JO.getString("user6")
                        val feedback_absent=JO.getString("dtime")*/
                        //val feedback_absents=JO.getString("dtime")



                        try {


                            productItems!!.add(salarypo(i.toString(),frmdate, type, "", "", "", "", "", "", "","",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "","","","","","","",""))


                        } catch (e: Exception) {
                            //Log.e("rewardrespnw", e.toString())
                            productItems!!.add(salarypo(i.toString(), frmdate, type, "", "", "", "", "", "", "","",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "","","","","","","",""))


                        }


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

    inner class RewardpointsUpdate : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progress_lay.setVisibility(View.VISIBLE)
            //recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "Update")

                //Log.i("rewardinput", Appconstants.coupon + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.coupon, jobj, "")
            } catch (e:Exception) {
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
         //   recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)


                    //val arrayLanguage = obj.getJSONObject(0).getJSONArray("Response")

                   // for (i in 0 until arrayLanguage.length()) {
                        val JO = obj.getJSONObject(0)



                        if(JO.getString("Status").equals("Success")){

                            val toas= Toast.makeText(applicationContext,"Request Updated",Toast.LENGTH_SHORT)
                            toas.setGravity(Gravity.CENTER,0,0)
                            toas.show()

                        }
                         else{

                            val toas= Toast.makeText(applicationContext,JO.getString("Response").toString(),Toast.LENGTH_SHORT)
                            toas.setGravity(Gravity.CENTER,0,0)
                            toas.show()
                        }
                        /*val todate = JO.getString("user1")
                        val name = JO.getString("user2")
                        val uname = JO.getString("user3")
                        val whome = JO.getString("user4")
                        val notview_without=JO.getString("user5")
                        val feedback_present=JO.getString("user6")
                        val feedback_absent=JO.getString("dtime")*/
                        //val feedback_absents=JO.getString("dtime")




                } else {

                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())

            }

        }
    }

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.coupon_portal, menu)
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
