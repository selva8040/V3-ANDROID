package com.elancier.healthzone

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Rewardfeedadap
import com.elancier.healthzone.Adapter.Rewardhistoryadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CheckNetwork
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.Reward_history
import kotlinx.android.synthetic.main.activity_reward_history.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Reward_history : AppCompatActivity() {
    var progress_lay: LinearLayout? = null
    var retry_lay: LinearLayout? = null
    var recyclerlist: RecyclerView? = null
    var prog: ProgressBar? = null
    var nodata: TextView? = null
    var retry: TextView? = null
    var nodatas: TextView? = null
    var utils: Utils? = null
    var picker: DatePickerDialog? = null
    var start = 0
    private val scrollValue = false
    private var scrollNeed = false
    var limit = 0
    var px = 0
    var frdt: TextView? = null
    var todt: TextView? = null
    private val loading = true
    var pastVisiblesItems = 0
    var visibleItemCount = 0
    var totalItemCount = 0
    var visual_time = ""
    var cutoff = ""
    var feedback = ""
    var fextra = ""
    var itemsAdapter: Rewardhistoryadap? = null
    private val mRecyclerListitems: MutableList<Any> = ArrayList()
    var mLayoutManager: LinearLayoutManager? = null
    var itemsAdapter1: Rewardfeedadap? = null
    private val mRecyclerListitems1: MutableList<Any> = ArrayList()
    private var productItems: MutableList<Rewardpointsbo>? = null
    private var productItems1: MutableList<Feedbackbo>? = null
    private var productItems2: MutableList<Feedbackbo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward_history)
        supportActionBar!!.title = "Reward History"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.newdashboard_gradient
            )
        )
        utils = Utils(applicationContext)
        progress_lay = findViewById<View>(R.id.progress_lay) as LinearLayout
        retry_lay = findViewById<View>(R.id.retry_lay) as LinearLayout
        frdt = findViewById<View>(R.id.frdt) as TextView
        todt = findViewById<View>(R.id.todt) as TextView
        prog = findViewById<View>(R.id.progressBar) as ProgressBar
        recyclerlist = findViewById<View>(R.id.recyclerlist) as RecyclerView
        recyclerlist!!.layoutManager = LinearLayoutManager(this)
        recyclerlist!!.itemAnimator = DefaultItemAnimator()
        nodata = findViewById<View>(R.id.nodata) as TextView
        retry = findViewById<View>(R.id.retry) as TextView
        scrollNeed = true
        start = 0
        limit = 20

        val c = Calendar.getInstance().time
        println("Current time => $c")
        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = df.format(c)
        frdt!!.text = formattedDate
        todt!!.text = formattedDate

        mLayoutManager = LinearLayoutManager(this)
        recyclerlist!!.layoutManager = mLayoutManager
        if (!frdt!!.text.toString().isEmpty()) {
            productItems = ArrayList()
            productItems1 = ArrayList()
            productItems2 = ArrayList()
            productItems!!.clear()
            productItems1!!.clear()
            productItems2!!.clear()
            mRecyclerListitems.clear()
            val reward: RewardpointsAsync = RewardpointsAsync()
            reward.execute()
        }


        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                finish()
                startActivity(Intent(this@Reward_history, Reward_history::class.java))
                swipeToRefresh.setRefreshing(false)
            }
        })

        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics)
            .toInt()

        frdt!!.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            picker = DatePickerDialog(
                this@Reward_history,
                { view, year, monthOfYear, dayOfMonth ->
                    frdt!!.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                    if (!todt!!.text.toString().isEmpty()) {
                        if (CheckNetwork.isInternetAvailable(this@Reward_history)) {
                            start = 0
                            limit = 20
                            val myFormat = SimpleDateFormat("yyyy-MM-dd")
                            val inputString1 = frdt!!.text.toString()
                            val inputString2 = todt!!.text.toString()
                            try {
                                val date1 = myFormat.parse(inputString1)
                                val date2 = myFormat.parse(inputString2)
                                val diff = date2.time - date1.time
                                println(
                                    "Days: " + TimeUnit.DAYS.convert(
                                        diff,
                                        TimeUnit.MILLISECONDS
                                    )
                                )
                                val date =
                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
                                        .toInt()
                                if (date <= 1) {
                                    productItems = ArrayList()
                                    productItems1 = ArrayList()
                                    productItems2 = ArrayList()
                                    productItems!!.clear()
                                    productItems1!!.clear()
                                    productItems2!!.clear()
                                    mRecyclerListitems.clear()
                                    val reward: RewardpointsAsync = RewardpointsAsync()
                                    reward.execute()
                                } else {
                                    try {
                                        val alertBuilder = AlertDialog.Builder(this@Reward_history)
                                        alertBuilder.setCancelable(false)
                                        alertBuilder.setTitle("Information")
                                        alertBuilder.setMessage(Html.fromHtml("Note: 2days record only available here\nPrevious records you can check our website \n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"))
                                        alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which ->
                                            dialog.dismiss()
                                            val c = Calendar.getInstance().time
                                            println("Current time => $c")
                                            val df = SimpleDateFormat("yyyy-MM-dd")
                                            val formattedDate = df.format(c)
                                            frdt!!.text = formattedDate
                                        }
                                        val alert = alertBuilder.create()
                                        alert.show()
                                        (alert.findViewById<View>(android.R.id.message) as TextView).movementMethod =
                                            LinkMovementMethod.getInstance()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Date range should be in 2 days.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            applicationContext,
                                            "Visit website for more datas.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        } else {
                            recyclerlist!!.visibility = View.GONE
                            retry_lay!!.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(this@Reward_history, "Select date", Toast.LENGTH_LONG).show()
                    }
                }, year, month, day
            )
            picker!!.show()
        }
        todt!!.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            picker = DatePickerDialog(
                this@Reward_history,
                { view, year, monthOfYear, dayOfMonth ->
                    todt!!.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                    if (!frdt!!.text.toString().isEmpty()) {
                        if (CheckNetwork.isInternetAvailable(this@Reward_history)) {
                            start = 0
                            limit = 20
                            val myFormat = SimpleDateFormat("yyyy-MM-dd")
                            val inputString1 = frdt!!.text.toString()
                            val inputString2 = todt!!.text.toString()
                            try {
                                val date1 = myFormat.parse(inputString1)
                                val date2 = myFormat.parse(inputString2)
                                val diff = date2.time - date1.time
                                println(
                                    "Days: " + TimeUnit.DAYS.convert(
                                        diff,
                                        TimeUnit.MILLISECONDS
                                    )
                                )
                                val date =
                                    TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString()
                                        .toInt()
                                if (date <= 1) {
                                    productItems = ArrayList()
                                    productItems1 = ArrayList()
                                    productItems2 = ArrayList()
                                    productItems!!.clear()
                                    productItems1!!.clear()
                                    productItems2!!.clear()
                                    mRecyclerListitems.clear()
                                    val reward: RewardpointsAsync = RewardpointsAsync()
                                    reward.execute()
                                } else {
                                    try {
                                        val alertBuilder = AlertDialog.Builder(this@Reward_history)
                                        alertBuilder.setCancelable(false)
                                        alertBuilder.setTitle("Information")
                                        alertBuilder.setMessage(Html.fromHtml("Note: 2days record only available here\nPrevious records you can check our website\n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"))
                                        alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which ->
                                            dialog.dismiss()
                                            val c = Calendar.getInstance().time
                                            println("Current time => $c")
                                            val df = SimpleDateFormat("yyyy-MM-dd")
                                            val formattedDate = df.format(c)
                                            todt!!.text = formattedDate
                                        }
                                        val alert = alertBuilder.create()
                                        alert.show()
                                        (alert.findViewById<View>(android.R.id.message) as TextView).movementMethod =
                                            LinkMovementMethod.getInstance()
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            applicationContext,
                                            "Date range should be in 2 days.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Toast.makeText(
                                            applicationContext,
                                            "Visit website for more datas.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            } catch (e: ParseException) {
                                e.printStackTrace()
                            }
                        } else {
                            recyclerlist!!.visibility = View.GONE
                            retry_lay!!.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(this@Reward_history, "Select date", Toast.LENGTH_LONG).show()
                    }
                }, year, month, day
            )
            picker!!.show()
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        retry!!.setOnClickListener {
            if (CheckNetwork.isInternetAvailable(this@Reward_history)) {
                recyclerlist!!.visibility = View.VISIBLE
                retry_lay!!.visibility = View.GONE
                val myFormat = SimpleDateFormat("yyyy-MM-dd")
                val inputString1 = frdt!!.text.toString()
                val inputString2 = todt!!.text.toString()
                try {
                    val date1 = myFormat.parse(inputString1)
                    val date2 = myFormat.parse(inputString2)
                    val diff = date2.time - date1.time
                    println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                    val date = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString().toInt()
                    if (date <= 1) {
                        productItems = ArrayList()
                        productItems1 = ArrayList()
                        productItems2 = ArrayList()
                        productItems!!.clear()
                        productItems1!!.clear()
                        productItems2!!.clear()
                        mRecyclerListitems.clear()
                        val reward: RewardpointsAsync = RewardpointsAsync()
                        reward.execute()
                    } else {
                        try {
                            val alertBuilder = AlertDialog.Builder(this@Reward_history)
                            alertBuilder.setCancelable(false)
                            alertBuilder.setTitle("Information")
                            alertBuilder.setMessage(Html.fromHtml("Note: 2days record only available here\nPrevious records you can check our website\n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"))
                            alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss()
                                val c = Calendar.getInstance().time
                                println("Current time => $c")
                                val df = SimpleDateFormat("yyyy-MM-dd")
                                val formattedDate = df.format(c)
                                todt!!.text = formattedDate
                                frdt!!.text = formattedDate}
                            val alert = alertBuilder.create()
                            alert.show()
                            (alert.findViewById<View>(android.R.id.message) as TextView).movementMethod =
                                LinkMovementMethod.getInstance()
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "Date range should be in 2 days.",
                                Toast.LENGTH_LONG
                            ).show()
                            Toast.makeText(
                                applicationContext,
                                "Visit website for more datas.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            } else {
                recyclerlist!!.visibility = View.GONE
                retry_lay!!.visibility = View.VISIBLE
            }
        }
        itemsAdapter = Rewardhistoryadap(mRecyclerListitems, this) { view, position, viewType ->
            val item = mRecyclerListitems[position] as Rewardpointsbo
            Log.e("clickresp", "value")

            //clikffed();
        }
        recyclerlist!!.adapter = itemsAdapter
        itemsAdapter1 = Rewardfeedadap(
            mRecyclerListitems1,
            applicationContext
        ) { view, position, viewType -> val item = mRecyclerListitems1[position] as Feedbackbo }
    }

    inner class RewardpointsAsync : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {
            prog!!.visibility = View.VISIBLE
            recyclerlist!!.visibility = View.GONE
        }

        protected override fun doInBackground(vararg strings: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils!!.loadName())
                jobj.put("from", frdt!!.text.toString())
                jobj.put("to", todt!!.text.toString())
                Log.i("rewardinput", Appconstants.REWARDHIST + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARDHIST, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                //Log.e("rewardresp", resp);
            } catch (e: Exception) {
                // Log.e("rewardrespcatch", e.toString());
            }
            prog!!.visibility = View.GONE
            recyclerlist!!.visibility = View.VISIBLE
            //prog!!.visibility = View.GONE
            var feed: JSONArray? = null
            var feed1: String
            try {
                if (resp != null) {
                    val obj = JSONObject(resp)
                    val arrayLanguage = obj.getJSONArray("Response")
                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage[i] as JSONObject
                        val visual_time = JO.getString("visual")
                        //String username = jobject.getString("username");
                        val name = JO.getString("dat")
                        val uname = JO.getString("uname")
                        val whome = JO.getString("cutoff")
                        val type = JO.getString("status")
                        val fcolor = JO.getString("fcolor")
                        val fextra = JO.getString("fextra")
                        val time = JO.getString("time")
                        try {
                            feed = JO.getJSONArray("feedback")
                            productItems!!.add(
                                Rewardpointsbo(
                                    i.toString(),
                                    fcolor,
                                    visual_time,
                                    fextra,
                                    name,
                                    uname,
                                    whome,
                                    time,
                                    "feed",
                                    type
                                )
                            )
                            if (!feed.toString().isEmpty() && feed.toString() != "null") {
                                for (j in 0 until feed.length()) {
                                    val feedobj = feed[j] as JSONObject
                                    val id = feedobj.getString("id")
                                    val comment = feedobj.getString("comment")
                                    val types = feedobj.getString("type")
                                    val time = feedobj.getString("dtime")
                                    productItems2!!.add(
                                        Feedbackbo(
                                            id,
                                            types,
                                            comment,
                                            i.toString(),
                                            time
                                        )
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("rewardrespnw", e.toString())
                            productItems!!.add(
                                Rewardpointsbo(
                                    i.toString(),
                                    fcolor,
                                    visual_time,
                                    fextra,
                                    name,
                                    uname,
                                    whome,
                                    time,
                                    "notfeed",
                                    type
                                )
                            )
                        }
                    }
                    mRecyclerListitems.clear()
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter!!.notifyDataSetChanged()
                } else {
                    val reward: RewardpointsAsync = RewardpointsAsync()
                    reward.execute()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter!!.notifyDataSetChanged()
            }
        }
    }

    inner class DatepointsAsync : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() {
            progress_lay!!.visibility = View.VISIBLE
            recyclerlist!!.visibility = View.GONE
        }

        protected override fun doInBackground(vararg strings: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils!!.loadName())
                Log.i("rewardinput", Appconstants.REWARDHIST + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARDHIST, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            // Log.e("rewardresp", resp);
            recyclerlist!!.visibility = View.VISIBLE
            progress_lay!!.visibility = View.GONE
            productItems = ArrayList()
            try {
                val obj = JSONObject(resp)
                val arrayLanguage = obj.getJSONArray("Response")
                for (i in 0 until arrayLanguage.length()) {
                    val JO = arrayLanguage[i] as JSONObject
                    val visual_time = JO.getString("visual")
                    //String username = jobject.getString("username");
                    val name = JO.getString("dat")
                    val uname = JO.getString("uname")
                    val whome = JO.getString("point")
                    val type = JO.getString("status")
                    productItems!!.add(
                        Rewardpointsbo(
                            "",
                            "",
                            visual_time,
                            "",
                            name,
                            uname,
                            whome,
                            "",
                            "",
                            type
                        )
                    )
                }
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter!!.notifyDataSetChanged()
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }




    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@Reward_history, HomePage::class.java)
        startActivity(intent)
    }

    fun clikffed(pos: String, time: String, cutoffs: String, feedextra: String, times: String) {
        try {
            visual_time = time
            cutoff = cutoffs
            println("feedextra$feedextra")
            val update = Dialog(this@Reward_history)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            val v = layoutInflater.inflate(R.layout.feedback_popup, null)
            val updatebut = v.findViewById<View>(R.id.feedlist) as RecyclerView
            val scroll = v.findViewById<View>(R.id.sroll) as ScrollView
            val nofeed = v.findViewById<View>(R.id.textView18) as TextView
            val feed = v.findViewById<View>(R.id.textView15) as TextView
            val feedvt = v.findViewById<View>(R.id.textView15vtime) as TextView
            val feedcut = v.findViewById<View>(R.id.textView15vcut) as TextView
            val textView88 = v.findViewById<View>(R.id.textView88) as TextView
            val laterbut = v.findViewById<View>(R.id.agree) as Button
            val viewfile = v.findViewById<View>(R.id.viewfile) as Button
            val download = v.findViewById<View>(R.id.download) as Button
            feed.text = "Feedback - " + utils!!.loadName()
            feedvt.text = "Visual Time - $time"
            feedcut.text = "Cutoff Time - $cutoffs"

            if(times.equals("0")){
                download.visibility=View.GONE

            }
            update.setContentView(v)
            val window = update.window
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            update.setCancelable(false)
            update.show()
            for (k in productItems2!!.indices) {
                Log.e("VALUEGET", productItems2!![k].points)
                if (pos == productItems2!![k].points) {
                    productItems1!!.add(
                        Feedbackbo(
                            productItems2!![k].id,
                            productItems2!![k].gettype(),
                            productItems2!![k].getcomment(),
                            "",
                            productItems2!![k].gettime()
                        )
                    )
                    feedback = productItems2!![k].getcomment()
                }
            }
            if (productItems1!!.isEmpty()) {
                nofeed.visibility = View.VISIBLE
            }
            mRecyclerListitems1.addAll(productItems1!!)
            itemsAdapter1!!.notifyDataSetChanged()
            val mLayoutManager3: RecyclerView.LayoutManager =
                LinearLayoutManager(this@Reward_history, LinearLayoutManager.VERTICAL, false)
            updatebut.layoutManager = mLayoutManager3
            scroll.smoothScrollBy(updatebut.computeHorizontalScrollOffset(), 0)
            updatebut.adapter = itemsAdapter1
            ///titlename.setText("New Version 1."+Number);
            laterbut.setOnClickListener {
                update.dismiss()
                productItems1!!.clear()
                mRecyclerListitems1.clear()
                nofeed.visibility = View.GONE
            }
            try {
                val times = time.replace("-".toRegex(), "_")
                val times1 = times.replace(":".toRegex(), "_")
                val time2 = times1.trim { it <= ' ' }
                var imagePath: File? = null
                imagePath = if (time != "null") {
                    File(
                        Environment.getExternalStorageDirectory().absoluteFile
                            .toString() + "/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
                    )
                } else {
                    File(
                        Environment.getExternalStorageDirectory().absoluteFile
                            .toString() + "/V3 Online TV/V3 reward images/" + feedextra + utils!!.loadName() + ".png"
                    )
                }
                if (imagePath.exists()) {
                    download.text = "DOWNLOADED"
                    download.isEnabled = false
                    textView88.visibility = View.VISIBLE
                    if (time != "null") {
                        textView88.text =
                            "Downloaded Path : " + "storage/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
                    } else {
                        textView88.text =
                            "Downloaded Path : " + "storage/V3 Online TV/V3 reward images/" + feedextra + utils!!.loadName() + ".png"
                    }
                } else {
                }
            } catch (e: Exception) {

            }

            download.setOnClickListener {
                if (checkPermission() && download.text.toString() == "DOWNLOAD") {
                    try {
                        val bitmap = takeScreenShot(update)
                        Log.e("timenull", feedextra)
                        if (time != "null") {
                            saveBitmap(bitmap, time, download, textView88, viewfile)
                        } else {
                            saveBitmap(bitmap, feedextra, download, textView88, viewfile)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e("filerr", e.toString())
                    }

                } else {
                    requestPermission()
                }
            }
        } catch (e: Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }
    }

    fun saveBitmap(bitmap: Bitmap, time: String, button: Button, tt: TextView, viewfile: Button) {
        Log.e("timenull", time)
        val times = time.replace("-".toRegex(), "_")
        val times1 = times.replace(":".toRegex(), "_")
        val time2 = times1.trim { it <= ' ' }
        Log.e("timenxx", time2)
        /*val directory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File(this.getExternalFilesDir(null), File.separator + "V3 Online TV/V3 reward images")
        } else{ File(
            Environment.getExternalStorageState(), File.separator + "V3 Online TV/V3 reward images")
        }//sk*/
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + File.separator + "V3 Online TV/V3 reward images"
        )
            Log.e("directory", directory.exists().toString())//sk
        if (!directory.exists()) {
            directory.mkdirs()
            Log.e("directorymk", directory.mkdir().toString())//sk
        }
        val imagePath = File(
            directory.absolutePath + "/" + time2 + utils!!.loadName() + ".png"
        )
        Log.e("imagePath", imagePath.path)//sk
        /*val imagePath = File(
            Environment.getExternalStorageDirectory().absoluteFile
                .toString() + "/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
        )*/
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(imagePath)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            Log.e("Screenshot", "saved successfully")
            fos.flush()
            fos.close()
            button.text = "DOWNLOADED"
            //viewfile.visibility=View.VISIBLE
            tt.visibility = View.VISIBLE
            tt.text =
                "Downloaded Path : " + "Pictures/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
            save()
            button.setOnClickListener {
                if (button.text.toString() == "Open") {
                    openScreenshot(imagePath)
                }
            }


        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
            val directory = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + "V3 Online TV/V3 reward images"
            )
            Log.e("directory", directory.exists().toString())//sk
            if (!directory.exists()) {
                directory.mkdirs()
                Log.e("directorymk", directory.mkdir().toString())//sk
            }
            val imagePath = File(
                directory.absolutePath + "/" + time2 + utils!!.loadName() + ".png"
            )
            Log.e("imagePath", imagePath.path)//sk
            /*val imagePath = File(
                Environment.getExternalStorageDirectory().absoluteFile
                    .toString() + "/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
            )*/
            val fos: FileOutputStream
            try {
                fos = FileOutputStream(imagePath)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                Log.e("Screenshot", "saved successfully")
                fos.flush()
                fos.close()
                button.text = "DOWNLOADED"
                tt.visibility = View.VISIBLE
                tt.text =
                    "Downloaded Path : " + "storage/Pictures/V3 Online TV/V3 reward images/" + time2 + utils!!.loadName() + ".png"
                save()
                button.setOnClickListener {
                    if (button.text.toString() == "Open") {
                        openScreenshot(imagePath)
                    }
                }
            }
            catch (e: Exception){

            }

        } catch (e: IOException) {
            Log.e("GREC", e.message, e)
        }
    }

    private fun checkPermission(): Boolean {
        return if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                    != PackageManager.PERMISSION_GRANTED)
        ) {
            // Permission is not granted
            false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()

                // main logic
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                                != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                                != PackageManager.PERMISSION_GRANTED)
                    ) {
                        showMessageOKCancel("You need to allow access permissions",
                            DialogInterface.OnClickListener { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermission()
                                }
                            })
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@Reward_history)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    fun save() {
        val submit: Videosubmit = Videosubmit()
        submit.execute("Download")
    }

    inner class Videosubmit : AsyncTask<String?, String?, String?>() {
        var pdialog: ProgressDialog? = null
        override fun onPreExecute() {
            pdialog = ProgressDialog(this@Reward_history)
            pdialog!!.setMessage("Saving...")
            pdialog!!.setCancelable(false)
            pdialog!!.show()
        }

        protected override fun doInBackground(vararg param: String?): String? {
            var result: String? = null
            val con = Connection()
            val versionCode = BuildConfig.VERSION_CODE
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils!!.loadName())
                jobj.put("visual_time", visual_time)
                jobj.put("cutoff", cutoff)
                jobj.put("feedback", feedback)
                jobj.put("status", param[0])
                jobj.put("type", "Normal")
                Log.i("Videossubmitinput", Appconstants.download + "    " + jobj.toString() + "")
                result = con.sendHttpPostjson2(Appconstants.download, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                if(resp!=null) {
                    val jobj = JSONArray(resp)
                    if (jobj.getJSONObject(0).getString("Status") == "Success") {
                        pdialog!!.dismiss()
                        Toast.makeText(this@Reward_history, "Saved.", Toast.LENGTH_LONG).show()
                    } else {
                    }
                }
                else{
                    //Toast.makeText(this@Reward_history, "Saved.", Toast.LENGTH_LONG).show()

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun openScreenshot(imageFile: File) {
        val files = imageFile.toString().replace("%20".toRegex(), " ")
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(File(files))
        intent.setDataAndType(uri, "image/*")
        startActivity(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 200
        private fun takeScreenShot(activity: Dialog): Bitmap {
            val v1 = activity.window!!.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false
            Log.e("Screenshot", "taken successfully")
            return bitmap
        }
    }
}