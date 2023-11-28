package com.example.eweather;

import android.app.Application;
import android.content.Context;

import com.example.util.ControlManager;
import com.example.util.MyCityManager;
import com.example.util.ResponseManager;
import com.example.util.SpinnerUtil;

public class eWeatherApplication extends Application {
    private static Context context;
    private static SpinnerUtil spinnerUtil=new SpinnerUtil();
    private static MyCityManager myCityManager=new MyCityManager();
    private static ControlManager controlManager=new ControlManager();
    private static ResponseManager responseManager=new ResponseManager();
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
    public static MyCityManager getMyCityManager(){
        return myCityManager;
    }
    public static ControlManager getControlManager(){
        return controlManager;
    }
    public static ResponseManager getResponseManager(){
        return responseManager;
    }
}
