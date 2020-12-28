package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CheckNetwork
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.feedback_layout.*
import org.json.JSONArray
import org.json.JSONObject

class FeedbackActivity : AppCompatActivity() {
    var str = "Feedback"
    val fradcheck = arrayOf("")
    private val REQ_CODE = 100
    lateinit var progbar : Dialog
    lateinit var utils : Utils

    var amount = ""
    var gpv = ""
    var ibv = ""
    var purchase = ""
    var sales = ""
    var target = ""
    var achieve = ""
    var balance = ""
    var wallet_amt = ""
    var todayreward = ""
    var totalreward = ""
    var available_reward = ""
    var vid = ""
    var videoid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feedback_layout)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setHomeAsUpIndicator(R.mipmap.back_arrow)
        progbar = Dialog(this)
        progbar.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progbar.getWindow()!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT))
        progbar.setContentView(R.layout.load)
        progbar.setCancelable(false)

        utils = Utils(applicationContext)
        try {
            amount = intent.getStringExtra("amount").toString()
            gpv = intent.getStringExtra("gpv").toString()
            ibv = intent.getStringExtra("ibv").toString()
            purchase = intent.getStringExtra("purchase").toString()
            sales = intent.getStringExtra("sales").toString()
            target = intent.getStringExtra("target").toString()
            achieve = intent.getStringExtra("achieve").toString()
            balance = intent.getStringExtra("balance").toString()
            wallet_amt = intent.getStringExtra("wallet_amt").toString()
            todayreward = intent.getStringExtra("todayreward").toString()
            totalreward = intent.getStringExtra("totalreward").toString()
            available_reward = intent.getStringExtra("available_reward").toString()
            vid = intent.getStringExtra("vid").toString()
        } catch (e: Exception) {
        }
        videoid = intent.getStringExtra("videoid").toString()

        println("videoid : "+videoid)
        if (videoid=="14"){
            check.visibility=View.VISIBLE
        }else{
            check.visibility=View.GONE
        }
        yes.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                no.isChecked=false
            }
            println("yes : "+yes.isChecked.compareTo(false))
            println("no : "+no.isChecked.compareTo(false))
        }
        no.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                yes.isChecked=false
            }
            println("yes : "+yes.isChecked.compareTo(false))
            println("no : "+no.isChecked.compareTo(false))
        }
        fdradio.setOnClickListener(View.OnClickListener {
            /*fdradio.setChecked(true);
                cmpradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(false);*/
            str = "Feedback"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
//submit.setVisibility(View.VISIBLE);
        })

        cmpradio.setOnClickListener(View.OnClickListener {
            /*fdradio.setChecked(false);
                cmpradio.setChecked(true);
                suggradio.setChecked(false);
                permradio.setChecked(false);*/
            str = "Complaint"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
//submit.setVisibility(View.VISIBLE);
        })

        suggradio.setOnClickListener(View.OnClickListener {
            /*fdradio.setChecked(false);
                cmpradio.setChecked(false);
                suggradio.setChecked(true);
                permradio.setChecked(false);*/
            str = "Suggestion"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
//submit.setVisibility(View.VISIBLE);
        })

        permradio.setOnClickListener(View.OnClickListener {
            /*fdradio.setChecked(false);
                cmpradio.setChecked(false);
                suggradio.setChecked(false);
                permradio.setChecked(true);*/
            str = "Permission"
            change_back()
            fradcheck[0] = "check"
            //feededit.setVisibility(View.VISIBLE);
//submit.setVisibility(View.VISIBLE);
        })

        val speak: CircleImageView = findViewById(R.id.imageButton2)
        speak.setOnClickListener {
            val colors = arrayOf("தமிழ்", "English", "हिंदी", "తెలుగు", "മലയാളം", "ಕನ್ನಡಂ")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Select your language")
            builder.setItems(colors) { dialog, which ->
                dialog.dismiss()
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
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
                    Toast.makeText(applicationContext,
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show()
                }
            }
            builder.show()
        }

        submit.setOnClickListener(View.OnClickListener {

            if (feededit.text.toString().trim().length > 0) {
                val feedoption = ""
                /*if(fdradio.isChecked()==true){
                        feedoption="Feedback";
                    }
                    else if(cmpradio.isChecked()==true){
                        feedoption="Complaint";

                    }
                    else if(suggradio.isChecked()==true){
                        feedoption="Suggestion";

                    }
                    else if(permradio.isChecked()==true){
                        feedoption="Permission";

                    }*/
                if (CheckNetwork.isInternetAvailable(this)) {
                    if (videoid=="14"){
                        if (yes.isChecked||no.isChecked){
                            submit.setEnabled(false)
                            progbar.show()
                            val task = feedback();
                            task.execute();
                        }else{
                            submit.setEnabled(true)
                            Toast.makeText(this,"select any option",Toast.LENGTH_LONG).show()
                        }
                    }else {
                        submit.setEnabled(false)
                        progbar.show()
                        val task = feedback();
                        task.execute();
                    }
                } else {
                    submit.setEnabled(true)
                    Toast.makeText(applicationContext, "Please turn on your network1", Toast.LENGTH_LONG).show()
                    /*linearbody.setVisibility(View.GONE)
                    retry_lay.setVisibility(View.VISIBLE)*/
                }
            } else {
                submit.setEnabled(true)
                if (feededit.text.toString().length == 0) {
                    Toast.makeText(applicationContext, "Please enter your feedback", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Please Check feedback category", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        Toast.makeText(this,"Submit Your Feedback and Exit",Toast.LENGTH_LONG).show()
    }

    fun change_back() {
        if (str == "Feedback") {
            selected(fdradio)
            unselected(cmpradio)
            unselected(suggradio)
            unselected(permradio)
        } else if (str == "Complaint") {
            unselected(fdradio)
            selected(cmpradio)
            unselected(suggradio)
            unselected(permradio)
        } else if (str == "Suggestion") {
            unselected(fdradio)
            unselected(cmpradio)
            selected(suggradio)
            unselected(permradio)
        } else if (str == "Permission") {
            unselected(fdradio)
            unselected(cmpradio)
            unselected(suggradio)
            selected(permradio)
        }
    }

    fun selected(tabTextView: TextView) {
        tabTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
        tabTextView.setBackgroundResource( /*context.resources.getDrawable(*/R.drawable.product_cat_selected)
    }

    fun unselected(tabTextView: TextView) {
        tabTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
        tabTextView.setBackgroundResource( /*context.resources.getDrawable(*/R.drawable.product_cat_unselected)
    }

    inner class feedback : AsyncTask<String?, String?, String?>() {
        override fun onPreExecute() { //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String?): String? {
            var result: String? = null
            val con = Connection()
            val y =yes.isChecked.compareTo(false)
            println("yes : "+yes.isChecked.compareTo(false))
            println("no : "+no.isChecked.compareTo(false))
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("vid", vid)
                jobj.put("feedback", feededit.text.toString().trim())
                jobj.put("yes",yes.isChecked.compareTo(false))
                jobj.put("no", no.isChecked.compareTo(false))
                Log.i("checkInput feedback", Appconstants.New_FEED + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.New_FEED, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
           // Log.i("tabresp", resp + "")
            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss()
            }
            try {
                if (resp != null) {
                    val js = JSONArray(resp)
                    val jobj = js.getJSONObject(0) //new JSONObject();
                    if (jobj.getString("Status") == "Success") {
                        feededit.setText(null)
                        Toast.makeText(this@FeedbackActivity, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        Toast.makeText(this@FeedbackActivity, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@FeedbackActivity, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Toast.makeText(this@FeedbackActivity, "Something went wrong.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
