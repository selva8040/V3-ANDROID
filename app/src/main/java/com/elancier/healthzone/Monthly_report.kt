package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils

import kotlinx.android.synthetic.main.activity_monthly_report.*
import org.json.JSONArray
import org.json.JSONObject
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.app.DownloadManager as DownloadManager
import io.fabric.sdk.android.services.settings.IconRequest.build

import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.Files.exists
import io.fabric.sdk.android.services.settings.IconRequest.build

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_daterequest_.*
import kotlinx.android.synthetic.main.activity_monthly_report.frdt
import kotlinx.android.synthetic.main.activity_monthly_report.todt
import kotlinx.android.synthetic.main.monthly_header.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Monthly_report : AppCompatActivity() {
    val MembersArrays = ArrayList<MembersData>()
    lateinit var adp : MemberAdapter
    var unamevalue="";
    lateinit var storeDir:String
    lateinit var context: Activity
    internal lateinit var utils: Utils
    private var downloadManager = null;
    private var Download_Uri = null;
    internal var progbar: Dialog? = null

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    internal lateinit var shp: SharedPreferences
    private var pdialog:ProgressDialog?=null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_report)

        utils = Utils(applicationContext)

        shp = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        unamevalue= utils.loadName()

        context=this;
        storeDir=Environment.getExternalStorageDirectory().toString();

        val task1 = recdateget()
        task1.execute("")

        menu_img.setOnClickListener {
            onBackPressed()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.theme));
        }
        button3.setOnClickListener {
            if(frdt.text.isNotEmpty()&&todt.text.toString().isNotEmpty()) {
                val task = billget()
                task.execute("")
            }
            else{
                Toast.makeText(applicationContext,"Please select date range",Toast.LENGTH_SHORT).show()
            }
        }


        frdt.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub

            try {
                //utils.savePreferences("datealready", frdt.text.toString())
            }
            catch (e:java.lang.Exception){

            }

            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val cals = Calendar.getInstance()  // or pick another time zone if necessary
            cals.timeInMillis = System.currentTimeMillis()
            cals.set(Calendar.DAY_OF_MONTH, cals.get(Calendar.DAY_OF_MONTH) + 1)
            cals.set(Calendar.HOUR_OF_DAY, 0)
            cals.set(Calendar.MINUTE, 0)
            cals.set(Calendar.SECOND, 0)

            //println("fgbh" + "tm1 : " + cals.timeInMillis / 1000)

            val datePickerDialog = DatePickerDialog(this@Monthly_report,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        frdt.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    }, mYear, mMonth, mDay)



            val calendar = Calendar.getInstance();
            val mdformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            val strDate = mdformat.format(calendar.getTime());
            //utils.savePreferences("datetime", strDate)



            //val simpledateformate = SimpleDateFormat("dd-MM-yyyy");
            //val date = simpledateformate.parse(System.currentTimeMillis());
            //datePickerDialog.getDatePicker().setMinDate(cals.timeInMillis);

            datePickerDialog.show()

        })


        todt.setOnClickListener(View.OnClickListener {
            // TODO Auto-generated method stub

            try {
                //utils.savePreferences("datealready", frdt.text.toString())
            }
            catch (e:java.lang.Exception){

            }

            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)
            val cals = Calendar.getInstance()  // or pick another time zone if necessary
            cals.timeInMillis = System.currentTimeMillis()
            cals.set(Calendar.DAY_OF_MONTH, cals.get(Calendar.DAY_OF_MONTH) + 1)
            cals.set(Calendar.HOUR_OF_DAY, 0)
            cals.set(Calendar.MINUTE, 0)
            cals.set(Calendar.SECOND, 0)

            //println("fgbh" + "tm1 : " + cals.timeInMillis / 1000)

            val datePickerDialog = DatePickerDialog(this@Monthly_report,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        todt.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)

                    }, mYear, mMonth, mDay)



            val calendar = Calendar.getInstance();
            val mdformat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            val strDate = mdformat.format(calendar.getTime());
            //utils.savePreferences("datetime", strDate)



            //val simpledateformate = SimpleDateFormat("dd-MM-yyyy");
            //val date = simpledateformate.parse(System.currentTimeMillis());
            //datePickerDialog.getDatePicker().setMinDate(cals.timeInMillis);

            datePickerDialog.show()

        })


        //memberslist.adapter = adp


    }


    private inner class recdateget : AsyncTask<String, String, String>() {

        var dt=""
        override fun onPreExecute() {

            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")



        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()
            dt=param[0]
            try {
                val jobj = JSONObject()
                jobj.put("uname", unamevalue)
                jobj.put("type", "Date List")
                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
              //  Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
               // Log.i("tabresp", resp!! + "")

            }

            //progbar!!.dismiss()

            try {
                if (resp != null) {

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())
                    //utils.savePreferences("date",dt)
                    if(status=="Success") {
                        utils


                        var billusernameval=jobj.get("Response").toString()
                        val df = SimpleDateFormat("yyyy-MM-dd")
                        //val formattedDate = df.format(billusernameval)
                        val date = df.parse(billusernameval) as Date
                        val newFormat = SimpleDateFormat("dd-MM-yyyy")
                        val finalString = newFormat.format(date)
                        frdt.setText(finalString.toString())



                    }
                    else{

                        Toast.makeText(applicationContext,"Date not saved",Toast.LENGTH_SHORT).show()

                        //billnum.setText(null)
                        //billusername.setText(null)

                        //billnum.setError("Invalid bill number")
                    }









                    //feededit.setText(null)
                    //Toast.makeText(this@HomePage, "Thanks for your valuable feedback!.", Toast.LENGTH_LONG).show()


                } else {


                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()


                //Log.e("ERROR OTP",e.toString())
                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }



    private inner class billget : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("feedbackTask", "started")

            progbar = Dialog(this@Monthly_report)
            progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progbar!!.getWindow()!!.setBackgroundDrawable(
                    ColorDrawable(android.graphics.Color.TRANSPARENT))
            progbar!!.setContentView(R.layout.load)
            progbar!!.setCancelable(false)
            progbar!!.show()




        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", unamevalue)
                jobj.put("type", "Month")
                jobj.put("fdate", frdt.text.toString())
                jobj.put("tdate", todt.text.toString())

                Log.i("checkInput feedback", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
                Log.i("tabresp", resp!! + "")
            }
            catch (e:java.lang.Exception){
                //Log.i("tabresp", resp!! + "")

            }

            //progbar!!.dismiss()

            try {
                if (resp != null) {
                    var data = MembersData()

                    val jarray= JSONArray(resp);

                    val jobj=jarray.getJSONObject(0)



                    var status=(jobj.get("Status").toString())

                    if(status=="Success") {

                        //Toast.makeText(applicationContext,"Date saved", Toast.LENGTH_SHORT).show()
                        var jarrayval=jobj.getJSONObject("Response")

                            val grand = jarrayval.get("grandtotal").toString()
                        val viewed = jarrayval.get("viewed").toString()
                        val notviewed = jarrayval.get("not_viewed").toString()
                        val feedback = jarrayval.get("feedback_present").toString()
                        val feedbacknull = jarrayval.get("feedback_null").toString()
                        wholelin.visibility=View.VISIBLE
                        textView30.setText(":      "+grand)
                        textViewview30.setText(":      "+viewed)
                        notextViewview30.setText(":      "+notviewed)
                        feedtextViewview30.setText(":      "+feedback)
                        feedprtextViewview30.setText(":      "+feedbacknull)


                        progbar!!.dismiss()
                        //memberslist.adapter = adp

                        //billusername.setText(billusernameval)

                    }
                    else{
                        progbar!!.dismiss()

                        Toast.makeText(applicationContext,"No reports found", Toast.LENGTH_SHORT).show()

                    }


                } else {
                    progbar!!.dismiss()
                    //Log.e("ERROR OTP",resp)

                    //Toast.makeText(this@HomePage, "Failed to uplaod feedback.", Toast.LENGTH_LONG).show()
                    Toast.makeText(applicationContext,"No reports found", Toast.LENGTH_SHORT).show()


                }

            } catch (e: Exception) {
                e.printStackTrace()
                //Log.e("ERROR OTP",e.toString())
                progbar!!.dismiss()

                Toast.makeText(applicationContext,"No reports found", Toast.LENGTH_SHORT).show()

                //Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.share_pins, menu)
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
        if (id == R.id.share_pin) {
            //sharearray()
            // Fillter();
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class BackTask : AsyncTask<String, Int, Void>() {
        internal lateinit var mNotifyManager: NotificationManager
        internal lateinit var mBuilder: NotificationCompat.Builder
        override fun onPreExecute() {
            super.onPreExecute()
            mNotifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mBuilder = NotificationCompat.Builder(context)
            mBuilder.setContentTitle("File Download")
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.v3onlinetv)
            Toast.makeText(context, "Downloading the file...", Toast.LENGTH_LONG).show()

        }

        override fun doInBackground(vararg params: String?): Void? {
            val url: URL
            var count: Int
            try {
                url = URL(params[0])
                var pathl = ""
                try {
                    val f = File(storeDir)
                    if (f.exists()) {
                        val con = url.openConnection() as HttpURLConnection
                        val `is` = con.inputStream
                        val pathr = url.path
                        val filename = pathr.substring(pathr.lastIndexOf('/') + 1)
                        pathl = storeDir + "/" + filename
                        val fos = FileOutputStream(pathl)
                        val lenghtOfFile = con.contentLength
                        val data = ByteArray(1024)
                        var total: Long = 0
                        var count: Int = 0

                        while ({count = `is`.read(data);count }() != -1) {
                            total += count.toLong()
                            // publishing the progress
                            publishProgress((total * 100 / lenghtOfFile).toInt())
                            // writing data to output file
                            fos.write(data, 0, count)
                        }

                        `is`.close()
                        fos.flush()
                        fos.close()
                    } else {
                       // Log.e("Error", "Not found: $storeDir")

                    }

                } catch (e: Exception) {
                    e.printStackTrace()

                }

            } catch (e: MalformedURLException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

            return null

        }

        protected override fun onProgressUpdate(vararg values: Int?) {

            values[0]?.let { mBuilder.setProgress(100, it, false) }
            // Displays the progress bar on notification
            mNotifyManager.notify(0, mBuilder.build())
        }

        override fun onPostExecute(result: Void) {
            mBuilder.setContentText("Download complete")
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false)
            mNotifyManager.notify(0, mBuilder.build())
        }

    }

    fun download(url:String,nm:String){
        //Log.e("clck",url)
        val downloadManager: DownloadManager
        val refid: Long
        val Download_Uri: Uri

        downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        Download_Uri = Uri.parse(url)

        val request = DownloadManager.Request(Download_Uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setAllowedOverRoaming(false)
        request.setTitle("Report $nm")
        request.setDescription("Downloading " + "Report $nm")
        request.setVisibleInDownloadsUi(true)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/V3 online/" + "/" + "V3 online" + ".csv")

        refid = downloadManager.enqueue(request)

        Toast.makeText(context, "Download Successfully", Toast.LENGTH_SHORT).show()
    }

}
