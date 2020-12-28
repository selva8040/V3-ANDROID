package com.elancier.healthzone.Adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elancier.healthzone.Pojo.Genosubbo;
import com.elancier.healthzone.R;

import java.util.ArrayList;

/**
 * Created by Manikandan on 6/21/2017.
 */
public class GeneiologyAdapter extends ArrayAdapter<Genosubbo> {

    int resource;
    ArrayList<Genosubbo> items;
    Context context;
    LayoutInflater layoutInflater;

    public GeneiologyAdapter(Context context, int resource,ArrayList<Genosubbo> items ) {
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

        holder.name = (TextView) convertView.findViewById(R.id.treename);
        Log.i("valuesssssssssssss",items.get(position).getTsusername()+"");

        holder.name.setText(Html.fromHtml("<font><b>" +"User: "+ "</b></font>" +items.get(position).getTsusername()+"<font><b>" +"    Name: "+ "</b></font>" +items.get(position).getTsname()+"<font><b>" +"    Desig: "+ "</b></font>" +items.get(position).getTsdesign()
        +"<font><b>" +"    IBV: "+ "</b></font>" +items.get(position).getTsibv()+"<font><b>" +"    GBV: "+ "</b></font>" +items.get(position).getTsgbv()));


        return convertView;
    }


    private static class ViewHolder {
        public TextView name;

    }
}
