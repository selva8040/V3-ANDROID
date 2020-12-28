package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.SponsorBonusBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class SponsorBonusAdapter extends ArrayAdapter<SponsorBonusBo> {

    int resource;
    ArrayList<SponsorBonusBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public SponsorBonusAdapter(Context context, int resource, ArrayList<SponsorBonusBo> items ) {
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
        holder.price = (TextView) convertView.findViewById(R.id.spplan);
        holder.name = (TextView) convertView.findViewById(R.id.spname);






        holder.date.setText(items.get(position).getSpdate());
        holder.price.setText(items.get(position).getSppintype()+"");

        holder.name.setText(items.get(position).getSpname());







































        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView price;
        public TextView total;
        public TextView name;

        LinearLayout productlay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
