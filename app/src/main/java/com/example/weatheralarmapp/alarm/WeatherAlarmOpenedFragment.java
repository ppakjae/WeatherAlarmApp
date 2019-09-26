package com.example.weatheralarmapp.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.AlarmAddActivity;

public class WeatherAlarmOpenedFragment extends Fragment {

    ImageView alarm5min;
    ImageView alarm10min;
    ImageView alarm15min;
    ImageView alarm20min;

    Boolean early5 = false;
    Boolean early10 = false;
    Boolean early15 = false;
    Boolean early20 = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weather_alarm_opened, container, false);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imgWeatherAlarmClose);
        alarm5min = (ImageView) rootView.findViewById(R.id.imgWeatherAlarm5min);
        alarm10min = (ImageView) rootView.findViewById(R.id.imgWeatherAlarm10min);
        alarm15min = (ImageView) rootView.findViewById(R.id.imgWeatherAlarm15min);
        alarm20min = (ImageView) rootView.findViewById(R.id.imgWeatherAlarm20min);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmAddActivity alarmAddActivity = (AlarmAddActivity) getActivity();
                alarmAddActivity.onFragmentChange(1);
            }
        });

        alarm5min.setOnClickListener(Click);
        alarm10min.setOnClickListener(Click);
        alarm15min.setOnClickListener(Click);
        alarm20min.setOnClickListener(Click);

        return rootView;
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.imgWeatherAlarm5min:
                    if(early5 == false){
                        alarm5min.setImageResource(R.drawable.five_m);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early5 = true;
                    } else{
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        early5 = false;
                    }
                    break;
                case R.id.imgWeatherAlarm10min:
                    if(early10 == false){
                        alarm10min.setImageResource(R.drawable.ten_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early10 = true;
                    } else{
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        early10 = false;
                    }
                    break;
                case R.id.imgWeatherAlarm15min:
                    if(early15 == false){
                        alarm15min.setImageResource(R.drawable.fifteen_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early15 = true;
                    } else{
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        early15 = false;
                    }
                    break;
                case R.id.imgWeatherAlarm20min:
                    if(early20 == false){
                        alarm20min.setImageResource(R.drawable.twenty_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        early20 = true;
                    } else{
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early20 = false;
                    }
                    break;
            }
        }
    };
}
