package com.example.weatheralarmapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.alarm.AlarmWakeUpActivity;
import com.example.weatheralarmapp.db_connect.DBConst;
import com.example.weatheralarmapp.db_connect.DBHelper;

public class AlarmSoundService extends Service {

//    DBHelper dbHelper = new DBHelper(getApplicationContext(), DBConst.ALARM_TABLE_NAME, null, DBConst.DATABASE_VERSION);
    MediaPlayer mp;
    public AlarmSoundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        mp = MediaPlayer.create(this, R.raw.gradius);
        mp.setLooping(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        dbHelper.


        Intent StartIntent = new Intent(getApplicationContext(), AlarmWakeUpActivity.class);
        Toast.makeText(this, "알람이 울립니다.", Toast.LENGTH_SHORT).show();
        Log.i("alarmSet","set");

         mp.start();

//        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_RINGTONE);
//        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
//        if (ringtone != null) {
//            ringtone.setStreamType(AudioManager.STREAM_ALARM);
//            ringtone.play();
//        }
        startActivity(StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mp.stop();
        Log.d("service", "stop");
    }
}
