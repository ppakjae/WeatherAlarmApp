package com.example.weatheralarmapp.alarm;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;


import com.example.weatheralarmapp.R;

import java.util.ArrayList;

public class ReAlarmAdapter extends RecyclerView.Adapter<ReAlarmAdapter.ViewHolder> {

    ArrayList<AlarmItem> alarms = new ArrayList<AlarmItem>();
    Context context;

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
        ToggleButton tbAlarmDeleteCheck;
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
            tbAlarmDeleteCheck = itemView.findViewById(R.id.tbAlarmDeleteCheck);
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


        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {

        AlarmItem alarmItem = alarms.get(vh.getAdapterPosition());
        // 데이터 세팅
        vh.tvNoon.setText(alarmItem.getNoon());
        vh.tvHour.setText(alarmItem.getHour()+"");
        vh.tvMinute.setText(alarmItem.getMinute()+"");

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
        temp.setTbAlarmDeleteCheck(item.tbAlarmDeleteCheck);
        temp.setToggleButton(item.toggleButton);

        alarms.add(temp);
    }
}