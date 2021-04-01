package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.elancier.healthzone.Adapter.Crore_Rechargeadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import kotlinx.android.synthetic.main.common_layout.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Crorepathi_recharge : AppCompatActivity() {
    internal lateinit var itemsAdapter: Crore_Rechargeadap
    private val mRecyclerListitems = ArrayList<Any>()
    private var productItems:MutableList<Rewardpointsbo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils
    var progbar: Dialog? = null
    var prog:android.app.Dialog? = null
    var otp_edit_box1:EditText? = null
    var otp_edit_box2:EditText? = null
    var otp_edit_box3:EditText?=null
    var otp_edit_box4:EditText?=null
    var otp_edit_box5:EditText?=null
    var otp_edit_box6:EditText?=null
    var otp_edit_box7:EditText?=null
    var otp_edit_box8:EditText?=null
    var otp_edit_box9:EditText?=null
    var otp_edit_box10:EditText?=null
    var otp_edit_box11:EditText?=null
    var otp_edit_box12:EditText?=null
    var root_otp_layout2:LinearLayout?=null
    var root_otp_layout3:LinearLayout?=null
    var root_otp_layout4:LinearLayout?=null
    var root_otp_layout5:LinearLayout?=null
    var root_otp_layout6:LinearLayout?=null
    var update:Dialog? = null
    var tot=0F
    var picker: DatePickerDialog? = null
    var pdialog: ProgressDialog? = null
    var otp_edit_box1str="";
    var from="";
    var to="";
    var next_from="";
    var next_to="";
    var last_from="";
    var last_to="";
    var last_from1=""
    var last_to1=""
    var last_from2=""
    var last_to2=""
    var last_from3=""
    var last_to3=""
    var button14:Button?=null
    var check_tod = "";
    var froms=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point__list)
        utils = Utils(applicationContext)

        supportActionBar!!.title = "Recharge"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = ArrayList()
        itemsAdapter = Crore_Rechargeadap(mRecyclerListitems, this, object :
            Crore_Rechargeadap.OnItemClickListener {
            override fun OnItemClick(view: View, position:Int, viewType:Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo
                // Log.e("clickresp", "value")

                //clikffed();
            }
        })
        recyclerlist.adapter = itemsAdapter

        //itemsAdapter1 = Rewardfeedadap(mRecyclerListitems1, applicationContext, Rewardfeedadap.OnItemClickListener { view, position, viewType -> val item = mRecyclerListitems1.get(position) as Feedbackbo })

        addsalary.setVisibility(View.VISIBLE)


        swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                //shuffle()
                //finish()
                onResume()
                //startActivity(Intent(this@Crorepathi_recharge,Crorepathi_recharge::class.java))
                swipeToRefresh.setRefreshing(false)
            }
        })

        try{
            froms=intent.extras!!.getString("from").toString()
        }
        catch (e:Exception){

        }

        addsalary.setOnClickListener{
            try {
                println("check_tod : "+check_tod)
                 update = Dialog(this@Crorepathi_recharge)
                update!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                update!!.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE)
                )
                val vs = layoutInflater.inflate(R.layout.crore_recharge_popup, null)
                val updatebut = vs.findViewById<View>(R.id.textView81) as TextView
                val up = vs.findViewById<View>(R.id.textView80) as TextView//sk
                up.setText("Recharge")//sk
                // laterbut=(ImageView) vs.findViewById(R.id.img);
                //TextView titlename=(TextView) vs.findViewById(R.id.titlename);
                otp_edit_box1 = vs.findViewById<View>(R.id.otp_edit_box1) as EditText
                otp_edit_box2 = vs.findViewById<View>(R.id.otp_edit_box2) as EditText
                otp_edit_box3 = vs.findViewById<View>(R.id.otp_edit_box3) as EditText
                otp_edit_box4 = vs.findViewById<View>(R.id.otp_edit_box4) as EditText
                otp_edit_box5 = vs.findViewById<View>(R.id.otp_edit_box5) as EditText
                otp_edit_box6 = vs.findViewById<View>(R.id.otp_edit_box6) as EditText
                otp_edit_box7 = vs.findViewById<View>(R.id.otp_edit_box7) as EditText
                otp_edit_box8 = vs.findViewById<View>(R.id.otp_edit_box8) as EditText
                otp_edit_box9 = vs.findViewById<View>(R.id.otp_edit_box9) as EditText
                otp_edit_box10 = vs.findViewById<View>(R.id.otp_edit_box10) as EditText
                otp_edit_box11 = vs.findViewById<View>(R.id.otp_edit_box11) as EditText
                otp_edit_box12 = vs.findViewById<View>(R.id.otp_edit_box12) as EditText
                root_otp_layout2=vs.findViewById<View>(R.id.root_otp_layout2) as LinearLayout
                root_otp_layout3=vs.findViewById<View>(R.id.root_otp_layout3) as LinearLayout
                root_otp_layout4=vs.findViewById<View>(R.id.root_otp_layout4) as LinearLayout
                root_otp_layout5=vs.findViewById<View>(R.id.root_otp_layout5) as LinearLayout
                root_otp_layout6=vs.findViewById<View>(R.id.root_otp_layout6) as LinearLayout
               // otp_edit_box1!!.setText(utils.loadWallet())
                 button14 =
                    vs.findViewById<View>(R.id.button14) as Button
               // otp_edit_box1!!.setText(otp_edit_box1str)
                button14!!.visibility=View.VISIBLE
                otp_edit_box3!!.setOnClickListener {
                    val cldr = Calendar.getInstance()
                    val day = cldr.get(Calendar.DAY_OF_MONTH)
                    val month = cldr.get(Calendar.MONTH)
                    val year = cldr.get(Calendar.YEAR)
                    // date picker dialog
                    picker = DatePickerDialog(this@Crorepathi_recharge,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            otp_edit_box3!!.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                            /*ChangepinTask_date().execute()
                            val k=SimpleDateFormat("yyyy-M-d")
                            val dt=k.format(Calendar.getInstance().time)
                            println("dt"+dt);
                            if(otp_edit_box1!!.text.toString()==dt){
                                toast("Unable to select today date")
                                otp_edit_box1!!.setText(null)
                            }
                            else{

                            }*/
                            /*if(check_tod.isNotEmpty()) {
                                val str_date = check_tod
                                val formatter = SimpleDateFormat("yyyy-MM-dd")
                                val min = formatter.parse(str_date) as Date
                                System.out.println("min is " + min.time)
                                view.minDate = min.time
                            }else{
                                System.out.println("check_tod is " +check_tod)
                            }*/

                          /*  if (!otp_edit_box2!!.text.toString().isEmpty()) {
                                if (CheckNetwork.isInternetAvailable(this@Crorepathi_recharge)) {
                                    //start = 0
                                    // limit = 20
                                    val myFormat = SimpleDateFormat("yyyy-MM-dd")
                                    val inputString1 = otp_edit_box1!!.text.toString()
                                    val inputString2 = otp_edit_box2!!.text.toString()

                                    try {
                                        val date1 = myFormat.parse(inputString1)
                                        val date2 = myFormat.parse(inputString2)
                                        val diff = date2.time - date1.time
                                        println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))

                                        if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()+1==90){
                                            button14!!.visibility=View.VISIBLE
                                        }
                                        else{
                                            button14!!.visibility=View.GONE
                                            if(otp_edit_box2!!.text.toString().isNotEmpty()){
                                                otp_edit_box2!!.setText(null)
                                            }
                                            toast("From - To limit should be 90 days")
                                        }

                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }


                                } else {
                                    //recyclerlist.visibility = View.GONE
                                    toast("You're offline")
                                }
                            } else {
                               // Toast.makeText(this@New_recharge_List, "Select 'To' date", Toast.LENGTH_LONG).show()
                            }*/
                        }, year, month, day)

                  /*  if (check_tod.isNotEmpty()){
                        val str_date = check_tod

                        val formatter = SimpleDateFormat("yyyy-MM-dd")
                        val min = getNextDate(check_tod)//formatter.parse(str_date) as Date
                        System.out.println("min is " + min.time)
                        //view.minDate = min.time
                        picker!!.datePicker.minDate= min.time
                    }*/

                    picker!!.show()
                }

                otp_edit_box4!!.setOnClickListener {
                    val cldr = Calendar.getInstance()
                    val day = cldr.get(Calendar.DAY_OF_MONTH)
                    val month = cldr.get(Calendar.MONTH)
                    val year = cldr.get(Calendar.YEAR)
                    // date picker dialog
                    picker = DatePickerDialog(this@Crorepathi_recharge,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            otp_edit_box4!!.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)
                            /*ChangepinTask_date().execute()
                            val k=SimpleDateFormat("yyyy-M-d")
                            val dt=k.format(Calendar.getInstance().time)
                            println("dt"+dt);
                            if(otp_edit_box1!!.text.toString()==dt){
                                toast("Unable to select today date")
                                otp_edit_box1!!.setText(null)
                            }
                            else{

                            }*/
                            /*if(check_tod.isNotEmpty()) {
                                val str_date = check_tod
                                val formatter = SimpleDateFormat("yyyy-MM-dd")
                                val min = formatter.parse(str_date) as Date
                                System.out.println("min is " + min.time)
                                view.minDate = min.time
                            }else{
                                System.out.println("check_tod is " +check_tod)
                            }*/

                            /*  if (!otp_edit_box2!!.text.toString().isEmpty()) {
                                  if (CheckNetwork.isInternetAvailable(this@Crorepathi_recharge)) {
                                      //start = 0
                                      // limit = 20
                                      val myFormat = SimpleDateFormat("yyyy-MM-dd")
                                      val inputString1 = otp_edit_box1!!.text.toString()
                                      val inputString2 = otp_edit_box2!!.text.toString()

                                      try {
                                          val date1 = myFormat.parse(inputString1)
                                          val date2 = myFormat.parse(inputString2)
                                          val diff = date2.time - date1.time
                                          println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))

                                          if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()+1==90){
                                              button14!!.visibility=View.VISIBLE
                                          }
                                          else{
                                              button14!!.visibility=View.GONE
                                              if(otp_edit_box2!!.text.toString().isNotEmpty()){
                                                  otp_edit_box2!!.setText(null)
                                              }
                                              toast("From - To limit should be 90 days")
                                          }

                                      } catch (e: ParseException) {
                                          e.printStackTrace()
                                      }


                                  } else {
                                      //recyclerlist.visibility = View.GONE
                                      toast("You're offline")
                                  }
                              } else {
                                 // Toast.makeText(this@New_recharge_List, "Select 'To' date", Toast.LENGTH_LONG).show()
                              }*/
                        }, year, month, day)

                    /*  if (check_tod.isNotEmpty()){
                          val str_date = check_tod

                          val formatter = SimpleDateFormat("yyyy-MM-dd")
                          val min = getNextDate(check_tod)//formatter.parse(str_date) as Date
                          System.out.println("min is " + min.time)
                          //view.minDate = min.time
                          picker!!.datePicker.minDate= min.time
                      }*/

                    picker!!.show()
                }

               /* otp_edit_box2!!.setOnClickListener {
                    val cldr = Calendar.getInstance()
                    val day = cldr.get(Calendar.DAY_OF_MONTH)
                    val month = cldr.get(Calendar.MONTH)
                    val year = cldr.get(Calendar.YEAR)
                    // date picker dialog
                    picker = DatePickerDialog(this@New_recharge_List,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            otp_edit_box2!!.setText(year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth)

                            val k=SimpleDateFormat("yyyy-M-d")
                            val dt=k.format(Calendar.getInstance().time)
                            println("dt"+dt);
                            if(otp_edit_box2!!.text.toString()==dt){
                                toast("Unable to select today date")
                                otp_edit_box2!!.setText(null)
                            }
                            else{

                            }

                            if (!otp_edit_box1!!.text.toString().isEmpty()) {
                                if (CheckNetwork.isInternetAvailable(this@New_recharge_List)) {
                                    //start = 0
                                    // limit = 20
                                    val myFormat = SimpleDateFormat("yyyy-MM-dd")
                                    val inputString1 = otp_edit_box1!!.text.toString()
                                    val inputString2 = otp_edit_box2!!.text.toString()

                                    try {
                                        val date1 = myFormat.parse(inputString1)
                                        val date2 = myFormat.parse(inputString2)
                                        val diff = date2.time - date1.time
                                        println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
                                        if(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toInt()+1==90){
                                            button14.visibility=View.VISIBLE
                                        }
                                        else{
                                            button14.visibility=View.GONE
                                            if(otp_edit_box2!!.text.toString().isNotEmpty()){
                                                otp_edit_box2!!.setText(null)
                                            }
                                            toast("From - To limit should be 90 days")
                                        }

                                    } catch (e: ParseException) {
                                        e.printStackTrace()
                                    }


                                } else {
                                    //recyclerlist.visibility = View.GONE
                                    toast("You're offline")
                                }
                            } else {
                               // Toast.makeText(this@New_recharge_List, "Select 'From' date", Toast.LENGTH_LONG).show()
                            }
                        }, year, month, day)

                    picker!!.datePicker.maxDate=System.currentTimeMillis()
                    picker!!.show()
                }*/

                button14!!.setOnClickListener(View.OnClickListener {
                    Log.e("click", "cli")
                    if (otp_edit_box3!!.getText().toString().isNotEmpty()&&
                                BuildConfig.VERSION_CODE>=43
                    ) {
                            val task = ChangepinTask()
                            task.execute()
                            button14!!.isEnabled = false

                    } else {
                        if(BuildConfig.VERSION_CODE<43){
                            toast("Please update the latest version of V3 Online TV app.")
                        }

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
    fun getNextDate(curDate: String?): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(curDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, 0)
        return calendar.time// format.format(calendar.time)
    }
    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progress_lay.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE

            pdialog = ProgressDialog(this@Crorepathi_recharge)
            pdialog!!.setMessage("Loading...")
            pdialog!!.setCancelable(false)
            pdialog!!.show()

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())

                if(froms=="one") {
                    jobj.put("type", "list1")
                }
                else if(froms=="two"){
                    jobj.put("type", "list2")

                }

                Log.i("rewardinput",  Appconstants.crore_recharge + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.crore_recharge,jobj, "")
            } catch (e:Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp:String?) {
            try {
                //Log.e("rewardresp", resp)
            } catch (e: Exception) {
                //Log.e("rewardrespcatch", e.toString())

            }

            recyclerlist.visibility = View.VISIBLE
            progress_lay.setVisibility(View.GONE)
            pdialog!!.dismiss()

            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)
                    if(obj.getJSONObject(0).getString("Status").equals("Success")) {
                        val arrayLanguage = obj.getJSONObject(0).getJSONArray("Response")
                        for (i in 0 until arrayLanguage.length()) {

                            val JO = arrayLanguage.get(i) as JSONObject
                            val frmdate = JO.getString("uname")
                            val todate = JO.getString("status")
                            val feedback_absent = JO.getString("dtime")
                            val frombank=  JO.getString("rdate")
                            val tid = ""
                            val vdtime= JO.getString("date")
                            val frombanks= ""
                            var tids=""
                            val vdtimes= JO.getString("adate")
                            val reject= JO.getString("remark")
                            if (i==0) {
                                //check_tod = tid
                            }
                            if(todate=="2"){
                                tids =reject;
                                addsalary.visibility=View.VISIBLE

                            }
                            if(todate=="1"){
                                tids =reject;
                                addsalary.visibility=View.GONE

                            }
                          //  val reject= JO.getString("reject")
                            if(todate=="0"){
                                addsalary.visibility=View.GONE
                            }

                            try {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid ,frombanks, tids, vdtimes))
                            } catch (e: Exception) {
                                productItems!!.add(Rewardpointsbo(i.toString(), frmdate, todate,feedback_absent,frombank,vdtime,tid , frombanks, tids, vdtimes))
                            }
                        }
                        nodata.visibility=View.GONE
                        recyclerlist.visibility=View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()
                    }
                    else{
                        pdialog!!.dismiss()

                        nodata.visibility=View.VISIBLE
                        recyclerlist.visibility=View.GONE
                    }

                } else {
                    nodata.visibility=View.VISIBLE
                    recyclerlist.visibility=View.GONE
                    pdialog!!.dismiss()

                }
            } catch (e: JSONException) {
                e.printStackTrace()
                pdialog!!.dismiss()

                Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter.notifyDataSetChanged()
                nodata.visibility=View.VISIBLE
                recyclerlist.visibility=View.GONE
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
            val con = Connection()

            try {
                val d = otp_edit_box1!!.text.toString().replace(",".toRegex(), "")
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("rdate", otp_edit_box3!!.text.toString() )
               // jobj.put("rdate2", otp_edit_box4!!.text.toString())
                if(froms=="one") {
                    jobj.put("type", "add1")
                }
                else if(froms=="two"){
                    jobj.put("type", "add2")

                }
                Log.i(
                    "HomePage Input",
                    Appconstants.crore_recharge + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.crore_recharge, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("error", e.toString())
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                if (resp != null) {
                    //val jsonarr = JSONArray(resp)
                    val json = JSONObject(resp)
                    if (json.getString("Status") == "Success") {
                        //JSONObject jarr = json.getJSONObject("Response");
                        progbar!!.dismiss()

                        try {
                            toast("Requested successfully.")
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

                    progbar!!.dismiss()
                    update!!.dismiss()

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("err", e.toString())
                progbar!!.dismiss()
                update!!.dismiss()

            }
        }
    }

    inner class ChangepinTask_date :
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
               // val d = otp_edit_box1!!.text.toString().replace(",".toRegex(), "")
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type","Dates")
                jobj.put("date",otp_edit_box1!!.text.toString().trim())

                Log.i(
                    "HomePage Input",
                    Appconstants.sal_req + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.sal_req, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("error", e.toString())
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                if (resp != null) {
                    //val jsonarr = JSONArray(resp)
                    Log.e("errort",resp)
                    val json = JSONObject(resp)
                    if (json.getString("Status") == "Success") {

                        progbar!!.dismiss()
                        try {
                            val jarr = json.getJSONObject("Response");
                            //otp_edit_box1str=(json.getString("Response"))
                            from=jarr.getString("from")
                            to=jarr.getString("to")
                            next_from=jarr.getString("next_from")
                            next_to=jarr.getString("next_to")
                            last_from=jarr.getString("last_from")
                            last_to=jarr.getString("last_to")
                            last_from1=jarr.getString("last_from1")
                            last_to1=jarr.getString("last_to1")
                            last_from2=jarr.getString("last_from2")
                            last_to2=jarr.getString("last_to2")
                            last_from3=jarr.getString("last_from3")
                            last_to3=jarr.getString("last_to3")

                            if(to.isNotEmpty()){
                                otp_edit_box2!!.visibility=View.VISIBLE
                                otp_edit_box2!!.setText(to);
                            }
                            if(next_from.isNotEmpty()){
                                root_otp_layout2!!.visibility=View.VISIBLE
                                otp_edit_box3!!.setText(next_from);
                            }
                            if(next_to.isNotEmpty()){
                                root_otp_layout2!!.visibility=View.VISIBLE
                                otp_edit_box4!!.setText(next_to);
                            }
                            if(last_from.isNotEmpty()){
                                root_otp_layout3!!.visibility=View.VISIBLE
                                otp_edit_box5!!.setText(last_from);
                            }
                            if(last_to.isNotEmpty()){
                                root_otp_layout3!!.visibility=View.VISIBLE
                                otp_edit_box6!!.setText(last_to);
                            }
                            if(last_from1.isNotEmpty()){
                                root_otp_layout4!!.visibility=View.VISIBLE
                                otp_edit_box7!!.setText(last_from1);
                            }
                            if(last_to1.isNotEmpty()){
                                root_otp_layout4!!.visibility=View.VISIBLE
                                otp_edit_box8!!.setText(last_to1);
                            }
                            if(last_from2.isNotEmpty()){
                                root_otp_layout5!!.visibility=View.VISIBLE
                                otp_edit_box9!!.setText(last_from2);
                            }
                            if(last_to2.isNotEmpty()){
                                root_otp_layout5!!.visibility=View.VISIBLE
                                otp_edit_box10!!.setText(last_to2);
                            }
                            if(last_from3.isNotEmpty()){
                                root_otp_layout6!!.visibility=View.VISIBLE
                                otp_edit_box11!!.setText(last_from3);
                            }
                            if(last_to3.isNotEmpty()){
                                root_otp_layout6!!.visibility=View.VISIBLE
                                otp_edit_box12!!.setText(last_to3);
                            }

                            if (otp_edit_box1!!.getText().toString().isNotEmpty()&&
                                otp_edit_box2!!.getText().toString().isNotEmpty()&&
                                otp_edit_box3!!.getText().toString().isNotEmpty()&&
                                otp_edit_box4!!.getText().toString().isNotEmpty()&&
                                otp_edit_box5!!.getText().toString().isNotEmpty()&&
                                otp_edit_box6!!.getText().toString().isNotEmpty()&&
                                otp_edit_box7!!.getText().toString().isNotEmpty()&&
                                otp_edit_box8!!.getText().toString().isNotEmpty()&&
                                otp_edit_box9!!.getText().toString().isNotEmpty()&&
                                otp_edit_box10!!.getText().toString().isNotEmpty()&&
                                otp_edit_box11!!.getText().toString().isNotEmpty()&&
                                otp_edit_box12!!.getText().toString().isNotEmpty()
                            ) {
                                button14!!.visibility=View.VISIBLE
                            }
                            else{
                                button14!!.visibility=View.GONE
                                toast("${jarr.getString("msg")}")
                            }

                        } catch (e: java.lang.Exception) {
                            progbar!!.dismiss()
                        }
                    } else {
                        progbar!!.dismiss()
                        //update!!.dismiss()
                        toast(json.getString("Response"))
                    }
                } else {
                    //retry.show();
                    progbar!!.dismiss()
                  //  update!!.dismiss()

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("err", e.toString())
                progbar!!.dismiss()

            }
        }
    }

    inner class ChangepinTask_dates :
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
            var result:String? = null
            val con =
                Connection()
            try {
                // val d = otp_edit_box1!!.text.toString().replace(",".toRegex(), "")
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type","Date")
               // jobj.put("date",otp_edit_box1!!.text.toString().trim())

                Log.i(
                    "HomePage Input",
                    Appconstants.sal_req + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.sal_req, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("error", e.toString())
            }
            return result
        }

        override fun onPostExecute(resp:String?) {
            try {
                if (resp != null) {
                    //val jsonarr = JSONArray(resp)
                    Log.e("errort",resp)
                    val json = JSONObject(resp)
                    if (json.getString("Status") == "Success") {

                        progbar!!.dismiss()
                        try {
                            check_tod= json.getString("Response");
                            //otp_edit_box1str=(json.getString("Response"))

                           /* to=jarr.getString("to")
                            next_from=jarr.getStgetSring("next_from")
                            next_to=jarr.getString("next_to")
                            last_from=jarr.getString("last_from")
                            last_to=jarr.getString("last_to")

                            if(to.isNotEmpty()){
                                otp_edit_box2!!.visibility=View.VISIBLE
                                otp_edit_box2!!.setText(to);
                            }
                            if(next_from.isNotEmpty()){
                                root_otp_layout2!!.visibility=View.VISIBLE
                                otp_edit_box3!!.setText(next_from);
                            }
                            if(next_to.isNotEmpty()){
                                root_otp_layout2!!.visibility=View.VISIBLE
                                otp_edit_box4!!.setText(next_to);
                            }
                            if(last_from.isNotEmpty()){
                                root_otp_layout3!!.visibility=View.VISIBLE
                                otp_edit_box5!!.setText(last_from);
                            }
                            if(last_to.isNotEmpty()){
                                root_otp_layout3!!.visibility=View.VISIBLE
                                otp_edit_box6!!.setText(last_to);
                            }

                            if (otp_edit_box1!!.getText().toString().isNotEmpty()&&
                                otp_edit_box2!!.getText().toString().isNotEmpty()&&
                                otp_edit_box3!!.getText().toString().isNotEmpty()&&
                                otp_edit_box4!!.getText().toString().isNotEmpty()&&
                                otp_edit_box5!!.getText().toString().isNotEmpty()&&
                                otp_edit_box6!!.getText().toString().isNotEmpty()
                            ) {
                                button14!!.visibility=View.VISIBLE
                            }
                            else{
                                button14!!.visibility=View.GONE
                                toast("${jarr.getString("msg")}")
                                //update!!.dismiss()

                            }*/
                            // toast("Requested successfully.")
                            //update!!.dismiss()
                            // onResume()
                        } catch (e: java.lang.Exception) {
                            progbar!!.dismiss()
                        }
                    } else {
                        progbar!!.dismiss()
                        //update!!.dismiss()
                        toast(json.getString("Response"))
                    }
                } else {
                    //retry.show();
                    progbar!!.dismiss()
                    //  update!!.dismiss()

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("err", e.toString())
                progbar!!.dismiss()
                //update!!.dismiss()


                //retry.show();
            }
        }
    }


    fun clikffed(pos: String, time: String,time1:String ,time2:String) {
        try {
            val update = Dialog(this@Crorepathi_recharge)
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
            val update = Dialog(this@Crorepathi_recharge)
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
            laterbut.setOnClickListener  {
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
        //ChangepinTask_dates().execute()
    }
}