package com.example.element;

public class MyCityEle {
    String provinceName;
    String cityName;
    String adcode;
    CityWeatherEle cityWeatherEle =new CityWeatherEle();
    WeatherResponse weatherResponse=new WeatherResponse();

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public CityWeatherEle getCityWeatherEle() {
        return cityWeatherEle;
    }

    public void setCityWeatherEle(CityWeatherEle cityWeatherEle) {
        this.cityWeatherEle = cityWeatherEle;
    }
}
