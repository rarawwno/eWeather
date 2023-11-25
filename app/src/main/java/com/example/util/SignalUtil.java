package com.example.util;

public class SignalUtil {
    public final int Exist=1;
    public final int notExist=0;
    /*城市Spinner信号*/
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
}
