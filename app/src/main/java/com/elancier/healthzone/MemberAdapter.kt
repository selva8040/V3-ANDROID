package com.elancier.healthzone

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.MembersData


class MemberAdapter(private val MemberArrays: List<MembersData>, private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int, viewType: Int)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.members_list_adapter, viewGroup, false)
                return ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(R.layout.members_list_adapter, viewGroup, false)
                return ItemViewHolder(productView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val itemViewHolder = holder as ItemViewHolder


                holder.mmob.setText(MemberArrays[position].MMobile)
                //holder.linkbutt.setText(MemberArrays[position].MAmount)
                holder.linkbutt.setOnClickListener {

                    (context as Monthly_report).download(MemberArrays[position].MAmount.toString(),MemberArrays[position].MMobile.toString())

                }
            }
            else -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val recyclerViewItem = MemberArrays[position]
        /*if (recyclerViewItem instanceof Confirmmodel) {
            return ITEM_CONFIRM_VIEW_TYPE;
        }*/
        return ITEM_CONTENT_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return MemberArrays.size
    }

    inner class ItemViewHolder internal constructor(rowView: View) : RecyclerView.ViewHolder(rowView), View.OnClickListener {


        internal var mid : TextView
        internal var mnm : TextView
        internal var mmob : TextView
        internal var linkbutt : Button



        init {

            mid = rowView.findViewById(R.id.mid) as TextView
            mnm = rowView.findViewById(R.id.mnm) as TextView
            mmob = rowView.findViewById(R.id.mmob) as TextView
            linkbutt = rowView.findViewById(R.id.button) as Button


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