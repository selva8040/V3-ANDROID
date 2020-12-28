package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.PinBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class CommissionAdapter extends ArrayAdapter<PinBo> {

    int resource;
    ArrayList<PinBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public CommissionAdapter(Context context, int resource, ArrayList<PinBo> items ) {
        super(context, resource);
        layoutInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        LinearLayout alertView = null;

        convertView = layoutInflater.inflate(resource,null);
        convertView.setTag(holder);


        holder.date = (TextView) convertView.findViewById(R.id.datetime);
        holder.level = (TextView) convertView.findViewById(R.id.level);
        holder.amount = (TextView) convertView.findViewById(R.id.amount);
       // holder.status = (TextView) convertView.findViewById(R.id.status);
        holder.whom = (TextView) convertView.findViewById(R.id.whom);



        holder.date.setText(items.get(position).getDate());
        holder.level.setText(items.get(position).getLevel()+"");

        holder.amount.setText(items.get(position).getAmount());
       // holder.status.setText(items.get(position).getPinstatus()+"");
        holder.whom.setText(items.get(position).getWhom()+"");

       /* if(items.get(position).getPinstatus().equalsIgnoreCase("Activate")){
            holder.status.setTextColor(context.getResources().getColor(R.color.nav_head));
        }
        else if(items.get(position).getPinstatus().equalsIgnoreCase("Not Activate")){
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
        }*/
        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView status;
        public TextView amount;
        public TextView level;
        public TextView whom;

        LinearLayout productlay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
