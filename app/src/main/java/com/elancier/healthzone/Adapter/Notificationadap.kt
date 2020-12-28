package com.elancier.healthzone.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.Adapter.Rewardhistoryadap.ItemViewHolder
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.R
import com.elancier.healthzone.Reward_history

class Notificationadap(private val mRecyclerViewItems2: List<Any>, private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int, viewType: Int)
    }


    override fun onCreateViewHolder(viewGroup:ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.notification_items, viewGroup, false)
                return ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.notification_items, viewGroup, false)
                return ItemViewHolder(productView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position] as Rewardpointsbo

                holder.username.text = item.date
                holder.date.text = item.visual_time
                holder.name.text = item.username




            }

            // fall through
            else -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position] as Rewardpointsbo

                holder.username.text = item.date
                holder.date.text = item.visual_time
                holder.name.text = item.username

            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val recyclerViewItem = mRecyclerViewItems2[position]
        /*if (recyclerViewItem instanceof Confirmmodel) {
            return ITEM_CONFIRM_VIEW_TYPE;
        }*/
        return ITEM_CONTENT_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return mRecyclerViewItems2.size
    }

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var username: TextView
        internal var date: TextView
        internal var name: TextView
        //internal var dt: TextView


        init {

            username = itemView.findViewById<View>(R.id.textView41) as TextView
            date = itemView.findViewById<View>(R.id.textView42) as TextView
            name = itemView.findViewById<View>(R.id.textView45) as TextView



            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            try {
                listener.OnItemClick(view, adapterPosition, ITEM_CONTENT_VIEW_TYPE)
            } catch (e: Exception) {

            }

        }
    }

    companion object {
        internal val ITEM_CONTENT_VIEW_TYPE = 1
    }
}