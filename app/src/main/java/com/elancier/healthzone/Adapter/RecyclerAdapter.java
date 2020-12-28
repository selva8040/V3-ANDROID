package com.elancier.healthzone.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.R;

import com.elancier.healthzone.Pojo.Recyclerbo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems;
    private Context context;
    private final OnItemClickListener listener;
    Utils utils;

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, int viewType);
    }

    public RecyclerAdapter(List<Object> mRecyclerViewItems, Context context, OnItemClickListener listener) {
        this.mRecyclerViewItems = mRecyclerViewItems;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:
            default:
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videorecycler, viewGroup, false);
                /*productView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                int width = productView.getMeasuredWidth();
                int height = productView.getMeasuredHeight();*/
                return new ItemViewHolder(productView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        utils = new Utils(context);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                final Recyclerbo item = (Recyclerbo) mRecyclerViewItems.get(position);

                if(item.getType().equals("Text"))
                {
                    ((ItemViewHolder) holder).images_lay.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).description.setText(item.getDesc());
                }
                if(item.getType().equals("Image"))
                {
                    ((ItemViewHolder) holder).text_lay.setVisibility(View.GONE);
                    Picasso.with(context).load(item.getUrl()).placeholder(R.mipmap.v3).into(((ItemViewHolder) holder).adimage);
                }
        }
    }

    @Override
    public int getItemViewType(int position) {

        Object recyclerViewItem = mRecyclerViewItems.get(position);
        /*if (recyclerViewItem instanceof Confirmmodel) {
            return ITEM_CONFIRM_VIEW_TYPE;
        }*/
        return ITEM_CONTENT_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewItems.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout images_lay, text_lay;
        ImageView adimage;

        TextView title, description, link;

        ItemViewHolder(final View itemView) {
            super(itemView);

            images_lay = (LinearLayout) itemView.findViewById(R.id.images_lay);
            text_lay = (LinearLayout) itemView.findViewById(R.id.text_lay);
            adimage = (ImageView) itemView.findViewById(R.id.adimage);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            link = (TextView) itemView.findViewById(R.id.link);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnItemClick(view, getAdapterPosition(), ITEM_CONTENT_VIEW_TYPE);
        }
    }
}
