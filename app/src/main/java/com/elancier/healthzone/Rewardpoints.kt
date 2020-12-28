package com.elancier.healthzone

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.RewardpointsAdapter
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CheckNetwork
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_reward_history.*
import kotlinx.android.synthetic.main.activity_reward_history.swipeToRefresh
import kotlinx.android.synthetic.main.activity_rewardpoints.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Rewardpoints : AppCompatActivity() {
    var progress_lay: LinearLayout? = null
    var retry_lay: LinearLayout? = null
    var recyclerlist: RecyclerView? = null
    var recyclerlist1: RecyclerView? = null
    var nodata: TextView? = null
    var retry: TextView? = null
    var utils: Utils? = null
    var searchval = ""
    var itemsAdapter: RewardpointsAdapter? = null
    private var loading = true
    private var loading1 = true
    var pastVisiblesItems = 0
    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    var submit=""

    var pastVisiblesItems1 = 0
    var visibleItemCount1:Int = 0
    var totalItemCount1:Int = 0
    var start:Int = 0
    var limit:Int = 10

    var startsearch:Int = 0
    var limitsearch:Int = 10
    private val mRecyclerListitems: MutableList<Any> = ArrayList()
    private val mRecyclerListitems1: MutableList<Any> = ArrayList()
    private var productItems: MutableList<Rewardpointsbo>? = null
    var itemsAdapter1: RewardpointsAdapter? = null
    private var productItems1: MutableList<Rewardpointsbo>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rewardpoints)
        supportActionBar!!.title = "Reward Points"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        utils = Utils(applicationContext)
        progress_lay = findViewById<View>(R.id.progress_lay) as LinearLayout
        retry_lay = findViewById<View>(R.id.retry_lay) as LinearLayout
        recyclerlist = findViewById<View>(R.id.recyclerlist) as RecyclerView
        recyclerlist!!.layoutManager = LinearLayoutManager(this)
        recyclerlist!!.itemAnimator = DefaultItemAnimator()

        recyclerlist1 = findViewById<View>(R.id.recyclerlist1) as RecyclerView
        recyclerlist1!!.layoutManager = LinearLayoutManager(this)
        recyclerlist1!!.itemAnimator = DefaultItemAnimator()
        nodata = findViewById<View>(R.id.nodata) as TextView
        retry = findViewById<View>(R.id.retry) as TextView
        if (CheckNetwork.isInternetAvailable(this@Rewardpoints)) {
            val reward: RewardpointsAsync = RewardpointsAsync()
            reward.execute()
        } else {
            swipeToRefresh!!.visibility = View.GONE
            retry_lay!!.visibility = View.VISIBLE
        }
        retry!!.setOnClickListener {
            if (CheckNetwork.isInternetAvailable(this@Rewardpoints)) {
                swipeToRefresh!!.visibility = View.VISIBLE
                retry_lay!!.visibility = View.GONE
                val reward: RewardpointsAsync = RewardpointsAsync()
                reward.execute()
            } else {
                swipeToRefresh!!.visibility = View.GONE
                retry_lay!!.visibility = View.VISIBLE
            }
        }



        itemsAdapter1 = RewardpointsAdapter(
            mRecyclerListitems1,
            applicationContext
        ) { view, position, viewType -> val item = mRecyclerListitems1[position] as Rewardpointsbo }
        recyclerlist1!!.adapter = itemsAdapter1

        itemsAdapter = RewardpointsAdapter(
            mRecyclerListitems,
            applicationContext
        ) { view, position, viewType -> val item = mRecyclerListitems[position] as Rewardpointsbo }
        recyclerlist!!.adapter = itemsAdapter


        val mLayoutManager: LinearLayoutManager
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist!!.setLayoutManager(mLayoutManager)

        val mLayoutManager1: LinearLayoutManager
        mLayoutManager1 = LinearLayoutManager(this)
        recyclerlist1!!.setLayoutManager(mLayoutManager1)

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                if(searchval.isEmpty()) {
                    finish()
                    startActivity(Intent(this@Rewardpoints, Rewardpoints::class.java))
                    swipeToRefresh.setRefreshing(false)
                }
                else{

                }
            }
        })

        recyclerlist!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.getChildCount()
                    totalItemCount = mLayoutManager.getItemCount()
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    Log.v("visibleItemCount",visibleItemCount.toString())
                    Log.v("pastVisiblesItems", pastVisiblesItems.toString())
                    Log.v("totalItemCount", totalItemCount.toString())
                    Log.v("loading", loading.toString())

                    if (loading) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                loading = false
                                Log.v("...", "Last Item Wow!")
                                Log.v("visibleItemCount", visibleItemCount.toString())
                                Log.v("totalItemCount", totalItemCount.toString())
                                progressBar7.visibility = View.VISIBLE
                                start = totalItemCount
                                //limit=totalItemCount+totalItemCount;
                                Log.v("start", start.toString())
                                Log.v("limit", limit.toString())

                                RewardpointsAsync().execute()
                            }
                        }
                }
            }
        })


        recyclerlist1!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down

                    visibleItemCount1= mLayoutManager1.getChildCount()
                    totalItemCount1 = mLayoutManager1.getItemCount()
                    pastVisiblesItems1 = mLayoutManager1.findFirstVisibleItemPosition()
                    Log.v("visibleItem_search1",pastVisiblesItems1.toString())
                    Log.v("pastVisibles_search1", visibleItemCount1.toString())
                    Log.v("totalItem_search1", totalItemCount1.toString())
                    Log.v("loading1", loading1.toString())

                    if (loading1) {
                        if (pastVisiblesItems1 + visibleItemCount1 >= totalItemCount1) {
                            loading1 = false
                            Log.v("...", "Last Item Wow!")
                            Log.v("visibleItem_search1", visibleItemCount1.toString())
                            Log.v("totalItem_search11", totalItemCount1.toString())
                            progressBar7.visibility = View.VISIBLE
                            startsearch = totalItemCount1
                            //limit=totalItemCount+totalItemCount;
                            Log.v("start_search", startsearch.toString())
                            Log.v("limit_search", limit.toString())

                            RewardpointsAsync_search().execute()
                        }
                    }
                }
            }
        })

    }

    inner class RewardpointsAsync : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {

            if(start==0) {
                progress_lay!!.visibility = View.VISIBLE
                swipeToRefresh!!.visibility = View.GONE
                recyclerlist1!!.visibility=View.INVISIBLE
            }
            else{
                progressBar7.visibility=View.VISIBLE
                recyclerlist1!!.visibility=View.INVISIBLE

                loading=false
            }

        }

        protected override fun doInBackground(vararg strings: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils!!.loadId())
                jobj.put("start", start)
                jobj.put("limit", limit)
                Log.i("rewardinput", Appconstants.REWARD + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARD, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.e("rewardresp", resp.toString());
            swipeToRefresh!!.visibility = View.VISIBLE
            recyclerlist1!!.visibility=View.INVISIBLE
            progress_lay!!.visibility=View.GONE
            progressBar7.visibility=View.GONE
            loading=true
            productItems = ArrayList()
            try {
                if (resp != null) {
                    val jarr = JSONArray(resp)
                    for (i in 0 until jarr.length()) {
                        val jobj = jarr.getJSONObject(i)
                        if (jobj.getString("Status") == "Success") {
                            val jarray = jobj.getJSONArray("Response")
                            for (j in 0 until jarray.length()) {
                                val jobject = jarray.getJSONObject(j)
                                val id = jobject.getString("id")
                                val date = jobject.getString("date")
                                val visual_time = jobject.getString("visual_time")
                                val username = jobject.getString("username")
                                val name = jobject.getString("name")
                                val whome = jobject.getString("whome")
                                val whomename = jobject.getString("whomename")
                                val points = jobject.getString("points")
                                val type = jobject.getString("type")
                                productItems!!.add(
                                    Rewardpointsbo(
                                        id,
                                        date,
                                        visual_time,
                                        username,
                                        name,
                                        "",
                                        whome,
                                        whomename,
                                        points,
                                        type
                                    )
                                )
                            }
                        } else {
                            swipeToRefresh!!.visibility = View.GONE
                            nodata!!.visibility = View.VISIBLE
                            progressBar7.visibility=View.GONE
                            recyclerlist1!!.visibility=View.INVISIBLE
                            recyclerlist1!!.visibility=View.INVISIBLE
                            loading=true
                        }
                    }
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter!!.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("err",e.toString())
            }
        }
    }

    inner class RewardpointsAsync_search : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {
            if(startsearch==0) {
                progress_lay!!.visibility = View.VISIBLE
                swipeToRefresh!!.visibility = View.GONE
                recyclerlist!!.visibility=View.INVISIBLE
                recyclerlist!!.visibility=View.INVISIBLE
            }
            else{
                progressBar7.visibility=View.VISIBLE
                loading1=false
                recyclerlist!!.visibility=View.INVISIBLE
                recyclerlist!!.visibility=View.INVISIBLE
            }
        }

        protected override fun doInBackground(vararg strings: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils!!.loadId())
                jobj.put("search", searchval)
                jobj.put("start", startsearch)
                jobj.put("limit", limitsearch)
                Log.i("rewardinput", Appconstants.REWARD + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARD, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            swipeToRefresh!!.visibility = View.VISIBLE
            progress_lay!!.visibility = View.GONE
            progressBar7.visibility=View.GONE
            loading1 = true
            recyclerlist!!.visibility=View.INVISIBLE
            recyclerlist1!!.visibility=View.VISIBLE
            productItems1 = ArrayList()
            try {
                if (resp != null) {
                    val jarr = JSONArray(resp)
                    for (i in 0 until jarr.length()) {
                        val jobj = jarr.getJSONObject(i)
                        if (jobj.getString("Status") == "Success") {
                            val jarray = jobj.getJSONArray("Response")
                            for (j in 0 until jarray.length()) {
                                val jobject = jarray.getJSONObject(j)
                                val id = jobject.getString("id")
                                val date = jobject.getString("date")
                                val visual_time = jobject.getString("visual_time")
                                val username = jobject.getString("username")
                                val name = jobject.getString("name")
                                val whome = jobject.getString("whome")
                                val whomename = jobject.getString("whomename")
                                val points = jobject.getString("points")
                                val type = jobject.getString("type")
                                productItems1!!.add(
                                    Rewardpointsbo(
                                        id,
                                        date,
                                        visual_time,
                                        username,
                                        name,
                                        "",
                                        whome,
                                        whomename,
                                        points,
                                        type
                                    )
                                )
                            }
                        }
                        else {
                            swipeToRefresh!!.visibility = View.GONE
                            nodata!!.visibility = View.VISIBLE
                            progressBar7.visibility=View.GONE
                            recyclerlist!!.visibility=View.INVISIBLE
                            recyclerlist!!.visibility=View.INVISIBLE
                            loading1=true
                        }
                    }

                    mRecyclerListitems1.addAll(productItems1!!)
                    itemsAdapter1!!.notifyDataSetChanged()
                } else {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e:JSONException) {
                e.printStackTrace()
            }
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_my_portal_search, menu)
        val searchViewItem = menu.findItem(R.id.menu_search)
        when (searchViewItem.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
            }
        }
        val searchView = MenuItemCompat.getActionView(searchViewItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                searchval = query
                recyclerlist1!!.visibility=View.VISIBLE
                recyclerlist!!.visibility=View.INVISIBLE
                submit="s";
                val reward = RewardpointsAsync_search()
                reward.execute()

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // adapter.getFilter().filter(newText);

                if (newText.length == 0) {
                    mRecyclerListitems1!!.clear()
                    itemsAdapter1!!.notifyDataSetChanged()
                    recyclerlist!!.visibility=View.VISIBLE
                    recyclerlist1!!.visibility=View.INVISIBLE
                    progressBar7.visibility=View.GONE
                    loading=true
                    searchval=""
                    startsearch=0
                    pastVisiblesItems1=0
                    visibleItemCount1=0
                    totalItemCount1=0
                }

                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Rewardpoints, HomePage::class.java)
        startActivity(intent)
        finish()
    }
}