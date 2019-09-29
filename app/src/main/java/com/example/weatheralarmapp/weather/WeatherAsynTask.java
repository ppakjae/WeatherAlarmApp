package com.example.weatheralarmapp.weather;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.main_fragment.WeatherFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

// < >안에 들은 자료형은 순서대로 doInBackground, onProgressUpdate, onPostExecute의 매개변수 자료형을 뜻한다.(내가 사용할 매개변수타입을 설정하면된다)
public class WeatherAsynTask extends AsyncTask<String, String, String> {

//    MainActivity mainActivity;
    WeatherFragment weatherFragment;
    private String url;
    private ContentValues contentValues;
    public ArrayList<WeatherWeeklyItem> weatherWeeklyItemArrayList;
    public ArrayList<WeatherDayilyHourlyItem> weatherDailyHourlyItemArrayList;

    public WeatherAsynTask(String url, ContentValues contentValues, WeatherFragment weatherFragment){
        this.url = url;
        this.contentValues = contentValues;
        this.weatherFragment = weatherFragment;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result;
        DailyRequestHttpURLConnection dailyRequestHttpURLConnection = new DailyRequestHttpURLConnection(contentValues);
        WeeklyRequestHttpURLConnection weeklyRequestHttpURLConnection = new WeeklyRequestHttpURLConnection(contentValues);
        DailyHourlyRequestHttpURLConnection dailyHourlyRequestHttpURLConnection = new DailyHourlyRequestHttpURLConnection(contentValues);

        result = dailyRequestHttpURLConnection.connect();
        result += "@#";
        result += weeklyRequestHttpURLConnection.connect();
        result += "@#";
        result += dailyHourlyRequestHttpURLConnection.connect();

        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        ((TextView)weatherFragment.findViewById(R.id.txt1)).setText(s);
//        weatherFragment.getView().findViewById(R.id.)

        String[] result = s.split("@#");
        try {

//            Daily JSON Parse
            JSONObject dailyJsonObect = new JSONObject(result[0]);
            JSONObject weather = dailyJsonObect.getJSONObject("weather");
            JSONArray hourly = weather.getJSONArray("hourly");
//            Toast.makeText(weatherFragment.getContext(), dailyJsonObect.toString(), Toast.LENGTH_SHORT).show();

            JSONObject hourly_1 = hourly.getJSONObject(0);      //지역 grid index

            //지역 이름가져오기
            JSONObject grid = hourly_1.getJSONObject("grid");
            String city = grid.getString("city");
            String county = grid.getString("county");
            String village = grid.getString("village");
            String area = city+" "+county+" "+village;
            String area2 = city+" "+village;

            //하늘 상태정보 가져오기
            JSONObject sky = hourly_1.getJSONObject("sky");
            String skyName = sky.getString("name");


            switch (skyName)
            {
                case "맑음":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon01);
                    break;
                case "구름조금":
                case "구름많음":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon02);
                    break;
                case "구름많고 비":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon13);
                    break;
                case "구름많고 눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon14);
                    break;
                case "구름많고 비 또는 눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon15);
                    break;
                case "흐림":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon03);
                    break;
                case "흐리고 눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon08);
                    break;
                case "흐리고 비 또는 눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon06);
                    break;
                case "흐리고 낙뢰":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon10);
                    break;
                case "뇌우/비":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon10);
                    break;
                case "뇌우/눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon22);
                    break;
                case "뇌우/비 또는 눈":
                    ((ImageView)weatherFragment.getView().findViewById(R.id.imgWeather)).setImageResource(R.drawable.icon22);
                    break;
                    default:
            }

            //최고, 최저 기온 가져오기
            Double temp;
            JSONObject temperature = hourly_1.getJSONObject("temperature");
            String tc = temperature.getString("tc");
            temp = Double.valueOf(tc);
            tc = String.valueOf(Math.round((temp*100)/100));


            String tmax = temperature.getString("tmax");
            temp = Double.valueOf(tmax);
            tmax = String.valueOf(Math.round((temp*100)/100));

            String tmin = temperature.getString("tmin");
            temp = Double.valueOf(tmin);
            tmin = String.valueOf(Math.round((temp*100)/100));

            String timeRelease = hourly_1.getString("timeRelease");
            String timeReleaseSplit[] = timeRelease.split(" ");
            String day[] = timeReleaseSplit[0].split("-");
            String dayDate = day[1]+"."+day[2];



            ((TextView)weatherFragment.getView().findViewById(R.id.txtMainTemper)).setText(tc);
            ((TextView)weatherFragment.getView().findViewById(R.id.txtHighTemper)).setText(tmax);
            ((TextView)weatherFragment.getView().findViewById(R.id.txtMinTemper)).setText(tmin);
            ((TextView)weatherFragment.getView().findViewById(R.id.txtWeather)).setText(skyName);
            ((TextView)weatherFragment.getView().findViewById(R.id.txtArea)).setText(area2);
            ((TextView)weatherFragment.getView().findViewById(R.id.txtDate)).setText(dayDate);
            //Day 요일 설정
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            String weekDay = dayFormat.format(calendar.getTime());
            ((TextView)weatherFragment.getView().findViewById(R.id.txtDay)).setText(weekDay);

            System.out.println("Main Weather Mark Complete");


//            Weekly JSON Parse
            JSONObject weeklyJsonObject = new JSONObject(result[1]);
            ArrayList<String> weeklySkyNameList = new ArrayList<>();
            ArrayList<String> weeklyTmaxList = new ArrayList<>();
            ArrayList<String> weeklyTminList = new ArrayList<>();
            JSONObject weekly_weather = weeklyJsonObject.getJSONObject("weather");
            JSONArray forecast6days = weekly_weather.getJSONArray("forecast6days");
            JSONObject weekly = forecast6days.getJSONObject(0);

            JSONObject weeklySky = weekly.getJSONObject("sky");
            String day2 = weeklySky.getString("pmName2day");
            String day3 = weeklySky.getString("pmName3day");
            String day4 = weeklySky.getString("pmName4day");
            String day5 = weeklySky.getString("pmName5day");
            String day6 = weeklySky.getString("pmName6day");
            String day7 = weeklySky.getString("pmName7day");
            String day8 = weeklySky.getString("pmName8day");
            String day9 = weeklySky.getString("pmName9day");
            String day10 = weeklySky.getString("pmName10day");
            weeklySkyNameList.add(day2);                //0
            weeklySkyNameList.add(day3);                //1
            weeklySkyNameList.add(day4);                //2
            weeklySkyNameList.add(day5);                //3
            weeklySkyNameList.add(day6);                //4
            weeklySkyNameList.add(day7);                //5
            weeklySkyNameList.add(day8);                //6
            weeklySkyNameList.add(day9);                //7
            weeklySkyNameList.add(day10);               //8


            JSONObject weeklyTemperature = weekly.getJSONObject("temperature");
            String tmax2day = weeklyTemperature.getString("tmax2day");
            String tmax3day = weeklyTemperature.getString("tmax3day");
            String tmin2day = weeklyTemperature.getString("tmin2day");
            String tmin3day = weeklyTemperature.getString("tmin3day");
            String tmax4day = weeklyTemperature.getString("tmax4day");
            String tmax5day = weeklyTemperature.getString("tmax5day");
            String tmin4day = weeklyTemperature.getString("tmin4day");
            String tmin5day = weeklyTemperature.getString("tmin5day");
            String tmax6day = weeklyTemperature.getString("tmax6day");
            String tmax7day = weeklyTemperature.getString("tmax7day");
            String tmin6day = weeklyTemperature.getString("tmin6day");
            String tmin7day = weeklyTemperature.getString("tmin7day");
            String tmax8day = weeklyTemperature.getString("tmax8day");
            String tmax9day = weeklyTemperature.getString("tmax9day");
            String tmin8day = weeklyTemperature.getString("tmin8day");
            String tmin9day = weeklyTemperature.getString("tmin9day");
            String tmax10day = weeklyTemperature.getString("tmax10day");
            String tmin10day = weeklyTemperature.getString("tmin10day");
            weeklyTmaxList.add(tmax2day);   weeklyTminList.add(tmin2day);           //0
            weeklyTmaxList.add(tmax3day);   weeklyTminList.add(tmin3day);           //1
            weeklyTmaxList.add(tmax4day);   weeklyTminList.add(tmin4day);           //2
            weeklyTmaxList.add(tmax5day);   weeklyTminList.add(tmin5day);           //3
            weeklyTmaxList.add(tmax6day);   weeklyTminList.add(tmin6day);           //4
            weeklyTmaxList.add(tmax7day);   weeklyTminList.add(tmin7day);           //5
            weeklyTmaxList.add(tmax8day);   weeklyTminList.add(tmin8day);           //6
            weeklyTmaxList.add(tmax9day);   weeklyTminList.add(tmin9day);           //7
            weeklyTmaxList.add(tmax10day);  weeklyTminList.add(tmin10day);          //8

            weatherWeeklyItemArrayList = new ArrayList<>();
            for(int i=0;i<weeklyTmaxList.size();i++){
                weatherWeeklyItemArrayList.add(new WeatherWeeklyItem(i+2+"days later", weeklySkyNameList.get(i), weeklyTmaxList.get(i), weeklyTminList.get(i)));
            }
            for(int i=0;i<weatherWeeklyItemArrayList.size();i++){
                System.out.println("weekly item"+i+":"+weatherWeeklyItemArrayList.get(i).getDay());
            }
            WeeklyAdapter weeklyAdapter = new WeeklyAdapter(weatherFragment.mainActivity, weatherWeeklyItemArrayList);
            RecyclerView weeklyRecyclerView = ((RecyclerView)weatherFragment.getView().findViewById(R.id.horizontalViewWeekly));

            weeklyRecyclerView.setAdapter(weeklyAdapter);
            weeklyRecyclerView.invalidate();
            weeklyRecyclerView.getAdapter().notifyDataSetChanged();

            System.out.println("Weekly RecyclerView  Complete");



            //오늘 하루 3시간 단위로 보여줄 데이터 받아오는 부분. realseTime 기준으로 4, 7, 10 ... 시 단위로 간격
            JSONObject dailyHourlyJsonObject = new JSONObject(result[2]);
            JSONObject dailyHourly_weather = dailyHourlyJsonObject.getJSONObject("weather");
            JSONArray dailyHourly_forecast3days = dailyHourly_weather.getJSONArray("forecast3days");
            JSONObject dailyHourly_subArray = dailyHourly_forecast3days.getJSONObject(0);
            JSONObject dailyHourly_subObject = dailyHourly_subArray.getJSONObject("fcst3hour");

            String dayilyHourly_timeRelease = dailyHourly_subArray.getString("timeRelease");    //데이터 제공되는 시간

            JSONObject dayilyHourly_sky = dailyHourly_subObject.getJSONObject("sky");                    //시간 단위 하늘정보 파싱부분
            String dailyHourly_sky_4hours_later = dayilyHourly_sky.getString("name4hour");
            String dailyHourly_sky_7hours_later = dayilyHourly_sky.getString("name7hour");
            String dailyHourly_sky_10hours_later = dayilyHourly_sky.getString("name10hour");
            String dailyHourly_sky_13hours_later = dayilyHourly_sky.getString("name13hour");
            String dailyHourly_sky_16hours_later = dayilyHourly_sky.getString("name16hour");
            String dailyHourly_sky_19hours_later = dayilyHourly_sky.getString("name19hour");
            HashMap<Integer, String> dailyHourly_sky_statement_hashMap = new HashMap<>();
            dailyHourly_sky_statement_hashMap.put(4, dailyHourly_sky_4hours_later);
            dailyHourly_sky_statement_hashMap.put(7, dailyHourly_sky_7hours_later);
            dailyHourly_sky_statement_hashMap.put(10, dailyHourly_sky_10hours_later);
            dailyHourly_sky_statement_hashMap.put(13, dailyHourly_sky_13hours_later);
            dailyHourly_sky_statement_hashMap.put(16, dailyHourly_sky_16hours_later);
            dailyHourly_sky_statement_hashMap.put(19, dailyHourly_sky_19hours_later);

            JSONObject dailyHourly_temperature = dailyHourly_subObject.getJSONObject("temperature");
            String dailyHourly_temperature_4hours_later = dailyHourly_temperature.getString("temp4hour");
            String dailyHourly_temperature_7hours_later = dailyHourly_temperature.getString("temp7hour");
            String dailyHourly_temperature_10hours_later = dailyHourly_temperature.getString("temp10hour");
            String dailyHourly_temperature_13hours_later = dailyHourly_temperature.getString("temp13hour");
            String dailyHourly_temperature_16hours_later = dailyHourly_temperature.getString("temp16hour");
            String dailyHourly_temperature_19hours_later = dailyHourly_temperature.getString("temp19hour");
            HashMap<Integer, String>dailyHourly_temperature_hashMap = new HashMap<>();
            dailyHourly_temperature_hashMap.put(4, dailyHourly_temperature_4hours_later);
            dailyHourly_temperature_hashMap.put(7, dailyHourly_temperature_7hours_later);
            dailyHourly_temperature_hashMap.put(10, dailyHourly_temperature_10hours_later);
            dailyHourly_temperature_hashMap.put(13, dailyHourly_temperature_13hours_later);
            dailyHourly_temperature_hashMap.put(16, dailyHourly_temperature_16hours_later);
            dailyHourly_temperature_hashMap.put(19, dailyHourly_temperature_19hours_later);

            weatherDailyHourlyItemArrayList = new ArrayList<>();
            for(int i=4;i<=19;i+=3){
                weatherDailyHourlyItemArrayList.add(new WeatherDayilyHourlyItem(String.valueOf(i),
                                                                                    dailyHourly_temperature_hashMap.get(i),
                                                                                    dailyHourly_sky_statement_hashMap.get(i)));
            }
            //제대로 리스트 만들어진거 확인 완료.
//            for(int i=0;i<weatherDailyHourlyItemArrayList.size();i++){
//                System.out.println("daily item"+i+":"+weatherDailyHourlyItemArrayList.get(i).getTime());
//            }
//            DailyAdapter dailyAdapter = new DailyAdapter(weatherFragment.mainActivity, weatherDailyHourlyItemArrayList);
//            dailyAdapter.updateData(weatherDailyHourlyItemArrayList);

            DailyAdapter dailyAdapter = new DailyAdapter(weatherFragment.mainActivity, weatherDailyHourlyItemArrayList);
            RecyclerView dailyRecyclerView = ((RecyclerView)weatherFragment.getView().findViewById(R.id.horizontalViewDaily));
//            dailyRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            dailyRecyclerView.setAdapter(dailyAdapter);
            dailyRecyclerView.invalidate();

            dailyRecyclerView.getAdapter().notifyDataSetChanged();


            System.out.println("Daily RecyclerView Complete");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        onCancelled();
    }


}
