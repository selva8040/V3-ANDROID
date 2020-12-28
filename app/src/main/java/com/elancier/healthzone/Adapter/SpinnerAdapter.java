package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.R;

/**
 * Created by System4 on 2/28/2017.
 */
public class SpinnerAdapter extends BaseAdapter {


    int resource;
    Context context;
    LayoutInflater mInflater;
    String[] items;

    public SpinnerAdapter(Context context, int resource, String[] items) {

        mInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder = new ViewHolder();
        LinearLayout alertView = null;
        if (convertView == null) {
            convertView = mInflater.inflate(resource, alertView, true);
            convertView.setTag(holder);
            alertView = (LinearLayout) convertView;
        } else {
            alertView = (LinearLayout) convertView;
        }
        holder.text = (TextView) alertView.findViewById(R.id.spinner_text);

        holder.text.setText(items[position]);

        return alertView;
    }


    private static class ViewHolder {
        public TextView text,accountno,accountid;

    }
}
