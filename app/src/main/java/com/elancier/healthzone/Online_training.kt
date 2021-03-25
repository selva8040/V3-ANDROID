package com.elancier.healthzone

import android.Manifest
import android.annotation.SuppressLint
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
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Rewardfeedadap
import com.elancier.healthzone.Adapter.Rewardhistoryonlineadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CheckNetwork
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_online_training.*
import kotlinx.android.synthetic.main.activity_online_training.swipeToRefresh
import kotlinx.android.synthetic.main.activity_reward_history.*
import kotlinx.android.synthetic.main.online_training_header.*
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

class Online_training : AppCompatActivity() {
    internal lateinit var progress_lay: LinearLayout
    internal lateinit var retry_lay:LinearLayout
    internal lateinit var recyclerlist: RecyclerView
    internal lateinit var prog: ProgressBar
    internal lateinit var nodata: TextView
    internal lateinit var retry:TextView
    internal var nodatas:TextView? = null
    internal lateinit var utils: Utils
    internal lateinit var picker: DatePickerDialog
    internal var start: Int = 0
    private val scrollValue: Boolean = false
    private var scrollNeed: Boolean = false
    internal var limit: Int = 0
    internal var px: Int = 0
    var visual_time = ""
    var cutoff = ""
    var feedback = ""
    internal lateinit var frdt: TextView
    internal lateinit var todt:TextView
    private val loading = true
    internal var pastVisiblesItems: Int = 0
    internal var visibleItemCount:Int = 0
    internal var totalItemCount:Int = 0
    private val PERMISSION_REQUEST_CODE = 200


    internal lateinit var itemsAdapter: Rewardhistoryonlineadap
    private val mRecyclerListitems = ArrayList<Any>()
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var itemsAdapter1: Rewardfeedadap
    private val mRecyclerListitems1 = ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo>? = null
    private var productItems1: MutableList<Feedbackbo>? = null
    private var productItems2: MutableList<Feedbackbo>? = null
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_training)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.newco));
        }
        utils = Utils(applicationContext)

        progress_lay = findViewById(R.id.progress_lay) as LinearLayout
        retry_lay = findViewById(R.id.retry_lay) as LinearLayout
        frdt = findViewById(R.id.frdt) as TextView
        todt = findViewById(R.id.todt) as TextView

        prog = findViewById(R.id.progressBar) as ProgressBar
        recyclerlist = findViewById(R.id.recyclerlist) as RecyclerView
        recyclerlist.layoutManager = LinearLayoutManager(this)
        recyclerlist.itemAnimator = DefaultItemAnimator()

        nodata = findViewById(R.id.nodata) as TextView
        retry = findViewById(R.id.retry) as TextView
        scrollNeed = true
        start = 0
        limit = 20
        val cal = Calendar.getInstance()

        cal.set(Calendar.DAY_OF_MONTH,
        cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        val firstDayOfTheMonth = cal.getTime();

        val cal1 = Calendar.getInstance()

        cal1.set(Calendar.DAY_OF_MONTH,
                cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
        val firstDayOfTheMonth1 = cal1.getTime();

        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = df.format(firstDayOfTheMonth)
        val formattedDate1 = df.format(firstDayOfTheMonth1)

        frdt.text = formattedDate
        todt.text = formattedDate1

        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.layoutManager = mLayoutManager
        if (!frdt.text.toString().isEmpty()) {
            productItems = ArrayList()
            productItems1 = ArrayList()
            productItems2 = ArrayList()

            productItems!!.clear()
            productItems1!!.clear()
            productItems2!!.clear()
            mRecyclerListitems.clear()
            val reward = RewardpointsAsync()
            reward.execute()
        }

        textView90.setText(
            Html.fromHtml("Note: 2days record only available here\\nLog on our website\\n<a href=\\\"www.v3healthzone.com\\\">www.v3healthzone.com</a>\""))
        px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics).toInt()


        menu_img.setOnClickListener {
            onBackPressed()
        }

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                finish()
                startActivity(Intent(this@Online_training,Online_training::class.java))
                swipeToRefresh.setRefreshing(false)
            }
        })

        textView90.setText(
            Html.fromHtml("Note: Current date only available here\nPrevious records you can check our website\n<a href=\\\"www.v3healthzone.com\\\">www.v3healthzone.com</a>\""))

        frdt.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(this@Online_training,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        frdt.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
                        if (!todt.text.toString().isEmpty()) {
                            if (CheckNetwork.isInternetAvailable(this@Online_training)) {

                                start = 0
                                limit = 20
                                val myFormat = SimpleDateFormat("yyyy-MM-dd")
                                val inputString1 = frdt.text.toString()
                                val inputString2 = todt.text.toString()

                                try {
                                    val date1 = myFormat.parse(inputString1)
                                    val date2 = myFormat.parse(inputString2)
                                    val diff = date2.time - date1.time
                                    println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                                    val date = Integer.parseInt(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString())
                                    //if (date <= 7) {
                                        productItems = ArrayList()
                                        productItems1 = ArrayList()
                                        productItems2 = ArrayList()

                                        productItems!!.clear()
                                        productItems1!!.clear()
                                        productItems2!!.clear()
                                        mRecyclerListitems.clear()
                                        val reward = RewardpointsAsync()
                                        reward.execute()


                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }


                            } else {
                                recyclerlist.visibility = View.GONE
                                retry_lay.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(this@Online_training, "Select date", Toast.LENGTH_LONG).show()
                        }
                    }, year, month, day)
            picker.show()
        }
        todt.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(this@Online_training,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        todt.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth

                        if (!frdt.text.toString().isEmpty()) {
                            if (CheckNetwork.isInternetAvailable(this@Online_training)) {

                                start = 0
                                limit = 20
                                val myFormat = SimpleDateFormat("yyyy-MM-dd")
                                val inputString1 = frdt.text.toString()
                                val inputString2 = todt.text.toString()

                                try {
                                    val date1 = myFormat.parse(inputString1)
                                    val date2 = myFormat.parse(inputString2)
                                    val diff = date2.time - date1.time
                                    println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                                    val date = Integer.parseInt(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString())
                                    //if (date <= 7) {

                                        productItems = ArrayList()
                                        productItems1 = ArrayList()
                                        productItems2 = ArrayList()

                                        productItems!!.clear()
                                        productItems1!!.clear()
                                        productItems2!!.clear()
                                        mRecyclerListitems.clear()
                                        val reward = RewardpointsAsync()
                                        reward.execute()
                                    //} else {
                                       /* try {
                                            val alertBuilder = android.app.AlertDialog.Builder(this@Online_training)
                                            alertBuilder.setCancelable(true)
                                            alertBuilder.setTitle("Information")
                                            alertBuilder.setMessage(Html.fromHtml("Last 7 days History only Show this page.If you want more history\nLog on our website\n.<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"))
                                            alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
                                            val alert = alertBuilder.create()
                                            alert.show()
                                            (alert.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
                                        } catch (e: Exception) {
                                            Toast.makeText(applicationContext, "Date range should be in 7 days.", Toast.LENGTH_LONG).show()
                                            Toast.makeText(applicationContext, "Visit website for more datas.", Toast.LENGTH_LONG).show()

                                        }*/

                                    //}

                                } catch (e: ParseException) {
                                    e.printStackTrace()
                                }

                            } else {
                                recyclerlist.visibility = View.GONE
                                retry_lay.visibility = View.VISIBLE
                            }
                        } else {
                            Toast.makeText(this@Online_training, "Select date", Toast.LENGTH_LONG).show()
                        }
                    }, year, month, day)
            picker.show()
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        retry.setOnClickListener {
            if (CheckNetwork.isInternetAvailable(this@Online_training)) {
                recyclerlist.visibility = View.VISIBLE
                retry_lay.visibility = View.GONE

                val myFormat = SimpleDateFormat("yyyy-MM-dd")
                val inputString1 = frdt.text.toString()
                val inputString2 = todt.text.toString()

                try {
                    val date1 = myFormat.parse(inputString1)
                    val date2 = myFormat.parse(inputString2)
                    val diff = date2.time - date1.time
                    println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                    val date = Integer.parseInt(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString())
                    //if (date <= 7) {
                        productItems = ArrayList()
                        productItems1 = ArrayList()
                        productItems2 = ArrayList()

                        productItems!!.clear()
                        productItems1!!.clear()
                        productItems2!!.clear()
                        mRecyclerListitems.clear()
                        val reward = RewardpointsAsync()
                        reward.execute()
                   // } else {
                        /*try {

                            val alertBuilder = android.app.AlertDialog.Builder(this@Online_training)
                            alertBuilder.setCancelable(true)
                            alertBuilder.setTitle("Information")
                            alertBuilder.setMessage(Html.fromHtml("Last 7 days History only Show this page.If you want more history\nLog on our website\n<a href=\"www.v3healthzone.com\">www.v3healthzone.com</a>"))
                            alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss() }
                            val alert = alertBuilder.create()
                            alert.show()
                            (alert.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, "Date range should be in 7 days.", Toast.LENGTH_LONG).show()
                            Toast.makeText(applicationContext, "Visit website for more datas.", Toast.LENGTH_LONG).show()

                        }*/

                   // }

                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            } else {
                recyclerlist.visibility = View.GONE
                retry_lay.visibility = View.VISIBLE
            }
        }

        itemsAdapter = Rewardhistoryonlineadap(mRecyclerListitems, this, Rewardhistoryonlineadap.OnItemClickListener { view, position, viewType ->
            val item = mRecyclerListitems[position] as Rewardpointsbo
            //Log.e("clickresp", "value")

            //clikffed();
        })
        recyclerlist.adapter = itemsAdapter

        itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1[position] as Feedbackbo })


        /* recyclerlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {

                    if(productItems.size()>=limit){

                        start=limit+1;
                        limit+=20;
                        prog.setVisibility(View.VISIBLE);

                        Log.e("startval",String.valueOf(start));
                        Log.e("limit",String.valueOf(limit));

                        RewardpointsAsync reward = new RewardpointsAsync();
                        reward.execute();


                    }
                    else{

                    }

                    //Toast.makeText(Reward_history.this, "Last"+productItems.size()+limit, Toast.LENGTH_LONG).show();

                }
            }
        });*/

    }

    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            prog.visibility = View.VISIBLE
            recyclerlist.visibility = View.GONE

        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("from", frdt.text.toString())
                jobj.put("to", todt.text.toString())


                Log.i("rewardinput", Appconstants.specvid + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.specvid, jobj, "")
            } catch (e:Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp:String?) {
            try {
                Log.e("rewardresp",resp.toString())
            } catch (e: Exception) {
                Log.e("rewardrespcatch", e.toString())

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
                        val visual_time = JO.getString("visual")
                        //String username = jobject.getString("username");
                        val name = JO.getString("dat")
                        val uname = JO.getString("uname")

                        val whome = JO.getString("cutoff")

                        //String whomename = jobject.getString("whomename");
                        //String points = jobject.getString("points");
                        val type = JO.getString("status")

                        val fcolor = JO.getString("fcolor")
                        val fextra = JO.getString("fextra")

                        try {
                            feed = JO.getJSONArray("feedback")
                            Log.e("feeed val", i.toString())

                            productItems!!.add(Rewardpointsbo(i.toString(), fcolor, visual_time, fextra, name, uname, whome, "", "feed", type))

                            if (!feed!!.toString().isEmpty() && feed.toString() != "null") {
                                for (j in 0 until feed.length()) {
                                    val feedobj = feed.get(j) as JSONObject
                                    val id = feedobj.getString("id")
                                    val comment = feedobj.getString("comment")
                                    val types = feedobj.getString("type")
                                    val time = feedobj.getString("dtime")

                                    Log.e("comment val", i.toString())
                                    productItems2!!.add(Feedbackbo(id, types, comment, i.toString(), time))


                                }
                            }
                        } catch (e: Exception) {
                            Log.e("rewardrespnw", e.toString())
                            productItems!!.add(Rewardpointsbo(i.toString(), fcolor, visual_time, fextra, name, uname, whome, "", "notfeed", type))

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


    inner class DatepointsAsync : AsyncTask<String, String, String>() {
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

                Log.i("rewardinput", Appconstants.REWARDHIST + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.REWARDHIST, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String) {
            Log.e("rewardresp", resp)
            recyclerlist.visibility = View.VISIBLE
            progress_lay.visibility = View.GONE

            productItems = ArrayList()

            try {
                val obj = JSONObject(resp)

                val arrayLanguage = obj.getJSONArray("Response")

                for (i in 0 until arrayLanguage.length()) {
                    val JO = arrayLanguage.get(i) as JSONObject
                    val visual_time = JO.getString("visual")
                    //String username = jobject.getString("username");
                    val name = JO.getString("dat")
                    val uname = JO.getString("uname")

                    val whome = JO.getString("point")
                    //String whomename = jobject.getString("whomename");
                    //String points = jobject.getString("points");
                    val type = JO.getString("status")
                    productItems!!.add(Rewardpointsbo("", "", visual_time, "", name, uname, whome, "", "", type))

                }
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

            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("E VALUE", e.toString())
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
        val intent = Intent(this@Online_training, HomePage::class.java)
        startActivity(intent)
        finish()
    }

    fun clikffed(pos: String,time:String,cutoffs:String,feedextra:String) {
        try {
            visual_time = time
            cutoff = cutoffs
            val update = Dialog(this@Online_training)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE))


            val v = layoutInflater.inflate(R.layout.feedback_popup, null)
            val updatebut = v.findViewById(R.id.feedlist) as RecyclerView
            val scroll = v.findViewById(R.id.sroll) as ScrollView
            val nofeed = v.findViewById(R.id.textView18) as TextView
            val feed = v.findViewById<View>(R.id.textView15) as TextView
            val feedvt = v.findViewById<View>(R.id.textView15vtime) as TextView
            val feedcut =
                v.findViewById<View>(R.id.textView15vcut) as TextView

            val laterbut = v.findViewById(R.id.agree) as Button
            val textView88 =
                v.findViewById<View>(R.id.textView88) as TextView
            val download =
                v.findViewById<View>(R.id.download) as Button

            feed.text = "Feedback - " + utils.loadName()
            feedvt.text = "Visual Time - $time"
            feedcut.text = "Cutoff Time - $cutoffs"

            update.setContentView(v)
            val window = update.window
            window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )

            update.setCancelable(false)
            update.show()


            for (k in productItems2!!.indices) {
                Log.e("VALUEGET", productItems2!!.get(k).points)
                if (pos == productItems2!!.get(k).points) {
                    productItems1!!.add(Feedbackbo(productItems2!!.get(k).id, productItems2!!.get(k).gettype(), productItems2!!.get(k).getcomment(), "", productItems2!!.get(k).gettime()))
                    feedback = productItems2!![k].getcomment()

                }
            }


            if (productItems1!!.isEmpty()) {
                nofeed.visibility = View.VISIBLE


            }

            mRecyclerListitems1.addAll(productItems1!!)
            itemsAdapter1.notifyDataSetChanged()

            val mLayoutManager3 = LinearLayoutManager(this@Online_training, LinearLayoutManager.VERTICAL, false)
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

                var imagePath: File? =null

                if(time != "null") {
                    imagePath=File(
                        Environment.getExternalStorageDirectory().absoluteFile
                            .toString() + "/V3 Online TV/Online training details/" + time2 + utils.loadName() + ".png"
                    )
                }
                else{
                    imagePath=File(
                        Environment.getExternalStorageDirectory().absoluteFile
                            .toString() + "/V3 Online TV/Online training details/" + feedextra + utils.loadName() + ".png")
                }
                if (imagePath.exists()) {
                    download.setText("DOWNLOADED")
                    download.isEnabled=false
                    textView88.setVisibility(View.VISIBLE)
                    if(time != "null") {

                        textView88.setText("Downloaded Path : " + "storage/V3 Online TV/Online training details/" + time2 + utils.loadName() + ".png")
                    }
                    else{
                        textView88.setText("Downloaded Path : " + "storage/V3 Online TV/Online training details/" + feedextra + utils.loadName() + ".png")

                    }
                    }
                else {

                }
            }
            catch (e:Exception){

            }
            download.setOnClickListener(View.OnClickListener {
                if (checkPermission()&&download.text.toString()=="DOWNLOAD") {
                    try {
                        val bitmap =takeScreenShot(update)
                        if (time != "null") {
                            saveBitmap(bitmap!!, time, download, textView88)
                        } else {
                            saveBitmap(bitmap!!, feedextra, download, textView88)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                        Log.e("filerr", e.toString())
                    }
                } else {
                    requestPermission()
                }
            })


        } catch (e: Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }

    }
    private fun takeScreenShot(activity: Dialog): Bitmap? {
        val v1 = activity.window!!.decorView.rootView
        v1.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(v1.drawingCache)
        v1.isDrawingCacheEnabled = false
        Log.e("Screenshot", "taken successfully")
        return bitmap
    }

    fun saveBitmap(
        bitmap: Bitmap,
        time: String,
        button: Button,
        tt: TextView
    ) {
        val times = time.replace("-".toRegex(), "_")
        val times1 = times.replace(":".toRegex(), "_")
        val time2 = times1.trim { it <= ' ' }
        val directory = File(
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + "V3 Online TV/Online training details"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val imagePath = File(
            Environment.getExternalStorageDirectory().absoluteFile
                .toString() + "/V3 Online TV/Online training details/" + time2 + utils.loadName() + ".png"
        )
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
                "Downloaded Path : " + "storage/V3 Online TV/Online training details/" + time2 + utils.loadName() + ".png"
            save()
            button.setOnClickListener {
                if (button.text.toString() == "Open") {
                    //openScreenshot(imagePath)
                }
            }
        } catch (e: FileNotFoundException) {
            Log.e("GREC", e.message, e)
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
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
          PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                    .show()

                // main logic
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT)
                    .show()
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

    fun save() {
        val submit = Videosubmit()
        submit.execute("Download")
    }

    inner class Videosubmit :
        AsyncTask<String?, String?, String?>() {
        var pdialog: ProgressDialog? = null
        override fun onPreExecute() {
            pdialog = ProgressDialog(this@Online_training)
            pdialog!!.setMessage("Saving...")
            pdialog!!.setCancelable(false)
            pdialog!!.show()
        }

         override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            val versionCode = BuildConfig.VERSION_CODE
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("visual_time", visual_time)
                jobj.put("cutoff", cutoff)
                jobj.put("feedback", feedback)
                jobj.put("status", params[0])
                jobj.put("type", "Online Training")

                Log.i("Videossubmitinput", Appconstants.download + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.download, jobj, "")
            } catch (e: java.lang.Exception) {
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
                        Toast.makeText(this@Online_training, "Saved.", Toast.LENGTH_LONG).show()
                    } else {
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this@Online_training)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }




    }

