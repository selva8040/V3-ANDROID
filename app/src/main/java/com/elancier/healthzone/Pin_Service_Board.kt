package com.elancier.healthzone

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.elancier.healthzone.Adapter.pinboard_adap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import kotlinx.android.synthetic.main.activity_pin__service__board.*
import kotlinx.android.synthetic.main.current_address_list_item.*
import kotlinx.android.synthetic.main.new_customer.*
import org.json.JSONObject

class Pin_Service_Board : AppCompatActivity() {

    lateinit var pDialo : ProgressDialog
    val activity=this
    val cntarea=ArrayList<String>()
    val statearea=ArrayList<String>()
    val distarea=ArrayList<String>()
    internal lateinit var itemsAdapter: pinboard_adap
    private val mRecyclerListitems = java.util.ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo_dup>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var utils: Utils

    var rate1=""
    var rate2=""
    var rate3=""
    var rate4=""
    var rate5=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin__service__board)

        utils = Utils(applicationContext)

        supportActionBar!!.title = "Pin & Service Board"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.newdashboard_gradient
            )
        )
        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)

        productItems = java.util.ArrayList()

        itemsAdapter = pinboard_adap(mRecyclerListitems, this, object :
            pinboard_adap.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int, viewType: Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo
                // Log.e("clickresp", "value")

                //clikffed();
            }
        })
        recyclerlist.adapter = itemsAdapter

        cntryload().execute()

        countryspin.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                stateload().execute()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        statespin.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                distload().execute()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        distspin.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {

                if (distspin.selectedItemPosition != 0) {
                    if (countryspin.selectedItemPosition != 0 &&
                        statespin.selectedItemPosition != 0 &&
                        distspin.selectedItemPosition != 0
                    ) {
                        Listload().execute()

                    } else {
                        if (countryspin.selectedItemPosition == 0) {
                            toast("Please select country")
                        }
                        if (statespin.selectedItemPosition == 0) {
                            toast("Please select state")
                        }
                        if (distspin.selectedItemPosition == 0) {
                            toast("Please select district")
                        }
                    }
                } else {

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }


    fun rate(name: String, id: String){
        try {
            val update = Dialog(this@Pin_Service_Board)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            val v = layoutInflater.inflate(R.layout.app_rating_dialog, null)
            val lin1=v.findViewById<LinearLayout>(R.id.lin1)
            val lin2=v.findViewById<LinearLayout>(R.id.lin2)
            val lin3=v.findViewById<LinearLayout>(R.id.lin3)
            val lin4=v.findViewById<LinearLayout>(R.id.lin4)
            val lin5=v.findViewById<LinearLayout>(R.id.lin5)
            val titlename1=v.findViewById<RadioButton>(R.id.titlename)
            val titlename2=v.findViewById<RadioButton>(R.id.titlename2)
            val titlename3=v.findViewById<RadioButton>(R.id.titlename3)
            val titlename4=v.findViewById<RadioButton>(R.id.titlename4)
            val titlename5=v.findViewById<RadioButton>(R.id.titlename5)
            val download=v.findViewById<TextView>(R.id.download)
            val close=v.findViewById<TextView>(R.id.update)

            if(rate1.isNotEmpty()){
                lin1.visibility=View.VISIBLE
                titlename1.setText(rate1)
            }
            if(rate2.isNotEmpty()){
                lin2.visibility=View.VISIBLE
                titlename2.setText(rate2)
            }
            if(rate3.isNotEmpty()){
                lin3.visibility=View.VISIBLE
                titlename3.setText(rate3)
            }
            if(rate4.isNotEmpty()){
                lin4.visibility=View.VISIBLE
                titlename4.setText(rate4)
            }
            if(rate5.isNotEmpty()){
                lin5.visibility=View.VISIBLE
                titlename5.setText(rate5)
            }
            var click=""

            titlename1.setOnClickListener {
                if(titlename1.isChecked==true){
                    titlename2.setChecked(false)
                    titlename3.setChecked(false)
                    titlename4.setChecked(false)
                    titlename5.setChecked(false)
                    click="1"
                }
            }
            titlename2.setOnClickListener {
                if(titlename2.isChecked==true){
                    titlename1.setChecked(false)
                    titlename3.setChecked(false)
                    titlename4.setChecked(false)
                    titlename5.setChecked(false)
                    click="2"

                }
            }
            titlename3.setOnClickListener {
                if(titlename3.isChecked==true){
                    titlename2.setChecked(false)
                    titlename1.setChecked(false)
                    titlename4.setChecked(false)
                    titlename5.setChecked(false)
                    click="3"

                }
            }
            titlename4.setOnClickListener {
                if(titlename4.isChecked==true){
                    titlename2.setChecked(false)
                    titlename1.setChecked(false)
                    titlename3.setChecked(false)
                    titlename5.setChecked(false)
                    click="4"

                }
            }
            titlename5.setOnClickListener {
                if(titlename5.isChecked==true){
                    titlename4.setChecked(false)
                    titlename1.setChecked(false)
                    titlename3.setChecked(false)
                    titlename5.setChecked(false)
                    click="5"

                }
            }

            download.setOnClickListener {
                var ratings=""
                if(click.isNotEmpty()){
                    if(titlename1.isChecked==true){
                        ratings=rate1
                    }
                    else if(titlename2.isChecked==true){
                        ratings=rate2
                    }
                    else if(titlename3.isChecked==true){
                        ratings=rate3
                    }
                    else if(titlename4.isChecked==true){
                        ratings=rate4
                    }
                    else if(titlename5.isChecked==true){
                        ratings=rate5
                    }
                    update.dismiss()
                    saverating().execute(name, ratings)
                }
                else if(click.isEmpty()){
                    toast("Please select your rating")
                }
            }







            update.setContentView(v)
            update.setCancelable(false)
            update.show()


            close.setOnClickListener {
                update.dismiss()
            }
          /*  titlename.text = "New Version 1.$Number"
            val content = v.findViewById<View>(R.id.content) as TextView
            content.text =
                "You are using an older version of Online Tv ( version $versionname ). Update now to get the latest features."
            updatebut.setOnClickListener {
                clickupdate = "true"
                update.dismiss()
                val intt = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.elancier.healthzone&hl=en")
                )
                startActivity(intt)*/

            /*laterbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update.dismiss();
                }
            });*/
        } catch (e: java.lang.Exception) {
            //logger.info("PerformVersionTask error" + e.getMessage());
            Log.e("rateerr", e.toString())
        }
    }

    private inner class cntryload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(activity);
            pDialo.setMessage("Loading...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            cntarea.clear()

        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "Country")
                Log.i("check Input", Appconstants.pinboard + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinboard, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        cntarea.add("Select")
                        val jarr = json.getJSONArray("Response")
                        for(i in 0 until jarr.length()){
                            val jobj=jarr.getJSONObject(i)
                            cntarea.add(jobj.getString("country"))
                        }

                        val jarr1 = json.getJSONArray("ratings")


                            try{
                                rate1=jarr1.getJSONObject(0).getString("name")
                            }
                            catch (e: Exception){}
                            try{
                                rate2=jarr1.getJSONObject(1).getString("name")
                            }
                            catch (e: Exception){}
                            try{
                                rate3=jarr1.getJSONObject(2).getString("name")
                            }
                            catch (e: Exception){}
                            try{
                                rate4=jarr1.getJSONObject(3).getString("name")
                            }
                            catch (e: Exception){}
                            try{
                                rate5=jarr1.getJSONObject(4).getString("name")
                            }
                            catch (e: Exception){}


                        val cntareaadap=ArrayAdapter(
                            activity,
                            R.layout.support_simple_spinner_dropdown_item,
                            cntarea
                        )
                        countryspin.adapter=cntareaadap
                        pDialo.dismiss()
                        countryspin.setSelection(1)

                    } else {
                        pDialo.dismiss()

                    }
                } else {
                    pDialo.dismiss()

                }
            } catch (e: Exception) {


            }


        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(activity);
            pDialo.setMessage("Loading...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            statearea.clear()
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "State")
                if(countryspin.selectedItemPosition==0) {
                    jobj.put("country", cntarea[1].toString())
                }
                else if(countryspin.selectedItemPosition!=0){
                    jobj.put("country", countryspin.selectedItem.toString())

                }
                Log.i("check Input", Appconstants.pinboard + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinboard, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        statearea.add("Select")

                        val jarr = json.getJSONArray("Response")
                        for(i in 0 until jarr.length()){
                            val jobj=jarr.getJSONObject(i)
                            statearea.add(jobj.getString("state"))
                        }
                        val stateareaadap=ArrayAdapter(
                            activity,
                            R.layout.support_simple_spinner_dropdown_item,
                            statearea
                        )
                        statespin.adapter=stateareaadap
                        pDialo.dismiss()

                    } else {
                        pDialo.dismiss()

                    }
                } else {
                    pDialo.dismiss()

                }
            } catch (e: Exception) {
                Log.e("err", e.toString())

            }


        }
    }



    private inner class Listload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            productItems!!.clear()
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(activity);
            pDialo.setMessage("Loading...");
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
                val jobj = JSONObject()
                jobj.put("type", "List")
                if(countryspin.selectedItemPosition==0) {
                    jobj.put("country", cntarea[1])

                }
                else if(countryspin.selectedItemPosition!=0){
                    jobj.put("country", countryspin.selectedItem.toString())

                }

                if(statespin.selectedItemPosition==0) {
                    jobj.put("state", statearea[1].toString())

                }

                else if(statespin.selectedItemPosition!=0){
                    jobj.put("state", statespin.selectedItem.toString())

                }

                if(distspin.selectedItemPosition==0) {
                    jobj.put("district", distarea[1].toString())

                }

                else if(distspin.selectedItemPosition!=0){
                    jobj.put("district", distspin.selectedItem.toString())

                }

               // jobj.put("state", statespin.selectedItem.toString())
               // jobj.put("district", distspin.selectedItem.toString())
                Log.i("check Input", Appconstants.pinboard + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinboard, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        val jarr = json.getJSONArray("Response")
                        for (i in 0 until jarr.length()) {
                            val jobj = jarr.getJSONObject(i)
                            val name = jobj.getString("name")
                            val id = jobj.getString("id")
                            val mobile = jobj.getString("mobile")
                            val whatsapp = jobj.getString("whatsapp")
                            val bank = jobj.getString("bank")
                            val branch = jobj.getString("branch")
                            val acc = jobj.getString("acc")
                            val ifsc = jobj.getString("ifsc")
                            val contact = jobj.getString("contact")
                            val lang = jobj.getString("language")
                            val spec = jobj.getString("specialist")
                            val desig = jobj.getString("designation")
                            val img = jobj.getString("gender")


                            try {
                                productItems!!.add(
                                    Rewardpointsbo_dup(
                                        id.toString(),
                                        name,
                                        mobile,
                                        whatsapp,
                                        bank,
                                        branch,
                                        acc,
                                        ifsc,
                                        contact,
                                        img, lang, spec, desig
                                    )
                                )
                            } catch (e: Exception) {
                                productItems!!.add(
                                    Rewardpointsbo_dup(
                                        id.toString(),
                                        name,
                                        mobile,
                                        whatsapp,
                                        bank,
                                        branch,
                                        acc,
                                        ifsc,
                                        contact,
                                        img, lang, spec, desig
                                    )
                                )
                            }
                        }
                        pDialo.dismiss()
                        recyclerlist.visibility = View.VISIBLE
                        mRecyclerListitems.clear()
                        mRecyclerListitems.addAll(productItems!!)
                        itemsAdapter.notifyDataSetChanged()

                    }
                    else{
                        pDialo.dismiss()

                        toast(json.getString("Response"))

                    }


                } else {
                    pDialo.dismiss()

                }
            } /*else {
                    pDialo.dismiss()

                }*/
            catch (e: Exception) {
                pDialo.dismiss()


            }


        }
    }
    fun toast(msg: String){
        val toa= Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
        toa.setGravity(Gravity.CENTER, 0, 0)
        toa.show()
    }

    private inner class distload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(activity);
            pDialo.setMessage("Loading...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            distarea.clear()
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            var yesval: String? = null

            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("type", "District")
                if(countryspin.selectedItemPosition==0) {
                    jobj.put("country", cntarea[1].toString())
                }
                else if(countryspin.selectedItemPosition!=0){
                    jobj.put("country", countryspin.selectedItem.toString())

                }
                if(statespin.selectedItemPosition==0) {
                    //jobj.put("country", cntarea[1].toString())
                    jobj.put("state", statearea[1].toString())

                }
                else if(statespin.selectedItemPosition!=0){
                    jobj.put("state", statespin.selectedItem.toString())

                }
                Log.i("check Input", Appconstants.pinboard + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinboard, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {
                        distarea.add("Select")
                        val jarr = json.getJSONArray("Response")
                        for(i in 0 until jarr.length()){
                            val jobj=jarr.getJSONObject(i)
                            distarea.add(jobj.getString("district"))
                        }
                        val stateareaadap=ArrayAdapter(
                            activity,
                            R.layout.support_simple_spinner_dropdown_item,
                            distarea
                        )
                        distspin.adapter=stateareaadap
                        pDialo.dismiss()

                    } else {
                        pDialo.dismiss()

                    }
                } else {
                    pDialo.dismiss()

                }
            } catch (e: Exception) {


            }


        }
    }


    private inner class saverating : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started")
            pDialo = ProgressDialog(activity);
            pDialo.setMessage("Submitting...");
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
                val jobj = JSONObject()
                jobj.put("type", "submit")
                jobj.put("username", utils.loadName())
                jobj.put("pinagent", param[0])
                jobj.put("ratings", param[1])
                Log.i("check Input", Appconstants.pinboard + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinboard, jobj, "")

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            Log.i("stateresp", resp!! + "")

            try {
                if (resp != null) {

                    val json = JSONObject(resp)
                    //val obj1 = json.getJSONObject(0)
                    if (json.getString("Status") == "Success") {

                        pDialo.dismiss()
                        toast("Thanks for your ratings.")

                    } else {
                        pDialo.dismiss()

                    }
                } else {
                    pDialo.dismiss()

                }
            } catch (e: Exception) {


            }


        }
    }

}