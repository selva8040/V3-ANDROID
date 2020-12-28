package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Pojo.Feedbackbo;
import com.elancier.healthzone.Pojo.Rewardpointsbo;
import com.elancier.healthzone.R;

import java.util.List;


    public class Rewardfeedadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        static final int ITEM_CONTENT_VIEW_TYPE = 1;
        private List<Object> mRecyclerViewItems2;
        private Context context;
        private com.elancier.healthzone.Adapter.Rewardhistoryadap.OnItemClickListener listener;

        public Rewardfeedadap(List<Object> mRecyclerListitems, Context applicationContext, Rewardfeedadap.OnItemClickListener onItemClickListener) {
            this.mRecyclerViewItems2 = mRecyclerListitems;
            this.context = context;
            this.listener = listener;
        }


        public interface OnItemClickListener {
            void OnItemClick(View view, int position, int viewType);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            switch (viewType) {
                case ITEM_CONTENT_VIEW_TYPE:
                default:
                    View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feedbacklist_item, viewGroup, false);
                    return new Rewardfeedadap.ItemViewHolder(productView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);

            switch (viewType) {
                case ITEM_CONTENT_VIEW_TYPE:

                    // fall through
                default:
                    Rewardfeedadap.ItemViewHolder itemViewHolder = (Rewardfeedadap.ItemViewHolder) holder;
                    Feedbackbo item = (Feedbackbo) mRecyclerViewItems2.get(position);

                    //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                    ((Rewardfeedadap.ItemViewHolder) holder).username.setText(item.getType());
                    ((Rewardfeedadap.ItemViewHolder) holder).date.setText(item.getcomment());
                    ((Rewardfeedadap.ItemViewHolder) holder).time.setText(item.gettime());





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

            TextView username, date,time, name, points, visualtime, cutoff, whome, whomename;

            ImageView type, red;

            ItemViewHolder(final View itemView) {
                super(itemView);

                username = (TextView) itemView.findViewById(R.id.textView19);
                date = (TextView) itemView.findViewById(R.id.textView20);
                time= (TextView) itemView.findViewById(R.id.textView24);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                try {
                    listener.OnItemClick(view, getAdapterPosition(), ITEM_CONTENT_VIEW_TYPE);
                } catch (Exception e) {

                }
            }
        }
    }

