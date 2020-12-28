package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.PassbookBo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class PassbookAdapter extends ArrayAdapter<PassbookBo> {

    int resource;
    ArrayList<PassbookBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public PassbookAdapter(Context context, int resource, ArrayList<PassbookBo> items ) {
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

        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.amount = (TextView) convertView.findViewById(R.id.credit);
        holder.total = (TextView) convertView.findViewById(R.id.total);
        holder.debit=(TextView)convertView.findViewById(R.id.debit);
        holder.creditlay=(LinearLayout)convertView.findViewById(R.id.creditlay);
        holder.date1 = (TextView) convertView.findViewById(R.id.date1);
        holder.amount1 = (TextView) convertView.findViewById(R.id.credit1);
        holder.total1 = (TextView) convertView.findViewById(R.id.total1);
        holder.debit1=(TextView)convertView.findViewById(R.id.debit1);
        holder.creditlay1=(LinearLayout)convertView.findViewById(R.id.creditlay1);

         if(!items.get(position).getType().equals("")) {
             holder.creditlay.setVisibility(View.VISIBLE);
             holder.amount.setText(items.get(position).getCredit());
             holder.debit.setText(items.get(position).getDebit());
             holder.date.setText(items.get(position).getDate());
             holder.total.setText(items.get(position).getBalance() + "");
         }
        else{
             holder.creditlay.setVisibility(View.GONE);
         }
        if(!items.get(position).getType1().equals("")){
            holder.creditlay1.setVisibility(View.VISIBLE);
            holder.amount1.setText(items.get(position).getCredit1());
            holder.debit1.setText(items.get(position).getDebit1());
            holder.date1.setText(items.get(position).getDate1());
            holder.total1.setText(items.get(position).getBalance1() + "");
        }
        else{
            holder.creditlay1.setVisibility(View.GONE);
        }



        return convertView;
    }


    private static class ViewHolder {
        public TextView amount;
        public TextView date;
        public TextView total;
        public TextView debit;
        public LinearLayout creditlay;
        public TextView amount1;
        public TextView date1;
        public TextView total1;
        public TextView debit1;
        public LinearLayout creditlay1;

    }
}
