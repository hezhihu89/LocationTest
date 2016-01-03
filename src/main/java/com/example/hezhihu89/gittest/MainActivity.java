package com.example.hezhihu89.gittest;

import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getlocation;
    private Button closrlocation;
    private TIFALocationPlugin mTifaLocationPlugin;
    private static String TAG = MainActivity.class.getSimpleName().toString();
    private String str;
    private EditText editText;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        initView();
        initEvent();

    }

    private void initView() {
        getlocation = (Button) findViewById(R.id.get_location);
        closrlocation = (Button) findViewById(R.id.close_location);
        editText = (EditText) findViewById(R.id.set_location);


    }

    private void initEvent() {
        getlocation.setOnClickListener(this);
        closrlocation.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTifaLocationPlugin != null) {
            mTifaLocationPlugin.removerLocationListaner();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.get_location) {
            mTifaLocationPlugin = new TIFALocationPlugin(MainActivity.this);
            mTifaLocationPlugin.setOnLocationListener(new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d(TAG, "地理位置发生改变");
                    str = "地理位置发生改变";
                    double Latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    editText.setText("纬度" + Latitude + "----经度" + longitude);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d(TAG, "状态发生改变");
                    str = "状态位置发生改变";
                    editText.setText(str);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d(TAG, "可用" + provider);
                    editText.setText("可用");

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d(TAG, "不可用" + provider);
                    editText.setText("不可用");

                }
            });


        } else if (id == R.id.close_location) {
            Log.d(TAG, "移除监听");
            if (mTifaLocationPlugin != null) {

                editText.setText("");
                mTifaLocationPlugin.removerLocationListaner();
            }
        }
    }
}
