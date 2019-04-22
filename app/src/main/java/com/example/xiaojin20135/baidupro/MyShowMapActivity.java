package com.example.xiaojin20135.baidupro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.example.xiaojin20135.maplibs.map.activity.ShowMapActivity;
import com.example.xiaojin20135.maplibs.map.fragment.ShowMapFragment;

public class MyShowMapActivity extends ShowMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_show_map);
        Fragment fragment = ShowMapFragment.newInstance((long)31.227,(long)121.481);
        getSupportFragmentManager().beginTransaction().replace(R.id.map1,fragment).commit();
    }
}
