package com.elancier.healthzone

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elancier.healthzone.Adapter.Notificationadap
import com.elancier.healthzone.Adapter.Notificationadap.OnItemClickListener
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Notifications_List : AppCompatActivity() {
    internal lateinit var itemsAdapter: Notificationadap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications__list)
        supportActionBar!!.title = "Notifications"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)
        utils=Utils(this)
        productItems = ArrayList()

        itemsAdapter = Notificationadap(mRecyclerListitems, this, object : OnItemClickListener {
            override fun OnItemClick(view: View, position: Int, viewType: Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo
                //Log.e("clickresp", "value")

                //clikffed();
            }
        })
        recyclerlist.adapter = itemsAdapter
    }

    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progressBar.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings:String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())

                //Log.i("rewardinput", Appconstants.super_salary + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.notif, jobj, "")

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
            progressBar.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)
                    val objs = obj.getJSONObject(0)
                    val arrayLanguage = objs.getJSONArray("Response")

                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage.get(i) as JSONObject
                        val frmdate = JO.getString("title")
                        val todate = JO.getString("content")
                        val name = JO.getString("dtime")


                        try {


                            productItems!!.add(
                                Rewardpointsbo(
                                    i.toString(),
                                    frmdate,
                                    todate,
                                    name,
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
                                Rewardpointsbo(
                                    i.toString(),
                                    frmdate,
                                    todate,
                                    name,
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
                    mRecyclerListitems.clear()
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter.notifyDataSetChanged()

                    utils.savePreferences("noti", "seen")
                    utils.savePreferences("notitime", "")
                } else {

                    val reward = RewardpointsAsync()
                    reward.execute()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                //Log.e("E VALUE", e.toString())
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


        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        RewardpointsAsync().execute()
    }
}
