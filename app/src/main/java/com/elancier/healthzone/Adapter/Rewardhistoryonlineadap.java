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

import com.elancier.healthzone.Online_training;
import com.elancier.healthzone.Pojo.Rewardpointsbo;
import com.elancier.healthzone.R;
import com.elancier.healthzone.Reward_history;

import java.util.List;

public class Rewardhistoryonlineadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public Rewardhistoryonlineadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                return new Rewardhistoryonlineadap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                Rewardhistoryonlineadap.ItemViewHolder itemViewHolder = (Rewardhistoryonlineadap.ItemViewHolder) holder;
                final Rewardpointsbo item = (Rewardpointsbo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                ((Rewardhistoryonlineadap.ItemViewHolder) holder).name.setText(item.getName());
                ((Rewardhistoryonlineadap.ItemViewHolder) holder).cutoff.setText(item.getWhome());

                ((Rewardhistoryonlineadap.ItemViewHolder) holder).uname.setText(item.getUName());


                if(item.getType().equals("Green")){
                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).type.setVisibility(View.VISIBLE);
                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).red.setVisibility(View.GONE);

                }
                else if(item.getType().equals("Red")){
                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).type.setVisibility(View.GONE);

                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).red.setVisibility(View.VISIBLE);

                }
                else{
                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).type.setVisibility(View.GONE);

                    ((Rewardhistoryonlineadap.ItemViewHolder) holder).red.setVisibility(View.VISIBLE);
                }
                //((Rewardhistoryadap.ItemViewHolder) holder).points.setText(item.getPoints());
                ((Rewardhistoryonlineadap.ItemViewHolder) holder).visualtime.setText(item.getVisual_time());
                //((Rewardhistoryadap.ItemViewHolder) holder).whome.setText(item.getWhome());
                //((Rewardhistoryadap.ItemViewHolder) holder).whomename.setText(item.getWhomename());



                    ((ItemViewHolder) holder).feed.setVisibility(View.VISIBLE);


                    if(item.getDate().toString().equals("Green")){
                        Log.e("Insed green","dd");
                        ((ItemViewHolder) holder).feed.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.feeddrawable_green));

                    }
                    else if(item.getDate().toString().equals("Red"))
                        {
                            ((ItemViewHolder) holder).feed.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.feeddrawable_red));
                            Log.e("Insed red","dd");

                        }



                ((ItemViewHolder) holder).feed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("inside","d");


                            Log.e("inside",String.valueOf(item.getId()));
                            ((Online_training)context).clikffed(item.getId(),item.getVisual_time(),item.getWhome(),item.getUsername());


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
        ImageView type,red;
        ItemViewHolder(final View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
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

