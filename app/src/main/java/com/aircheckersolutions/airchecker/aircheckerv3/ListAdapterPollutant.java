package com.aircheckersolutions.airchecker.aircheckerv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapterPollutant extends BaseAdapter{

    Context context;
    Pollutant[] data;
    private static LayoutInflater inflater = null;

    public ListAdapterPollutant(Context context, Pollutant[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.listitem_pollutant, null);
            holder = new ViewHolder();
            holder.txtname = (TextView) convertView.findViewById(R.id.text_item);
            holder.prgbar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TextView text = (TextView) convertView.findViewById(R.id.text_item);
        holder.txtname.setText(data[position].name);
        holder.prgbar.setProgress((int)(data[position].currentValue*100));
        //convertView.setBackgroundResource(R.drawable.smiley_happy);
        return convertView;
    }
}

class ViewHolder{
    ImageView imgview;
    TextView txtname;
    ProgressBar prgbar;
}
