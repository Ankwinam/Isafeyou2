package com.example.kwinam.isafeyou.isafeyou.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment1;
import com.example.kwinam.isafeyou.isafeyou.Fragment.TabFragment2;

/**
 * Created by Taewoong on 2017-11-19.
 */

public class OnLock_BroadcastReceiver extends BroadcastReceiver {
    long start = 0;
    int i = 0;
    double longitude = 0;
    double latitude = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if((System.currentTimeMillis() - start)/1000.0 > 3.0){
            i = 0;
        }
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            Toast.makeText(context, "BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, OnLock_Service.class);
            context.startService(i);
        }
        else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
        {
            i++;
            if(i == 1){
                start = System.currentTimeMillis();
            }
            Log.e("iii",""+i);
            if(i == 3){
                TabFragment1 tabFragment1 = new TabFragment1();
                try {
                    tabFragment1.sendSMS("01033537237");
                    i = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
        {
            i++;
            Log.e("iii",""+i);
            if(i == 1){
                start = System.currentTimeMillis();
            }
            if(i == 3){
                TabFragment1 tabFragment1 = new TabFragment1();
               try {
                    tabFragment1.sendSMS("01033537237");
                    i = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
