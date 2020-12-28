package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import kotlinx.android.synthetic.main.activity_new_signup.*
import org.json.JSONArray
import org.json.JSONObject

class NewSignup : AppCompatActivity() {
    internal var progbar: Dialog? = null
    var Res_OTP =""
    var MobilePattern = "[0-9]{10}"
    var NamePattern = "[a-zA-Z ]+"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_signup)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.ncolor1)
        }
        progbar = Dialog(this)
        progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progbar!!.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
        progbar!!.setContentView(R.layout.load)
        progbar!!.setCancelable(false)

        mobile.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mobile.text.toString().length==10){
                    if (mobile.text.toString().matches(MobilePattern.toRegex())){

                        CheckMobileTask().execute()
                    }else{
                        mobile.setError("Enter Valid Mobile Number")
                    }
                }
            }
        })

        save.setOnClickListener {
            if ((fname.text.toString().trim().isNotEmpty()&&fname.text.toString().matches(NamePattern.toRegex()))&&mobile.text.toString().length==10&&(Res_OTP==otp.text.toString().trim())&&(city.text.toString().trim().isNotEmpty()&&city.text.toString().matches(NamePattern.toRegex()))){
                SignupTask().execute()
            }else{
                if (!(fname.text.toString().trim().isNotEmpty()&&fname.text.toString().matches(NamePattern.toRegex()))){
                    fname.setError("Enter Valid Name")
                }
                if (!(city.text.toString().trim().isNotEmpty()&&city.text.toString().matches(NamePattern.toRegex()))){
                    city.setError("Enter Vaild City")
                }
                if(mobile.text.toString().length!=10){
                    mobile.setError("Enter Valid Mobile number")
                }
                if (Res_OTP!=otp.text.toString().trim()){
                    otp.setError("Enter Correct OTP")
                }
            }

        }
    }

    private inner class CheckMobileTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            progbar!!.show()
           // Log.i("CheckMobileTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String): String? {
            var result=""
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("mobile", mobile.text.toString().trim())
                jobj.put("type", "mobile")

               // Log.i("check Input", Appconstants.CORE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "")
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
          //  Log.i("mobresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }
            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        //JSONObject jarr = obj1.getJSONObject("Response");
                        mobile.clearFocus()
                        Res_OTP = obj1.getString("Response")
                    } else {
                        mobile.requestFocus()
                        mobile.setText("")
                        Toast.makeText(this@NewSignup, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {
                    mobile.requestFocus()
                    mobile.setText("")
                    Toast.makeText(applicationContext, "Error Response.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                mobile.requestFocus()
                mobile.setText("")
                e.printStackTrace()
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()

            }


        }
    }

    private inner class SignupTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            progbar!!.show()
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("SignupTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("name", fname.text.toString().trim())
                jobj.put("city", city.text.toString().trim())
                jobj.put("mobile",mobile.text.toString().trim())
                jobj.put("type", "newsignup")

               // Log.i("Submit", Appconstants.newsignup + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.newsignup, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp:String?) {
           // Log.i("tabresp", resp!! + "")
            save.text = "Submit"
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {

                    val json = JSONArray("["+resp+"]")
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val alert =AlertDialog.Builder(this@NewSignup)
                        alert.setTitle("Thank You")
                        alert.setMessage("Registered Successfully.\nOur executive will contact within 48 hours...\n")
                        alert.setPositiveButton("ok", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.dismiss()
                                //JSONObject jarr = obj1.getJSONObject("Response");
                                Toast.makeText(this@NewSignup, "Register Successfully.", Toast.LENGTH_LONG).show()
                                finish()
                                //startActivity(new Intent(SignUp.this,Login.class));
                            }
                        })
                        alert.show()
                    } else {

                        Toast.makeText(this@NewSignup, obj1.getString("Response"), Toast.LENGTH_LONG).show()

                    }
                } else {
                    Toast.makeText(applicationContext, "Error Response.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
