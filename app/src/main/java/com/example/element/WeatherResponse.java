package com.example.element;

import com.google.gson.JsonArray;

public class WeatherResponse {
    String status;
    String info;
    JsonArray lives;
    JsonArray forecast;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public JsonArray getLives() {
        return lives;
    }

    public void setLives(JsonArray lives) {
        this.lives = lives;
    }

    public JsonArray getForecast() {
        return forecast;
    }

    public void setForecast(JsonArray forecast) {
        this.forecast = forecast;
    }
}
