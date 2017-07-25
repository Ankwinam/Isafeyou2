package com.example.kwinam.isafeyou.isafeyou.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapGpsManager;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;
import com.skp.Tmap.TMapView;
import com.example.kwinam.isafeyou.R;

import java.util.ArrayList;

/**
 * Created by KwiNam on 2017-07-19.
 */

public class TabFragment2 extends Fragment {
    public TMapGpsManager tmapgps = null;
    private TMapView tmapview = null;
    private static String mApiKey = "441b40f8-11f4-38af-a21f-2b2a0e57bf21"; // 발급받은 appKey
    public static LocationManager lm = null;
    Button target;
    TMapPoint point = null;
    boolean askGPS = false;
    private ArrayList<MapPoint> m_mapPoint = new ArrayList<MapPoint>();
    private static int mMarkerID;
    private Double lat = null;
    private Double lon = null;
    private TMapData tmapdata = null;

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        tmapdata = new TMapData();
        tmapgps = new TMapGpsManager(getActivity());
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                startGPS();
            }
        } else {
            //Not in api-23, no need to prompt
            startGPS();
        }
        target = (Button) view.findViewById(R.id.btn_target);
        target.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                point = tmapgps.getLocation();
                if(point.getLongitude()<120){
                    Toast.makeText(getActivity(), "GPS 신호를 찾는중 입니다. \n실내 환경에서는 다소 지연될 수 있습니다.", Toast.LENGTH_LONG).show();
                }
                tmapview.setCenterPoint(point.getLongitude(), point.getLatitude());
                tmapview.setZoomLevel(15);
                tmapview.removeAllTMapPolyLine();
                tmapview.removeAllMarkerItem();
                tmapview.removeAllTMapPOIItem();
                getAroundpopo();
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.map_view);
        tmapview = new TMapView(getActivity());
        tmapview.setSKPMapApiKey(mApiKey);
        relativeLayout.addView(tmapview);

        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_SATELLITE);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapgps.setMinTime(1000);
        tmapgps.setMinDistance(5);
        tmapgps.setProvider(tmapgps.NETWORK_PROVIDER);
        tmapview.setIconVisibility(true);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(false);

        tmapview.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
            @Override
            public void onCalloutRightButton(TMapMarkerItem markerItem) {
                lat = markerItem.latitude;
                lon = markerItem.longitude;
                TMapPoint start = tmapview.getLocationPoint();
                TMapPoint end = new TMapPoint(lat, lon);
                searchRoute(start, end);
            }

        });
        return view;
    }

    @Override
    public void onStop() {
        stopGPS();
        super.onStop();
    }


    @Override
    public void onResume() {
        startGPS();
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


    private void searchRoute(TMapPoint start, TMapPoint end) {
        TMapData data = new TMapData();
        data.findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, start, end, new TMapData.FindPathDataListenerCallback() {
            @Override
            public void onFindPathData(TMapPolyLine path) {
                path.setLineWidth(20);
                path.setLineColor(Color.rgb(255,153,153));
                tmapview.addTMapPath(path);
            }
        });
    }

    public final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                double longitude = location.getLongitude();    //경도
                double latitude = location.getLatitude();         //위도
                float accuracy = location.getAccuracy();        //신뢰도
                //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
                //Network 위치제공자에 의한 위치변화
                //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
                tmapview.setLocationPoint(longitude, latitude);
            } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
                double longitude = location.getLongitude();    //경도
                double latitude = location.getLatitude();         //위도
                float accuracy = location.getAccuracy();        //신뢰도
                tmapview.setLocationPoint(longitude, latitude);
                tmapview.setCenterPoint(longitude, latitude);
            }
        }

        public void onProviderDisabled(String provider) {
            if (!askGPS) {
                askGPS = true;
                showSettingsAlert();
            }
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
        }
    };

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다.\n 설정창으로 가시겠습니까?");

        // OK 를 누르게 되면 설정창으로 이동합니다.
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                    }
                });
        // Cancle 하면 종료 합니다.
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

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
        tmapgps.OpenGps();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                2000, // 통지사이의 최소 시간간격 (miliSecond)
                2, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                2000, // 통지사이의 최소 시간간격 (miliSecond)
                2, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
    }

    public void stopGPS() {
        tmapgps.CloseGps();
        lm.removeUpdates(mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        startGPS();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    public void getAroundpopo() {
        TMapData tmapdata = new TMapData();
        TMapPoint point = tmapview.getCenterPoint();
        tmapdata.findAroundNamePOI(point, "관공서", 2, 10, new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        if (poiItem == null) {
                        } else {
                            for (int i = 0; i < poiItem.size(); i++) {
                                TMapPOIItem item = poiItem.get(i);
                                if (item.getPOIName().contains("경찰") || item.getPOIName().contains("파출") || item.getPOIName().contains("복지") || item.getPOIName().contains("방범")) {
                                    m_mapPoint.add(new MapPoint(item.getPOIName(), item.getPOIPoint().getLatitude(), item.getPOIPoint().getLongitude()));
                                }
                            }
                            for (int i = 0; i < m_mapPoint.size(); i++) {
                                TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLatitude(), m_mapPoint.get(i).getLongitude());
                                TMapMarkerItem item1 = new TMapMarkerItem();
                                Bitmap bitmap = null;
                                 /* 핀 이미지 */
                                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.poi_dot);
                                item1.setTMapPoint(point);
                                item1.setName(m_mapPoint.get(i).getName());
                                item1.setVisible(item1.VISIBLE);
                                item1.setIcon(bitmap);
                                item1.setCalloutTitle(m_mapPoint.get(i).getName());
                                item1.setCalloutSubTitle("까지 최단경로 탐색");
                                item1.setCanShowCallout(true);
                                item1.setAutoCalloutVisible(true);

                                Bitmap bitmap_i = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.i_go);
                                item1.setCalloutRightButtonImage(bitmap_i);

                                String strID = String.format("pmarker%d", mMarkerID++);

                                tmapview.addMarkerItem(strID, item1);
                            }
                        }
                    }
                });

        tmapdata.findAroundNamePOI(point, "편의점", 2, 5,
                new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> poiItem) {
                        if (poiItem == null) {
                        } else {
                            for (int i = 0; i < poiItem.size(); i++) {
                                TMapPOIItem item = poiItem.get(i);
                                m_mapPoint.add(new MapPoint(item.getPOIName(), item.getPOIPoint().getLatitude(), item.getPOIPoint().getLongitude()));
                            }
                            for (int i = 0; i < m_mapPoint.size(); i++) {
                                TMapPoint point = new TMapPoint(m_mapPoint.get(i).getLatitude(), m_mapPoint.get(i).getLongitude());
                                TMapMarkerItem item1 = new TMapMarkerItem();
                                Bitmap bitmap = null;
                                 /* 핀 이미지 */
                                bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.poi_dot);
                                item1.setTMapPoint(point);
                                item1.setName(m_mapPoint.get(i).getName());
                                item1.setVisible(item1.VISIBLE);
                                item1.setIcon(bitmap);
                                item1.setCalloutTitle(m_mapPoint.get(i).getName());
                                item1.setCalloutSubTitle("까지 최단경로 탐색");
                                item1.setCanShowCallout(true);
                                Bitmap bitmap_i = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.i_go);
                                item1.setCalloutRightButtonImage(bitmap_i);

                                String strID = String.format("pmarker%d", mMarkerID++);

                                tmapview.addMarkerItem(strID, item1);
                            }
                        }
                    }
                });
        m_mapPoint.clear();
    }
}
