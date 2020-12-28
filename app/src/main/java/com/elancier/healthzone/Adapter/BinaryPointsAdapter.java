package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.PointsBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class BinaryPointsAdapter extends ArrayAdapter<PointsBo> {

    int resource;
    ArrayList<PointsBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public BinaryPointsAdapter(Context context, int resource, ArrayList<PointsBo> items ) {
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


        holder.date = (TextView) convertView.findViewById(R.id.spdate);
        holder.id = (TextView) convertView.findViewById(R.id.bonusid);
        holder.amount = (TextView) convertView.findViewById(R.id.bonusamt);



        holder.date.setText(items.get(position).getPointdate());
        holder.id.setText(items.get(position).getPointid()+"");

        holder.amount.setText(items.get(position).getPointtotal());
        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView point;
        public TextView amount;
        public TextView id;

        LinearLayout productlay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
