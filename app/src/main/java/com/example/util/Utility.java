package com.example.util;

import android.text.TextUtils;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*遍历服务器城市*/
public class Utility {
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                /*获取所有省*/
                JSONArray allProvinces=new JSONArray(response);
                /*遍历所有省*/
                for(int i=0;i<allProvinces.length();i++){
                    JSONObject provinceObject=allProvinces.getJSONObject(i);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
