package com.elancier.healthzone

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.elancier.healthzone.Adapter.OptionTreeListAdapter
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.AutofillPojo
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class OptionTree : AppCompatActivity() {
    var utils: Utils? = null
    var Uname = ""
    var Unique = ""
    var update: Dialog? = null
    var data: ArrayList<AutofillPojo>? = null
    var pojo: AutofillPojo? = null
    var nodata: TextView? = null
    var retry: TextView? = null
    var retry_lay: LinearLayout? = null
    var progress_lay: LinearLayout? = null
    var paging_lay: LinearLayout? = null
    var desiglay: LinearLayout? = null
    var uname: TextView? = null
    var name: TextView? = null
    var unique: TextView? = null
    var doj: TextView? = null
    var sub_user: TextView? = null
    var add_user: TextView? = null
    var search: TextView? = null
    var uname1: TextView? = null
    var uname2: TextView? = null
    var adapter: OptionTreeListAdapter? = null
    var listView: ListView? = null
    var Spon_Unique = ""
    var Spon_Uname = ""
    var Spon_Name = ""
    var First_Name = ""
    var Last_Name = ""
    var edcancel: ImageView? = null
    var search_lay: LinearLayout? = null
    var cardView7: CardView? = null
    var unique_search: EditText? = null
    var search_check = false
    var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option_tree)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        // getSupportActionBar().setHomeAsUpIndicator(R.mipmap.menu);
        val d = resources.getDrawable(R.drawable.menu_bar_bg_red)
        supportActionBar!!.setBackgroundDrawable(d)
        utils = Utils(applicationContext)
        // Uname = getIntent().getExtras().getString("name");
        // Unique = getIntent().getExtras().getString("name");
        Uname = utils!!.loadName()
        Unique = utils!!.loadName()
        supportActionBar!!.setTitle(Uname)
        inits()
        retry!!.setOnClickListener {
            retry_lay!!.visibility = View.GONE
            cardView7!!.visibility = View.GONE
            progress_lay!!.visibility = View.VISIBLE
            data = ArrayList()
            val task: GetinfoTask = GetinfoTask()
            task.execute(Uname)
        }
        onclick()
    }

    private fun inits() {
        progress_lay = findViewById<View>(R.id.progress_lay) as LinearLayout
        cardView7 = findViewById<View>(R.id.cardView7) as CardView
        retry_lay = findViewById<View>(R.id.retry_lay) as LinearLayout
        paging_lay = findViewById<View>(R.id.paging_lay) as LinearLayout
        nodata = findViewById<View>(R.id.nodata) as TextView
        retry = findViewById<View>(R.id.retry) as TextView
        listView = findViewById<View>(R.id.listView) as ListView
        uname1 = findViewById<View>(R.id.uname1) as TextView
        uname2 = findViewById<View>(R.id.uname2) as TextView
        uname = findViewById<View>(R.id.uname) as TextView
        desiglay = findViewById<View>(R.id.linearLayout8) as LinearLayout
        sub_user = findViewById<View>(R.id.sub_users) as TextView
        add_user = findViewById<View>(R.id.add_user) as TextView
        name = findViewById<View>(R.id.name) as TextView
        unique = findViewById<View>(R.id.unique) as TextView
        doj = findViewById<View>(R.id.doj) as TextView
        unique_search = findViewById<View>(R.id.unique_search) as EditText
        search = findViewById<View>(R.id.search) as TextView
        edcancel = findViewById<View>(R.id.edcancel) as ImageView
        search_lay = findViewById<View>(R.id.search_lay) as LinearLayout
    }

    private fun onclick() {
        listView!!.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (data!!.size > 0) {
                    Uname = data!![position].uname
                    supportActionBar!!.setTitle(Uname)
                    Log.i("UNAME", Uname)
                    progress_lay!!.visibility = View.VISIBLE
                    data = ArrayList()
                    val task: GetinfoTask = GetinfoTask()
                    task.execute(Uname)
                }
            }
        edcancel!!.setOnClickListener {
            progress_lay!!.visibility = View.VISIBLE
            data = ArrayList()
            Uname = Unique
            val task: GetinfoTask = GetinfoTask()
            task.execute(Uname)
            supportActionBar!!.setTitle(Uname)

            /*getSupportActionBar().setTitle(Unique);
                    Log.i("UNAME",Unique);
                    progress_lay.setVisibility(View.VISIBLE);
                    data = new ArrayList<>();
                    GetinfoTask task = new GetinfoTask();
                    task.execute(Unique);*/
        }
        search!!.setOnClickListener {
            if (unique_search!!.text.toString().trim { it <= ' ' }.length > 0) {
                Uname = unique_search!!.text.toString().trim { it <= ' ' }
                Log.i("UNAME", Uname)
                progress_lay!!.visibility = View.VISIBLE
                data = ArrayList()
                val task: GetinfoTask = GetinfoTask()
                task.execute(Uname)
            }
        }
        add_user!!.setOnClickListener {
            val `in` = Intent(this@OptionTree, SignUp::class.java)
            val bun = Bundle()
            bun.putString("spon_uname", Spon_Uname)
            bun.putString("spon_unique", Spon_Unique)
            bun.putString("spon_name", Spon_Name)
            `in`.putExtras(bun)
            startActivity(`in`)
        }
    }

    override fun onResume() {
        super.onResume()
        progress_lay!!.visibility = View.VISIBLE
        cardView7!!.visibility = View.GONE
        data = ArrayList()
        val task: GetinfoTask = GetinfoTask()
        task.execute(Uname)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_search, menu)
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
        if (id == R.id.search) {
            if (!search_check) {
                search_lay!!.visibility = View.VISIBLE
                search_check = true
            } else {
                search_lay!!.visibility = View.GONE
                search_check = false
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (Uname.equals(Unique, ignoreCase = true)) {
            super.onBackPressed()
        } else {
            progress_lay!!.visibility = View.VISIBLE
            data = ArrayList()
            Uname = Unique
            val task: GetinfoTask = GetinfoTask()
            task.execute(Uname)
            supportActionBar!!.setTitle(Uname)
        }
    }

    fun openpin(name:String) {
        try {
            update = Dialog(this)
            update!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update!!.window!!.setBackgroundDrawable(
                ColorDrawable(Color.WHITE)
            )
            val vs = layoutInflater.inflate(R.layout.welcome_pin_popup, null)
            val updatebut =
                vs.findViewById<View>(R.id.textView81) as TextView
            val pintext=vs.findViewById<View>(R.id.otp_edit_box1) as EditText

            updatebut.setOnClickListener {

                if(pintext.text.toString().isNotEmpty()) {
                    update!!.dismiss()
                    Checkpin().execute(pintext.text.toString().trim(),name)
                }
                else{
                    pintext.setError("Required field*")
                }

            }


            update!!.setContentView(vs)
            update!!.setCancelable(true)
            update!!.show()


        } catch (e: java.lang.Exception) {
            Log.e("er", e.toString())
        }
    }


    inner class Checkpin : AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            // super.onPreExecute();
            Log.i("GetInfoTask", "started")

        }

        protected override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val json = JSONObject()
                json.put("type", "Welcome Pin")
                json.put("pin", params[0])
                json.put("uname", utils!!.loadName())
                json.put("current", params[1])
                Log.i("utilsInput", Appconstants.STARTUP_TREE_PIN + "    " + json.toString())
                result = con.sendHttpPostjson2(Appconstants.STARTUP_TREE_PIN, json, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("utilsresp", resp + "")

            val jsonarr=JSONArray(resp)
            var status=jsonarr.getJSONObject(0).getString("Status")
            if(status=="Success"){
                toast(jsonarr.getJSONObject(0).getString("Response"))
            }
            else if(status=="Failure"){
                toast(jsonarr.getJSONObject(0).getString("Response"))

            }

        }
    }

    fun toast(msg:String){
        val toast=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

     inner class GetinfoTask : AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            // super.onPreExecute();
            Log.i("GetInfoTask", "started")
        }

        protected override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con = Connection()
            try {
                val json = JSONObject()
                json.put("uname", params[0])
                Log.i("utilsInput", Appconstants.STARTUP_TREE + "    " + json.toString())
                result = con.sendHttpPostjson2(Appconstants.STARTUP_TREE, json, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("utilsresp", resp + "")
            progress_lay!!.visibility = View.GONE
            cardView7!!.visibility = View.VISIBLE

            //listView.setPadding(0, 0, 0, 0);
            paging_lay!!.visibility = View.GONE
            // scrollNeed = true;
            listView!!.visibility = View.VISIBLE
            try {
                if (resp != null) {
                    val jsonArray = JSONArray(resp)
                    val jsonObject = jsonArray.getJSONObject(0)
                    if (jsonObject.getString("Status") == "Success") {
                        val jsonArray1 = jsonObject.getJSONArray("Response")
                        val jsonObject1 = jsonArray1.getJSONObject(0)
                        supportActionBar!!.title = Uname
                        doj!!.text = Uname
                        //uname.setText(jsonObject1.getString("username").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("username"));
                        // Spon_Uname = (jsonObject1.getString("username").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("username"));
                        unique!!.text = if (jsonObject1.getString("last_name").trim { it <= ' ' }
                                .equals(
                                    "null",
                                    ignoreCase = true
                                )) "" else jsonObject1.getString("last_name")
                        Last_Name = if (jsonObject1.getString("last_name").trim { it <= ' ' }
                                .equals(
                                    "null",
                                    ignoreCase = true
                                )) "" else jsonObject1.getString("last_name")
                        // Spon_Unique = (jsonObject1.getString("unique").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("unique"));
                        First_Name = if (jsonObject1.getString("first_name").trim { it <= ' ' }
                                .equals(
                                    "null",
                                    ignoreCase = true
                                )) "" else jsonObject1.getString("first_name")
                        name!!.text = if (jsonObject1.getString("first_name").trim { it <= ' ' }
                                .equals(
                                    "null",
                                    ignoreCase = true
                                )) "" else jsonObject1.getString("first_name")
                        //  Spon_Name = (jsonObject1.getString("name").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("name"));
                        if (!jsonObject1.getString("designation").trim { it <= ' ' }.isEmpty()) {
                            desiglay!!.visibility = View.VISIBLE
                            desiglay!!.visibility = View.VISIBLE
                            uname!!.text = jsonObject1.getString("designation")
                        } else {
                            uname!!.text = "None"
                        }
                        uname1!!.text = jsonObject1.getString("super1")
                        uname2!!.text = jsonObject1.getString("super2")
                        sub_user!!.text = if (jsonObject1.getString("plan").trim { it <= ' ' }
                                .equals(
                                    "null",
                                    ignoreCase = true
                                )) "" else jsonObject1.getString("plan")
                        //sub_user.setText(jsonObject1.getString("child_count").trim().equalsIgnoreCase("null") ? "" : jsonObject1.getString("child_count"));
                        Spon_Uname = Uname
                        Spon_Unique = Uname
                        Spon_Name = First_Name + Last_Name
                        if (!jsonObject1.isNull("child") && !jsonObject1.getString("child")
                                .equals("", ignoreCase = true) && !jsonObject1.getString("child")
                                .equals("Null", ignoreCase = true)
                        ) {
                            val jsonArray2 = jsonObject1.getJSONArray("child")
                            for (i in 0 until jsonArray2.length()) {
                                val jsonObject2 = jsonArray2.getJSONObject(i)
                                pojo = AutofillPojo()

                                // pojo.setData(jsonObject1.toString());
                                pojo!!.uname =
                                    if (jsonObject2.getString("username").trim { it <= ' ' }
                                            .equals(
                                                "null",
                                                ignoreCase = true
                                            )) "" else jsonObject2.getString("username")
                                pojo!!.name = if (jsonObject2.getString("name").trim { it <= ' ' }
                                        .equals(
                                            "null",
                                            ignoreCase = true
                                        )) "" else jsonObject2.getString("name")
                                pojo!!.mobile =
                                    if (jsonObject2.getString("designation").trim { it <= ' ' }
                                            .equals(
                                                "null",
                                                ignoreCase = true
                                            )) "None" else jsonObject2.getString("designation")
                                pojo!!.sub_users =
                                    if (jsonObject2.getString("utype").trim { it <= ' ' }
                                            .equals(
                                                "null",
                                                ignoreCase = true
                                            )) "" else jsonObject2.getString("utype")
                                pojo!!.product =
                                    if (jsonObject2.getString("color").trim { it <= ' ' }
                                            .equals(
                                                "null",
                                                ignoreCase = true
                                            )) "" else jsonObject2.getString("color")
                                pojo!!.mrp = if (jsonObject2.getString("super1").trim { it <= ' ' }
                                        .equals(
                                            "null",
                                            ignoreCase = true
                                        )) "None" else jsonObject2.getString("super1")
                                pojo!!.qty = if (jsonObject2.getString("super2").trim { it <= ' ' }
                                        .equals(
                                            "null",
                                            ignoreCase = true
                                        )) "None" else jsonObject2.getString("super2")
                                pojo!!.doj = if (jsonObject2.getString("welcome").trim { it <= ' ' }
                                        .equals(
                                            "null",
                                            ignoreCase = true
                                        )) "None" else jsonObject2.getString("welcome")
                                pojo!!.unique =
                                    if (jsonObject2.getString("welcome_up").trim { it <= ' ' }
                                            .equals(
                                                "null",
                                                ignoreCase = true
                                            )) "None" else jsonObject2.getString("welcome_up")

                                // pojo.setUnique(jsonObject2.getString("unique").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("unique"));
                                //pojo.setSub_users(jsonObject2.getString("child_count").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("child_count"));
                                //pojo.setDoj(jsonObject2.getString("doj").trim().equalsIgnoreCase("null") ? "" : jsonObject2.getString("doj"));
                                data!!.add(pojo!!)
                            }
                        } else {
                            listView!!.visibility = View.GONE
                            nodata!!.visibility = View.VISIBLE
                            nodata!!.text = "No Sub Users "
                            adapter = OptionTreeListAdapter(
                                this@OptionTree,
                                R.layout.option_tree_list_items,
                                data
                            )
                            listView!!.adapter = adapter
                        }
                        nodata!!.visibility = View.GONE
                        adapter = OptionTreeListAdapter(
                            this@OptionTree,
                            R.layout.option_tree_list_items,
                            data
                        )
                        listView!!.adapter = adapter
                    } else {
                        nodata!!.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, "No Data Found.", Toast.LENGTH_SHORT)
                            .show()
                        retry_lay!!.visibility = View.GONE
                    }
                } else {
                    retry_lay!!.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext,
                        "Please check your internet connection and try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Oops! Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}