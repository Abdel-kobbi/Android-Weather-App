package com.kobbi.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DailyWeatherAdapter extends ArrayAdapter<Weather> {

    private final Context context;
    private final List<Weather> weatherList;

    public DailyWeatherAdapter(@NonNull Context context, @NonNull List<Weather> weatherList) {
        super(context, 0, weatherList);
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);

        TextView date = convertView.findViewById(R.id.date);
        TextView temp = convertView.findViewById(R.id.temp);

        Weather weather = weatherList.get(position);
        temp.setText(weather.getTemp() + " °C");
        date.setText(weather.getDate());

        return convertView;
    }

}

