package com.zaharovs.weatherapp30.Helper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zaharovs.weatherapp30.R;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class MyAdapter extends RealmBaseAdapter<RealmWeather> implements ListAdapter {
    private Context context;

    public MyAdapter(Context context, RealmResults<RealmWeather> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragm_items, null);

            viewHolder = new ViewHolder();

            viewHolder.dayOfWeek = (TextView) convertView.findViewById(R.id.list_day_of_week);
            viewHolder.dayOfMonth = (TextView) convertView.findViewById(R.id.list_day_of_month);
            viewHolder.month = (TextView) convertView.findViewById(R.id.list_month);

            viewHolder.weatherIcon = (ImageView) convertView.findViewById(R.id.list_icon);

            viewHolder.temp = (TextView) convertView.findViewById(R.id.list_temp);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RealmWeather realmWeather = realmResults.get(position);

        if (realmWeather != null) {
            WD WD = new WD(realmWeather.getDtTxt());
            viewHolder.dayOfWeek.setText(WD.getDayOfWeek());
            viewHolder.dayOfMonth.setText(WD.getDayOfMonth());
            viewHolder.month.setText(WD.getMonth());

            double temp = realmWeather.getTemp();
            viewHolder.temp.setText(String.valueOf((int) temp) + "°C");

            String iconUrl = "http://openweathermap.org/img/w/" + realmWeather.getIcon() + ".png";
            Picasso.with(context).load(iconUrl).into(viewHolder.weatherIcon);
        }

        return convertView;
    }
}