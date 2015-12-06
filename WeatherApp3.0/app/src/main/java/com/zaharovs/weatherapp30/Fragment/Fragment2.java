package com.zaharovs.weatherapp30.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zaharovs.weatherapp30.R;
import com.zaharovs.weatherapp30.Helper.RealmWeather;
import com.zaharovs.weatherapp30.Helper.WD;

import io.realm.Realm;
import io.realm.RealmResults;

public class Fragment2 extends Fragment {
    private Realm realm;

    public static Fragment2 newInstance(int itemPosition) {
        Bundle args = new Bundle();
        args.putInt("itemPosition", itemPosition);
        Fragment2 fragment2 = new Fragment2();
        fragment2.setArguments(args);
        return fragment2;
    }

    public int getPosition() {
        return getArguments().getInt("itemPosition", 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentContentView = inflater.inflate(R.layout.fragm2, container, false);

        realm = Realm.getInstance(getContext());
        RealmResults<RealmWeather> realmResults = realm.where(RealmWeather.class).findAll();
        RealmWeather selectedRealmWeather = realmResults.get(getPosition());

        TextView dayMonth = (TextView) fragmentContentView.findViewById(R.id.item_day_month);
        WD WD = new WD(selectedRealmWeather.getDtTxt());
        StringBuilder sbDayMonth = new StringBuilder();
        sbDayMonth.append(WD.getDayOfMonth()).append(" ")
                .append(WD.getMonth()).append(", ")
                .append(WD.getDayOfWeek());
        dayMonth.setText(sbDayMonth.toString());

        ImageView imageView = (ImageView) fragmentContentView.findViewById(R.id.item_icon);
        StringBuilder sbIcon = new StringBuilder();
        sbIcon.append("http://openweathermap.org/img/w/").append(selectedRealmWeather.getIcon()).append(".png");
        Picasso.with(getActivity()).load(sbIcon.toString()).into(imageView);

        TextView iconDescription = (TextView) fragmentContentView.findViewById(R.id.item_icon_description);
        StringBuilder sbIconDescription = new StringBuilder();
        sbIconDescription.append(WD.getTime()).append(", ").append(selectedRealmWeather.getIconDescription());
        iconDescription.setText(sbIconDescription.toString());

        TextView tempMin = (TextView) fragmentContentView.findViewById(R.id.item_temp_min);
        StringBuilder sbTempMin = new StringBuilder();
        sbTempMin.append("Temp Min: ").append(String.valueOf((int) selectedRealmWeather.getTempMin())).append("°C");
        tempMin.setText(sbTempMin.toString());

        TextView tempMax = (TextView) fragmentContentView.findViewById(R.id.item_temp_max);
        StringBuilder sbTempMax = new StringBuilder();
        sbTempMax.append("Temp Max: ").append(String.valueOf((int) selectedRealmWeather.getTempMax())).append("°C");
        tempMax.setText(sbTempMax.toString());

        TextView windSpeed = (TextView) fragmentContentView.findViewById(R.id.item_wind_speed);
        StringBuilder sbWindSpeed = new StringBuilder();
        sbWindSpeed.append("Wind Speed: ").append((int) selectedRealmWeather.getWindSpeed()).append(" meter/sec");
        windSpeed.setText(sbWindSpeed.toString());

        TextView cloudsAll = (TextView) fragmentContentView.findViewById(R.id.item_clouds_all);
        StringBuilder sbClouds = new StringBuilder();
        sbClouds.append("Cloudiness: ").append((int) selectedRealmWeather.getClouds()).append("%");
        cloudsAll.setText(sbClouds.toString());

        TextView humidity = (TextView) fragmentContentView.findViewById(R.id.item_humidity);
        StringBuilder sbHumidity = new StringBuilder();
        sbHumidity.append("Humidity: ").append((int) selectedRealmWeather.getHumidity()).append("%");
        humidity.setText(sbHumidity.toString());

        TextView pressure = (TextView) fragmentContentView.findViewById(R.id.item_pressure);
        StringBuilder sbPressure = new StringBuilder();
        sbPressure.append("Pressure: ").append((int) selectedRealmWeather.getPressure()).append(" hPa");
        pressure.setText(sbPressure.toString());

        return fragmentContentView;
    }
}