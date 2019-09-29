package com.example.weatheralarmapp.alarm;


import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.weatheralarmapp.R;

public class AlarmItem {
    String noon;
    int hour;
    int minute;

//    boolean checkOnOff = false;

    int mon = R.drawable.mon_g;
    int tue = R.drawable.tue_g;
    int wed = R.drawable.wed_g;
    int thu = R.drawable.thu_g;
    int fri = R.drawable.fri_g;

    int sat = R.drawable.sat_g;
    int sun = R.drawable.sun_g;

    int bMon;
    int bTue;
    int bWed;
    int bThu;
    int bFri;
    int bSat;
    int bSun;

    int delay;
    int weather;

    int onoff;

    boolean isSelected = false;
    boolean isTbSelected = true;

    ////////view//////////
    public ToggleButton toggleButton;
    public CheckBox cbAlarmDeleteCheck;
    Drawable ivAlarmEdit;

//    public boolean tbDeleteChecked = tbAlarmDeleteCheck.isChecked();
//    public boolean tbChecked = toggleButton.isChecked();

    public AlarmItem() {
    }

    public AlarmItem(String noon, int hour, int minute, int bMon, int bTue, int bWed, int bThu, int bFri, int bSat, int bSun, int delay, int weather, int onoff){
        this.noon = noon;
        this.hour = hour;
        this.minute = minute;
        this.bMon = bMon;
        this.bTue = bTue;
        this.bWed = bWed;
        this.bThu = bThu;
        this.bFri = bFri;
        this.bSat = bSat;
        this.bSun = bSun;
        this.weather = weather;
        this.delay = delay;
        this.onoff = onoff;
    }

    public String getNoon() {
        return noon;
    }

    public void setNoon(String noon) {
        this.noon = noon;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public int getTue() {
        return tue;
    }

    public void setTue(int tue) {
        this.tue = tue;
    }

    public int getWed() {
        return wed;
    }

    public void setWed(int wed) {
        this.wed = wed;
    }

    public int getThu() {
        return thu;
    }

    public void setThu(int thu) {
        this.thu = thu;
    }

    public int getFri() {
        return fri;
    }

    public void setFri(int fri) {
        this.fri = fri;
    }

    public int getSat() {
        return sat;
    }

    public void setSat(int sat) {
        this.sat = sat;
    }

    public int getSun() {
        return sun;
    }

    public void setSun(int sun) {
        this.sun = sun;
    }

    public int getbMon() {
        return bMon;
    }

    public void setbMon(int bMon) {
        this.bMon = bMon;
    }

    public int getbTue() {
        return bTue;
    }

    public void setbTue(int bTue) {
        this.bTue = bTue;
    }

    public int getbWed() {
        return bWed;
    }

    public void setbWed(int bWed) {
        this.bWed = bWed;
    }

    public int getbThu() {
        return bThu;
    }

    public void setbThu(int bThu) {
        this.bThu = bThu;
    }

    public int getbFri() {
        return bFri;
    }

    public void setbFri(int bFri) {
        this.bFri = bFri;
    }

    public int getbSat() {
        return bSat;
    }

    public void setbSat(int bSat) {
        this.bSat = bSat;
    }

    public int getbSun() {
        return bSun;
    }

    public void setbSun(int bSun) {
        this.bSun = bSun;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getWeather() {
        return weather;
    }

    public void setOnoff(int onoff){
        this.onoff = onoff;
    }

    public int getOnoff(){
        return onoff;
    }

    public boolean isTbSelected() {
        return isTbSelected;
    }

    public void setTbSelected(boolean tbSelected) {
        isTbSelected = tbSelected;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public ToggleButton getToggleButton() {
        return toggleButton;
    }

    public void setToggleButton(ToggleButton toggleButton) {
        this.toggleButton = toggleButton;
    }

    public CheckBox getCbAlarmDeleteCheck() {
        return cbAlarmDeleteCheck;
    }

    public void setCbAlarmDeleteCheck(CheckBox cbAlarmDeleteCheck) {
        this.cbAlarmDeleteCheck = cbAlarmDeleteCheck;
    }

    public Drawable getIvAlarmEdit() {
        return ivAlarmEdit;
    }

    public void setIvAlarmEdit(Drawable ivAlarmEdit) {
        this.ivAlarmEdit = ivAlarmEdit;
    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "noon='" + noon + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", mon=" + mon +
                ", tue=" + tue +
                ", wed=" + wed +
                ", thu=" + thu +
                ", fri=" + fri +
                ", sat=" + sat +
                ", sun=" + sun +
                ", bMon=" + bMon +
                ", bTue=" + bTue +
                ", bWed=" + bWed +
                ", bThu=" + bThu +
                ", bFri=" + bFri +
                ", bSat=" + bSat +
                ", bSun=" + bSun +
                ", delay=" + delay +
                ", weather=" + weather +
                ", isSelected=" + isSelected +
                ", toggleButton=" + toggleButton +
                ", cbAlarmDeleteCheck=" + cbAlarmDeleteCheck +
                ", ivAlarmEdit=" + ivAlarmEdit +
                '}';
    }

    //    public boolean isCheckOnOff() {
//        return checkOnOff;
//    }
//
//    public void setCheckOnOff(boolean checkOnOff) {
//        this.checkOnOff = checkOnOff;
//    }

}

