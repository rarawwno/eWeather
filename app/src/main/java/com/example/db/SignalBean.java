package com.example.db;

public class SignalBean {
    public final int Exist=1;
    public final int notExist=0;
    /*Spinner信号*/
    int CountrySignal=Exist;
    int ProvinceSignal=notExist;
    int CitySignal=notExist;

    public int getProvinceSignal() {
        return ProvinceSignal;
    }

    public void setProvinceSignal(int provinceSignal) {
        ProvinceSignal = provinceSignal;
    }

    public int getCitySignal() {
        return CitySignal;
    }

    public void setCitySignal(int citySignal) {
        CitySignal = citySignal;
    }

    public int getCountrySignal() {
        return CountrySignal;
    }

    public void setCountrySignal(int countrySignal) {
        CountrySignal = countrySignal;
    }
}
