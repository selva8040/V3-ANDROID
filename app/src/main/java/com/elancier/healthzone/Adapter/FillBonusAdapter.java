package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.FillbonusBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class FillBonusAdapter extends ArrayAdapter<FillbonusBo> {

    int resource;
    ArrayList<FillbonusBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public FillBonusAdapter(Context context, int resource, ArrayList<FillbonusBo> items ) {
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

        convertView = layoutInflater.inflate(resource,null);
        convertView.setTag(holder);


        holder.date = (TextView) convertView.findViewById(R.id.spdate);
        holder.id = (TextView) convertView.findViewById(R.id.level);
        holder.point = (TextView) convertView.findViewById(R.id.bonuspoint);
        holder.amount = (TextView) convertView.findViewById(R.id.bonusamt);
        holder.position = (TextView) convertView.findViewById(R.id.position);



        holder.date.setText(items.get(position).getDate());
        holder.id.setText(items.get(position).getLevel()+"");

        holder.amount.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getAmount())));
        holder.point.setText(items.get(position).getPosition());
        if(items.get(position).getPosition().equalsIgnoreCase("1")){
            holder.position.setText("st position");
        }
        else if(items.get(position).getPosition().equalsIgnoreCase("2")){
            holder.position.setText("nd position");
        }
        else if(items.get(position).getPosition().equalsIgnoreCase("3")){
            holder.position.setText("rd position");
        }
        else{
            holder.position.setText("th position");
        }

        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView point;
        public TextView amount;
        public TextView id;
        public TextView position;


    }
}
