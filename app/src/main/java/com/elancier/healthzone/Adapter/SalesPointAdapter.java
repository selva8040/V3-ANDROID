package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.SalesPointPojo;
import com.elancier.healthzone.R;
import com.elancier.healthzone.SalesPoint;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class SalesPointAdapter extends ArrayAdapter<SalesPointPojo> {

    int resource;
    ArrayList<SalesPointPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public SalesPointAdapter(Context context, int resource, ArrayList<SalesPointPojo> items) {
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
        final ViewHolder holder = new ViewHolder();
        LinearLayout alertView = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, alertView, true);
            convertView.setTag(holder);
            alertView = (LinearLayout) convertView;
        }

        holder.productname = (TextView) convertView.findViewById(R.id.product_name);
        holder.mrp = (TextView) convertView.findViewById(R.id.mrp);
        holder.qty = (TextView) convertView.findViewById(R.id.qty);
        holder.subtotal = (TextView) convertView.findViewById(R.id.subtotal);
        holder.remove = (ImageView) convertView.findViewById(R.id.remove);

        holder.productname.setText(items.get(position).getProduct_name());
        holder.mrp.setText(items.get(position).getMyprice() + "");
        holder.qty.setText(items.get(position).getQty() + "");
          holder.subtotal.setText(String.format("%.2f",(items.get(position).getMyprice() * items.get(position).getQty())));

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                notifyDataSetChanged();
                Log.i("Pos", position + "");
                ((SalesPoint) context).Refresh(items);
                ((SalesPoint) context).setTotal();
            }
        });


        return convertView;
    }


    private static class ViewHolder {
        public TextView customername;
        public TextView productname;
        public TextView mobile;
        public TextView category;
        public TextView subCategory;
        public TextView mrp;
        public TextView stock;
        public TextView qty;
        public TextView subtotal;
        public ImageView remove;
    }
}
