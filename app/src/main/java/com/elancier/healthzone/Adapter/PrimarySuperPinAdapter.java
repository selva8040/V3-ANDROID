package com.elancier.healthzone.Adapter;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.elancier.healthzone.CircleImageView;
import com.elancier.healthzone.Pojo.PinBo;
import com.elancier.healthzone.PrimeSuperPinActivity;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class PrimarySuperPinAdapter extends ArrayAdapter<PinBo> {

    int resource;
    ArrayList<PinBo> items;
    Activity context;
    LayoutInflater layoutInflater;

    public PrimarySuperPinAdapter(Activity context, int resource, ArrayList<PinBo> items ) {
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
        final ViewHolder holder = new ViewHolder();
        LinearLayout alertView = null;

        convertView = layoutInflater.inflate(resource,null);
        convertView.setTag(holder);


        holder.date = (TextView) convertView.findViewById(R.id.spdate);
        holder.copy = (TextView) convertView.findViewById(R.id.copy);
        holder.id = (TextView) convertView.findViewById(R.id.bonusid);
        holder.amount = (TextView) convertView.findViewById(R.id.bonusamt);
        holder.status = (TextView) convertView.findViewById(R.id.status);
        holder.username = (TextView) convertView.findViewById(R.id.username);
        holder.linear_lay = (ConstraintLayout) convertView.findViewById(R.id.linear_lay);
        holder.img = (CircleImageView) convertView.findViewById(R.id.userimage);



        holder.date.setText(items.get(position).getPindate());
        holder.id.setText(items.get(position).getPinno()+"");

        holder.amount.setText(items.get(position).getSaleid());
        holder.status.setText(items.get(position).getPinstatus()+"");
        holder.username.setText(items.get(position).getUsername()+"");



        if(items.get(position).gettype().equalsIgnoreCase("Free")){
            holder.img.setImageResource(R.mipmap.freeicon);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Premium"))
        {
            holder.img.setImageResource(R.mipmap.premiumicon);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Star Premium"))
        {
            holder.img.setImageResource(R.mipmap.starpremiumnew);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Super Premium"))
        {
            holder.img.setImageResource(R.mipmap.superpremiumnew);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Pre Super Premium"))
        {
            holder.img.setImageResource(R.mipmap.prepremium);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Super Salary Achiever"))
        {
            holder.img.setImageResource(R.mipmap.supersalary);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Trial"))
        {
            holder.img.setImageResource(R.mipmap.trialicon);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Entrance"))
        {
            holder.img.setImageResource(R.mipmap.entryicon);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Base(X)"))
        {
            holder.img.setImageResource(R.mipmap.basepins_xs_foreground);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Base Pin"))
        {
            holder.img.setImageResource(R.mipmap.basepins_img_foreground);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Base(N)"))
        {
            holder.img.setImageResource(R.mipmap.basen_pic);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Student"))
        {
            holder.img.setImageResource(R.mipmap.student);

        }
        else if(items.get(position).gettype().equalsIgnoreCase("Welcome Plan"))
        {
            holder.img.setImageResource(R.mipmap.welcomeimg);

        }


        if(items.get(position).getPinstatus().equalsIgnoreCase("Activated")){
            holder.status.setTextColor(context.getResources().getColor(R.color.nav_head));
            holder.copy.setVisibility(View.GONE);
        }
        else /*if(items.get(position).getPinstatus().equalsIgnoreCase("Not Activate"))*/{
            holder.status.setTextColor(context.getResources().getColor(R.color.red));
            holder.copy.setVisibility(View.VISIBLE);
        }
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(items.get(position).getPinno());
                Toast.makeText(context, "Pin Copied, you can share anywhere!", Toast.LENGTH_LONG).show();
            }
        });


        holder.linear_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!items.get(position).getPinstatus().equalsIgnoreCase("Activated")) {

                    if (items.get(position).isSelect()) {
                            items.get(position).setSelect(false);
                            holder.linear_lay.setBackgroundColor(context.getResources().getColor(R.color.white));
                            ((PrimeSuperPinActivity) context).removePin(items.get(position).getPinno());
                    } else {
                        items.get(position).setSelect(true);
                        holder.linear_lay.setBackgroundColor(context.getResources().getColor(R.color.lightgreen));
                        ((PrimeSuperPinActivity) context).addPin(items.get(position).getPinno());
                    }

                }else {
                    Toast.makeText(context, "Pin already activated", Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (items.get(position).isSelect()){
            holder.linear_lay.setBackgroundColor(context.getResources().getColor(R.color.lightgreen));
        }else {
            holder.linear_lay.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        return convertView;
    }


    private static class ViewHolder {
        public TextView date;
        public TextView status;
        public TextView amount;
        public CircleImageView img;
        public TextView id,copy;
        public TextView username;

        ConstraintLayout linear_lay;
        LinearLayout childlay;
        LinearLayout rootlay;
    }
}
