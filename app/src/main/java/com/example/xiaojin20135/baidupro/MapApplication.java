package com.example.xiaojin20135.baidupro;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.example.xiaojin20135.maplibs.map.ApplicationHelp;
import com.example.xiaojin20135.maplibs.map.LocationService;

public class MapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
    }


    //百度地图相关
    public LocationService locationService;
    public Vibrator mVibrator;
    public void initLocation(){
        /**
         * 初始化百度地图SDK，建议在Application中创建
         */
        locationService = new LocationService(this);
        mVibrator =(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(this);
        ///将当前地图的坐标类型设置为GCJ02
        SDKInitializer.setCoordType(CoordType.GCJ02);
        ApplicationHelp.APPLICATION_HELP.locationService = locationService;
    }
}
