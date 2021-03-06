package com.elancier.healthzone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.*
import com.elancier.healthzone.Pojo.Rewardpointsbo

class Pointsadap(private val mRecyclerViewItems2: List<Any>, private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int, viewType: Int)
    }


    override fun onCreateViewHolder(viewGroup:ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.point_items, viewGroup, false)
                return ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.point_items, viewGroup, false)
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
                holder.date.text = item.username
                holder.name.text = "Amount - ₹ "+item.whomename

                if(item.visual_time=="0"){
                    holder.remark.text = "Pending"
                    holder.remark.setBackground(context.resources.getDrawable(R.drawable.feeddrawable_orange))
                }
                else if(item.visual_time=="1"){
                    holder.remark.text = "Approved"
                    holder.remark.setBackground(context.resources.getDrawable(R.drawable.feeddrawable_green))

                }
                else{
                    holder.remark.text = "Rejected"
                    holder.remark.setBackground(context.resources.getDrawable(R.drawable.feeddrawable_red))

                }

                holder.remark.setOnClickListener {

                    if(holder.remark.text.toString()=="Approved") {
                        (context as New_wallet_List).clikffed(item.id, item.name,item.whome,item.uName)
                    }
                    else if(holder.remark.text.toString()=="Rejected") {
                        (context as New_wallet_List).clikffed_rej(item.id, item.points,item.uName,item.uName)
                    }

                }



            }

            // fall through
            else -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position] as Rewardpointsbo

                holder.username.text = item.date
                holder.date.text = item.username
                if(item.visual_time=="0"){
                    holder.remark.text = "Pending"
                    holder.remark.setTextColor(context.resources.getColor(R.color.red))
                }
                else{
                    holder.remark.text = "Approved"
                    holder.remark.setTextColor(context.resources.getColor(R.color.greens))

                }

            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val recyclerViewItem = mRecyclerViewItems2[position]
        return ITEM_CONTENT_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return mRecyclerViewItems2.size
    }

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var username: TextView
        internal var date: TextView
        internal var name: TextView
        internal lateinit var remark: TextView
        internal lateinit var amount: TextView

        init {

            username = itemView.findViewById<View>(R.id.textView41) as TextView
            date = itemView.findViewById<View>(R.id.textView42) as TextView
            name = itemView.findViewById<View>(R.id.textView45) as TextView
            remark=itemView.findViewById<View>(R.id.textView67) as TextView
            amount=itemView.findViewById<View>(R.id.textView68) as TextView


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