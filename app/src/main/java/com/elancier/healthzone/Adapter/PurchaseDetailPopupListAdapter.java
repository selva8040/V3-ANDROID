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
public class PurchaseDetailPopupListAdapter extends ArrayAdapter<AutofillPojo> {

    int resource;
    ArrayList<AutofillPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public PurchaseDetailPopupListAdapter(Context context, int resource, ArrayList<AutofillPojo> items) {
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

        holder.pname = (TextView) convertView.findViewById(R.id.pname);
        holder.mrp = (TextView) convertView.findViewById(R.id.mrp);
        holder.qty = (TextView) convertView.findViewById(R.id.qty);
        holder.total = (TextView) convertView.findViewById(R.id.total);

        holder.pname.setText(items.get(position).getPname());
        holder.mrp.setText(items.get(position).getMrp());
        holder.qty.setText(items.get(position).getQty());
        holder.total.setText(items.get(position).getTotal());

        return convertView;
    }


    private static class ViewHolder {
        public TextView pname;
        public TextView mrp;
        public TextView qty;
        public TextView total;
    }
}
