package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.StockPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class StockListAdapter extends ArrayAdapter<StockPojo> {

    int resource;
    ArrayList<StockPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public StockListAdapter(Context context, int resource, ArrayList<StockPojo> items ) {
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

        holder.productname = (TextView) convertView.findViewById(R.id.product_name);
        holder.purchase = (TextView) convertView.findViewById(R.id.purchase);
        holder.sales = (TextView) convertView.findViewById(R.id.sales);
        holder.inhand = (TextView) convertView.findViewById(R.id.inhand);

        holder.productname.setText(items.get(position).getProductname());
       holder.purchase.setText(items.get(position).getPurchase()+"");
        holder.sales.setText(items.get(position).getSales()+"");
        holder.inhand.setText(items.get(position).getInhand()+"");

        return convertView;
    }


    private static class ViewHolder {
        public TextView productname;
        public TextView purchase;
        public TextView sales;
        public TextView inhand;
    }
}
