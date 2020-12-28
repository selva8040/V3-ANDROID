package com.elancier.healthzone

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class Datelist_adap(
        private val context: Activity, //to store the list of countries
        private val Family: ArrayList<String>,
        private val Family1: ArrayList<String>

) : ArrayAdapter<Any>(context, R.layout.datelist_items, (Family as List<Any>?)!!) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {

        //val inflater = context.layoutInflater
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.datelist_items, null, true)
        val Fid = rowView.findViewById<TextView>(R.id.textView27)
        val Fid2 = rowView.findViewById<TextView>(R.id.textView28)



        Fid.setText(Family[position])
        Fid2.setText(Family1[position])

        return rowView

    }
}