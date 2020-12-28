package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.OverAllBonusActivity;
import com.elancier.healthzone.Pojo.SalesBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class OverallBonusAdapter extends ArrayAdapter<SalesBo> {

    int resource;
    ArrayList<SalesBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public OverallBonusAdapter(Context context, int resource, ArrayList<SalesBo> items ) {
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


        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.price = (TextView) convertView.findViewById(R.id.amount);
        holder.totalbonus = (TextView) convertView.findViewById(R.id.totbonus);
        holder.tottds=(TextView)convertView.findViewById(R.id.tottds);
        holder.totsc=(TextView)convertView.findViewById(R.id.totsc);
        holder.tottr=(TextView)convertView.findViewById(R.id.tottr);
        holder.totreceive=(TextView)convertView.findViewById(R.id.totrecbonus);
        holder.rootlay=(LinearLayout)convertView.findViewById(R.id.root);
        holder.productlay=(LinearLayout)convertView.findViewById(R.id.productlay);
        holder.childlay=(LinearLayout)convertView.findViewById(R.id.childlay);


        holder.date.setText(items.get(position).getSdate()+"");
        holder.price.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getSprice()))+"");
        holder.totalbonus.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getTotbonus()))+"");
        holder.tottds.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getTottds()))+"");
        holder.tottr.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getTottr()))+"");
        holder.totsc.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getTotsc()))+"");
        holder.totreceive.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).getTotreciev()))+"");
        if(items.get(position).isSaleselect()){
            holder.productlay.setVisibility(View.VISIBLE);

        }
        else{
            holder.productlay.setVisibility(View.GONE);
        }

        holder.rootlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(items.get(position).isSaleselect()){
                    items.get(position).setSaleselect(false);
                }
                else{
                    items.get(position).setSaleselect(true);
                }
                notifyDataSetChanged();
            }
        });
        //  Log.i("utilsssprosize",position+"          "+items.get(position).salesprodicts.size()+"");
        for(int k=0;k<items.get(position).bonussublist.size();k++){
            View v=layoutInflater.inflate(R.layout.overall_bonus_sub_item,null);
            LinearLayout layout=(LinearLayout)v.findViewById(R.id.layout);
            TextView pqty=(TextView) v.findViewById(R.id.qty);
            TextView name=(TextView) v.findViewById(R.id.proname);
            TextView totprice=(TextView) v.findViewById(R.id.totprice);
            pqty.setText(items.get(position).bonussublist.get(k).getType()+"");
            totprice.setText(String.format("Rs.%.2f",Double.parseDouble(items.get(position).bonussublist.get(k).getBonusamt()))+"");
            name.setText(items.get(position).bonussublist.get(k).getUniqueid()+"");
            layout.setId(k);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((OverAllBonusActivity)context).Detailsdialog(items.get(position).bonussublist.get(v.getId()));
                }
            });
            // holder.childlay.removeViewAt(position);

            holder.childlay.addView(v);
        }





        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView price;
        public TextView totalbonus;
        public TextView tottds,totsc,tottr,totreceive;

        LinearLayout productlay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
