package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.ReportsPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class GbvReportsAdapter extends ArrayAdapter<ReportsPojo> {

    int resource;
    ArrayList<ReportsPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public GbvReportsAdapter(Context context, int resource, ArrayList<ReportsPojo> items ) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        LinearLayout alertView = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, alertView, true);
            convertView.setTag(holder);
            alertView = (LinearLayout) convertView;
        }

        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.amount = (TextView) convertView.findViewById(R.id.amount);
        holder.ibv = (TextView) convertView.findViewById(R.id.ibv);
        holder.uname=(TextView)convertView.findViewById(R.id.uname);
        holder.percentage=(TextView)convertView.findViewById(R.id.percentage);


        holder.uname.setText("# "+items.get(position).getUsername());
        holder.date.setText(items.get(position).getDate());
        holder.amount.setText("Rs."+items.get(position).getAmount()+"");
        holder.ibv.setText("Rs."+items.get(position).getIbv());
        holder.name.setText(items.get(position).getName());
        holder.percentage.setText(items.get(position).getPercentage());


        return convertView;
    }


    private static class ViewHolder {
        public TextView uname;
        public TextView date;
        public TextView amount;
        public TextView ibv;
        public TextView name;
        public TextView percentage;
        public ImageView remove;
    }
}
