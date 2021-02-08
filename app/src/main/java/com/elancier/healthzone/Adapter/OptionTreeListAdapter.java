package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.elancier.healthzone.OptionTree;
import com.elancier.healthzone.Pojo.AutofillPojo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class OptionTreeListAdapter extends ArrayAdapter<AutofillPojo> {

    int resource;
    ArrayList<AutofillPojo> items;
    Context context;
    LayoutInflater layoutInflater;

    public OptionTreeListAdapter(Context context, int resource, ArrayList<AutofillPojo> items) {
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
        ConstraintLayout alertView = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, alertView, true);
            convertView.setTag(holder);
            alertView = (ConstraintLayout) convertView;
        }

        holder.doj = (TextView) convertView.findViewById(R.id.doj);
        //holder.uname = (TextView) convertView.findViewById(R.id.uname);
        holder.unique = (TextView) convertView.findViewById(R.id.unique);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.uname = (TextView) convertView.findViewById(R.id.uname);
        holder.desig = (TextView) convertView.findViewById(R.id.desig);
        holder.super_desig1 = (TextView) convertView.findViewById(R.id.super_desig);
        holder.super_desig2 = (TextView) convertView.findViewById(R.id.super_desig2);
        holder.cons= (ConstraintLayout) convertView.findViewById(R.id.cons);
        holder.linearLayout = (TextView) convertView.findViewById(R.id.textView84);
        holder.welc_desig=(TextView) convertView.findViewById(R.id.welc_desig);
        holder.verify=(Button) convertView.findViewById(R.id.button15);
        holder.welccard=(CardView) convertView.findViewById(R.id.cardView10welc);



       /* holder.tds = (TextView) convertView.findViewById(R.id.tds);
        holder.netamt = (TextView) convertView.findViewById(R.id.netamt);*/

        if(items.get(position).getProduct().equals("Green")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.color6));
            holder.uname.setTextColor(context.getResources().getColor(R.color.color6));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cons.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_two));
            }
        } else if (items.get(position).getProduct().equals("Red")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.color12));
            holder.uname.setTextColor(context.getResources().getColor(R.color.color12));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cons.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_one));

            }



        }
        else if (items.get(position).getProduct().equals("Blue")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.color22));
            holder.uname.setTextColor(context.getResources().getColor(R.color.color22));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cons.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_three));

            }
        }
        else if (items.get(position).getProduct().equals("Purple")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.color23));
            holder.uname.setTextColor(context.getResources().getColor(R.color.color23));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cons.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_four));

            }
        }
        else if (items.get(position).getProduct().equals("Skyblue")){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(R.color.color24));
            holder.uname.setTextColor(context.getResources().getColor(R.color.color24));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cons.setBackground(context.getResources().getDrawable(R.drawable.gradient_background_five));

            }
        }

       /* if(items.get(position).getUnique().equals("0")) {
            holder.verify.setVisibility(View.VISIBLE);

        }
        else{
            holder.verify.setVisibility(View.GONE);
        }*/

        if(items.get(position).getSub_users().equals("Welcome Pin")){
            holder.verify.setVisibility(View.VISIBLE);

        }
        else{
            //holder.welccard.setVisibility(View.GONE);
            holder.verify.setVisibility(View.GONE);

        }

        holder.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OptionTree)context).openpin(items.get(position).getUname());
            }
        });

        /*int color_arr[] = {R.color.ncolor1, R.color.ncolor2, R.color.ncolor3, R.color.ncolor4, R.color.ncolor5,
                R.color.ncolor6, R.color.ncolor7, R.color.sample1, R.color.sample2, R.color.sample3, R.color.sample4,
                R.color.status_green, R.color.status_red, R.color.lgreen};

        int rnd = new Random().nextInt(color_arr.length);

        holder.linearLayout.setBackgroundResource(color_arr[rnd]);*/

        holder.doj.setText(items.get(position).getMobile());
       // holder.uname.setText(items.get(position).getUname());
        holder.unique.setText(items.get(position).getUname());
        holder.name.setText(items.get(position).getName());
        holder.uname.setText(items.get(position).getSub_users());
        holder.desig.setText(items.get(position).getMobile());
        holder.super_desig1.setText(items.get(position).getMrp());
        holder.super_desig2.setText(items.get(position).getQty());
        holder.welc_desig.setText(items.get(position).getDoj());



        /*holder.tds.setText(items.get(position).getTds());
        holder.netamt.setText(items.get(position).getNetamt());*/

        return convertView;
    }


    private static class ViewHolder {
        public TextView doj;
        public TextView uname;
        public TextView unique;
        public TextView name;
        public TextView desig;
         public TextView super_desig1;
        public TextView super_desig2;
        public TextView tds;
        public ConstraintLayout cons;
        public TextView linearLayout;
        public TextView netamt;
        public TextView welc_desig;
        public Button verify;
        public CardView welccard;

    }
}
