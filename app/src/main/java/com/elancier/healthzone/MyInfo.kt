package com.elancier.healthzone

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore

import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.elancier.healthzone.Common.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_info.*
import kotlinx.android.synthetic.main.personal_details_list_items.*
import org.jetbrains.annotations.Nullable

import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream

import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.ArrayList

class MyInfo : MainView() {
    val REQUEST_IMAGE = 100;
    lateinit var pDialo : ProgressDialog
    var cloud_name=""
    var api_key=""
    var api_secret=""
    internal lateinit var picker: Dialog
    internal lateinit var datep: DatePicker
    internal lateinit var date: String
    internal lateinit var mobilenum: String
    internal var whatsnum: String = ""
    internal var cdate: String? = null
    internal var time: String? = null
    internal var date1: String? = null
    internal var time1: String? = null
    internal var hour: Int? = null
    internal var minute: Int? = null
    internal var month: Int? = null
    internal var day: Int? = null
    internal var year: Int? = null
    internal var hour1: Int? = null
    internal var minute1: Int? = null
    internal var month1: Int? = null
    internal var day1: Int? = null
    internal var year1: Int? = null
    internal lateinit var cancel: TextView
    internal lateinit var select: TextView
    internal lateinit var saves: TextView
    internal lateinit var gender_error: TextView
    internal lateinit var sponsor_name_text: TextView
    internal lateinit var sponcer_fname_text: TextView
    internal lateinit var mname_text: TextView
    internal var my_info: TextView? = null

    internal lateinit var lname: EditText
    internal lateinit var fname: EditText
    internal lateinit var dob: EditText
    internal lateinit var mname: EditText
    internal lateinit var sponcer_name: EditText
    internal lateinit var sponcer_fname: EditText
    internal lateinit var mobile: EditText
    internal lateinit var same: CheckBox
    internal lateinit var whatsapp: EditText
    internal lateinit var address: EditText
    internal lateinit var city: EditText
    internal lateinit var pincode: EditText
    internal  var disrict: EditText? = null
    internal lateinit var pan_num: EditText
    internal lateinit var state: Spinner
    internal lateinit var cityspin: Spinner
    internal lateinit var byteArray: ByteArray
    var imagecode=""

    internal lateinit var radiogrp: RadioGroup
    internal lateinit var male: RadioButton
    internal lateinit var female: RadioButton
    internal lateinit var others: RadioButton
    internal lateinit var drawerLayouts: DrawerLayout
    internal  var imgpathh = 0
    internal lateinit var drawer_layout: LinearLayout
    internal lateinit var profile_lays: LinearLayout
    internal lateinit var my_info_lays: LinearLayout
    internal lateinit var diff_lay: LinearLayout
    internal  var MobilePattern = "[0-9]{10}"
    internal  var NamePattern = "[a-zA-Z]+"
    internal var RelationPattern = "[a-zA-Z -]+"
    //String email1 = email.getText().toString().trim();
    internal var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    internal lateinit var user_img: ImageView
    internal lateinit var profile_imgs: ImageView
    internal lateinit var choose_files: TextView
    internal lateinit var no_file: TextView
    internal var myBitmap: Bitmap? = null
    internal var picUri: Uri? = null
    internal lateinit var otp_lay:LinearLayout
    internal lateinit var langlay:LinearLayout
    internal lateinit var pin_lay:LinearLayout
    internal lateinit var contlay:LinearLayout
    internal lateinit var contlay_type:LinearLayout
    internal lateinit var list1: MutableList<String>
    internal lateinit var list2: MutableList<String>

    internal lateinit var list3: MutableList<String>
    internal lateinit var list4: MutableList<String>

    internal var dataAdapter: ArrayAdapter<String>? = null
    internal var dataAdapter1: ArrayAdapter<String>? = null
    internal lateinit var dataAdapter2: ArrayAdapter<String>
    internal lateinit var dataAdapter3: ArrayAdapter<String>

    private val permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected = ArrayList<String>()
    private val permissions = ArrayList<String>()
    internal lateinit var utilss: Utils
    internal lateinit var fi: File
    internal lateinit var cloudinary: Cloudinary
    internal var profimage = ""
    internal var getgender = ""
    internal lateinit var retry: Dialog
    internal lateinit var retrybut: ImageView
    internal lateinit var moblay: LinearLayout
    private var upFile: File? = null
    private val RESULT_LOAD_IMAGE1 = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    internal lateinit var imgfilee: File
    internal var MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    internal var menuvalue = 0

    internal var citydist = ""
    internal var statevalue = ""
    var frm=""
    var imgverify=""


    /*public Intent getPickImageChooserIntent() {

        // Determine Uri of camera image to save.
        Uri outputFileUri = getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();

        // collect all camera intents
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        // collect all gallery intents
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image*//*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        // the main intent is the last in the list (fucking android) so pickup the useless one
        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        // Create a chooser from the main intent
        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");

        // Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }*/


    /**
     * Get URI to image received from capture by camera.
     */
    private val captureImageOutputUri: Uri?
        get() {
            var outputFileUri: Uri? = null
            val getImage = externalCacheDir
            if (getImage != null) {
                outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
            }
            return outputFileUri
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_info)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)
        utilss = Utils(applicationContext)
        val tasks = stateload()
        tasks.execute("", "")
        init()
        //checkPermission();
        //checkPermission1();
        onclick()

        val frms=intent.extras
        try {
            frm = frms!!.getString("frm").toString()
        }
        catch(e:Exception){

        }

        if(frm=="img"){
            EnableFalse1()

        }
        else{
            EnableFalse()

        }
        loadprogress()
        //stateloads().execute()
        retry = Dialog(this)
        retry.requestWindowFeature(Window.FEATURE_NO_TITLE)
        retry.window!!.setBackgroundDrawable(
                ColorDrawable(android.graphics.Color.TRANSPARENT))
        val v = layoutInflater.inflate(R.layout.retrylay, null)
        retrybut = v.findViewById<View>(R.id.retry) as ImageView
        retry.setContentView(v)
        retry.setCancelable(false)

        cityspin.visibility = View.GONE

        list2 = ArrayList()
        list1 = ArrayList()

        list3 = ArrayList()
        list4 = ArrayList()
        typelay.visibility=View.GONE
        langlay.visibility=View.GONE

        dataAdapter2 = ArrayAdapter(this, R.layout.spinner_item, list2)
        dataAdapter3 = ArrayAdapter(this, R.layout.spinner_item, list3)


        Log.i("tabresp", utilss.loadImage() + "")

        startprogress()
        val task = GetInfoTask()
        task.execute()


        retrybut.setOnClickListener {
            retry.dismiss()
            startprogress()
            val task = GetInfoTask()
            task.execute()
        }


        same.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                whatsapp.setText(mobile.text.toString())
            }else{
                whatsapp.setText("")
            }
        }

        state.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {

                val selectedbankname = state.selectedItem.toString()
                val stateidval = state.selectedItemPosition
                val stateidstr = stateidval.toString()
                statevalue = stateidstr

                println("selectedbankname$selectedbankname")
                println("stateidstr$stateidstr")

                if (stateidstr == "31") {
                    cityspin.visibility = View.VISIBLE
                    city.visibility = View.GONE
                    val task = cityload()
                    task.execute(stateidstr, "")


                } else {
                    cityspin.visibility = View.GONE
                    city.visibility = View.VISIBLE
                    city.setText(citydist)
                }


            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    private fun init() {

      //  drawerLayouts = findViewById<View>(R.id.drawerLayout) as DrawerLayout
      //  drawer_layout = findViewById<View>(R.id.drawer_layout) as LinearLayout
    //    profile_lays = findViewById<View>(R.id.profile_lay) as LinearLayout
       // my_info_lays = findViewById<View>(R.id.my_info_lay) as LinearLayout
        diff_lay = findViewById<View>(R.id.diff_lay) as LinearLayout
        //setclick(drawer_layout, drawerLayout, this@MyInfo)

        gender_error = findViewById<View>(R.id.gender_error) as TextView
        sponsor_name_text = findViewById<View>(R.id.sponcer_name_text) as TextView
        sponcer_fname_text = findViewById<View>(R.id.sponcer_fname_text) as TextView
        mname_text = findViewById<View>(R.id.mname_text) as TextView
        saves = findViewById<View>(R.id.save) as TextView
        otp_lay=findViewById<View>(R.id.otp_lay) as LinearLayout
        langlay=findViewById<View>(R.id.langlay) as LinearLayout
        pin_lay=findViewById<View>(R.id.pin_lay) as LinearLayout
        contlay=findViewById<View>(R.id.contlay) as LinearLayout
        contlay_type=findViewById<View>(R.id.contlay_type) as LinearLayout
        moblay = findViewById<View>(R.id.moblay) as LinearLayout
        user_img = findViewById<View>(R.id.user_img) as ImageView
        choose_files = findViewById<View>(R.id.choose_file) as TextView
        no_file = findViewById<View>(R.id.no_file) as TextView
        contlay_type.visibility=View.GONE
        lname = findViewById<View>(R.id.lname) as EditText
        fname = findViewById<View>(R.id.fname) as EditText
        sponcer_name = findViewById<View>(R.id.sponcer_name) as EditText
        sponcer_fname = findViewById<View>(R.id.sponcer_fullname) as EditText
        dob = findViewById<View>(R.id.dob) as EditText
        mname = findViewById<View>(R.id.mname) as EditText
        mobile = findViewById<View>(R.id.mobile) as EditText
        same = findViewById<View>(R.id.same) as CheckBox
        whatsapp = findViewById<View>(R.id.mobilewhatasapp) as EditText
        pan_num = findViewById<View>(R.id.pan_num) as EditText

        address = findViewById<View>(R.id.address) as EditText
        city = findViewById<View>(R.id.city) as EditText
        pincode = findViewById<View>(R.id.pincode) as EditText
        //disrict = (EditText) findViewById(R.id.dist);
        state = findViewById<View>(R.id.state) as Spinner
        cityspin = findViewById<View>(R.id.spincity) as Spinner

      //  profile_imgs = findViewById<View>(R.id.profile_img) as ImageView

        radiogrp = findViewById<View>(R.id.radiogrp) as RadioGroup
        male = findViewById<View>(R.id.male) as RadioButton
        female = findViewById<View>(R.id.female) as RadioButton
        others = findViewById<View>(R.id.others) as RadioButton

        sponcer_name.visibility = View.GONE
        sponcer_fname.visibility = View.GONE
        // mname.setVisibility(View.GONE);
        sponsor_name_text.visibility = View.GONE
        sponcer_fname_text.visibility = View.GONE
        //mname_text.setVisibility(View.GONE);

        /*my_info = (TextView) findViewById(R.id.my_info);
        my_info.setBackgroundColor(0xFFFFFFFF);*/
       // profile_lay.setVisibility(View.VISIBLE)
       // profile_imgs.setImageResource(R.mipmap.up_arrow)
       // my_info_lay.setBackgroundColor(resources.getColor(R.color.eee))

    }

    private inner class cityload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkcity", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {


                //Log.i("check Input", Appconstants.CORE + "    " + jobj.toString());
                try {
                    val jobj = JSONObject()
                    jobj.put("state", param[0])


                    Log.i("checkInput", Appconstants.CITY + "    " + jobj.toString())
                    result = con.sendHttpPostjson2(Appconstants.CITY, jobj, "")

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                /*JSONObject json = new JSONObject();
			json.put("mobile",param[0]);
			json.put("otp", param[1]);
			result=con.sendHttpPostjson2(, json, "");*/


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("cityresp", resp!! + "")
            if (progbar.isShowing && progbar != null) {
                progbar.dismiss()
            }
            try {
                if (resp != null) {

                    list3.clear()
                    list4.clear()
                    list3.add("Select")
                    list4.add("0")
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        for (i in 0 until jarr.length()) {
                            val jobj = jarr.getJSONObject(i)

                            val cities = jobj.getString("city")
                            val citiesid = jobj.getString("id")

                            list3.add(cities)
                            list4.add(citiesid)

                            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            cityspin.adapter = dataAdapter3
                            cityspin.setSelection(list3.indexOf(citydist))
                          //  Log.e("VALUE STATE", cities)
                        }
                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {


                        Toast.makeText(this@MyInfo, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {

                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {

               // Log.e("VALUE STATE", e.toString())

                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    private fun onclick() {

        dob.setOnClickListener {
            picker = Dialog(this@MyInfo)
            picker.setContentView(R.layout.datepicker)
            picker.setTitle("Select Date ")
            datep = picker.findViewById<View>(R.id.datePicker) as DatePicker
            datep.spinnersShown = true
            datep.calendarViewShown = false
            val df = SimpleDateFormat("dd-MM-yyyy")
            /*try {
                    Date date = df.parse("31-12-2000");
                    long milliseconds = date.getTime();
                    datep.setMaxDate(milliseconds);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(dob.getText().toString().trim().length()>0){
                    String s[]=dob.getText().toString().trim().split("/");
                    Log.i("validddddd date",Integer.parseInt(s[0])+"     "+Integer.parseInt(s[1])+"    "+Integer.parseInt(s[2]));
                    datep.updateDate(Integer.parseInt(s[0]),Integer.parseInt(s[1])-1,Integer.parseInt(s[2]));

                }*/
            cancel = picker.findViewById<View>(R.id.cancel) as TextView
            cancel.setOnClickListener { picker.dismiss() }

            select = picker.findViewById<View>(R.id.select) as TextView
            select.setOnClickListener {
                month = datep.month + 1
                day = datep.dayOfMonth
                year = datep.year
                // hour = timep.getCurrentHour();
                // minute = timep.getCurrentMinute();
                date = day.toString() + "-" + month + "-" + year
                // time = hour + ":" + minute;
                // Toast.makeText(view.getContext(), date , Toast.LENGTH_LONG).show();
                dob.setText(date)
                //  datep.init(year,month,day,null);
                picker.dismiss()
                /* String picyear= date.substring(6,9);
                        String picmnth= date.substring(3,5);
                        String picday = date.substring(0,2);
                       // datep.updateDate(Integer.parseInt(picyear),Integer.parseInt(picmnth),Integer.parseInt(picday));
                        Toast.makeText(MyInfo.this, ""+picyear+picmnth+picday, Toast.LENGTH_SHORT).show();*/
            }
            picker.show()
        }

        choose_files.setOnClickListener(View.OnClickListener {
            if (choose_files.getText().toString().trim({ it <= ' ' }).equals("Choose Image", ignoreCase = true)) {
                //addFiles()
                onProfileImageClick()
            } else {
                profimage = ""
                imgpathh = 0
                choose_files.setText("Choose Image")
                Picasso.with(applicationContext).load(R.mipmap.userplaceholder).into(user_img)
                imageView29.visibility=View.GONE
            }
        })


        /*  permissions.add(CAMERA);
        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }*/

        saves.setOnClickListener(View.OnClickListener {

            if(frm=="info") {
                if (fname.text.toString().length <= 0) {
                    fname.error = "Enter Firstname"
                }


               /* if (lname.text.toString().length <= 0) {
                    lname.error = "Enter Lastname"
                }*/
                if (lname.text.toString().length > 0 && !lname.text.toString()
                        .matches(NamePattern.toRegex())
                ) {
                    lname.error = "Enter Characters Only"
                }
                mobilenum = mobile.text.toString().trim { it <= ' ' }
                whatsnum = whatsapp.text.toString().trim { it <= ' ' }

                if (mobilenum.isEmpty() || !isValidMobile(mobile) || mobilenum.length < 10 || mobile.length() > 10) {
                    mobile.error = "Enter Valid Mob No"
                }

                if (whatsnum.isEmpty() || !isValidMobile(whatsapp) || whatsnum.length < 10 || whatsapp.length() > 10) {
                    whatsapp.error = "Enter Valid Whatsapp No"
                }

                if (dob.text.toString().length <= 0) {
                    dob.error = "Enter Valid Date"
                }

                if (!male.isChecked && !female.isChecked && !others.isChecked) {
                    gender_error.visibility = View.VISIBLE
                    // Toast.makeText(SignUp.this, "please Select Gender", Toast.LENGTH_SHORT).show();
                } else {
                    gender_error.visibility = View.GONE
                }

                if (address.text.toString().length <= 0) {
                    address.error = "Enter Address"
                }
                if (city.text.toString().length <= 0 && cityspin.selectedItemPosition == 0) {
                    if (city.visibility == View.VISIBLE) {
                        city.error = "Enter City"
                    } else if (cityspin.visibility == View.VISIBLE) {
                        Toast.makeText(applicationContext, "Please select city", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                if (city.text.toString().length <= 0) {
                    city.error = "Enter City"
                }

                if (city.text.toString().length > 0 && !city.text.toString()
                        .matches(NamePattern.toRegex())
                ) {
                    city.error = "Enter Characters Only"
                }

                if (pincode.text.toString().trim { it <= ' ' }.length < 6 || pincode.text.toString()
                        .trim { it <= ' ' }.length > 6
                ) {
                    pincode.error = "Enter Pincode"
                }

                if (state.selectedItemPosition == 0) {

                    Toast.makeText(applicationContext, "Please select state", Toast.LENGTH_LONG)
                        .show()
                }

                if ((fname.text.toString().trim { it <= ' ' }.length > 0
                            && address.text.toString().trim { it <= ' ' }.length > 0
                            && (state.selectedItemPosition != 0 || cityspin.selectedItemPosition != 0) && pincode.text.toString()
                        .trim { it <= ' ' }.length == 6 && mobile.text.toString()
                        .trim { it <= ' ' }.length == 10
                            && whatsapp.text.toString()
                        .trim { it <= ' ' }.length == 10 && (male.isChecked || female.isChecked || others.isChecked) && dob.text.toString()
                        .trim { it <= ' ' }.length > 0 && mname.text.toString()
                        .trim { it <= ' ' }.length > 0)
                ) {

                    var cityval = ""
                    var stateval: Int? = 0

                    var gender = ""
                    if (male.isChecked) {
                        gender = "Male"
                    } else if (female.isChecked) {
                        gender = "Female"
                    } else if (others.isChecked) {
                        gender = "others"
                    }

                    if (city.visibility == View.VISIBLE) {
                        cityval = city.text.toString().trim { it <= ' ' }
                    } else {
                        try {
                            cityval = cityspin.selectedItem.toString()
                        } catch (e: Exception) {
                            cityval = cityspin.selectedItem.toString()

                        }
                    }
                    stateval = list1.indexOf(statevalue)

                    if (whatsapp.text.toString().isNotEmpty() && whatsapp.text.toString()
                            .trim { it <= ' ' }.length == 10
                    ) {

                        startprogress()
                        Log.i("imgpathhhh", imgpathh.toString() + "")
                        if (imgpathh == 2 || imgpathh == 0) {
                            val task = UpdateInfoTask()
                            task.execute(fname.text.toString().trim { it <= ' ' },
                                lname.text.toString().trim { it <= ' ' },
                                dob.text.toString().trim { it <= ' ' },
                                mobile.text.toString().trim { it <= ' ' },
                                gender,
                                address.text.toString().trim { it <= ' ' },
                                cityval,
                                pincode.text.toString().trim { it <= ' ' },
                                "",
                                stateval.toString(),
                                mname.text.toString().trim { it <= ' ' },
                                whatsapp.text.toString().trim { it <= ' ' })
                        } else {
                            val task = UpdateInfoTask()
                            task.execute(fname.text.toString().trim { it <= ' ' },
                                lname.text.toString().trim { it <= ' ' },
                                dob.text.toString().trim { it <= ' ' },
                                mobile.text.toString().trim { it <= ' ' },
                                gender,
                                address.text.toString().trim { it <= ' ' },
                                cityval,
                                pincode.text.toString().trim { it <= ' ' },
                                "",
                                stateval.toString(),
                                mname.text.toString().trim { it <= ' ' },
                                whatsapp.text.toString().trim { it <= ' ' })
                        }
                    } else {
                        if (whatsapp.text.toString().isEmpty()) {
                            Toast.makeText(
                                this@MyInfo,
                                "Whatsapp number required",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@MyInfo,
                                "Enter valid whatsapp number",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }

                } else {

                    println("fname  " + fname.text.toString())
                    println("lname  " + lname.text.toString())
                    println("address  " + address.text.toString())
                    println("city  " + city.text.toString())
                    println("state  " + state.selectedItem.toString())
                    println("cityspin  " + cityspin.selectedItem.toString())
                    println("pincode  " + pincode.text.toString())
                    println("mobile  " + mobile.text.toString())
                    println("whatsapp  " + whatsapp.text.toString())
                    println("dob  " + dob.text.toString())
                    println("mname  " + mname.text.toString())
                    Toast.makeText(this@MyInfo, "Please Fill Above Details", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else{
                var cityval = ""
                var stateval: Int? = 0

                var gender = ""
                if (male.isChecked) {
                    gender = "Male"
                } else if (female.isChecked) {
                    gender = "Female"
                } else if (others.isChecked) {
                    gender = "others"
                }

                if (city.visibility == View.VISIBLE) {
                    cityval = city.text.toString().trim { it <= ' ' }
                } else {
                    try {
                        cityval = cityspin.selectedItem.toString()
                    } catch (e: Exception) {
                        cityval = cityspin.selectedItem.toString()

                    }
                }
                stateval = list1.indexOf(statevalue)
                startprogress()

                if (imgpathh == 2 || imgpathh == 0) {
                    val task = UpdateInfoTask()
                    task.execute(fname.text.toString().trim { it <= ' ' },
                        lname.text.toString().trim { it <= ' ' },
                        dob.text.toString().trim { it <= ' ' },
                        mobile.text.toString().trim { it <= ' ' },
                        gender,
                        address.text.toString().trim { it <= ' ' },
                        cityval,
                        pincode.text.toString().trim { it <= ' ' },
                        "",
                        stateval.toString(),
                        mname.text.toString().trim { it <= ' ' },
                        whatsapp.text.toString().trim { it <= ' ' })
                } else {
                    startprogress()
                    val task = UpdateInfoTask()
                    task.execute(fname.text.toString().trim { it <= ' ' }, lname.text.toString().trim { it <= ' ' }, dob.text.toString().trim { it <= ' ' }, mobile.text.toString().trim { it <= ' ' },
                        gender, address.text.toString().trim { it <= ' ' }, cityval, pincode.text.toString().trim { it <= ' ' }, "", stateval.toString(),
                        pan_num.text.toString().trim { it <= ' ' }, whatsapp.text.toString().trim { it <= ' ' })
                }
            }
        })

    }

    fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this@MyInfo, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MyInfo, Manifest.permission.CAMERA)) {
                    val alertBuilder = AlertDialog.Builder(this)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("To use camera it is necessary to allow required permission")
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which ->
                        ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                        ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                    }
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                    ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }


    fun checkPermission1(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this@MyInfo, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@MyInfo, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    val alertBuilder = AlertDialog.Builder(this)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("To use gallery it is necessary to allow required permission")
                    alertBuilder.setPositiveButton(android.R.string.yes) { dialog, which -> ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE) }
                    val alert = alertBuilder.create()
                    alert.show()
                } else {
                    ActivityCompat.requestPermissions(this@MyInfo, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    private fun isValidMobile(mobile: EditText): Boolean {

        return Patterns.PHONE.matcher(mobile.text.toString().trim { it <= ' ' }).matches()
    }

    private fun isValidEmail(username: EditText): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(username.text.toString().trim { it <= ' ' }).matches()
    }

    private fun EnableFalse() {

        fname.isEnabled = false
        fname.setBackgroundResource(R.drawable.grey_bg1)
        lname.isEnabled = false
        lname.setBackgroundResource(R.drawable.grey_bg1)
        dob.isEnabled = false
        dob.setBackgroundResource(R.drawable.grey_bg1)
        mobile.isEnabled = false
        moblay.setBackgroundResource(R.drawable.grey_bg1)
        same.isEnabled = false
        whatsapp.isEnabled = false
        address.isEnabled = false
        address.setBackgroundResource(R.drawable.grey_bg1)
        city.isEnabled = false
        city.setBackgroundResource(R.drawable.grey_bg1)
        cityspin.isEnabled = false
        cityspin.setBackgroundResource(R.drawable.grey_bg1)
        /*disrict.setEnabled(false);
        disrict.setBackgroundResource(R.drawable.grey_bg1);*/
        pincode.isEnabled = false
        pincode.setBackgroundResource(R.drawable.grey_bg1)
        state.isEnabled = false
        state.setBackgroundResource(R.drawable.grey_bg1)
        male.isEnabled = false
        female.isEnabled = false
        others.isEnabled = false
        mname.isEnabled = false
        mname.setBackgroundResource(R.drawable.grey_bg1)
        choose_files.setVisibility(View.GONE)
        otp_lay.visibility=View.GONE
        langlay.visibility=View.GONE
        pin_lay.visibility=View.GONE
        contlay.visibility=View.GONE
    }

    private fun EnableFalse1() {

        fname.isEnabled = false
        fname.setBackgroundResource(R.drawable.grey_bg1)
        lname.isEnabled = false
        lname.setBackgroundResource(R.drawable.grey_bg1)
        dob.isEnabled = false
        dob.setBackgroundResource(R.drawable.grey_bg1)
        mobile.isEnabled = false
        moblay.setBackgroundResource(R.drawable.grey_bg1)
        same.isEnabled = false
        whatsapp.isEnabled = false
        address.isEnabled = false
        address.setBackgroundResource(R.drawable.grey_bg1)
        city.isEnabled = false
        city.setBackgroundResource(R.drawable.grey_bg1)
        cityspin.isEnabled = false
        cityspin.setBackgroundResource(R.drawable.grey_bg1)
        /*disrict.setEnabled(false);
        disrict.setBackgroundResource(R.drawable.grey_bg1);*/
        pincode.isEnabled = false
        pincode.setBackgroundResource(R.drawable.grey_bg1)
        state.isEnabled = false
        state.setBackgroundResource(R.drawable.grey_bg1)
        male.isEnabled = false
        female.isEnabled = false
        others.isEnabled = false
        mname.isEnabled = false
        mname.setBackgroundResource(R.drawable.grey_bg1)
       // choose_files.setVisibility(View.GONE)
        otp_lay.visibility=View.GONE
        langlay.visibility=View.GONE
        pin_lay.visibility=View.GONE
        contlay.visibility=View.GONE
        saves.setVisibility(View.VISIBLE)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(frm=="img"){
            menuInflater.inflate(R.menu.menu_my_portal, menu)

        }
        else{
            menuInflater.inflate(R.menu.menu_my_info, menu)

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == android.R.id.home) {
            if (menuvalue == 0)
                onBackPressed()
            return true
        }

        if (id == R.id.action_settings) {
            item.isVisible = false
            EnableTrue()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

      /*  if (drawerLayouts.isDrawerOpen(Gravity.LEFT)) {
            drawerLayouts.closeDrawer(Gravity.LEFT)
        } else {
        }*/
    }

    private fun EnableTrue() {


        if (whatsapp.text.toString().trim().isEmpty()){
            whatsapp.isEnabled = true
        }

        if (mname.text.toString().trim().isEmpty()){
            mname.isEnabled = true
        }


        same.isEnabled = true
        address.isEnabled = true
        address.setBackgroundResource(R.drawable.grey_stroke_bg)
        city.isEnabled = true
        city.setBackgroundResource(R.drawable.grey_stroke_bg)
        cityspin.isEnabled = true
        cityspin.setBackgroundResource(R.drawable.grey_stroke_bg)
        /*disrict.setEnabled(true);
        disrict.setBackgroundResource(R.drawable.grey_stroke_bg);*/
        pincode.isEnabled = true
        pincode.setBackgroundResource(R.drawable.grey_stroke_bg)
        state.isEnabled = true
        state.setBackgroundResource(R.drawable.grey_stroke_bg)
        male.isEnabled = true
        otp_lay.visibility=View.GONE
        langlay.visibility=View.GONE
        pin_lay.visibility=View.GONE
        contlay.visibility=View.GONE
        /*female.setEnabled(true);
        others.setEnabled(true);
        mname.setEnabled(true);
        mname.setBackgroundResource(R.drawable.grey_stroke_bg);*/
        choose_files.setVisibility(View.VISIBLE)
        saves.setVisibility(View.VISIBLE)
    }


    fun addFiles() {
        val openwith = android.app.AlertDialog.Builder(this@MyInfo)
        val popUpView = layoutInflater.inflate(R.layout.file_open, null)

        val gallery = popUpView.findViewById<View>(R.id.gallery) as TextView
        //val camera = popUpView.findViewById<View>(R.id.camera) as TextView
        openwith.setView(popUpView)
        val filopen = openwith.create()
        filopen.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        //Toast.makeText(getApplicationContext(),"sdasdasdasdasdasdas",Toast.LENGTH_SHORT).show();

        gallery.setOnClickListener {
            filopen.dismiss()
            if (ContextCompat.checkSelfPermission(this@MyInfo, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                try {
                    val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    i.type = "image/*"
                    startActivityForResult(i, RESULT_LOAD_IMAGE1)
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Gallery is not opening. Try later..", Toast.LENGTH_LONG).show()
                }

            } else {
                //checkPermission();
                //checkPermission1();
            }
        }

        filopen.show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("data : "+data)
        try {
            if (requestCode ==REQUEST_IMAGE /*RESULT_LOAD_IMAGE1*/ && resultCode == Activity.RESULT_OK && null != data) {
                var picturePath: String? = null
                val selectedImage = data!!.extras!!.get("path") as Uri //data.data
                //var selectedImages = data!!.data

                picturePath = getImgPath(selectedImage)
                fi = File(picturePath!!)

                // user_img.setImageURI(selectedImage);
                // Picasso.with(MyInfo.this).load(picturePath).placeholder(R.mipmap.userplaceholder).into(user_img);
                val yourSelectedImage = CommonFunctions.decodeFile1(picturePath, 400, 200)

                Log.i("original path1", picturePath + "")

                //removeimg.setVisibility(View.VISIBLE);
                // addimgbut.setVisibility(View.GONE);

                Log.i("pathsizeeeeee", (fi.length() / 1024).toString() + "      " + yourSelectedImage)
                val image1 = CommonFunctions.decodeFile1(picturePath, 0, 0)

                /*val bitmap = MediaStore.Images.Media.getBitmap(
                    this!!.getBaseContext().getContentResolver(),
                    selectedImages
                )*/

                val resizeBitmap =
                    resize(yourSelectedImage, yourSelectedImage.getWidth() / 2, yourSelectedImage.getHeight() / 2);

               // canvasLL!!.setImageBitmap(resizeBitmap)
                val stream = ByteArrayOutputStream()
                yourSelectedImage.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                byteArray = stream.toByteArray()
                imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                println("imagecode"+imagecode)
                if (fi.length() / 1024 == 0L || yourSelectedImage == null) {
                    user_img.setImageResource(R.mipmap.userplaceholder)
                    imgpathh = 0
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                            .show()
                } else {
                    imgpathh = 1
                    user_img.setImageURI(selectedImage)
                    choose_files.setText("Remove Image")

                }


            } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
                fi = File(upFile!!.absolutePath)
                var selectedImages = data!!.data

                val yourSelectedImage = CommonFunctions.decodeFile1(upFile!!.absolutePath, 400, 200)
                user_img.setImageBitmap(yourSelectedImage)
                Log.i("original path2", upFile!!.absolutePath + "")
                val bitmap = MediaStore.Images.Media.getBitmap(
                    this!!.getBaseContext().getContentResolver(),
                    selectedImages
                )

                val resizeBitmap =
                    resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

                // canvasLL!!.setImageBitmap(resizeBitmap)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                byteArray = stream.toByteArray()
                imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                imgfilee = File(upFile!!.absolutePath)
                imgfilee = File(CommonFunctions.decodeFile(this@MyInfo, upFile!!.absolutePath, 600, 600)!!)
                Log.i("imagepathhhhhh123", (fi.length() / 1024).toString() + "    " + imgfilee)
                Log.i("ipath2", upFile!!.absolutePath + "")
                choose_files.setText("Remove Image")
                imgpathh = 1
            }
            else if (requestCode == 1) {
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
                        this!!.getBaseContext().getContentResolver(),
                        selectedImage
                    )

                    val resizeBitmap =
                        resize(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

                    user_img!!.setImageBitmap(resizeBitmap)
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                    byteArray = stream.toByteArray()
                    imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    Log.e("imagecode", imagecode)
                    imgpathh = 1

                    val path = getImgPath(selectedImage!!)
                    //choose_files.setText("Remove Image")
                    if (path != null) {
                        val f = File(path!!)

                    }

                }
            }
            else {
                imgpathh = 0
                Toast.makeText(this, "You haven't picked Image, Try Again!!",
                        Toast.LENGTH_LONG).show()
                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                i.type = "image/*"
                startActivityForResult(i, 1)
            }

            if (requestCode == REQUEST_IMAGE)
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    val uri = data!!.extras!!.get("path") as Uri//.data//.getParcelableExtra("path")
                    try
                    {
                        // You can update this bitmap to your server
                        val bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
                        // loading profile image from local cache
                        loadProfile(uri.toString())
                    }
                    catch (e:IOException) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            imgpathh = 0
            Log.e("excep",e.toString());
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show()
        }

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



    fun getImgPath(uri: Uri?): String? {
        val result: String?
        val cursor = contentResolver.query(uri!!, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        Log.i("utilsresult", result!! + "")
        return result

    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 0) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    /**
     * Get the URI of the selected image from [.].<br></br>
     * Will return the correct URI for camera and gallery image.
     *
     * @param data the returned data of the activity result
     */
    fun getPickImageResultUri(data: Intent?): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }


        return if (isCamera) captureImageOutputUri else data!!.data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("pic_uri", picUri)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // get the file url
        picUri = savedInstanceState.getParcelable("pic_uri")
    }

    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String> {
        val result = ArrayList<String>()

        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }

        return result
    }

    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {

            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest!!) {
                    if (hasPermission(perms)) {

                    } else {

                        permissionsRejected.add(perms)
                    }
                }

                if (permissionsRejected.size > 0) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected[0])) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                            requestPermissions(permissionsRejected.toTypedArray(), ALL_PERMISSIONS_RESULT)
                                        }
                                    })
                            return
                        }
                    }

                }
            }
        }

    }

    private inner class ImageUpload : AsyncTask<String, String, String>() {


        override fun onPreExecute() {

            Log.i("ImageUpload", "started")
        }

        override fun doInBackground(vararg arg0: String): String? {
            var resp: String? = null
            // Log.i("prof URL: ",arg0[0]);

            val con = Connection()
            try {
                val upload = cloudinary.uploader().upload(fi, ObjectUtils.asMap("public_id", "profile/" + utilss.loadName()))
                val returnimage = upload["secure_url"] as String?
                val format = upload["format"] as String?
                resp = returnimage
                Log.i("returnimage1", "" + resp!!)
                Log.i("format", "" + format!!)


            } catch (uee: Exception) {
                Log.i("respppppppppppppp", uee.message + "")
                // uee.printStackTrace();
            }

            return resp
        }

        override fun onPostExecute(resp: String?) {
            Log.i("save resp", resp!! + "")

            if (resp != null) {
                try {
                    profimage = resp + ""
                    var gender = ""
                    var cityval = ""
                    var stateval: Int? = 0


                    if (male.isChecked) {
                        gender = "Male"
                    } else if (female.isChecked) {
                        gender = "Female"
                    } else if (others.isChecked) {
                        gender = "others"
                    }

                    if (city.visibility == View.VISIBLE) {
                        cityval = city.text.toString().trim { it <= ' ' }
                    } else {
                        cityval = cityspin.selectedItem.toString()
                    }

                    stateval = list1.indexOf(statevalue)



                } catch (e: Exception) {
                    // rgtxt.setText("Save");
                    stopprogress()
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

                }

            } else {
                stopprogress()
                // rgtxt.setText("Save");
                Toast.makeText(applicationContext, "Failed to update.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    private inner class GetInfoTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetInfoTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utilss.loadName())
                jobj.put("type", "fetch")


                Log.i("check Input", Appconstants.GET_MY_INFO + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.GET_MY_INFO, jobj, "")
                /*JSONObject json = new JSONObject();
json.put("mobile",param[0]);
json.put("otp", param[1]);
result=con.sendHttpPostjson2(, json, "");*/


            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp:String?) {
          //  Log.i("tabresp", resp!! + "")
            stopprogress()

            try {
                if (resp != null) {

                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        val jobj = jarr.getJSONObject(0)
                        fname.setText(if (jobj.isNull("first_name")) "" else jobj.getString("first_name"))
                        lname.setText(if (jobj.isNull("last_name")) "" else jobj.getString("last_name"))
                        dob.setText(if (jobj.isNull("dob")) "" else jobj.getString("dob"))
                        mobile.setText(if (jobj.isNull("mobile")) "" else jobj.getString("mobile"))
                        whatsapp.setText(if (jobj.isNull("whatsup")) "" else jobj.getString("whatsup"))

                        getgender = if (jobj.isNull("gender")) "" else jobj.getString("gender")
                        // fname.setText();
                        if (getgender.equals("Male", ignoreCase = true)) {
                            male.isChecked = true
                        } else if (getgender.equals("Female", ignoreCase = true)) {
                            female.isChecked = true
                        } else if (getgender.equals("Others", ignoreCase = true)) {
                            others.isChecked = true
                        }
                        citydist = jobj.getString("city")
                        statevalue = jobj.getString("state")
                        //Log.e("stateval", statevalue)
                       // Log.e("stateval dd", list1.indexOf(statevalue).toString())

                        if (statevalue === "31") {
                            val task = cityload()
                            task.execute(statevalue, "")
                        } else {
                            cityspin.visibility = View.GONE
                            city.visibility = View.VISIBLE
                        }
                        state.setSelection(list1.indexOf(statevalue))
                        address.setText(if (jobj.isNull("address")) "" else jobj.getString("address"))
                        //city.setText(jobj.isNull("city")?"":jobj.getString("city"));
                        pincode.setText(if (jobj.isNull("pincode")) "" else jobj.getString("pincode"))
                        /*disrict.setText(jobj.isNull("district")?"":jobj.getString("district"));*/
                        //state.setSelection(jobj.isNull("state")?"":jobj.getString("state"));
                        mname.setText(if (jobj.isNull("mname")) "" else jobj.getString("mname"))

                        imgverify=if (jobj.isNull("image_verify")) "" else jobj.getString("image_verify")
                        //println("imgverify"+imgverify)

                        /*utils.savePreferences("name",jobj.getString("username"));
                     utils.savePreferences("image","");
                     utils.savePreferences("doj",jobj.getString("doj"));
                     utils.savePreferences("designation",jobj.getString("designation"));
                     utils.savePreferences("ibv",jobj.getString("ibv"));
                     utils.savePreferences("gbv",jobj.getString("gbv"));
                     utils.savePreferences("commition",jobj.getString("p_commition"));*/
                        if (utilss.loadImage().toString().trim({ it <= ' ' }).length > 0) {
                            profimage = utilss.loadImage()
                            imgpathh = 2
                            Picasso.with(applicationContext).load(profimage).resize(200, 250).placeholder(R.mipmap.userplaceholder).into(user_img)
                            choose_files.setText("Remove image")
                            if(imgverify.isNotEmpty()){
                                if(imgverify=="1"){
                                    imageView29.visibility=View.VISIBLE
                                    imageView29.setImageResource(R.mipmap.verify)

                                }
                                else if(imgverify=="0"){
                                    imageView29.visibility=View.VISIBLE
                                    imageView29.setImageResource(R.mipmap.notverified)

                                }
                            }
                        } else {
                            imgpathh = 0
                            profimage = ""
                            choose_files.setText("Choose image")
                            Picasso.with(applicationContext).load(R.mipmap.userplaceholder).into(user_img)
                            imageView29.visibility=View.GONE


                        }

                    }
                } else {
                    retry.show()
                    // Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (e: Exception) {
                retry.show()
                e.printStackTrace()
                // Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {

                result = con.connStringResponse(Appconstants.STATE)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
          //  Log.i("stateresp", resp!! + "")
            if (progbar.isShowing && progbar != null) {
                progbar.dismiss()
            }
            try {
                if (resp != null) {

                    try {
                        list2.clear()
                        list1.clear()
                    } catch (e: Exception) {

                    }

                    list2.add("Select")
                    list1.add("0")
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        for (i in 0 until jarr.length()) {
                            val jobj = jarr.getJSONObject(i)

                            val states = jobj.getString("state")
                            val statesid = jobj.getString("id")

                            list2.add(states)
                            list1.add(statesid)

                            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            state.adapter = dataAdapter2
                            //state.setSelection(list2.indexOf(statevalue));
                            //Log.e("VALUE STATE", states)
                        }
                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {


                        Toast.makeText(this@MyInfo, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {

                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {

                //Log.e("VALUE STATE", e.toString())

                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private inner class stateloads : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@MyInfo);
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
                            cloudinary = Cloudinary("cloudinary://" + api_key  + ":" + api_secret+ "@" + cloud_name)

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
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }

    private inner class UpdateInfoTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("UpdateInfoTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("fname", param[0])
                jobj.put("lname", param[1])
                jobj.put("dob", param[2])
                jobj.put("mobile", param[3])
                jobj.put("gender", param[4])
                jobj.put("shreet", param[5])
                jobj.put("city", param[6])
                jobj.put("pincode", param[7])
                jobj.put("district", param[8])
                jobj.put("state", param[9])
                jobj.put("mname", param[10])
                jobj.put("whatsup", param[11])

                jobj.put("uname", utilss.loadName())
                jobj.put("type", "update")
                jobj.put("image", "data:image/png;base64"+","+imagecode)

                if(frm=="img") {
                    jobj.put("image_update", "1")
                }
                else{
                    jobj.put("image_update", "")

                }


                Log.i("check Input", Appconstants.GET_MY_INFO + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.GET_MY_INFO, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            //Log.i("tabresp", resp!! + "")
            stopprogress()

            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {

                        utilss.savePreferences("fname", fname.text.toString())
                        utilss.savePreferences("lname", lname.text.toString())
                        Toast.makeText(applicationContext, "Update successfully.", Toast.LENGTH_SHORT).show()
                        // onBackPressed();
                        utilss.savePreferences("image", "data:image/png;base64"+","+imagecode)
                        startActivity(Intent(this@MyInfo, HomePage::class.java))


                    } else {
                        Toast.makeText(applicationContext, "Faile to update.", Toast.LENGTH_SHORT).show()

                    }
                } else {
                    // retry.show();
                    Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                // retry.show();
                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    companion object {

        private val ALL_PERMISSIONS_RESULT = 107


        @Throws(IOException::class)
        private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap {

            val ei = ExifInterface(selectedImage.path.toString())
            val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> return rotateImage(img, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> return rotateImage(img, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> return rotateImage(img, 270)
                else -> return img
            }
        }

        private fun rotateImage(img: Bitmap, degree: Int): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(degree.toFloat())
            val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
            img.recycle()
            return rotatedImg
        }
    }


    fun onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        if (report.areAllPermissionsGranted())
                        {
                            showImagePickerOptions()
                        }
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            showSettingsDialog()
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(permissions:List<PermissionRequest>, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }
    private fun showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, object:ImagePickerActivity.PickerOptionListener {
            override fun onTakeCameraSelected() {
                launchCameraIntent()
            }
            override fun onChooseGallerySelected() {
                launchGalleryIntent()
            }
        })
    }

    private fun launchCameraIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }
    private fun launchGalleryIntent() {
        val intent = Intent(this, ImagePickerActivity::class.java)
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE)
        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.dialog_permission_title))
        builder.setMessage(getString(R.string.dialog_permission_message))
        builder.setPositiveButton(getString(R.string.go_to_settings), { dialog, which->
            dialog.cancel()
            openSettings() })
        builder.setNegativeButton(getString(android.R.string.cancel), { dialog, which-> dialog.cancel() })
        builder.show()
    }
    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", getPackageName(), null)
        intent.setData(uri)
        startActivityForResult(intent, 101)
    }

    private fun loadProfile(url:String) {
        Log.d("loadpro", "Image cache path: " + url)
        /*GlideApp.with(this).load(url)
                .into(imgProfile)
        imgProfile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent))*/
    }
    private fun loadProfileDefault() {
       /* GlideApp.with(this).load(R.drawable.baseline_account_circle_black_48)
                .into(imgProfile)
        imgProfile.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint))*/
    }
}
