package com.chenshixin.nofriendscircle;

import android.app.Application;

import com.chenshixin.nofriendscircle.util.PreferenceUtil;

/**
 * Created by csx.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtil.init(this);
    }

}
