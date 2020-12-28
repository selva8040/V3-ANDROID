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
public class BonusAdapter extends ArrayAdapter<ReportsPojo> {

    int resource;
    ArrayList<ReportsPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public BonusAdapter(Context context, int resource, ArrayList<ReportsPojo> items ) {
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

        holder.pqid = (TextView) convertView.findViewById(R.id.pqid);
        holder.date = (TextView) convertView.findViewById(R.id.dateval);
        holder.price = (TextView) convertView.findViewById(R.id.bv);
        holder.name = (TextView) convertView.findViewById(R.id.user);
        holder.uname=(TextView)convertView.findViewById(R.id.name);
        holder.bonus=(TextView)convertView.findViewById(R.id.bonus);
        holder.total=(TextView)convertView.findViewById(R.id.ibv);


        holder.pqid.setText("# "+items.get(position).getPqid());
        holder.date.setText(items.get(position).getDate());
        holder.price.setText("Rs."+items.get(position).getAmount()+"");
        holder.bonus.setText("Rs."+items.get(position).getBonus());
        holder.total.setText("Rs."+items.get(position).getIbv());
        holder.name.setText(items.get(position).getUsername());
        holder.uname.setText(items.get(position).getUserid());


        return convertView;
    }


    private static class ViewHolder {
        public TextView pqid;
        public TextView date;
        public TextView price;
        public TextView total;
        public TextView name;
        public TextView uname;
        public TextView bonus;
        public ImageView remove;
    }
}
