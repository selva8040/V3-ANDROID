package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.elancier.healthzone.Pojo.DirectTeamPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class DirectTeamAdapter extends ArrayAdapter<DirectTeamPojo> {

    int resource;
    ArrayList<DirectTeamPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public DirectTeamAdapter(Context context, int resource,ArrayList<DirectTeamPojo> items ) {
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

        holder.name = (TextView) convertView.findViewById(R.id.username);
        holder.design = (TextView) convertView.findViewById(R.id.designation);
        holder.uname = (TextView) convertView.findViewById(R.id.user_id);
        holder.dob = (TextView) convertView.findViewById(R.id.date);
        holder.mob = (TextView) convertView.findViewById(R.id.mobile);

        holder.name.setText(items.get(position).getName());
        holder.design.setText(items.get(position).getDesignation());
        holder.uname.setText(items.get(position).getUsername());
        holder.dob.setText(items.get(position).getDate());
        holder.mob.setText("Ph: "+items.get(position).getMobile());


        return convertView;
    }


    private static class ViewHolder {
        public TextView name;
        public TextView design;
        public TextView uname;
        public TextView dob;
        public TextView mob;
        public ImageView remove;
    }
}
