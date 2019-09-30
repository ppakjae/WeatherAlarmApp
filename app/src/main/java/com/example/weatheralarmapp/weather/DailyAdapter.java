package com.example.weatheralarmapp.weather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatheralarmapp.R;

import java.util.ArrayList;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<WeatherDayilyHourlyItem> dailyItems;

    public DailyAdapter(Activity activity, ArrayList<WeatherDayilyHourlyItem> dailyItems){
        this.activity = activity;
        this.dailyItems = dailyItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDailyItemTime;
        TextView txtDailyItemRain;
        ImageView imgDailyItemIcon;
        TextView txtDailyItemTemper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDailyItemTime = (TextView)itemView.findViewById(R.id.txtDailyItemTime);
            txtDailyItemRain = (TextView)itemView.findViewById(R.id.txtDailyItemRain);
            imgDailyItemIcon = (ImageView)itemView.findViewById(R.id.imgDailyItemIcon);
            txtDailyItemTemper = (TextView)itemView.findViewById(R.id.txtDailyItemTemper);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(activity, "click " +
//                            dailyItems.get(getAdapterPosition()).getTime(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_weather_daily, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    // 재활용 되는 View가 호출, Adapter가 해당 position에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherDayilyHourlyItem data = dailyItems.get(position);

        holder.txtDailyItemTime.setText(data.getTime());
        holder.txtDailyItemRain.setText("");                //API 강수확률이 없음.
        holder.txtDailyItemTemper.setText(data.getTemperature());
//        holder.imgDailyItemIcon.setImageResource(); 넣어주기 전에 위에서 어떤 하늘인지 골라내고 넣기.
        String skyState = data.getSky();
        switch (skyState)
        {
            case "맑음":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon01);
                break;
            case "구름조금":
            case "구름많음":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon02);
                break;
            case "구름많고 비":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon13);
                break;
            case "구름많고 눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon14);
                break;
            case "구름많고 비 또는 눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon15);
                break;
            case "흐림":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon03);
                break;
            case "흐리고 눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon08);
                break;
            case "흐리고 비 또는 눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon06);
                break;
            case "흐리고 낙뢰":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon10);
                break;
            case "뇌우/비":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon10);
                break;
            case "뇌우/눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon22);
                break;
            case "뇌우/비 또는 눈":
                holder.imgDailyItemIcon.setImageResource(R.drawable.icon22);
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return dailyItems.size();
    }

    private void removeItemView(int position) {
        dailyItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dailyItems.size()); // 지워진 만큼 다시 채워넣기.
    }

    public void updateData(ArrayList<WeatherDayilyHourlyItem> items){
        dailyItems.clear();
        dailyItems.addAll(items);
        notifyDataSetChanged();
    }
}
