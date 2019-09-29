package com.example.weatheralarmapp.alarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;


import com.example.weatheralarmapp.AlarmAddActivity;
import com.example.weatheralarmapp.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReAlarmAdapter extends RecyclerView.Adapter<ReAlarmAdapter.ViewHolder> {

    ArrayList<AlarmItem> alarms = new ArrayList<AlarmItem>();
    public boolean modiStatus = false;
    Context context;
    public int pos;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoon;
        TextView tvHour;
        TextView tvMinute;
        ImageView ivMon;
        ImageView ivTue;
        ImageView ivWed;
        ImageView ivThu;
        ImageView ivFri;
        ImageView ivSat;
        ImageView ivSun;
        ToggleButton toggleButton;
        CheckBox cbAlarmDeleteCheck;
        ImageView ivAlarmEdit;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            tvNoon = itemView.findViewById(R.id.tvNoon);
            tvHour = itemView.findViewById(R.id.tvHour);
            tvMinute = itemView.findViewById(R.id.tvMinute);
            ivMon = itemView.findViewById(R.id.ivMon);
            ivTue = itemView.findViewById(R.id.ivTue);
            ivWed = itemView.findViewById(R.id.ivWed);
            ivThu = itemView.findViewById(R.id.ivThu);
            ivFri = itemView.findViewById(R.id.ivFri);
            ivSat = itemView.findViewById(R.id.ivSat);
            ivSun = itemView.findViewById(R.id.ivSun);
            toggleButton = itemView.findViewById(R.id.toggleButton);
            cbAlarmDeleteCheck = itemView.findViewById(R.id.cbAlarmDeleteCheck);
            ivAlarmEdit = itemView.findViewById(R.id.ivAlarmEdit);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ReAlarmAdapter(ArrayList<AlarmItem> list) {
        alarms = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        View view = inflater.inflate(R.layout.listview_item_alarm, parent, false) ;
        ReAlarmAdapter.ViewHolder vh = new ReAlarmAdapter.ViewHolder(view) ;

//        ToggleButton toggleButton = view.findViewById(R.id.toggleButton) ;
//        ToggleButton tbAlarmDeleteCheck = view.findViewById(R.id.tbAlarmDeleteCheck);
//        ImageView ivAlarmEdit = view.findViewById(R.id.ivAlarmEdit);
//
//        TextView tvNoon = view.findViewById(R.id.tvNoon);
//        TextView tvHour = view.findViewById(R.id.tvHour);
//        TextView tvMinute = view.findViewById(R.id.tvMinute);
//        ImageView ivMon = view.findViewById(R.id.ivMon);
//        ImageView ivTue = view.findViewById(R.id.ivTue);
//        ImageView ivWed = view.findViewById(R.id.ivWed);
//        ImageView ivThu = view.findViewById(R.id.ivThu);
//        ImageView ivFri = view.findViewById(R.id.ivFri);
//        ImageView ivSat = view.findViewById(R.id.ivSat);
//        ImageView ivSun = view.findViewById(R.id.ivSun);

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {

 //       AlarmItem alarmItem = alarms.get(vh.getAdapterPosition());
        if(!modiStatus) {
            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final AlarmItem alarmItem = alarms.get(position);
            pos = position;

            // 아이템 내 각 위젯에 데이터 반영
            // 데이터 세팅
            vh.tvNoon.setText(alarmItem.getNoon());
            vh.tvHour.setText(alarmItem.getHour()+"");
            vh.tvMinute.setText(alarmItem.getMinute()+"");
            vh.ivAlarmEdit.setVisibility(View.GONE);
            vh.toggleButton.setVisibility(View.VISIBLE);
            vh.cbAlarmDeleteCheck.setVisibility(View.GONE);
            vh.toggleButton.setChecked(alarmItem.isTbSelected());
            vh.toggleButton.setTag(alarmItem);
            if(alarmItem.getbMon() != 0) {
                vh.ivMon.setImageResource(R.drawable.mon);
            }
            if(alarmItem.getbTue() != 0) {
                vh.ivTue.setImageResource(R.drawable.tue);
            }
            if(alarmItem.getbWed() != 0) {
                vh.ivWed.setImageResource(R.drawable.wed);
            }
            if(alarmItem.getbThu() != 0) {
                vh.ivThu.setImageResource(R.drawable.thu);
            }
            if(alarmItem.getbFri() != 0) {
                vh.ivFri.setImageResource(R.drawable.fri);
            }
            if(alarmItem.getbSat() != 0) {
                vh.ivSat.setImageResource(R.drawable.sat);
            }
            if(alarmItem.getbSun() != 0) {
                vh.ivSun.setImageResource(R.drawable.sun);
            }
            vh.toggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToggleButton tb = (ToggleButton) v;
                    AlarmItem contact = (AlarmItem) tb.getTag();
                    contact.setTbSelected(tb.isChecked());
                    alarms.get(pos).setSelected(tb.isChecked());

                    Toast.makeText(
                            v.getContext(),
                            "Clicked on toggleButton: " + tb.getText() + " is "
                                    + tb.isChecked(), Toast.LENGTH_LONG).show();
                    ((AlarmAddActivity)AlarmAddActivity.context).offAlarm(pos);



/*
                    // 현재 지정된 시간으로 알람 시간 설정
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
//                Log.d("currentTimeMillis",System.currentTimeMillis()+"");
                    calendar.set(Calendar.HOUR_OF_DAY, alarmItem.getHour());
                    calendar.set(Calendar.MINUTE, alarmItem.getMinute());
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

                    //현재는 sharedpreference에 저장하고 있음 디비에 저장해야
                    //  Preference에 설정한 값 저장
//                    SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
//                    editor.putLong("nextNotifyTime", (long)calendar.getTimeInMillis());
//                    editor.apply();

//                    boolean [] repeat = {repeatMon,repeatTue, repeatWed, repeatThur, repeatFri, repeatSat, repeatSun};
//
//                    for(int i = 0; i < repeat.length; i++){
//                        if(repeat[i] == false){
//                            repeatInt[i] = 0;
//                        } else{
//                            repeatInt[i] = 1;
//                        }
//                    }

//                dbHelper.addContact("오전", 8, 10, 1, 1,0,1, 1, 1, 0, 0, 0);
//                    setDbHelper(am_pm, hour, minute, repeatInt, early);

                    add.diaryNotification(calendar);

                    //알람 추가하고 액티비티 사라짐 -> alarm fragment에서 업데이트 할것.
//                    onBackPressed();

*/
                }
            });

        }else{
            AlarmItem alarmItem = alarms.get(position);
            pos = position;
            // 아이템 내 각 위젯에 데이터 반영
            // 데이터 세팅
            vh.tvNoon.setText(alarmItem.getNoon());
            vh.tvHour.setText(alarmItem.getHour()+"");
            vh.tvMinute.setText(alarmItem.getMinute()+"");
            vh.ivAlarmEdit.setVisibility(View.VISIBLE);
            vh.toggleButton.setVisibility(View.GONE);
            vh.cbAlarmDeleteCheck.setVisibility(View.VISIBLE);
            vh.cbAlarmDeleteCheck.setChecked(alarmItem.isSelected());
            vh.cbAlarmDeleteCheck.setTag(alarmItem);
            if(alarmItem.getbMon() != 0) {
                vh.ivMon.setImageResource(R.drawable.mon);
            }
            if(alarmItem.getbTue() != 0) {
                vh.ivTue.setImageResource(R.drawable.tue);
            }
            if(alarmItem.getbWed() != 0) {
                vh.ivWed.setImageResource(R.drawable.wed);
            }
            if(alarmItem.getbThu() != 0) {
                vh.ivThu.setImageResource(R.drawable.thu);
            }
            if(alarmItem.getbFri() != 0) {
                vh.ivFri.setImageResource(R.drawable.fri);
            }
            if(alarmItem.getbSat() != 0) {
                vh.ivSat.setImageResource(R.drawable.sat);
            }
            if(alarmItem.getbSun() != 0) {
                vh.ivSun.setImageResource(R.drawable.sun);
            }

            vh.ivAlarmEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AlarmAddActivity.class);
                    context.startActivity(intent);

                }
            });

            vh.cbAlarmDeleteCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Log.d("id", String.valueOf(pos));
                    if (vh.cbAlarmDeleteCheck.isChecked() == true)
                        checked = true;
                    else {
                        vh.cbAlarmDeleteCheck.setChecked(false);
                        checked = false;
                    }
                    */
                    CheckBox cb = (CheckBox) v;
                    AlarmItem contact = (AlarmItem) cb.getTag();

                    contact.setSelected(cb.isChecked());
                    alarms.get(pos).setSelected(cb.isChecked());

                    Toast.makeText(
                            v.getContext(),
                            "Clicked on Checkbox: " + cb.getText() + " is "
                                    + cb.isChecked(), Toast.LENGTH_LONG).show();
                }

            });


        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return alarms.size() ;
    }

    public void addItem(AlarmItem item){

        AlarmItem temp = new AlarmItem();


        if(item.getbMon() != 0){temp.setMon(R.drawable.mon);}
        if(item.getbTue() != 0){temp.setTue(R.drawable.tue);}
        if(item.getbWed() != 0){temp.setWed(R.drawable.wed);}
        if(item.getbThu() != 0){temp.setbThu(R.drawable.thu);}
        if(item.getbFri() != 0){temp.setFri(R.drawable.fri);}
        if(item.getbSat() != 0){temp.setSat(R.drawable.sat);}
        if(item.getbSun() != 0){temp.setSun(R.drawable.sun);}

        temp.setHour(item.hour);
        temp.setMinute(item.minute);
        temp.setNoon(item.noon);
        temp.setIvAlarmEdit(item.ivAlarmEdit);
        temp.setCbAlarmDeleteCheck(item.cbAlarmDeleteCheck);
        temp.setToggleButton(item.toggleButton);

        alarms.add(temp);
    }

    public AlarmItem getItem(int position){
        return alarms.get(position);
    }

    public int[] getItemPos(int position){
        int index[] = new int[alarms.size()];
        for(int i =0 ; i<alarms.size() ; i++){
            index[i] = position;
        }
        return index;
    }



}