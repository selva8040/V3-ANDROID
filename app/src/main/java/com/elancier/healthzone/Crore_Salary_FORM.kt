package com.elancier.healthzone

import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_super__salary.*
import kotlinx.android.synthetic.main.activity_super__salary.custname
import kotlinx.android.synthetic.main.activity_super__salary.uname
import kotlinx.android.synthetic.main.activity_super_saver.*
import kotlinx.android.synthetic.main.saver_header.*
import org.json.JSONArray
import org.json.JSONObject

class Crore_Salary_FORM : AppCompatActivity() {
    internal var progbar: Dialog?=null;
    lateinit var pDialo : ProgressDialog
    var unamevalue="";
    internal lateinit var utils: Utils
    internal lateinit var shp: SharedPreferences
    private var fromDatePickerDialog: DatePickerDialog? = null
    var click=""
    var curentxt="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_saver)
        utils = Utils(applicationContext)
        head.setText("Super Salary Form")
        shp = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        unamevalue= utils.loadName()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }

        menu_img.setOnClickListener{
            onBackPressed()
        }

        if(BuildConfig.VERSION_CODE==42&&utils.loadfirstversion().isEmpty()){
            utils.savePreferences("clearversion","true")
        }


        if(utils.loadfirstversion()=="true"){
            utils.savePreferences("cs_uname1","")
            utils.savePreferences("cs_uname2","")
            utils.savePreferences("cs_uname3","")
            utils.savePreferences("cs_uname4","")
            utils.savePreferences("cs_uname5","")
            utils.savePreferences("cs_uname6","")
            utils.savePreferences("cs_uname","")
        }


        if(utils.cs_uname().isNotEmpty()){
            if(utils.cs_uname()!=utils.loadName()){
                utils.savePreferences("cs_uname1","")
                utils.savePreferences("cs_uname2","")
                utils.savePreferences("cs_uname3","")
                utils.savePreferences("cs_uname4","")
                utils.savePreferences("cs_uname5","")
                utils.savePreferences("cs_uname6","")
                utils.savePreferences("cs_uname","")

            }
        }

        if(utils.loadcs_uname1().isNotEmpty()){
            uname.setText(utils.loadcs_uname1())
        }

        if(utils.loadcs_uname2().isNotEmpty()){
            custname.setText(utils.loadcs_uname2())
        }
        if(utils.loadcs_uname3().isNotEmpty()){
            user3.setText(utils.loadcs_uname3())
        }
        if(utils.loadcs_uname4().isNotEmpty()){
            user4.setText(utils.loadcs_uname4())
        }
        if(utils.loadcs_uname5().isNotEmpty()){
            user5.setText(utils.loadcs_uname5())
        }
        if(utils.loadcs_uname6().isNotEmpty()){
            user6.setText(utils.loadcs_uname6())
        }


        if(utils.loadcs_uname1().isNotEmpty()&&
            utils.loadcs_uname2().isNotEmpty()&&
            utils.loadcs_uname3().isNotEmpty()&&
            utils.loadcs_uname4().isNotEmpty()&&
            utils.loadcs_uname5().isNotEmpty()&&
            utils.loadcs_uname6().isNotEmpty()){
            button8.visibility=View.VISIBLE
        }
        else{
            button8.visibility=View.GONE

        }



        save1.setOnClickListener {
            if(uname.text.toString().trim().isNotEmpty()) {

            if(custname.text.toString()!=uname.text.toString()&&
                user3.text.toString()!=uname.text.toString()&&
                user4.text.toString()!=uname.text.toString()&&
                user5.text.toString()!=uname.text.toString()&&
                user6.text.toString()!=uname.text.toString()) {
                val task = stateload()
                task.execute(uname.text.toString().trim(), "uname")
            }
            else{
                toast("Username already used")
                uname.setText("")
            }
            }
            else{
                toast("Please fill this field")
                uname.setError("Required field")


            }
        }

        save2.setOnClickListener {
            if(custname.text.toString().trim().isNotEmpty()) {

            if(uname.text.toString()!=custname.text.toString()&&
                user3.text.toString()!=custname.text.toString()&&
                user4.text.toString()!=custname.text.toString()&&
                user5.text.toString()!=custname.text.toString()&&
                user6.text.toString()!=custname.text.toString()) {
                val task = stateload()
                task.execute(custname.text.toString().trim(), "custname")
            }
            else {
                toast("Username already used")
                custname.setText("")
            }
            }
            else{
                toast("Please fill this field")
                custname.setError("Required field")


            }

        }
        save3.setOnClickListener {
            if(user3.text.toString().trim().isNotEmpty()) {

            if(custname.text.toString()!=user3.text.toString()&&
                uname.text.toString()!=user3.text.toString()&&
                user4.text.toString()!=user3.text.toString()&&
                user5.text.toString()!=user3.text.toString()&&
                user6.text.toString()!=user3.text.toString()) {
                val task = stateload()
                task.execute(user3.text.toString().trim(), "user3")
            }
            else {
                toast("Username already used")
                user3.setText("")
            }
            }
            else{
                toast("Please fill this field")
                user3.setError("Required field")


            }



        }
        save4.setOnClickListener {
            if(user4.text.toString().trim().isNotEmpty()) {

            if(custname.text.toString()!=user4.text.toString()&&
                uname.text.toString()!=user4.text.toString()&&
                user3.text.toString()!=user4.text.toString()&&
                user5.text.toString()!=user4.text.toString()&&
                user6.text.toString()!=user4.text.toString()) {
                val task = stateload()
                task.execute(user4.text.toString().trim(), "user4")
            }
            else {
                toast("Username already used")
                user4.setText("")
            }
            }
            else{
                toast("Please fill this field")
                user4.setError("Required field")

            }

        }
        save5.setOnClickListener {
            if(user5.text.toString().trim().isNotEmpty()) {

            if(custname.text.toString()!=user5.text.toString()&&
                uname.text.toString()!=user5.text.toString()&&
                user3.text.toString()!=user5.text.toString()&&
                user4.text.toString()!=user5.text.toString()&&
                user6.text.toString()!=user5.text.toString()) {
                val task = stateload()
                task.execute(user5.text.toString().trim(), "user5")
            }
            else {
                toast("Username already used")
                user5.setText("")
            }
            }
            else{
                toast("Please fill this field")
                user5.setError("Required field")

            }
        }
        save6.setOnClickListener {
            if(user6.text.toString().trim().isNotEmpty()) {

            if(custname.text.toString()!=user6.text.toString()&&
                uname.text.toString()!=user6.text.toString()&&
                user3.text.toString()!=user6.text.toString()&&
                user4.text.toString()!=user6.text.toString()&&
                user5.text.toString()!=user6.text.toString()) {
                val task = stateload()
                task.execute(user6.text.toString().trim(), "user6")
            }
            else {
                toast("Username already used")
                user6.setText("")
            }
            }
            else{
                toast("Please fill this field")
                user6.setError("Required field")

            }
        }
        /*uname.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {

                    if(custname.text.toString()!=Caption.text.toString()&&
                        user3.text.toString()!=Caption.text.toString()&&
                        user4.text.toString()!=Caption.text.toString()&&
                        user5.text.toString()!=Caption.text.toString()&&
                        user6.text.toString()!=Caption.text.toString()){
                        if(utils.loadgd_uname1().isEmpty()) {

                            val task = stateload()
                            task.execute(Caption.text.toString().trim { it <= ' ' }, "uname")
                        }
                    }
                    else{
                        toast("Username already used")
                        uname.setText("")

                    }
                } else {
                    uname.setText("")
                    //uname.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }

        custname.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {

                    if(uname.text.toString()!=Caption.text.toString()&&
                        user3.text.toString()!=Caption.text.toString()&&
                        user4.text.toString()!=Caption.text.toString()&&
                        user5.text.toString()!=Caption.text.toString()&&
                        user6.text.toString()!=Caption.text.toString()) {

                         if(utils.loadgd_uname2().isEmpty()) {
                        val task = stateload()
                        task.execute(Caption.text.toString().trim { it <= ' ' }, "custname")
                                           }

                    }
                    else{
                        toast("Username already used")
                        custname.setText("")

                    }
                } else {
                    custname.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }

        user3.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    if(uname.text.toString()!=Caption.text.toString()&&
                        custname.text.toString()!=Caption.text.toString()&&
                        user4.text.toString()!=Caption.text.toString()&&
                        user5.text.toString()!=Caption.text.toString()&&
                        user6.text.toString()!=Caption.text.toString()) {
                        if(utils.loadgd_uname3().isEmpty()) {

                            val task = stateload()
                            task.execute(Caption.text.toString().trim { it <= ' ' }, "user3")
                        }
                    }
                    else{
                        toast("Username already used")
                        user3.setText("")

                    }
                } else {
                    user3.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }

        user4.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    if(uname.text.toString()!=Caption.text.toString()&&
                        custname.text.toString()!=Caption.text.toString()&&
                        user3.text.toString()!=Caption.text.toString()&&
                        user5.text.toString()!=Caption.text.toString()&&
                        user6.text.toString()!=Caption.text.toString()) {

                        if(utils.loadgd_uname4().isEmpty()) {
                            val task = stateload()
                            task.execute(Caption.text.toString().trim { it <= ' ' }, "user4")
                        }
                    }
                    else{
                        toast("Username already used")
                        user4.setText("")

                    }

                } else {
                    user4.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }
        user5.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {

                    if(uname.text.toString()!=Caption.text.toString()&&
                        custname.text.toString()!=Caption.text.toString()&&
                        user3.text.toString()!=Caption.text.toString()&&
                        user4.text.toString()!=Caption.text.toString()&&
                        user6.text.toString()!=Caption.text.toString()) {
                        if(utils.loadgd_uname5().isEmpty()) {

                            val task = stateload()
                            task.execute(Caption.text.toString().trim { it <= ' ' }, "user5")
                        }
                    }
                    else{
                        toast("Username already used")
                        user5.setText("")

                    }
                } else {
                    user5.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }

        user6.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                //mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    if(uname.text.toString()!=Caption.text.toString()&&
                        custname.text.toString()!=Caption.text.toString()&&
                        user3.text.toString()!=Caption.text.toString()&&
                        user4.text.toString()!=Caption.text.toString()&&
                        user5.text.toString()!=Caption.text.toString()) {

                        if(utils.loadgd_uname6().isEmpty()) {
                            val task = stateload()
                            task.execute(Caption.text.toString().trim { it <= ' ' }, "user6")
                        }
                    }
                    else{
                        toast("Username already used")
                        user6.setText("")

                    }
                } else {
                    user6.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                //mfocus = 1
            }
        }*/

        button8.setOnClickListener {

            if (uname.text.toString().isNotEmpty() &&
                custname.text.toString().isNotEmpty() &&
                user3.text.toString().isNotEmpty() &&
                user4.text.toString().isNotEmpty() &&
                user5.text.toString().isNotEmpty() &&
                user6.text.toString().isNotEmpty()) {

                click="yes"

                if(click=="yes") {
                    val savetask = savetask()
                    savetask.execute();
                }

            } else {
                if (uname.text.isEmpty()) {
                    uname.setError("Required field")
                }
                if (custname.text.isEmpty()) {
                    custname.setError("Required field")
                }
                if (user3.text.isEmpty()) {
                    user3.setError("Required field")
                }

                if (user4.text.isEmpty()) {
                    user4.setError("Required field")
                }
                if (user5.text.isEmpty()) {
                    user5.setError("Required field")
                }
                if (user6.text.isEmpty()) {
                    user6.setError("Required field")
                }
            }
        }



    }
    override fun onBackPressed() {
/*        utils.savePreferences("gd_uname1",uname.text.toString().trim())
        utils.savePreferences("gd_uname2",custname.text.toString().trim())
        utils.savePreferences("gd_uname3",user3.text.toString().trim())
        utils.savePreferences("gd_uname4",user4.text.toString().trim())
        utils.savePreferences("gd_uname5",user5.text.toString().trim())
        utils.savePreferences("gd_uname6",user6.text.toString().trim())*/
        finish()
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@Crore_Salary_FORM);
            pDialo.setMessage("Validating...");
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
                curentxt=param[1]
                jobj.put("type", "Check")
                jobj.put("uname",param[0])
                jobj.put("login",unamevalue)
                //jobj.put("fdate",uname.text.toString().trim())

                Log.i("check Input", Appconstants.cr_supersal + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.cr_supersal, jobj, "")

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
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        val jarr = json.getString("Response")
                        //val states = jarr.getString("from")
                        //val statesid = jarr.getString("to")
                        // uname.setText(states)
                        //custname.setText(statesid)
                        pDialo.dismiss()




                        if(curentxt=="uname"){
                            // uname.setText("")
                            //click=""
                            utils.savePreferences("cs_uname1",uname.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")

                        }
                        else if(curentxt=="custname"){
                            //  custname.setText("")
                            utils.savePreferences("cs_uname2",custname.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")
                        }
                        else if(curentxt=="user3"){
                            // user3.setText("")
                            utils.savePreferences("cs_uname3",user3.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")
                        }
                        else if(curentxt=="user4"){
                            // user4.setText("")
                            utils.savePreferences("cs_uname4",user4.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")
                        }
                        else if(curentxt=="user5"){
                            //user5.setText("")
                            utils.savePreferences("cs_uname5",user5.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")
                        }
                        else if(curentxt=="user6"){
                            //user6.setText("")
                            utils.savePreferences("cs_uname6",user6.text.toString().trim())
                            utils.savePreferences("cs_uname",utils.loadName())
                            utils.savePreferences("clearversion","false")

                            toast("Saved")
                        }


                        if(utils.loadcs_uname1().isNotEmpty()&&
                            utils.loadcs_uname2().isNotEmpty()&&
                            utils.loadcs_uname3().isNotEmpty()&&
                            utils.loadcs_uname4().isNotEmpty()&&
                            utils.loadcs_uname5().isNotEmpty()&&
                            utils.loadcs_uname6().isNotEmpty()){
                            button8.visibility=View.VISIBLE
                        }
                        else{
                            button8.visibility=View.GONE

                        }

                        //state.setSelection(list2.indexOf(statevalue));
                        //Log.e("VALUE STATE", states)

                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {

                        pDialo.dismiss()
                        val jarr = json.getString("Response")
                        toast(jarr)
                        if(curentxt=="uname"){
                            uname.setText("")
                            click=""
                        }
                        else if(curentxt=="custname"){
                            custname.setText("")
                            click=""

                        }
                        else if(curentxt=="user3"){
                            user3.setText("")
                            click=""

                        }
                        else if(curentxt=="user4"){
                            user4.setText("")
                            click=""

                        }
                        else if(curentxt=="user5"){
                            user5.setText("")
                            click=""

                        }
                        else if(curentxt=="user6"){
                            user6.setText("")
                            click=""

                        }
                        //Toast.makeText(this@Super_Salary, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {
                    pDialo.dismiss()
                    toast("Enter a valid username")
                    if(curentxt=="uname"){
                        uname.setText("")
                        click=""

                    }
                    else if(curentxt=="custname"){
                        custname.setText("")
                        click=""

                    }
                    else if(curentxt=="user3"){
                        user3.setText("")
                        click=""

                    }
                    else if(curentxt=="user4"){
                        user4.setText("")
                        click=""

                    }
                    else if(curentxt=="user5"){
                        user5.setText("")
                        click=""


                    }
                    else if(curentxt=="user6"){
                        user6.setText("")
                        click=""

                    }
                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {

                Log.e("VALUE STATE", e.toString())
                pDialo.dismiss()
                toast("Enter a valid username")
                if(curentxt=="uname"){
                    uname.setText("")
                    click=""

                }
                else if(curentxt=="custname"){
                    custname.setText("")
                    click=""

                }
                else if(curentxt=="user3"){
                    user3.setText("")
                    click=""

                }
                else if(curentxt=="user4"){
                    user4.setText("")
                    click=""

                }
                else if(curentxt=="user5"){
                    user5.setText("")
                    click=""

                }
                else if(curentxt=="user6"){
                    user6.setText("")
                    click=""


                }

                e.printStackTrace()
                Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    fun toast(msg:String){
        val toast=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    private inner class savetask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            progbar = Dialog(this@Crore_Salary_FORM)
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
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "Add")
                jobj.put("uname",unamevalue)
                jobj.put("username1",uname.text.toString().trim())
                jobj.put("username2",custname.text.toString().trim())
                jobj.put("username3",user3.text.toString().trim())
                jobj.put("username4",user4.text.toString().trim())
                jobj.put("username5",user5.text.toString().trim())
                jobj.put("username6",user6.text.toString().trim())
                //jobj.put("username",custnamew.text.toString().trim())


                Log.i("checkInput feedback", Appconstants.cr_supersal + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.cr_supersal, jobj, "")

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


            try {
                if (resp != null) {

                    val jarray= JSONObject(resp);

                    if(jarray.getString("Status").equals("Success")){
                        toast("Data saved successfully")
                        finish()
                    }
                    else{
                        toast("Data not saved.")

                    }




                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {
                    toast("Data not saved.")

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            }

            catch (e:Exception)
            {
                e.printStackTrace()
                toast(e.toString())

            }


        }
    }
}
