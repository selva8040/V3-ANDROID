package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_super__salary.*
import kotlinx.android.synthetic.main.activity_super__salary.address
import kotlinx.android.synthetic.main.activity_super__salary.custname
import kotlinx.android.synthetic.main.activity_super__salary.dob
import kotlinx.android.synthetic.main.activity_super__salary.fathername
import kotlinx.android.synthetic.main.activity_super__salary.mobile
import kotlinx.android.synthetic.main.activity_super__salary.pincode
import kotlinx.android.synthetic.main.activity_super__salary.uname
import kotlinx.android.synthetic.main.activity_vip__client_add.*
import kotlinx.android.synthetic.main.callentry_header.*
import kotlinx.android.synthetic.main.supersalary_header.*
import kotlinx.android.synthetic.main.supersalary_header.head
import kotlinx.android.synthetic.main.supersalary_header.menu_img
import org.json.JSONArray
import org.json.JSONObject

import java.text.SimpleDateFormat
import java.util.*
import kotlin.check

class Super_Salary : AppCompatActivity() {
    private var mYear: Int = 0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    internal var progbar: Dialog?=null;
    var otpvalid="";
    var validcaptcha="";
    var tot=0;
    lateinit var pDialo : ProgressDialog
    var intenttype="";

    var unamevalue="";
    internal lateinit var utils: Utils
    internal lateinit var shp: SharedPreferences
    private var fromDatePickerDialog: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super__salary)
        utils = Utils(applicationContext)

        shp = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        unamevalue= utils.loadName()
        //Log.e("unamevalue",unamevalue)
        val intents=intent.extras
        intenttype=intents!!.getString("frm").toString()

        if(intenttype=="normal") {
            head!!.text = "Monthly Report"
            pincode.setText("0")
            pincode1.setText("0")
            reflin.visibility=View.VISIBLE
            duplin.visibility=View.GONE
            //totvids.visibility=View.GONE
           // viewvids.visibility=View.GONE
            textView7w.setText("Feedback")
            duplin.visibility=View.GONE
            notseen_lin.visibility=View.GONE
            feedth_lin.visibility=View.GONE
            feed_notgive.visibility=View.GONE
           /* feedgiv_lin.visibility=View.GONE
            */
            perm_give.visibility=View.GONE
            perm3_give.visibility=View.GONE
            reflin.visibility=View.GONE
        }
        if(intenttype=="star") {
            head!!.text = "Super Premium Monthly Report"
            reflin.visibility=View.GONE
            duplin.visibility=View.GONE
            pincode.setText("0")
            pincode1.setText("0")

        }
        if(intenttype=="super") {
            head!!.text = "Super Salary Achiever Report"
            reflin.visibility=View.VISIBLE
            duplin.visibility=View.GONE
            pincode.setText("0")
            pincode1.setText("0")
            textView7ref.setText("Super premium members referred count")

        }
        textView89.setText(utils.loadName())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }


        if(utils.loadplan()=="Super Salary Achiever"){
            //supercheck.visibility=View.VISIBLE
        }

        feedbutt.setOnClickListener {

            if (intenttype != "normal") {
                if (uname.text.isNotEmpty() && custname.text.isNotEmpty() && address.text.isNotEmpty() && address1.text.isNotEmpty()
                    && fathername.text.isNotEmpty() && fathername1.text.isNotEmpty()
                    && dob.text.isNotEmpty() && custnamew.text.isNotEmpty() && custnamew1.text.isNotEmpty() && mobile.text.isNotEmpty() &&
                    mobile1.text.isNotEmpty() && nofeed.text.isNotEmpty() && nofeed1.text.isNotEmpty() && perm.text.isNotEmpty() &&
                    perm1.text.isNotEmpty() && noperm.text.isNotEmpty() && noperm1.text.isNotEmpty() &&
                    checkBox.isChecked == true
                ) {

                    println("inside" + "ins")
                    if (intenttype == "normal" || intenttype == "super") {
                        if (refname.text.toString().isNotEmpty()) {

                            val alert = AlertDialog.Builder(this)
                            val vs = layoutInflater.inflate(R.layout.new_reward_popup, null)
                            alert.setTitle("Information")
                            alert.setCancelable(false)
                            val okay = vs.findViewById<TextView>(R.id.textView81);
                            val ed = vs.findViewById<EditText>(R.id.otp_edit_box1)
                            alert.setView(vs)
                            val pop = alert.create()
                            pop.show()
                            ed.setText(utils.available_reward())
                            okay.setOnClickListener {
                                pop.dismiss()
                                val savetask = savetask()
                                savetask.execute();
                            }


                        } else {
                            toast("Please fill star premium refereed count")
                            refname.setError("Required field")
                        }
                    } else {
                        val alert = AlertDialog.Builder(this)
                        val vs = layoutInflater.inflate(R.layout.new_reward_popup, null)
                        alert.setTitle("Information")
                        alert.setCancelable(false)
                        val okay = vs.findViewById<TextView>(R.id.textView81);
                        val ed = vs.findViewById<EditText>(R.id.otp_edit_box1)
                        alert.setView(vs)
                        val pop = alert.create()
                        pop.show()
                        ed.setText(utils.available_reward())
                        okay.setOnClickListener {
                            pop.dismiss()
                            val savetask = savetask()
                            savetask.execute();
                        }
                        /* val savetask=savetask()
                    savetask.execute();*/
                    }

                } else {
                    toast("Please fill required fields")
                    if (uname.text.isEmpty()) {
                        uname.setError("Required field*")
                    }
                    if (custname.text.isEmpty()) {
                        custname.setError("Required field*")
                    }
                    if (address.text.isEmpty()) {
                        address.setError("Required field*")
                    }
                    if (address1.text.isEmpty()) {
                        address1.setError("Required field*")
                    }
                    if (fathername.text.isEmpty()) {
                        fathername.setError("Required field*")
                    }
                    if (fathername1.text.isEmpty()) {
                        fathername1.setError("Required field*")
                    }
                    if (dob.text.isEmpty()) {
                        dob.setError("Required field*")
                    }
                    if (custnamew.text.isEmpty()) {
                        custnamew.setError("Required field*")
                    }
                    if (custnamew1.text.isEmpty()) {
                        custnamew1.setError("Required field*")
                    }
                    if (mobile.text.isEmpty()) {
                        mobile.setError("Required field*")
                    }
                    if (mobile1.text.isEmpty()) {
                        mobile1.setError("Required field*")
                    }
                    if (nofeed.text.isEmpty()) {
                        nofeed.setError("Required field*")
                    }
                    if (nofeed1.text.isEmpty()) {
                        nofeed1.setError("Required field*")
                    }
                    if (perm.text.isEmpty()) {
                        perm.setError("Required field*")
                    }
                    if (perm1.text.isEmpty()) {
                        perm1.setError("Required field*")
                    }
                    if (noperm.text.isEmpty()) {
                        noperm.setError("Required field*")
                    }
                    if (noperm1.text.isEmpty()) {
                        noperm1.setError("Required field*")
                    }
                    if (refname.text.toString().isEmpty()) {
                        refname.setError("Required field")

                    }

                    if (checkBox.isChecked == false) {
                        toast("Please agree the terms and conditions")
                    }


                }

            }
            else if(intenttype=="normal"){
                if (uname.text.isNotEmpty() && custname.text.isNotEmpty() && address.text.isNotEmpty() && address1.text.isNotEmpty()
                    && dob.text.isNotEmpty() && custnamew.text.isNotEmpty() && custnamew1.text.isNotEmpty()&&
                    checkBox.isChecked == true
                ) {

                    println("inside" + "ins")
                    if (intenttype == "normal" || intenttype == "super") {
                       // if (refname.text.toString().isNotEmpty()) {

                            val alert = AlertDialog.Builder(this)
                            val vs = layoutInflater.inflate(R.layout.new_reward_popup, null)
                            alert.setTitle("Information")
                            alert.setCancelable(false)
                            val okay = vs.findViewById<TextView>(R.id.textView81);
                            val ed = vs.findViewById<EditText>(R.id.otp_edit_box1)
                            alert.setView(vs)
                            val pop = alert.create()
                            pop.show()
                            ed.setText(utils.available_reward())
                            okay.setOnClickListener {
                                pop.dismiss()
                                val savetask = savetask()
                                savetask.execute();
                            }


                        /*} else {
                            toast("Please fill star premium refereed count")
                            refname.setError("Required field")
                        }*/
                    } else {
                        val alert = AlertDialog.Builder(this)
                        val vs = layoutInflater.inflate(R.layout.new_reward_popup, null)
                        alert.setTitle("Information")
                        alert.setCancelable(false)
                        val okay = vs.findViewById<TextView>(R.id.textView81);
                        val ed = vs.findViewById<EditText>(R.id.otp_edit_box1)
                        alert.setView(vs)
                        val pop = alert.create()
                        pop.show()
                        ed.setText(utils.available_reward())
                        okay.setOnClickListener {
                            pop.dismiss()
                            val savetask = savetask()
                            savetask.execute();
                        }
                        /* val savetask=savetask()
                    savetask.execute();*/
                    }

                } else {
                    toast("Please fill required fields")
                    if (uname.text.isEmpty()) {
                        uname.setError("Required field*")
                    }
                    if (custname.text.isEmpty()) {
                        custname.setError("Required field*")
                    }
                    if (address.text.isEmpty()) {
                        address.setError("Required field*")
                    }
                    if (address1.text.isEmpty()) {
                        address1.setError("Required field*")
                    }

                    if (dob.text.isEmpty()) {
                        dob.setError("Required field*")
                    }
                    if (custnamew.text.isEmpty()) {
                        custnamew.setError("Required field*")
                    }
                    if (custnamew1.text.isEmpty()) {
                        custnamew1.setError("Required field*")
                    }
                   /* if (mobile.text.isEmpty()) {
                        mobile.setError("Required field*")
                    }
                    if (mobile1.text.isEmpty()) {
                        mobile1.setError("Required field*")
                    }
                    if (nofeed.text.isEmpty()) {
                        nofeed.setError("Required field*")
                    }
                    if (nofeed1.text.isEmpty()) {
                        nofeed1.setError("Required field*")
                    }
                    if (perm.text.isEmpty()) {
                        perm.setError("Required field*")
                    }
                    if (perm1.text.isEmpty()) {
                        perm1.setError("Required field*")
                    }
                    if (noperm.text.isEmpty()) {
                        noperm.setError("Required field*")
                    }
                    if (noperm1.text.isEmpty()) {
                        noperm1.setError("Required field*")
                    }
                    if (refname.text.toString().isEmpty()) {
                        refname.setError("Required field")

                    }*/

                    if (checkBox.isChecked == false) {
                        toast("Please agree the terms and conditions")
                    }


                }
            }
        }
        menu_img.setOnClickListener {
            onBackPressed()
            finish()
        }



    }

    override fun onResume() {
        super.onResume()
        stateload().execute();
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@Super_Salary);
            pDialo.setMessage("Loading...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "Date")
                jobj.put("uname",unamevalue)

                if(intenttype=="star"){
                    jobj.put("utype", "1")

                }
                else if(intenttype=="normal") {
                    jobj.put("utype", "0")
                }
                else{
                    jobj.put("utype", "2")

                }

                //jobj.put("fdate",uname.text.toString().trim())

               // Log.i("check Input", Appconstants.VIPCLIENT + "    " + jobj.toString())

                result = con.sendHttpPostjson2(Appconstants.VIPCLIENT, jobj, "")

                /*JSONObject json = new JSONObject();
			json.put("mobile",param[0]);
			json.put("otp", param[1]);
			result=con.sendHttpPostjson2(, json, "");*/


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONObject("Response")
                        val states = jarr.getString("from")
                        val statesid = jarr.getString("to")
                        uname.setText(states)
                        custname.setText(statesid)
                        pDialo.dismiss()

                        //state.setSelection(list2.indexOf(statevalue));
                            Log.e("VALUE STATE", states)

                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {

                        pDialo.dismiss()

                        //Toast.makeText(this@Super_Salary, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {
                    pDialo.dismiss()

                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {

                Log.e("VALUE STATE", e.toString())
                pDialo.dismiss()

                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }



    private inner class savetask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            progbar = Dialog(this@Super_Salary)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.saveload)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String):String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "Add")
                jobj.put("uname",unamevalue)
                jobj.put("fdate",uname.text.toString().trim())
                jobj.put("tdate",custname.text.toString().trim())
                jobj.put("played",dob.text.toString().trim())
                jobj.put("viewed",address.text.toString().trim())
                jobj.put("view_duplicate",pincode.text.toString().trim())
                jobj.put("not_seen",fathername.text.toString().trim())
                jobj.put("feedback_below4",custnamew.text.toString().trim())
                jobj.put("feedback_above3",mobile.text.toString().trim())
                jobj.put("feedback_not",nofeed.text.toString().trim())
                jobj.put("permission_below4",perm.text.toString().trim())
                jobj.put("permission_above3",noperm.text.toString().trim())
                jobj.put("splayed",dob1.text.toString().trim())
                jobj.put("sviewed",address1.text.toString().trim())
                jobj.put("sview_duplicate",pincode1.text.toString().trim())
                jobj.put("snot_seen",fathername1.text.toString().trim())
                jobj.put("sfeedback_below4",custnamew1.text.toString().trim())
                jobj.put("sfeedback_above3",mobile1.text.toString().trim())
                jobj.put("sfeedback_not",nofeed1.text.toString().trim())
                jobj.put("spermission_below4",perm1.text.toString().trim())
                jobj.put("spermission_above3",noperm1.text.toString().trim())
                jobj.put("count",refname.text.toString().trim())
                jobj.put("reward",utils.total_reward())


                    if(intenttype=="star"){
                        jobj.put("utype", "1")

                    }
                    else if(intenttype=="normal") {
                        jobj.put("utype", "0")
                    }
                    else{
                        jobj.put("utype", "2")

                    }




               Log.i("checkInput feedback", Appconstants.super_salary + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.super_salary, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {

            try {
                Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
                //Log.i("tabresp", resp!! + "")

            }


            try {
                if (resp != null) {

                    val jarray= JSONObject(resp);

                    if(jarray.getString("Status").equals("Success")){

                        Toast.makeText(applicationContext,"Data saved successfully",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    else{
                        progbar!!.dismiss()
                        toast(jarray.getString("Response"))
                    }

                } else {
                    progbar!!.dismiss()
                    toast("Data not saved")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                progbar!!.dismiss()
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }
    fun toast(msg:String){
        val toa=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toa.setGravity(Gravity.CENTER,0,0)
        toa.show()
    }


}
