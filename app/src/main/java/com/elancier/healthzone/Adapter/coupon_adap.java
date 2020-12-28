package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Pojo.salarypo;
import com.elancier.healthzone.R;

import java.util.List;

public class coupon_adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public coupon_adap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
        this.mRecyclerViewItems2 = mRecyclerListitems;
        this.context = contexts;
        this.listener =  onItemClickListener;
    }


    public interface OnItemClickListener {
        void OnItemClick(View view, int position, int viewType);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:
            default:
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coupon_items, viewGroup, false);
                return new coupon_adap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                coupon_adap.ItemViewHolder itemViewHolder = (coupon_adap.ItemViewHolder) holder;
                final salarypo item = (salarypo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                //((Super_salhistoryadap.ItemViewHolder) holder).name.setText(item.getVisual_time());
                ((coupon_adap.ItemViewHolder) holder).fdate.setText(item.getfdate());
                ((coupon_adap.ItemViewHolder) holder).tdate.setText(item.gettdate());
               // ((coupon_adap.ItemViewHolder) holder).tdate.setText("UserName 1 : \t"+item.gettdate());




        }
    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems2.get(position);
        /*if (recyclerViewItem instanceof Confirmmodel) {
            return ITEM_CONFIRM_VIEW_TYPE;
        }*/
        return ITEM_CONTENT_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems2.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView username, fdate, tdate,uname,  played,viewed,view_duplicate,not_seen,
                feedback_below4,feedback_above3, feedback_not, permission_below4, permission_above3,
                splayed,sviewed,sview_duplicate,snot_seen,sfeedback_below4,sfeedback_above3,
                sfeedback_not,spermission_below4,spermission_above3;
        Button feed;
        ImageView type,red;
        ItemViewHolder(final View itemView) {
            super(itemView);

            fdate=(TextView) itemView.findViewById(R.id.textView46);
            tdate=(TextView) itemView.findViewById(R.id.textView60);

            //feedback_not=(TextView) itemView.findViewById(R.id.textView53);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                listener.OnItemClick(view, getAdapterPosition(), ITEM_CONTENT_VIEW_TYPE);
            }
            catch (Exception e){

            }
        }
    }
}

