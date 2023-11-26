package com.example.eweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bean.CityBean;
import com.example.bean.CityWeatherBean;
import com.example.bean.CountryBean;
import com.example.bean.ProvinceBean;
import com.example.bean.SignalBean;
import com.example.bean.WeatherResponse;
import com.example.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    /*侧滑布局*/
    DrawerLayout drawerLayout;
    EditText search_city_input;
    ImageView search_city_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_city_input=(EditText)findViewById(R.id.search_city_input);
        search_city_btn=(ImageView)findViewById(R.id.search_city_btn);

        search_city_btn.setOnClickListener(eWeatherApplication.getControlManager().getClickListener());
        /*设置toolBar*/
        setToolBar();
        /*设置侧滑导航*/
        setNavigationIcon();
        /*获取所有location*/
        getAllLocation();
    }
    /*拼接请求地址和参数*/
    private String spliceAddress(String requestURL, HashMap<String,String> params){
        StringBuilder url=new StringBuilder();
        url.append(requestURL).append("?");
        if(params!=null&&params.size()!=0){
            for(Map.Entry<String,String> entry:params.entrySet()){
                url.append(entry.getKey()).append("=").append(entry.getValue());
                url.append("&");
            }
            url.deleteCharAt(url.length()-1);
        }
        return url.toString();
    }

    /*ToolBar中item点击事件*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            /*点击侧滑导航item*/
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            /*点击搜索城市item*/
            case R.id.search_city:

                break;
            /*点击隐藏item*/
            case R.id.manage_city:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
    /*将toolbar.xml加载到Toolbar*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    /*设置滑动导航图片*/
    public void setNavigationIcon(){
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            /*显示侧滑导航*/
            actionBar.setDisplayHomeAsUpEnabled(true);
            /*设置侧滑图片*/
            actionBar.setHomeAsUpIndicator(R.drawable.user_navigation);
        }
    }
    /*设置toolBar*/
    public void setToolBar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.homeToolbar);
        /*设置toolBar作为ActionBar*/
        setSupportActionBar(toolbar);
        /*隐藏supportActionBar*/
//        getSupportActionBar().hide();
        /*显示大标题*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    /*根据省获取市列表*/
    public void getCityList(String provinceName){
        /*判断相匹配的省*/
        for (ProvinceBean provinceBean:eWeatherApplication.getSpinnerUtil().getProvinceArrayList()){
            /*匹配省名字*/
            if(provinceBean.getName().equals(provinceName)){
                /*跟新市列表*/
                eWeatherApplication.getSpinnerUtil().setCityJsonArray(provinceBean.getDistricts());
                Gson gson=new Gson();
                ArrayList<CityBean> arrayList=new ArrayList<>();
                for (JsonElement City:eWeatherApplication.getSpinnerUtil().getCityJsonArray()){
                    /*JsonElement转CityBean*/
                    CityBean cityBean=gson.fromJson(City,new TypeToken<CityBean>(){}.getType());
                    arrayList.add(cityBean);
                }
                eWeatherApplication.getSpinnerUtil().setCityArrayList(arrayList);
            }
        }
    }
    /*获取天气*/
    public void getWeather(){
        /*请求地址*/
        String requestURL="https://restapi.amap.com/v3/weather/weatherInfo";
        /*参数*/
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        String city=eWeatherApplication.getMyCityManager().getDefaultCity().getAdcode();
        String extensions="base";
        String output="JSON";
        /*参数打包*/
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("city",city);
        params.put("extensions",extensions);
        params.put("output",output);

        requestURL=spliceAddress(requestURL,params);
        HttpUtil.sendOkHttpRequest(requestURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    /*获取整个weather的Json*/
                    String responseData=response.body().string();
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();
                    Gson gson=new Gson();
                    /*整个json转成WeatherResponse*/
                    WeatherResponse weatherResponse=gson.fromJson(jsonObject,new TypeToken<WeatherResponse>(){}.getType());
                    eWeatherApplication.getMyCityManager().getDefaultCity().setWeatherResponse(weatherResponse);
                    JsonArray lives=weatherResponse.getLives();
                    JsonElement livesBody=lives.get(0);
                    JsonObject livesBodyObject=gson.fromJson(livesBody,new TypeToken<JsonObject>(){}.getType());
                    CityWeatherBean cityWeatherBean=gson.fromJson(livesBodyObject,new TypeToken<CityWeatherBean>(){}.getType());
                    String weather=cityWeatherBean.getWeather();
                    String temperature=cityWeatherBean.getTemperature();
                    eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherBean().setWeather(weather);
                    eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherBean().setTemperature(temperature);
                    loadWeather();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*改变天气UI*/
    public void loadWeather(){
        final String weather=eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherBean().getWeather();
        final String temperature=eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherBean().getTemperature();
        final TextView weatherContent=(TextView)findViewById(R.id.weatherContent);
        final TextView temperatureContent=(TextView)findViewById(R.id.temperatureContent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weatherContent.setText(weather);
                temperatureContent.setText(temperature);
            }
        });
    }
    /*获取行政区*/
    public void getAllLocation(){
        String requestURL="https://restapi.amap.com/v3/config/district";
        /*请求参数*/
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        String subdistrict="2";
        /*参数打包*/
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("subdistrict",subdistrict);
        /*参数拼接*/
        requestURL=spliceAddress(requestURL,params);
        /*请求*/
        HttpUtil.sendOkHttpRequest(requestURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity","响应失败");
            }
            @Override
            public void onResponse(Call call, Response response) {
                try{
                    String responseData=response.body().string();
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();

                    /*获取所有国家*/
                    eWeatherApplication.getSpinnerUtil().setCountryJsonArray(jsonObject.getAsJsonArray("districts"));
                    Gson gson=new Gson();
                    for(JsonElement Country:eWeatherApplication.getSpinnerUtil().getCountryJsonArray()){
                        CountryBean CountryBean=gson.fromJson(Country,new TypeToken<CountryBean>(){}.getType());
                        eWeatherApplication.getSpinnerUtil().getCountryArrayList().add(CountryBean);
                    }
                    /*获取所有省*/
                    eWeatherApplication.getSpinnerUtil().setProvinceJsonArray(eWeatherApplication.getSpinnerUtil().getCountryArrayList().get(0).getDistricts());
                    for(JsonElement Province:eWeatherApplication.getSpinnerUtil().getProvinceJsonArray()){
                        ProvinceBean ProvinceBean=gson.fromJson(Province,new TypeToken<ProvinceBean>(){}.getType());
                        eWeatherApplication.getSpinnerUtil().getProvinceArrayList().add(ProvinceBean);
                    }
                    /*初始化spinner*/
                    initSpinner();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /*初始化spinner*/
    public void initSpinner(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*获取下拉所有省级*/
                final ArrayList<String> nameList=new ArrayList<>();
                final ArrayList<String> adcodeList=new ArrayList<>();
                /*遍历出name和adcode*/
                for(ProvinceBean provinceBean:eWeatherApplication.getSpinnerUtil().getProvinceArrayList()){
                    String provinceName=provinceBean.getName();
                    String provinceAdcode=provinceBean.getAdcode();
                    nameList.add(provinceName);
                    adcodeList.add(provinceAdcode);
                }
                /*适配省级下拉*/
                Spinner ProvinceSpinner=(Spinner)findViewById(R.id.ProvinceSpinner);
                ProvinceSpinner.setAdapter(new ArrayAdapter<String>(eWeatherApplication.getContext(),R.layout.spinner_item,nameList));
                ProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                        /*获取选中的name和adcode*/
                        eWeatherApplication.getMyCityManager().getDefaultCity().setProvinceName(nameList.get(index));
                        eWeatherApplication.getMyCityManager().getDefaultCity().setAdcode(adcodeList.get(index));
                        /*根据省名获取市list*/
                        getCityList(eWeatherApplication.getMyCityManager().getDefaultCity().getProvinceName());
                        /*我的省已经选好*/
                        eWeatherApplication.getSignalManagerUtil().getSignalBean().setProvinceSignal(eWeatherApplication.getSignalManagerUtil().getSignalBean().Exist);
                        /*init城市spinner*/
                        initCitySpinner();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

    }
    /*根据省初始化城市spinner*/
    public void initCitySpinner(){
        if(eWeatherApplication.getSignalManagerUtil().getSignalBean().getProvinceSignal()== SignalBean.Exist){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ArrayList<String> nameList=new ArrayList<>();
                        final ArrayList<String> adcodeList=new ArrayList<>();
                        for(CityBean cityBean:eWeatherApplication.getSpinnerUtil().getCityArrayList()){
                            String name=cityBean.getName();
                            String adcode=cityBean.getAdcode();
                            nameList.add(name);
                            adcodeList.add(adcode);
                        }
                        Spinner CitySpinner=(Spinner)findViewById(R.id.CitySpinner);
                        CitySpinner.setAdapter(new ArrayAdapter<String>(eWeatherApplication.getContext(),R.layout.spinner_item,nameList));
                        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                                eWeatherApplication.getMyCityManager().getDefaultCity().setCityName(nameList.get(index));
                                eWeatherApplication.getMyCityManager().getDefaultCity().setAdcode(adcodeList.get(index));
                                getWeather();
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
