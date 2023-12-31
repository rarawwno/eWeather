package com.example.util;

import com.example.element.CityEle;
import com.example.element.ProvinceEle;

import java.util.ArrayList;

public class SpinnerUtil {
    ArrayList<ProvinceEle> provinceList;
    ArrayList<CityEle> cityList;

    public ArrayList<ProvinceEle> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(ArrayList<ProvinceEle> provinceList) {
        this.provinceList = provinceList;
    }

    public ArrayList<CityEle> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityEle> cityList) {
        this.cityList = cityList;
    }
}
