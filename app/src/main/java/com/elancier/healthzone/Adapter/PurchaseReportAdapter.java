package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.SalesBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class PurchaseReportAdapter extends ArrayAdapter<SalesBo> {

    int resource;
    ArrayList<SalesBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public PurchaseReportAdapter(Context context, int resource, ArrayList<SalesBo> items) {
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

        convertView = layoutInflater.inflate(resource, null);
        convertView.setTag(holder);


        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.amount = (TextView) convertView.findViewById(R.id.amount);
        holder.pqid = (TextView) convertView.findViewById(R.id.pqid);
        holder.store = (TextView) convertView.findViewById(R.id.store);
        holder.storeid = (TextView) convertView.findViewById(R.id.storeid);
        holder.rootlay = (LinearLayout) convertView.findViewById(R.id.root);
        holder.productlay = (LinearLayout) convertView.findViewById(R.id.productlay);
        holder.childlay = (LinearLayout) convertView.findViewById(R.id.childlay);


        holder.date.setText(items.get(position).getSdate());
        holder.store.setText(items.get(position).getSname());
        holder.storeid.setText(items.get(position).getStoreid());
        holder.pqid.setText("# " + items.get(position).getAmount() + "");
        holder.amount.setText(String.format("Rs.%.2f", Double.parseDouble(items.get(position).getAmount())));
        holder.store.setText(items.get(position).getSname());
        if (items.get(position).isSaleselect()) {
            holder.productlay.setVisibility(View.VISIBLE);

        } else {
            holder.productlay.setVisibility(View.GONE);
        }

        holder.rootlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(position).isSaleselect()) {
                    items.get(position).setSaleselect(false);
                } else {
                    items.get(position).setSaleselect(true);
                }
                notifyDataSetChanged();
            }
        });
        //  Log.i("utilsssprosize",position+"          "+items.get(position).salesprodicts.size()+"");
        for (int k = 0; k < items.get(position).salesprodicts.size(); k++) {
            View v = layoutInflater.inflate(R.layout.sales_sub_report, null);
            TextView pname = (TextView) v.findViewById(R.id.proname);
            TextView pqty = (TextView) v.findViewById(R.id.qty);
            TextView totprice = (TextView) v.findViewById(R.id.totprice);
            Log.i("productnameeeeeeee", position + "     " + k + "        " + items.get(position).salesprodicts.get(k).getPname());
            pname.setText(items.get(position).salesprodicts.get(k).getPname());
            pqty.setText(items.get(position).salesprodicts.get(k).getPmrp() + " X " + items.get(position).salesprodicts.get(k).getPqty());
            totprice.setText(String.format("Rs.%.2f", Double.parseDouble(items.get(position).salesprodicts.get(k).getPtotal())));
            // holder.childlay.removeViewAt(position);

            holder.childlay.addView(v);
        }


        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView amount;
        public TextView total;
        public TextView pqid;
        public TextView store;
        public TextView storeid;

        LinearLayout productlay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
