package com.example.element;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class DistrictResponse {
    String status;
    String info;
    String count;
    ArrayList<Country> countryList;
    public class Country{
        String name;
        ArrayList<Province> provinceList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Province> getProvinceList() {
            return provinceList;
        }

        public void setProvinceList(ArrayList<Province> provinceList) {
            this.provinceList = provinceList;
        }

        public class Province{
            String name;
            ArrayList<City> cityList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<City> getCityList() {
                return cityList;
            }

            public void setCityList(ArrayList<City> cityList) {
                this.cityList = cityList;
            }

            public class City{
                String name;
                String adcode;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAdcode() {
                    return adcode;
                }

                public void setAdcode(String adcode) {
                    this.adcode = adcode;
                }
            }
        }
    }

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<Country> countryList) {
        this.countryList = countryList;
    }
}
