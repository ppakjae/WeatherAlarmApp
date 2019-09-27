package com.example.weatheralarmapp.main_fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatheralarmapp.MainActivity;
import com.example.weatheralarmapp.R;
import com.example.weatheralarmapp.weather.DailyAdapter;
import com.example.weatheralarmapp.weather.GpsTracker;
import com.example.weatheralarmapp.weather.WeatherAsynTask;
import com.example.weatheralarmapp.weather.WeatherDayilyHourlyItem;
import com.example.weatheralarmapp.weather.WeatherWeeklyItem;
import com.example.weatheralarmapp.weather.WeeklyAdapter;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {

    MainActivity mainActivity;
    GpsTracker gpsTracker;
    ContentValues values;

    TextView txtMainTemper, txtHighTemper, txtMinTemper, txtWeather,txtArea;
    ImageView btnRefresh, imgWeahter;
    RecyclerView horizontalDaily, horizontalWeekly;
    DailyAdapter dailyAdapter;
    WeeklyAdapter weeklyAdapter;
    LinearLayoutManager dManager, wManager;
    View view;

    public WeatherFragment(MainActivity mainActivity, ContentValues values){
        this.mainActivity = mainActivity;
        this.values = values;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        InitView();

        return view;
    }

    private void InitView() {
        txtMainTemper = (TextView)view.findViewById(R.id.txtMainTemper);
        txtMinTemper = (TextView)view.findViewById(R.id.txtMinTemper);
        txtHighTemper = (TextView)view.findViewById(R.id.txtHighTemper);
        txtWeather = (TextView)view.findViewById(R.id.txtWeather);
        txtArea = (TextView)view.findViewById(R.id.txtArea);
        imgWeahter = (ImageView)view.findViewById(R.id.imgWeather);


        btnRefresh = (ImageView)view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(imgViewHandler);

        horizontalDaily = (RecyclerView)view.findViewById(R.id.horizontalViewDaily);
        horizontalWeekly = (RecyclerView)view.findViewById(R.id.horizontalViewWeekly);

        dManager = new LinearLayoutManager(getContext());
        horizontalDaily.addItemDecoration(new DividerItemDecoration(getContext(), dManager.getOrientation()));
        horizontalDaily.setLayoutManager(dManager);
        ArrayList<WeatherDayilyHourlyItem> dailyItems = new ArrayList<>();
        //ArrayList에 담을 API 가공 처리 코드 넣기
        dailyAdapter = new DailyAdapter(getActivity(), dailyItems);

        wManager = new LinearLayoutManager(getContext());
        horizontalWeekly.addItemDecoration(new DividerItemDecoration(getContext(), wManager.getOrientation()));
        horizontalWeekly.setLayoutManager(wManager);
        ArrayList<WeatherWeeklyItem> weeklyItems = new ArrayList<WeatherWeeklyItem>();
        //ArrayList에 담을 API 가공 처리 코드 넣기
        weeklyAdapter = new WeeklyAdapter(getActivity(), weeklyItems);


    }

    ImageView.OnClickListener imgViewHandler = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnRefresh:
                    //API 정보 갱신하는 부분.
                    WeatherAsynTask weatherAsynTask = new WeatherAsynTask(null, values, WeatherFragment.this);
                    weatherAsynTask.execute();
                    break;
//                case R.id.btnPlusCity:
//                    CustomDialogWeather dialog = CustomDialogWeather.getInstance();
//                    dialog.show(getFragmentManager(), CustomDialogWeather.TAG_EVENT_DIALOG);
//                    break;
            }
        }
    };
}
