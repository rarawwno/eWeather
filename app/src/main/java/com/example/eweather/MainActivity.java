package com.example.eweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.db.CityBean;
import com.example.db.CountryBean;
import com.example.db.ProvinceBean;
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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    /*设置toolBar*/
    public void setToolBar(){
        Toolbar toolbar=(Toolbar)findViewById(R.id.homeToolbar);
        /*设置toolBar作为ActionBar*/
        setSupportActionBar(toolbar);
        /*隐藏supportActionBar*/
//        getSupportActionBar().hide();
    }
    /*获取行政区*/
    public void getAllLocation(){
        /*请求地址*/
        String requestURL="https://restapi.amap.com/v3/config/district";
        /*应用Key*/
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        /*请求完整路径，带参数*/
        String address="";
        /*行政区返回范围*/
        String subdistrict="2";
        /*请求参数打包成map*/
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("subdistrict",subdistrict);
        /*拼接地址和参数*/
        address=spliceAddress(requestURL,params);
        /*get请求*/
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity","响应失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try{
                    String responseData=response.body().string();
                    /*获取json对象*/
                    JsonObject jsonObject=new JsonParser().parse(responseData).getAsJsonObject();

                    /*获取json最外级数组*/
                    JsonArray CountryArray=jsonObject.getAsJsonArray("districts");

                    Gson gson=new Gson();

                    /*定义国家,省，市级数组*/
                    ArrayList<CountryBean> CountryList=new ArrayList<>();
                    ArrayList<ProvinceBean> ProvinceList=new ArrayList<>();
                    ArrayList<CityBean> CityList=new ArrayList<>();
                    /*json数组载入到java数组*/
                    for(JsonElement Country:CountryArray){
                        /*反射得到CountryBean.class*/
                        CountryBean CountryBean=gson.fromJson(Country,new TypeToken<CountryBean>(){}.getType());
                        CountryList.add(CountryBean);
                    }
                    /*将CountryList中json数组遍历出来*/
                    for(CountryBean CountryBean:CountryList){
                        JsonArray ProvinceArray=CountryBean.districts;
                        /*将遍历的json数组载入到java数组*/
                        for(JsonElement Province:ProvinceArray){
                            /*反射得到ProvinceBean.class.class*/
                            ProvinceBean ProvinceBean=gson.fromJson(Province,new TypeToken<ProvinceBean>(){}.getType());
                            ProvinceList.add(ProvinceBean);
                        }
                    }
                    /*将ProvinceList中json数组遍历出来*/
                    for(ProvinceBean ProvinceBean:ProvinceList){
                        JsonArray CityArray=ProvinceBean.districts;
                        /*将遍历的城市json数组载入到java城市数组*/
                        for(JsonElement City:CityArray){
                            CityBean CityBean=gson.fromJson(City,new TypeToken<CityBean>(){}.getType());
                            CityList.add(CityBean);
                        }
                    }
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
