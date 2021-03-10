package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_daterequest_.*
import kotlinx.android.synthetic.main.callentry_header.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Daterequest_Activity : AppCompatActivity() {

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    var unamevalue="";
    var intenttype="";
    var respval=""
    internal lateinit var utils: Utils
    var tot:Int =0
    internal var progbar: Dialog?=null;
    internal var progbars: Dialog?=null;
    var otpvalid="3"
    var datarr=ArrayList<String>()
    var dat1arr=ArrayList<String>()
    var dat2arr=ArrayList<String>()
    internal lateinit var shp: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daterequest_)
        utils = Utils(applicationContext)
        shp = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        unamevalue= utils.loadName()
        val dat =   utils.loaddate()
        val dat1 = utils.loadalreadydate()



        val intents=intent.extras
        intenttype=intents!!.getString("frm").toString()
        textView89.setText(utils.loadName())

        val task1 = recdateget()
        task1.execute("")

        val task = listget()
        task.execute("")

        if(intenttype=="normal") {
            head!!.text = "Quarterly Benefit"
            //submit.visibility=View.VISIBLE
            //button2.visibility=View.VISIBLE
            checkBox.visibility=View.VISIBLE
            otpdt.visibility=View.VISIBLE
            editText.visibility=View.VISIBLE
            textView28.visibility=View.VISIBLE
            captcha.visibility=View.VISIBLE
            textView26.visibility=View.VISIBLE
            textView25.visibility=View.VISIBLE
            list.visibility=View.VISIBLE
            submit.visibility=View.VISIBLE
        }
        if(intenttype=="star") {
            head!!.text = "Super Premium Date Request"
        }
        if(intenttype=="super") {
            head!!.text = "Super Salary Achiever"
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }

        val min = 1
        val max = 10
        val random = Random().nextInt(max - min + 1)
        val random1 = Random().nextInt(max - min + 1)
        captcha.setText("$random+$random1 =")
        tot=random+random1



        val c = Calendar.getInstance().time
        println("Current time => $c")
        menu_img.setOnClickListener {
            onBackPressed()
        }


        submit.setOnClickListener {

            if(otpdt.text.toString().isNotEmpty()&&checkBox.isChecked==true&&(editText.text.toString()==tot.toString())) {

                val alert= AlertDialog.Builder(this)
                val vs = layoutInflater.inflate(R.layout.new_available_popup, null)
                alert.setTitle("Information")
                alert.setCancelable(false)
                val no=vs.findViewById<TextView>(R.id.textView81);
                val okay=vs.findViewById<TextView>(R.id.textView82);
                val ed=vs.findViewById<EditText>(R.id.otp_edit_box1)
                alert.setView(vs)
                val pop=alert.create()
                pop.show()
                ed.setText(utils.available_reward())
                okay.setOnClickListener {
                    pop.dismiss()

                    val alerts= AlertDialog.Builder(this)
                    val vs = layoutInflater.inflate(R.layout.new_msg_popup, null)
                    alerts.setCancelable(false)
                    val no=vs.findViewById<TextView>(R.id.textView81);
                    val okay=vs.findViewById<TextView>(R.id.textView82);
                    val ed=vs.findViewById<EditText>(R.id.otp_edit_box1)
                    alerts.setView(vs)
                    val pops=alerts.create()
                    pops.show()

                    okay.setOnClickListener{
                        pops.dismiss()
                        val task = billget()
                        task.execute(frdt.text.toString())
                    }
                    no.setOnClickListener{
                        pops.dismiss()
                    }

                }
                no.setOnClickListener {
                    pop.dismiss()
                }
            }
            else{
                if(otpdt.text.toString().isEmpty()){
                    otpdt.setError("Required field")
                }
                if(checkBox.isChecked==false){
                    Toast.makeText(applicationContext,"Please accept terms and conditions",Toast.LENGTH_SHORT).show()
                }
                if(editText.text.toString()!=tot.toString()){
                    editText.setError("Invalid captcha")
                    val min = 1
                    val max = 10
                    val random = Random().nextInt(max - min + 1)
                    val random1 = Random().nextInt(max - min + 1)
                    captcha.setText("$random+$random1 =")
                    tot=random+random1
                }
            }
        }

        frdt.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub

            if(frdt.text.toString()=="Select Date"||intenttype=="normal") {
                try {
                    utils.savePreferences("datealready", frdt.text.toString())
                } catch (e: java.lang.Exception) {

                }

                val c = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)
                val cals = Calendar.getInstance()  // or pick another time zone if necessary
                cals.timeInMillis = System.currentTimeMillis()
                cals.set(Calendar.DAY_OF_MONTH, cals.get(Calendar.DAY_OF_MONTH) + 1)
                cals.set(Calendar.HOUR_OF_DAY, 0)
                cals.set(Calendar.MINUTE, 0)
                cals.set(Calendar.SECOND, 0)

                //println("fgbh" + "tm1 : " + cals.timeInMillis / 1000)

                val datePickerDialog = DatePickerDialog(this@Daterequest_Activity,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        frdt.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    }, mYear, mMonth, mDay
                )


                val calendar = Calendar.getInstance();
                val mdformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                val strDate = mdformat.format(calendar.getTime());
                utils.savePreferences("datetime", strDate)


                //val simpledateformate = SimpleDateFormat("dd-MM-yyyy");
                //val date = simpledateformate.parse(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMinDate(cals.timeInMillis);

                datePickerDialog.show()
            }
        })

        otpdt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(otpdt.text.isNotEmpty()&&otpdt.text.length==4) {

                    if(utils.loadmpin().isNotEmpty()){
                        if(utils.loadmpin()==otpdt.text.toString()){

                        }
                        else{
                            toast("Enter your correct V3 pin")
                            otpdt.setText(null)
                        }
                    }
                    else{

                    }

                }
                else{

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                otpdt.setError(null)
            }
        })
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                editText.setError(null)
            }
        })


    }

    fun toast(msg:String){
        val toats=Toast.makeText(applicationContext,msg,Toast.LENGTH_LONG)
        toats.setGravity(Gravity.CENTER,0,0)
        toats.show()
    }


    private inner class recdateget : AsyncTask<String, String, String>() {

        var dt=""
        override fun onPreExecute() {
            progbar = Dialog(this@Daterequest_Activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")



        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()
            dt=param[0]
            try {
                val jobj = JSONObject()
                jobj.put("uname", unamevalue)
                jobj.put("type", "Date List")

                if(intenttype=="normal"){
                    jobj.put("types", "Normal")

                }
                if(intenttype=="star"){
                    jobj.put("types", "super")

                }
                if(intenttype=="super"){
                    jobj.put("types", "star")

                }
                Log.i("checkInputrec feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                Log.i("tabresp_rec", resp!! + "")
            }
            catch (e:java.lang.Exception){
               // Log.i("tabresp", resp!! + "")

            }

            //progbar!!.dismiss()

            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())
                    //utils.savePreferences("date",dt)
                    if(status=="Success") {
                        utils
                        progbar!!.dismiss()

                        var billusernameval=jobj.get("Response").toString()
                        respval=billusernameval;

                        if(respval!="null") {
                            val df = SimpleDateFormat("yyyy-MM-dd")
                            //val formattedDate = df.format(billusernameval)
                            val date = df.parse(billusernameval) as Date
                            val newFormat = SimpleDateFormat("dd-MM-yyyy")
                            val finalString = newFormat.format(date)
                            frdt.setText(finalString.toString())
                            /*if (finalString.isNotEmpty()){
                                checkBox.visibility=View.GONE
                                otpdt.visibility=View.GONE
                                editText.visibility=View.GONE
                                textView28.visibility=View.GONE
                                captcha.visibility=View.GONE
                                textView25.visibility=View.GONE
                            }*/
                        }
                        else if(respval=="null"){
                            submit.visibility=View.VISIBLE
                        }




                    }
                    else{
                        progbar!!.dismiss()

                        Toast.makeText(applicationContext,"Date not saved",Toast.LENGTH_SHORT).show()

                        //billnum.setText(null)
                        //billusername.setText(null)

                        //billnum.setError("Invalid bill number")
                    }

                } else {
                    progbar!!.dismiss()

                }

            } catch (e: Exception) {
                e.printStackTrace()
                progbar!!.dismiss()

            }


        }
    }


    private inner class otpget : AsyncTask<String, String, String>() {

        var dt=""
        override fun onPreExecute() {
            progbar = Dialog(this@Daterequest_Activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")



        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()
            dt=param[0]
            try {
                val jobj = JSONObject()
                jobj.put("uname", unamevalue)
                jobj.put("type", "Date Update")
              //  Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {

            }
            catch (e:java.lang.Exception){
            }

            try {
                if (resp != null) {
                    val jarray= JSONArray(resp);
                    val jobj=jarray.getJSONObject(0)
                    var status=(jobj.get("Status").toString())
                    //utils.savePreferences("date",dt)
                    if(status=="Success") {
                        utils
                        progbar!!.dismiss()
                        Toast.makeText(applicationContext,"OTP sent to registered mobile number",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        progbar!!.dismiss()
                        Toast.makeText(applicationContext,"OTP not sent",Toast.LENGTH_SHORT).show()
                    }

                } else {
                    progbar!!.dismiss()

                }

            } catch (e: Exception) {
                e.printStackTrace()
                progbar!!.dismiss()

            }


        }
    }

    private inner class billget : AsyncTask<String, String, String>() {

        var dt=""
        override fun onPreExecute() {
            progbar = Dialog(this@Daterequest_Activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.saveload)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()
            dt=param[0]
            try {
                val jobj = JSONObject()
                jobj.put("uname", unamevalue)
                jobj.put("type", "Date Updates")

                if(intenttype=="normal"){
                    jobj.put("types", "Normal")
                }
                if(intenttype=="star"){
                    jobj.put("types", "star")
                }
                if(intenttype=="super"){
                    jobj.put("types", "super")

                }
                jobj.put("otp", otpdt.text.toString())
                jobj.put("rdate", param[0])

                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

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
                Log.i("tabresp", resp!! + "")

            }

            //progbar!!.dismiss()

            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())
                    utils.savePreferences("date",dt)
                    if(status=="Success") {
                        utils
                        progbar!!.dismiss()
                        Toast.makeText(applicationContext,"Date saved",Toast.LENGTH_SHORT).show()
                        finish()
                        //var billusernameval=jobj.get("Response").toString()

                        //billusername.setText(billusernameval)



                    }
                    else{
                        progbar!!.dismiss()

                        //otpdt.setText(null)
                        ///otpdt.setError("Invalid V3 pin")
                        Toast.makeText(applicationContext,jobj.getString("Response"),Toast.LENGTH_SHORT).show()

                    }


                }

                else {
                    progbar!!.dismiss()

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                progbar!!.dismiss()

                //Log.e("ERROR OTP",e.toString())
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    private inner class listget : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
            progbars = Dialog(this@Daterequest_Activity)
            progbars!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbars!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbars!!.setContentView(R.layout.load)
            progbars!!.setCancelable(false)
            progbars!!.show()


        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "List Out")
                jobj.put("uname",unamevalue)
                if(intenttype=="normal"){
                    jobj.put("types", "Normal")

                }
                if(intenttype=="star"){
                    jobj.put("types", "super")

                }
                if(intenttype=="super"){
                    jobj.put("types", "star")

                }

                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                //Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
                //Log.i("tabresp", resp!! + "")

            }

            //progbar!!.dismiss()

            try {
                if (resp != null) {

                    val jarray=JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())
                    progbars!!.dismiss()

                    if(status=="Success") {
                        var billusernameval=jobj.getJSONArray("Response")
                        Log.i("tabrespdetails", resp!! + "")

                        for(j in 0 until billusernameval.length()){
                            val jobje=billusernameval.getJSONObject(j)
                            var det=jobje.get("details").toString()
                            var adetails=jobje.get("adetails").toString()

                            Log.i("tabrespdetails", resp!! + "")

                                datarr.add(det)
                                dat1arr.add(adetails)


                        }

                        val adap=Datelist_adap(this@Daterequest_Activity,datarr,dat1arr)
                        list.adapter=adap
                        Log.i(" ", resp!! + "")

                    }
                    else{
                        progbars!!.dismiss()

                    }

                } else {

                    list.visibility=View.GONE
                    progbars!!.dismiss()

                }

            } catch (e: Exception) {
                e.printStackTrace()
                //Log.e("ERROR OTP",e.toString())
                list.visibility=View.GONE
                progbars!!.dismiss()

                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }






    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.share_pins, menu)
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
        if (id == R.id.share_pin) {
            //sharearray()
            // Fillter();
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

