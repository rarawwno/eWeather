package com.example.eweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.example.element.CityEle;
import com.example.element.CityWeatherEle;
import com.example.element.CountryEle;
import com.example.element.DistrictResponse;
import com.example.element.ProvinceEle;
import com.example.element.WeatherResponse;
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
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
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
                    CityWeatherEle cityWeatherEle =gson.fromJson(livesBodyObject,new TypeToken<CityWeatherEle>(){}.getType());
                    String weather= cityWeatherEle.getWeather();
                    String temperature= cityWeatherEle.getTemperature();
                    eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherEle().setWeather(weather);
                    eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherEle().setTemperature(temperature);
                    loadWeather();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*改变天气UI*/
    public void loadWeather(){
        final String weather=eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherEle().getWeather();
        final String temperature=eWeatherApplication.getMyCityManager().getDefaultCity().getCityWeatherEle().getTemperature();
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
                    Gson gson=new Gson();
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();
                    /*取出国家列表*/
                    JsonArray countryArray=jsonObject.getAsJsonArray("districts");
                    /*转换国家列表*/
                    ArrayList<CountryEle> countryList=new ArrayList<>();
                    for(JsonElement Country:countryArray){
                        CountryEle CountryEle =gson.fromJson(Country,new TypeToken<CountryEle>(){}.getType());
                        countryList.add(CountryEle);
                    }
                    eWeatherApplication.getResponseManager().getAreaResponse().setCountryList(countryList);

                    /*取出省列表*/
                    CountryEle countryEle =countryList.get(0);
                    JsonArray provinceArray= countryEle.getDistricts();
                    /*转换省列表*/
                    ArrayList<ProvinceEle> provinceList=new ArrayList<>();
                    for(JsonElement province:provinceArray){
                        ProvinceEle provinceEle =gson.fromJson(province,new TypeToken<ProvinceEle>(){}.getType());
                        provinceList.add(provinceEle);
                    }
                    eWeatherApplication.getResponseManager().getAreaResponse().setProvinceList(provinceList);
                    /*取出所有市列表*/
                    ArrayList<CityEle> cityList=new ArrayList<>();
                    for(ProvinceEle provinceEle :provinceList){
                        JsonArray cityArray= provinceEle.getDistricts();
                        for(JsonElement city:cityArray){
                            CityEle cityEle =gson.fromJson(city,new TypeToken<CityEle>(){}.getType());
                            cityList.add(cityEle);
                        }
                    }
                    eWeatherApplication.getResponseManager().getAreaResponse().setCityList(cityList);
                    /*初始化spinner*/
                    initSpinner();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*省下拉显示所有省
    * 保存选择的省*/
    public void initSpinner(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*获取下拉所有省级*/
                final ArrayList<String> nameList=new ArrayList<>();
                ArrayList<ProvinceEle> provinceList=eWeatherApplication.getResponseManager().getAreaResponse().getProvinceList();
                eWeatherApplication.getSpinnerUtil().setProvinceList(provinceList);
                /*遍历出name*/
                for(ProvinceEle provinceEle :provinceList){
                    String provinceName= provinceEle.getName();
                    nameList.add(provinceName);
                }
                /*适配省级下拉*/
                Spinner ProvinceSpinner=(Spinner)findViewById(R.id.ProvinceSpinner);
                ProvinceSpinner.setAdapter(new ArrayAdapter<String>(eWeatherApplication.getContext(),R.layout.spinner_item,nameList));
                ProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                        /*获取name*/
                        String provinceName=nameList.get(index);
                        eWeatherApplication.getMyCityManager().getDefaultCity().setProvinceName(provinceName);
                        /*根据省名获取市list*/
                        getCityList(provinceName);
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
    /*根据省获取市列表*/
    public void getCityList(String provinceName){
        ArrayList<ProvinceEle> provinceList=eWeatherApplication.getResponseManager().getAreaResponse().getProvinceList();
        ArrayList<CityEle> cityList=new ArrayList<>();
        for (ProvinceEle provinceEle :provinceList){
            /*匹配省名字*/
            if(provinceEle.getName().equals(provinceName)){
                JsonArray cityArray= provinceEle.getDistricts();
                Gson gson=new Gson();
                for (JsonElement City:cityArray){
                    CityEle cityEle =gson.fromJson(City,new TypeToken<CityEle>(){}.getType());
                    cityList.add(cityEle);
                }
                eWeatherApplication.getSpinnerUtil().setCityList(cityList);
            }
        }
    }
    /*根据省初始化城市spinner*/
    public void initCitySpinner(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try{
                        final ArrayList<String> nameList=new ArrayList<>();
                        final ArrayList<String> adcodeList=new ArrayList<>();
                        ArrayList<CityEle> cityList=eWeatherApplication.getSpinnerUtil().getCityList();
                        for(CityEle cityEle :cityList){
                            String name= cityEle.getName();
                            String adcode= cityEle.getAdcode();
                            nameList.add(name);
                            adcodeList.add(adcode);
                        }
                        Spinner CitySpinner=(Spinner)findViewById(R.id.CitySpinner);
                        CitySpinner.setAdapter(new ArrayAdapter<String>(eWeatherApplication.getContext(),R.layout.spinner_item,nameList));
                        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                                String name=nameList.get(index);
                                String adcode=adcodeList.get(index);
                                eWeatherApplication.getMyCityManager().getDefaultCity().setCityName(name);
                                eWeatherApplication.getMyCityManager().getDefaultCity().setAdcode(adcode);
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
