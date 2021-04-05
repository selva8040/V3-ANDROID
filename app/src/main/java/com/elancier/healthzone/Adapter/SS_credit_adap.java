package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Pojo.SScredits_bo;
import com.elancier.healthzone.R;
import com.elancier.healthzone.SSCredit_List;

import java.util.List;

public class SS_credit_adap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public SS_credit_adap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewardhistitems, viewGroup, false);
                return new SS_credit_adap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                SS_credit_adap.ItemViewHolder itemViewHolder = (SS_credit_adap.ItemViewHolder) holder;
                final SScredits_bo item = (SScredits_bo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                ((SS_credit_adap.ItemViewHolder) holder).name.setText(item.getdate());
                ((SS_credit_adap.ItemViewHolder) holder).cutoff.setText(item.getopen());

                ((SS_credit_adap.ItemViewHolder) holder).uname.setText(item.getUsername());
                ((ItemViewHolder) holder).visualtime.setText(item.getreduce());


                if(item.getcomments().equals("1")){
                    ((SS_credit_adap.ItemViewHolder) holder).type.setVisibility(View.GONE);
                    ((SS_credit_adap.ItemViewHolder) holder).red.setVisibility(View.GONE);
                    ((SS_credit_adap.ItemViewHolder) holder).orange.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).feed.setBackground(context.getResources().getDrawable(R.drawable.feeddrawable_green));
                    ((ItemViewHolder) holder).feed.setText("View Details");


                }
                else if(item.getcomments().equals("0")){
                    ((SS_credit_adap.ItemViewHolder) holder).orange.setVisibility(View.GONE);
                    ((SS_credit_adap.ItemViewHolder) holder).type.setVisibility(View.GONE);
                    ((SS_credit_adap.ItemViewHolder) holder).red.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).feed.setBackground(context.getResources().getDrawable(R.drawable.feeddrawable_red));
                    //((ItemViewHolder) holder).feed.setBackground(context.getResources().getDrawable(R.drawable.feeddrawable_red));
                    ((ItemViewHolder) holder).feed.setText("Pending");

                }

                //((Rewardhistoryadap.ItemViewHolder) holder).points.setText(item.getPoints());
                //((SS_credit_adap.ItemViewHolder) holder).visualtime.setText("Visual Time - "+item.getVisual_time());
                //((Rewardhistoryadap.ItemViewHolder) holder).whome.setText(item.getWhome());
                //((Rewardhistoryadap.ItemViewHolder) holder).whomename.setText(item.getWhomename());



                    ((ItemViewHolder) holder).feed.setVisibility(View.VISIBLE);



                ((ItemViewHolder) holder).feed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("inside","d");

                        if(item.getcomments().equals("1")) {
                            ((SSCredit_List) context).clikffed(position);
                        }

                    }
                });

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

        TextView username, date, name,uname,  points, visualtime,cutoff, whome, whomename;
        Button feed;
        ImageView type,orange,red;
        ItemViewHolder(final View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            orange = (ImageView) itemView.findViewById(R.id.orange);
            type = (ImageView) itemView.findViewById(R.id.green);
            red = (ImageView) itemView.findViewById(R.id.red);
            feed = (Button) itemView.findViewById(R.id.feedbutt);
            uname = (TextView) itemView.findViewById(R.id.uname);
            //points = (TextView) itemView.findViewById(R.id.points);
            visualtime = (TextView) itemView.findViewById(R.id.visualtime);
            cutoff = (TextView) itemView.findViewById(R.id.cutoff);

            //whome = (TextView) itemView.findViewById(R.id.whome);
            whomename = (TextView) itemView.findViewById(R.id.whomename);

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

