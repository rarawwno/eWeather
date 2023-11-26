package com.example.bean;

import com.google.gson.JsonArray;

public class CityBean {
    public String name;
    public JsonArray districts;
    public String adcode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonArray getDistricts() {
        return districts;
    }

    public void setDistricts(JsonArray districts) {
        this.districts = districts;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }
}
