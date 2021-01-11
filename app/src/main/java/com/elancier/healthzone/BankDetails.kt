package com.elancier.healthzone

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.cloudinary.Cloudinary
import com.elancier.healthzone.Adapter.SpinnerAdapter
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.CommonFunctions
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_bank_details.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class BankDetails : AppCompatActivity() {
    var mr_text =
        arrayOf("Select Account Type", "Savings", "Current")
    var acc_type_spin: Spinner? = null
    var acc_ype_adap: SpinnerAdapter? = null
    var bank_name: EditText? = null
    var branch_name: EditText? = null
    var acc_no: EditText? = null
    var acc_type: EditText? = null
    var ifsc: EditText? = null
    var micr: EditText? = null
    var save: TextView? = null
    var acc_type_error: TextView? = null
    var my_bank: TextView? = null
    var profile_img: ImageView? = null
    lateinit var utils: Utils
    var retry: Dialog? = null
    var menuvalue = 0
    var retrybut: ImageView? = null
    var drawerLayout: DrawerLayout? = null
    var drawer_layout: LinearLayout? = null
    var profile_lay: LinearLayout? = null
    var my_bank_lay: LinearLayout? = null
    var diff_lay: LinearLayout? = null
    var MobilePattern = "[0-9]{10}"
    val NamePattern = "[a-zA-Z]"
    var RelationPattern = "[a-zA-Z -]+"
    val activity=this
    val RequestPermissionCode = 7
    internal lateinit var byteArray:ByteArray
    var imagecode=""
    internal lateinit var fi: File
    lateinit var pDialo : ProgressDialog
    var cloud_name=""
    var api_key=""
    var api_secret=""
    var panimage=""
    var bankimage=""

    internal lateinit var byteArray1: ByteArray
    var imagecode1=""
    internal lateinit var fi1: File
    var progbar: Dialog? = null
    var prog:android.app.Dialog? = null

    //String email1 = email.getText().toString().trim();
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)
        utils = Utils(this)
        init()
        acc_type_spin!!.isEnabled = false
        onclick()
       loadprogress()
        retry = Dialog(this)
        retry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        retry!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        val v = layoutInflater.inflate(R.layout.retrylay, null)
        retrybut = v.findViewById<View>(R.id.retry) as ImageView
        retry!!.setContentView(v)
        retry!!.setCancelable(false)
        //startprogress()

            GetInfoTask().execute()
                //stateload().execute()

        retrybut!!.setOnClickListener {
            retry!!.dismiss()

            val task =
                GetInfoTask()
            task.execute()
        }

        panimg.setOnClickListener {

            if(CheckingPermissionIsEnabledOrNot(this)&&(panimage.isEmpty()||panimage=="null")){

                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.type = "image/*"
                startActivityForResult(i, 1)

            }
            else{
                RequestMultiplePermission(activity!!)
            }

        }

        bankimg.setOnClickListener {

            if(CheckingPermissionIsEnabledOrNot(this)&&(bankimage.isEmpty()||bankimage=="null")){

                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.type = "image/*"
                startActivityForResult(i, 2)

            }
            else{
                RequestMultiplePermission(activity!!)
            }

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


    private fun init() {
        //drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        //drawer_layout = findViewById<View>(R.id.drawer_layout) as LinearLayout
        //profile_lay = findViewById<View>(R.id.profile_lay) as LinearLayout
        //my_bank_lay = findViewById<View>(R.id.my_bank_lay) as LinearLayout
        diff_lay = findViewById<View>(R.id.diff_lay) as LinearLayout
       // MainView().setclick(drawer_layout, drawerLayout, this@BankDetails)
        acc_type_spin = findViewById<View>(R.id.acc_type_spin) as Spinner
        acc_ype_adap = SpinnerAdapter(
            this@BankDetails,
            R.layout.spinner_list_item,
            mr_text
        )
        acc_type_spin!!.adapter = acc_ype_adap
        bank_name = findViewById<View>(R.id.bank_name) as EditText
        branch_name = findViewById<View>(R.id.branch_name) as EditText
        acc_no = findViewById<View>(R.id.acc_num) as EditText
        //acc_type = findViewById<View>(R.id.acc_type) as EditText
        ifsc = findViewById<View>(R.id.ifsc) as EditText
        micr = findViewById<View>(R.id.micr) as EditText
        acc_type_error = findViewById<View>(R.id.acc_type_error) as TextView
        save = findViewById<View>(R.id.save) as TextView
//        profile_img = findViewById<View>(R.id.profile_img) as ImageView
        //MainView().profile_lay.setVisibility(View.VISIBLE)
        //MainView().profile_img.setImageResource(R.mipmap.up_arrow)
        //MainView().my_bank_lay.setBackgroundColor(resources.getColor(R.color.eee))
        /* my_bank = (TextView) findViewById(R.id.my_bank);
        my_bank.setBackgroundColor(0xFFFFFFFF);*/
        // Toast.makeText(BankDetails.this, ""+acc_type_spin.getSelectedItem(), Toast.LENGTH_SHORT).show();
       /* val mDrawerToggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close
            ) {
                *//** Called when a drawer has settled in a completely open state.  *//*
                override fun onDrawerOpened(drawerView: View) {
                    menuvalue = 1
                    supportActionBar!!.setHomeAsUpIndicator(R.mipmap.back_arrow)
                }

                *//** Called when a drawer has settled in a completely closed state.  *//*
                override fun onDrawerClosed(view: View) {
                    menuvalue = 0
                    supportActionBar!!.setHomeAsUpIndicator(R.mipmap.menu)
                }
            }
        mDrawerToggle.isDrawerIndicatorEnabled = false*/
//        MainView().drawerLayout.setDrawerListener(mDrawerToggle)
    }

    private fun onclick() {
        save!!.setOnClickListener(View.OnClickListener {
            if (bank_name!!.text.toString().length <= 0) {
                bank_name!!.error = "Enter Bankname"
            }

            if (branch_name!!.text.toString().length <= 0) {
                branch_name!!.error = "Enter Branchname"
            }

            if (acc_no!!.text.toString().length <= 0) {
                acc_no!!.error = "Enter Account Number"
            }
            if (ifsc!!.text.toString().length <= 0) {
                ifsc!!.error = "Enter IFSC"
            }
            if (micr!!.text.toString().length <= 0) {
                micr!!.error = "Enter MICR"
            }
            if (acc_type_spin!!.selectedItemPosition == 0) {
                acc_type_spin!!.setBackgroundResource(R.drawable.red_stroke_bg)
                acc_type_error!!.visibility = View.VISIBLE
                //  Toast.makeText(BankDetails.this, "Please Select Your ID", Toast.LENGTH_SHORT).show();
            }

            else {
                acc_type_spin!!.setBackgroundResource(R.drawable.grey_stroke_bg)
                acc_type_error!!.visibility = View.GONE
            }
            if (bank_name!!.text.toString()
                    .trim { it <= ' ' }.length > 0 && branch_name!!.text.toString()
                    .trim { it <= ' ' }.length > 0
                 && ifsc!!.text.toString()
                    .trim { it <= ' ' }.length > 0 && micr!!.text.toString()
                    .trim { it <= ' ' }.length > 0
                && acc_no!!.text.toString()
                    .trim { it <= ' ' }.length > 0 && (acc_type_spin!!.selectedItemPosition == 1 || acc_type_spin!!.selectedItemPosition == 2)
            ) {

                if(imagecode.isNotEmpty()||imagecode1.isNotEmpty()){
                    val task = UpdateInfoTask()
                    task.execute(
                        bank_name!!.text.toString().trim{ it <= ' ' },
                        branch_name!!.text.toString().trim{ it <= ' ' },
                        ifsc!!.text.toString().trim{ it <= ' ' },
                        mr_text[acc_type_spin!!.selectedItemPosition],
                        micr!!.text.toString().trim { it <= ' ' },
                        acc_no!!.text.toString().trim { it <= ' ' },
                        "data:image/png;base64"+","+imagecode,"data:image/png;base64"+","+imagecode1
                    )
                }
                else{
                    val task = UpdateInfoTask()
                    task.execute(
                        bank_name!!.text.toString().trim{ it <= ' ' },
                        branch_name!!.text.toString().trim{ it <= ' ' },
                        ifsc!!.text.toString().trim{ it <= ' ' },
                        mr_text[acc_type_spin!!.selectedItemPosition],
                        micr!!.text.toString().trim { it <= ' ' },
                        acc_no!!.text.toString().trim { it <= ' ' },
                        panimage,bankimage
                    )
                }

            } else {
                Toast.makeText(
                    this@BankDetails,
                    "Please Fill Above Details",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onBackPressed() {

            super.onBackPressed()

    }

    override fun onCreateOptionsMenu(menu:Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_bank, menu)
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
        return if (id == R.id.action_settings) {

            //EnableTrue();
            //item.setVisible(false);
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun EnableTrue() {
        diff_lay!!.visibility = View.GONE

        if(bank_name!!.text.toString().isEmpty()){
            bank_name!!.isEnabled = true
            bank_name!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }
        if(branch_name!!.text.toString().isEmpty()){
            branch_name!!.isEnabled = true
            branch_name!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }
        if(acc_type_spin!!.selectedItemPosition==0){
            acc_type_spin!!.isEnabled = true
            acc_type_spin!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }
        if(acc_no!!.text.toString().isEmpty()){
            acc_no!!.isEnabled = true
            acc_no!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }
        if(ifsc!!.text.toString().isEmpty()){
            ifsc!!.isEnabled = true
            ifsc!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }
        if(micr!!.text.toString().isEmpty()){
            micr!!.isEnabled = true
            micr!!.setBackgroundResource(R.drawable.grey_stroke_bg)
            save!!.setVisibility(View.VISIBLE)

        }

        if(panimage.isEmpty()||panimage=="null"){
            panimg.setImageResource(R.mipmap.placeholders)
            save!!.setVisibility(View.VISIBLE)

        }
        if(bankimage.isEmpty()||bankimage=="null"){
            bankimg.setImageResource(R.mipmap.placeholders)
            save!!.setVisibility(View.VISIBLE)

        }

    }

    inner class GetInfoTask :
        AsyncTask<String?,Void?,String?>() {
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
                jobj.put("type", "fetch")
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

        override fun onPostExecute(resp: String?) {
            Log.i("tabresp", resp + "")
            progbar!!.dismiss()
            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        val jobj = jarr.getJSONObject(0)
                        bank_name!!.setText(if (jobj.isNull("bankname")) "" else jobj.getString("bankname") + "")
                        branch_name!!.setText(if (jobj.isNull("branch")) "" else jobj.getString("branch") + "")
                        ifsc!!.setText(if (jobj.isNull("ifsccode")) "" else jobj.getString("ifsccode") + "")
                        //acc_type.setText(jobj.getString("acctype")+"");
                        micr!!.setText(if (jobj.isNull("panno")) "" else jobj.getString("panno") + "")
                        acc_no!!.setText(if (jobj.isNull("bankaccountno")) "" else jobj.getString("bankaccountno") + "")
                        panimage=jobj.getString("pan_image")
                        bankimage=jobj.getString("bank_image")

                        var pan_verify=jobj.getString("pan_verify")
                        var bank_verify=jobj.getString("bank_verify")

                        if(pan_verify.isNotEmpty()&&pan_verify=="1"){
                            imageView26.visibility=View.VISIBLE
                            imageView26.setImageResource(R.mipmap.verify)
                        }
                        else if(pan_verify=="0"){
                            imageView26.visibility=View.VISIBLE
                            imageView26.setImageResource(R.mipmap.notverified)
                        }
                        if(bank_verify.isNotEmpty()&&bank_verify=="1"){
                            imageView27.visibility=View.VISIBLE
                            imageView27.setImageResource(R.mipmap.verify)
                        }
                        else if(bank_verify=="0"){
                            imageView27.visibility=View.VISIBLE
                            imageView27.setImageResource(R.mipmap.notverified)
                        }

                        if(panimage.isNotEmpty()){
                            Picasso.with(this@BankDetails).load(panimage).placeholder(R.mipmap.loading).into(panimg)

                            if(jobj.getString("bank_date").isNotEmpty()&&jobj.getString("bank_date")!="null") {
                                textView73.setText("Uploaded on : "+jobj.getString("bank_date"))
                                //textView77.setText("Uploaded on : "+jobj.getString("bank_date"))
                            }
                            else{

                            }


                        }
                        if(bankimage.isNotEmpty()){
                           // Picasso.with(this@BankDetails).load(panimage).placeholder(R.mipmap.loading).into(panimg)
                            Picasso.with(this@BankDetails).load(bankimage).placeholder(R.mipmap.loading).into(bankimg)
                            if(jobj.getString("bank_date").isNotEmpty()&&jobj.getString("bank_date")!="null") {
                                //textView73.setText("Uploaded on : "+jobj.getString("bank_date"))
                                textView77.setText("Uploaded on : "+jobj.getString("bank_date"))
                            }
                            else{

                            }



                        }



                        for (i in mr_text.indices) {
                            if (mr_text[i]
                                    .equals(jobj.getString("acctype"), ignoreCase = true)
                            ) {
                                acc_type_spin!!.setSelection(i)
                            }
                        }
                        EnableTrue()
                    }
                } else {
                    retry!!.show()
                    //println("errorneet"+e.toString())

                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();
                }
            } catch (e: Exception) {
                retry!!.show()
                e.printStackTrace()
                println("errorneet"+e.toString())
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }

     inner class UpdateInfoTask :
        AsyncTask<String?, Void?, String?>() {
         internal lateinit var pDialo : ProgressDialog

         override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            pDialo = ProgressDialog(activity!!);
            pDialo.setMessage("Saving...");
            pDialo.setIndeterminate(true);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(true);
            pDialo.show()
            Log.i("UpdateInfoTask", "started")
        }

        override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                jobj.put("bankname", params[0])
                jobj.put("branch", params[1])
                jobj.put("ifsccode", params[2])
                jobj.put("acctype", params[3])
                jobj.put("panno", params[4])
                jobj.put("bankaccountno", params[5])
                jobj.put("pan_image", params[6])
                jobj.put("bank_image", params[7])
                jobj.put("uname", utils.loadName())
               /// jobj.put("sign", utils.loadSign())
                jobj.put("type", "update")
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

        override fun onPostExecute(resp: String?) {
            Log.i("tabresp", resp + "")
            pDialo!!.dismiss()

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
                        startActivity(Intent(this@BankDetails, HomePage::class.java))
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
                pDialo.dismiss()
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    inner class Get : AsyncTask<String, Void, String>() {
        internal lateinit var pDialo : ProgressDialog
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);

            pDialo = ProgressDialog(activity!!);
            pDialo.setMessage("Saving...");
            pDialo.setIndeterminate(true);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(true);
            pDialo.show()

            Log.i("LoginTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String = ""
            val config = HashMap<Any,Any>();
            config.put("cloud_name", cloud_name);
            config.put("api_key", api_key);
            config.put("api_secret",api_secret);
            val cloudinary = Cloudinary(config);

            try {
                val config = HashMap<Any,Any>();
                config.put("cloud_name", cloud_name);
                config.put("api_key", api_key);
                config.put("api_secret", api_secret);
                val cloudinary = Cloudinary(config);
                var k=""
                var k1=""
                try {

                    if(imagecode.isNotEmpty()){
                        val fi=cloudinary.uploader().upload("data:image/png;base64"+","+imagecode,HashMap<Any,Any>());
                        k=fi.get("url").toString()

                    }
                    if(imagecode1.isNotEmpty()){
                        val fis=cloudinary.uploader().upload("data:image/png;base64"+","+imagecode1,HashMap<Any,Any>());
                        k1=fis.get("url").toString()
                    }


                    pDialo.dismiss()

                    runOnUiThread {
                        //LoginTask().execute(k)

                    }


                   // Log.e("fival",k.toString());

                } catch (e:IOException) {
                    e.printStackTrace();
                }

            } catch (e:IOException ) {
                e.printStackTrace();
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            pDialo.dismiss()
            // familyimg.setText("ADD IMAGES " + familyimgarr.size + " / " + 5)
            ////pDialo.dismiss()
            //Toast.makeText(activity,"Uploaded Successfully",Toast.LENGTH_SHORT).show()
            //Toast.makeText(activity,"Uploaded Successfully",Toast.LENGTH_SHORT).show()


        }
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@BankDetails);
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
                //e.printStackTrace()
                Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
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

                        panimg.setImageBitmap(resizeBitmap)
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
                        val resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                        panimg.setImageBitmap(resizeBitmap)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
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
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                        byteArray = stream.toByteArray()

                        imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)

                        saveImage(thumbnail)

                        //imglin.visibility = View.VISIBLE
                        panimg.setImageBitmap(thumbnail)
                        //upload.setText("Retake")
                    }

                }
                else if (requestCode ==2) {
                    if (resultCode == Activity.RESULT_OK && null != data) {
                        var picturePath: String? = null
                        var selectedImage = data!!.data
                        picturePath = getImgPath(selectedImage)
                        fi1 = File(picturePath!!)
                        val yourSelectedImage = CommonFunctions.decodeFile1(picturePath, 400, 200)
                        //Log.i("original path1", picturePath + "")
                        Log.i(
                            "pathsizeeeeee",
                            (fi1.length() / 1024).toString() + "      " + yourSelectedImage
                        )
                        //val image1 = CommonFunctions.decodeFile1(picturePath, 0, 0)
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity!!.getBaseContext().getContentResolver(),
                            selectedImage
                        )

                        val resizeBitmap =
                            resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

                        bankimg.setImageBitmap(resizeBitmap)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                        byteArray1 = stream.toByteArray()
                        imagecode1 = Base64.encodeToString(byteArray1, Base64.DEFAULT)
                        Log.e("imagecode", imagecode1)

                        val path = getImgPath(selectedImage!!)
                        //choose_files.setText("Remove Image")
                        if (path != null) {
                            val f = File(path!!)

                        }

                    } else {

                    }
                } else if (requestCode == 103) {


                    try {

                        var selectedImageUri = data!!.data
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            activity!!.getBaseContext().getContentResolver(),
                            selectedImageUri
                        )
                        val resizeBitmap = resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
                        bankimg.setImageBitmap(resizeBitmap)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream)
                        byteArray1 = stream.toByteArray()
                        imagecode1 = Base64.encodeToString(byteArray1, Base64.DEFAULT)

                        val path = getImgPath(selectedImageUri!!)
                        //getBase64FromPath(path);
                        if (path != null) {
                            val f = File(path!!)

                            selectedImageUri = Uri.fromFile(f)

                        }


                    } catch (e: Exception) {
                        val thumbnail = data!!.extras!!.get("data") as Bitmap?
                        val stream = ByteArrayOutputStream()
                        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                        byteArray1 = stream.toByteArray()

                        imagecode1 = Base64.encodeToString(byteArray1, Base64.DEFAULT)

                        saveImage(thumbnail)

                        //imglin.visibility = View.VISIBLE
                        bankimg.setImageBitmap(thumbnail)
                        //upload.setText("Retake")
                    }




                }
            }




        }
        catch (e:Exception){
        }
    }


    fun CheckingPermissionIsEnabledOrNot(context:Activity):Boolean {
        val ACCESS_NETWORK_STATEt = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val ACCESS_COARSE_LOCATION = ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return ACCESS_NETWORK_STATEt == PackageManager.PERMISSION_GRANTED &&
                ACCESS_COARSE_LOCATION == PackageManager.PERMISSION_GRANTED
    }

    fun getImgPath(uri: Uri?): String? {
        val result: String?
        val cursor = activity!!.contentResolver.query(uri!!, null, null, null, null)
        if (cursor == null) {
            result = uri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result

    }
    fun saveImage(myBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            Environment.getExternalStorageDirectory().toString() + "IMAGE_DIRECTORY"
        )

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

    fun toast(msg:String){
        val toa=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toa.setGravity(Gravity.CENTER,0,0)
        toa.show()
    }

    private fun RequestMultiplePermission(context:Activity) {
        ActivityCompat.requestPermissions(context,arrayOf<String>(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ), RequestPermissionCode
        )

    }

    public fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 102)
            } else if (options[item] == "Choose from Gallery") {
                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.type = "image/*"
                startActivityForResult(i, 1)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    public fun selectImage1() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 103)
            } else if (options[item] == "Choose from Gallery") {
                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.type = "image/*"
                startActivityForResult(i, 2)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }
}