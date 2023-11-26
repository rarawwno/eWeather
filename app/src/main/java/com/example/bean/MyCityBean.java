package com.example.bean;

public class MyCityBean {
    String provinceName;
    String cityName;
    String adcode;
    CityWeatherBean cityWeatherBean=new CityWeatherBean();
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

    public CityWeatherBean getCityWeatherBean() {
        return cityWeatherBean;
    }

    public void setCityWeatherBean(CityWeatherBean cityWeatherBean) {
        this.cityWeatherBean = cityWeatherBean;
    }
}
