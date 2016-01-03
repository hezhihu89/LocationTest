package com.example.hezhihu89.gittest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import javax.crypto.spec.GCMParameterSpec;

/**
 * @项目名 ：GooglePlay
 * @包名 ：com.example.hezhihu89.gittest
 * @User ： hezhihu89 by：贺志虎
 * @创建时间 ：2015 年 12 月 30 日   15时 17分.
 * @类的描述 : TODO：
 */
public class TIFALocationPlugin {

    private String TAG = this.getClass().getSimpleName().toString();
    private Context mContext;
    private LocationManager mLman;
    private static TIFALocationPlugin instance;
    private LocationListener listener;
    private Handler handler;
    private Location TIFALocation;

    /**
     * 初始化
     */
    private TIFALocationPlugin() {

    }

    public TIFALocationPlugin(Context content) {
        this.mContext = content;
        handler = new Handler();
       // onCreate();
    }

    public static TIFALocationPlugin getInstance() {
        if (instance == null) {
            synchronized (TIFALocationPlugin.class) {
                if (instance == null) {
                    instance = new TIFALocationPlugin();
                }
            }
        }
        return instance;
    }

    private void onCreate() {
        //获取地理位置管理器
        mLman = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Log.d(TAG, "开始获取地理位置");


        handler.post(new Runnable() {
            @Override
            public void run() {

                //使用GPS 定位 获取地理位置
                mLman.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000, 0, locationListener);
            }
        });


    }

    public void setOnLocationListener(LocationListener clistener) {
        this.listener = clistener;
    }



    public void removerLocationListaner() {
        mLman.removeUpdates(locationListener);
    }


    /**
     * 获取城市
     *
     * @return
     */
    public String getCity() {

        return "";
    }

    /**
     * 获取经纬度
     *
     * @return
     */
    public String getLocation() {

        onCreate();

        return "";
    }


    //地址位置监听器
    private final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "地理位置发生改变" + location.getProvider());
            if (listener != null) {
                listener.onLocationChanged(location);
                //获取到地理位置后 移除监听
                removerLocationListaner();
                TIFALocation = location;
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "状态发生改变");
            if (listener != null) {
                listener.onStatusChanged(provider, status, extras);

            }

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "可用" + provider);
            if (listener != null) {
                listener.onProviderEnabled(provider);

            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "不可用" + provider);
            if (listener != null) {
                listener.onProviderDisabled(provider);
            }
            if ("gps".equals(provider)) {
                //如果GPS 不可用 则申请使用 网络定位
                mLman.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10,
                        locationListener);
            }
        }
    };


}

