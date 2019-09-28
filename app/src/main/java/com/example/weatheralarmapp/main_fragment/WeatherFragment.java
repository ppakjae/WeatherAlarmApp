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
import com.example.weatheralarmapp.weather.WeatherAsynTask;
import com.example.weatheralarmapp.weather.WeeklyAdapter;


public class WeatherFragment extends Fragment {

    public MainActivity mainActivity;
    ContentValues values;

    TextView txtMainTemper, txtHighTemper, txtMinTemper, txtWeather,txtArea;
    ImageView btnRefresh, imgWeahter;
    RecyclerView horizontalDaily, horizontalWeekly;
    public DailyAdapter dailyAdapter;
    public WeeklyAdapter weeklyAdapter;
    LinearLayoutManager dManager, wManager;
    View view;

    public WeatherAsynTask weatherAsynTask;     //refresh 버튼 눌렸을 때 쓰는 AsyncTask
    WeatherAsynTask task;                       //화면이  처음 등장 할 때 쓰이는 AsyncTask

    public WeatherFragment(MainActivity mainActivity, ContentValues values){
        this.mainActivity = mainActivity;
        this.values = values;

        weatherAsynTask = new WeatherAsynTask(null, values, WeatherFragment.this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        InitView();
        task = new WeatherAsynTask(null, values, WeatherFragment.this);
        task.execute();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
//        ArrayList<WeatherDayilyHourlyItem> dailyItems = new ArrayList<>();
        //ArrayList에 담을 API 가공 처리 코드 넣기
        dailyAdapter = new DailyAdapter(mainActivity, null);

        wManager = new LinearLayoutManager(getContext());
        horizontalWeekly.addItemDecoration(new DividerItemDecoration(getContext(), wManager.getOrientation()));
        horizontalWeekly.setLayoutManager(wManager);
//        ArrayList<WeatherWeeklyItem> weeklyItems = new ArrayList<WeatherWeeklyItem>();
        //ArrayList에 담을 API 가공 처리 코드 넣기
        weeklyAdapter = new WeeklyAdapter(mainActivity, null);


    }

    ImageView.OnClickListener imgViewHandler = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnRefresh:
                    //API 정보 갱신하는 부분.
                    Toast.makeText(mainActivity.getApplicationContext(), "refesh pressed", Toast.LENGTH_SHORT).show();
                    WeatherAsynTask asynTask = new WeatherAsynTask(null, values, WeatherFragment.this);
                    asynTask.execute();
                    break;
//                case R.id.btnPlusCity:
//                    CustomDialogWeather dialog = CustomDialogWeather.getInstance();
//                    dialog.show(getFragmentManager(), CustomDialogWeather.TAG_EVENT_DIALOG);
//                    break;
            }
        }
    };

    public void showItems(){

    }
}
