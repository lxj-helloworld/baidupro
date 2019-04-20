package com.example.xiaojin20135.baidupro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xiaojin20135.maplibs.map.LocationActivity;
import com.example.xiaojin20135.maplibs.map.LocationInMapActivity;
import com.example.xiaojin20135.maplibs.map.LocationItem;

public class MainActivity extends LocationActivity {
    private TextView locationinfo_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationinfo_TV = (TextView)findViewById(R.id.locationinfo_TV);
    }

    @Override
    public void locationSuccess(LocationItem locationItem) {
        if(locationinfo_TV != null){
            StringBuilder stringBuilder = new StringBuilder("");
            stringBuilder.append("地址：" + locationItem.getProvince () + " " + locationItem.getCity () + " " + locationItem.getLocationDescribe());
            stringBuilder.append("经纬度：" + locationItem.getLongitude() + "," + locationItem.getLatitude());
            locationinfo_TV.setText (locationItem.toString ());
        }
    }

    public void getLocation(View view) {
        startLocation();
    }

    public void openMap(View view) {
        Intent intent = new Intent(this,LocationInMapActivity.class);
        startActivity(intent);
    }
}
