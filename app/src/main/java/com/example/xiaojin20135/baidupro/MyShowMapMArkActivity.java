package com.example.xiaojin20135.baidupro;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.xiaojin20135.maplibs.map.LocationItem;
import com.example.xiaojin20135.maplibs.map.fragment.ShowMapFragment;

public class MyShowMapMArkActivity extends AppCompatActivity implements ShowMapFragment.OnFragmentInteractionListener {

    private static String TAG = "MyShowMapMArkActivity";
    private TextView location_result_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_show_map_mark);
        location_result_tv = (TextView)findViewById(R.id.location_result_tv);

        Fragment fragment = ShowMapFragment.newInstance((long)31.227,(long)121.481);
        ((ShowMapFragment) fragment).setShowMark(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.map1,fragment).commit();
    }


    @Override
    public void onFragmentInteraction(LocationItem locationItem) {
        Log.d(TAG,"getLatitude = " + locationItem.getLatitude());
        Log.d(TAG,"getLongitude = " + locationItem.getLongitude());
        location_result_tv.setText("当前坐标： latitude 为 " + locationItem.getLatitude() + " longtitude 为 " + locationItem.getLongitude());
    }
}
