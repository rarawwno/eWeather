package com.example.db;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class SearchBean {
    JsonArray AllProvinceArray;
    ArrayList<ProvinceBean> AllProvinceList;
    JsonArray CityArray;
    ArrayList<CityBean> CityList;

    public JsonArray getAllProvinceArray() {
        return AllProvinceArray;
    }

    public void setAllProvinceArray(JsonArray allProvinceArray) {
        AllProvinceArray = allProvinceArray;
    }

    public ArrayList<ProvinceBean> getAllProvinceList() {
        return AllProvinceList;
    }

    public void setAllProvinceList(ArrayList<ProvinceBean> allProvinceList) {
        AllProvinceList = allProvinceList;
    }

    public JsonArray getCityArray() {
        return CityArray;
    }

    public void setCityArray(JsonArray cityArray) {
        CityArray = cityArray;
    }

    public ArrayList<CityBean> getCityList() {
        return CityList;
    }

    public void setCityList(ArrayList<CityBean> cityList) {
        CityList = cityList;
    }
}
