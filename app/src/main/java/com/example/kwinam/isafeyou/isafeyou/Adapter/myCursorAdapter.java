package com.example.kwinam.isafeyou.isafeyou.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.kwinam.isafeyou.R;

/**
 * Created by KwiNam on 2017-08-07.
 */

public class myCursorAdapter extends CursorAdapter {

    @SuppressWarnings("depreacton")
    public myCursorAdapter(Context context, Cursor c){
        //noinspection deprecation
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        TextView view = new TextView(context);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        String name = cursor.getString(0);
        String phone = cursor.getString(1);
        // Get all the values
        // Use it however you need to
        TextView textView = (TextView) view;
        textView.setText(name);
    }
}
