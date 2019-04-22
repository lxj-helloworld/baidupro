package com.example.xiaojin20135.maplibs.map.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.example.xiaojin20135.maplibs.R;
import com.example.xiaojin20135.maplibs.map.util.MapUtil;

public class ShowMapActivity extends AppCompatActivity {

    private long latitude; //经度
    private long longitude; //纬度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
     * @author lixiaojin
     * create on 2019/4/20 17:40
     * description: 地图加载流程
     */
    private void initMap(){
        initParas();
        showMap();

    }

    /*
     * @author lixiaojin
     * create on 2019/4/20 17:38
     * description: 初始化经纬度信息
     */
    public void initParas(){
        Intent intent = getIntent();
        latitude = intent.getLongExtra(MapUtil.latitude,0);
        longitude = intent.getLongExtra(MapUtil.longitude,0);
    }


    /*
     * @author lixiaojin
     * create on 2019/4/20 17:40
     * description:显示地图
     */
    private void showMap() {
//        LatLng GEO_BEIJING = new LatLng(latitude, longitude);
//        //以传入点为地图中心，logo在左上角
//        MapStatusUpdate status1 = MapStatusUpdateFactory.newLatLng(GEO_BEIJING);
//        SupportMapFragment map1 = (SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.map1));
//        map1.getBaiduMap().setMapStatus(status1);
//        map1.getMapView().setLogoPosition(LogoPosition.logoPostionleftTop);
    }



}
