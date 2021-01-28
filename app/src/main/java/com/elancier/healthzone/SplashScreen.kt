package com.elancier.healthzone

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.crashlytics.android.Crashlytics
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.elancier.healthzone.Common.CheckNetwork
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.google.firebase.messaging.FirebaseMessaging
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.content_splash_screen.*
import org.json.JSONObject
import java.text.SimpleDateFormat

class SplashScreen : AppCompatActivity() {
    var logo: ImageView? = null
    var imageView5: ImageView? = null
    var fade_in_up: Animation? = null
    var utils: Utils? = null
    var alarmManager: AlarmManager? = null
    var pendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        /*window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
        setContentView(R.layout.activity_splash_screen)
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.hide()
        utils = Utils(applicationContext)


        logo = findViewById<View>(R.id.imageView4) as ImageView
        imageView5 = findViewById<View>(R.id.imageView4) as ImageView
        val currentapiVersion = Build.VERSION.SDK_INT
        if (currentapiVersion <= 25) {
            frameLayout1.visibility=View.GONE
            try {
                imageView5!!.setImageResource(R.mipmap.vlite_splash)
            }
            catch (e: Exception){
                imageView5!!.setImageResource(R.drawable.tvonlie)

            }
        } else {
            imageView5!!.setImageResource(R.mipmap.vlite_splash)
            frameLayout1.visibility=View.GONE
         /*   val path = "android.resource://" + packageName + "/" + R.raw.introvid
            geoloc_anim.setVideoURI(Uri.parse(path))
            geoloc_anim.start()
            geoloc_anim.setZOrderOnTop(true)
            geoloc_anim.setOnPreparedListener(object: MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer) {
                    mp.setLooping(true)
                    placeholder.setVisibility(View.GONE);
                }
            })*/
           //
            //imageView5!!.visibility = View.VISIBLE
        }


        // Intent alarmIntent = new Intent(this, MyBroadCastReceiver.class);
        // pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.ncolor1));
        }
        //StartAnimations();
        FirebaseMessaging.getInstance().subscribeToTopic("v3online")


        if (CheckNetwork.isInternetAvailable(this)) {
            GetInfoTask().execute()
        }
        else{
            navigate()
        }



        //startAlarm();
        /*Handler().postDelayed({ //   Log.i("valuesss",utils.loadName().toString().trim().length()+"    "+utils.loadName());

        }, SPLASH_TIME_OUT.toLong())*/
    }


    inner class GetInfoTask :
        AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
           /* progbar= ProgressDialog(this@Upload_Image)
            progbar!!.setMessage("Uploading...")
            progbar!!.show()*/
            Log.i("GetInfoTask", "started")
        }

        override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con = Connection()
            try {

                val json= JSONObject()
                json.put("uname", utils!!.loadName())
                result = con.sendHttpPostjson(
                    "https://www.v3healthzone.com/app/frontAd.php",
                    json
                )


            } catch (e:Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("tabresp", resp + "")
            //progbar!!.dismiss()
            try {
                if (resp != null) {
                    val json = JSONObject(resp)
                    val obj1 = json
                    if (obj1.getString("Status") == "Success") {
                        //Toast.makeText(applicationContext, "Uploaded Successfully.", Toast.LENGTH_SHORT).show()
                        val front=obj1.getJSONArray("front")
                        val middle=obj1.getJSONArray("middle")
                        val back=obj1.getJSONArray("back")
                        if(front.length()!=0) {
                            val frontobj = front.getJSONObject(0)
                            val front_url=frontobj.getString("url")
                            val front_type=frontobj.getString("type")
                            val front_seconds=frontobj.getString("seconds")
                            val front_linkUrl=frontobj.getString("linkUrl")

                            utils!!.savePreferences("front_url", front_url)
                            utils!!.savePreferences("front_type", front_type)
                            utils!!.savePreferences("front_seconds", front_seconds)
                            utils!!.savePreferences("front_linkUrl", front_linkUrl)

                        }
                        else{
                            utils!!.savePreferences("front_url", "")
                            utils!!.savePreferences("front_type", "")
                            utils!!.savePreferences("front_seconds", "")
                            utils!!.savePreferences("front_linkUrl", "")
                        }

                        if(middle.length()!=0) {
                            val frontobj = middle.getJSONObject(0)
                            val front_url=frontobj.getString("url")
                            val front_type=frontobj.getString("type")
                            val front_seconds=frontobj.getString("seconds")
                            val front_linkUrl=frontobj.getString("linkUrl")

                            utils!!.savePreferences("middle_url", front_url)
                            utils!!.savePreferences("middle_type", front_type)
                            utils!!.savePreferences("middle_seconds", front_seconds)
                            utils!!.savePreferences("middle_linkUrl", front_linkUrl)

                        }
                        else{
                            utils!!.savePreferences("middle_url", "")
                            utils!!.savePreferences("middle_type", "")
                            utils!!.savePreferences("middle_seconds", "")
                            utils!!.savePreferences("middle_linkUrl", "")
                        }

                        if(back.length()!=0) {
                            val backobj = back.getJSONObject(0)
                            val back_url=backobj.getString("url")
                            val back_type=backobj.getString("type")
                            val back_seconds=backobj.getString("seconds")
                            val back_linkUrl=backobj.getString("linkUrl")

                            utils!!.savePreferences("back_url", back_url)
                            utils!!.savePreferences("back_type", back_type)
                            utils!!.savePreferences("back_seconds", back_seconds)
                            utils!!.savePreferences("back_linkUrl", back_linkUrl)

                        }
                        else{
                            utils!!.savePreferences("back_url", "")
                            utils!!.savePreferences("back_type", "")
                            utils!!.savePreferences("back_seconds", "")
                            utils!!.savePreferences("back_linkUrl", "")
                        }
                        navigate()

                    }
                    else{
                        navigate()

                    }
                } else {
                    navigate()

                    //println("errorneet"+e.toString())

                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (e: Exception) {

                e.printStackTrace()
                navigate()
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    fun navigate(){
        Handler().postDelayed(Runnable {


        if (utils!!.loadName().toString().trim { it <= ' ' }.length > 0) {
            val today = SimpleDateFormat("dd/MM/yyyy")
                .format(System.currentTimeMillis())
            val currentapiVersions = Build.VERSION.SDK_INT
            if (currentapiVersions < 30) {
                utils!!.savePreferences("jsonobj", "")
            }
            if (today != utils!!.loadcheckdt() || utils!!.loadjsonval().isEmpty()) {
                val currentapiVersion = Build.VERSION.SDK_INT
                if (currentapiVersion < 30) {
                    utils!!.savePreferences("jsonobj", "")
                    val i = Intent(this@SplashScreen, Tableview::class.java)
                    startActivity(i)
                } else {
                    val i = Intent(this@SplashScreen, Tableview::class.java)
                    startActivity(i)
                }
            } else {
                val i = Intent(this@SplashScreen, Tableview::class.java)
                startActivity(i)
            }
        } else {
            val i = Intent(this@SplashScreen, Login::class.java)
            startActivity(i)
        }
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)


        // close this activity
        finish()
        },2000)
    }

    private fun startAlarm() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val interval = 300000
        alarmManager!!.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis(),
            interval.toLong(),
            pendingIntent
        )
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
    }

    private fun cancelAlarm() {
        alarmManager!!.cancel(pendingIntent)
        Toast.makeText(applicationContext, "Alarm Cancelled", Toast.LENGTH_LONG).show()
    }

    private fun StartAnimations() {
        YoYo.with(Techniques.ZoomInUp)
            .duration(4000)
            .playOn(findViewById(R.id.logo))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    companion object {
        private const val SPLASH_TIME_OUT = 2000
    }
}