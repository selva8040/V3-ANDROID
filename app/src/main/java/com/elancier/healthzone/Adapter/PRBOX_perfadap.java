package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Pojo.salarypo;
import com.elancier.healthzone.R;

import java.util.List;

public class PRBOX_perfadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public PRBOX_perfadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pb_box_items, viewGroup, false);
                return new PRBOX_perfadap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                PRBOX_perfadap.ItemViewHolder itemViewHolder = (PRBOX_perfadap.ItemViewHolder) holder;
                final salarypo item = (salarypo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                //((Super_salhistoryadap.ItemViewHolder) holder).name.setText(item.getVisual_time());
                ((PRBOX_perfadap.ItemViewHolder) holder).fdate.setText("UserName : \t"+item.getfdate());
                ((PRBOX_perfadap.ItemViewHolder) holder).tdate.setText("UserName 1 : \t"+item.gettdate());
                ((PRBOX_perfadap.ItemViewHolder) holder).played.setText("UserName 2 : \t"+item.getplayed());
                ((PRBOX_perfadap.ItemViewHolder) holder).viewed.setText("UserName 3 : \t"+item.getviewed());
                //((PRBOX_perfadap.ItemViewHolder) holder).view_duplicate.setText("UserName 4 : \t"+item.getview_duplicate());
                //((PRBOX_perfadap.ItemViewHolder) holder). not_seen.setText("UserName 5 : \t"+item.getnot_seen());
                //((PRBOX_perfadap.ItemViewHolder) holder).feedback_below4.setText("UserName 6 : \t"+item.getfeedback_below4());
                //((Star_perfadap.ItemViewHolder) holder).feedback_above3.setText(item.getfeedback_above3());
                ((PRBOX_perfadap.ItemViewHolder) holder).dt.setText(item.getfeedback_above3());

                if(item.getview_duplicate().equals("0")){
                    ((PRBOX_perfadap.ItemViewHolder) holder).feedback_not.setText("Pending");
                    ((PRBOX_perfadap.ItemViewHolder) holder).status.setCardBackgroundColor(Color.parseColor("#FFA500"));
                    ((PRBOX_perfadap.ItemViewHolder) holder).apdt.setVisibility(View.GONE);
                    ((PRBOX_perfadap.ItemViewHolder) holder).details.setVisibility(View.GONE);

                }
                else if(item.getview_duplicate().equals("1")){
                    ((PRBOX_perfadap.ItemViewHolder) holder).feedback_not.setText("Approved");
                    ((PRBOX_perfadap.ItemViewHolder) holder).status.setCardBackgroundColor(Color.parseColor("#4CA94C"));
                    ((PRBOX_perfadap.ItemViewHolder) holder).apdt.setVisibility(View.VISIBLE);
                    ((PRBOX_perfadap.ItemViewHolder) holder).apdt.setText("Approved On : "+item.getfeedback_below4());
                }
                else if(item.getview_duplicate().equals("2")){
                    ((PRBOX_perfadap.ItemViewHolder) holder).feedback_not.setText("Rejected");
                    ((PRBOX_perfadap.ItemViewHolder) holder).status.setCardBackgroundColor(Color.parseColor("#d94d4d"));
                    ((PRBOX_perfadap.ItemViewHolder) holder).details.setVisibility(View.VISIBLE);
                    ((PRBOX_perfadap.ItemViewHolder) holder).details.setText("Reason : "+item.getnot_seen());

                }



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

        TextView username, fdate, tdate,uname,  played,viewed,details,apdt,
                feedback_below4,feedback_above3, feedback_not, permission_below4, permission_above3,
                splayed,sviewed,sview_duplicate,snot_seen,sfeedback_below4,sfeedback_above3,
                sfeedback_not,spermission_below4,spermission_above3,dt;
        Button feed;
        ImageView type,red;
        CardView status;
        ItemViewHolder(final View itemView) {
            super(itemView);

            fdate=(TextView) itemView.findViewById(R.id.textView46);
            tdate=(TextView) itemView.findViewById(R.id.textView47);
            played=(TextView) itemView.findViewById(R.id.textView48);
            viewed=(TextView) itemView.findViewById(R.id.textView49);
            dt=(TextView) itemView.findViewById(R.id.textView53);
            feedback_not=(TextView) itemView.findViewById(R.id.textView101);
            status=(CardView) itemView.findViewById(R.id.cardView6);
            apdt=(TextView) itemView.findViewById(R.id.textView49atime);
            details=(TextView) itemView.findViewById(R.id.textView49details);
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

