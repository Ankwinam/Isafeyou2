package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.example.kwinam.isafeyou.R;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment2 extends Fragment {
    RelativeLayout relative;
    private TMapGpsManager tmapgps = null;
    private TMapView tmapview = null;
    private static String mApiKey = "441b40f8-11f4-38af-a21f-2b2a0e57bf21"; // 발급받은 appKey
    LocationManager lm = null;
    Button target;
    TMapPoint point = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tmapgps = new TMapGpsManager(getActivity());

        View view = inflater.inflate(R.layout.fragment_2, container, false);
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Log.e("LocationManager", lm + "");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        target = (Button)view.findViewById(R.id.btn_target);
        target.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                point = tmapgps.getLocation();
                tmapview.setCenterPoint(point.getLongitude(), point.getLatitude());
                tmapview.setZoomLevel(15);
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.map_view);
        tmapview = new TMapView(getActivity());
        tmapview.setSKPMapApiKey(mApiKey);
        Log.e("tmapview", tmapview + "");
        Log.e("layout", relativeLayout + "");
        relativeLayout.addView(tmapview);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_SATELLITE);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapgps = new TMapGpsManager(getActivity());
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        tmapgps.OpenGps();

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        tmapview.setIconVisibility(true);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(false);
        Log.e("tmapview",tmapview+"");

        return view;
    }

    @Override
    public void onStop() {
        lm.removeUpdates(mLocationListener);
        tmapgps.CloseGps();
        super.onStop();
    }

    @Override
    public void onPause() {
        lm.removeUpdates(mLocationListener);
        tmapgps.CloseGps();
        super.onPause();
    }

    @Override
    public void onResume() {
        tmapgps.OpenGps();
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
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                1000, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        super.onResume();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                //  TODO: Prompt with explanation!

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    public final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                Log.e("GPS changed","바뀌고 있다! "+ location.getLatitude() +","+ location.getLongitude());
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                double longitude = location.getLongitude();    //경도
                double latitude = location.getLatitude();         //위도
                float accuracy = location.getAccuracy();        //신뢰도
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                //Network 위치제공자에 의한 위치변화
                //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
                tmapview.setLocationPoint(longitude,latitude);
                tmapview.setCenterPoint(longitude,latitude);
            }
            else if(location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
                Log.e("Network changed","바뀌고 있다! "+ location.getLatitude() +","+ location.getLongitude());
                double longitude = location.getLongitude();    //경도
                double latitude = location.getLatitude();         //위도
                float accuracy = location.getAccuracy();        //신뢰도
                tmapview.setLocationPoint(longitude,latitude);
                tmapview.setCenterPoint(longitude,latitude);
            }
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            new AlertDialog.Builder(getActivity())
                    .setMessage("GPS가 꺼져있습니다.\n GPS를 켜시면 더 정확한 정보파악이 가능해집니다.")
                    .setPositiveButton("설정",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("취소",null).show();
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };
}
