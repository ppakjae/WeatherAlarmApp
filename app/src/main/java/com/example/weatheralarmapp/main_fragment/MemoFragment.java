package com.example.weatheralarmapp.main_fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.db_connect.DBConst;
import com.example.weatheralarmapp.db_connect.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MemoFragment extends Fragment {

    Context context;
    TextView tvDay_1;
    TextView tvDay_2;
    TextView tvDay_3;
    TextView tvDay_4;
    TextView tvDay_5;
    TextView tvDay_6;
    TextView tvDay_7;
    TextView memoTitle;

    EditText edtDay_1;
    EditText edtDay_2;
    EditText edtDay_3;
    EditText edtDay_4;
    EditText edtDay_5;
    EditText edtDay_6;
    EditText edtDay_7;

    TextView btnSave;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_memo, container, false);
        context = container.getContext();

        tvDay_1 = (TextView) view.findViewById(R.id.tvDay_1);
        tvDay_2 = (TextView) view.findViewById(R.id.tvDay_2);
        tvDay_3 = (TextView) view.findViewById(R.id.tvDay_3);
        tvDay_4 = (TextView) view.findViewById(R.id.tvDay_4);
        tvDay_5 = (TextView) view.findViewById(R.id.tvDay_5);
        tvDay_6 = (TextView) view.findViewById(R.id.tvDay_6);
        tvDay_7 = (TextView) view.findViewById(R.id.tvDay_7);
        memoTitle = (TextView) view.findViewById(R.id.memoTitle);

        edtDay_1 = (EditText) view.findViewById(R.id.edtDay_1);
        edtDay_2 = (EditText) view.findViewById(R.id.edtDay_2);
        edtDay_3 = (EditText) view.findViewById(R.id.edtDay_3);
        edtDay_4 = (EditText) view.findViewById(R.id.edtDay_4);
        edtDay_5 = (EditText) view.findViewById(R.id.edtDay_5);
        edtDay_6 = (EditText) view.findViewById(R.id.edtDay_6);
        edtDay_7 = (EditText) view.findViewById(R.id.edtDay_7);

        btnSave = (TextView) view.findViewById(R.id.btnSave);

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MM.dd", Locale.getDefault());

        String weekDay = weekdayFormat.format(currentTime);
        String monthDay = monthDayFormat.format(currentTime);
        Log.d("weekDay", weekDay);
        System.out.println("weekDay : "+weekDay);
        DayForm(weekDay, monthDay);

        autoDelete();
        ReadMemo();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMemo();
            }
        });

        return view;
    }

    public void AddMemo() {
        DBHelper dbHelper = new DBHelper(context.getApplicationContext(), DBConst.MEMO_TABLE_NAME, null, DBConst.DATABASE_VERSION);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.AllMemoDelete(database);

        for (int i=1; i<8 ; i++) {
            String id = String.valueOf(i);
            switch (i) {
                case 1:
                    dbHelper.addMemoContact(id, tvDay_1.getText().toString() ,edtDay_1.getText().toString(), database);
                    break;

                case 2:
                    dbHelper.addMemoContact(id, tvDay_2.getText().toString(), edtDay_2.getText().toString(), database);
                    break;

                case 3:
                    dbHelper.addMemoContact(id, tvDay_3.getText().toString(), edtDay_3.getText().toString(), database);
                    break;

                case 4:
                    dbHelper.addMemoContact(id, tvDay_4.getText().toString(), edtDay_4.getText().toString(), database);
                    break;

                case 5:
                    dbHelper.addMemoContact(id, tvDay_5.getText().toString(), edtDay_5.getText().toString(), database);
                    break;

                case 6:
                    dbHelper.addMemoContact(id, tvDay_6.getText().toString(), edtDay_6.getText().toString(), database);
                    break;

                case 7:
                    dbHelper.addMemoContact(id, tvDay_7.getText().toString(), edtDay_7.getText().toString(), database);
                    break;
            }
        }

        Toast.makeText(getContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
        dbHelper.close();
    }

    public void ReadMemo() {
        DBHelper dbHelper = new DBHelper(context.getApplicationContext(), DBConst.MEMO_TABLE_NAME, null, DBConst.DATABASE_VERSION);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readMemoContact(database);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String Memo_Contents = cursor.getString(2);

            if (i==0) {
                Log.d("day", cursor.getString(1));
                Log.d("tvDay", tvDay_1.getText().toString());
                Log.d("MemoContents", cursor.getString(2));
            }

            if (Memo_Contents != null) {
                String day = cursor.getString(1);

                if (day.equals(tvDay_1.getText().toString()))
                    edtDay_1.setText(Memo_Contents);

                else if (day.equals(tvDay_2.getText().toString()))
                    edtDay_2.setText(Memo_Contents);

                else if (day.equals(tvDay_3.getText().toString()))
                    edtDay_3.setText(Memo_Contents);

                else if (day.equals(tvDay_4.getText().toString()))
                    edtDay_4.setText(Memo_Contents);

                else if (day.equals(tvDay_5.getText().toString()))
                    edtDay_5.setText(Memo_Contents);

                else if (day.equals(tvDay_6.getText().toString()))
                    edtDay_6.setText(Memo_Contents);

                else if (day.equals(tvDay_7.getText().toString()))
                    edtDay_7.setText(Memo_Contents);

            }
        }

        cursor.close();
        dbHelper.close();
    }

    public void autoDelete() {
        int nDay;
        int ntvDay;
        int subDay;

        DBHelper dbHelper = new DBHelper(context.getApplicationContext(), DBConst.MEMO_TABLE_NAME, null, DBConst.DATABASE_VERSION);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readMemoContact(database);

        for (int i = 0; i < 1; i++) {
            try {
                cursor.moveToNext();
                String day = cursor.getString(1);
                nDay = nDayToCount(day);
                ntvDay = nDayToCount(tvDay_1.getText().toString());

                if (nDay > ntvDay) {
                    subDay = nDay - ntvDay;

                    for (int j=1; j<=subDay ; j++) {
                        dbHelper.autoMemoDelete(j, database);
                    }
                }

                else if (nDay < ntvDay) {
                    subDay = ntvDay - nDay;

                    for (int j=1 ; j<=subDay ; j++) {
                        dbHelper.autoMemoDelete(j, database);
                    }
                }
            }
            catch (Exception e) {}
        }
    }

    public void DayForm(String weekDay, String monthDay) {
        switch (weekDay) {
            case "Mon":
                tvDay_1.setText("월요일");
                tvDay_2.setText("화요일");
                tvDay_3.setText("수요일");
                tvDay_4.setText("목요일");
                tvDay_5.setText("금요일");
                tvDay_6.setText("토요일");
                tvDay_7.setText("일요일");
                memoTitle.setText(monthDay+".월요일");
                break;

            case "Tue":
                tvDay_1.setText("화요일");
                tvDay_2.setText("수요일");
                tvDay_3.setText("목요일");
                tvDay_4.setText("금요일");
                tvDay_5.setText("토요일");
                tvDay_6.setText("일요일");
                tvDay_7.setText("월요일");
                memoTitle.setText(monthDay+".화요일");
                break;

            case "Wed":
                tvDay_1.setText("수요일");
                tvDay_2.setText("목요일");
                tvDay_3.setText("금요일");
                tvDay_4.setText("토요일");
                tvDay_5.setText("일요일");
                tvDay_6.setText("월요일");
                tvDay_7.setText("화요일");
                memoTitle.setText(monthDay+".수요일");
                break;

            case "Thu":
                tvDay_1.setText("목요일");
                tvDay_2.setText("금요일");
                tvDay_3.setText("토요일");
                tvDay_4.setText("일요일");
                tvDay_5.setText("월요일");
                tvDay_6.setText("화요일");
                tvDay_7.setText("수요일");
                memoTitle.setText(monthDay+".목요일");
                break;

            case "Fri":
                tvDay_1.setText("금요일");
                tvDay_2.setText("토요일");
                tvDay_3.setText("일요일");
                tvDay_4.setText("월요일");
                tvDay_5.setText("화요일");
                tvDay_6.setText("수요일");
                tvDay_7.setText("목요일");
                memoTitle.setText(monthDay+".금요일");
                break;

            case "Sat":
                tvDay_1.setText("토요일");
                tvDay_2.setText("일요일");
                tvDay_3.setText("월요일");
                tvDay_4.setText("화요일");
                tvDay_5.setText("수요일");
                tvDay_6.setText("목요일");
                tvDay_7.setText("금요일");
                memoTitle.setText(monthDay+".토요일");
                break;

            case "Sun":
                tvDay_1.setText("일요일");
                tvDay_2.setText("월요일");
                tvDay_3.setText("화요일");
                tvDay_4.setText("수요일");
                tvDay_5.setText("목요일");
                tvDay_6.setText("금요일");
                tvDay_7.setText("토요일");
                memoTitle.setText(monthDay+".일요일");
                break;
        }
    }

    public int nDayToCount(String date) {
        int nCount = 0;

        if (date.equals("월요일"))
            nCount = 1;

        else if (date.equals("화요일"))
            nCount = 2;

        else if (date.equals("수요일"))
            nCount = 3;

        else if (date.equals("목요일"))
            nCount = 4;

        else if (date.equals("금요일"))
            nCount = 5;

        else if (date.equals("토요일"))
            nCount = 6;

        else if (date.equals("일요일"))
            nCount = 7;

        return nCount;
    }
}
