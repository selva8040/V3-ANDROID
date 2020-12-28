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
import com.elancier.healthzone.Pojo.Rewardreducebo;
import com.elancier.healthzone.R;
import com.elancier.healthzone.Reward_history;

import java.util.List;

public class Rewardreduceadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
    private OnItemClickListener listener;

    public Rewardreduceadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
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
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewardreduceitems, viewGroup, false);
                return new Rewardreduceadap.ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                Rewardreduceadap.ItemViewHolder itemViewHolder = (Rewardreduceadap.ItemViewHolder) holder;
                final Rewardreducebo item = (Rewardreducebo) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                ((Rewardreduceadap.ItemViewHolder) holder).name.setText(item.getdate());
                ((Rewardreduceadap.ItemViewHolder) holder).cutoff.setText(item.getopen());

                ((Rewardreduceadap.ItemViewHolder) holder).uname.setText(item.getUsername());

                ((ItemViewHolder) holder).comments.setText(item.getcomments());


                //((Rewardhistoryadap.ItemViewHolder) holder).points.setText(item.getPoints());
                ((Rewardreduceadap.ItemViewHolder) holder).visualtime.setText(item.getreduce());
                //((Rewardhistoryadap.ItemViewHolder) holder).whome.setText(item.getWhome());
                //((Rewardhistoryadap.ItemViewHolder) holder).whomename.setText(item.getWhomename());





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

        TextView username, date,comments, name,uname,  points, visualtime,cutoff, whome, whomename;
        Button feed;
        ImageView type,red;
        ItemViewHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);

            uname = (TextView) itemView.findViewById(R.id.uname);
            comments= (TextView) itemView.findViewById(R.id.comments);
            //points = (TextView) itemView.findViewById(R.id.points);
            visualtime = (TextView) itemView.findViewById(R.id.visualtime);
            cutoff = (TextView) itemView.findViewById(R.id.cutoff);

            //whome = (TextView) itemView.findViewById(R.id.whome);


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

