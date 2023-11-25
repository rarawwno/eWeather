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

import com.example.db.CityBean;
import com.example.db.CityWeather;
import com.example.db.CountryBean;
import com.example.db.ProvinceBean;
import com.example.db.SearchBean;
import com.example.util.HttpUtil;
import com.example.util.MyLocationUtil;
import com.example.util.SignalUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*侧滑布局*/
    DrawerLayout drawerLayout;
    EditText search_city_input;
    ImageView search_city_btn;
    SearchBean searchBean;
    SignalUtil signalUtil;
    MyLocationUtil myLocationUtil;
    CityWeather cityWeather;
    /*初始化spinner*/
    public void initSpinner(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ProvinceBean> allProvinceList=searchBean.getAllProvinceList();
                final ArrayList<String> nameList=new ArrayList<>();
                final ArrayList<String> adcodeList=new ArrayList<>();
                /*遍历出name*/
                for(ProvinceBean provinceBean:allProvinceList){
                    String provinceName=provinceBean.name;
                    String provinceAdcode=provinceBean.name;
                    nameList.add(provinceName);
                    adcodeList.add(provinceAdcode);
                }
                Spinner ProvinceSpinner=(Spinner)findViewById(R.id.ProvinceSpinner);
                ProvinceSpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.spinner_item,nameList));
                ProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                        String provinceName= nameList.get(index);
                        String provinceAdcode=adcodeList.get(index);
                        myLocationUtil.setProvinceName(provinceName);
                        Log.d("MainActivity","spinner省名是："+myLocationUtil.getProvinceName());
                        /*根据省名获取市list*/
                        getCityList(myLocationUtil.getProvinceName());
                        signalUtil.setCitySignal(signalUtil.Exist);
                        myLocationUtil.setAdcode(provinceAdcode);
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
        if(signalUtil.getCitySignal()==signalUtil.Exist){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    /*获取cityList*/
                    ArrayList<CityBean> CityList=searchBean.getCityList();
                    final ArrayList<String> nameList=new ArrayList<>();
                    final ArrayList<String> adcodeList=new ArrayList<>();
                    for(CityBean cityBean:CityList){
                        String name=cityBean.name;
                        String adcode=cityBean.adcode;
                        nameList.add(name);
                        adcodeList.add(adcode);
                    }
                    /*改变UI*/
                    Spinner CitySpinner=(Spinner)findViewById(R.id.CitySpinner);
                    /*将数据适配到spinner*/
                    CitySpinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.spinner_item,nameList));
                    /*选择监听事件*/
                    CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                            String name=nameList.get(index);
                            String adcode=adcodeList.get(index);

                            myLocationUtil.setCityName(name);
                            myLocationUtil.setAdcode(adcode);
                            getWeather();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            });
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search_city_input=(EditText)findViewById(R.id.search_city_input);
        search_city_btn=(ImageView)findViewById(R.id.search_city_btn);
        search_city_btn.setOnClickListener(this);
        searchBean=new SearchBean();
        signalUtil=new SignalUtil();
        myLocationUtil=new MyLocationUtil();
        cityWeather=new CityWeather();
        /*设置toolBar*/
        setToolBar();
        /*设置侧滑导航*/
        setNavigationIcon();
        /*获取所有location*/
        getAllLocation();
    }
    /*监听点击事件*/
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*点击搜索城市输入框中搜索按钮事件*/
            case R.id.search_city_btn:

                break;
            default:
                break;
        }
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
        for (ProvinceBean provinceBean:searchBean.getAllProvinceList()){
            /*匹配省名字*/
            if(provinceBean.name.equals(provinceName)){
                /*获取城市array*/
                searchBean.setCityArray(provinceBean.districts);

                Gson gson=new Gson();
                ArrayList<CityBean> CityList=new ArrayList<>();
                /*城市JsonArray转城市list*/
                for (JsonElement City:searchBean.getCityArray()){
                    /*JsonElement转CityBean*/
                    CityBean cityBean=gson.fromJson(City,new TypeToken<CityBean>(){}.getType());
                    CityList.add(cityBean);
                }
                /*将局部list set到SearchBean中*/
                searchBean.setCityList(CityList);
            }
        }
    }
    /*获取天气*/
    public void getWeather(){
        /*请求地址*/
        String requestURL="https://restapi.amap.com/v3/weather/weatherInfo";
        /*参数*/
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        String city=myLocationUtil.getAdcode();
        String extensions="base";
        String output="JSON";

        /*请求参数打包成map*/
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("city",city);
        params.put("extensions",extensions);
        params.put("output",output);
        /*拼接地址和参数*/
        requestURL=spliceAddress(requestURL,params);

        HttpUtil.sendOkHttpRequest(requestURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String responseData=response.body().string();
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();
                    JsonArray jsonArray=jsonObject.getAsJsonArray("lives");
                    ArrayList<CityWeather> weatherInformations=new ArrayList<>();
                    Gson gson=new Gson();
                    for (JsonElement jsonElement:jsonArray){
                        CityWeather cityWeather=gson.fromJson(jsonElement,new TypeToken<CityWeather>(){}.getType());
                        weatherInformations.add(cityWeather);
                    }
                    cityWeather.setWeather(weatherInformations.get(0).getWeather());
                    cityWeather.setTemperature(weatherInformations.get(0).getTemperature());
                    loadWeather();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    /*改变天气UI*/
    public void loadWeather(){
        final String weather=cityWeather.getWeather();
        final String temperature=cityWeather.getTemperature();
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
        /*请求地址*/
        String requestURL="https://restapi.amap.com/v3/config/district";
        /*应用Key*/
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        /*行政区返回范围*/
        String subdistrict="2";
        /*请求参数打包成map*/
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("subdistrict",subdistrict);
        /*拼接地址和参数*/
        requestURL=spliceAddress(requestURL,params);
        /*get请求*/
        HttpUtil.sendOkHttpRequest(requestURL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity","响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try{
                    /*json转String*/
                    String responseData=response.body().string();
                    /*获取json对象*/
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();
                    /*获取json最外级数组*/
                    JsonArray ALLCountryArray=jsonObject.getAsJsonArray("districts");
                    /*gson用来解析*/
                    Gson gson=new Gson();
                    /*定义国家,省，市级数组
                    * 用来存储最外级数组转换后的数据
                    * 获取所有城市*/
                    ArrayList<CountryBean> ALLCountryList=new ArrayList<>();
                    ArrayList<ProvinceBean> ALLProvinceList=new ArrayList<>();
                    /*json数组载入到java数组*/
                    for(JsonElement Country:ALLCountryArray){
                        /*反射得到CountryBean.class*/
                        CountryBean CountryBean=gson.fromJson(Country,new TypeToken<CountryBean>(){}.getType());
                        ALLCountryList.add(CountryBean);
                    }
                    /*将CountryList中json数组遍历出来*/
                    for(CountryBean CountryBean:ALLCountryList){
                        JsonArray ProvinceArray=CountryBean.districts;
                        /*将遍历的json数组载入到java数组*/
                        for(JsonElement Province:ProvinceArray){
                            /*反射得到ProvinceBean.class.class*/
                            ProvinceBean ProvinceBean=gson.fromJson(Province,new TypeToken<ProvinceBean>(){}.getType());
                            ALLProvinceList.add(ProvinceBean);
                        }
                    }
                    searchBean.setAllProvinceList(ALLProvinceList);
                    signalUtil.setProvinceSignal(signalUtil.Exist);
                    /*初始化spinner*/
                    initSpinner();
                }catch (Exception e){
                    e.printStackTrace();
                }
                /*更新UI*/
            }
        });
    }

    /*拼接请求地址和参数*/
    private String spliceAddress(String requestURL, Map<String,String> params){
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
}
