package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognizerIntent
import android.telephony.TelephonyManager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class Save_Video:AppCompatActivity() {
    private val REQ_CODE = 100
    var feedrad: TextView? = null
    var compradio:TextView? = null
    var suggradio:TextView? = null
    var permradio:TextView? = null
    var tittext:TextView? = null
    var tittextstar:TextView? = null
    var noticnt:TextView? = null
    var feededit: EditText? = null
    var submitfeed: Button? = null
    var speak: CircleImageView? = null
    var str = ""
    val fradcheck = arrayOf("")
    var utils: Utils? = null

    var id=""
    var s=""
    var deviceId=""
    var amount=""
    var gpv=""
    var ibv=""
    var purchase=""
    var sales=""
    var target=""
    var achieve=""
    var balance=""
    var wallet_amt=""
    var todayreward=""
    var totalreward=""
    var available_reward=""
    var fname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_lay)

        Log.e("inside nerw","true")
        feededit = findViewById<View>(R.id.feededit) as EditText
        submitfeed = findViewById<View>(R.id.submit) as Button
        feedrad = findViewById<View>(R.id.fdradio) as TextView
        compradio = findViewById<View>(R.id.cmpradio) as TextView
        suggradio = findViewById<View>(R.id.suggradio) as TextView
        permradio = findViewById<View>(R.id.permradio) as TextView

        feededit!!.setScroller(Scroller(this@Save_Video))
        feededit!!.maxLines = 2
        feededit!!.isVerticalScrollBarEnabled = true
        feededit!!.movementMethod = ScrollingMovementMethod()

        utils = Utils(applicationContext)
        fname = utils!!.loadName()

        val intents=intent.extras
        id=intents!!.getString("id").toString()
        s=intents!!.getString("s").toString()
        deviceId=intents.getString("deviceId").toString()
        amount=intents.getString("amount").toString()
        gpv=intents.getString("gpv").toString()
        ibv=intents.getString("ibv").toString()
        purchase=intents.getString("purchase").toString()
        sales=intents.getString("sales").toString()
        target=intents.getString("target").toString()
        achieve=intents.getString("achieve").toString()
        balance=intents.getString("balance").toString()
        wallet_amt=intents.getString("wallet_amt").toString()
        todayreward=intents.getString("todayreward").toString()
        totalreward=intents.getString("totalreward").toString()
        available_reward=intents.getString("available_reward").toString()


        deviceId = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )

        s = "OS API " + Build.VERSION.SDK_INT + "- Version " + System.getProperty("os.version")
        //startprogress();
        /*PerformVersionTask ptask = new PerformVersionTask();
        ptask.execute();*/

        /* GetInfoTask task = new GetInfoTask();
        task.execute();*/speak!!.setOnClickListener {
            val colors = arrayOf(
                "தமிழ்",
                "English",
                "हिंदी",
                "తెలుగు",
                "മലയാളം",
                "ಕನ್ನಡಂ"
            )
            val builder =
                AlertDialog.Builder(this@Save_Video)
            builder.setTitle("Select your language")
            builder.setItems(
                colors
            ) { dialog, which ->
                dialog.dismiss()
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                if (which == 0) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ta-IN")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "உங்கள் கருத்து")
                } else if (which == 1) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en_US")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your feedback")
                } else if (which == 2) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "hi_IN")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "आपकी राय")
                } else if (which == 3) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "te_IN")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "మీ అభిప్రాయం")
                } else if (which == 4) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ml_IN")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "നിങ്ങളുടെ അഭിപ്രായം")
                } else if (which == 5) {
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "kn_IN")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "ನಿಮ್ಮ ಅಭಿಪ್ರಾಯ")
                }
                try {
                    startActivityForResult(intent, REQ_CODE)
                } catch (a: ActivityNotFoundException) {
                    Toast.makeText(
                        applicationContext,
                        "Sorry your device not supported",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            builder.show()
        }

        //fradcheck[0] ="check";
        feedrad!!.setOnClickListener {
            /*feedrad.setChecked(true);
                        compradio.setChecked(false);
                        suggradio.setChecked(false);
                        permradio.setChecked(false);*/
            str = "Feedback"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
            //submitfeed.setVisibility(View.VISIBLE);
        }

        compradio!!.setOnClickListener {
            /*feedrad.setChecked(false);
                        compradio.setChecked(true);
                        suggradio.setChecked(false);
                        permradio.setChecked(false);*/
            str = "Complaint"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
            //submitfeed.setVisibility(View.VISIBLE);
        }

        suggradio!!.setOnClickListener {
            /*feedrad.setChecked(false);
                        compradio.setChecked(false);
                        suggradio.setChecked(true);
                        permradio.setChecked(false);*/
            str = "Suggestion"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
            //submitfeed.setVisibility(View.VISIBLE);
        }

        permradio!!.setOnClickListener {
            /*feedrad.setChecked(false);
                        compradio.setChecked(false);
                        suggradio.setChecked(false);
                        permradio.setChecked(true);*/
            str = "Permission"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
            //submitfeed.setVisibility(View.VISIBLE);
        }



        submitfeed!!.setOnClickListener {
            submitfeed!!.isEnabled = false
            if (feededit!!.text.toString().length > 0 && !fradcheck[0].isEmpty()) {
                val feedoption = ""

                Videosubmit().execute("completed")
            } else {
                submitfeed!!.isEnabled = true
                if (feededit!!.text.toString().length == 0) {
                    Toast.makeText(
                        applicationContext,
                        "Please enter your feedback",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Please Check feedback category",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


    }
    fun change_back() {
        if (str == "Feedback") {
            selected(feedrad!!)
            unselected(compradio!!)
            unselected(suggradio!!)
            unselected(permradio!!)
        } else if (str == "Complaint") {
            unselected(feedrad!!)
            selected(compradio!!)
            unselected(suggradio!!)
            unselected(permradio!!)
        } else if (str == "Suggestion") {
            unselected(feedrad!!)
            unselected(compradio!!)
            selected(suggradio!!)
            unselected(permradio!!)
        } else if (str == "Permission") {
            unselected(feedrad!!)
            unselected(compradio!!)
            unselected(suggradio!!)
            selected(permradio!!)
        }
    }
    fun selected(tabTextView: TextView) {
        tabTextView.setTextColor(
            ContextCompat.getColor(
                this@Save_Video,
                R.color.white
            )
        )
        tabTextView.setBackgroundResource( /*context.resources.getDrawable(*/R.drawable.product_cat_selected)
    }

    fun unselected(tabTextView: TextView) {
        tabTextView.setTextColor(
            ContextCompat.getColor(
                this@Save_Video,
                R.color.black
            )
        )
        tabTextView.setBackgroundResource( /*context.resources.getDrawable(*/R.drawable.product_cat_unselected)
    }


     inner class Videosubmit :
        AsyncTask<String?, String?, String?>() {
        var pdialog: ProgressDialog? = null
        override fun onPreExecute() {
            pdialog = ProgressDialog(this@Save_Video)
            pdialog!!.setMessage("Video Submission...")
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
                jobj.put("vid", id)
                jobj.put("uname", utils!!.loadName())
                jobj.put("type", params[0])
                jobj.put("appversion", versionCode)
                jobj.put("mobileos", s)
                jobj.put("deviceid", deviceId)
                jobj.put("mobile_model", Build.MODEL)
                jobj.put("network", getNetworkClass(getApplicationContext()))

                //Log.i("Videossubmitinput", Appconstants.VIDEOSUBMIT + "    " + jobj.toString() + "");
                result = con.sendHttpPostjson2(Appconstants.VIDEOSUBMIT, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                // Log.e("Videossubmitresp", resp);
                val jobj = JSONObject(resp)
                if (jobj.getString("Status") == "Success") {
                    val jarr = jobj.getJSONArray("Response")
                    try {
                        utils!!.savePreferences("countvalue", "1")
                        //jsonobjarr.set(countpos, jarr.getJSONObject(0))
                        //utils!!.savePreferences("jsonobj", jsonobjarr.toString())
                    } catch (e: Exception) {
                    }
                    pdialog!!.dismiss()
                    Toast.makeText(
                        this@Save_Video,
                        "Congratulations! you are now eligible for reward points",
                        Toast.LENGTH_LONG
                    ).show()
                   feedback().execute(fname, feededit!!.getText().toString().trim({ it <= ' ' }), str)
                } else {
                    //Toast.makeText(Videoimage.this,"Failed to submit",Toast.LENGTH_LONG).show();
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

     inner class feedback :
        AsyncTask<String?, String?, String?>() {
        var pdialog: ProgressDialog? = null
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("feedbackTask", "started");
            pdialog = ProgressDialog(this@Save_Video)
            pdialog!!.setMessage("Feedback Submission...")
            pdialog!!.setCancelable(false)
            pdialog!!.show()
        }

         override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                jobj.put("user", params[0])
                jobj.put("comment", params[1])
                jobj.put("type", params[2])


                //Log.i("checkInput feedback", Appconstants.FEEDBACK_API+"    "+jobj.toString());
                result = con.sendHttpPostjson2(Appconstants.FEEDBACK_API, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("tabresp", resp + "");

            try {
                if (resp != null) {
                    pdialog!!.dismiss()
                    feededit!!.setText(null)
                    Toast.makeText(
                        this@Save_Video,
                        "Thanks for your valuable feedback!.",
                        Toast.LENGTH_LONG
                    ).show()
                    val i = Intent(this@Save_Video, HomePage::class.java)
                    i.putExtra("amount", amount)
                    i.putExtra("gpv", gpv)
                    i.putExtra("ibv", ibv)
                    i.putExtra("purchase", purchase)
                    i.putExtra("sales", sales)
                    i.putExtra("target", target)
                    i.putExtra("achieve", achieve)
                    i.putExtra("balance", balance)
                    i.putExtra("wallet_amt", wallet_amt)
                    i.putExtra("todayreward", todayreward)
                    i.putExtra("totalreward", totalreward)
                    i.putExtra("available_reward", available_reward)
                    startActivity(i)
                    finish()
                } else {
                    pdialog!!.dismiss()
                    Toast.makeText(this@Save_Video, "Failed to uplaod feedback.", Toast.LENGTH_LONG)
                        .show()
                    submitfeed!!.setEnabled(true)
                    val i = Intent(this@Save_Video, HomePage::class.java)
                    i.putExtra("amount", amount)
                    i.putExtra("gpv", gpv)
                    i.putExtra("ibv", ibv)
                    i.putExtra("purchase", purchase)
                    i.putExtra("sales", sales)
                    i.putExtra("target", target)
                    i.putExtra("achieve", achieve)
                    i.putExtra("balance", balance)
                    i.putExtra("wallet_amt", wallet_amt)
                    i.putExtra("todayreward", todayreward)
                    i.putExtra("totalreward", totalreward)
                    i.putExtra("available_reward", available_reward)
                    startActivity(i)
                    finish()
                }
            } catch (e: Exception) {
                pdialog!!.dismiss()
                e.printStackTrace()
                Toast.makeText(
                    getApplicationContext(),
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()
                val i = Intent(this@Save_Video, HomePage::class.java)
                i.putExtra("amount", amount)
                i.putExtra("gpv", gpv)
                i.putExtra("ibv", ibv)
                i.putExtra("purchase", purchase)
                i.putExtra("sales", sales)
                i.putExtra("target", target)
                i.putExtra("achieve", achieve)
                i.putExtra("balance", balance)
                i.putExtra("wallet_amt", wallet_amt)
                i.putExtra("todayreward", todayreward)
                i.putExtra("totalreward", totalreward)
                i.putExtra("available_reward", available_reward)
                startActivity(i)
                finish()
            }
        }
    }

    //Listens internet status whether net is on/off.
    /*public void addLogText(String log) {
            //linearbody = (LinearLayout) findViewById(R.id.linearbody);

            //retry_lay = (LinearLayout) findViewById(R.id.retry_lay);

            if(log.equals("NOT_CONNECT")){

                Log.e("inside noc",log);

                /// if connection is off then all views becomes disable
                insidethis="nc";
                stopCountdown();
                //Toast.makeText(getA,"Please turn on your network",Toast.LENGTH_LONG).show();




            }
            else
            {
                /// if connection is off then all views becomes enabled

                if(!(insidethis =="")) {
                    insidethis="";
                    startActivity(new Intent(Videoimage.this, HomePage.class));

                }
            }

            }
*/
    //comment close
    /* @Override
    protected void onRestart() {
        super.onRestart();

        stopCountdown();
        if(CheckNetwork.isInternetAvailable(Videoimage.this))
        {
            startActivity(new Intent(Videoimage.this, HomePage.class));
        }
        else
        {
            linearbody.setVisibility(View.GONE);
            retry_lay.setVisibility(View.VISIBLE);
        }
    }*/
    @SuppressLint("MissingPermission")
    fun getNetworkClass(context: Context): String? {
        val mTelephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkType = mTelephonyManager.networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> "2G"
            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> "3G"
            TelephonyManager.NETWORK_TYPE_LTE -> "4G"
            else -> "WIFI"
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result: ArrayList<*> = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!
                    feededit!!.setText(result[0] as CharSequence)
                }
            }
        }
    }





}