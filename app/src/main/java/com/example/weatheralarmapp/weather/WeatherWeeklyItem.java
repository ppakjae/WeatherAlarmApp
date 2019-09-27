package com.example.weatheralarmapp.weather;

public class WeatherWeeklyItem {
    private String day;
    private String skyName;
    private String tmax;
    private String tmin;

    public WeatherWeeklyItem(String day, String skyName, String tmax, String tmin) {
        this.day = day;
        this.skyName = skyName;
        this.tmax = tmax;
        this.tmin = tmin;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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
}
