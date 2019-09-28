package com.example.weatheralarmapp;


import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.weatheralarmapp.main_fragment.AlarmFragment;
import com.example.weatheralarmapp.main_fragment.MemoFragment;
import com.example.weatheralarmapp.main_fragment.WeatherFragment;
import com.example.weatheralarmapp.weather.GpsTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtBottomMenuWeather, txtBottomMenuAlarm, txtBottomMenuMemo;
    ImageView imgBottomMenuWeather, imgBottomMenuAlarm, imgBottomMenuMemo;
    FrameLayout fragmentContainer;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    public WeatherFragment weatherFragment;
    AlarmFragment alarmFragment;
    MemoFragment memoFragment;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    GpsTracker gpsTracker;
    ContentValues values;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkLocationServicesStatus()){
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }


        try{
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }


        InitGPS();                  //앱을 켜자마자 위치정보를 가져옴.
        InitView();                 //앱 시작시, 뷰 초기화
        InitFragment();             //앱 시작시, 첫번째 화면 세팅

    }

    private void InitGPS(){
        gpsTracker = new GpsTracker(MainActivity.this);
        values = new ContentValues();
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        values.put("lat", latitude);
        values.put("lon", longitude);
    }

    private void InitFragment(){
        fragmentManager = getSupportFragmentManager();
        weatherFragment = new WeatherFragment(MainActivity.this, values);
        weatherFragment.weatherAsynTask.execute();

        memoFragment = new MemoFragment();
        alarmFragment = new AlarmFragment();

        fragmentTransaction.replace(R.id.fragmentContainer, weatherFragment).commit();


    }

    private void InitView(){
        txtBottomMenuWeather = (TextView)findViewById(R.id.txtBottomMenuWeather);
        imgBottomMenuWeather = (ImageView)findViewById(R.id.imgBottomMenuWeather);
        txtBottomMenuAlarm = (TextView)findViewById(R.id.txtBottomMenuAlarm);
        imgBottomMenuAlarm = (ImageView)findViewById(R.id.imgBottomMenuAlarm);
        txtBottomMenuMemo = (TextView)findViewById(R.id.txtBottomMenuMemo);
        imgBottomMenuMemo = (ImageView)findViewById(R.id.imgBottomMenuMemo);

        txtBottomMenuWeather.setOnClickListener(bottomMenuTxtHanlder);
        txtBottomMenuMemo.setOnClickListener(bottomMenuTxtHanlder);
        txtBottomMenuAlarm.setOnClickListener(bottomMenuTxtHanlder);

        imgBottomMenuWeather.setOnClickListener(bottomMenuImgHandler);
        imgBottomMenuMemo.setOnClickListener(bottomMenuImgHandler);
        imgBottomMenuAlarm.setOnClickListener(bottomMenuImgHandler);

    }

    ImageView.OnClickListener bottomMenuImgHandler = new ImageView.OnClickListener(){
        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (v.getId())
            {
                case R.id.imgBottomMenuWeather:
                    fragmentTransaction.replace(R.id.fragmentContainer, weatherFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm_g);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo_g);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textBlue));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textGray));
                    break;
                case R.id.imgBottomMenuAlarm:
                    fragmentTransaction.replace(R.id.fragmentContainer, alarmFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather_g);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo_g);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textBlue));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textGray));

                    break;
                case R.id.imgBottomMenuMemo:
                    fragmentTransaction.replace(R.id.fragmentContainer, memoFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather_g);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm_g);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textBlue));
                    break;
            }
        }
    };
    TextView.OnClickListener bottomMenuTxtHanlder = new TextView.OnClickListener(){

        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (v.getId()){
                case R.id.txtBottomMenuWeather:
                    fragmentTransaction.replace(R.id.fragmentContainer, weatherFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm_g);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo_g);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textBlue));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textGray));
                    break;
                case R.id.txtBottomMenuAlarm:
                    fragmentTransaction.replace(R.id.fragmentContainer, alarmFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather_g);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo_g);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textBlue));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textGray));
                    break;
                case R.id.txtBottomMenuMemo:
                    fragmentTransaction.replace(R.id.fragmentContainer, memoFragment).commit();
                    imgBottomMenuWeather.setImageResource(R.drawable.weather_g);
                    imgBottomMenuAlarm.setImageResource(R.drawable.alarm_g);
                    imgBottomMenuMemo.setImageResource(R.drawable.memo);
                    txtBottomMenuWeather.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuAlarm.setTextColor(getResources().getColor(R.color.textGray));
                    txtBottomMenuMemo.setTextColor(getResources().getColor(R.color.textBlue));
                    break;
            }
        }
    };

    public String getCurrentAddress(double latitude, double longitude){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        try{
            addresses = geocoder.getFromLocation(latitude, longitude, 7);

        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    public boolean checkLocationServicesStatus(){
        LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case GPS_ENABLE_REQUEST_CODE :
                if (checkLocationServicesStatus()){
                    if(checkLocationServicesStatus()){
                        Log.d("@@@", "onActivityResult: GPS 활성화 되있음");
                        checkRunTimePermission();
                        return ;
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length){
            boolean check_result = true;

            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false;
                    break;
                }
            }

            if(check_result){

            } else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])){
                    Toast.makeText(MainActivity.this, "Permission deny, restart app", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "permission deny, 설정에서 퍼미션 허용 바람.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if(hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED){

        } else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])){
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();

                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

}
