package com.elancier.healthzone

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_vip__client_add.*

import kotlinx.android.synthetic.main.activity_vip_new_activity.*
import kotlinx.android.synthetic.main.activity_vip_new_activity.address
import kotlinx.android.synthetic.main.activity_vip_new_activity.billnum
import kotlinx.android.synthetic.main.activity_vip_new_activity.captcha
import kotlinx.android.synthetic.main.activity_vip_new_activity.check
import kotlinx.android.synthetic.main.activity_vip_new_activity.confirmmobile
import kotlinx.android.synthetic.main.activity_vip_new_activity.custname
import kotlinx.android.synthetic.main.activity_vip_new_activity.datebilling
import kotlinx.android.synthetic.main.activity_vip_new_activity.dob
import kotlinx.android.synthetic.main.activity_vip_new_activity.fathername
import kotlinx.android.synthetic.main.activity_vip_new_activity.imageButton
import kotlinx.android.synthetic.main.activity_vip_new_activity.intake
import kotlinx.android.synthetic.main.activity_vip_new_activity.no
import kotlinx.android.synthetic.main.activity_vip_new_activity.otp
import kotlinx.android.synthetic.main.activity_vip_new_activity.pincode
import kotlinx.android.synthetic.main.activity_vip_new_activity.textView3
import kotlinx.android.synthetic.main.activity_vip_new_activity.uname
import kotlinx.android.synthetic.main.activity_vip_new_activity.weight
import kotlinx.android.synthetic.main.activity_vip_new_activity.yes
import kotlinx.android.synthetic.main.activity_vip_new_activity.yesdetails
import kotlinx.android.synthetic.main.callentry_header.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.check

class Vip_new_activity : AppCompatActivity() {
    private var mYear: Int = 0
    private var mMonth:Int = 0
    private var mDay:Int = 0
    private var mHour:Int = 0
    private var mMinute:Int = 0
    internal var progbar: Dialog?=null;
    var validcaptcha="";
    var tot=0;
    var unamevalue="";

    internal lateinit var utils: Utils


    var otpvalid="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vip_new_activity)

        utils = Utils(applicationContext)

        unamevalue= utils.loadName()

        uname.setText(unamevalue)
        val task = unameget()
        task.execute(uname.text.toString())



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }

        uname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(uname.text.toString().isNotEmpty()){
                    val task = unameget()
                    task.execute(uname.text.toString())
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


            }
        })

        menu_img.setOnClickListener {
            onBackPressed()
        }

        custname.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                custname.setError(null)

            }
        })
        dob.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                dob.setError(null)

            }
        })
        address.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                address.setError(null)

            }
        })
        pincode.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                pincode.setError(null)

            }
        })

        textView3.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this@Vip_new_activity)
            val inflater = this.layoutInflater

            val dialogView = builder.create()
            val v = layoutInflater.inflate(R.layout.new_terms_popup, null)

            dialogView.setView(v)

            val add = v.findViewById(R.id.agree) as Button
            add.setOnClickListener {

                dialogView.dismiss()
            }
            dialogView.show()

        }


        captcha.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(captcha.text.toString().isNotEmpty()){
                    if(captcha.text.toString().isNotEmpty()) {
                        if (tot == captcha.text.toString().toInt()) {
                            validcaptcha = "true";
                        } else {
                            validcaptcha = "";

                        }
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                captcha.setError(null)


            }
        })
        fathername.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                fathername.setError(null)

            }
        })
        confirmmobile.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                confirmmobile.setError(null)

            }
        })


        billnum.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                billnum.setError(null)
            }
        })
        datebilling.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                datebilling.setError(null)


            }
        })
        intake.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                intake.setError(null)
            }
        })
        weight.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {



            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                weight.setError(null)


            }
        })




        otp.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(otp.text.toString().isNotEmpty()&&otp.text.length==4){

                    if(otpvalid == otp.text.toString()){

                        imageButton.visibility= View.VISIBLE
                    }
                    else{

                        imageButton.visibility= View.VISIBLE
                        imageButton.setImageDrawable(resources.getDrawable(R.mipmap.redcancel));

                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                otp.setError(null)
                if(otp.text.toString().isEmpty()) {

                    imageButton.visibility= View.GONE
                }

                if(otp.text.toString().isNotEmpty()&&otp.text.length==4){

                    if(otpvalid == otp.text.toString()){

                        imageButton.visibility= View.VISIBLE
                        imageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_checked));

                    }
                    else{

                        imageButton.visibility= View.VISIBLE
                        imageButton.setImageDrawable(resources.getDrawable(R.mipmap.redcancel));

                    }
                }

            }
        })


        yes.setOnClickListener {

            yes.isChecked=true
            no.isChecked=false

        }

        no.setOnClickListener {

            no.isChecked=true
            yes.isChecked=false

        }



        confirmmobile.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(confirmmobile.text.toString().isNotEmpty()&&confirmmobile.text.length==10){
                    otp.isEnabled=true
                    otpvalid=utils.loadmpin()
                }
                else{
                    otp.isEnabled=false

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


            }
        })


        billnum.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                if(billnum.text.toString().isNotEmpty()&&billnum.text.length==7){

                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


            }
        })

        intake.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub
            val task = billget()
            task.execute(billnum.text.toString())
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val cals = Calendar.getInstance()  // or pick another time zone if necessary
            cals.timeInMillis =System.currentTimeMillis()
            cals.set(Calendar.DAY_OF_MONTH, cals.get(Calendar.DAY_OF_MONTH) + 1)
            cals.set(Calendar.HOUR_OF_DAY, 0)
            cals.set(Calendar.MINUTE, 0)
            cals.set(Calendar.SECOND, 0)

            val datePickerDialog = DatePickerDialog(this@Vip_new_activity,

            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> intake.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)

                val simpledateformate = SimpleDateFormat("dd-MM-yyyy");
                // val date = simpledateformate.parse(datebilling.text.toString());
                datePickerDialog.getDatePicker().setMinDate(cals.timeInMillis);

                datePickerDialog.show()

        })
        val min = 1
        val max = 10
        val random = Random().nextInt(max - min + 1)
        val random1 = Random().nextInt(max - min + 1)
        captca.setText("$random+$random1 =")
        tot=random+random1


        dob.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this@Vip_new_activity,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> dob.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)
            datePickerDialog.show()
        })

        datebilling.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub
            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(this@Vip_new_activity,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> datebilling.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) }, mYear, mMonth, mDay)
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            datePickerDialog.show()
        })

        head.setText("New User")

        save.setOnClickListener {

            if(uname.text.isNotEmpty()&&spuname.text.isNotEmpty()&&spmbl.text.isNotEmpty()&&custname.text.isNotEmpty()&&address.text.isNotEmpty()
                    &&pincode.text.isNotEmpty()&&fathername.text.isNotEmpty()&&confirmmobile.text.isNotEmpty()
                    &&otp.text.isNotEmpty()&&dob.text.isNotEmpty()&&billnum.text.isNotEmpty()&&datebilling.text.isNotEmpty()&&intake.text.isNotEmpty()
                    &&weight.text.isNotEmpty()&&captcha.text.isNotEmpty()&&check.isChecked==true&&(yes.isChecked==true||no.isChecked==true)&&validcaptcha.isNotEmpty()){

                val savetask=savetask()
                savetask.execute();


            }
            else{
                if(uname.text.isEmpty()){
                    uname.setError("Required field")
                }
                if(spuname.text.isEmpty()){
                    spuname.setError("Required field")
                }
                if(spmbl.text.isEmpty()){
                    spmbl.setError("Required field")
                }
                if(dob.text.isEmpty()){
                    dob.setError("Required field")
                }
                if(custname.text.isEmpty()){
                    custname.setError("Required field")
                }

                if(address.text.isEmpty()){
                    address.setError("Required field")
                }
                if(pincode.text.isEmpty()){
                    pincode.setError("Required field")
                }
                if(fathername.text.isEmpty()){
                    fathername.setError("Required field")
                }
                if(confirmmobile.text.isEmpty()){
                    confirmmobile.setError("Required field")
                }

                if(otp.text.isEmpty()){
                    otp.setError("Required field")
                }
                if(billnum.text.isEmpty()){
                    billnum.setError("Required field")
                }
                if(datebilling.text.isEmpty()){
                    datebilling.setError("Required field")
                }
                if(intake.text.isEmpty()){
                    intake.setError("Required field")
                }
                if(weight.text.isEmpty()){
                    weight.setError("Required field")
                }
                if(captcha.text.isEmpty()){
                    captcha.setError("Required field")
                }
                if(validcaptcha.isEmpty()){
                    captcha.setError("Invalid captcha")
                }
                if(check.isChecked==false){
                    Toast.makeText(applicationContext,"Please fill required fields", Toast.LENGTH_LONG).show()
                }
                if(yes.isChecked==false){
                    Toast.makeText(applicationContext,"Please fill required fields", Toast.LENGTH_LONG).show()

                }
                if(no.isChecked==false){
                    Toast.makeText(applicationContext,"Please fill required fields", Toast.LENGTH_LONG).show()

                }


            }

        }


    }
    private inner class unameget : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            progbar = Dialog(this@Vip_new_activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(
                    ColorDrawable(android.graphics.Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", param[0])
                jobj.put("type", "New User")


                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
               // Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
               // Log.i("tabresp", resp!! + "")

            }

            progbar!!.dismiss()
            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)
                    if(jobj.getString("Status").equals("Success")){
                        val jarray1=jobj.getJSONArray("Response");
                        val jobj1=jarray1.getJSONObject(0)

                        /*custname.setError(null)
                        mobilenum.setError(null)
                        address.setError(null)
                        pincode.setError(null)
                        dob.setError(null)*/
                        spuname.setText(jobj1.get("name").toString())
                        spmbl.setText(jobj1.get("mobile").toString())





                    }




                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private inner class savetask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            progbar = Dialog(this@Vip_new_activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.saveload)
            progbar!!.setCancelable(false)
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                runOnUiThread {
                val jobj = JSONObject()
                jobj.put("type", "User Insert")
                jobj.put("utype","New")
                jobj.put("username",uname.text.toString())
                jobj.put("name",custname.text.toString())
                jobj.put("mobile",confirmmobile.text.toString())
                jobj.put("fname",fathername.text.toString())
                jobj.put("dob",dob.text.toString())
                jobj.put("address",address.text.toString())
                jobj.put("pincode",pincode.text.toString())
                jobj.put("dobilling",datebilling.text.toString())
                jobj.put("intake",intake.text.toString())
                jobj.put("billno",billnum.text.toString())

                if(yes.isChecked==true){
                    yesval="Yes"
                }
                else if(no.isChecked==true){
                    yesval="No"
                }
                jobj.put("medicine",yesval)
                jobj.put("medicinedetail",yesdetails.text.toString())


                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

                }
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


            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)
                    if(jobj.getString("Status").equals("Success")){
                        Toast.makeText(applicationContext,"Data saved successfully", Toast.LENGTH_LONG).show()
                        finish()
                    }




                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private inner class billget : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
           /* progbar = Dialog(this@Vip_new_activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()*/


        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("bill", param[0])
                jobj.put("type", "Bill No")


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

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())

                    if(status=="Success") {

                        var billusernameval=jobj.get("Response").toString()

                        billusernamenew.setText(billusernameval)



                    }
                    else{

                        billnum.setText(null)
                        billusernamenew.setText(null)

                        billnum.setError("Invalid bill number")
                    }









                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ERROR OTP",e.toString())
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    private inner class otpget : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
            progbar = Dialog(this@Vip_new_activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()


        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("mobile", param[0])
                jobj.put("type", "OTP")


                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
               /// Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
               // Log.i("tabresp", resp!! + "")

            }
            progbar!!.dismiss()

            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())
                    otp.isEnabled=true
                    if(status=="Success") {
                        otpvalid=(jobj.get("OTP").toString())



                    }
                    else{
                        var response = jobj.get("Response").toString()

                        Toast.makeText(applicationContext,response,Toast.LENGTH_SHORT).show()
                    }









                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("ERROR OTP",e.toString())
                Toast.makeText(applicationContext,"Not a registered mobile number",Toast.LENGTH_SHORT).show()

                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }
}
