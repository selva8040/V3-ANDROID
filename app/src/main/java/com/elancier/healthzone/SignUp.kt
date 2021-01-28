package com.elancier.healthzone

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.cloudinary.Cloudinary
import com.elancier.healthzone.Common.Appconstants
import com.elancier.healthzone.Common.Connection
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.current_address_list_item.*
import kotlinx.android.synthetic.main.personal_details_list_items.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SignUp : AppCompatActivity() {

    internal lateinit var picker: Dialog
    internal lateinit var datep: DatePicker
    internal lateinit var date: String
    internal lateinit var mobilenum: String
    internal lateinit var whatsnum: String
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
    internal lateinit var save: TextView
    internal lateinit var gender_error: TextView
    internal lateinit var lname: EditText
    internal lateinit var fname: EditText
    internal lateinit var dob: EditText
    internal lateinit var mname: EditText
    internal lateinit var sponcer_name: EditText
    internal lateinit var sponcer_fname: EditText
    internal lateinit var sponcer_name_text: TextView
    internal lateinit var sponcer_fname_text: TextView
    internal lateinit var mobile: EditText
    internal lateinit var whatsapp: EditText
    internal lateinit var pan_num: EditText
    internal lateinit var otp: EditText
    internal lateinit var male: RadioButton
    internal lateinit var female: RadioButton
    internal lateinit var others: RadioButton
    internal var checkedRadioButtonId: Int = 0
    internal lateinit var nominee_name: EditText
    internal lateinit var nominee_relationship: EditText
    internal lateinit var username: EditText
    internal lateinit var password: EditText
    internal lateinit var confirmpass: EditText
    internal lateinit var address: EditText
    internal lateinit var city: EditText
    internal lateinit var pincode: EditText
    internal var disrict: EditText? = null
    internal lateinit var country: AutoCompleteTextView
    internal lateinit var list1: MutableList<String>
    internal lateinit var list2: MutableList<String>
    internal lateinit var productItems_dist2: MutableList<String>
    internal lateinit var productItems_dist2tz: MutableList<String>
    lateinit var pDialo : ProgressDialog
    var cloud_name=""
    var api_key=""
    var api_secret=""

    internal lateinit var list3: MutableList<String>
    internal lateinit var list4: MutableList<String>
    internal var statevalue = ""
    internal var timezone = ""
    var photoFile: File?=null


    internal var dataAdapter: ArrayAdapter<String>? = null
    internal var dataAdapter1: ArrayAdapter<String>? = null
    internal lateinit var dataAdapter2: ArrayAdapter<String>
    internal lateinit var dataAdapter3: ArrayAdapter<String>

    internal lateinit var state: Spinner
    internal lateinit var cityspin: Spinner
    internal var spname = true
    internal var MobilePattern = "[0-9]{10}"
    internal var NamePattern = "[a-zA-Z ]+"
    internal var RelationPattern = "[a-zA-Z -]+"
    //String email1 = email.getText().toString().trim();
    internal var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val MY_PERMISSIONS_REQUEST_CODE = 100

    internal lateinit var personal_layout: LinearLayout
    internal lateinit var personal_inner_layout: LinearLayout
    internal lateinit var personal_header_layout: LinearLayout
    internal lateinit var personal_text: TextView
    internal var personal_check = false

    internal lateinit var nominee_layout: LinearLayout
    internal lateinit var nominee_inner_layout: LinearLayout
    internal lateinit var nominee_header_layout: LinearLayout
    internal lateinit var nominee_text: TextView
    internal var nominee_check = false

    internal lateinit var login_layout: LinearLayout
    internal lateinit var login_inner_layout: LinearLayout
    internal lateinit var login_header_layout: LinearLayout
    internal lateinit var pin_lay: LinearLayout
    internal lateinit var otp_lay: LinearLayout
    internal lateinit var login_text: TextView
    internal var login_check = false

    internal lateinit var current_layout: LinearLayout
    internal lateinit var current_inner_layout: LinearLayout
    internal lateinit var current_header_layout: LinearLayout
    internal lateinit var current_text: TextView
    internal var current_check = false
    internal var progbar: Dialog? = null
    internal var mfocus = 0
    internal lateinit var radiogrp: RadioGroup
    internal var checkval = 0
    internal lateinit var dialog: Dialog
    internal lateinit var dialog1:Dialog

    internal lateinit var lang: Spinner
    internal lateinit var utype: Spinner

    internal var Spon_Unique: String? = ""
    internal var Spon_Uname : String? = ""
    internal var Spon_Name  : String? = ""
    internal var Res_OTP = ""
    internal var mobile_confirm = false

    internal lateinit var dataaapter: ArrayAdapter<*>
    internal lateinit var typeadp: ArrayAdapter<*>
    val arr= ArrayList<String>()
    internal lateinit var langdata: MutableList<String>
    internal lateinit var adapter3: ArrayAdapter<*>
    internal lateinit var adapter4: ArrayAdapter<*>
    var urlink=""

    var frm=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val d = resources.getDrawable(R.drawable.menu_bar_bg)
        supportActionBar!!.setBackgroundDrawable(d)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.ncolor1)
            }
        }
        progbar = Dialog(this)
        progbar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progbar!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        progbar!!.setContentView(R.layout.load)
        progbar!!.setCancelable(false)

        try {
            Spon_Unique = intent.extras!!.getString("spon_unique")
            Spon_Uname = intent.extras!!.getString("spon_uname")
            Spon_Name = intent.extras!!.getString("spon_name")

        } catch (e: Exception) {

        }


        try{
            var frms=intent.extras
            frm=frms!!.getString("frm").toString()
        }
        catch (e: Exception){

        }


        list2 = ArrayList()
        list1 = ArrayList()
        productItems_dist2 = ArrayList()
        productItems_dist2tz = ArrayList()
        list3 = ArrayList()
        list4 = ArrayList()
        langdata = ArrayList()


        val jsoncntry = "[\n" +
                "  {\n" +
                "    \"Status\": \"Success\",\n" +
                "\"Response\":\n" +
                "[\n" +
                "{\"ctime\":\"Andorra - Andorra\",\"timezone\":\"Europe\\/Andorra\"},\n" +
                "{\"ctime\":\"United Arab Emirates - Dubai\",\"timezone\":\"Asia\\/Dubai\"},\n" +
                "{\"ctime\":\"Afghanistan - Kabul\",\"timezone\":\"Asia\\/Kabul\"},\n" +
                "{\"ctime\":\"Antigua and Barbuda - Antigua\",\"timezone\":\"America\\/Antigua\"},\n" +
                "{\"ctime\":\"Anguilla - Anguilla\",\"timezone\":\"America\\/Anguilla\"},\n" +
                "{\"ctime\":\"Albania - Tirane\",\"timezone\":\"Europe\\/Tirane\"},\n" +
                "{\"ctime\":\"Armenia - Yerevan\",\"timezone\":\"Asia\\/Yerevan\"},\n" +
                "{\"ctime\":\"Angola - Luanda\",\"timezone\":\"Africa\\/Luanda\"},\n" +
                "{\"ctime\":\"Antarctica - McMurdo\",\"timezone\":\"Antarctica\\/McMurdo\"},\n" +
                "{\"ctime\":\"Antarctica - Casey\",\"timezone\":\"Antarctica\\/Casey\"},\n" +
                "{\"ctime\":\"Antarctica - Davis\",\"timezone\":\"Antarctica\\/Davis\"},\n" +
                "{\"ctime\":\"Antarctica - DumontDUrville\",\"timezone\":\"Antarctica\\/DumontDUrville\"},\n" +
                "{\"ctime\":\"Antarctica - Mawson\",\"timezone\":\"Antarctica\\/Mawson\"},\n" +
                "{\"ctime\":\"Antarctica - Palmer\",\"timezone\":\"Antarctica\\/Palmer\"},\n" +
                "{\"ctime\":\"Antarctica - Rothera\",\"timezone\":\"Antarctica\\/Rothera\"},\n" +
                "{\"ctime\":\"Antarctica - Syowa\",\"timezone\":\"Antarctica\\/Syowa\"},\n" +
                "{\"ctime\":\"Antarctica - Troll\",\"timezone\":\"Antarctica\\/Troll\"},\n" +
                "{\"ctime\":\"Antarctica - Vostok\",\"timezone\":\"Antarctica\\/Vostok\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Buenos_Aires\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Cordoba\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Salta\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Jujuy\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Tucuman\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Catamarca\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/La_Rioja\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/San_Juan\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Mendoza\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/San_Luis\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Rio_Gallegos\"},\n" +
                "{\"ctime\":\"Argentina - Argentina\",\"timezone\":\"America\\/Argentina\\/Ushuaia\"},\n" +
                "{\"ctime\":\"American Samoa - Pago_Pago\",\"timezone\":\"Pacific\\/Pago_Pago\"},\n" +
                "{\"ctime\":\"Austria - Vienna\",\"timezone\":\"Europe\\/Vienna\"},\n" +
                "{\"ctime\":\"Australia - Lord_Howe\",\"timezone\":\"Australia\\/Lord_Howe\"},\n" +
                "{\"ctime\":\"Australia - Macquarie\",\"timezone\":\"Antarctica\\/Macquarie\"},\n" +
                "{\"ctime\":\"Australia - Hobart\",\"timezone\":\"Australia\\/Hobart\"},\n" +
                "{\"ctime\":\"Australia - Currie\",\"timezone\":\"Australia\\/Currie\"},\n" +
                "{\"ctime\":\"Australia - Melbourne\",\"timezone\":\"Australia\\/Melbourne\"},\n" +
                "{\"ctime\":\"Australia - Sydney\",\"timezone\":\"Australia\\/Sydney\"},\n" +
                "{\"ctime\":\"Australia - Broken_Hill\",\"timezone\":\"Australia\\/Broken_Hill\"},\n" +
                "{\"ctime\":\"Australia - Brisbane\",\"timezone\":\"Australia\\/Brisbane\"},\n" +
                "{\"ctime\":\"Australia - Lindeman\",\"timezone\":\"Australia\\/Lindeman\"},\n" +
                "{\"ctime\":\"Australia - Adelaide\",\"timezone\":\"Australia\\/Adelaide\"},\n" +
                "{\"ctime\":\"Australia - Darwin\",\"timezone\":\"Australia\\/Darwin\"},\n" +
                "{\"ctime\":\"Australia - Perth\",\"timezone\":\"Australia\\/Perth\"},\n" +
                "{\"ctime\":\"Australia - Eucla\",\"timezone\":\"Australia\\/Eucla\"},\n" +
                "{\"ctime\":\"Aruba - Aruba\",\"timezone\":\"America\\/Aruba\"},\n" +
                "{\"ctime\":\"Åland Islands - Mariehamn\",\"timezone\":\"Europe\\/Mariehamn\"},\n" +
                "{\"ctime\":\"Azerbaijan - Baku\",\"timezone\":\"Asia\\/Baku\"},\n" +
                "{\"ctime\":\"Bosnia and Herzegovina - Sarajevo\",\"timezone\":\"Europe\\/Sarajevo\"},\n" +
                "{\"ctime\":\"Barbados - Barbados\",\"timezone\":\"America\\/Barbados\"},\n" +
                "{\"ctime\":\"Bangladesh - Dhaka\",\"timezone\":\"Asia\\/Dhaka\"},\n" +
                "{\"ctime\":\"Belgium - Brussels\",\"timezone\":\"Europe\\/Brussels\"},\n" +
                "{\"ctime\":\"Burkina Faso - Ouagadougou\",\"timezone\":\"Africa\\/Ouagadougou\"},\n" +
                "{\"ctime\":\"Bulgaria - Sofia\",\"timezone\":\"Europe\\/Sofia\"},\n" +
                "{\"ctime\":\"Bahrain - Bahrain\",\"timezone\":\"Asia\\/Bahrain\"},\n" +
                "{\"ctime\":\"Burundi - Bujumbura\",\"timezone\":\"Africa\\/Bujumbura\"},\n" +
                "{\"ctime\":\"Benin - Porto-Novo\",\"timezone\":\"Africa\\/Porto-Novo\"},\n" +
                "{\"ctime\":\"Saint Barthélemy - St_Barthelemy\",\"timezone\":\"America\\/St_Barthelemy\"},\n" +
                "{\"ctime\":\"Bermuda - Bermuda\",\"timezone\":\"Atlantic\\/Bermuda\"},\n" +
                "{\"ctime\":\"Brunei Darussalam - Brunei\",\"timezone\":\"Asia\\/Brunei\"},\n" +
                "{\"ctime\":\"Bolivia, Plurinational State of - La_Paz\",\"timezone\":\"America\\/La_Paz\"},\n" +
                "{\"ctime\":\"Bonaire, Sint Eustatius and Saba - Kralendijk\",\"timezone\":\"America\\/Kralendijk\"},\n" +
                "{\"ctime\":\"Brazil - Noronha\",\"timezone\":\"America\\/Noronha\"},\n" +
                "{\"ctime\":\"Brazil - Belem\",\"timezone\":\"America\\/Belem\"},\n" +
                "{\"ctime\":\"Brazil - Fortaleza\",\"timezone\":\"America\\/Fortaleza\"},\n" +
                "{\"ctime\":\"Brazil - Recife\",\"timezone\":\"America\\/Recife\"},\n" +
                "{\"ctime\":\"Brazil - Araguaina\",\"timezone\":\"America\\/Araguaina\"},\n" +
                "{\"ctime\":\"Brazil - Maceio\",\"timezone\":\"America\\/Maceio\"},\n" +
                "{\"ctime\":\"Brazil - Bahia\",\"timezone\":\"America\\/Bahia\"},\n" +
                "{\"ctime\":\"Brazil - Sao_Paulo\",\"timezone\":\"America\\/Sao_Paulo\"},\n" +
                "{\"ctime\":\"Brazil - Campo_Grande\",\"timezone\":\"America\\/Campo_Grande\"},\n" +
                "{\"ctime\":\"Brazil - Cuiaba\",\"timezone\":\"America\\/Cuiaba\"},\n" +
                "{\"ctime\":\"Brazil - Santarem\",\"timezone\":\"America\\/Santarem\"},\n" +
                "{\"ctime\":\"Brazil - Porto_Velho\",\"timezone\":\"America\\/Porto_Velho\"},\n" +
                "{\"ctime\":\"Brazil - Boa_Vista\",\"timezone\":\"America\\/Boa_Vista\"},\n" +
                "{\"ctime\":\"Brazil - Manaus\",\"timezone\":\"America\\/Manaus\"},\n" +
                "{\"ctime\":\"Brazil - Eirunepe\",\"timezone\":\"America\\/Eirunepe\"},\n" +
                "{\"ctime\":\"Brazil - Rio_Branco\",\"timezone\":\"America\\/Rio_Branco\"},\n" +
                "{\"ctime\":\"Bahamas - Nassau\",\"timezone\":\"America\\/Nassau\"},\n" +
                "{\"ctime\":\"Bhutan - Thimphu\",\"timezone\":\"Asia\\/Thimphu\"},\n" +
                "{\"ctime\":\"Botswana - Gaborone\",\"timezone\":\"Africa\\/Gaborone\"},\n" +
                "{\"ctime\":\"Belarus - Minsk\",\"timezone\":\"Europe\\/Minsk\"},\n" +
                "{\"ctime\":\"Belize - Belize\",\"timezone\":\"America\\/Belize\"},\n" +
                "{\"ctime\":\"Canada - St_Johns\",\"timezone\":\"America\\/St_Johns\"},\n" +
                "{\"ctime\":\"Canada - Halifax\",\"timezone\":\"America\\/Halifax\"},\n" +
                "{\"ctime\":\"Canada - Glace_Bay\",\"timezone\":\"America\\/Glace_Bay\"},\n" +
                "{\"ctime\":\"Canada - Moncton\",\"timezone\":\"America\\/Moncton\"},\n" +
                "{\"ctime\":\"Canada - Goose_Bay\",\"timezone\":\"America\\/Goose_Bay\"},\n" +
                "{\"ctime\":\"Canada - Blanc-Sablon\",\"timezone\":\"America\\/Blanc-Sablon\"},\n" +
                "{\"ctime\":\"Canada - Toronto\",\"timezone\":\"America\\/Toronto\"},\n" +
                "{\"ctime\":\"Canada - Nipigon\",\"timezone\":\"America\\/Nipigon\"},\n" +
                "{\"ctime\":\"Canada - Thunder_Bay\",\"timezone\":\"America\\/Thunder_Bay\"},\n" +
                "{\"ctime\":\"Canada - Iqaluit\",\"timezone\":\"America\\/Iqaluit\"},\n" +
                "{\"ctime\":\"Canada - Pangnirtung\",\"timezone\":\"America\\/Pangnirtung\"},\n" +
                "{\"ctime\":\"Canada - Atikokan\",\"timezone\":\"America\\/Atikokan\"},\n" +
                "{\"ctime\":\"Canada - Winnipeg\",\"timezone\":\"America\\/Winnipeg\"},\n" +
                "{\"ctime\":\"Canada - Rainy_River\",\"timezone\":\"America\\/Rainy_River\"},\n" +
                "{\"ctime\":\"Canada - Resolute\",\"timezone\":\"America\\/Resolute\"},\n" +
                "{\"ctime\":\"Canada - Rankin_Inlet\",\"timezone\":\"America\\/Rankin_Inlet\"},\n" +
                "{\"ctime\":\"Canada - Regina\",\"timezone\":\"America\\/Regina\"},\n" +
                "{\"ctime\":\"Canada - Swift_Current\",\"timezone\":\"America\\/Swift_Current\"},\n" +
                "{\"ctime\":\"Canada - Edmonton\",\"timezone\":\"America\\/Edmonton\"},\n" +
                "{\"ctime\":\"Canada - Cambridge_Bay\",\"timezone\":\"America\\/Cambridge_Bay\"},\n" +
                "{\"ctime\":\"Canada - Yellowknife\",\"timezone\":\"America\\/Yellowknife\"},\n" +
                "{\"ctime\":\"Canada - Inuvik\",\"timezone\":\"America\\/Inuvik\"},\n" +
                "{\"ctime\":\"Canada - Creston\",\"timezone\":\"America\\/Creston\"},\n" +
                "{\"ctime\":\"Canada - Dawson_Creek\",\"timezone\":\"America\\/Dawson_Creek\"},\n" +
                "{\"ctime\":\"Canada - Fort_Nelson\",\"timezone\":\"America\\/Fort_Nelson\"},\n" +
                "{\"ctime\":\"Canada - Vancouver\",\"timezone\":\"America\\/Vancouver\"},\n" +
                "{\"ctime\":\"Canada - Whitehorse\",\"timezone\":\"America\\/Whitehorse\"},\n" +
                "{\"ctime\":\"Canada - Dawson\",\"timezone\":\"America\\/Dawson\"},\n" +
                "{\"ctime\":\"Cocos (Keeling) Islands - Cocos\",\"timezone\":\"Indian\\/Cocos\"},\n" +
                "{\"ctime\":\"Congo, the Democratic Republic of the - Kinshasa\",\"timezone\":\"Africa\\/Kinshasa\"},\n" +
                "{\"ctime\":\"Congo, the Democratic Republic of the - Lubumbashi\",\"timezone\":\"Africa\\/Lubumbashi\"},\n" +
                "{\"ctime\":\"Central African Republic - Bangui\",\"timezone\":\"Africa\\/Bangui\"},\n" +
                "{\"ctime\":\"Congo - Brazzaville\",\"timezone\":\"Africa\\/Brazzaville\"},\n" +
                "{\"ctime\":\"Switzerland - Zurich\",\"timezone\":\"Europe\\/Zurich\"},\n" +
                "{\"ctime\":\"Côte d'Ivoire - Abidjan\",\"timezone\":\"Africa\\/Abidjan\"},\n" +
                "{\"ctime\":\"Cook Islands - Rarotonga\",\"timezone\":\"Pacific\\/Rarotonga\"},\n" +
                "{\"ctime\":\"Chile - Santiago\",\"timezone\":\"America\\/Santiago\"},\n" +
                "{\"ctime\":\"Chile - Punta_Arenas\",\"timezone\":\"America\\/Punta_Arenas\"},\n" +
                "{\"ctime\":\"Chile - Easter\",\"timezone\":\"Pacific\\/Easter\"},\n" +
                "{\"ctime\":\"Cameroon - Douala\",\"timezone\":\"Africa\\/Douala\"},\n" +
                "{\"ctime\":\"China - Shanghai\",\"timezone\":\"Asia\\/Shanghai\"},\n" +
                "{\"ctime\":\"China - Urumqi\",\"timezone\":\"Asia\\/Urumqi\"},\n" +
                "{\"ctime\":\"Colombia - Bogota\",\"timezone\":\"America\\/Bogota\"},\n" +
                "{\"ctime\":\"Costa Rica - Costa_Rica\",\"timezone\":\"America\\/Costa_Rica\"},\n" +
                "{\"ctime\":\"Cuba - Havana\",\"timezone\":\"America\\/Havana\"},\n" +
                "{\"ctime\":\"Cape Verde - Cape_Verde\",\"timezone\":\"Atlantic\\/Cape_Verde\"},\n" +
                "{\"ctime\":\"Curaçao - Curacao\",\"timezone\":\"America\\/Curacao\"},\n" +
                "{\"ctime\":\"Christmas Island - Christmas\",\"timezone\":\"Indian\\/Christmas\"},\n" +
                "{\"ctime\":\"Cyprus - Nicosia\",\"timezone\":\"Asia\\/Nicosia\"},\n" +
                "{\"ctime\":\"Cyprus - Famagusta\",\"timezone\":\"Asia\\/Famagusta\"},\n" +
                "{\"ctime\":\"Czech Republic - Prague\",\"timezone\":\"Europe\\/Prague\"},\n" +
                "{\"ctime\":\"Germany - Berlin\",\"timezone\":\"Europe\\/Berlin\"},\n" +
                "{\"ctime\":\"Germany - Busingen\",\"timezone\":\"Europe\\/Busingen\"},\n" +
                "{\"ctime\":\"Djibouti - Djibouti\",\"timezone\":\"Africa\\/Djibouti\"},\n" +
                "{\"ctime\":\"Denmark - Copenhagen\",\"timezone\":\"Europe\\/Copenhagen\"},\n" +
                "{\"ctime\":\"Dominica - Dominica\",\"timezone\":\"America\\/Dominica\"},\n" +
                "{\"ctime\":\"Dominican Republic - Santo_Domingo\",\"timezone\":\"America\\/Santo_Domingo\"},\n" +
                "{\"ctime\":\"Algeria - Algiers\",\"timezone\":\"Africa\\/Algiers\"},\n" +
                "{\"ctime\":\"Ecuador - Guayaquil\",\"timezone\":\"America\\/Guayaquil\"},\n" +
                "{\"ctime\":\"Ecuador - Galapagos\",\"timezone\":\"Pacific\\/Galapagos\"},\n" +
                "{\"ctime\":\"Estonia - Tallinn\",\"timezone\":\"Europe\\/Tallinn\"},\n" +
                "{\"ctime\":\"Egypt - Cairo\",\"timezone\":\"Africa\\/Cairo\"},\n" +
                "{\"ctime\":\"Western Sahara - El_Aaiun\",\"timezone\":\"Africa\\/El_Aaiun\"},\n" +
                "{\"ctime\":\"Eritrea - Asmara\",\"timezone\":\"Africa\\/Asmara\"},\n" +
                "{\"ctime\":\"Spain - Madrid\",\"timezone\":\"Europe\\/Madrid\"},\n" +
                "{\"ctime\":\"Spain - Ceuta\",\"timezone\":\"Africa\\/Ceuta\"},\n" +
                "{\"ctime\":\"Spain - Canary\",\"timezone\":\"Atlantic\\/Canary\"},\n" +
                "{\"ctime\":\"Ethiopia - Addis_Ababa\",\"timezone\":\"Africa\\/Addis_Ababa\"},\n" +
                "{\"ctime\":\"Finland - Helsinki\",\"timezone\":\"Europe\\/Helsinki\"},\n" +
                "{\"ctime\":\"Fiji - Fiji\",\"timezone\":\"Pacific\\/Fiji\"},\n" +
                "{\"ctime\":\"Falkland Islands (Malvinas) - Stanley\",\"timezone\":\"Atlantic\\/Stanley\"},\n" +
                "{\"ctime\":\"Micronesia, Federated States of - Chuuk\",\"timezone\":\"Pacific\\/Chuuk\"},\n" +
                "{\"ctime\":\"Micronesia, Federated States of - Pohnpei\",\"timezone\":\"Pacific\\/Pohnpei\"},\n" +
                "{\"ctime\":\"Micronesia, Federated States of - Kosrae\",\"timezone\":\"Pacific\\/Kosrae\"},\n" +
                "{\"ctime\":\"Faroe Islands - Faroe\",\"timezone\":\"Atlantic\\/Faroe\"},\n" +
                "{\"ctime\":\"France - Paris\",\"timezone\":\"Europe\\/Paris\"},\n" +
                "{\"ctime\":\"Gabon - Libreville\",\"timezone\":\"Africa\\/Libreville\"},\n" +
                "{\"ctime\":\"United Kingdom - London\",\"timezone\":\"Europe\\/London\"},\n" +
                "{\"ctime\":\"Grenada - Grenada\",\"timezone\":\"America\\/Grenada\"},\n" +
                "{\"ctime\":\"Georgia - Tbilisi\",\"timezone\":\"Asia\\/Tbilisi\"},\n" +
                "{\"ctime\":\"French Guiana - Cayenne\",\"timezone\":\"America\\/Cayenne\"},\n" +
                "{\"ctime\":\"Guernsey - Guernsey\",\"timezone\":\"Europe\\/Guernsey\"},\n" +
                "{\"ctime\":\"Ghana - Accra\",\"timezone\":\"Africa\\/Accra\"},\n" +
                "{\"ctime\":\"Gibraltar - Gibraltar\",\"timezone\":\"Europe\\/Gibraltar\"},\n" +
                "{\"ctime\":\"Greenland - Godthab\",\"timezone\":\"America\\/Godthab\"},\n" +
                "{\"ctime\":\"Greenland - Danmarkshavn\",\"timezone\":\"America\\/Danmarkshavn\"},\n" +
                "{\"ctime\":\"Greenland - Scoresbysund\",\"timezone\":\"America\\/Scoresbysund\"},\n" +
                "{\"ctime\":\"Greenland - Thule\",\"timezone\":\"America\\/Thule\"},\n" +
                "{\"ctime\":\"Gambia - Banjul\",\"timezone\":\"Africa\\/Banjul\"},\n" +
                "{\"ctime\":\"Guinea - Conakry\",\"timezone\":\"Africa\\/Conakry\"},\n" +
                "{\"ctime\":\"Guadeloupe - Guadeloupe\",\"timezone\":\"America\\/Guadeloupe\"},\n" +
                "{\"ctime\":\"Equatorial Guinea - Malabo\",\"timezone\":\"Africa\\/Malabo\"},\n" +
                "{\"ctime\":\"Greece - Athens\",\"timezone\":\"Europe\\/Athens\"},\n" +
                "{\"ctime\":\"South Georgia and the South Sandwich Islands - South_Georgia\",\"timezone\":\"Atlantic\\/South_Georgia\"},\n" +
                "{\"ctime\":\"Guatemala - Guatemala\",\"timezone\":\"America\\/Guatemala\"},\n" +
                "{\"ctime\":\"Guam - Guam\",\"timezone\":\"Pacific\\/Guam\"},\n" +
                "{\"ctime\":\"Guinea-Bissau - Bissau\",\"timezone\":\"Africa\\/Bissau\"},\n" +
                "{\"ctime\":\"Guyana - Guyana\",\"timezone\":\"America\\/Guyana\"},\n" +
                "{\"ctime\":\"Hong Kong - Hong_Kong\",\"timezone\":\"Asia\\/Hong_Kong\"},\n" +
                "{\"ctime\":\"Honduras - Tegucigalpa\",\"timezone\":\"America\\/Tegucigalpa\"},\n" +
                "{\"ctime\":\"Croatia - Zagreb\",\"timezone\":\"Europe\\/Zagreb\"},\n" +
                "{\"ctime\":\"Haiti - Port-au-Prince\",\"timezone\":\"America\\/Port-au-Prince\"},\n" +
                "{\"ctime\":\"Hungary - Budapest\",\"timezone\":\"Europe\\/Budapest\"},\n" +
                "{\"ctime\":\"Indonesia - Jakarta\",\"timezone\":\"Asia\\/Jakarta\"},\n" +
                "{\"ctime\":\"Indonesia - Pontianak\",\"timezone\":\"Asia\\/Pontianak\"},\n" +
                "{\"ctime\":\"Indonesia - Makassar\",\"timezone\":\"Asia\\/Makassar\"},\n" +
                "{\"ctime\":\"Indonesia - Jayapura\",\"timezone\":\"Asia\\/Jayapura\"},\n" +
                "{\"ctime\":\"Ireland - Dublin\",\"timezone\":\"Europe\\/Dublin\"},\n" +
                "{\"ctime\":\"Israel - Jerusalem\",\"timezone\":\"Asia\\/Jerusalem\"},\n" +
                "{\"ctime\":\"Isle of Man - Isle_of_Man\",\"timezone\":\"Europe\\/Isle_of_Man\"},\n" +
                "{\"ctime\":\"British Indian Ocean Territory - Chagos\",\"timezone\":\"Indian\\/Chagos\"},\n" +
                "{\"ctime\":\"Iraq - Baghdad\",\"timezone\":\"Asia\\/Baghdad\"},\n" +
                "{\"ctime\":\"Iran, Islamic Republic of - Tehran\",\"timezone\":\"Asia\\/Tehran\"},\n" +
                "{\"ctime\":\"Iceland - Reykjavik\",\"timezone\":\"Atlantic\\/Reykjavik\"},\n" +
                "{\"ctime\":\"Italy - Rome\",\"timezone\":\"Europe\\/Rome\"},\n" +
                "{\"ctime\":\"Jersey - Jersey\",\"timezone\":\"Europe\\/Jersey\"},\n" +
                "{\"ctime\":\"Jamaica - Jamaica\",\"timezone\":\"America\\/Jamaica\"},\n" +
                "{\"ctime\":\"Jordan - Amman\",\"timezone\":\"Asia\\/Amman\"},\n" +
                "{\"ctime\":\"Japan - Tokyo\",\"timezone\":\"Asia\\/Tokyo\"},\n" +
                "{\"ctime\":\"Kenya - Nairobi\",\"timezone\":\"Africa\\/Nairobi\"},\n" +
                "{\"ctime\":\"Kyrgyzstan - Bishkek\",\"timezone\":\"Asia\\/Bishkek\"},\n" +
                "{\"ctime\":\"Cambodia - Phnom_Penh\",\"timezone\":\"Asia\\/Phnom_Penh\"},\n" +
                "{\"ctime\":\"Kiribati - Tarawa\",\"timezone\":\"Pacific\\/Tarawa\"},\n" +
                "{\"ctime\":\"Kiribati - Enderbury\",\"timezone\":\"Pacific\\/Enderbury\"},\n" +
                "{\"ctime\":\"Kiribati - Kiritimati\",\"timezone\":\"Pacific\\/Kiritimati\"},\n" +
                "{\"ctime\":\"Comoros - Comoro\",\"timezone\":\"Indian\\/Comoro\"},\n" +
                "{\"ctime\":\"Saint Kitts and Nevis - St_Kitts\",\"timezone\":\"America\\/St_Kitts\"},\n" +
                "{\"ctime\":\"Korea, Democratic People's Republic of - Pyongyang\",\"timezone\":\"Asia\\/Pyongyang\"},\n" +
                "{\"ctime\":\"Korea, Republic of - Seoul\",\"timezone\":\"Asia\\/Seoul\"},\n" +
                "{\"ctime\":\"Kuwait - Kuwait\",\"timezone\":\"Asia\\/Kuwait\"},\n" +
                "{\"ctime\":\"Cayman Islands - Cayman\",\"timezone\":\"America\\/Cayman\"},\n" +
                "{\"ctime\":\"Kazakhstan - Almaty\",\"timezone\":\"Asia\\/Almaty\"},\n" +
                "{\"ctime\":\"Kazakhstan - Qyzylorda\",\"timezone\":\"Asia\\/Qyzylorda\"},\n" +
                "{\"ctime\":\"Kazakhstan - Qostanay\",\"timezone\":\"Asia\\/Qostanay\"},\n" +
                "{\"ctime\":\"Kazakhstan - Aqtobe\",\"timezone\":\"Asia\\/Aqtobe\"},\n" +
                "{\"ctime\":\"Kazakhstan - Aqtau\",\"timezone\":\"Asia\\/Aqtau\"},\n" +
                "{\"ctime\":\"Kazakhstan - Atyrau\",\"timezone\":\"Asia\\/Atyrau\"},\n" +
                "{\"ctime\":\"Kazakhstan - Oral\",\"timezone\":\"Asia\\/Oral\"},\n" +
                "{\"ctime\":\"Lao People's Democratic Republic - Vientiane\",\"timezone\":\"Asia\\/Vientiane\"},\n" +
                "{\"ctime\":\"Lebanon - Beirut\",\"timezone\":\"Asia\\/Beirut\"},\n" +
                "{\"ctime\":\"Saint Lucia - St_Lucia\",\"timezone\":\"America\\/St_Lucia\"},\n" +
                "{\"ctime\":\"Liechtenstein - Vaduz\",\"timezone\":\"Europe\\/Vaduz\"},\n" +
                "{\"ctime\":\"Sri Lanka - Colombo\",\"timezone\":\"Asia\\/Colombo\"},\n" +
                "{\"ctime\":\"Liberia - Monrovia\",\"timezone\":\"Africa\\/Monrovia\"},\n" +
                "{\"ctime\":\"Lesotho - Maseru\",\"timezone\":\"Africa\\/Maseru\"},\n" +
                "{\"ctime\":\"Lithuania - Vilnius\",\"timezone\":\"Europe\\/Vilnius\"},\n" +
                "{\"ctime\":\"Luxembourg - Luxembourg\",\"timezone\":\"Europe\\/Luxembourg\"},\n" +
                "{\"ctime\":\"Latvia - Riga\",\"timezone\":\"Europe\\/Riga\"},\n" +
                "{\"ctime\":\"Libya - Tripoli\",\"timezone\":\"Africa\\/Tripoli\"},\n" +
                "{\"ctime\":\"Morocco - Casablanca\",\"timezone\":\"Africa\\/Casablanca\"},\n" +
                "{\"ctime\":\"Monaco - Monaco\",\"timezone\":\"Europe\\/Monaco\"},\n" +
                "{\"ctime\":\"Moldova, Republic of - Chisinau\",\"timezone\":\"Europe\\/Chisinau\"},\n" +
                "{\"ctime\":\"Montenegro - Podgorica\",\"timezone\":\"Europe\\/Podgorica\"},\n" +
                "{\"ctime\":\"Saint Martin (French part) - Marigot\",\"timezone\":\"America\\/Marigot\"},\n" +
                "{\"ctime\":\"Madagascar - Antananarivo\",\"timezone\":\"Indian\\/Antananarivo\"},\n" +
                "{\"ctime\":\"Marshall Islands - Majuro\",\"timezone\":\"Pacific\\/Majuro\"},\n" +
                "{\"ctime\":\"Marshall Islands - Kwajalein\",\"timezone\":\"Pacific\\/Kwajalein\"},\n" +
                "{\"ctime\":\"Macedonia, the Former Yugoslav Republic of - Skopje\",\"timezone\":\"Europe\\/Skopje\"},\n" +
                "{\"ctime\":\"Mali - Bamako\",\"timezone\":\"Africa\\/Bamako\"},\n" +
                "{\"ctime\":\"Myanmar - Yangon\",\"timezone\":\"Asia\\/Yangon\"},\n" +
                "{\"ctime\":\"Mongolia - Ulaanbaatar\",\"timezone\":\"Asia\\/Ulaanbaatar\"},\n" +
                "{\"ctime\":\"Mongolia - Hovd\",\"timezone\":\"Asia\\/Hovd\"},\n" +
                "{\"ctime\":\"Mongolia - Choibalsan\",\"timezone\":\"Asia\\/Choibalsan\"},\n" +
                "{\"ctime\":\"Macao - Macau\",\"timezone\":\"Asia\\/Macau\"},\n" +
                "{\"ctime\":\"Northern Mariana Islands - Saipan\",\"timezone\":\"Pacific\\/Saipan\"},\n" +
                "{\"ctime\":\"Martinique - Martinique\",\"timezone\":\"America\\/Martinique\"},\n" +
                "{\"ctime\":\"Mauritania - Nouakchott\",\"timezone\":\"Africa\\/Nouakchott\"},\n" +
                "{\"ctime\":\"Montserrat - Montserrat\",\"timezone\":\"America\\/Montserrat\"},\n" +
                "{\"ctime\":\"Malta - Malta\",\"timezone\":\"Europe\\/Malta\"},\n" +
                "{\"ctime\":\"Mauritius - Mauritius\",\"timezone\":\"Indian\\/Mauritius\"},\n" +
                "{\"ctime\":\"Maldives - Maldives\",\"timezone\":\"Indian\\/Maldives\"},\n" +
                "{\"ctime\":\"Malawi - Blantyre\",\"timezone\":\"Africa\\/Blantyre\"},\n" +
                "{\"ctime\":\"Mexico - Mexico_City\",\"timezone\":\"America\\/Mexico_City\"},\n" +
                "{\"ctime\":\"Mexico - Cancun\",\"timezone\":\"America\\/Cancun\"},\n" +
                "{\"ctime\":\"Mexico - Merida\",\"timezone\":\"America\\/Merida\"},\n" +
                "{\"ctime\":\"Mexico - Monterrey\",\"timezone\":\"America\\/Monterrey\"},\n" +
                "{\"ctime\":\"Mexico - Matamoros\",\"timezone\":\"America\\/Matamoros\"},\n" +
                "{\"ctime\":\"Mexico - Mazatlan\",\"timezone\":\"America\\/Mazatlan\"},\n" +
                "{\"ctime\":\"Mexico - Chihuahua\",\"timezone\":\"America\\/Chihuahua\"},\n" +
                "{\"ctime\":\"Mexico - Ojinaga\",\"timezone\":\"America\\/Ojinaga\"},\n" +
                "{\"ctime\":\"Mexico - Hermosillo\",\"timezone\":\"America\\/Hermosillo\"},\n" +
                "{\"ctime\":\"Mexico - Tijuana\",\"timezone\":\"America\\/Tijuana\"},\n" +
                "{\"ctime\":\"Mexico - Bahia_Banderas\",\"timezone\":\"America\\/Bahia_Banderas\"},\n" +
                "{\"ctime\":\"Malaysia - Kuala_Lumpur\",\"timezone\":\"Asia\\/Kuala_Lumpur\"},\n" +
                "{\"ctime\":\"Malaysia - Kuching\",\"timezone\":\"Asia\\/Kuching\"},\n" +
                "{\"ctime\":\"Mozambique - Maputo\",\"timezone\":\"Africa\\/Maputo\"},\n" +
                "{\"ctime\":\"Namibia - Windhoek\",\"timezone\":\"Africa\\/Windhoek\"},\n" +
                "{\"ctime\":\"New Caledonia - Noumea\",\"timezone\":\"Pacific\\/Noumea\"},\n" +
                "{\"ctime\":\"Niger - Niamey\",\"timezone\":\"Africa\\/Niamey\"},\n" +
                "{\"ctime\":\"Norfolk Island - Norfolk\",\"timezone\":\"Pacific\\/Norfolk\"},\n" +
                "{\"ctime\":\"Nigeria - Lagos\",\"timezone\":\"Africa\\/Lagos\"},\n" +
                "{\"ctime\":\"Nicaragua - Managua\",\"timezone\":\"America\\/Managua\"},\n" +
                "{\"ctime\":\"Netherlands - Amsterdam\",\"timezone\":\"Europe\\/Amsterdam\"},\n" +
                "{\"ctime\":\"Norway - Oslo\",\"timezone\":\"Europe\\/Oslo\"},\n" +
                "{\"ctime\":\"Nepal - Kathmandu\",\"timezone\":\"Asia\\/Kathmandu\"},\n" +
                "{\"ctime\":\"Nauru - Nauru\",\"timezone\":\"Pacific\\/Nauru\"},\n" +
                "{\"ctime\":\"Niue - Niue\",\"timezone\":\"Pacific\\/Niue\"},\n" +
                "{\"ctime\":\"New Zealand - Auckland\",\"timezone\":\"Pacific\\/Auckland\"},\n" +
                "{\"ctime\":\"New Zealand - Chatham\",\"timezone\":\"Pacific\\/Chatham\"},\n" +
                "{\"ctime\":\"Oman - Muscat\",\"timezone\":\"Asia\\/Muscat\"},\n" +
                "{\"ctime\":\"Panama - Panama\",\"timezone\":\"America\\/Panama\"},\n" +
                "{\"ctime\":\"Peru - Lima\",\"timezone\":\"America\\/Lima\"},\n" +
                "{\"ctime\":\"French Polynesia - Tahiti\",\"timezone\":\"Pacific\\/Tahiti\"},\n" +
                "{\"ctime\":\"French Polynesia - Marquesas\",\"timezone\":\"Pacific\\/Marquesas\"},\n" +
                "{\"ctime\":\"French Polynesia - Gambier\",\"timezone\":\"Pacific\\/Gambier\"},\n" +
                "{\"ctime\":\"Papua New Guinea - Port_Moresby\",\"timezone\":\"Pacific\\/Port_Moresby\"},\n" +
                "{\"ctime\":\"Papua New Guinea - Bougainville\",\"timezone\":\"Pacific\\/Bougainville\"},\n" +
                "{\"ctime\":\"Philippines - Manila\",\"timezone\":\"Asia\\/Manila\"},\n" +
                "{\"ctime\":\"Pakistan - Karachi\",\"timezone\":\"Asia\\/Karachi\"},\n" +
                "{\"ctime\":\"Poland - Warsaw\",\"timezone\":\"Europe\\/Warsaw\"},\n" +
                "{\"ctime\":\"Saint Pierre and Miquelon - Miquelon\",\"timezone\":\"America\\/Miquelon\"},\n" +
                "{\"ctime\":\"Pitcairn - Pitcairn\",\"timezone\":\"Pacific\\/Pitcairn\"},\n" +
                "{\"ctime\":\"Puerto Rico - Puerto_Rico\",\"timezone\":\"America\\/Puerto_Rico\"},\n" +
                "{\"ctime\":\"Palestine, State of - Gaza\",\"timezone\":\"Asia\\/Gaza\"},\n" +
                "{\"ctime\":\"Palestine, State of - Hebron\",\"timezone\":\"Asia\\/Hebron\"},\n" +
                "{\"ctime\":\"Portugal - Lisbon\",\"timezone\":\"Europe\\/Lisbon\"},\n" +
                "{\"ctime\":\"Portugal - Madeira\",\"timezone\":\"Atlantic\\/Madeira\"},\n" +
                "{\"ctime\":\"Portugal - Azores\",\"timezone\":\"Atlantic\\/Azores\"},\n" +
                "{\"ctime\":\"Palau - Palau\",\"timezone\":\"Pacific\\/Palau\"},\n" +
                "{\"ctime\":\"Paraguay - Asuncion\",\"timezone\":\"America\\/Asuncion\"},\n" +
                "{\"ctime\":\"Qatar - Qatar\",\"timezone\":\"Asia\\/Qatar\"},\n" +
                "{\"ctime\":\"Réunion - Reunion\",\"timezone\":\"Indian\\/Reunion\"},\n" +
                "{\"ctime\":\"Romania - Bucharest\",\"timezone\":\"Europe\\/Bucharest\"},\n" +
                "{\"ctime\":\"Serbia - Belgrade\",\"timezone\":\"Europe\\/Belgrade\"},\n" +
                "{\"ctime\":\"Russian Federation - Kaliningrad\",\"timezone\":\"Europe\\/Kaliningrad\"},\n" +
                "{\"ctime\":\"Russian Federation - Moscow\",\"timezone\":\"Europe\\/Moscow\"},\n" +
                "{\"ctime\":\"Ukraine - Simferopol\",\"timezone\":\"Europe\\/Simferopol\"},\n" +
                "{\"ctime\":\"Russian Federation - Kirov\",\"timezone\":\"Europe\\/Kirov\"},\n" +
                "{\"ctime\":\"Russian Federation - Astrakhan\",\"timezone\":\"Europe\\/Astrakhan\"},\n" +
                "{\"ctime\":\"Russian Federation - Volgograd\",\"timezone\":\"Europe\\/Volgograd\"},\n" +
                "{\"ctime\":\"Russian Federation - Saratov\",\"timezone\":\"Europe\\/Saratov\"},\n" +
                "{\"ctime\":\"Russian Federation - Ulyanovsk\",\"timezone\":\"Europe\\/Ulyanovsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Samara\",\"timezone\":\"Europe\\/Samara\"},\n" +
                "{\"ctime\":\"Russian Federation - Yekaterinburg\",\"timezone\":\"Asia\\/Yekaterinburg\"},\n" +
                "{\"ctime\":\"Russian Federation - Omsk\",\"timezone\":\"Asia\\/Omsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Novosibirsk\",\"timezone\":\"Asia\\/Novosibirsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Barnaul\",\"timezone\":\"Asia\\/Barnaul\"},\n" +
                "{\"ctime\":\"Russian Federation - Tomsk\",\"timezone\":\"Asia\\/Tomsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Novokuznetsk\",\"timezone\":\"Asia\\/Novokuznetsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Krasnoyarsk\",\"timezone\":\"Asia\\/Krasnoyarsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Irkutsk\",\"timezone\":\"Asia\\/Irkutsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Chita\",\"timezone\":\"Asia\\/Chita\"},\n" +
                "{\"ctime\":\"Russian Federation - Yakutsk\",\"timezone\":\"Asia\\/Yakutsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Khandyga\",\"timezone\":\"Asia\\/Khandyga\"},\n" +
                "{\"ctime\":\"Russian Federation - Vladivostok\",\"timezone\":\"Asia\\/Vladivostok\"},\n" +
                "{\"ctime\":\"Russian Federation - Ust-Nera\",\"timezone\":\"Asia\\/Ust-Nera\"},\n" +
                "{\"ctime\":\"Russian Federation - Magadan\",\"timezone\":\"Asia\\/Magadan\"},\n" +
                "{\"ctime\":\"Russian Federation - Sakhalin\",\"timezone\":\"Asia\\/Sakhalin\"},\n" +
                "{\"ctime\":\"Russian Federation - Srednekolymsk\",\"timezone\":\"Asia\\/Srednekolymsk\"},\n" +
                "{\"ctime\":\"Russian Federation - Kamchatka\",\"timezone\":\"Asia\\/Kamchatka\"},\n" +
                "{\"ctime\":\"Russian Federation - Anadyr\",\"timezone\":\"Asia\\/Anadyr\"},\n" +
                "{\"ctime\":\"Rwanda - Kigali\",\"timezone\":\"Africa\\/Kigali\"},\n" +
                "{\"ctime\":\"Saudi Arabia - Riyadh\",\"timezone\":\"Asia\\/Riyadh\"},\n" +
                "{\"ctime\":\"Solomon Islands - Guadalcanal\",\"timezone\":\"Pacific\\/Guadalcanal\"},\n" +
                "{\"ctime\":\"Seychelles - Mahe\",\"timezone\":\"Indian\\/Mahe\"},\n" +
                "{\"ctime\":\"Sudan - Khartoum\",\"timezone\":\"Africa\\/Khartoum\"},\n" +
                "{\"ctime\":\"Sweden - Stockholm\",\"timezone\":\"Europe\\/Stockholm\"},\n" +
                "{\"ctime\":\"Singapore - Singapore\",\"timezone\":\"Asia\\/Singapore\"},\n" +
                "{\"ctime\":\"Saint Helena, Ascension and Tristan da Cunha - St_Helena\",\"timezone\":\"Atlantic\\/St_Helena\"},\n" +
                "{\"ctime\":\"Slovenia - Ljubljana\",\"timezone\":\"Europe\\/Ljubljana\"},\n" +
                "{\"ctime\":\"Svalbard and Jan Mayen - Longyearbyen\",\"timezone\":\"Arctic\\/Longyearbyen\"},\n" +
                "{\"ctime\":\"Slovakia - Bratislava\",\"timezone\":\"Europe\\/Bratislava\"},\n" +
                "{\"ctime\":\"Sierra Leone - Freetown\",\"timezone\":\"Africa\\/Freetown\"},\n" +
                "{\"ctime\":\"San Marino - San_Marino\",\"timezone\":\"Europe\\/San_Marino\"},\n" +
                "{\"ctime\":\"Senegal - Dakar\",\"timezone\":\"Africa\\/Dakar\"},\n" +
                "{\"ctime\":\"Somalia - Mogadishu\",\"timezone\":\"Africa\\/Mogadishu\"},\n" +
                "{\"ctime\":\"Suriname - Paramaribo\",\"timezone\":\"America\\/Paramaribo\"},\n" +
                "{\"ctime\":\"South Sudan - Juba\",\"timezone\":\"Africa\\/Juba\"},\n" +
                "{\"ctime\":\"Sao Tome and Principe - Sao_Tome\",\"timezone\":\"Africa\\/Sao_Tome\"},\n" +
                "{\"ctime\":\"El Salvador - El_Salvador\",\"timezone\":\"America\\/El_Salvador\"},\n" +
                "{\"ctime\":\"Sint Maarten (Dutch part) - Lower_Princes\",\"timezone\":\"America\\/Lower_Princes\"},\n" +
                "{\"ctime\":\"Syrian Arab Republic - Damascus\",\"timezone\":\"Asia\\/Damascus\"},\n" +
                "{\"ctime\":\"Swaziland - Mbabane\",\"timezone\":\"Africa\\/Mbabane\"},\n" +
                "{\"ctime\":\"Turks and Caicos Islands - Grand_Turk\",\"timezone\":\"America\\/Grand_Turk\"},\n" +
                "{\"ctime\":\"Chad - Ndjamena\",\"timezone\":\"Africa\\/Ndjamena\"},\n" +
                "{\"ctime\":\"French Southern Territories - Kerguelen\",\"timezone\":\"Indian\\/Kerguelen\"},\n" +
                "{\"ctime\":\"Togo - Lome\",\"timezone\":\"Africa\\/Lome\"},\n" +
                "{\"ctime\":\"Thailand - Bangkok\",\"timezone\":\"Asia\\/Bangkok\"},\n" +
                "{\"ctime\":\"Tajikistan - Dushanbe\",\"timezone\":\"Asia\\/Dushanbe\"},\n" +
                "{\"ctime\":\"Tokelau - Fakaofo\",\"timezone\":\"Pacific\\/Fakaofo\"},\n" +
                "{\"ctime\":\"Timor-Leste - Dili\",\"timezone\":\"Asia\\/Dili\"},\n" +
                "{\"ctime\":\"Turkmenistan - Ashgabat\",\"timezone\":\"Asia\\/Ashgabat\"},\n" +
                "{\"ctime\":\"Tunisia - Tunis\",\"timezone\":\"Africa\\/Tunis\"},\n" +
                "{\"ctime\":\"Tonga - Tongatapu\",\"timezone\":\"Pacific\\/Tongatapu\"},\n" +
                "{\"ctime\":\"Turkey - Istanbul\",\"timezone\":\"Europe\\/Istanbul\"},\n" +
                "{\"ctime\":\"Trinidad and Tobago - Port_of_Spain\",\"timezone\":\"America\\/Port_of_Spain\"},\n" +
                "{\"ctime\":\"Tuvalu - Funafuti\",\"timezone\":\"Pacific\\/Funafuti\"},\n" +
                "{\"ctime\":\"Taiwan, Province of China - Taipei\",\"timezone\":\"Asia\\/Taipei\"},\n" +
                "{\"ctime\":\"Tanzania, United Republic of - Dar_es_Salaam\",\"timezone\":\"Africa\\/Dar_es_Salaam\"},\n" +
                "{\"ctime\":\"Ukraine - Kiev\",\"timezone\":\"Europe\\/Kiev\"},\n" +
                "{\"ctime\":\"Ukraine - Uzhgorod\",\"timezone\":\"Europe\\/Uzhgorod\"},\n" +
                "{\"ctime\":\"Ukraine - Zaporozhye\",\"timezone\":\"Europe\\/Zaporozhye\"},\n" +
                "{\"ctime\":\"Uganda - Kampala\",\"timezone\":\"Africa\\/Kampala\"},\n" +
                "{\"ctime\":\"United States Minor Outlying Islands - Midway\",\"timezone\":\"Pacific\\/Midway\"},\n" +
                "{\"ctime\":\"United States Minor Outlying Islands - Wake\",\"timezone\":\"Pacific\\/Wake\"},\n" +
                "{\"ctime\":\"United States - New_York\",\"timezone\":\"America\\/New_York\"},\n" +
                "{\"ctime\":\"United States - Detroit\",\"timezone\":\"America\\/Detroit\"},\n" +
                "{\"ctime\":\"United States - Kentucky\",\"timezone\":\"America\\/Kentucky\\/Louisville\"},\n" +
                "{\"ctime\":\"United States - Kentucky\",\"timezone\":\"America\\/Kentucky\\/Monticello\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Indianapolis\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Vincennes\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Winamac\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Marengo\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Petersburg\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Vevay\"},\n" +
                "{\"ctime\":\"United States - Chicago\",\"timezone\":\"America\\/Chicago\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Tell_City\"},\n" +
                "{\"ctime\":\"United States - Indiana\",\"timezone\":\"America\\/Indiana\\/Knox\"},\n" +
                "{\"ctime\":\"United States - Menominee\",\"timezone\":\"America\\/Menominee\"},\n" +
                "{\"ctime\":\"United States - North_Dakota\",\"timezone\":\"America\\/North_Dakota\\/Center\"},\n" +
                "{\"ctime\":\"United States - North_Dakota\",\"timezone\":\"America\\/North_Dakota\\/New_Salem\"},\n" +
                "{\"ctime\":\"United States - North_Dakota\",\"timezone\":\"America\\/North_Dakota\\/Beulah\"},\n" +
                "{\"ctime\":\"United States - Denver\",\"timezone\":\"America\\/Denver\"},\n" +
                "{\"ctime\":\"United States - Boise\",\"timezone\":\"America\\/Boise\"},\n" +
                "{\"ctime\":\"United States - Phoenix\",\"timezone\":\"America\\/Phoenix\"},\n" +
                "{\"ctime\":\"United States - Los_Angeles\",\"timezone\":\"America\\/Los_Angeles\"},\n" +
                "{\"ctime\":\"United States - Anchorage\",\"timezone\":\"America\\/Anchorage\"},\n" +
                "{\"ctime\":\"United States - Juneau\",\"timezone\":\"America\\/Juneau\"},\n" +
                "{\"ctime\":\"United States - Sitka\",\"timezone\":\"America\\/Sitka\"},\n" +
                "{\"ctime\":\"United States - Metlakatla\",\"timezone\":\"America\\/Metlakatla\"},\n" +
                "{\"ctime\":\"United States - Yakutat\",\"timezone\":\"America\\/Yakutat\"},\n" +
                "{\"ctime\":\"United States - Nome\",\"timezone\":\"America\\/Nome\"},\n" +
                "{\"ctime\":\"United States - Adak\",\"timezone\":\"America\\/Adak\"},\n" +
                "{\"ctime\":\"United States - Honolulu\",\"timezone\":\"Pacific\\/Honolulu\"},\n" +
                "{\"ctime\":\"Uruguay - Montevideo\",\"timezone\":\"America\\/Montevideo\"},\n" +
                "{\"ctime\":\"Uzbekistan - Samarkand\",\"timezone\":\"Asia\\/Samarkand\"},\n" +
                "{\"ctime\":\"Uzbekistan - Tashkent\",\"timezone\":\"Asia\\/Tashkent\"},\n" +
                "{\"ctime\":\"Holy See (Vatican City State) - Vatican\",\"timezone\":\"Europe\\/Vatican\"},\n" +
                "{\"ctime\":\"Saint Vincent and the Grenadines - St_Vincent\",\"timezone\":\"America\\/St_Vincent\"},\n" +
                "{\"ctime\":\"Venezuela, Bolivarian Republic of - Caracas\",\"timezone\":\"America\\/Caracas\"},\n" +
                "{\"ctime\":\"Virgin Islands, British - Tortola\",\"timezone\":\"America\\/Tortola\"},\n" +
                "{\"ctime\":\"Virgin Islands, U.S. - St_Thomas\",\"timezone\":\"America\\/St_Thomas\"},\n" +
                "{\"ctime\":\"Viet Nam - Ho_Chi_Minh\",\"timezone\":\"Asia\\/Ho_Chi_Minh\"},\n" +
                "{\"ctime\":\"Vanuatu - Efate\",\"timezone\":\"Pacific\\/Efate\"},\n" +
                "{\"ctime\":\"Wallis and Futuna - Wallis\",\"timezone\":\"Pacific\\/Wallis\"},\n" +
                "{\"ctime\":\"Samoa - Apia\",\"timezone\":\"Pacific\\/Apia\"},\n" +
                "{\"ctime\":\"Yemen - Aden\",\"timezone\":\"Asia\\/Aden\"},\n" +
                "{\"ctime\":\"Mayotte - Mayotte\",\"timezone\":\"Indian\\/Mayotte\"},\n" +
                "{\"ctime\":\"South Africa - Johannesburg\",\"timezone\":\"Africa\\/Johannesburg\"},\n" +
                "{\"ctime\":\"Zambia - Lusaka\",\"timezone\":\"Africa\\/Lusaka\"},\n" +
                "{\"ctime\":\"Zimbabwe - Harare\",\"timezone\":\"Africa\\/Harare\"}\n" +
                "]\n" +
                "}\n" +
                "  ]\n" +
                "\n"


        dataAdapter2 = ArrayAdapter(this, R.layout.spinner_item, list2)
        dataAdapter3 = ArrayAdapter(this, R.layout.spinner_item, list3)
        dataaapter = ArrayAdapter(this, R.layout.spinner_item, langdata)
        adapter3 = ArrayAdapter(
            this@SignUp,
            android.R.layout.select_dialog_item,
            productItems_dist2
        )
        adapter4 = ArrayAdapter(
            this@SignUp,
            android.R.layout.select_dialog_item,
            productItems_dist2tz
        )

        val task = stateload()
        task.execute("", "")

        val taskss = stateloads()
        taskss.execute("", "")

        init()

        try {
            onclick()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        var json: JSONArray? = null
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                json = JSONArray(jsoncntry)
            } else {
                json = JSONArray(jsoncntry)

            }
        } catch (e: JSONException) {
            Log.e("err", e.toString())
        }

        var obj1: JSONObject? = null
        try {
            assert(json != null)
            obj1 = json!!.getJSONObject(0)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        ind.setOnClickListener {
            country.setText("Asia/Kolkata")
            timezone = "Asia/Kolkata"
            println("Position " + productItems_dist2tz[195])
            country.isEnabled=false
            nri.isChecked=false
            contlay.visibility=View.GONE
            statelin.visibility=View.VISIBLE
            dictlin.visibility=View.VISIBLE
            pinlin.visibility=View.VISIBLE
        }
        nri.setOnClickListener {
            country.isEnabled=true
            contlay.visibility=View.VISIBLE
            ind.isChecked=false
            country.setText(null)
            statelin.visibility=View.GONE
            dictlin.visibility=View.GONE
            pinlin.visibility=View.GONE
            timezone="";
        }



        try {
            if (obj1!!.getString("Status") == "Success") {
                productItems_dist2.clear()
                productItems_dist2tz.clear()
                adapter3.clear()
                Log.e("inside", "ins")
                val jarr = obj1.getJSONArray("Response")

                for (i in 0 until jarr.length()) {

                    val jobj = jarr.getJSONObject(i)
                    val states = jobj.getString("ctime")
                    val statesid = jobj.getString("timezone")
                    productItems_dist2.add(states)
                    productItems_dist2tz.add(statesid)
                    country.threshold = 1 //will start working from first character
                    country.setAdapter(adapter3)
                }

            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        student_plan.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked==true){
                    utype.setSelection(2)
                    utype.isEnabled=false
                }
                else{
                    utype.setSelection(0)
                    utype.isEnabled=true
                }
            }
        }
        )


        /*country.setOnItemClickListener(object : AdapterView.OnItemClickListener {

            override fun onItemClick(
                parent: AdapterView<*>, arg1: View, pos: Int,
                id: Long
            ) {
                // Toast.makeText(this@Pintransfer, " selected", Toast.LENGTH_LONG).show()

                try {
                    println("position"+(timezone))

                }
                catch(e:Exception){

                }

            }
        })*/



        country.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {


            }

            override fun afterTextChanged(s: Editable) {


            }
        })


        cityspin.visibility = View.GONE

        val tasks = langload()
        tasks.execute("", "")

        sponcer_name.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    progbar!!.show()
                    val task = GetSponsername()
                    task.execute(Caption.text.toString().trim { it <= ' ' })
                } else {
                    sponcer_fname.setText("")
                    sponcer_name.setText("")
                }
                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                mfocus = 1
            }
        }
        mobile.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                //Log.i("valuessss", Caption.text.toString().trim { it <= ' ' }.length.toString() + "    " + Caption.text.toString().trim { it <= ' ' })
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {

                    progbar!!.show()
                    val task = CheckMobileTask()
                    task.execute(Caption.text.toString().trim { it <= ' ' })
                }

                // items.get(position).setPronotes( Caption.getText().toString());
                // dbCon.updateNotes(items.get(position).getPronotes(),items.get(position).getProid());
                // Log.i("parvalue",items.get(position).parameterlist.get(v.getId()).getParamvalue()+"       "+ v.getId());
            } else {
                mfocus = 1
            }
        }




        username.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    progbar!!.show()
                    val task = CheckUsernameTask()
                    task.execute(Caption.text.toString().trim { it <= ' ' })
                }
            } else {
                mfocus = 1
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
                }


            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }


        pan_num.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                mfocus = 0
                //  final int pos = v.getId();
                val Caption = v as EditText
                if (Caption.text.toString().trim { it <= ' ' }.length > 0) {
                    progbar!!.show()
                    val task = CheckPinNumberTask()
                    task.execute(Caption.text.toString().trim { it <= ' ' })
                }
            } else {
                mfocus = 1
            }
        }

        sponcer_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 1
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
        mobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 2
            }

            override fun afterTextChanged(s: Editable) {
                mobile_confirm = true

                if (same.isChecked) {
                    whatsapp.setText(mobile.text.toString())
                } else {
                    whatsapp.setText("")
                }
            }
        })
        same.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                whatsapp.setText(mobile.text.toString())
            }else{
                whatsapp.setText("")
            }
        }

        whatsapp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 5
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 3
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        pan_num.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 4
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        /*otp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkval = 4
            }

            override fun afterTextChanged(s: Editable) {

                if (s.length == 6) {
                    if (Res_OTP == s.toString().trim { it <= ' ' }) {
                        otp.isEnabled = false
                        mobile_confirm = true

                    } else {
                        Toast.makeText(this@SignUp, "please enter valid OTP", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        })*/

    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "capturedImage"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )

        // Save a file: path for use with ACTION_VIEW intents
        return File.createTempFile(
            imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir      /* directory */
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == Activity.RESULT_OK) {

                //Log.e("imagecode", imagecode)

                if (requestCode == 101) {
                    //Log.e("imagecode101", imagecode)


                    //Bitmap bitmaps=(Bitmap)data.getExtras().get("data");
                    try {
                        // var selectedImageUri = data!!.getData()

                        Get().execute()

                        //...


                        /* val bitmap = MediaStore.Images.Media.getBitmap(baseContext.contentResolver, selectedImageUri)
                         //imageone.setImageBitmap(bitmap)
                         //imageone.visibility = View.VISIBLE
                         val stream = ByteArrayOutputStream()
                         bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                         byteArray = stream.toByteArray()
                         imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                         imagecode = "data:image/png;base64,$imagecode"*/


                        /*Toast.makeText(getApplicationContext(),""+imagecode,Toast.LENGTH_SHORT).show();*/


                        //imagevw!!.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))


                        /*val path = getPathFromURI(selectedImageUri)
                        //getBase64FromPath(path);
                        if (path != null) {
                            val f = File(path)
                            selectedImageUri = Uri.fromFile(f)
                        }*/
                    } catch (e: Exception) {
                        Get().execute()


                        //cam.setVisibility(View.GONE);
                        //cam1.setVisibility(View.GONE);
                        //imagevalues = "selected";
                        //saveImage(thumbnail)
                    }

                    // Set the image in ImageView
                    //imageView.setImageURI(selectedImageUri);
                } else if (requestCode == 201) {
                    //Log.e("imagecode101", imagecode)


                    //Bitmap bitmaps=(Bitmap)data.getExtras().get("data");
                    try {
                        // var selectedImageUri = data!!.getData()
                        val selectedImage = data!!.getData()
                        val filePath = arrayOf<String>(MediaStore.Images.Media.DATA)
                        val c = getContentResolver().query(
                            selectedImage!!,
                            filePath,
                            null,
                            null,
                            null
                        )
                        c!!.moveToFirst()
                        val columnIndex = c.getColumnIndex(filePath[0])
                        val picturePath = c.getString(columnIndex)
                        photoFile = File(picturePath)
                        c.close()
                        Getpick().execute()

                        //...


                        /* val bitmap = MediaStore.Images.Media.getBitmap(baseContext.contentResolver, selectedImageUri)
                         //imageone.setImageBitmap(bitmap)
                         //imageone.visibility = View.VISIBLE
                         val stream = ByteArrayOutputStream()
                         bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
                         byteArray = stream.toByteArray()
                         imagecode = Base64.encodeToString(byteArray, Base64.DEFAULT)
                         imagecode = "data:image/png;base64,$imagecode"*/


                        /*Toast.makeText(getApplicationContext(),""+imagecode,Toast.LENGTH_SHORT).show();*/


                        //imagevw!!.setImageDrawable(resources.getDrawable(R.drawable.ic_checked))


                        /*val path = getPathFromURI(selectedImageUri)
                        //getBase64FromPath(path);
                        if (path != null) {
                            val f = File(path)
                            selectedImageUri = Uri.fromFile(f)
                        }*/
                    } catch (e: Exception) {

                        Log.e("pickrr", e.toString())
                        Getpick().execute()


                        //cam.setVisibility(View.GONE);
                        //cam1.setVisibility(View.GONE);
                        //imagevalues = "selected";
                        //saveImage(thumbnail)
                    }

                    // Set the image in ImageView
                    //imageView.setImageURI(selectedImageUri);
                }
            }
        }



        catch (e: Exception) {
            Log.e("FileSelectorActivity", "File select error", e)
        }

    }

    inner class Getpick : AsyncTask<String, Void, String>() {
        internal lateinit var pDialo : ProgressDialog
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            pDialo = ProgressDialog(this@SignUp);
            pDialo.setMessage("Uploading....");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            Log.i("LoginTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String = ""
            val config = HashMap<Any, Any>();
            config.put("cloud_name", cloud_name);
            config.put("api_key", api_key);
            config.put("api_secret", api_secret);
            val cloudinary = Cloudinary(config);
            try {

                val fi=cloudinary.uploader().upload(
                    photoFile!!.getAbsolutePath(),
                    HashMap<Any, Any>()
                );
                val k=fi.get("url")
                urlink=k.toString()
               // Log.e("fival",k.toString());

            } catch (e: IOException) {
                e.printStackTrace();
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            pDialo.dismiss()
            Picasso.with(this@SignUp).load(urlink).into(shop_img)

            Toast.makeText(applicationContext, "Image saved", Toast.LENGTH_SHORT).show()

            Toast.makeText(applicationContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show()


        }
    }

    private fun init() {

        save = findViewById<View>(R.id.save) as TextView
        personal_text = findViewById<View>(R.id.personal_text) as TextView
        nominee_text = findViewById<View>(R.id.nominee_text) as TextView
        login_text = findViewById<View>(R.id.login_text) as TextView
        country = findViewById<View>(R.id.country) as AutoCompleteTextView
        current_text = findViewById<View>(R.id.current_text) as TextView
        gender_error = findViewById<View>(R.id.gender_error) as TextView
        radiogrp = findViewById<View>(R.id.radiogrp) as RadioGroup
        personal_layout = findViewById<View>(R.id.personal_layout) as LinearLayout
        personal_header_layout = findViewById<View>(R.id.personal_header_layout) as LinearLayout
        personal_inner_layout = findViewById<View>(R.id.personal_inner_layout) as LinearLayout

        nominee_layout = findViewById<View>(R.id.nominee_layout) as LinearLayout
        nominee_header_layout = findViewById<View>(R.id.nominee_header_layout) as LinearLayout
        nominee_inner_layout = findViewById<View>(R.id.nominee_inner_layout) as LinearLayout
        lang = findViewById<View>(R.id.lang) as Spinner
        utype = findViewById<View>(R.id.utype) as Spinner

        arr.add("Select")
        arr.add("Blue")
        arr.add("Green")
        arr.add("Brown")
        typeadp = ArrayAdapter(this, R.layout.spinner_item, arr)
        typeadp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        utype.adapter = typeadp
        //utype.setSelection(1)
        //utype.isEnabled=false
        login_layout = findViewById<View>(R.id.login_layout) as LinearLayout
        login_header_layout = findViewById<View>(R.id.login_header_layout) as LinearLayout
        login_inner_layout = findViewById<View>(R.id.login_inner_layout) as LinearLayout
        pin_lay = findViewById<View>(R.id.pin_lay) as LinearLayout
        otp_lay = findViewById<View>(R.id.otp_lay) as LinearLayout

        current_layout = findViewById<View>(R.id.current_layout) as LinearLayout
        current_header_layout = findViewById<View>(R.id.current_header_layout) as LinearLayout
        current_inner_layout = findViewById<View>(R.id.current_inner_layout) as LinearLayout

        pin_lay.visibility = View.VISIBLE
        lname = findViewById<View>(R.id.lname) as EditText
        fname = findViewById<View>(R.id.fname) as EditText
        sponcer_name = findViewById<View>(R.id.sponcer_name) as EditText
        sponcer_fname = findViewById<View>(R.id.sponcer_fullname) as EditText


        sponcer_name_text= findViewById<View>(R.id.sponcer_name_text) as TextView
        sponcer_fname_text= findViewById<View>(R.id.sponcer_fname_text) as TextView

        dob = findViewById<View>(R.id.dob) as EditText
        mname = findViewById<View>(R.id.mname) as EditText
        mobile = findViewById<View>(R.id.mobile) as EditText
        whatsapp = findViewById<View>(R.id.mobilewhatasapp) as EditText
        pan_num = findViewById<View>(R.id.pan_num) as EditText
        otp = findViewById<View>(R.id.otp) as EditText
        // email = (EditText) findViewById(R.id.email);
        nominee_name = findViewById<View>(R.id.nominee_name) as EditText
        nominee_relationship = findViewById<View>(R.id.nominee_relationship) as EditText
        username = findViewById<View>(R.id.username) as EditText
        password = findViewById<View>(R.id.password) as EditText
        confirmpass = findViewById<View>(R.id.confirmpass) as EditText

        address = findViewById<View>(R.id.address) as EditText
        city = findViewById<View>(R.id.city) as EditText
        pincode = findViewById<View>(R.id.pincode) as EditText
        //disrict = (EditText) findViewById(R.id.dist);
        state = findViewById<View>(R.id.state) as Spinner
        cityspin = findViewById<View>(R.id.spincity) as Spinner
        radiogrp = findViewById<View>(R.id.radiogrp) as RadioGroup
        checkedRadioButtonId = radiogrp.checkedRadioButtonId


        male = findViewById<View>(R.id.male) as RadioButton
        female = findViewById<View>(R.id.female) as RadioButton
        others = findViewById<View>(R.id.others) as RadioButton

        sponcer_name.setText(Spon_Uname)
        sponcer_fname.setText(Spon_Name)


        if(frm.isNotEmpty()&&frm=="login"){
            pin_lay.visibility=View.GONE
            sponcer_name.visibility=View.GONE
            sponcer_fname.visibility=View.GONE
            sponcer_name_text.visibility=View.GONE
            sponcer_fname_text.visibility=View.GONE
        }

        /*if (!mobile_confirm) {
            nominee_layout.setClickable(false);
            login_layout.setClickable(false);
            current_layout.setClickable(false);
        }*/

        // Get current date by calender

        /* final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        cdate = day + "-" + month + "-" + year;
        dob.setText(cdate);*/

    }

    @Throws(JSONException::class)
    private fun onclick() {

        personal_header_layout.setOnClickListener {
            if (checkval == 1) {
                sponcer_name.clearFocus()
            } else if (checkval == 2) {
                mobile.clearFocus()
            } else if (checkval == 3) {
                username.clearFocus()
            } else if (checkval == 4) {
                pan_num.clearFocus()
            } else if (checkval == 5) {
                whatsapp.clearFocus()
            }


            if (!personal_check) {
                personal_layout.setBackgroundResource(R.drawable.orange_solid_bg)
                // personal_header_layout.setBackgroundResource(R.drawable.green_solid_bg);
                personal_inner_layout.visibility = View.VISIBLE
                personal_text.setTextColor(-0x1)
                personal_check = true
            } else {
                personal_layout.setBackgroundResource(R.drawable.grey_stroke_bg)
                // personal_header_layout.setBackgroundResource(R.drawable.white_solid_back);
                personal_inner_layout.visibility = View.GONE
                personal_text.setTextColor(-0xddddde)
                personal_check = false
            }
        }

        sponcer_name.setOnEditorActionListener { v, actionId, event ->
            if (event != null && actionId == EditorInfo.IME_ACTION_DONE) {
                // Log.i(TAG,"Enter pressed");
            }
            false
        }

        radiogrp.setOnCheckedChangeListener { group, checkedId ->
            if (checkval == 1) {
                sponcer_name.clearFocus()
            } else if (checkval == 2) {
                mobile.clearFocus()
            } else if (checkval == 3) {
                username.clearFocus()
            } else if (checkval == 4) {
                pan_num.clearFocus()
            } else if (checkval == 5) {
                whatsapp.clearFocus()
            }
        }

        nominee_header_layout.setOnClickListener {
            if (mobile_confirm) {


                if (checkval == 1) {
                    sponcer_name.clearFocus()
                } else if (checkval == 2) {
                    mobile.clearFocus()
                } else if (checkval == 3) {
                    username.clearFocus()
                } else if (checkval == 4) {
                    pan_num.clearFocus()
                } else if (checkval == 5) {
                    whatsapp.clearFocus()
                }
                if (nominee_check == false&&pan_num.text.toString().trim().isNotEmpty()) {
                    nominee_layout.setBackgroundResource(R.drawable.green_solid_bg)
                    //  personal_header_layout.setBackgroundResource(R.drawable.green_solid_bg);
                    nominee_inner_layout.visibility = View.VISIBLE
                    nominee_text.setTextColor(-0x1)
                    nominee_check = true
                } else {
                    if(pan_num.text.toString().trim().isEmpty()){
                        Toast.makeText(applicationContext, "Please enter pin", Toast.LENGTH_SHORT).show()
                    }
                    nominee_layout.setBackgroundResource(R.drawable.grey_stroke_bg)
                    // personal_header_layout.setBackgroundResource(R.drawable.white_solid_back);
                    nominee_inner_layout.visibility = View.GONE
                    nominee_text.setTextColor(-0xddddde)
                    nominee_check = false
                }
            }
        }

        login_header_layout.setOnClickListener {
            if (mobile_confirm) {

                if (checkval == 1) {
                    sponcer_name.clearFocus()
                } else if (checkval == 2) {
                    mobile.clearFocus()
                } else if (checkval == 3) {
                    username.clearFocus()
                } else if (checkval == 4) {
                    pan_num.clearFocus()
                } else if (checkval == 5) {
                    whatsapp.clearFocus()
                }
                if (login_check == false) {
                    login_layout.setBackgroundResource(R.drawable.blue_solid_bg)
                    //  personal_header_layout.setBackgroundResource(R.drawable.green_solid_bg);
                    login_inner_layout.visibility = View.VISIBLE
                    login_text.setTextColor(-0x1)
                    login_check = true
                } else {
                    login_layout.setBackgroundResource(R.drawable.grey_stroke_bg)
                    // personal_header_layout.setBackgroundResource(R.drawable.white_solid_back);
                    login_inner_layout.visibility = View.GONE
                    login_text.setTextColor(-0xddddde)
                    login_check = false
                }
            }
        }

        current_header_layout.setOnClickListener {
            if (mobile_confirm) {

                if (checkval == 1) {
                    sponcer_name.clearFocus()
                } else if (checkval == 2) {
                    mobile.clearFocus()
                } else if (checkval == 3) {
                    username.clearFocus()
                } else if (checkval == 4) {
                    pan_num.clearFocus()
                } else if (checkval == 5) {
                    whatsapp.clearFocus()
                }
                if (current_check == false) {
                    current_layout.setBackgroundResource(R.drawable.pink_solid_bg)
                    current_inner_layout.visibility = View.VISIBLE
                    current_text.setTextColor(-0x1)
                    current_check = true
                } else {
                    current_layout.setBackgroundResource(R.drawable.grey_stroke_bg)
                    current_inner_layout.visibility = View.GONE
                    current_text.setTextColor(-0xddddde)
                    current_check = false
                }
            }
        }

        dob.setOnClickListener {
            Log.i("checkfocusss", mfocus.toString() + "")
            if (mfocus == 1) {
                if (checkval == 1) {
                    sponcer_name.clearFocus()
                } else if (checkval == 2) {
                    mobile.clearFocus()
                } else if (checkval == 3) {
                    username.clearFocus()
                } else if (checkval == 4) {
                    pan_num.clearFocus()
                } else if (checkval == 5) {
                    whatsapp.clearFocus()
                }

            } else {
                picker = Dialog(this@SignUp)
                picker.setContentView(R.layout.datepicker)
                picker.setTitle("Select Date ")
                datep = picker.findViewById<View>(R.id.datePicker) as DatePicker
                datep.spinnersShown = true
                datep.calendarViewShown = false
                val df = SimpleDateFormat("dd-MM-yyyy")
                //String formattedDate = df.format(c.getTime());
                // Date d = df.parse(formattedDate);
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
                        Log.i("validddddd date",Integer.parseInt(s[2])+"     "+Integer.parseInt(s[1])+"    "+Integer.parseInt(s[0]));
                        datep.updateDate(Integer.parseInt(s[2]),Integer.parseInt(s[1])-1,Integer.parseInt(s[0]));

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

                    val age = getAge(year!!, month!!, day!!)

                    if(student_plan.isChecked==true) {
                        if (age.toInt() > 9 && age.toInt() <= 18) {
                            dob.setText(date)
                        } else {
                            Toast.makeText(
                                this!!,
                                "Age Should be above 10 and below 18 Only",
                                Toast.LENGTH_LONG
                            ).show()
                            dob.setError(null)
                            dob.setText(null)
                        }

                    }
                    else{
                         if (age.toInt() >= 18) {
                            dob.setText(date)
                        }
                         else {
                            Toast.makeText(
                                this!!,
                                "Age Should be above 18 Years",
                                Toast.LENGTH_LONG
                            ).show()
                             dob.setError(null)
                             dob.setText(null)
                        }
                    }
                    picker.dismiss()
                }
                picker.show()
            }
        }

        utype.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {

                if (utype.getSelectedItemPosition() == 0) {
                    //Toast.makeText(LiveFillBonus.this, "0", Toast.LENGTH_SHORT).show();
                    // bank_lay.setVisibility(View.GONE)
                    val tos = Toast.makeText(
                        applicationContext,
                        "Please select type",
                        Toast.LENGTH_SHORT
                    )
                    tos.setGravity(Gravity.CENTER, 0, 0)
                    tos.show()
                } else if (utype.getSelectedItemPosition() == 1) {

                    dialog = Dialog(this@SignUp)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.getWindow()!!.setBackgroundDrawable(
                        ColorDrawable(android.graphics.Color.WHITE)
                    )
                    val v = layoutInflater.inflate(R.layout.blue_green_popup, null)
                    val accept = v.findViewById<View>(R.id.acceptbutt) as Button
                    val decline = v.findViewById<View>(R.id.decbutt) as Button
                    val textView44 = v.findViewById<View>(R.id.textView44) as TextView
                    val textView40 = v.findViewById<View>(R.id.textView40) as TextView
                    val tb2 = v.findViewById<View>(R.id.tb2) as TableLayout
                    val tb1 = v.findViewById<View>(R.id.tb1) as TableLayout

                    textView40.visibility = View.VISIBLE
                    tb1.visibility = View.VISIBLE
                    textView44.visibility = View.GONE
                    tb2.visibility = View.GONE
                    accept.visibility = View.VISIBLE
                    decline.visibility = View.VISIBLE
                    dialog.setContentView(v)
                    dialog.setCancelable(false)
                    dialog.show()

                    accept.setOnClickListener {
                        dialog.dismiss()
                    }
                    decline.setOnClickListener {
                        dialog.dismiss()
                        utype.setSelection(0)

                    }

                } else if (utype.getSelectedItemPosition() == 2) {
                    // Toast.makeText(LiveFillBonus.this, "2", Toast.LENGTH_SHORT).show();
                    //bank_lay.setVisibility(View.VISIBLE)

                    dialog1 = Dialog(this@SignUp)
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog1.getWindow()!!.setBackgroundDrawable(
                        ColorDrawable(android.graphics.Color.WHITE)
                    )
                    val v = layoutInflater.inflate(R.layout.green_popup, null)
                    val accept = v.findViewById<View>(R.id.acceptbutt) as Button
                    val decline = v.findViewById<View>(R.id.decbutt) as Button
                    val textView44 = v.findViewById<View>(R.id.textView44) as TextView
                    //val textView40 = v.findViewById<View>(R.id.textView40) as TextView
                    val tb2 = v.findViewById<View>(R.id.tb2) as TableLayout
                    //val tb1 = v.findViewById<View>(R.id.tb1) as TableLayout


                    textView44.visibility = View.VISIBLE
                    tb2.visibility = View.VISIBLE
                    accept.visibility = View.VISIBLE
                    decline.visibility = View.VISIBLE
                    dialog1.setContentView(v)
                    dialog1.setCancelable(false)
                    dialog1.show()

                    accept.setOnClickListener {
                        dialog1.dismiss()
                    }
                    decline.setOnClickListener {
                        dialog1.dismiss()
                        utype.setSelection(0)

                    }
                }

                else if (utype.getSelectedItemPosition() == 3) {
                    // Toast.makeText(LiveFillBonus.this, "2", Toast.LENGTH_SHORT).show();
                    //bank_lay.setVisibility(View.VISIBLE)

                    dialog1 = Dialog(this@SignUp)
                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog1.getWindow()!!.setBackgroundDrawable(
                        ColorDrawable(android.graphics.Color.WHITE)
                    )
                    val v = layoutInflater.inflate(R.layout.brown_popup, null)
                    val accept = v.findViewById<View>(R.id.acceptbutt) as Button
                    val decline = v.findViewById<View>(R.id.decbutt) as Button
                    val textView44 = v.findViewById<View>(R.id.textView44) as TextView
                    //val textView40 = v.findViewById<View>(R.id.textView40) as TextView
                    val tb2 = v.findViewById<View>(R.id.tb2) as TableLayout
                    //val tb1 = v.findViewById<View>(R.id.tb1) as TableLayout


                    textView44.visibility = View.VISIBLE
                    tb2.visibility = View.VISIBLE
                    accept.visibility = View.VISIBLE
                    decline.visibility = View.VISIBLE
                    dialog1.setContentView(v)
                    dialog1.setCancelable(false)
                    dialog1.show()

                    accept.setOnClickListener {
                        dialog1.dismiss()
                    }
                    decline.setOnClickListener {
                        dialog1.dismiss()
                        utype.setSelection(0)

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        })

        country.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selection = parent.getItemAtPosition(position) as String
            var pos = -1

            for (i in productItems_dist2.indices) {
                if (productItems_dist2[i] == selection) {
                    pos = i
                    break
                }
            }
            println("slected timezone" + productItems_dist2tz[pos])
            timezone = productItems_dist2tz[pos]
             //check it now in Logcat
        }

        save.setOnClickListener {
            mobile.clearFocus()
            if (save.text.toString().trim { it <= ' ' }.equals("Submit", ignoreCase = true)) {
                /*   if(entered_number.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
                    Toast.makeText(MyDialog.this,"Please enter "+"\n"+" valid phone number",Toast.LENGTH_SHORT).show();
                    // am_checked=0;
                }`*/

                if (fname.text.toString().length <= 0) {
                    fname.error = "Enter Firstname"
                }
                if (fname.text.toString().length > 0 && !fname.text.toString().matches(NamePattern.toRegex())) {
                    fname.error = "Enter Characters Only"
                }
                if (lang.selectedItemPosition == 0) {
                    Toast.makeText(this@SignUp, "Please select language", Toast.LENGTH_SHORT).show()
                }

                if (lname.text.toString().length <= 0) {
                    lname.error = "Enter Lastname"
                }
                if (lname.text.toString().length > 0 && !lname.text.toString().matches(NamePattern.toRegex())) {
                    lname.error = "Enter Characters Only"
                }

                if (mname.text.toString().length <= 0) {
                    mname.error = "Enter Mothername"
                }
                if (mname.text.toString().length > 0 && !mname.text.toString().matches(NamePattern.toRegex())) {
                    mname.error = "Enter Characters Only"
                }
                /*if (sponcer_name.getText().toString().length() > 0 && !sponcer_name.getText().toString().matches(NamePattern)) {
                    sponcer_name.setError("Enter Characters Only");
                    spname =false;
                }else {
                    spname = true;
                }*/

                mobilenum = mobile.text.toString().trim { it <= ' ' }
                whatsnum = whatsapp.text.toString().trim { it <= ' ' }

                println("whatsappnum" + whatsapp.text.toString())
                println("whatsnum" + whatsnum)

                if (mobilenum.isEmpty() || !isValidMobile(mobile) || mobilenum.length < 10 || mobile.length() > 10) {
                    mobile.error = "Enter Valid Mob No"
                }

                if ((whatsnum.isEmpty() || !isValidMobile(whatsapp) || whatsnum.length < 10 || whatsapp.length() > 10)) {
                    whatsapp.error = "Enter Valid WhatsApp No"
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

                if (nominee_name.text.toString().length <= 0) {
                    nominee_name.error = "Enter Nominee name"
                }

                if (nominee_name.text.toString().length > 0 && !nominee_name.text.toString().matches(
                        NamePattern.toRegex()
                    )) {
                    nominee_name.error = "Enter Characters Only"
                }

                if (nominee_relationship.text.toString().length <= 0) {
                    nominee_relationship.error = "Enter Relationship"
                }

                if (nominee_relationship.text.toString().length > 0 && !nominee_relationship.text.toString().matches(
                        RelationPattern.toRegex()
                    )) {
                    nominee_relationship.error = "Enter Characters Only"
                }

                if (username.text.toString().length <= 0) {
                    username.error = "Enter Username"
                }

                if (username.text.toString().contains(" ")) {
                    username.error = "Remove Blank space"
                    Toast.makeText(
                        applicationContext,
                        "Username contains blankspace",
                        Toast.LENGTH_LONG
                    ).show()
                }

                if (password.text.toString().length <= 0 /*|| password.getText().toString().length() <= 7*/) {
                    password.error = "Enter Password"
                }

                if (confirmpass.text.toString().length <= 0 /*|| !confirmpass.getText().toString().equals( password.getText().toString())*/) {
                    confirmpass.error = "Enter Valid Password"
                }

                if (confirmpass.text.toString().length > 0 && confirmpass.text.toString() != password.text.toString()) {
                    confirmpass.error = "Password and Confirm Password must be same"
                }

                if (address.text.toString().length <= 0) {
                    address.error = "Enter Address"
                }

                if (city.text.toString().length <= 0 && cityspin.selectedItemPosition == 0) {
                    if (city.visibility == View.VISIBLE) {
                        city.error = "Enter City"
                    } else if (cityspin.visibility == View.VISIBLE) {
                        Toast.makeText(applicationContext, "Please select city", Toast.LENGTH_LONG).show()
                    }
                }

                if (city.text.toString().length > 0 && !city.text.toString().matches(NamePattern.toRegex()) && dictlin.visibility == View.VISIBLE) {
                    city.error = "Enter Characters Only"
                }
                if (country.text.toString().length == 0) {
                    country.error = "Enter Country"
                }

                if ((pincode.text.toString().trim { it <= ' ' }.length < 6 || pincode.text.toString().trim { it <= ' ' }.length > 6)&&pinlin.visibility==View.VISIBLE) {
                    pincode.error = "Enter Pincode"
                }

                /* if (disrict.getText().toString().length() <= 0) {
                        disrict.setError("Enter district");
                    }*/

                /*if (disrict.getText().toString().length() > 0 && !disrict.getText().toString().matches(NamePattern)) {
                        disrict.setError("Enter Characters Only");
                    }*/

                if (state.selectedItemPosition == 0&&statelin.visibility==View.VISIBLE) {

                    Toast.makeText(applicationContext, "Please select state", Toast.LENGTH_LONG).show()
                }

                /*if (state.getText().toString().length() > 0 && !state.getText().toString().matches(NamePattern)) {
                        state.setError("Enter Characters Only");
                    }*/

                /*if(fname.getText().toString().trim().length() <=0 || !fname.getText().toString().matches(NamePattern) || lname.getText().toString().trim().length() <=0
                        || !lname.getText().toString().matches(NamePattern) ||mname.getText().toString().trim().length() <=0 || !mname.getText().toString().matches(NamePattern) || (dob.getText().toString().length() <= 0)
                        || (!male.isChecked() && !female.isChecked() || !others.isChecked()) || (mobilenum.isEmpty() || !isValidMobile(mobile) || mobilenum.length() < 10 || mobile.length() > 10)){
                }*/

                if (fname.text.toString().length <= 0 || !fname.text.toString().matches(NamePattern.toRegex()) || lname.text.toString().length <= 0 || !lname.text.toString().matches(
                        NamePattern.toRegex()
                    ) ||
                        mname.text.toString().length <= 0 || !mname.text.toString().matches(
                        NamePattern.toRegex()
                    ) || dob.text.toString().length <= 0 || lang.selectedItemPosition == 0 ||
                        !male.isChecked && !female.isChecked && !others.isChecked || mobilenum.isEmpty() || !isValidMobile(
                        mobile
                    ) || mobilenum.length < 10 || mobile.length() > 10 || (whatsapp.text.toString().isNotEmpty()&&whatsnum.isEmpty() || !isValidMobile(
                        whatsapp
                    ) || whatsnum.length < 10 || whatsapp.length() > 10)) {
                    personal_layout.setBackgroundResource(R.drawable.red_solid_bg)
                    personal_text.setTextColor(-0xddddde)
                }

                if (nominee_name.text.toString().length <= 0 || !nominee_name.text.toString().matches(
                        NamePattern.toRegex()
                    )
                        || !nominee_relationship.text.toString().matches(RelationPattern.toRegex()) || nominee_relationship.text.toString().length <= 0) {
                    nominee_layout.setBackgroundResource(R.drawable.red_solid_bg)
                    nominee_text.setTextColor(-0xddddde)
                }

                if (username.text.toString().length <= 0 || password.text.toString().length <= 0 ||
                        confirmpass.text.toString().trim { it <= ' ' }.length <= 0 || confirmpass.text.toString() != password.text.toString()) {
                    login_layout.setBackgroundResource(R.drawable.red_solid_bg)
                    login_text.setTextColor(-0xddddde)
                }

                if (address.text.toString().trim { it <= ' ' }.length <= 0 || city.text.toString().trim { it <= ' ' }.length <= 0 && city.visibility == View.VISIBLE || !city.text.toString().matches(
                        NamePattern.toRegex()
                    ) && dictlin.visibility == View.VISIBLE
                        /*|| (disrict.getText().toString().trim().length() <= 0)*/ || (state.selectedItemPosition == 0 && ind.isChecked==true)
                        || pincode.text.toString().trim { it <= ' ' }.length < 6 ) {
                    current_layout.setBackgroundResource(R.drawable.red_solid_bg)
                    current_text.setTextColor(-0xddddde)
                }


                try {

                    if((fname.text.toString().trim { it <= ' ' }.length > 0 && fname.text.toString().matches(
                            NamePattern.toRegex()
                        ))){
                        println("fname")
                    }else{
                        println("emp fname")
                    }
                    if (lname.text.toString().trim { it <= ' ' }.length > 0 && lname.text.toString().matches(
                            NamePattern.toRegex()
                        )){
                        println("lname")
                    }else{
                        println("emp lname")
                    }
                    if (mname.text.toString().trim { it <= ' ' }.length > 0 && mname.text.toString().matches(
                            NamePattern.toRegex()
                        )){
                        println("mname")
                    }else{
                        println("emp mname")
                    }

                    if (username.text.toString().trim { it <= ' ' }.length > 0 ){
                        println("uname")
                    }else{
                        println("emp uname")
                    }

                    if (password.text.toString().trim { it <= ' ' }.length > 0){
                        println("pass")
                    }else{
                        println("emp pass")
                    }

                    if (dob.text.toString().trim { it <= ' ' }.length > 0){
                        println("dob")
                    }else{
                        println("emp dob")
                    }

                    if (confirmpass.text.toString().trim { it <= ' ' }.length > 0 && confirmpass.text.toString() == password.text.toString()){
                        println("cpass")
                    }else{
                        println("emp cpass")
                    }

                    if (nominee_name.text.toString().trim { it <= ' ' }.length > 0 && nominee_name.text.toString().matches(
                            NamePattern.toRegex()
                        )){
                        println("nname")
                    }else{
                        println("emp nname")
                    }

                    if (nominee_relationship.text.toString().trim { it <= ' ' }.length > 0 && nominee_relationship.text.toString().matches(
                            RelationPattern.toRegex()
                        )){
                        println("nrelat")
                    }else{
                        println("emp nrelat")
                    }

                    if (address.text.toString().trim { it <= ' ' }.length > 0 ){
                        println("adrs")
                    }else{
                        println("emp adrs")
                    }

                    if (city.text.toString().trim { it <= ' ' }.length > 0&& city.text.toString().matches(
                            NamePattern.toRegex()
                        ) && city.visibility == View.VISIBLE){
                        println("city")
                    }else{
                        println("emp city")
                    }

                    try {
                        if (cityspin.selectedItem.toString().matches(NamePattern.toRegex()) && cityspin.visibility == View.VISIBLE) {
                            println("cityspin")
                        } else {
                            println("emp cityspin")
                        }
                    }
                    catch (e: Exception){

                    }
                    try {
                    if (state.selectedItem.toString().matches(NamePattern.toRegex())&&(state.selectedItemPosition != 0 || cityspin.selectedItemPosition != 0)){
                        println("state")
                    }else{
                        println("emp state")
                    }
                }
                catch (e: Exception){

                }

                    if (state.selectedItem.toString().matches(NamePattern.toRegex())){
                        println("state")
                    }else{
                        println("emp state")
                    }

                    if (!timezone.isEmpty()){
                        println("timezone")
                    }else{
                        println("emp timezone")
                    }

                    if ( pincode.text.toString().trim { it <= ' ' }.length == 6 ){
                        println("pincode")
                    }else{
                        println("emp pincode")
                    }

                    if (mobile.text.toString().trim { it <= ' ' }.length == 10){
                        println("mobile")
                    }else{
                        println("emp mobile")
                    }

                    println("whatsappnum" + whatsapp.text.toString())
                    println("whatsnum" + whatsnum)

                    if (whatsapp.text.toString().trim { it <= ' ' }.length == 10 ){
                        println("whatsapp")
                    }else{
                        println("emp whatsapp")
                    }

                    if ((male.isChecked || female.isChecked || others.isChecked)){
                        println("gen")
                    }else{
                        println("emp gen")
                    }

                    if (lang.selectedItemPosition != 0){
                        println("lang")
                    }else{
                        println("emp lang")
                    }

                    if (utype.selectedItemPosition != 0){
                        println("utype")
                    }else{
                        println("emp utype")
                    }

                    println("whatsappnum" + whatsapp.text.toString())
                    println("whatsnum" + whatsnum)

                    if ((fname.text.toString().trim { it <= ' ' }.length > 0 && fname.text.toString().matches(
                            NamePattern.toRegex()
                        ) && lname.text.toString().trim { it <= ' ' }.length > 0
                                    && lname.text.toString().matches(NamePattern.toRegex()) && mname.text.toString().trim { it <= ' ' }.length > 0 && mname.text.toString().matches(
                            NamePattern.toRegex()
                        )
                                    && username.text.toString().trim { it <= ' ' }.length > 0 && password.text.toString().trim { it <= ' ' }.length > 0 && dob.text.toString().trim { it <= ' ' }.length > 0
                                    && confirmpass.text.toString().trim { it <= ' ' }.length > 0 && confirmpass.text.toString() == password.text.toString()
                                    && nominee_name.text.toString().trim { it <= ' ' }.length > 0 && nominee_name.text.toString().matches(
                            NamePattern.toRegex()
                        ) && nominee_relationship.text.toString().trim { it <= ' ' }.length > 0
                                    && nominee_relationship.text.toString().matches(RelationPattern.toRegex()) && address.text.toString().trim { it <= ' ' }.length > 0

                                    && (state.selectedItem.toString().matches(NamePattern.toRegex())||nri.isChecked==true)
                                    && (state.selectedItemPosition != 0 || cityspin.selectedItemPosition != 0||nri.isChecked==true) && !timezone.isEmpty() && (pincode.text.toString().trim { it <= ' ' }.length == 6||pinlin.visibility==View.GONE) && mobile.text.toString().trim { it <= ' ' }.length == 10
                                    && whatsapp.text.toString().trim { it <= ' ' }.length == 10 && (male.isChecked || female.isChecked || others.isChecked) && lang.selectedItemPosition != 0&& utype.selectedItemPosition != 0)) {

                        //Toast.makeText(SignUp.this, "Saved Sucessfully", Toast.LENGTH_SHORT).show();

                        /*Intent  intent = new Intent(SignUp.this,Login.class);
                    startActivity(intent);*/
                        var gendername = ""
                        var stateval: Int? = 0


                        if (male.isChecked) {
                            gendername = "Male"
                        } else if (female.isChecked) {
                            gendername = "Female"
                        } else if (others.isChecked) {
                            gendername = "Others"
                        }

                        if(dictlin.visibility==View.VISIBLE){
                            if((city.text.toString().trim { it <= ' ' }.length > 0 && city.text.toString().matches(
                                    NamePattern.toRegex()
                                ) && city.visibility == View.VISIBLE) || (cityspin.selectedItem.toString().matches(
                                    NamePattern.toRegex()
                                ) && cityspin.visibility == View.VISIBLE)){
                                if(whatsapp.text.toString().isNotEmpty()&&whatsapp.text.toString().trim { it <= ' ' }.length == 10) {

                                    progbar!!.show()

                                    save.text = "Processing..."
                                    val task = SignupTask()
                                    var cityval = ""
                                    if (dictlin.visibility == View.VISIBLE) {
                                        if(city.text.toString().isNotEmpty()) {
                                            cityval = city.text.toString().trim { it <= ' ' }
                                        }
                                        else{
                                            try {
                                                cityval = cityspin.selectedItem.toString()
                                            }
                                            catch (e: Exception){

                                            }
                                        }
                                    } else {
                                        cityval = cityspin.selectedItem.toString()
                                    }
                                    stateval = list1.indexOf(statevalue)

                                    task.execute(fname.text.toString().trim { it <= ' ' },
                                        lname.text.toString().trim { it <= ' ' },
                                        mname.text.toString().trim { it <= ' ' },
                                        sponcer_name.text.toString().trim { it <= ' ' },
                                        dob.text.toString().trim { it <= ' ' },
                                        mobile.text.toString().trim { it <= ' ' },
                                        gendername,
                                        nominee_name.text.toString().trim { it <= ' ' },
                                        nominee_relationship.text.toString().trim { it <= ' ' },
                                        username.text.toString().trim { it <= ' ' },
                                        password.text.toString().trim { it <= ' ' },
                                        address.text.toString().trim { it <= ' ' },
                                        cityval,
                                        pincode.text.toString().trim { it <= ' ' },
                                        "",
                                        stateval.toString(),
                                        pan_num.text.toString().trim { it <= ' ' },
                                        lang.selectedItem.toString(),
                                        timezone,
                                        whatsapp.text.toString().trim { it <= ' ' })
                                }
                                else{
                                    if(whatsapp.text.toString().isEmpty()) {
                                        Toast.makeText(
                                            this@SignUp,
                                            "Whatsapp number required",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    else{
                                        Toast.makeText(
                                            this@SignUp,
                                            "Enter valid whatsapp number",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            }
                        }
                        else if(whatsapp.text.toString().isNotEmpty()&&whatsapp.text.toString().trim { it <= ' ' }.length == 10) {

                            progbar!!.show()

                            save.text = "Processing..."
                            val task = SignupTask()
                            var cityval = ""
                            if (dictlin.visibility == View.VISIBLE) {
                                if(city.visibility==View.VISIBLE) {
                                    cityval = city.text.toString().trim { it <= ' ' }
                                }
                                else if(cityspin.visibility==View.VISIBLE){
                                    cityval = cityspin.selectedItem.toString()

                                }
                            } else {
                            }
                            stateval = list1.indexOf(statevalue)

                            task.execute(fname.text.toString().trim { it <= ' ' },
                                lname.text.toString().trim { it <= ' ' },
                                mname.text.toString().trim { it <= ' ' },
                                sponcer_name.text.toString().trim { it <= ' ' },
                                dob.text.toString().trim { it <= ' ' },
                                mobile.text.toString().trim { it <= ' ' },
                                gendername,
                                nominee_name.text.toString().trim { it <= ' ' },
                                nominee_relationship.text.toString().trim { it <= ' ' },
                                username.text.toString().trim { it <= ' ' },
                                password.text.toString().trim { it <= ' ' },
                                address.text.toString().trim { it <= ' ' },
                                cityval,
                                pincode.text.toString().trim { it <= ' ' },
                                "",
                                stateval.toString(),
                                pan_num.text.toString().trim { it <= ' ' },
                                lang.selectedItem.toString(),
                                timezone,
                                whatsapp.text.toString().trim { it <= ' ' })
                        }
                        else{
                            if(whatsapp.text.toString().isEmpty()) {
                                Toast.makeText(
                                    this@SignUp,
                                    "Whatsapp number required",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            else{
                                Toast.makeText(
                                    this@SignUp,
                                    "Enter valid whatsapp number",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    } else {
                        Toast.makeText(this@SignUp, "Please Fill Above Details", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("errorsave", e.toString())
                    Toast.makeText(this@SignUp, "Please fill all data", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun getAge(year: Int, month: Int, day: Int): String {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.set(year, month, day)

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        val ageInt = age

        return ageInt.toString()
    }

    private fun isValidMobile(mobile: EditText): Boolean {

        return Patterns.PHONE.matcher(mobile.text.toString().trim { it <= ' ' }).matches()
    }

    private fun isValidEmail(username: EditText): Boolean {

        return Patterns.EMAIL_ADDRESS.matcher(username.text.toString().trim { it <= ' ' }).matches()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == android.R.id.home) {
            super.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    private inner class GetSponsername : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("GetSponsername", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("sponsor", param[0])
                jobj.put("type", "sponsor")

               // Log.i("check Input", Appconstants.CORE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
           // Log.i("tabresp", resp!! + "")

            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        /*JSONArray jarr = obj1.getJSONArray("Response");
                        JSONObject obj=jarr.getJSONObject(0);*/
                        sponcer_fname.setText(obj1.getString("Response"))
                        sponcer_name.clearFocus()
                        if (mobile.text.toString().trim { it <= ' ' }.length == 0) {
                            mobile.requestFocus()
                        } else {
                            nominee_name.requestFocus()
                        }

                        if (whatsapp.text.toString().trim { it <= ' ' }.length == 0) {
                            whatsapp.requestFocus()
                        } else {
                            nominee_name.requestFocus()
                        }
                        checkval = 0


                    } else {
                        sponcer_name.setText("")
                        sponcer_fname.setText("")
                        sponcer_name.requestFocus()
                        Toast.makeText(
                            applicationContext,
                            "Enter correct sponsor name.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    sponcer_name.setText("")
                    sponcer_fname.setText("")
                    sponcer_name.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                sponcer_name.setText("")
                sponcer_fname.setText("")
                sponcer_name.requestFocus()
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }

    private inner class stateload : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
           // Log.i("Checkstate", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {


                //Log.i("check Input", Appconstants.CORE + "    " + jobj.toString());
                result = con.connStringResponse(Appconstants.STATE)
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
           // Log.i("stateresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }
            try {
                if (resp != null) {

                    list2.clear()
                    list1.clear()
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
                            Log.e("VALUE STATE", states)
                        }
                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {


                        Toast.makeText(
                            this@SignUp,
                            "Mobile Number already exists.",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {

                Log.e("VALUE STATE", e.toString())

                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }


    /*private class countryload extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("Checkstate", "started");
        }

        protected String doInBackground(String... param) {
            String result = null;
            Connection con = new Connection();

            try {


                //Log.i("check Input", Appconstants.CORE + "    " + jobj.toString());
                result = con.connStringResponse(Appconstants.LANG);
			JSONObject json = new JSONObject();
			json.put("type","Country");
			json.put("search", param[1]);
			result=con.sendHttpPostjson2(Appconstants.CORE, json, "");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(String resp) {
            Log.i("stateresp", resp + "");
            if (progbar.isShowing() && progbar != null) {
                progbar.dismiss();
            }
            try {
                if (resp != null) {

                    productItems_dist2.clear();
                            productItems_dist2tz.clear();
                            try{
                                adapter3.clear();
                            }
                            catch (Exception e){

                            }
                    JSONArray json = new JSONArray(resp);
                    JSONObject obj1 = json.getJSONObject(0);
                    if (obj1.getString("Status").equals("Success")) {
                        JSONArray jarr = obj1.getJSONArray("Response");
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject jobj = jarr.getJSONObject(i);

                            String states = jobj.getString("country");
                            String statesid = jobj.getString("timezone");

                            productItems_dist2.add(states);
                            productItems_dist2tz.add(statesid);

                            country.setThreshold(1); //will start working from first character
                            country.setAdapter(adapter3);
                            Log.e("VALUE STATE",states);
                        }
                        //JSONObject jarr = obj1.getJSONObject("Response");




                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {


                        Toast.makeText(SignUp.this, "Mobile Number already exists.", Toast.LENGTH_LONG).show();


                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Oops! Something went wrong please try again.", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {

                Log.e("VALUE STATE",e.toString());

                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();

            }


        }
    }*/

    private inner class langload : AsyncTask<String, Void, String>() {

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
                    jobj.put("type", "language")


                   // Log.i("checkInput", Appconstants.LANG + "    " + jobj.toString())
                    result = con.sendHttpPostjson2(Appconstants.LANG, jobj, "")

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
           // Log.i("cityresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }
            try {
                if (resp != null) {


                    langdata.clear()
                    langdata.add("Select")

                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        val jarr = obj1.getJSONArray("Response")
                        for (i in 0 until jarr.length()) {
                            val jobj = jarr.getJSONObject(i)

                            val cities = jobj.getString("name")
                            //String citiesid = jobj.getString("id");

                            langdata.add(cities)
                            dataaapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            lang.adapter = dataaapter
                            Log.e("VALUE STATE", cities)
                        }

                    } else {


                        Toast.makeText(
                            this@SignUp,
                            "Mobile Number already exists.",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {

                Log.e("VALUE STATE", e.toString())

                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
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


                    //Log.i("checkInput", Appconstants.CITY + "    " + jobj.toString())
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
           // Log.i("cityresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
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
                            Log.e("VALUE STATE", cities)
                        }
                        //JSONObject jarr = obj1.getJSONObject("Response");


                        //nominee_name.requestFocus();
                        // checkval=0;
                    } else {


                        Toast.makeText(
                            this@SignUp,
                            "Mobile Number already exists.",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {

                Log.e("VALUE STATE", e.toString())

                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }

    inner class Get : AsyncTask<String, Void, String>() {
        internal lateinit var pDialo : ProgressDialog
        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            pDialo = ProgressDialog(this@SignUp);
            pDialo.setMessage("Uploading....");
            pDialo.setIndeterminate(false);
            pDialo.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialo.setCancelable(false);
            //pDialo.setMax(3)
            pDialo.show()
            Log.i("LoginTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String = ""
            val config = HashMap<Any, Any>();
            config.put("cloud_name", cloud_name);
            config.put("api_key", api_key);
            config.put("api_secret", api_secret);
            val cloudinary = Cloudinary(config);
            try {
                val fi=cloudinary.uploader().upload(
                    photoFile!!.getAbsolutePath(),
                    HashMap<Any, Any>()
                );
                val k=fi.get("url")
                urlink=k.toString()
               // Log.e("fival",k.toString());

            } catch (e: IOException) {
                e.printStackTrace();
            }

            return result
        }

        override fun onPostExecute(resp: String?) {
            pDialo.dismiss()

            Picasso.with(this@SignUp).load(urlink).into(shop_img)
            Toast.makeText(applicationContext, "Image saved", Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext, "Uploaded Successfully", Toast.LENGTH_SHORT).show()


        }
    }


    private inner class CheckMobileTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckMobileTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("mobile", param[0])
                jobj.put("type", "mobile")

               // Log.i("check Input", Appconstants.CORE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "")
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
            //Log.i("mobresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }
            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        //JSONObject jarr = obj1.getJSONObject("Response");
                       mobile.clearFocus()


                        //Res_OTP = obj1.getString("Response")

                       /* if (pan_num.text.toString().trim { it <= ' ' }.length == 0) {
                            pan_num.requestFocus()
                        } else {
                            nominee_name.requestFocus()
                        }
                        checkval = 0*/
                        //nominee_name.requestFocus();
                         checkval=0;
                    } else {
                        mobile.requestFocus()
                        mobile.setText("")

                        Toast.makeText(
                            this@SignUp,
                            "Mobile Number already exists.",
                            Toast.LENGTH_LONG
                        ).show()


                    }
                } else {
                    mobile.requestFocus()
                    mobile.setText("")
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                mobile.requestFocus()
                mobile.setText("")
                e.printStackTrace()
                Toast.makeText(applicationContext, "Something went wrong.", Toast.LENGTH_SHORT).show()

            }


        }
    }


    private inner class CheckUsernameTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckUsernameTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("username", param[0])
                jobj.put("type", "username")

             //   Log.i("check Input", Appconstants.CORE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "")
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
            //Log.i("tabresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        //JSONObject jarr = obj1.getJSONObject("Response");
                        username.clearFocus()
                        password.requestFocus()
                        checkval = 0

                    } else {
                        username.setText("")
                        username.requestFocus()
                        Toast.makeText(this@SignUp, "Username already exists.", Toast.LENGTH_LONG).show()


                    }
                } else {
                    username.setText("")
                    username.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                username.setText("")
                username.requestFocus()
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }

    private inner class CheckPinNumberTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("CheckUsernameTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                jobj.put("pin", param[0])
                jobj.put("type", "pincheck")

                if(student_plan.isChecked==true){
                    jobj.put("student", "1")

                }
                else if(student_plan.isChecked==false)
                {
                    jobj.put("student", "0")

                }
                Log.i("check Input", Appconstants.pinchecks + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.pinchecks, jobj, "")
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
            Log.i("tabresp", resp!! + "")
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {
                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {
                        //JSONObject jarr = obj1.getJSONObject("Response");
                        pan_num.clearFocus()
                        nominee_name.requestFocus()
                        checkval = 0



                    } else {
                        pan_num.setText("")
                        pan_num.requestFocus()
                        Toast.makeText(this@SignUp, obj1.getString("Response"), Toast.LENGTH_LONG).show()


                    }
                } else {
                    pan_num.setText("")
                    pan_num.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                pan_num.setText("")
                pan_num.requestFocus()
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }

    private inner class SignupTask : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            Log.i("SignupTask", "started")
        }

        override fun doInBackground(vararg param: String): String? {
            var result: String? = null
            val con = Connection()

            try {
                val jobj = JSONObject()
                if (intent.extras!!.getString("frm")=="login") {
                    jobj.put("from", "Signup")
                }
                if (intent.extras!!.getString("frm")!="login") {
                    jobj.put("from", "Tree")
                }




                jobj.put("fname", param[0])
                jobj.put("lname", param[1])
                jobj.put("mname", param[2])
                jobj.put("suser", param[3])
                jobj.put("dob", param[4])
                jobj.put("mobile", param[5])
                jobj.put("gender", param[6])
                jobj.put("nname", param[7])
                jobj.put("relationship", param[8])
                jobj.put("username", param[9])
                jobj.put("password", param[10])
                jobj.put("address", param[11])
                jobj.put("city", param[12])
                jobj.put("pincode", param[13])
                jobj.put("district", param[14])
                jobj.put("state", param[15])
                jobj.put("pinno", param[16])
                jobj.put("language", param[17])
                jobj.put("country", param[18])
                jobj.put("whatsup", param[19])
                jobj.put("image", urlink)

                if((utype.selectedItemPosition) ==1) {
                    jobj.put("utype", "0")

                }
                else if(utype.selectedItemPosition==2){
                    jobj.put("utype", "1")

                }
                else if(utype.selectedItemPosition==3){
                    jobj.put("utype", "2")

                }
                jobj.put("type", "register")

                if(student_plan.isChecked==true){
                    jobj.put("student", "1")
                }
                else if(student_plan.isChecked==false)
                {
                    jobj.put("student", "0")
                }
                Log.i("Submit", Appconstants.CORE + "    " + jobj.toString())
                result = con.sendHttpPostjson2(Appconstants.CORE, jobj, "")
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
            //Log.i("tabresp", resp!! + "")
            save.text = "Submit"
            if (progbar!!.isShowing && progbar != null) {
                progbar!!.dismiss()
            }

            try {
                if (resp != null) {


                    val json = JSONArray(resp)
                    val obj1 = json.getJSONObject(0)
                    if (obj1.getString("Status") == "Success") {

                        //JSONObject jarr = obj1.getJSONObject("Response");
                        Toast.makeText(this@SignUp, "Register Successfully.", Toast.LENGTH_LONG).show()
                        finish()
                        //startActivity(new Intent(SignUp.this,Login.class));

                    } else {
                        username.setText("")
                        username.requestFocus()

                        Toast.makeText(this@SignUp, obj1.getString("Response"), Toast.LENGTH_LONG).show()

                    }
                } else {
                    username.setText("")
                    username.requestFocus()
                    Toast.makeText(
                        applicationContext,
                        "Oops! Something went wrong please try again.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } catch (e: Exception) {
                username.setText("")
                username.requestFocus()
                println("errorvhealth" + e.toString())
                e.printStackTrace()
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }

    private inner class stateloads : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            //progbar.setVisibility(View.VISIBLE);
            //Log.i("Checkstate", "started")
            pDialo = ProgressDialog(this@SignUp);
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

                            println("cloud_name" + cloud_name)
                            println("api_key" + api_key)
                            println("api_secret" + api_secret)
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
                Toast.makeText(
                    applicationContext,
                    "Please check your internet connection and try again.",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }



}
