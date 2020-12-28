package com.elancier.healthzone

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
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
import com.elancier.healthzone.Common.Utils
import com.google.firebase.messaging.FirebaseMessaging
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.content_splash_screen.*
import java.lang.Exception
import java.text.SimpleDateFormat

class SplashScreen : AppCompatActivity() {
    var logo: ImageView? = null
    var imageView5: ImageView? = null
    var fade_in_up: Animation? = null
    var utils: Utils? = null
    var alarmManager: AlarmManager? = null
    var pendingIntent: PendingIntent? = null
    override fun onCreate(savedInstanceState:Bundle?) {
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
            findViewById<View>(R.id.toolbar) as Toolbar
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
            catch (e:Exception){
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


        //startAlarm();
        Handler().postDelayed({ //   Log.i("valuesss",utils.loadName().toString().trim().length()+"    "+utils.loadName());
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
        }, SPLASH_TIME_OUT.toLong())
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