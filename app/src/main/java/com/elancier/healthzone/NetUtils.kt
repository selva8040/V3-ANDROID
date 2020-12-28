package com.elancier.healthzone

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager

object NetUtils {

    var TYPE_WIFI = 1
    var TYPE_MOBILE = 2
    var TYPE_NOT_CONNECTED = 0


    fun getConnectivityStatus(context: Context): Int {
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): String? {
        val conn = getConnectivityStatus(context)
        val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.getNetworkInfo(conn)
        var status: String? = null
        if (conn == TYPE_WIFI) {
            val wifiManager = context.getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.getConnectionInfo();
            val speed = wifiInfo.getLinkSpeed()
            return "Wifi Network Connected"

        } else if (conn == TYPE_MOBILE) {
            when (info!!.subtype) {
                TelephonyManager.NETWORK_TYPE_1xRTT -> return "50-100 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_CDMA -> return "14-64 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_EDGE -> return "50-100 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_EVDO_0 -> return "400-1000 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_EVDO_A -> return "600-1400 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_GPRS -> return "100 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_HSDPA -> return "2-14 Mbps speed Connection"
                TelephonyManager.NETWORK_TYPE_HSPA -> return "700-1700 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_HSUPA -> return "1-23 Mbps speed Connection"
                TelephonyManager.NETWORK_TYPE_UMTS -> return "400-7000 kbps speed Connection"
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                TelephonyManager.NETWORK_TYPE_EHRPD // API level 11
                -> return "1-2 Mbps speed Connection"
                TelephonyManager.NETWORK_TYPE_EVDO_B // API level 9
                -> return "5 Mbps speed Connection"
                TelephonyManager.NETWORK_TYPE_HSPAP // API level 13
                -> return "10-20 Mbps speed Connection"
                TelephonyManager.NETWORK_TYPE_IDEN // API level 8
                -> return "25 kbps speed Connection"
                TelephonyManager.NETWORK_TYPE_LTE // API level 11
                -> return "10+ Mbps speed Connection"
            // Unknown
                TelephonyManager.NETWORK_TYPE_UNKNOWN -> return ""
                else -> return ""
            }
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = "You're offline"
        }

        return status
    }
}