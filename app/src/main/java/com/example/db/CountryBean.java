package com.example.db;

import com.google.gson.JsonArray;

public class CountryBean {
    private String name;
    private JsonArray districts;

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
}
