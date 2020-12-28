package com.elancier.healthzone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.elancier.healthzone.NetUtils

class NetChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val status = NetUtils.getConnectivityStatusString(context)

        //Snackbar.make(coodi,status,Snackbar.LENGTH_SHORT).show()
        Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
    }
}