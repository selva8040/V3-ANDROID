package com.elancier.healthzone

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.elancier.healthzone.Adapter.WalletBookAdapter
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.PassbookBo
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class Rewardpoints : MainView() {
    var retry: Dialog? = null
    var retrybut: ImageView? = null
    var passlist: ArrayList<PassbookBo>? = null
    var plistvw: ListView? = null
    var crore: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)
        supportActionBar!!.setTitle("Reward Points")
        utils = Utils(applicationContext)
        loadprogress()
        retry = Dialog(this)
        retry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        retry!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        val v = layoutInflater.inflate(R.layout.retrylay, null)
        retrybut = v.findViewById<View>(R.id.retry) as ImageView
        retry!!.setContentView(v)
        retry!!.setCancelable(false)
        plistvw = findViewById<View>(R.id.listView) as ListView
        nodata = findViewById<View>(R.id.nodata) as TextView
        startprogress()
        val task: GetInfoTask = GetInfoTask()
        task.execute()

        retrybut!!.setOnClickListener {
            retry!!.dismiss()
            startprogress()
            val task: GetInfoTask =
                GetInfoTask()
            task.execute()
        }
    }

     inner class GetInfoTask :
        AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started")
        }

        protected override fun doInBackground(vararg param: String?): String {
            var result: String? = null
            val con = Connection()
            try {
                val jobj = JSONObject()

                    jobj.put("uname", utils.loadName())

                Log.i("utilsInput", Appconstants.Reward_WALLET + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.Reward_WALLET, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result!!
        }

        protected override fun onPostExecute(resp: String?) {
            Log.i("utilsresp", resp + "")
            stopprogress()
            passlist = ArrayList()
            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        for (i in 0 until jarr.length()) {
                            val jobj = jarr.getJSONObject(i)
                            val bo = PassbookBo()
                            bo.date = if (jobj.getString("date").toString().trim { it <= ' ' }
                                    .equals(
                                        "null",
                                        ignoreCase = true
                                    )) "" else jobj.getString("date")
                            bo.mode = if (jobj.getString("mode").toString().trim { it <= ' ' }
                                    .equals(
                                        "null",
                                        ignoreCase = true
                                    )) "" else jobj.getString("mode")
                            bo.type = if (jobj.getString("type").toString().trim { it <= ' ' }
                                    .equals(
                                        "null",
                                        ignoreCase = true
                                    )) "0.00" else jobj.getString("type")
                            bo.amount = if (jobj.getString("amount").toString().trim { it <= ' ' }
                                    .equals(
                                        "null",
                                        ignoreCase = true
                                    )) "0.00" else jobj.getString("amount")
                            bo.balance = if (jobj.getString("balance").toString().trim { it <= ' ' }
                                    .equals(
                                        "null",
                                        ignoreCase = true
                                    )) "0.00" else jobj.getString("balance")
                            /*if(jobj.getString("debit").toString().equals("")){
                                bo.setType1("");
                            }
                            else{
                                JSONArray array=jobj.getJSONArray("debit");
                                JSONObject object=array.getJSONObject(0);
                                bo.setType1("Debit");
                                Log.i("positionnnnnnn",i+"       "+object.toString());

                                bo.setDebit1(object.getString("wamount").toString().trim().equalsIgnoreCase("null")?"":object.getString("wamount"));
                                bo.setDate1(object.getString("wdate").toString().trim().equalsIgnoreCase("null")?"":object.getString("wdate"));
                                bo.setBalance1(object.getString("balance").toString().trim().equalsIgnoreCase("null")?"0.00":object.getString("balance"));
                                bo.setCredit1("");

                            }
                            if(jobj.getString("credit").toString().equals("")){
                                bo.setType("");
                            }
                            else{
                                JSONArray array=jobj.getJSONArray("credit");
                                JSONObject object=array.getJSONObject(0);
                                bo.setType("Credit");
                                bo.setCredit(object.getString("amount").toString().trim().equalsIgnoreCase("null")?"":object.getString("amount"));
                                bo.setDate(object.getString("date").toString().trim().equalsIgnoreCase("null")?"":object.getString("date"));
                                bo.setBalance(object.getString("total").toString().trim().equalsIgnoreCase("null")?"0.00":object.getString("total"));
                                bo.setDebit("");

                            }*/
                            // bo.setUserid(jobj.getString("username").toString().trim().equalsIgnoreCase("null")?"":jobj.getString("username"));
                            passlist!!.add(bo)
                        }
                        val adapter =
                            WalletBookAdapter(this@Rewardpoints, R.layout.wallet_book_list_item, passlist)
                        plistvw!!.adapter = adapter
                        scrollMyListViewToBottom(passlist!!.size - 1)
                    } else {
                        nodata.setVisibility(View.GONE)
                    }
                } else {
                    retry!!.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                retry!!.show()
            }
        }
    }

    private fun scrollMyListViewToBottom(pos: Int) {
        plistvw!!.post { // Select the last row so it will scroll into view...
            plistvw!!.setSelection(pos)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
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
}

/*
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
}*/
