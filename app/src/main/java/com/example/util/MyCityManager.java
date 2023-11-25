package com.example.util;

import com.example.db.MyCityBean;

public class MyCityManager {
    private MyCityBean defaultCity=new MyCityBean();

    public MyCityBean getDefaultCity() {
        return defaultCity;
    }

    public void setDefaultCity(MyCityBean defaultCity) {
        this.defaultCity = defaultCity;
    }
}
