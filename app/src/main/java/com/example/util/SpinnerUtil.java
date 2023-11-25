package com.example.util;

import com.example.db.CityBean;
import com.example.db.CountryBean;
import com.example.db.ProvinceBean;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class SpinnerUtil {
    private JsonArray CountryJsonArray;
    private ArrayList<CountryBean> CountryArrayList;
    private JsonArray provinceJsonArray;
    private ArrayList<ProvinceBean> provinceArrayList;
    private JsonArray CityJsonArray;
    private ArrayList<CityBean> CityArrayList;

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
