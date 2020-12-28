package com.elancier.healthzone.Adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.FeedbackActivity
import com.elancier.healthzone.Pojo.Feedbackbo
import com.elancier.healthzone.Pojo.WelcomeReport
import com.elancier.healthzone.R

class WelcomeReportAdapter(private val mRecyclerViewItems2: List<WelcomeReport>, private val context: Activity, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnItemClickListener {
        fun OnItemClick(view: View?, position: Int, viewType: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.welcome_report_adapter, viewGroup, false)
                ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.welcome_report_adapter, viewGroup, false)
                ItemViewHolder(productView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position]
                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                holder.name.text = item.date
                holder.cutoff.text = item.feedback
                holder.uname.text = item.username
                holder.utype.text = item.type
                holder.feed!!.setOnClickListener {
                    clikffed(item.feedback!!)
                }

            }
            else -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position]
                holder.name.text = item.date
                holder.cutoff.text = item.feedback
                holder.uname.text = item.username
                holder.utype.text = item.type
                holder.feed!!.setOnClickListener {
                    clikffed(item.feedback!!)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val recyclerViewItem = mRecyclerViewItems2[position]
        /*if (recyclerViewItem instanceof Confirmmodel) {
            return ITEM_CONFIRM_VIEW_TYPE;
        }*/return ITEM_CONTENT_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return mRecyclerViewItems2.size
    }

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var username: TextView? = null
        var date: TextView? = null
        var comments: TextView
        var name: TextView
        var utype: TextView
        var uname: TextView
        var points: TextView? = null
        var visualtime: TextView
        var cutoff: TextView
        var whome: TextView? = null
        var whomename: TextView? = null
        var feed: Button? = null
        var type: ImageView? = null
        var red: ImageView? = null
        override fun onClick(view: View) {
            try {
                listener.OnItemClick(view, adapterPosition, ITEM_CONTENT_VIEW_TYPE)
            } catch (e: Exception) {
            }
        }

        init {
            utype = itemView.findViewById<View>(R.id.type) as TextView
            name = itemView.findViewById<View>(R.id.name) as TextView
            uname = itemView.findViewById<View>(R.id.uname) as TextView
            comments = itemView.findViewById<View>(R.id.comments) as TextView
            feed = itemView.findViewById<View>(R.id.feedbutt) as Button
            //points = (TextView) itemView.findViewById(R.id.points);
            visualtime = itemView.findViewById<View>(R.id.visualtime) as TextView
            cutoff = itemView.findViewById<View>(R.id.cutoff) as TextView
            //whome = (TextView) itemView.findViewById(R.id.whome);
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        const val ITEM_CONTENT_VIEW_TYPE = 1
    }

    fun clikffed(feedback: String) {
        try {
            val update = Dialog(context)
            update.requestWindowFeature(Window.FEATURE_NO_TITLE)
            update.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.WHITE))
            val v: View = context.getLayoutInflater().inflate(R.layout.welfeedback_popup, null)
            val scroll = v.findViewById<View>(R.id.sroll) as ScrollView
            val nofeed = v.findViewById<View>(R.id.textView18) as TextView
            val textView33 = v.findViewById<View>(R.id.textView33) as TextView
            val laterbut = v.findViewById<View>(R.id.agree) as Button
            update.setContentView(v)
            update.setCancelable(true)
            update.show()

            if (feedback.isEmpty()) {
                nofeed.visibility = View.VISIBLE
                textView33.setText(feedback)
            }else{
                nofeed.visibility = View.GONE
                textView33.setText(feedback)
            }

            ///titlename.setText("New Version 1."+Number);
            laterbut.setOnClickListener {
                update.dismiss()

            }
        } catch (e: java.lang.Exception) {
            Log.e("Log val", e.toString())
            //logger.info("PerformVersionTask error" + e.getMessage());
        }
    }

}

