package com.example.weatheralarmapp.weather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatheralarmapp.R;

import java.util.ArrayList;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<WeatherWeeklyItem> weeklyItems;

    public WeeklyAdapter(Activity activity, ArrayList<WeatherWeeklyItem> weeklyItems){
        this.activity = activity;
        this.weeklyItems = weeklyItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtWeeklyItemDay;
        ImageView imgWeeklyItemIcon;
        TextView txtWeeklyHighTemper;
        TextView txtWeeklyLowTemper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWeeklyItemDay = (TextView)itemView.findViewById(R.id.txtWeeklyItemDay);
            imgWeeklyItemIcon = (ImageView)itemView.findViewById(R.id.imgWeeklyItemIcon);
            txtWeeklyHighTemper = (TextView)itemView.findViewById(R.id.txtWeeklyHighTemper);
            txtWeeklyLowTemper = (TextView)itemView.findViewById(R.id.txtWeeklyLowTemper);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "click " +
                            weeklyItems.get(getAdapterPosition()).getDay(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_weather_weekly, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyAdapter.ViewHolder holder, int position) {
        WeatherWeeklyItem data = weeklyItems.get(position);

        holder.txtWeeklyItemDay.setText(data.getDay());
        String skyState = data.getSkyName();
        switch (skyState)
        {
            case "맑음":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon01);
                break;
            case "구름조금":
            case "구름많음":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon02);
                break;
            case "구름많고 비":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon13);
                break;
            case "구름많고 눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon14);
                break;
            case "구름많고 비 또는 눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon15);
                break;
            case "흐림":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon03);
                break;
            case "흐리고 눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon08);
                break;
            case "흐리고 비 또는 눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon06);
                break;
            case "흐리고 낙뢰":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon10);
                break;
            case "뇌우/비":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon10);
                break;
            case "뇌우/눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon22);
                break;
            case "뇌우/비 또는 눈":
                holder.imgWeeklyItemIcon.setImageResource(R.drawable.icon22);
                break;
            default:
        }
        holder.txtWeeklyHighTemper.setText(data.getTmax());
        holder.txtWeeklyLowTemper.setText(data.getTmin());

    }

    @Override
    public int getItemCount() {
        return weeklyItems.size();
    }

    private void removeItemView(int position) {
        weeklyItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, weeklyItems.size()); // 지워진 만큼 다시 채워넣기.
    }

    public void setItems(ArrayList<WeatherWeeklyItem> items){
        this.weeklyItems = items;
    }

    public void removeItems(){
        weeklyItems.clear();
    }

    public void updateData(ArrayList<WeatherWeeklyItem> items){
        weeklyItems.clear();
        weeklyItems.addAll(items);
        notifyDataSetChanged();
    }

}
