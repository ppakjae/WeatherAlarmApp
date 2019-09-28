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
