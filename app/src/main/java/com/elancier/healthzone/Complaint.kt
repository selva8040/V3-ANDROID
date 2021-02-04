package com.elancier.healthzone

import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.elancier.healthzone.Common.Utils
import kotlinx.android.synthetic.main.activity_complaint.*
import java.io.File
import java.io.FileOutputStream
import java.util.*


class Complaint : AppCompatActivity() {
    internal lateinit var utils: Utils
    val activity = this
    val RequestPermissionCode = 7
    lateinit var pDialo : ProgressDialog

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)
        utils = Utils(applicationContext)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        Handler().postDelayed(Runnable {
            if (CheckingPermissionIsEnabledOrNot()) {

            }else{
                RequestMultiplePermission()
            }
        }, 2000)


        try {
            uname.setText(utils.loadName())
            name.setText(utils.loadmob())
            cutoff.setText(Build.VERSION.RELEASE.toString())
            visualtime.setText(android.os.Build.MODEL.toString())
            apkversion.setText(BuildConfig.VERSION_NAME.toString())
            val now = Date()
            val l=android.text.format.DateFormat.format("dd-MM-yyyy hh:mm:ss", now)
            dttime.setText(l.toString())
        }
        catch (e:Exception){

        }



        button7.setOnClickListener {
            pDialo = ProgressDialog(this@Complaint);
            pDialo.setMessage("Preparing to send...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            //val rootView = window.decorView.findViewById<View>(android.R.id.content)
            val v1 = window.decorView.rootView


            try {
                takeScreenshot()
            }
            catch(e:Exception){

            }
        }


    }

    private fun takeScreenshot() {
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"

            // create bitmap screen capture
            val v1 = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false

            val imageFile = File(mPath)

            println("insidetry"+"try")
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            shareImage(imageFile)
        } catch (e: Throwable) {
            println("insidecatch"+e.toString())

            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }

    }



    private fun shareImage(file: File) {
        val uri = Uri.fromFile(file)
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        pDialo.dismiss()

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "")
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "No App Available", Toast.LENGTH_SHORT).show()
        }

    }
    fun CheckingPermissionIsEnabledOrNot(): Boolean {

        val INTERNET = ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
        val ACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE)
        //val ACCESS_NOTIFICATION_POLICY = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NOTIFICATION_POLICY)
        val READ_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val WRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val CAMERA = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        //val CALL_PHONE = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)

        return INTERNET == PackageManager.PERMISSION_GRANTED &&
                ACCESS_NETWORK_STATE == PackageManager.PERMISSION_GRANTED &&
                //ACCESS_NOTIFICATION_POLICY == PackageManager.PERMISSION_GRANTED &&
                READ_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                WRITE_EXTERNAL_STORAGE == PackageManager.PERMISSION_GRANTED &&
                CAMERA == PackageManager.PERMISSION_GRANTED //&&
        //CALL_PHONE == PackageManager.PERMISSION_GRANTED
    }
    fun RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(this, arrayOf<String>
        (android.Manifest.permission.INTERNET,

                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE

                //android.Manifest.permission.CALL_PHONE
        ), 0)


    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

           0 ->

                if (grantResults.size > 0) {

                    val INTERNET = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val ACCESS_NETWORK_STATE = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    //val ACCESS_NOTIFICATION_POLICY = grantResults[2] == PackageManager.PERMISSION_GRANTED
                    val READ_EXTERNAL_STORAGE = grantResults[2] == PackageManager.PERMISSION_GRANTED

                    //val CALL_PHONE = grantResults[4] == PackageManager.PERMISSION_GRANTED

                    if (INTERNET && ACCESS_NETWORK_STATE /*&& ACCESS_NOTIFICATION_POLICY*/ && READ_EXTERNAL_STORAGE) {
                        //if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                        /*val mLocationManager =  getSystemService(LOCATION_SERVICE) as (LocationManager);
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, this);*/
                        /* locationmanager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                         locationmanager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5f, activity)*/

                        // }
                        //Toast.makeText(this@MainFirstActivity, "Permission Granted", Toast.LENGTH_LONG).show()
                    } else {
                        //Toast.makeText(this@MainFirstActivity, "Permission Denied", Toast.LENGTH_LONG).show()
                        RequestMultiplePermission()
                    }
                }

        }
    }
}
