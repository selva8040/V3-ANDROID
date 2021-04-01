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
public class WalletBookAdapter extends ArrayAdapter<PassbookBo> {

    int resource;
    ArrayList<PassbookBo> items;
    Context context;
    LayoutInflater layoutInflater;

    public WalletBookAdapter(Context context, int resource, ArrayList<PassbookBo> items ) {
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
        holder.debit = (TextView) convertView.findViewById(R.id.debit);
        holder.credit = (TextView) convertView.findViewById(R.id.credit);
        holder.mode = (TextView) convertView.findViewById(R.id.mode);
        holder.balance=(TextView)convertView.findViewById(R.id.balance);

        holder.mode.setText(items.get(position).getType());
        holder.balance.setText(items.get(position).getBalance());
        holder.date.setText(items.get(position).getDate());

         if(items.get(position).getMode().equals("Debit")) {
             holder.debit.setText(items.get(position).getAmount());
             holder.credit.setText("");
           // holder.debit.setTextColor(ContextCompat.getColor(context, R.color.status_red));
         }
        else{
             holder.credit.setText(items.get(position).getAmount());
             holder.debit.setText("");
            // holder.debit.setTextColor(ContextCompat.getColor(context, R.color.status_green));
         }



        return convertView;
    }


    private static class ViewHolder {
        public TextView debit;
        public TextView date;
        public TextView mode;
        public TextView balance;
        public LinearLayout creditlay;
        public TextView credit;
        public TextView date1;
        public TextView total1;
        public TextView debit1;
        public LinearLayout creditlay1;

    }
}
