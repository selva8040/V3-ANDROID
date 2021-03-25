package com.elancier.healthzone

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import kotlinx.android.synthetic.main.content_forget.*
import kotlinx.android.synthetic.main.content_forget.password
import kotlinx.android.synthetic.main.content_forget.username
import kotlinx.android.synthetic.main.sign_up.*
import org.json.JSONArray
import org.json.JSONObject

class Forgot_Activity : AppCompatActivity() {
    internal var progbar: Dialog? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_)
        val toolbar = findViewById<View>(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.sky_blue));
        }

        login.setOnClickListener {
            if(username.text.toString().isNotEmpty()||password.text.toString().isNotEmpty()) {
                val task = LoginTask()
                task.execute(username.getText().toString().trim({ it <= ' ' }),password.getText().toString().trim({ it <= ' ' }))
            }
            else if(username.text.isEmpty()){
                Toast.makeText(applicationContext,"Enter Username",Toast.LENGTH_SHORT).show()
            }
            else if(password.text.isEmpty()){
                Toast.makeText(applicationContext,"Enter Registered Mobile Number",Toast.LENGTH_SHORT).show()
            }
        }


    }
    private inner class LoginTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("LoginTask", "started")
            progbar = Dialog(this@Forgot_Activity)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()

        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", param[0])
                jobj.put("mobile", param[1])
                Log.i("checkInput", Appconstants.forget_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.forget_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {

           // Log.i("tabresp", resp!! + "")
            if (progbar!!.isShowing() && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {

                    val json = JSONObject(resp)

                    if (json.getString("Status") == "Success") {

                        val alertBuilder = android.app.AlertDialog.Builder(this@Forgot_Activity)
                        alertBuilder.setCancelable(false)
                        alertBuilder.setTitle("Information")
                        alertBuilder.setMessage("Your request received, within 24 hours you will receive your login details to your registered mobile number!...")
                        alertBuilder.setPositiveButton(android.R.string.ok) { dialog, which -> dialog.dismiss()
                        finish()
                        }
                        val alert = alertBuilder.create()
                        alert.show()

                        Handler().postDelayed(Runnable {
                            finish()
                        },7000)

                       // Toast.makeText(applicationContext,"Request sent to admin.",Toast.LENGTH_SHORT).show()
                  // finish()

                    }
                    else{
                        Toast.makeText(applicationContext,"Incorrect username or mobile number",Toast.LENGTH_SHORT).show()

                    }

                }
                else{

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Incorrect Username.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
