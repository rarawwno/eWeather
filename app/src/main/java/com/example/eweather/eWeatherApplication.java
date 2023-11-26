package com.example.eweather;

import android.app.Application;
import android.content.Context;

import com.example.util.ControlManager;
import com.example.util.MyCityManager;
import com.example.util.SignalManagerUtil;
import com.example.util.SpinnerUtil;

public class eWeatherApplication extends Application {
    private static Context context;
    private static SpinnerUtil spinnerUtil=new SpinnerUtil();
    private static SignalManagerUtil signalManagerUtil=new SignalManagerUtil();
    private static MyCityManager myCityManager=new MyCityManager();
    private static ControlManager controlManager=new ControlManager();
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
    public static SpinnerUtil getSpinnerUtil(){
        return spinnerUtil;
    }
    public static SignalManagerUtil getSignalManagerUtil(){
        return signalManagerUtil;
    }
    public static MyCityManager getMyCityManager(){
        return myCityManager;
    }
    public static ControlManager getControlManager(){
        return controlManager;
    }
}
