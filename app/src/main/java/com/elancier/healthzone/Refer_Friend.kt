package com.elancier.healthzone

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.refer_a_friend.*

class Refer_Friend : AppCompatActivity() {
    internal lateinit var utils: Utils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.refer_a_friend)
        utils = Utils(applicationContext)

        supportActionBar!!.title = "Refer a friend"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.newdashboard_gradient
            )
        )
        wtapp.setOnClickListener {
            onClickWhatsApp()

        }
        instagram.setOnClickListener {
            fbsend()

        }
        fb.setOnClickListener {
            moresend()

        }

    }
    fun onClickWhatsApp() {
        val waIntent = Intent(Intent.ACTION_SEND)
        waIntent.setType("text/plain")
        val text = "https://v3.v3onlinetv.com/registers.php?username="+utils.loadId()
        waIntent.setPackage("com.whatsapp")
        if (waIntent != null) {
            waIntent.putExtra(Intent.EXTRA_TEXT, text) //
            startActivity(Intent.createChooser(waIntent, "Share with"))
        } else {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
        }
    }
    fun moresend(){
        val message = "https://v3.v3onlinetv.com/registers.php?username="+utils.loadId()
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/plain"
        share.putExtra(Intent.EXTRA_TEXT, message)

        startActivity(Intent.createChooser(share, "Refer via"))
    }

    fun fbsend(){
        val message = "https://v3.v3onlinetv.com/registers.php?username="+utils.loadId()
        val intent1 =  Intent(Intent.ACTION_SEND);
        intent1.setPackage("com.facebook.katana");
        intent1.setAction("android.intent.action.SEND");
        intent1.setType("text/plain");
        intent1.putExtra("android.intent.extra.TEXT", message);
        startActivity(intent1);
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