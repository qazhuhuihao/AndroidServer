package cn.hhh.myandroidserver;

import android.app.Application;

/**
 * Application
 * Created by hhh on 2017/2/18.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        myApplication = this;

    }

    public static MyApplication getInstance(){
        return myApplication;
    }
}
