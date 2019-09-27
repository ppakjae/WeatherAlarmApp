package com.example.weatheralarmapp.weather;

public class WeatherDayilyHourlyItem {
    private String time;            //release Time 기점 몇시간 후
    private String temperature;     //기온
    private String sky;             //하늘정보

    public WeatherDayilyHourlyItem(String time, String temperature, String sky) {
        this.time = time;
        this.temperature = temperature;
        this.sky = sky;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }
}
