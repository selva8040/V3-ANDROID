package com.elancier.healthzone.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.New_recharge_List
import com.elancier.healthzone.Point_List
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.R
import com.elancier.healthzone.Reward_history

class Crore_Rechargeadap(private val mRecyclerViewItems2: List<Any>, private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int, viewType: Int)
    }


    override fun onCreateViewHolder(viewGroup:ViewGroup,viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.crore_recharge_items, viewGroup, false)
                return ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.crore_recharge_items, viewGroup, false)
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
                holder.amount.setText("To date - "+item.whome)
                holder.name.setText("Date - "+item.name)

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
                        (context as New_recharge_List).clikffed(item.id, item.whomename,item.points,item.type)
                    }
                    else if(holder.remark.text.toString()=="Rejected") {
                        (context as New_recharge_List).clikffed_rej(item.id, item.points,item.points,item.type)
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
        internal lateinit var remark: TextView
        internal lateinit var amount: TextView
        //internal var dt: TextView


        init {

            username = itemView.findViewById<View>(R.id.name) as TextView
            date = itemView.findViewById<View>(R.id.uname) as TextView
            name = itemView.findViewById<View>(R.id.cutoff) as TextView
            remark=itemView.findViewById<View>(R.id.textView67) as TextView
            amount=itemView.findViewById<View>(R.id.visualtime) as TextView


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