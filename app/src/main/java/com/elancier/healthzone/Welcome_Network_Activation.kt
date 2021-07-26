package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_network__activation.*
import org.json.JSONObject

class Welcome_Network_Activation : AppCompatActivity() {
    lateinit var utils: Utils
    var progbar: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network__activation)
        utils=Utils(applicationContext)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.newdashboard_gradient
            )
        )
        supportActionBar!!.setTitle("Welcome Activation")


        if(utils.loadnspdtime().toString().isNotEmpty()){
            act1.visibility=View.VISIBLE
            act1.setInputType(InputType.TYPE_CLASS_TEXT)
            act1.setText(utils.loadnspdtime())
            act1.setFocusable(false)
            act2.visibility=View.INVISIBLE
            act3.visibility=View.GONE
            button17.visibility=View.INVISIBLE
        }

        act1.setLongClickable(false);
        act1.setTextIsSelectable(false);
        act2.setLongClickable(false);
        act2.setTextIsSelectable(false);
        act3.setLongClickable(false);
        act3.setTextIsSelectable(false);

        val mOnLongClickListener =
            View.OnLongClickListener { // prevent context menu from being popped up, so that user
                // cannot copy/paste from/into any EditText fields.
                true
            }

        act1.setOnLongClickListener(View.OnLongClickListener { true })

        act1.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })

        act1.cancelLongPress()


        act2.setOnLongClickListener(View.OnLongClickListener { true })

        act2.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })

        act2.cancelLongPress()



        act3.setOnLongClickListener(View.OnLongClickListener { true })

        act3.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return false
            }
        })

        act3.cancelLongPress()

        button17.setOnClickListener {
            if(button17.text=="NEXT") {
                if(act1.text.toString().isNotEmpty()&&act2.text.toString().isNotEmpty()){
                    if(act1.text.toString().equals(act2.text.toString())){
                        act3.visibility= View.VISIBLE
                        button17.setText("SUBMIT")
                    }
                    else{
                       val toast= Toast.makeText(
                           applicationContext,
                           "Both pins are mismatch.",
                           Toast.LENGTH_SHORT
                       )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }
                else{
                    if(act1.text.toString().isEmpty()){
                        act1.setError("Required field")
                    }
                    if(act2.text.toString().isEmpty()){
                        act2.setError("Required field")

                    }
                }

            }
            else if(button17.text.toString()=="SUBMIT"){
                if(act3.text.isNotEmpty()){
                    if(act3.text.toString().equals(utils.loadmob())){
                        ChangepinTask().execute()
                    }
                    else{
                        act3.setError("Incorrect Mobile Number")
                    }
                }
                else{
                    act3.setError("Required field")
                }
            }


        }

    }



    inner class ChangepinTask :
        AsyncTask<String?, Void?, String?>() {
        var value = ""
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            loadprogress()
         //   progbar!!.show()
            //Log.i("GetInfoTask", "started");
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: String?): String? {
            val deviceId = Settings.Secure.getString(
                this@Welcome_Network_Activation.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "Activation")
                jobj.put("deviceid", deviceId)
                jobj.put("pin", act2.text.toString().toString())
                jobj.put("mobile", act3.text.toString().toString())
                jobj.put("version", BuildConfig.VERSION_CODE)

                Log.i(
                    "HomePage Input",
                    Appconstants.welnsp_activation + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.welnsp_activation, jobj, "")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("error", e.toString())
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                if (resp != null) {
                    val jsonarr = JSONObject(resp)

                    if (jsonarr.getString("Status") == "Success") {
                        //JSONObject jarr = json.getJSONObject("Response");
                        progbar!!.dismiss()
                        try {
                            toast("Request Submitted.")
                            finish()

                        } catch (e: java.lang.Exception) {
                            progbar!!.dismiss()
                        }
                    } else {
                        progbar!!.dismiss()

                        toast(jsonarr.getString("Response"))
                    }
                } else {
                    //retry.show();
                    progbar!!.dismiss()

                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                Log.e("err", e.toString())
                progbar!!.dismiss()


                //retry.show();
            }
        }
    }
    fun toast(msg: String?) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
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
}