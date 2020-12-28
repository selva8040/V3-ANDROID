package com.elancier.healthzone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elancier.healthzone.Adapter.Notificationadap
import com.elancier.healthzone.Adapter.Paymentadap
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.elancier.healthzone.Common.Utils
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.shreyaspatil.easyupipayment.EasyUpiPayment
import com.shreyaspatil.easyupipayment.listener.PaymentStatusListener
import com.shreyaspatil.easyupipayment.model.TransactionDetails
import com.shreyaspatil.easyupipayment.model.TransactionStatus
import kotlinx.android.synthetic.main.activity_payment_.*
import kotlinx.android.synthetic.main.activity_payment_.progressBar
import kotlinx.android.synthetic.main.activity_payment_.recyclerlist
import kotlinx.android.synthetic.main.activity_super__salary.*
import kotlinx.android.synthetic.main.activity_super__salry_history.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Payment_Activity : AppCompatActivity(), PaymentStatusListener {
    lateinit var pDialo : ProgressDialog
    val arraystr=ArrayList<String>()
    lateinit var utils: Utils
    var tid="";
    internal lateinit var itemsAdapter: Paymentadap
    private val mRecyclerListitems = java.util.ArrayList<Any>()
    private var productItems: MutableList<Rewardpointsbo>? = null
    internal lateinit var mLayoutManager: LinearLayoutManager

    private  fun onTransactionSubmitted() {
        toast("Transaction processing...")

    }

    override fun onTransactionCancelled() {
        toast("Transaction cancelled")

    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails) {
        when (transactionDetails.transactionStatus) {
            TransactionStatus.SUCCESS -> onTransactionSuccess()
            TransactionStatus.FAILURE -> onTransactionFailed()
            TransactionStatus.SUBMITTED -> onTransactionSubmitted()
        }
    }

    private  fun onTransactionSuccess() {

        val update = Dialog(this@Payment_Activity)
        update.requestWindowFeature(Window.FEATURE_NO_TITLE)
        update.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        val v = layoutInflater.inflate(R.layout.success_popup1, null)
        val updatebut = v.findViewById<View>(R.id.yes) as TextView
        update.setContentView(v)
        update.setCancelable(false)
        update.show()


        //titlename.text = "New Version 1.$Number"

        updatebut.setOnClickListener {
            //clickupdate = "true"
            update.dismiss()

        }

        Handler().postDelayed(Runnable {
            UpdateInfoTask().execute()
        },2000)





    }

    private  fun onAppNotFound() {
        toast("UPI App Not Found.")
    }



    private  fun onTransactionFailed() {
        toast("Transaction failed...")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_)
        stateload().execute()
        utils=Utils(this)


        imageButton13.setOnClickListener {
            finish()
        }

        mLayoutManager = LinearLayoutManager(this)
        recyclerlist.setLayoutManager(mLayoutManager)
        utils=Utils(this)
        productItems = java.util.ArrayList()


        itemsAdapter = Paymentadap(mRecyclerListitems, this, object :
            Paymentadap.OnItemClickListener {
            override fun OnItemClick(view: View, position: Int, viewType: Int) {
                val item = mRecyclerListitems[position] as Rewardpointsbo
               // Log.e("clickresp", "value")

                //clikffed();
            }
        })
        recyclerlist.adapter = itemsAdapter

        RewardpointsAsync().execute()


        imageButton13.setOnClickListener {
            finish()
        }


        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    if (editText.text.toString().isNotEmpty() && editText.text.toString().toInt()>=1&&remarks.text.toString().isNotEmpty()) {
                        button10.isEnabled = true
                        button10.setBackgroundColor(resources.getColor(R.color.nav_head))
                    }
                    else {
                        if(editText.text.toString().toInt()<=0){
                            toast("Amount should be maximum Rs.1")

                        }
                        button10.isEnabled = false
                        button10.setBackgroundColor(resources.getColor(R.color.nav_headdis))
                    }
                }
                catch (e:Exception){

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

               }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        remarks.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                try {
                    if (editText.text.toString().isNotEmpty() && editText.text.toString().toInt()>=1&&remarks.text.toString().isNotEmpty()) {
                        button10.isEnabled = true
                        button10.setBackgroundColor(resources.getColor(R.color.nav_head))
                    }
                    else {
                       // toast("Pleas")
                        button10.isEnabled = false
                        button10.setBackgroundColor(resources.getColor(R.color.nav_headdis))
                    }
                }
                catch (e:Exception){

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {

            }
        })

        button10.setOnClickListener {
        if (editText.text.toString()!="0"&&remarks.text.toString().isNotEmpty()){
            val rand = Random();
            val rand_int1 = rand.nextInt(1000000000);
            println("rand_int1 : " + rand_int1)
            tid=rand_int1.toString()
       /*     idval = dollar
            tid = rand_int1.toString()
            plan = dollar*/
            var amounttotal=""
            if(editText.text.toString().contains(".")){
                amounttotal=editText.text.toString()
            }
            else{
                amounttotal=editText.text.toString()+".00"
                println("amounttotal"+amounttotal)
            }

            val easyUpiPayment = EasyUpiPayment.Builder(this)
                .setPayeeVpa(autoCompleteTextView.selectedItem.toString())
                .setPayeeName("V3 Payment")
                .setTransactionId("$rand_int1")
                .setTransactionRefId("$rand_int1")
                .setDescription(remarks.text.toString())
                .setAmount(amounttotal)//"1.00
                .build();
            easyUpiPayment.startPayment();
            easyUpiPayment.setPaymentStatusListener(this@Payment_Activity)
        }
            else{

        }

        }



    }


    inner class UpdateInfoTask :
        AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            pDialo = ProgressDialog(this@Payment_Activity);
            pDialo.setMessage("Saving...");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            //Log.i("UpdateInfoTask", "started")
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg params: String?): String? {
            var result: String? = null
            val con =
                Connection()
            try {
                val jobj = JSONObject()
                jobj.put("amount",editText.text.toString())
                jobj.put("remark", remarks.text.toString())
                jobj.put("upiid",autoCompleteTextView.selectedItem.toString())
                jobj.put("tid", tid)
                jobj.put("type", "Add")
                jobj.put("uname", utils.loadName())

                //Log.i(
                   /* "check Input",
                    Appconstants.UPI + "    " + jobj.toString()
                )*/
                result = con.sendHttpPostjson2(Appconstants.UPI, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(resp: String?) {


            try {
                //Log.i("tabresp", resp + "")

                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        pDialo!!.dismiss()

                        Toast.makeText(
                            applicationContext,
                            "Payment successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@Payment_Activity, HomePage::class.java))
                    } else {
                        startActivity(Intent(this@Payment_Activity, HomePage::class.java))

                    }
                } else {
                    // retry.show();
                    pDialo!!.dismiss()
                    startActivity(Intent(this@Payment_Activity, HomePage::class.java))

                }
            } catch (e: Exception) {
                pDialo!!.dismiss()

                // retry.show();
                e.printStackTrace()
                startActivity(Intent(this@Payment_Activity, HomePage::class.java))

            }
        }
    }



    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@Payment_Activity);
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
                result = con.connStringResponse(Appconstants.UPI)
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
                            val states = json.getString("upi")
                            val statesid = json.getString("upi2")

                            if(states.isNotEmpty()){
                                arraystr.add(states)
                            }
                            if(statesid.isNotEmpty()){
                                arraystr.add(statesid)

                            }
                        }
                        pDialo.dismiss()
                        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                            this@Payment_Activity,
                            android.R.layout.simple_spinner_item,
                            arraystr
                        )
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        autoCompleteTextView.setAdapter(adapter);
                    } else {

                        pDialo.dismiss()


                        //Toast.makeText(this@Super_Salary, "Mobile Number already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {
                    pDialo.dismiss()

                    //Toast.makeText(applicationContext, "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {

                //Log.e("VALUE STATE", e.toString())
                pDialo.dismiss()

                e.printStackTrace()
                Toast.makeText(applicationContext, "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    inner class RewardpointsAsync : AsyncTask<String, String, String>() {
        override fun onPreExecute() {

            progressBar.setVisibility(View.VISIBLE)
            recyclerlist.visibility = View.GONE

        }

        override fun doInBackground(vararg strings: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("uname", utils.loadName())
                jobj.put("type", "List")
                Log.i(
                    "check Input",
                    Appconstants.UPI + "    " + jobj.toString()
                )
                result = con.sendHttpPostjson2(Appconstants.UPI, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            try {
              //  Log.e("rewardresp", resp)
            } catch (e: Exception) {
                ///Log.e("rewardrespcatch", e.toString())

            }

            //prog.setVisibility(View.GONE)
            recyclerlist.visibility = View.VISIBLE
            progressBar.setVisibility(View.GONE)


            var feed: JSONArray? = null
            val feed1: String
            try {
                if (resp != null) {
                    val obj = JSONArray(resp)
                    val objs = obj.getJSONObject(0)
                    val arrayLanguage = objs.getJSONArray("Response")

                    for (i in 0 until arrayLanguage.length()) {
                        val JO = arrayLanguage.get(i) as JSONObject
                        val username = JO.getString("username")
                        val todate = JO.getString("amount")
                        val name = JO.getString("remark")
                        val upiid = JO.getString("upiid")
                        val dtime = JO.getString("dtime")
                        val first_name = JO.getString("first_name")
                        val last_name = JO.getString("last_name")

                        val fullname=first_name+" "+last_name


                        try {


                            productItems!!.add(Rewardpointsbo(i.toString(), fullname, upiid,todate,name,dtime ,"", "", "", ""))


                        } catch (e: Exception) {
                            //Log.e("rewardrespnw", e.toString())
                            productItems!!.add(Rewardpointsbo(i.toString(), fullname, upiid,todate,name,dtime , "", "", "", ""))


                        }


                    }
                    mRecyclerListitems.clear()
                    mRecyclerListitems.addAll(productItems!!)
                    itemsAdapter.notifyDataSetChanged()
                    recyclerlist.setNestedScrollingEnabled(false);
                    utils.savePreferences("noti","seen")
                } else {

                    val reward = RewardpointsAsync()
                    reward.execute()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                //Log.e("E VALUE", e.toString())
                mRecyclerListitems.addAll(productItems!!)
                itemsAdapter.notifyDataSetChanged()
            }

        }
    }




    fun toast(msg:String){
        val toast=Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }
}
