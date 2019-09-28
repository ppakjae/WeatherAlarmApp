package com.example.weatheralarmapp.main_fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatheralarmapp.AlarmAddActivity;
import com.example.weatheralarmapp.alarm.AlarmItem;
import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.alarm.ReAlarmAdapter;
import com.example.weatheralarmapp.db_connect.DBConst;
import com.example.weatheralarmapp.db_connect.DBHelper;

import java.util.ArrayList;


public class AlarmFragment extends Fragment {

    View view;
    Context context;

    TextView tvEdit;
    TextView tvAlarm;
    TextView tvPlus;
    TextView tvCancel;

    RecyclerView listAlarm;

    ReAlarmAdapter adapter;

    String noon;
    String hour;
    String minute;

    // 형 텍스트뷰를 2개 만들어서 보였다 안보였다가 하면 나중에 지금 현 상태가 편집모드인지 아니면 기본모드인지 상태파악하기가 엄청 곤란해져요
    // 어차피 여기는 텍스트만 바뀌는 거니까
    // false 일때 일반모드 text는 편집
    // true 일때 수정모드 text는 취소
    // 로작업하는게 좋습니다.
    boolean editStatus = false;
    boolean plusStatus = false;


    DBHelper dbHelper;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        editStatus = false;

        dbHelper = new DBHelper(context.getApplicationContext(), DBConst.ALARM_TABLE_NAME, null, DBConst.DATABASE_VERSION);

        view = inflater.inflate(R.layout.fragment_alarm, container, false);

        tvEdit = view.findViewById(R.id.tvEdit); //편집
        tvAlarm = view.findViewById(R.id.tvAlarm); //알람
        tvPlus = view.findViewById(R.id.tvPlus); //추가
        listAlarm = view.findViewById(R.id.listAlarm);



        dbHelper.AllDelete(dbHelper.getReadableDatabase());
        dbHelper.addContact(0,"AM", 10, 11, 1, 0,0,0, 1, 0, 1, 10, 1);
        dbHelper.addContact(1,"PM", 02, 12, 0, 1,0,0, 0, 1, 0, 5, 1);
        dbHelper.addContact(2,"PM", 03, 13, 1, 1,1,0, 0, 1, 1, 15, 1);
        dbHelper.addContact(3,"AM", 07, 14, 0, 1,0,1, 1, 0, 0, 0, 0);

//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));
//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));
//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));
//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));
//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));
//        adapter.addItem(new AlarmItem("오전", 8, 10, 1, 1,0,1, 1, 1, 0));

        final ArrayList<AlarmItem> alarmItems = dbHelper.readContact();

        adapter = new ReAlarmAdapter(alarmItems);

        listAlarm.setLayoutManager(new LinearLayoutManager(context)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        listAlarm.setAdapter(adapter) ;
//        for (AlarmItem item : alarmItems){
//            adapter.addItem(item);
//        }
//
//        DBHelper db = new DBHelper(getContext());
//        AlarmItem alarm = db.readAlarm(db);
//        adapter.addItem(alarm); //여기서는 db에 있는 내용으로 받아와서 넣기


        //편집 버튼을 눌렀을때 취소 버튼으로 바뀌는 이벤트 리스너
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editStatus){
                    //만약 editStatus가 false 라면 텍스트를 취소로 바꾸고 editStatus를 수정 모드인 true로 바꾼다.
                    tvEdit.setText("삭제");
                    tvEdit.setTextColor(getResources().getColor(R.color.textRed));
                    tvPlus.setText("취소");
                    if(adapter.modiStatus){
                        adapter.modiStatus = false;
                    }else{
                        adapter.modiStatus = true;
                    }

                    adapter.notifyDataSetChanged();
                    editStatus = !editStatus;
                    plusStatus = !plusStatus;
                }else{
                    for(int id = 0 ; id < adapter.getItemCount(); id++){
                        if(adapter.getItem(id).isSelected()){
                       // if(alarmItems.get(id).isSelected()){
                            Log.d("pos", String.valueOf(id));
                            Log.d("cou", String.valueOf(adapter.getItemCount()));
                            alarmItems.remove(id);
                            Log.d("pos2", String.valueOf(id));
                            Log.d("cou2", String.valueOf(adapter.getItemCount()));
                            dbHelper.delete(id, dbHelper.getWritableDatabase());
                        }
                    }

                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    Cursor cursor = dbHelper.readAlarmContact(database);
                 //   Log.d("pos", String.valueOf(id));
                    for (int j = 0; j < adapter.getItemCount(); j++) {
                        cursor.moveToNext();
                        dbHelper.idUpdate(cursor.getInt(0), j, database);
                        Log.d("count", String.valueOf(cursor.getInt(0)));
                    }
                    Log.d("cou", String.valueOf(adapter.getItemCount()));



//                        int index[] = adapter.getItem(i);
/*
                        int id = adapter.pos;
                        boolean checked = adapter.checked;
                        if(checked == true) {
//                        alarmItems.get(id).tbAlarmDeleteCheck.isChecked();;
                            adapter.checked = false;
                            alarmItems.remove(id);

                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                            Cursor cursor = dbHelper.readAlarmContact(database);

                            Log.d("pos", String.valueOf(id));
                            for (int j = 0; j < adapter.getItemCount(); j++) {
                                cursor.moveToNext();
                                dbHelper.idUpdate(cursor.getInt(0), j, database);
                                Log.d("count", String.valueOf(cursor.getInt(0)));
                            }
                            dbHelper.delete(id, checked, dbHelper.getWritableDatabase());
                            Log.d("cou", String.valueOf(adapter.getItemCount()));

                        }

*/
//                    Log.d("db", String.valueOf(dbHelper.getColumn(id+3, dbHelper.getReadableDatabase()).getPosition()));
//                    int i = 0;
//                    int index[] = new int[adapter.getItemCount()];
//                    if(adapter.getItem(adapter.pos).tbAlarmDeleteCheck.isChecked()== true){
//                        index[i] = adapter.pos;
//                        i++;
//                        Log.d("index", index.toString());
//                    }
//
//                    for(int j = 0 ; j<adapter.getItemCount(); j++){
//                        int id = index[j];
//                        alarmItems.remove(id);
//                        SQLiteDatabase database = dbHelper.getWritableDatabase();
//                        Cursor cursor = dbHelper.readAlarmContact(database);
//
//                        Log.d("pos", String.valueOf(id));
//                        for (int k = 0; k < adapter.getItemCount(); k++) {
//                            cursor.moveToNext();
//                            dbHelper.idUpdate(cursor.getInt(0), k, database);
//                            Log.d("count", String.valueOf(cursor.getInt(0)));
//                        }
//                        dbHelper.delete(id, dbHelper.getWritableDatabase());
//                    }


                    adapter.notifyDataSetChanged();
                    dbHelper.close();

                }
//                tvEdit.setVisibility(View.GONE);
//                tvCancel.setVisibility(View.VISIBLE);
//
//                ImageView ivAlarmEdit = (ImageView) view.findViewById(R.id.ivAlarmEdit);
//                ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
//                ToggleButton tbAlarmDeleteCheck = (ToggleButton) view.findViewById(R.id.tbAlarmDeleteCheck);
//
//                tbAlarmDeleteCheck.setVisibility(View.VISIBLE);
//                toggleButton.setVisibility(View.GONE);
//                ivAlarmEdit.setVisibility(View.VISIBLE);

            }
        });


        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!plusStatus){
                    Intent intent = new Intent(getContext(), AlarmAddActivity.class);
                    startActivity(intent);

            }else{
                    //만약 editStatus가 true 라면 텍스트를 편집으로 바꾸고 editStatus를 다시 일반 모드인 false 로 바꾼다.
                    tvEdit.setText("편집");
                    tvEdit.setTextColor(getResources().getColor(R.color.textBlack));
                    tvPlus.setText("추가");
                    if(adapter.modiStatus){
                        adapter.modiStatus = false;
                    }else{
                        adapter.modiStatus = true;
                    }

                    adapter.notifyDataSetChanged();

                    editStatus = !editStatus;
                    plusStatus = !plusStatus;
                    /*
                    ImageView ivAlarmEdit = (ImageView) view.findViewById(R.id.ivAlarmEdit);
                    ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
                    ToggleButton tbAlarmDeleteCheck = (ToggleButton) view.findViewById(R.id.tbAlarmDeleteCheck);
                    ivAlarmEdit.setVisibility(View.GONE);
                    toggleButton.setVisibility(View.VISIBLE);
                    tbAlarmDeleteCheck.setVisibility(View.GONE);
                    */

                }
            }
        });

        return view;
    }




}


