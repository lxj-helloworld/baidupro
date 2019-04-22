package com.example.xiaojin20135.maplibs.map.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.xiaojin20135.maplibs.R;
import com.example.xiaojin20135.maplibs.map.LocationItem;
import com.example.xiaojin20135.maplibs.map.util.MapUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowMapFragment extends Fragment {
    private static final String TAG = "ShowMapFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    public double latitude; //经度
    public double longitude; //纬度

    private OnFragmentInteractionListener mListener;

    //地图视图
    MapView mapView = null;
    //地图控制器对象
    BaiduMap baiduMap;
    LocationClient locationClient;

    //显示自定义标注
    private boolean showMark = false;
    //标注允许拖拽
    private boolean canDrag = true;
    //标注
    private Marker marker;

    //位置信息
    private LocationItem locationItem = new LocationItem ();

    public ShowMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ShowMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowMapFragment newInstance(long latitude, long longitude) {
        ShowMapFragment fragment = new ShowMapFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, latitude);
        args.putLong(ARG_PARAM2, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getLong(ARG_PARAM1);
            longitude = getArguments().getLong(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_map, container, false);
        initMap(view);
        return view;
    }


    private void initMap(View view){
        //获取地图控件引用
        mapView = (MapView) view.findViewById(R.id.bmapView);
        //开启地图定位图层
        baiduMap = mapView.getMap();
        //显示普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启地图的定位图层
        baiduMap.setMyLocationEnabled(true);
        //开启定位
        startLocation();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
        if(mapView != null){
            mapView.onResume();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(mapView != null){
            mapView.onDestroy();
        }
    }

    @Override
    public void onDestroy() {
        if(locationClient != null){
            locationClient.stop();
            baiduMap.setMyLocationEnabled(false);
            mapView.onDestroy();
            mapView = null;
        }
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(LocationItem locationItem);
    }


    /*
     * @author lixiaojin
     * create on 2019/4/22 08:52
     * description:发起定位
     */
    private void startLocation(){
        locationClient = new LocationClient(getActivity());
        //设置定位参数
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true); //打开GPS
        locationClientOption.setCoorType(MapUtil.CoorType_GCJ02); //设置坐标类型
        locationClientOption.setScanSpan(5000);
        //设置LocationClientOption
        locationClient.setLocOption(locationClientOption);
        //注册LocationListener的监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        locationClient.start();
    }


    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null){
                return;
            }
            Log.d(TAG," latitide = "+location.getLatitude() + "");
            Log.d(TAG," longitude = "+location.getLongitude() + "");
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            //向外传递坐标信息
            if(mListener != null){
                locationItem = new LocationItem(location);
                mListener.onFragmentInteraction(locationItem);
            }
            if(showMark){
                showMark(location);
            }else{
                showNormalMark(location);
            }
        }
    }

    /*
    * @author lixiaojin
    * create on 2019/4/22 09:15
    * description: 显示标注
    */
    public void showNormalMark(BDLocation location){
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).
                        latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        baiduMap.setMyLocationData(locData);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));

    }

    /*
    * @author lixiaojin
    * create on 2019/4/22 10:26
    * description: 显示自定义标注
    */
    private void showMark(BDLocation location){

        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
        // 构建Marker图标
        BitmapDescriptor bitmap = null;
        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark); // 非推算结果
        baiduMap.setMyLocationEnabled(canDrag);
        // 构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(true);
        //删除之前的mark
        if(marker != null){
            marker.remove();
        }
        // 在地图上添加Marker，并显示
        marker =  (Marker) baiduMap.addOverlay(option);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
        if(canDrag){
            //拖拽监听事件
            baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDrag(Marker marker) {
                    //拖拽中
                    Log.d("LocationInMapActivity","in onMarkerDrag.");
                }
                @Override
                public void onMarkerDragEnd(Marker marker) {
                    //拖拽结束
                    Log.d("LocationInMapActivity","in onMarkerDragEnd. latitude="+marker.getPosition().latitude+";longitude="+marker.getPosition().longitude);
                    latitude = marker.getPosition().latitude;
                    longitude = marker.getPosition().longitude;
                    //记录新的位置信息
                    locationItem = new LocationItem();
                    locationItem.setLatitude(marker.getPosition().latitude);
                    locationItem.setLongitude(marker.getPosition().longitude);
                    //停止定位
                    locationClient.stop();

                    //外传位置信息
                    if(mListener != null){
                        mListener.onFragmentInteraction(locationItem);
                    }
                }
                @Override
                public void onMarkerDragStart(Marker marker) {
                    ///开始拖拽
                    Log.d("LocationInMapActivity","in onMarkerDragStart.");
                }
            });
        }
    }



    public boolean isShowMark() {
        return showMark;
    }

    public void setShowMark(boolean showMark) {
        this.showMark = showMark;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }
}
