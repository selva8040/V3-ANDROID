package com.elancier.healthzone.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.ReportsPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

public class PurchaseLoyalityListAdapter extends ArrayAdapter<ReportsPojo> {

    int resource;
    Context context;
    LayoutInflater mInflater;
    ArrayList<ReportsPojo> items;

    public PurchaseLoyalityListAdapter(Context context, int resource, ArrayList<ReportsPojo> items) {
        super(context, resource, items);
        mInflater = LayoutInflater.from(context);
        this.resource=resource;
        this.context=context;
        this.items = items;
    }


    @SuppressLint("NewApi")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        LinearLayout alertView = null;
        holder = new ViewHolder();
        if(convertView==null)
        {
            convertView = mInflater.inflate(resource, alertView, true);
            convertView.setTag(holder);
            alertView = (LinearLayout)convertView;
        }
        else
        {
            alertView = (LinearLayout) convertView;
        }
        holder.uname = (TextView) convertView.findViewById(R.id.uname);
      /*  holder.tds = (TextView) convertView.findViewById(R.id.tds);
        holder.pvalue = (TextView) convertView.findViewById(R.id.pvalue);
        holder.tvalue = (TextView) convertView.findViewById(R.id.tvalue);
        holder.name = (TextView) convertView.findViewById(R.id.name);*/


      /*  holder.uname.setText(items.get(position).getUname());
        holder.tds.setText(items.get(position).getTds());
        holder.pvalue.setText(items.get(position).getPvalue());
        holder.tvalue.setText(items.get(position).getTvalue());
        holder.name.setText(items.get(position).getName());*/

        return alertView;
    }

    private static class ViewHolder {
        public LinearLayout bg;
        public TextView uname,tds,pvalue,tvalue,name;

    }


}

