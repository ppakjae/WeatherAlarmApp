package com.example.weatheralarmapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatheralarmapp.AlarmReceiver;
import com.example.weatheralarmapp.DeviceBootReceiver;
import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.alarm.WeatherAlarmClosedFragment;
import com.example.weatheralarmapp.alarm.WeatherAlarmOpenedFragment;
import com.example.weatheralarmapp.db_connect.DBConst;
import com.example.weatheralarmapp.db_connect.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AlarmAddActivity extends AppCompatActivity implements WeatherAlarmOpenedFragment.OnEarlySelectedListener {

    WeatherAlarmOpenedFragment weatherAlarmOpenedFragment;
    WeatherAlarmClosedFragment weatherAlarmClosedFragment;

    TextView tvAlarmEditCancel;
    TextView tvAlarmEditSave;

    ImageView imgRepeatMon;
    ImageView imgRepeatTue;
    ImageView imgRepeatWed;
    ImageView imgRepeatThur;
    ImageView imgRepeatFri;
    ImageView imgRepeatSat;
    ImageView imgRepeatSun;

    boolean repeatMon = false;
    boolean repeatTue = false;
    boolean repeatWed = false;
    boolean repeatThur = false;
    boolean repeatFri = false;
    boolean repeatSat = false;
    boolean repeatSun = false;

    int [] repeatInt = new int[7];
    int [] early = new int [4];

    ArrayList<String> sound_items;
    ArrayAdapter<String> soundArray_adapter;

    DBHelper dbHelper;

    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_add);

        context = this;

        dbHelper = new DBHelper(getApplicationContext(), DBConst.ALARM_TABLE_NAME, null, DBConst.DATABASE_VERSION);

        tvAlarmEditCancel = findViewById(R.id.tvAlarmEditCancel);
        tvAlarmEditSave = findViewById(R.id.tvAlarmEditSave);

        imgRepeatMon = findViewById(R.id.imgRepeatMon);
        imgRepeatTue = findViewById(R.id.imgRepeatTue);
        imgRepeatWed = findViewById(R.id.imgRepeatWed);
        imgRepeatThur = findViewById(R.id.imgRepeatThur);
        imgRepeatFri = findViewById(R.id.imgRepeatFri);
        imgRepeatSat = findViewById(R.id.imgRepeatSat);
        imgRepeatSun = findViewById(R.id.imgRepeatSun);

        imgRepeatMon.setOnClickListener(Click);
        imgRepeatTue.setOnClickListener(Click);
        imgRepeatWed.setOnClickListener(Click);
        imgRepeatThur.setOnClickListener(Click);
        imgRepeatFri.setOnClickListener(Click);
        imgRepeatSat.setOnClickListener(Click);
        imgRepeatSun.setOnClickListener(Click);

        final TimePicker alarmTimePicker=(TimePicker)findViewById(R.id.time_picker);
        alarmTimePicker.setIs24HourView(false);

        //데이터를 저장하게 되는 리스트
        sound_items = new ArrayList<>();

        sound_items.add("기본 설정음");
        sound_items.add("gradios");

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        soundArray_adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,sound_items);
        spinner.setAdapter(soundArray_adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),sound_items.get(position)+"()가 선택되었습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // 앞서 설정한 값으로 보여주기
        // 없으면 디폴트 값은 현재시간
//        SharedPreferences sharedPreferences = getSharedPreferences("daily alarm", MODE_PRIVATE);
//        long millis = sharedPreferences.getLong("nextNotifyTime", Calendar.getInstance().getTimeInMillis());
//
//        Calendar nextNotifyTime = new GregorianCalendar();
//        nextNotifyTime.setTimeInMillis(millis);
//
//        Date nextDate = nextNotifyTime.getTime();
//        String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(nextDate);
//        Toast.makeText(getApplicationContext(),"[처음 실행시] 다음 알람은 " + date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();
//
//
//        // 이전 설정값으로 TimePicker 초기화
//        Date currentTime = nextNotifyTime.getTime();
//        SimpleDateFormat HourFormat = new SimpleDateFormat("kk", Locale.getDefault());
//        SimpleDateFormat MinuteFormat = new SimpleDateFormat("mm", Locale.getDefault());
//
//        int pre_hour = Integer.parseInt(HourFormat.format(currentTime));
//        int pre_minute = Integer.parseInt(MinuteFormat.format(currentTime));
//
//
//        if (Build.VERSION.SDK_INT >= 23 ){
//            alarmTimePicker.setHour(pre_hour);
//            alarmTimePicker.setMinute(pre_minute);
//        }
//        else{
//            alarmTimePicker.setCurrentHour(pre_hour);
//            alarmTimePicker.setCurrentMinute(pre_minute);
//        }

        tvAlarmEditSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                int hour, hour_24, minute;
                String am_pm;
                if (Build.VERSION.SDK_INT >= 23 ){
                    hour_24 = alarmTimePicker.getHour();
//                    Log.d("hour_24", hour_24+"");
                    minute = alarmTimePicker.getMinute();
//                    Log.d("minute", minute+"");
                }
                else{
                    hour_24 = alarmTimePicker.getCurrentHour();
                    minute = alarmTimePicker.getCurrentMinute();
                }
                if(hour_24 > 12) {
                    am_pm = "PM";
                    hour = hour_24 - 12;
                }
                else
                {
                    hour = hour_24;
                    am_pm="AM";
                }

                // 현재 지정된 시간으로 알람 시간 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
//                Log.d("currentTimeMillis",System.currentTimeMillis()+"");
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

//                Log.d("Calendar.HOUR_OF_DAY",Calendar.HOUR_OF_DAY+"");
//                Log.d("Calendar.MINUTE",Calendar.MINUTE+"");
//                Log.d("Calendar.SECOND",Calendar.SECOND+"");
//                Log.d("Calendar.DATE",Calendar.DATE+"");

                // 이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                Log.d("Calendar.getTime", calendar.getTime()+"");
                String date_text = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일 a hh시 mm분 ", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(getApplicationContext(),date_text + "으로 알람이 설정되었습니다!", Toast.LENGTH_SHORT).show();

                //현재는 sharedpreference에 저장하고 있음 디비에 저장해야
                //  Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
                editor.apply();

                boolean [] repeat = {repeatMon,repeatTue, repeatWed, repeatThur, repeatFri, repeatSat, repeatSun};

                for(int i = 0; i < repeat.length; i++){
                    if(repeat[i] == false){
                        repeatInt[i] = 0;
                    } else{
                        repeatInt[i] = 1;
                    }
                }

//                dbHelper.addContact("오전", 8, 10, 1, 1,0,1, 1, 1, 0, 0, 0);
                setDbHelper(am_pm, hour, minute, repeatInt, early);

                diaryNotification(calendar);

            }
        });

        tvAlarmEditCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 알람 정지 버튼
//        Button alarm_off = findViewById(R.id.btn_finish);
//        alarm_off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"Alarm 종료",Toast.LENGTH_SHORT).show();
//                // 알람매니저 취소
//                alarm_manager.cancel(pendingIntent);
//
//                my_intent.putExtra("state","alarm off");
//
//                // 알람취소
//                sendBroadcast(my_intent);
//            }
//        });


        if (findViewById(R.id.fragmentWeatherAlarm) != null)
        {
            if(savedInstanceState != null)
            {
                return;
            }

            weatherAlarmClosedFragment = new WeatherAlarmClosedFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentWeatherAlarm, weatherAlarmClosedFragment).commit();
        }

        weatherAlarmOpenedFragment = new WeatherAlarmOpenedFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.alarmFragmentContainer,weatherAlarmOpenedFragment).commit();

    }

    void diaryNotification(Calendar calendar)
    {
//        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        Boolean dailyNotify = sharedPref.getBoolean(SettingsActivity.KEY_PREF_DAILY_NOTIFICATION, true);
        Boolean dailyNotify = true; // 무조건 알람을 사용

        PackageManager pm = this.getPackageManager();
        ComponentName receiver = new ComponentName(this, DeviceBootReceiver.class);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        // 사용자가 매일 알람을 허용했다면
        if (dailyNotify) {

            if (alarmManager != null) {

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            }

            // 부팅 후 실행되는 리시버 사용가능하게 설정
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                    PackageManager.DONT_KILL_APP);

        }
//        else { //Disable Daily Notifications
//            if (PendingIntent.getBroadcast(this, 0, alarmIntent, 0) != null && alarmManager != null) {
//                alarmManager.cancel(pendingIntent);
//                //Toast.makeText(this,"Notifications were disabled",Toast.LENGTH_SHORT).show();
//            }
//            pm.setComponentEnabledSetting(receiver,
//                    PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                    PackageManager.DONT_KILL_APP);
//        }
    }

    View.OnClickListener Click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.imgRepeatMon:
                    if(repeatMon == false){
                        imgRepeatMon.setImageResource(R.drawable.mon);
                        repeatMon = true;
                    } else{
                        imgRepeatMon.setImageResource(R.drawable.mon_g);
                        repeatMon = false;
                    }
                    break;
                case R.id.imgRepeatTue:
                    if(repeatTue == false){
                        imgRepeatTue.setImageResource(R.drawable.tue);
                        repeatTue = true;
                    } else{
                        imgRepeatTue.setImageResource(R.drawable.tue_g);
                        repeatTue = false;
                    }
                    break;
                case R.id.imgRepeatWed:
                    if(repeatWed == false){
                        imgRepeatWed.setImageResource(R.drawable.wed);
                        repeatWed = true;
                    } else{
                        imgRepeatWed.setImageResource(R.drawable.wed_g);
                        repeatWed = false;
                    }
                    break;
                case R.id.imgRepeatThur:
                    if(repeatThur == false){
                        imgRepeatThur.setImageResource(R.drawable.thu);
                        repeatThur = true;
                    } else{
                        imgRepeatThur.setImageResource(R.drawable.thu_g);
                        repeatThur = false;
                    }
                    break;
                case R.id.imgRepeatFri:
                    if(repeatFri == false){
                        imgRepeatFri.setImageResource(R.drawable.fri);
                        repeatFri = true;
                    } else{
                        imgRepeatFri.setImageResource(R.drawable.fri_g);
                        repeatFri = false;
                    }
                    break;
                case R.id.imgRepeatSat:
                    if(repeatSat == false){
                        imgRepeatSat.setImageResource(R.drawable.sat);
                        repeatSat = true;
                    } else{
                        imgRepeatSat.setImageResource(R.drawable.sat_g);
                        repeatSat = false;
                    }
                    break;
                case R.id.imgRepeatSun:
                    if(repeatSun == false){
                        imgRepeatSun.setImageResource(R.drawable.sun);
                        repeatSun = true;
                    } else{
                        imgRepeatSun.setImageResource(R.drawable.sun_g);
                        repeatSun = false;
                    }
                    break;
            }
        }
    };

    public void onFragmentChange(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentWeatherAlarm, weatherAlarmOpenedFragment).commit();
        } else if(index == 1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentWeatherAlarm, weatherAlarmClosedFragment).commit();
        }
    }

    //fragment_weather_alarm_opened에 웨더 알람 몇분 일찍 울릴지 정보 배열로 받아오는 메소드(프래그먼트-> 액티비티로 데이터 전달)

    @Override
    public void onEarlySet(int[] arr) {
        // 해당 Activity 에서 사용될 동작
        early = arr;
    }

    public void setDbHelper(String am_pm, int hour, int minute, int [] repeat, int [] early){
        int weather_alarm = 0;
        int delay = 0;
        String noon;

        if(am_pm == "AM"){
            noon = "오전";
        } else{
            noon = "오후";
        }

        //delay = 0,1,2,3(각각 5분, 10분, 15분, 20분)
        for(int i = 0; i < early.length; i++){
            if(early[i] == 1){
                delay = i;
                weather_alarm = 1;
                break;
            }
            weather_alarm = 0;
        }

        Log.d("addContact", noon + " " + hour + " " + minute + " " + Arrays.toString(repeat) + " " + delay + " " + weather_alarm);

        //id 들어가야된다. 업데이트도 해야된다.
//        dbHelper.addContact(noon, hour, minute, repeat[0], repeat[1], repeat[2], repeat[3], repeat[4], repeat[5], repeat[6], delay, weather_alarm);

    }

    //알람 끄기
//    public void offAlarm(){
//        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//        Log.d("cancel", "cancel");
//    }

}
