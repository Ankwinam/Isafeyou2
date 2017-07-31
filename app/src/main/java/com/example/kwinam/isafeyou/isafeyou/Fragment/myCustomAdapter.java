package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;

import java.util.ArrayList;

/**
 * Created by Taewoong on 2017-08-01.
 */

public class myCustomAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    int layout;
    ArrayList<Item> alist;
    public myCustomAdapter(Context context, int layout, ArrayList<Item> alist){
        this.context = context;
        this.layout = layout;
        this.alist = alist;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        TextView tv_item_name = (TextView) convertView.findViewById(R.id.item_name_txt);
        TextView tv_item_phonenumber = (TextView) convertView.findViewById(R.id.item_phonenumber_txt);
        LinearLayout item_layout = (LinearLayout) convertView.findViewById(R.id.item_layout);

        tv_item_name.setText(alist.get(position).getName());
        tv_item_phonenumber.setText(alist.get(position).getPhonenumber());

        return convertView;
    }
}
