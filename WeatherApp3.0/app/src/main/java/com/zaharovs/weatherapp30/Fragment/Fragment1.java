package com.zaharovs.weatherapp30.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.zaharovs.weatherapp30.ClickInterface;
import com.zaharovs.weatherapp30.Helper.MyAdapter;
import com.zaharovs.weatherapp30.Helper.RealmWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmResults;

public class Fragment1 extends ListFragment {
    private Realm realm;
    private ClickInterface clickInterface;
    private MyAdapter adapter;
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=Cherkasy&units=metric&appid=8a3ea4b3ab2bc27e4a85f3f1020301a6";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        clickInterface = (ClickInterface) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        realm = Realm.getInstance(getContext());
        RealmResults<RealmWeather> realmResults = realm.where(RealmWeather.class).findAll();

        new GetWeatherDate().execute();

        adapter = new MyAdapter(getActivity(), realmResults, true);
        setListAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        clickInterface.clickItem(position);
    }

    public class GetWeatherDate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Realm realm = Realm.getInstance(getContext());
            HttpURLConnection connection = null;
            InputStream inputStream = null;

            try {
                URL baseUrl = new URL(BASE_URL);
                connection = (HttpURLConnection) baseUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();
                String getDataString;

                while ((getDataString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(getDataString);
                }

                JSONObject rootJsonObject = new JSONObject(stringBuilder.toString());
                JSONArray rootJsonArray = rootJsonObject.getJSONArray("list");

                for (int i = 0; i < rootJsonArray.length(); i++) {
                    JSONObject weatherJsonObject = rootJsonArray.getJSONObject(i);
                    RealmWeather realmWeather = new RealmWeather();

                    String dtTxt = weatherJsonObject.getString("dt_txt");
                    realmWeather.setDtTxt(dtTxt);

                    double temp = weatherJsonObject.getJSONObject("main").getDouble("temp");
                    realmWeather.setTemp(temp);

                    double tempMin = weatherJsonObject.getJSONObject("main").getDouble("temp_min");
                    realmWeather.setTempMin(tempMin);

                    double tempMax = weatherJsonObject.getJSONObject("main").getDouble("temp_max");
                    realmWeather.setTempMax(tempMax);

                    String weatherIcon = weatherJsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                    realmWeather.setIcon(weatherIcon);

                    String iconDescription = weatherJsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                    realmWeather.setIconDescription(iconDescription);

                    double windSpeed = weatherJsonObject.getJSONObject("wind").getDouble("speed");
                    realmWeather.setWindSpeed(windSpeed);

                    double clouds = weatherJsonObject.getJSONObject("clouds").getDouble("all");
                    realmWeather.setClouds(clouds);

                    double humidity = weatherJsonObject.getJSONObject("main").getDouble("humidity");
                    realmWeather.setHumidity(humidity);

                    double pressure = weatherJsonObject.getJSONObject("main").getDouble("pressure");
                    realmWeather.setPressure(pressure);

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(realmWeather);
                    realm.commitTransaction();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
        }
    }
}