package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Html
import android.util.AttributeSet
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.cloudinary.Cloudinary
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CommonFunctions
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_termsconditions.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Termsconditions : AppCompatActivity() {
    lateinit var utils: Utils
    var progbar: Dialog? = null
    private var canvasLL: ImageView? = null
    var file: File? = null
    private var mSignature: signature? = null
    private var views: View? = null
    private var bitmap: Bitmap? = null
    private var btnSave: Button? = null
    private var clear: Button? = null
    val RequestPermissionCode = 7
    internal var Res_OTP = ""
    internal lateinit var byteArray: ByteArray
    var imagecode=""
    internal lateinit var fi: File
    val activity=this
    lateinit var pDialo : ProgressDialog
    var cloud_name=""
    var api_key=""
    var crore=""
    var crorepin=""
    var api_secret=""
    private val STROKE_WIDTH = 5f
    private val HALF_STROKE_WIDTH = STROKE_WIDTH / 2
    internal var DIRECTORY = Environment.getExternalStorageDirectory().path + "/Signature/"
    internal var pic_name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    internal var StoredPath = "$DIRECTORY$pic_name.png"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termsconditions)

        supportActionBar!!.title = "V3 Online TV"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        utils = Utils(this)
       //actionBar.setTitle("Terms and conditions")

        if(CheckingPermissionIsEnabledOrNot(this)){

        }
        else{
            RequestMultiplePermission(this)
        }

        mSignature = signature(applicationContext, null)
        mSignature!!.setBackgroundColor(Color.WHITE)
        // Dynamically generating Layout through java code


        try{
            var frm=intent.extras
            crore=frm!!.getString("term").toString();
            println("term"+crore)

        }
        catch (e:Exception){
            println("err"+e.toString())

        }


        button13.setOnClickListener {

            if(crore=="crore"||crore=="welcome"){
                Pincheck().execute()
            }

            else {
                if (Res_OTP == otptext.text.toString()) {
                    val dialogBuilder = AlertDialog.Builder(this)
                    // ...Irrelevant code for customizing the buttons and title
                    val inflater = this.layoutInflater
                    val dialogView = inflater.inflate(R.layout.sign_popup, null)
                    dialogBuilder.setView(dialogView)
                    dialogBuilder.setCancelable(false)
                    canvasLL = dialogView.findViewById<View>(R.id.sign) as ImageView
                    btnSave = dialogView.findViewById<View>(R.id.button11) as Button
                    clear = dialogView.findViewById<View>(R.id.button12) as Button
                    mSignature = signature(applicationContext, null)
                    mSignature!!.setBackgroundColor(Color.WHITE)
                    val textView71 = dialogView.findViewById<TextView>(R.id.textView71)
                    // Dynamically generating Layout through java code
                    // canvasLL!!.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    val alertDialog = dialogBuilder.create()
                    alertDialog.show()
                    //views = canvasLL

                    canvasLL!!.setOnClickListener {

                        if (CheckingPermissionIsEnabledOrNot(this)) {
                            val i = Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            i.type = "image/*"
                            startActivityForResult(i, 1)
                        } else {
                            RequestMultiplePermission(this)
                        }
                    }

                    textView71.setOnClickListener {
                        alertDialog.dismiss()
                    }

                    clear!!.setOnClickListener {
                        alertDialog.show()
                        val dialogBuilder = AlertDialog.Builder(this)
                        // ...Irrelevant code for customizing the buttons and title
                        val inflater = this.layoutInflater
                        val dialogView = inflater.inflate(R.layout.sign_popup, null)
                        dialogBuilder.setView(dialogView)
                        dialogBuilder.setCancelable(false)
                        canvasLL = dialogView.findViewById<View>(R.id.sign) as ImageView
                        btnSave = dialogView.findViewById<View>(R.id.button11) as Button
                        clear = dialogView.findViewById<View>(R.id.button12) as Button
                        mSignature = signature(applicationContext, null)
                        mSignature!!.setBackgroundColor(Color.WHITE)
                        val textView71 = dialogView.findViewById<TextView>(R.id.textView71)

                        val alertDialog = dialogBuilder.create()
                        alertDialog.show()
                        imagecode = "";

                        canvasLL!!.setOnClickListener {
                            if (CheckingPermissionIsEnabledOrNot(this)) {
                                val i = Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                i.type = "image/*"
                                startActivityForResult(i, 1)
                            } else {
                                RequestMultiplePermission(this)

                            }
                        }

                        textView71.setOnClickListener {
                            alertDialog.dismiss()
                        }

                        btnSave!!.setOnClickListener { view ->

                            if (NetUtils.getConnectivityStatus(this) != 0) {

                                if (imagecode.isNotEmpty()) {
                                    //Get().execute()
                                    UpdateInfoTask().execute("data:image/png;base64" + "," + imagecode)

                                    toast("Image saved")
                                } else {
                                    toast("Please capture sign image")
                                }

                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "You're offline",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            //Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }
                    }

                    btnSave!!.setOnClickListener { view ->

                        if (NetUtils.getConnectivityStatus(this) != 0) {
                            if (imagecode.isNotEmpty()) {
                                // Get().execute()
                                UpdateInfoTask().execute("data:image/png;base64" + "," + imagecode)

                                toast("Image saved")
                            } else {
                                toast("Please capture sign image")
                            }

                        } else {
                            Toast.makeText(applicationContext, "You're offline", Toast.LENGTH_LONG)
                                .show()
                        }
                        //Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
                        alertDialog.dismiss()
                    }
                } else {
                    toast("Enter correct V3 Pin")

                }
            }
        }



        button11.setOnClickListener {
            textView72.visibility=View.VISIBLE
            progressBar5.visibility=View.INVISIBLE
            if(crore=="crore") {
                textView72.setText("")
                otptext.setHint("Enter Your Pin")
            }
            else{
                textView72.setText("")
                otptext.setHint("Enter Your V3 Pin")
            }
            otptext.visibility=View.VISIBLE
            button13.visibility=View.VISIBLE
            Res_OTP = utils.loadmpin()
            scr.post {
                scr.fullScroll(View.FOCUS_DOWN)
            }

        }
        file = File(DIRECTORY)
        if (!file!!.exists()) {
            file!!.mkdir()
        }
        loadprogress()
        GetInfoTask().execute()
        //stateload().execute()

    }

    fun toast(msg:String){
        val toast=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    inner class Get : AsyncTask<String, Void, String>() {
        internal lateinit var pDialo: ProgressDialog
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            runOnUiThread {

                pDialo = ProgressDialog(this@Termsconditions);
                pDialo.setMessage("Uploading....");
                pDialo.setIndeterminate(false);
                pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialo.setCancelable(false);
                //pDialo.setMax(3)
                pDialo.show()
            }
            Log.i("LoginTask", "started")
        }

        override fun doInBackground(vararg param: String):String? {
            var result: String = ""
            val config = HashMap<Any, Any>();
            config.put("cloud_name", cloud_name);
            config.put("api_key", api_key);
            config.put("api_secret", api_secret);
            val cloudinary = Cloudinary(config);
            try {
                val fi = cloudinary.uploader().upload("data:image/png;base64"+","+imagecode, HashMap<Any, Any>());
                val k = fi.get("url")
                Log.e("fival", k.toString());
                utils.savePreferences("sign",k.toString())

                runOnUiThread{
                    pDialo.dismiss()
                }

                finish()


            } catch (e: IOException) {
                e.printStackTrace();
                Log.e("except",e.toString())
            }

            return result
        }

        override fun onPostExecute(resp: String?) {

        }
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


    inner class Pincheck :
        AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started")
        }

        override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                crorepin=otptext.text.toString().trim()
                jobj.put("pin", otptext.text.toString().trim())
                if(crore=="welcome"){
                    jobj.put("type", "welpincheck")

                }
                else{
                    jobj.put("type", "pincheck")

                }
                jobj.put("uname", utils.loadName())

                Log.i(
                    "check Input",
                    Appconstants.pincheck + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.pincheck, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("tabresp", resp + "")
            progbar!!.dismiss()
            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    if (json.getJSONObject(0).getString("Status") == "Success") {
                        val dialogBuilder = AlertDialog.Builder(this@Termsconditions)
                        // ...Irrelevant code for customizing the buttons and title
                        val inflater = this@Termsconditions.layoutInflater
                        val dialogView = inflater.inflate(R.layout.sign_popup, null)
                        dialogBuilder.setView(dialogView)
                        dialogBuilder.setCancelable(false)
                        canvasLL = dialogView.findViewById<View>(R.id.sign) as ImageView
                        btnSave = dialogView.findViewById<View>(R.id.button11) as Button
                        clear = dialogView.findViewById<View>(R.id.button12) as Button
                        mSignature = signature(applicationContext, null)
                        mSignature!!.setBackgroundColor(Color.WHITE)
                        val textView71 = dialogView.findViewById<TextView>(R.id.textView71)
                        // Dynamically generating Layout through java code
                        // canvasLL!!.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        val alertDialog = dialogBuilder.create()
                        alertDialog.show()
                        //views = canvasLL

                        canvasLL!!.setOnClickListener {

                            if (CheckingPermissionIsEnabledOrNot(this@Termsconditions)) {
                                val i = Intent(
                                    Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                i.type = "image/*"
                                startActivityForResult(i, 1)
                            } else {
                                RequestMultiplePermission(this@Termsconditions)
                            }
                        }

                        textView71.setOnClickListener {
                            alertDialog.dismiss()
                        }

                        clear!!.setOnClickListener {
                            alertDialog.show()
                            val dialogBuilder = AlertDialog.Builder(this@Termsconditions)
                            // ...Irrelevant code for customizing the buttons and title
                            val inflater = this@Termsconditions.layoutInflater
                            val dialogView = inflater.inflate(R.layout.sign_popup, null)
                            dialogBuilder.setView(dialogView)
                            dialogBuilder.setCancelable(false)
                            canvasLL = dialogView.findViewById<View>(R.id.sign) as ImageView
                            btnSave = dialogView.findViewById<View>(R.id.button11) as Button
                            clear = dialogView.findViewById<View>(R.id.button12) as Button
                            mSignature = signature(applicationContext, null)
                            mSignature!!.setBackgroundColor(Color.WHITE)
                            val textView71 = dialogView.findViewById<TextView>(R.id.textView71)

                            // Dynamically generating Layout through java code
                            //canvasLL!!.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                            val alertDialog = dialogBuilder.create()
                            alertDialog.show()
                            imagecode = "";

                            canvasLL!!.setOnClickListener {
                                if (CheckingPermissionIsEnabledOrNot(this@Termsconditions)) {
                                    val i = Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    )
                                    i.type = "image/*"
                                    startActivityForResult(i, 1)
                                } else {
                                    RequestMultiplePermission(this@Termsconditions)

                                }
                            }

                            textView71.setOnClickListener {
                                alertDialog.dismiss()
                            }

                            btnSave!!.setOnClickListener { view ->

                                if (NetUtils.getConnectivityStatus(this@Termsconditions) != 0) {

                                    if (imagecode.isNotEmpty()) {
                                        //Get().execute()
                                        UpdateInfoTask().execute("data:image/png;base64" + "," + imagecode)

                                        toast("Image saved")
                                    } else {
                                        toast("Please capture sign image")
                                    }

                                } else {
                                    Toast.makeText(
                                        applicationContext,
                                        "You're offline",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                //Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
                                alertDialog.dismiss()
                            }
                        }

                        btnSave!!.setOnClickListener { view ->

                            if (NetUtils.getConnectivityStatus(this@Termsconditions) != 0) {
                                if (imagecode.isNotEmpty()) {
                                    // Get().execute()
                                    UpdateInfoTask().execute("data:image/png;base64" + "," + imagecode)

                                    toast("Image saved")
                                } else {
                                    toast("Please capture sign image")
                                }

                            } else {
                                Toast.makeText(applicationContext, "You're offline", Toast.LENGTH_LONG)
                                    .show()
                            }
                            //Toast.makeText(applicationContext, "Successfully Saved", Toast.LENGTH_SHORT).show()
                            alertDialog.dismiss()
                        }

                    }
                    else{
                        toast("Invalid Pin")
                    }
                }
                else{
                    toast("Something went wrong")
                }
            } catch (e:Exception) {
                toast("Something went wrong")
            }
        }
    }

    inner class GetInfoTask :
        AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started")
        }

        override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()

                jobj.put("uname", utils.loadName())
                Log.e("pinName",utils.pinName().toString())

                if(crore=="crore"){
                    jobj.put("type","Base Pin")
                }
                else if(crore=="welcome"){
                    jobj.put("type","Welcome Plan")

                }
                else{
                    jobj.put("type", "")
                }

                Log.i(
                    "check Input",
                    Appconstants.terms + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.terms, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("tabresp", resp + "")
            progbar!!.dismiss()
            try {
                if (resp != null) {
                    val json = JSONObject(resp)
                    if (json.getString("Status") == "Success") {
                        val jarr = json.getJSONObject("Response")
                        val terms=jarr.getString("terms")
                        val terms1=jarr.getString("terms1")

                        textView70.setText(Html.fromHtml("<p style=\"color:red\">"+terms1+"\n\n"+jarr.getString("text")))
                        val sign=jarr.getString("sign")

                        val signdt=jarr.getString("sign_date")
                        var pin=""
                        try {
                             pin = jarr.getString("pin")
                        }
                        catch (e:Exception){

                        }
                        //val profile_verify=jarr.getString("profile_verify")

                        if(signdt.isEmpty()||signdt=="null") {
                            textView78.visibility=View.GONE
                        }
                        else{
                            textView78.visibility=View.VISIBLE

                            if(crore=="crore") {
                                textView78.setText("Uploaded on : " + signdt+"\n"+"Your Pin \t\t\t: "+pin)
                            }
                            else{

                                textView78.setText("Uploaded on : " + signdt)
                            }

                        }

                        Log.e("view_plan",utils.loadplan())
                        if((sign=="null"||sign.isNullOrEmpty())&&utils.loadplan().equals("Welcome Pin")){
                            button11.visibility=View.VISIBLE
                            button11.setText(terms)
                            textView79.visibility=View.VISIBLE

                        }
                        else{
                            button11.visibility=View.INVISIBLE
                            textView79.visibility=View.INVISIBLE
                            imageView23.visibility=View.VISIBLE
                            Picasso.with(this@Termsconditions).load(sign).placeholder(R.mipmap.loading).into(imageView23)

                        }
                    }
                } else {
                    progbar!!.dismiss()
                }
            } catch (e: Exception) {
                progbar!!.dismiss()
                e.printStackTrace()
                Log.e("Errterms",e.toString())
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    inner class UpdateInfoTask :
        AsyncTask<String?, Void?, String?>() {
        internal lateinit var pDialo : ProgressDialog

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            runOnUiThread {
            pDialo = ProgressDialog(this@Termsconditions!!);
            pDialo.setMessage("Saving...");
            pDialo.setIndeterminate(true);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(true);
            pDialo.show()
            }
            Log.i("UpdateInfoTask", "started")
        }

        override fun doInBackground(vararg params:String?):String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "Sign")

                if(crore=="crore"){
                    jobj.put("types", "Crorepati")
                    jobj.put("pinno", crorepin)

                }
                else if(crore=="welcome"){
                    jobj.put("types", "Welcome")
                    jobj.put("pinno", crorepin)

                }
                else {
                    jobj.put("types", "")
                    jobj.put("pinno", "")


                }
                jobj.put("sign", params[0])

                Log.i(
                    "check Input",
                    Appconstants.GET_MY_BANKINFO + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.GET_MY_BANKINFO, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp:String?) {
            //Log.i("tabresp", resp + "")
            runOnUiThread {
            pDialo!!.dismiss()
            }
            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        Toast.makeText(
                            applicationContext,
                            "Updated successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        //startActivity(Intent(this@Termsconditions, HomePage::class.java))
                        finish()
                    } else {
                        pDialo.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "Failed to update.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    pDialo.dismiss()
                    // retry.show();
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // retry.show();
                //Log.e()
                pDialo.dismiss()
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    inner class signature(context: Context, attrs: AttributeSet?) : View(context, attrs) {
        private val paint = Paint()
        private val path = Path()
        private var lastTouchX: Float = 0.toFloat()
        private var lastTouchY: Float = 0.toFloat()
        private val dirtyRect = RectF()

        init {
            paint.isAntiAlias = true
            paint.color = Color.BLACK
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.ROUND
            paint.strokeWidth = STROKE_WIDTH
        }

        @SuppressLint("WrongThread")
        fun save(v: View, StoredPath: String) {
            Log.v("log_tag", "Width: " + v.width)
            Log.v("log_tag", "Height: " + v.height)
            if (bitmap == null) {
                try {
                    bitmap = Bitmap.createBitmap(canvasLL!!.width, canvasLL!!.height, Bitmap.Config.RGB_565)

                } catch (e: Exception) {

                }

            }
            val canvas = Canvas(bitmap!!)
            try {
                // Output the file
                val mFileOutStream = FileOutputStream(StoredPath)
                v.draw(canvas)

                // Convert the output file to Image such as .png
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 30, mFileOutStream)
                mFileOutStream.flush()
                mFileOutStream.close()

            } catch (e: Exception) {
                Log.v("log_tag", e.toString())
            }

        }

        fun clear() {
            path.reset()
            invalidate()

        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawPath(path, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val eventX = event.x
            val eventY = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(eventX, eventY)
                    lastTouchX = eventX
                    lastTouchY = eventY
                    return true
                }

                MotionEvent.ACTION_MOVE,

                MotionEvent.ACTION_UP -> {

                    resetDirtyRect(eventX, eventY)
                    val historySize = event.historySize
                    for (i in 0 until historySize) {
                        val historicalX = event.getHistoricalX(i)
                        val historicalY = event.getHistoricalY(i)
                        expandDirtyRect(historicalX, historicalY)
                        path.lineTo(historicalX, historicalY)
                    }
                    path.lineTo(eventX, eventY)
                }

                else -> {
                    debug("Ignored touch event: $event")
                    return false
                }
            }

            invalidate((dirtyRect.left - HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.top - HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.right + HALF_STROKE_WIDTH).toInt(),
                (dirtyRect.bottom + HALF_STROKE_WIDTH).toInt())

            lastTouchX = eventX
            lastTouchY = eventY

            return true
        }

        private fun debug(string: String) {

            Log.v("log_tag", string)

        }

        private fun expandDirtyRect(historicalX: Float, historicalY: Float) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY
            }
        }

        private fun resetDirtyRect(eventX: Float, eventY: Float) {
            dirtyRect.left = Math.min(lastTouchX, eventX)
            dirtyRect.right = Math.max(lastTouchX, eventX)
            dirtyRect.top = Math.min(lastTouchY, eventY)
            dirtyRect.bottom = Math.max(lastTouchY, eventY)
        }

    }

    fun CheckingPermissionIsEnabledOrNot(context: Activity):Boolean {
        val ACCESS_NETWORK_STATEt = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        return ACCESS_NETWORK_STATEt == PackageManager.PERMISSION_GRANTED &&
                ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED

    }

    private inner class CheckMobileTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {

            textView72.visibility=View.VISIBLE
            progressBar5.visibility=View.VISIBLE
            textView72.setText("Sending OTP to your registered mobile number...")

            scr.post {
                scr.fullScroll(View.FOCUS_DOWN)
            }
            Log.i("CheckMobileTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("mobile", param[0])
                jobj.put("type", "OTP")
                 Log.i("check Input", Appconstants.VIP_API + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.VIP_API, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
           // Log.i("mobresp", resp!! + "")

            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {

                    } else {
                        textView72.visibility=View.GONE
                        progressBar5.visibility=View.GONE
                        Toast.makeText(this@Termsconditions, "OTP not sent.", Toast.LENGTH_LONG).show()
                    }
                } else {

                    textView72.visibility=View.GONE
                    progressBar5.visibility=View.GONE
                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                textView72.visibility=View.GONE
                progressBar5.visibility=View.GONE
                e.printStackTrace()
                Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my_portal, menu)
        return true
    }

    override fun onOptionsItemSelected(item:MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == 1) {
                    if (resultCode == Activity.RESULT_OK && null != data) {
                        var picturePath: String? = null
                        var selectedImage = data!!.data
                        picturePath = getImgPath(selectedImage)
                        fi = File(picturePath!!)

                        // user_img.setImageURI(selectedImage);
                        // Picasso.with(MyInfo.this).load(picturePath).placeholder(R.mipmap.userplaceholder).into(user_img);
                        val yourSelectedImage = CommonFunctions.decodeFile1(picturePath, 400, 200)

                        //Log.i("original path1", picturePath + "")

                        //removeimg.setVisibility(View.VISIBLE);
                        // addimgbut.setVisibility(View.GONE);

                        Log.i(
                            "pathsizeeeeee",
                            (fi.length() / 1024).toString() + "      " + yourSelectedImage
                        )
                        //val image1 = CommonFunctions.decodeFile1(picturePath, 0, 0)
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity!!.getBaseContext().getContentResolver(),
                            selectedImage
                        )

                        val resizeBitmap =
                            resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

                        canvasLL!!.setImageBitmap(resizeBitmap)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                        byteArray = stream.toByteArray()
                        imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                        Log.e("imagecode", imagecode)

                        val path = getImgPath(selectedImage!!)
                        //choose_files.setText("Remove Image")
                        if (path != null) {
                            val f = File(path!!)

                        }

                    } else {

                    }
                } else if (requestCode == 102) {


                    try {

                        var selectedImageUri = data!!.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity!!.getBaseContext().getContentResolver(),
                            selectedImageUri
                        )
                        val resizeBitmap =
                            resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                        canvasLL!!.setImageBitmap(resizeBitmap)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                        byteArray = stream.toByteArray()
                        imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)

                        val path = getImgPath(selectedImageUri!!)
                        //getBase64FromPath(path);
                        if (path != null) {
                            val f = File(path!!)

                            selectedImageUri = Uri.fromFile(f)

                        }


                    } catch (e: Exception) {
                        val thumbnail = data!!.extras!!.get("data") as Bitmap?
                        val stream = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                        byteArray = stream.toByteArray()

                        imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)

                        saveImage(thumbnail)

                        //imglin.visibility = View.VISIBLE
                        canvasLL!!.setImageBitmap(thumbnail)
                        //upload.setText("Retake")
                    }

                }


            }
        }
        catch (e:Exception){
        }
    }

    fun getImgPath(uri: Uri?): String? {
        val result: String?
        val cursor = activity!!.contentResolver.query(uri!!, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        // Log.i("utilsresult", result!! + "")
        return result

    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@Termsconditions);
            pDialo.setMessage("Please wait...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                result = con.connStringResponse(Appconstants.cloudinary)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            // Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")

                        for(i in 0 until jarr.length()){
                            val json=jarr.getJSONObject(i)
                            cloud_name = json.getString("cloud_name")
                            api_key = json.getString("api_key")
                            api_secret = json.getString("api_secret")

                            println("cloud_name"+cloud_name)
                            println("api_key"+api_key)
                            println("api_secret"+api_secret)
                        }
                        pDialo.dismiss()

                    } else {
                        pDialo.dismiss()
                    }
                } else {
                    pDialo.dismiss()
                }
            } catch (e: Exception) {
                pDialo.dismiss()
                e.printStackTrace()
                Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory().toString() + "IMAGE_DIRECTORY"
        )
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }

        try {
            val f = File(
                wallpaperDirectory, Calendar.getInstance()
                    .timeInMillis.toString() + ".jpg"
            )
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(
                activity!!,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null
            )
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
    private fun resize(image: Bitmap, maxWidth:Int, maxHeight:Int): Bitmap {
        if (maxHeight > 0 && maxWidth > 0)
        {
            val width = image.getWidth()
            val height = image.getHeight()
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap)
            {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            }
            else
            {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            val images = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
            return images
        }
        else
        {
            return image
        }
    }


    private fun RequestMultiplePermission(context:Activity) {
        ActivityCompat.requestPermissions(context,arrayOf<String>(

            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE

        ), RequestPermissionCode
        )

    }
}