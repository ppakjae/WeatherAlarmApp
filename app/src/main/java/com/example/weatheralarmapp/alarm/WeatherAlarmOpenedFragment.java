package com.example.weatheralarmapp.alarm;

import android.content.Context;
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

    boolean early5 = false;
    boolean early10 = false;
    boolean early15 = false;
    boolean early20 = false;

    private OnEarlySelectedListener onEarlySelectedListener;

    int[] early = new int[4];

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

        onEarlySelectedListener.onEarlySet(early);

        return rootView;
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            AlarmAddActivity alarmAddActivity = (AlarmAddActivity) getActivity();
            switch (view.getId()){
                case R.id.imgWeatherAlarm5min:
                    if(early5 == false){
                        alarm5min.setImageResource(R.drawable.five_m);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early5 = true;
                        early[0]=1;
                        early[1]=0;
                        early[2]=0;
                        early[3]=0;
                    } else{
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        early5 = false;
                        early[0]=0;
                        early[1]=0;
                        early[2]=0;
                        early[3]=0;
                    }
                    break;
                case R.id.imgWeatherAlarm10min:
                    if(early10 == false){
                        alarm10min.setImageResource(R.drawable.ten_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early10 = true;
                        early[0]=0;
                        early[1]=1;
                        early[2]=0;
                        early[3]=0;
                    } else{
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        early10 = false;
                        early[0]=0;
                        early[1]=0;
                        early[2]=0;
                        early[3]=0;
                    }
                    break;
                case R.id.imgWeatherAlarm15min:
                    if(early15 == false){
                        alarm15min.setImageResource(R.drawable.fifteen_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early15 = true;
                        early[0]=0;
                        early[1]=0;
                        early[2]=1;
                        early[3]=0;
                    } else{
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        early15 = false;
                        early[0]=0;
                        early[1]=0;
                        early[2]=0;
                        early[3]=0;
                    }
                    break;
                case R.id.imgWeatherAlarm20min:
                    if(early20 == false){
                        alarm20min.setImageResource(R.drawable.twenty_m);
                        alarm5min.setImageResource(R.drawable.five_m_g);
                        alarm10min.setImageResource(R.drawable.ten_m_g);
                        alarm15min.setImageResource(R.drawable.fifteen_m_g);
                        early20 = true;
                        early[0]=0;
                        early[1]=0;
                        early[2]=0;
                        early[3]=1;
                    } else{
                        alarm20min.setImageResource(R.drawable.twenty_m_g);
                        early20 = false;
                        early[0]=0;
                        early[1]=0;
                        early[2]=0;
                        early[3]=0;
                    }
                    break;
            }
        }
    };

    public interface OnEarlySelectedListener {

        public void onEarlySet(int[] arr);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnEarlySelectedListener){
            onEarlySelectedListener = (OnEarlySelectedListener) context;
        } else{
            throw new RuntimeException(context.toString() + "must implement OnEarlySetListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onEarlySelectedListener = null;
    }
}
