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

import com.elancier.healthzone.Pojo.Rewardpointsbo;
import com.elancier.healthzone.R;
import com.elancier.healthzone.Reward_history;

import java.util.List;

public class Rewardhistoryadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public Rewardhistoryadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                return new Rewardhistoryadap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                Rewardhistoryadap.ItemViewHolder itemViewHolder = (Rewardhistoryadap.ItemViewHolder) holder;
                final Rewardpointsbo item = (Rewardpointsbo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                ((Rewardhistoryadap.ItemViewHolder) holder).name.setText(item.getName());
                ((Rewardhistoryadap.ItemViewHolder) holder).cutoff.setText("Cut-off Time - "+item.getWhome());

                ((Rewardhistoryadap.ItemViewHolder) holder).uname.setText(item.getUName());


                if(item.getType().equals("Green")){
                    ((Rewardhistoryadap.ItemViewHolder) holder).type.setVisibility(View.VISIBLE);
                    ((Rewardhistoryadap.ItemViewHolder) holder).red.setVisibility(View.GONE);
                    ((Rewardhistoryadap.ItemViewHolder) holder).orange.setVisibility(View.GONE);

                }
                else if(item.getType().equals("Red")){
                    ((Rewardhistoryadap.ItemViewHolder) holder).orange.setVisibility(View.GONE);
                    ((Rewardhistoryadap.ItemViewHolder) holder).type.setVisibility(View.GONE);

                    ((Rewardhistoryadap.ItemViewHolder) holder).red.setVisibility(View.VISIBLE);

                }
                else if(item.getType().equals("orange")){
                    ((Rewardhistoryadap.ItemViewHolder) holder).orange.setVisibility(View.VISIBLE);
                    ((Rewardhistoryadap.ItemViewHolder) holder).type.setVisibility(View.GONE);

                    ((Rewardhistoryadap.ItemViewHolder) holder).red.setVisibility(View.GONE);

                }
                else{
                    ((Rewardhistoryadap.ItemViewHolder) holder).type.setVisibility(View.GONE);
                    ((Rewardhistoryadap.ItemViewHolder) holder).orange.setVisibility(View.GONE);

                    ((Rewardhistoryadap.ItemViewHolder) holder).red.setVisibility(View.VISIBLE);
                }
                //((Rewardhistoryadap.ItemViewHolder) holder).points.setText(item.getPoints());
                ((Rewardhistoryadap.ItemViewHolder) holder).visualtime.setText("Visual Time - "+item.getVisual_time());
                //((Rewardhistoryadap.ItemViewHolder) holder).whome.setText(item.getWhome());
                //((Rewardhistoryadap.ItemViewHolder) holder).whomename.setText(item.getWhomename());



                    ((ItemViewHolder) holder).feed.setVisibility(View.VISIBLE);


                    if(item.getDate().toString().equals("Green")){
                        Log.e("Insed green","dd");
                        ((ItemViewHolder) holder).feed.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.feeddrawable_green));

                    }
                    else if(item.getDate().toString().equals("Red")) {
                        {
                            ((ItemViewHolder) holder).feed.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.feeddrawable_red));
                            Log.e("Insed red", "dd");

                        }
                    }else if(item.getDate().toString().equals("Orange")) {
                        {
                            ((ItemViewHolder) holder).feed.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.feeddrawable_orange));
                            Log.e("Insed orange", "dd");

                        }
                    }


                ((ItemViewHolder) holder).feed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("inside","d");


                            Log.e("inside",String.valueOf(item.getId()));
                            ((Reward_history)context).clikffed(item.getId(),item.getVisual_time(),item.getWhome(),item.getUsername(),item.getWhomename());


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

