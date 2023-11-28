package com.example.util;

import com.example.element.MyCityEle;

public class MyCityManager {
    private MyCityEle defaultCity=new MyCityEle();

    public MyCityEle getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(MyCityEle defaultCity) {
        this.defaultCity = defaultCity;
    }
}
