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
public class LiveBonusListAdapter extends ArrayAdapter<AutofillPojo> {

    int resource;
    ArrayList<AutofillPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public LiveBonusListAdapter(Context context, int resource, ArrayList<AutofillPojo> items) {
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

        holder.datetime = (TextView) convertView.findViewById(R.id.datetime);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.level = (TextView) convertView.findViewById(R.id.level);
        holder.bonus = (TextView) convertView.findViewById(R.id.bonus);
        holder.tds = (TextView) convertView.findViewById(R.id.tds);
        holder.netamt = (TextView) convertView.findViewById(R.id.netamt);

        holder.datetime.setText(items.get(position).getDatetime());
        holder.date.setText(items.get(position).getDate());
        holder.level.setText(items.get(position).getLavel());
        holder.bonus.setText(items.get(position).getBonus());
        holder.tds.setText(items.get(position).getTds());
        holder.netamt.setText(items.get(position).getNetamt());

        return convertView;
    }


    private static class ViewHolder {
        public TextView datetime;
        public TextView date;
        public TextView level;
        public TextView bonus;
        public TextView tds;
        public TextView netamt;
    }
}
