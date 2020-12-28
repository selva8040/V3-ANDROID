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

public class Star_perfadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public Star_perfadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.star_perfm_items, viewGroup, false);
                return new Star_perfadap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                Star_perfadap.ItemViewHolder itemViewHolder = (Star_perfadap.ItemViewHolder) holder;
                final salarypo item = (salarypo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                //((Super_salhistoryadap.ItemViewHolder) holder).name.setText(item.getVisual_time());
                ((Star_perfadap.ItemViewHolder) holder).fdate.setText("UserName : \t"+item.getfdate());
                ((Star_perfadap.ItemViewHolder) holder).tdate.setText("UserName 1 : \t"+item.gettdate());
                ((Star_perfadap.ItemViewHolder) holder).played.setText("UserName 2 : \t"+item.getplayed());
                ((Star_perfadap.ItemViewHolder) holder).viewed.setText("UserName 3 : \t"+item.getviewed());
                ((Star_perfadap.ItemViewHolder) holder).view_duplicate.setText("UserName 4 : \t"+item.getview_duplicate());
                ((Star_perfadap.ItemViewHolder) holder). not_seen.setText("UserName 5 : \t"+item.getnot_seen());
                ((Star_perfadap.ItemViewHolder) holder).feedback_below4.setText("UserName 6 : \t"+item.getfeedback_below4());
                //((Star_perfadap.ItemViewHolder) holder).feedback_above3.setText(item.getfeedback_above3());
                ((Star_perfadap.ItemViewHolder) holder).feedback_not.setText(item.getfeedback_above3());



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
            tdate=(TextView) itemView.findViewById(R.id.textView47);
            played=(TextView) itemView.findViewById(R.id.textView48);
            viewed=(TextView) itemView.findViewById(R.id.textView49);
            view_duplicate=(TextView) itemView.findViewById(R.id.textView50);
            not_seen=(TextView) itemView.findViewById(R.id.textView52);
            feedback_below4=(TextView) itemView.findViewById(R.id.textView51);
            feedback_not=(TextView) itemView.findViewById(R.id.textView53);
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

