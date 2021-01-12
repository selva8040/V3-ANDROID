package com.elancier.healthzone

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_video_ad.*


class Front_VideoActivity : AppCompatActivity() {
    var imageurl=""
    var gifurl=""
    var videourl=""
    var seconds=""
    private var countDownTimer: CountDownTimer? = null
    var utils: Utils?=null
    var videoresp=""
    var orgdomain=""
    var orgcount=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_ad)

        utils=Utils(this)


        try{
            videoresp=intent.extras!!.getString("videoresp").toString()
            orgdomain=intent.extras!!.getString("orgdomain").toString()
            orgcount=intent.extras!!.getString("orgcount").toString()
        }
        catch (e:Exception)

        {

        }

        wb.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(utils!!.front_linkUrl()))
                startActivity(browserIntent)

            }
            catch(e:Exception){

            }
        }
        videoView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(utils!!.front_linkUrl()))
                startActivity(browserIntent)

            }
            catch(e:Exception){

            }
        }


        try {
            var frm=utils!!.front_type()
            var seconds=utils!!.front_seconds()

            if(frm=="Image"){
                imageurl = utils!!.front_url()
                loadimage()
                startTimer(seconds.toInt())

            }

            else if(frm=="Gif"){
                gifurl = utils!!.front_url()
                loadgif()
                imageButton.isEnabled=false
                wb.visibility= View.VISIBLE

                card.isEnabled=false

                startTimer(seconds.toInt())

               /* Handler().postDelayed(Runnable {
                }, 4000)*/
            }

            else if(frm=="Video"){
                videourl = utils!!.front_url()
                wb.visibility= View.INVISIBLE
                loadvideo()
                imageButton.isEnabled=false
                card.isEnabled=false
                startTimer(seconds.toInt())

               /* Handler().postDelayed(Runnable {
                }, 4000)*/

            }
            else{
                val currentapiVersion = Build.VERSION.SDK_INT

                //textView2.setText("SKIP AD")
                if (currentapiVersion > 20) {
                    println("inside21")
                    val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                    a.putExtra("videoresp", videoresp)
                    a.putExtra("orgdomain", orgdomain)
                    a.putExtra("orgcount", orgcount)
                    a.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    a.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(a)
                    overridePendingTransition(0, 0)
                    finish()
                } else {
                    println("inside20")
                    val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                    //a.putExtra("videoresp", videoresp);
                    startActivity(a)
                    finish()
                }
            }
        }
        catch (e: Exception){
            val currentapiVersion = Build.VERSION.SDK_INT

            //textView2.setText("SKIP AD")
            if (currentapiVersion > 20) {
                println("inside21")
                val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                a.putExtra("videoresp", videoresp)
                a.putExtra("orgdomain", orgdomain)
                a.putExtra("orgcount", orgcount)
                a.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                a.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(a)
                overridePendingTransition(0, 0)
                finish()
            } else {
                println("inside20")
                val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                //a.putExtra("videoresp", videoresp);
                startActivity(a)
                finish()
            }
        }


        /*imageButton.setOnClickListener {
            finish()
        }
        card.setOnClickListener {
            finish()
        }
        textView.setOnClickListener {
            finish()
        }*/

      /*  cardView4.setOnClickListener {
            val appPackageName =
                packageName // getPackageName() from Context or Activity object

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone")

                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone")
                    )
                )
            }
        }*/


    }

    private fun startTimer(noOfMinutes: Int) {
        Log.e("countvalue", "count")
        object : CountDownTimer(noOfMinutes * 1000.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //if((millisUntilFinished/1000).toInt()==6){
                    card.visibility=View.VISIBLE
               // }
                textView2.setText("Ad ends in : " + millisUntilFinished / 1000 + " sec")
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                val currentapiVersion = Build.VERSION.SDK_INT

                //textView2.setText("SKIP AD")
                if (currentapiVersion > 20) {
                    println("inside21")
                    val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                    a.putExtra("videoresp", videoresp)
                    a.putExtra("orgdomain", orgdomain)
                    a.putExtra("orgcount", orgcount)
                    a.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    a.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(a)
                    overridePendingTransition(0, 0)
                    finish()
                } else {
                    println("inside20")
                    val a = Intent(this@Front_VideoActivity, Videoimage::class.java)
                    //a.putExtra("videoresp", videoresp);
                    startActivity(a)
                    finish()
                }
                imageButton.isEnabled=true
                card.isEnabled=true

            }
        }.start()
    }

    fun loadvideo(){
        videoView.visibility=View.VISIBLE
        wb.visibility=View.INVISIBLE
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
      /*  val params = videoView.layoutParams as ConstraintLayout.LayoutParams
        params.width = (300 * metrics.density).toInt()
        params.height = (250 * metrics.density).toInt()
        params.leftMargin = 30
        videoView.setLayoutParams(params);*/
        videoView.setMediaController(null)
        videoView.setVideoPath(videourl)
        videoView.start();
    }
    fun loadgif(){
        videoView.visibility=View.INVISIBLE
        wb.visibility=View.VISIBLE
        Glide.with(this)
             .load(gifurl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(wb);
        //wb.loadUrl()

    }

    fun loadimage(){
        videoView.visibility=View.INVISIBLE
        wb.visibility=View.VISIBLE
        Glide.with(this)
            .load(imageurl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(wb);
        //wb.loadUrl()

    }

    override fun onBackPressed() {

    }
}