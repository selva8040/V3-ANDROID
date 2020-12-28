package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.MenuItemBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class MenuItemAdapter extends ArrayAdapter<MenuItemBo> {

    int resource;
    ArrayList<MenuItemBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public MenuItemAdapter(Context context, int resource, ArrayList<MenuItemBo> items ) {
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

        holder.name = (TextView) convertView.findViewById(R.id.item_name);
        holder.name.setText(items.get(position).getMenuname()+"");



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
