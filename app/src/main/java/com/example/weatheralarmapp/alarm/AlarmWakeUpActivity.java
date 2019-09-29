package com.example.weatheralarmapp.alarm;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatheralarmapp.AlarmAddActivity;
import com.example.weatheralarmapp.AlarmReceiver;
import com.example.weatheralarmapp.AlarmSoundService;
import com.example.weatheralarmapp.R;

import java.text.SimpleDateFormat;

public class AlarmWakeUpActivity extends AppCompatActivity {

    Button btnAlarmWakeUp;

    TextView tvNoon;
    TextView tvAlarmTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_wake_up);

        btnAlarmWakeUp = (Button)findViewById(R.id.btnAlarmWakeUp);

        tvNoon = (TextView)findViewById(R.id.tvNoon);
        tvAlarmTime = (TextView)findViewById(R.id.tvAlarmTime);

        SimpleDateFormat format1 = new SimpleDateFormat("HH : mm");
        String format_time = format1.format (System.currentTimeMillis());

        SimpleDateFormat format2 = new SimpleDateFormat("HH");
        String format_noon = format2.format (System.currentTimeMillis());

        if(Integer.parseInt(format_noon) > 12){
            tvNoon.setText("오후");
        }
        //24시간 확인해보고 시간도 바꾸기
        tvAlarmTime.setText(format_time);

//        PackageManager pm = this.getPackageManager();
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        btnAlarmWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((AlarmAddActivity)AlarmAddActivity.context).offAlarm();

                Intent intent = new Intent(getApplicationContext(), AlarmSoundService.class);
                stopService(intent); // 서비스 종료
                Log.d("offAlarm", "offAlarm");
                //어플리케이션 실행 종료-> 완전히 종료되는 것인가??
                finish();

            }        });
    }
}
