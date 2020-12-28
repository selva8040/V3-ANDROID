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
public class CustomerListAdapter extends ArrayAdapter<ReportsPojo> {

    int resource;
    ArrayList<ReportsPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public CustomerListAdapter(Context context, int resource, ArrayList<ReportsPojo> items ) {
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
        holder.mobile = (TextView) convertView.findViewById(R.id.mobile);
        holder.ibv = (TextView) convertView.findViewById(R.id.ibv);
        holder.cid=(TextView)convertView.findViewById(R.id.cid);
        //holder.percentage=(TextView)convertView.findViewById(R.id.percentage);


        holder.cid.setText(items.get(position).getUsername());
        holder.date.setText(items.get(position).getDate());
        holder.mobile.setText("Rs."+items.get(position).getAmount()+"");
        holder.name.setText(items.get(position).getName());


        return convertView;
    }


    private static class ViewHolder {
        public TextView cid;
        public TextView date;
        public TextView mobile;
        public TextView ibv;
        public TextView name;
        public TextView percentage;
        public ImageView remove;
    }
}
