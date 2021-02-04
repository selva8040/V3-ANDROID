package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.elancier.healthzone.Common.Utils;
import com.elancier.healthzone.Pojo.salarypo_app;
import com.elancier.healthzone.R;
import com.elancier.healthzone.Super_Salry_history;

import java.util.List;

public class Super_salhistoryadap extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int ITEM_CONTENT_VIEW_TYPE = 1;
    private List<Object> mRecyclerViewItems2;
     private  Context context;
     private Utils utils;
    private OnItemClickListener listener;

    public Super_salhistoryadap(List<Object> mRecyclerListitems, Context contexts, OnItemClickListener onItemClickListener) {
        this.mRecyclerViewItems2 = mRecyclerListitems;
        this.context = contexts;
        this.listener =  onItemClickListener;
        this.utils=new Utils(context);
    }


    public interface OnItemClickListener {
        void OnItemClick(View view, int position, int viewType);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:
            default:
                View productView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.supersal_items, viewGroup, false);
                return new Super_salhistoryadap.ItemViewHolder(productView);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);

        switch (viewType) {
            case ITEM_CONTENT_VIEW_TYPE:

                // fall through
            default:
                Super_salhistoryadap.ItemViewHolder itemViewHolder = (Super_salhistoryadap.ItemViewHolder) holder;
                final salarypo_app item = (salarypo_app) mRecyclerViewItems2.get(position);

                //((Rewardhistoryadap.ItemViewHolder) holder).username.setText(item.getUsername());
//                ((Rewardhistoryadap.ItemViewHolder) holder).date.setText(item.getDate());
                //((Super_salhistoryadap.ItemViewHolder) holder).name.setText(item.getVisual_time());
                ((Super_salhistoryadap.ItemViewHolder) holder).fdate.setText(item.getfdate());
                ((Super_salhistoryadap.ItemViewHolder) holder).reward.setText(item.getReward());
                ((Super_salhistoryadap.ItemViewHolder) holder).tdate.setText(item.gettdate());
                ((Super_salhistoryadap.ItemViewHolder) holder).played.setText(item.getplayed());
                ((Super_salhistoryadap.ItemViewHolder) holder).viewed.setText(item.getviewed());
                ((Super_salhistoryadap.ItemViewHolder) holder).view_duplicate.setText(item.getview_duplicate());
                ((Super_salhistoryadap.ItemViewHolder) holder). not_seen.setText(item.getnot_seen());
                ((Super_salhistoryadap.ItemViewHolder) holder).feedback_below4.setText(item.getfeedback_below4());
                ((Super_salhistoryadap.ItemViewHolder) holder).feedback_above3.setText(item.getfeedback_above3());
                ((Super_salhistoryadap.ItemViewHolder) holder).feedback_not.setText(item.getfeedback_not());
                ((Super_salhistoryadap.ItemViewHolder) holder).permission_below4.setText(item.getpermission_below4());
                ((Super_salhistoryadap.ItemViewHolder) holder).permission_above3.setText(item.getpermission_above3());
                ((Super_salhistoryadap.ItemViewHolder) holder).splayed.setText(item.getsplayed());
                ((Super_salhistoryadap.ItemViewHolder) holder).sviewed.setText(item.getsviewed());
                ((Super_salhistoryadap.ItemViewHolder) holder).sview_duplicate.setText(item.getsview_duplicate());
                ((Super_salhistoryadap.ItemViewHolder) holder).snot_seen.setText(item.getsnot_seen());
                ((Super_salhistoryadap.ItemViewHolder) holder).sfeedback_below4.setText(item.getsfeedback_below4());
                ((Super_salhistoryadap.ItemViewHolder) holder).sfeedback_above3.setText(item.getsfeedback_above3());
                ((Super_salhistoryadap.ItemViewHolder) holder).sfeedback_not.setText(item.getsfeedback_not());
                ((Super_salhistoryadap.ItemViewHolder) holder).spermission_below4.setText(item.getspermission_below4());
                ((Super_salhistoryadap.ItemViewHolder) holder).spermission_above3.setText(item.getspermission_below4());
                ((Super_salhistoryadap.ItemViewHolder) holder).refname.setText(item.getCount());

                Log.e("status",item.getstatus());
                if (item.getstatus().equals("0")) {
                    ((ItemViewHolder) holder).button9.setBackground(context.getResources().getDrawable(R.drawable.blue_solid_bg));
                    ((ItemViewHolder) holder).button9.setText("Pending");
                    ((ItemViewHolder) holder).textView54.setText("Your Monthly report is under progress. The status will be updated within 15 Working days.");
                    ((ItemViewHolder) holder).textView54.setTextColor(Color.parseColor("#ff1d1d"));
                    ((ItemViewHolder) holder).textView54.setTextSize(12);
                }
                else if(item.getstatus().equals("1")){
                    ((ItemViewHolder) holder).button9.setBackground(context.getResources().getDrawable(R.drawable.feeddrawable_green));
                    ((ItemViewHolder) holder).button9.setText("Approved");
                    ((ItemViewHolder) holder).textView54.setText("Current Status");

                    ((ItemViewHolder) holder).textView54.setTextColor(Color.parseColor("#000000"));

                    ((ItemViewHolder) holder).textView54.setTextSize(14);


                }
                else if(item.getstatus().equals("2")){
                    ((ItemViewHolder) holder).button9.setBackground(context.getResources().getDrawable(R.drawable.feeddrawable_red));
                    ((ItemViewHolder) holder).button9.setText("Rejected");
                    ((ItemViewHolder) holder).textView54.setText("Current Status");
                    ((ItemViewHolder) holder).textView54.setTextColor(Color.parseColor("#000000"));
                    ((ItemViewHolder) holder).textView54.setTextSize(14);
                }

                ((ItemViewHolder) holder).button9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(item.getstatus().equals("1")){
                            ((Super_Salry_history) context).clikffed(position);

                        }
                        else if(item.getstatus().equals("2")) {

                            ((Super_Salry_history) context).clikffed(position);
                        }
                    }
                });

                if(((Super_Salry_history) context).getIntenttype().equals("normal")){
                     ((ItemViewHolder) holder).reflin.setVisibility(View.GONE);
                     ((ItemViewHolder) holder).duplin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).tot_lin.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).vw_lin.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).textView7w.setText("Feedback ");
                    ((ItemViewHolder) holder).vwnt_lin.setVisibility(View.GONE);
                     ((ItemViewHolder) holder).perm_lin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).perm3_lin.setVisibility(View.GONE);
                     ((ItemViewHolder) holder).trwd_lin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).fed3_lin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).fednot_lin.setVisibility(View.GONE);
                }
                else if(((Super_Salry_history) context).getIntenttype().equals("star")){
                    ((ItemViewHolder) holder).reflin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).duplin.setVisibility(View.GONE);
                }
                else if(((Super_Salry_history) context).getIntenttype().equals("super")){
                    ((ItemViewHolder) holder).reflin.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).duplin.setVisibility(View.GONE);
                    ((ItemViewHolder) holder).reflintext.setText("Super premium members referred count");
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

        TextView username, fdate,reward, tdate,uname,  played,viewed,view_duplicate,not_seen,
                feedback_below4,feedback_above3, feedback_not, permission_below4, permission_above3,
                splayed,sviewed,sview_duplicate,snot_seen,sfeedback_below4,sfeedback_above3,
                sfeedback_not,spermission_below4,spermission_above3,refname,reflintext,textView7w,textView54;
        Button feed,button9;
        ImageView type,red;
        LinearLayout reflin,duplin,tot_lin,vw_lin,vwnt_lin,perm_lin,perm3_lin,trwd_lin,fed3_lin,fednot_lin;
        ItemViewHolder(final View itemView) {
            super(itemView);

            fdate=(TextView) itemView.findViewById(R.id.uname);
            reward=(TextView) itemView.findViewById(R.id.reward);
            refname=(TextView) itemView.findViewById(R.id.refname);
            tdate=(TextView) itemView.findViewById(R.id.custname);
            played=(TextView) itemView.findViewById(R.id.dob);
            viewed=(TextView) itemView.findViewById(R.id.address);
            view_duplicate=(TextView) itemView.findViewById(R.id.pincode);
            not_seen=(TextView) itemView.findViewById(R.id.fathername);
            feedback_below4=(TextView) itemView.findViewById(R.id.custnamew);
            feedback_above3=(TextView) itemView.findViewById(R.id.mobile);
            feedback_not=(TextView) itemView.findViewById(R.id.nofeed);
            permission_below4=(TextView) itemView.findViewById(R.id.perm);
            permission_above3=(TextView) itemView.findViewById(R.id.noperm);
            splayed=(TextView) itemView.findViewById(R.id.dob1);
            sviewed=(TextView) itemView.findViewById(R.id.address1);
            sview_duplicate=(TextView) itemView.findViewById(R.id.pincode1);
            snot_seen=(TextView) itemView.findViewById(R.id.fathername1);
            sfeedback_below4=(TextView) itemView.findViewById(R.id.custnamew1);
            sfeedback_above3=(TextView) itemView.findViewById(R.id.mobile1);
            sfeedback_not=(TextView) itemView.findViewById(R.id.nofeed1);
            spermission_below4=(TextView) itemView.findViewById(R.id.perm1);
            spermission_above3=(TextView) itemView.findViewById(R.id.noperm1);
            button9=(Button) itemView.findViewById(R.id.button9);
            reflin=(LinearLayout) itemView.findViewById(R.id.reflin);
            reflintext=(TextView) itemView.findViewById(R.id.textView7ref);
            duplin=(LinearLayout) itemView.findViewById(R.id.duplin);
            tot_lin=(LinearLayout) itemView.findViewById(R.id.tot_lin);
            vw_lin=(LinearLayout) itemView.findViewById(R.id.vw_lin);
            vwnt_lin=(LinearLayout) itemView.findViewById(R.id.vwnt_lin);
            perm_lin=(LinearLayout) itemView.findViewById(R.id.perm_lin);
            perm3_lin=(LinearLayout) itemView.findViewById(R.id.perm3_lin);
            trwd_lin=(LinearLayout) itemView.findViewById(R.id.trwd_lin);
            textView7w=(TextView) itemView.findViewById(R.id.textView7w);
            fed3_lin=(LinearLayout) itemView.findViewById(R.id.fed3_lin);
            fednot_lin=(LinearLayout) itemView.findViewById(R.id.fednot_lin);
            textView54=(TextView) itemView.findViewById(R.id.textView54);
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

