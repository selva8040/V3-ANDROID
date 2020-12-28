package com.elancier.healthzone.Adapter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.elancier.healthzone.Pin_Service_Board
import com.elancier.healthzone.Pojo.Rewardpointsbo
import com.elancier.healthzone.R
import com.elancier.healthzone.Rewardpointsbo_dup
import java.util.jar.Manifest


class pinboard_adap(
    private val mRecyclerViewItems2: List<Any>,
    private val context: Context,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface OnItemClickListener {
        fun OnItemClick(view: View, position: Int, viewType: Int)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.pinboard_items,
                    viewGroup,
                    false
                )
                return ItemViewHolder(productView)
            }
            else -> {
                val productView = LayoutInflater.from(viewGroup.context).inflate(
                    R.layout.pinboard_items,
                    viewGroup,
                    false
                )
                return ItemViewHolder(productView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)

        when (viewType) {
            ITEM_CONTENT_VIEW_TYPE -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position] as Rewardpointsbo_dup

                holder.username.text = item.date  // name
                holder.date.text = item.visual_time  // mobile
                holder.amount.setText(" - " + item.uName) // branch
                holder.name.setText(" - " + item.name)  //bank
                holder.acc.setText(" - " + item.whome) // acc
                holder.remark.setText(item.username) // whatsapp
                holder.ifsc.setText(" - " + item.whomename) // whatsapp
                holder.contact.setText(" - " + item.points) // whatsapp
                holder.lang.setText(" - " + item.getlang()) // Language
                holder.spec.setText(" - " + item.getspec()) // Specification
                holder.desig.setText(" - " + item.getdesig()) // Specification
                holder.rate.isSelected = true

                holder.ratecard.setOnClickListener {

                    (context as Pin_Service_Board).rate(item.date,item.id)
                }

                holder.date.setOnClickListener {
                    try {


                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:"+ item.visual_time)
                        context.startActivity(intent)

                    } catch (e: Exception) {
                        Toast.makeText(context, "Unable to make call", Toast.LENGTH_LONG).show()
                    }
                }
                holder.remark.setOnClickListener {
                    try {
                        val uri = Uri.parse("smsto:" + item.username)
                        val i = Intent(Intent.ACTION_SENDTO, uri)
                        i.putExtra("sms_body", "Any Queries: ")
                        i.setPackage("com.whatsapp")
                        context.startActivity(i)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Install Whatsapp", Toast.LENGTH_LONG).show()
                    }
                }


                if (item.type == "Male") {
                    holder.img.setImageResource(R.mipmap.male_places)
                } else if (item.type == "Female") {
                    holder.img.setImageResource(R.mipmap.female_place)
                }
                /* if(item.visual_time=="0"){
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

                }*/

                /*holder.remark.setOnClickListener {

                    if(holder.remark.text.toString()=="Approved") {
                        (context as New_recharge_List).clikffed(item.id, item.whomename,item.points,item.type)
                    }
                    else if(holder.remark.text.toString()=="Rejected") {
                        (context as New_recharge_List).clikffed_rej(item.id, item.points,item.points,item.type)
                    }

                }*/


            }

            // fall through
            else -> {
                val itemViewHolder = holder as ItemViewHolder
                val item = mRecyclerViewItems2[position] as Rewardpointsbo

                holder.username.text = item.date
                holder.date.text = item.username

              /*  if(item.visual_time=="0"){
                    holder.remark.text = "Pending"
                    holder.remark.setTextColor(context.resources.getColor(R.color.red))
                }
                else{
                    holder.remark.text = "Approved"
                    holder.remark.setTextColor(context.resources.getColor(R.color.greens))

                }*/

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

    inner class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ), View.OnClickListener {

        internal var username: TextView
        internal var date: TextView
        internal var name: TextView
        internal lateinit var remark: TextView
        internal lateinit var ifsc: TextView
        internal lateinit var amount: TextView
        internal lateinit var acc: TextView
        internal lateinit var contact: TextView
        internal lateinit var lang: TextView
        internal lateinit var spec: TextView
        internal lateinit var desig: TextView
        internal lateinit var img: ImageView
        internal lateinit var ratecard: CardView
        internal lateinit var rate: TextView
        //internal var dt: TextView


        init {

            username = itemView.findViewById<View>(R.id.name) as TextView
            date = itemView.findViewById<View>(R.id.unames) as TextView
            name = itemView.findViewById<View>(R.id.cutoff) as TextView
            remark=itemView.findViewById<View>(R.id.textView67) as TextView
            amount=itemView.findViewById<View>(R.id.visualtime) as TextView
            acc=itemView.findViewById<View>(R.id.acc) as TextView
            contact=itemView.findViewById<View>(R.id.contact) as TextView
            ifsc=itemView.findViewById<View>(R.id.ifsc) as TextView
            lang=itemView.findViewById<View>(R.id.lang) as TextView
            spec=itemView.findViewById<View>(R.id.spec) as TextView
            desig=itemView.findViewById<View>(R.id.desig) as TextView
            img=itemView.findViewById<View>(R.id.imageView31) as ImageView
            ratecard=itemView.findViewById<View>(R.id.cardView5) as CardView
            rate=itemView.findViewById<View>(R.id.textView100) as TextView

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