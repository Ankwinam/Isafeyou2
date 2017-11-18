package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kwinam.isafeyou.R;
import com.example.kwinam.isafeyou.isafeyou.Activity.WhistleActivity;
import com.pkmmte.view.CircularImageView;
import com.skp.Tmap.TMapPoint;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment1 extends Fragment {
    AudioManager audio;
    public static LocationManager lm = null;
    boolean askGPS = false;
    double longitude = 0;
    double latitude = 0;
    private static final long DOUBLE_PRESS_INTERVAL = 250;
    private long lastPressTIme;

    private boolean mHasDoubleClicked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        Button whistle = (Button) view.findViewById(R.id.Whistle);
        CircularImageView safebutton = (CircularImageView) view.findViewById(R.id.safebutton);
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
        audio = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        startGPS();
        whistle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT){
                    Toast.makeText(getActivity(),"무음모드를 해제해 주세요",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), WhistleActivity.class);
                    startActivity(intent);
                }
            }
        });
        safebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pressTime = System.currentTimeMillis();
                if(pressTime - lastPressTIme <= DOUBLE_PRESS_INTERVAL){
                    try {
                        sendSMS("01051436822");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    mHasDoubleClicked = false;
                    Handler myHandler = new Handler(){
                        public void handleMessage(Message m){
                            if(!mHasDoubleClicked){
                            }
                        }
                    };
                    Message m = new Message();
                    myHandler.sendMessageDelayed(m,DOUBLE_PRESS_INTERVAL);
                }
                lastPressTIme = pressTime;
            }
        });
        return view;
    }

    public void sendSMS(String phoneNo) throws InterruptedException {
        String messageURL = "https://www.google.co.kr/maps/search/" + latitude + "+" + longitude;
        String message ="이태웅님이 위의 장소에서 위험에 처했습니다! \n 도와주세요!";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, messageURL, null, null);
        Thread.sleep(1000);
        smsManager.sendTextMessage(phoneNo, null, message, null, null);
        Toast.makeText(getActivity(),"메세지가 전송되었습니다",Toast.LENGTH_SHORT).show();
    }

    public final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                longitude = location.getLongitude();    //경도
                latitude = location.getLatitude();      //위도
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                //Network 위치제공자에 의한 위치변화
                //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                longitude = location.getLongitude();    //경도
                latitude = location.getLatitude();      //위도
            }
        }

        public void onProviderDisabled(String provider) {
            if (!askGPS) {
                askGPS = true;
            }
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

    public void startGPS() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                2000, // 통지사이의 최소 시간간격 (miliSecond)
                2, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                2000, // 통지사이의 최소 시간간격 (miliSecond)
                2, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
    }

}
