package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.AutofillPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class PurchaseDetailListAdapter extends ArrayAdapter<AutofillPojo> {

    int resource;
    ArrayList<AutofillPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public PurchaseDetailListAdapter(Context context, int resource, ArrayList<AutofillPojo> items) {
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

        holder.sid = (TextView) convertView.findViewById(R.id.sid);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.store = (TextView) convertView.findViewById(R.id.store);
        holder.total = (TextView) convertView.findViewById(R.id.total);

        holder.sid.setText(items.get(position).getSid());
        holder.date.setText(items.get(position).getDate());
        holder.store.setText(items.get(position).getBname());
        holder.total.setText(items.get(position).getAmount());

        return convertView;
    }


    private static class ViewHolder {
        public TextView sid;
        public TextView date;
        public TextView store;
        public TextView total;
    }
}
