package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Pojo.Rewardpointsbo;
import com.squareup.picasso.Picasso;
import com.elancier.healthzone.R;

import java.util.List;

public class RewardpointsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
    private Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, int viewType);
    }

    public RewardpointsAdapter(List<Object> mRecyclerViewItems, Context context, OnItemClickListener listener) {
        this.mRecyclerViewItems2 = mRecyclerViewItems;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:
            default:
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewarditems, viewGroup, false);
                return new ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Rewardpointsbo item = (Rewardpointsbo) mRecyclerViewItems2.get(position);

                ((ItemViewHolder) holder).username.setText(item.getUsername());
                ((ItemViewHolder) holder).date.setText(item.getDate());
                ((ItemViewHolder) holder).name.setText(item.getName());
                ((ItemViewHolder) holder).type.setText("Type - "+item.getType());
                ((ItemViewHolder) holder).points.setText("Points - "+item.getPoints());
                ((ItemViewHolder) holder).visualtime.setText("Visual Time - "+item.getVisual_time());
                ((ItemViewHolder) holder).whome.setText("Whom - "+item.getWhome());
                ((ItemViewHolder) holder).whomename.setText("Whom name - "+item.getWhomename());

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

        TextView username, date, name, type, points, visualtime, whome, whomename;

        ItemViewHolder(final View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            date = (TextView) itemView.findViewById(R.id.date);
            name = (TextView) itemView.findViewById(R.id.name);
            type = (TextView) itemView.findViewById(R.id.type);
            points = (TextView) itemView.findViewById(R.id.points);
            visualtime = (TextView) itemView.findViewById(R.id.visualtime);
            whome = (TextView) itemView.findViewById(R.id.whome);
            whomename = (TextView) itemView.findViewById(R.id.whomename);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnItemClick(view, getAdapterPosition(), ITEM_CONTENT_VIEW_TYPE);
        }
    }
}

