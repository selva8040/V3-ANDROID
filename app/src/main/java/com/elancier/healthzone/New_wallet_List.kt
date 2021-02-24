package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Pointsadap
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

class New_wallet_List : AppCompatActivity() {
    internal lateinit var itemsAdapter: Pointsadap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils
    var progbar: Dialog? = null
    var prog:android.app.Dialog? = null
    var otp_edit_box1: EditText? = null
    var otp_edit_box2: EditText? = null
    var update:Dialog? = null
    var tot=0F
    var crore=""
    var from=""
    var welwallet=""

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point__list)
        utils = Utils(applicationContext)

        try{
            val from=intent.extras
            crore=from!!.getString("from").toString()

        }
        catch (e:Exception){

        }

        if(crore=="welcome"){
            supportActionBar!!.title = "Welcome Redeem Wallet"

        }
        else{
            supportActionBar!!.title = "Redeem Wallet"

        }

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()

        itemsAdapter = Pointsadap(mRecyclerListitems, this, object :
            Pointsadap.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int, viewType:Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo
                // Log.e("clickresp", "value")

                //clikffed();
            }
        })
        recyclerlist.adapter = itemsAdapter




        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })


        if (!utils.loadWallet().isEmpty()&&crore!="crore") {
            val point =
                utils.loadWallet().toString().split("\\.".toRegex(), 2).toTypedArray()[0]
            println("point$point")
            try {
                if (!point.isEmpty()) {
                    val d = point.replace(",".toRegex(), "").toInt()
                    tot=d.toFloat()
                    if (d >= 500) {
                        addsalary.setVisibility(View.VISIBLE)
                    } else {
                        addsalary.setVisibility(View.GONE)
                    }
                }
            }
            catch (e:Exception){

            }
        }

        if (!utils.loadbasewallet().isEmpty()&&(crore=="crore"||crore=="welcome")) {
            val point =
                utils.loadbasewallet().toString().split("\\.".toRegex(), 2).toTypedArray()[0]
            println("point$point")
            try {
                if (!point.isEmpty()) {
                    val d = point.replace(",".toRegex(), "").toInt()
                    tot=d.toFloat()
                    if (d >= 500) {
                        addsalary.setVisibility(View.VISIBLE)
                    } else {
                        addsalary.setVisibility(View.GONE)
                    }
                }
            }
            catch (e:Exception){

            }
        }
        //loadprogress()

        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                finish()

                if(crore!="crore"&&crore!="welcome") {
                    startActivity(Intent(this@New_wallet_List, New_wallet_List::class.java))
                    swipeToRefresh.setRefreshing(false)
                }
                else if(crore=="crore"){
                    startActivity(Intent(this@New_wallet_List, New_wallet_List::class.java).putExtra("from","crore"))
                    swipeToRefresh.setRefreshing(false)
                }
                else if(crore=="welcome"){
                    startActivity(Intent(this@New_wallet_List, New_wallet_List::class.java).putExtra("from","welcome"))
                    swipeToRefresh.setRefreshing(false)
                }
            }
        })

        addsalary.setOnClickListener{
            try {
                 update = Dialog(this@New_wallet_List)
                update!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                update!!.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE)
                )
                val vs = layoutInflater.inflate(R.layout.new_wallet_popup, null)
                val updatebut =
                    vs.findViewById<View>(R.id.textView81) as TextView
                // laterbut=(ImageView) vs.findViewById(R.id.img);
                //TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                 otp_edit_box1 = vs.findViewById<View>(R.id.otp_edit_box1) as EditText
                otp_edit_box2 = vs.findViewById<View>(R.id.otp_edit_box2) as EditText

                if(crore=="crore"){
                    otp_edit_box1!!.setText(utils.loadbasewallet())

                }
                else if(crore=="welcome"){
                    otp_edit_box1!!.setText(welwallet)

                }
                else{
                    otp_edit_box1!!.setText(utils.loadWallet())

                }

                val button14 =
                    vs.findViewById<View>(R.id.button14) as Button


                otp_edit_box2!!.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(s: Editable) {

                    }

                    override fun beforeTextChanged(s: CharSequence, start: Int,
                                                   count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int,
                                               before: Int, count: Int) {

                    }
                })


                button14.setOnClickListener(View.OnClickListener {
                    Log.e("click", "cli")


                    if (!otp_edit_box1!!.getText().toString().isEmpty()&&
                        otp_edit_box2!!.getText().toString().isNotEmpty()
                    ) {

                            if(otp_edit_box2!!.text.toString().toFloat()<500||otp_edit_box2!!.text.toString().toInt()>=tot){

                                if(otp_edit_box2!!.text.toString().toFloat()<500) {
                                    otp_edit_box2!!.setText(null)

                                    toast("Amount should be greater than 500")
                                }
                                else if(otp_edit_box2!!.text.toString().toFloat()>=tot){
                                    otp_edit_box2!!.setText(null)

                                    toast("Could not request full amount from wallet\n Wallet will remain ₹1 atleast.")

                                }
                            }
                            else{

                                if(otp_edit_box2!!.text.toString().toInt()<tot) {

                                    if(BuildConfig.VERSION_CODE>=43) {
                                        val task = ChangepinTask()
                                        task.execute()
                                        button14.isEnabled = false
                                    }
                                    else{
                                        toast("Please update the latest version of V3 Online TV.")

                                    }
                                }
                                else{
                                    toast("Enter lesser amount")
                                    otp_edit_box2!!.setText(null)
                                }
                            }



                    } else {
                            toast("Please fill above fields")
                    }
                })
                update!!.setContentView(vs)
                update!!.setCancelable(true)
                update!!.show()

                updatebut.setOnClickListener { // clickupdate="true";
                    update!!.dismiss()
                }

            } catch (e: java.lang.Exception) {
                Log.e("er", e.toString())
            }
        }

    }
    inner class RewardpointsAsync:AsyncTask<String, String, String>() {
        override fun onPreExecute()
        {

            progress_lay.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE
            loadprogress()

        }

        override fun doInBackground(vararg strings:String): String? {
            var result:String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "List")
                if(crore=="crore"){
                    jobj.put("types", "Base Pin")

                }
                else if(crore=="welcome"){
                    jobj.put("types", "Welcome Pin")

                }
                else{
                    jobj.put("types","")

                }

                Log.i("rewardinput", Appconstants.wallet_request + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.wallet_request, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp:String?) {
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
                        val wallet=obj.getJSONObject(0).getString("wallet");
                        val base_wallet=obj.getJSONObject(0).getString("base_wallet");
                        welwallet=obj.getJSONObject(0).getString("wel_wallet");
                        utils.savePreferences("wallet",wallet)
                        utils.savePreferences("base_wallet",base_wallet)

                        val arrayLanguage = obj.getJSONObject(0).getJSONArray("Response")

                        for (i in 0 until arrayLanguage.length()) {
                            val JO = arrayLanguage.get(i) as JSONObject
                            val frmdate = JO.getString("uname")
                            val todate = JO.getString("status")
                            val feedback_absent = JO.getString("dtime")
                            val frombank=  JO.getString("frombank")
                            val tid=  JO.getString("tid")
                            val vdtime= JO.getString("vdtime")
                            val feedback_absents=JO.getString("request")
                            val reason=JO.getString("reason")

                            if(todate=="0"){
                                addsalary.visibility=View.GONE
                            }
                            else{
                                addsalary.visibility=View.VISIBLE

                            }

                            try {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid ,feedback_absents, reason, ""))
                            } catch (e: Exception) {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid , feedback_absents, reason, ""))
                            }
                        }
                        nodata.visibility=View.GONE
                        recyclerlist.visibility=View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()
                    }
                    else{
                        progbar!!.dismiss()
                        nodata.visibility=View.VISIBLE
                        recyclerlist.visibility=View.GONE
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
                if(crore=="crore"){
                    jobj.put("types", "Base Pin")

                }
                else if(crore=="welcome"){
                    jobj.put("types", "Welcome Pin")

                }
                else{
                    jobj.put("types","")

                }

                jobj.put("point", d)
                jobj.put("request", otp_edit_box2!!.text.toString())

                Log.i(
                    "HomePage Input",
                    Appconstants.wallet_request + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.wallet_request, jobj, "")
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

                            toast("Redeemed successfully.")
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
            val update = Dialog(this@New_wallet_List)
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
            val update = Dialog(this@New_wallet_List)
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
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
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