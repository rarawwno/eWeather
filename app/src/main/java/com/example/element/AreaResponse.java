package com.example.element;

import java.util.ArrayList;

public class AreaResponse {
    ArrayList<CountryEle> countryList;
    ArrayList<ProvinceEle> provinceList;
    ArrayList<CityEle> cityList;

    public ArrayList<CountryEle> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<CountryEle> countryList) {
        this.countryList = countryList;
    }

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
