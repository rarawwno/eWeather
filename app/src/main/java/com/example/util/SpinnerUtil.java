package com.example.util;

import com.example.bean.CityBean;
import com.example.bean.CountryBean;
import com.example.bean.ProvinceBean;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class SpinnerUtil {
    private JsonArray CountryJsonArray;
    private ArrayList<CountryBean> CountryArrayList=new ArrayList<>();
    private JsonArray provinceJsonArray;
    private ArrayList<ProvinceBean> provinceArrayList=new ArrayList<>();
    private JsonArray CityJsonArray;
    private ArrayList<CityBean> CityArrayList=new ArrayList<>();

    public JsonArray getCountryJsonArray() {
        return CountryJsonArray;
    }

    public void setCountryJsonArray(JsonArray countryJsonArray) {
        CountryJsonArray = countryJsonArray;
    }

    public ArrayList<CountryBean> getCountryArrayList() {
        return CountryArrayList;
    }

    public void setCountryArrayList(ArrayList countryArrayList) {
        CountryArrayList = countryArrayList;
    }

    public JsonArray getCityJsonArray() {
        return CityJsonArray;
    }

    public void setCityJsonArray(JsonArray cityJsonArray) {
        CityJsonArray = cityJsonArray;
    }

    public JsonArray getProvinceJsonArray() {
        return provinceJsonArray;
    }

    public void setProvinceJsonArray(JsonArray provinceJsonArray) {
        this.provinceJsonArray = provinceJsonArray;
    }

    public ArrayList<CityBean> getCityArrayList() {
        return CityArrayList;
    }

    public void setCityArrayList(ArrayList cityArrayList) {
        CityArrayList = cityArrayList;
    }

    public ArrayList<ProvinceBean> getProvinceArrayList() {
        return provinceArrayList;
    }

    public void setProvinceArrayList(ArrayList provinceArrayList) {
        this.provinceArrayList = provinceArrayList;
    }
}
