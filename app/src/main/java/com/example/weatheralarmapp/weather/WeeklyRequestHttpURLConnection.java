package com.example.weatheralarmapp.weather;


import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeeklyRequestHttpURLConnection {
    URL url = null;
    HttpURLConnection httpURLConnection = null;

    ContentValues values = null;

    static final String REQUEST_LOCATION_URL_WEEKLY = "https://apis.openapi.sk.com/weather/forecast/6days?";    //주간 날짜 업뎃
    static final String APP_KEY = "21ba537b-98d5-4ac0-ae6e-7f8cdf80af64";
    String params = "";

    public WeeklyRequestHttpURLConnection(ContentValues reqLocation){
//        String lat = (String) reqLocation.get("lat");
//        String lon = (String) reqLocation.get("lon");
        String  lat = String.valueOf(reqLocation.get("lat"));
        String lon = String.valueOf(reqLocation.get("lon"));
        params = "appkey="+APP_KEY+"&lat="+lat+"&lon="+lon;

        try{
            url = new URL(REQUEST_LOCATION_URL_WEEKLY+params);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("content-type", "application/json; charset=UTF-8");    //api 호출을 헤더파일을 이와 같이 지어줘야함.
            httpURLConnection.setRequestProperty("Accept", "application/json");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String connect(){
        String lines = "";
        String line = "";

        BufferedReader reader = null;
        try{
            httpURLConnection.connect();
            InputStream in = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));

            while( (line = reader.readLine()) != null){
                lines += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }

        return lines;
    }
}

