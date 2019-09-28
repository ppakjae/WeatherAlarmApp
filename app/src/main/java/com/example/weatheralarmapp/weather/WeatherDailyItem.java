package com.example.weatheralarmapp.weather;

public class WeatherDailyItem {
    private String skyName;     //하늘정보
    private String tc;          //지금 기온
    private String tmax;        //최고 기온
    private String tmin;        //최저 기온
    private String areaName;    //지역 이름
    private String time;        //현재 시간

    public WeatherDailyItem(String skyName, String tc, String tmax, String tmin, String areaName, String time) {
        this.skyName = skyName;
        this.tc = tc;
        this.tmax = tmax;
        this.tmin = tmin;
        this.areaName = areaName;
        this.time = time;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSkyName() {
        return skyName;
    }

    public void setSkyName(String skyName) {
        this.skyName = skyName;
    }

    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }

    public String getTmin() {
        return tmin;
    }

    public void setTmin(String tmin) {
        this.tmin = tmin;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
