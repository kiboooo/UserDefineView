package com.example.kiboooo.userdefinedview.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kiboooo on 2017/12/5.
 */

public class WeathResult {
    public Realtime realtime;
    public Life life;

    @SerializedName("weather")
    public List<Weather> WeatherList;
    public Pm25 pm25;
    public int isForeign;

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public Life getLife() {
        return life;
    }

    public void setLife(Life life) {
        this.life = life;
    }

    public List<Weather> getWeatherList() {
        return WeatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        WeatherList = weatherList;
    }

    public Pm25 getPm25() {
        return pm25;
    }

    public void setPm25(Pm25 pm25) {
        this.pm25 = pm25;
    }

    public int getIsForeign() {
        return isForeign;
    }

    public void setIsForeign(int isForeign) {
        this.isForeign = isForeign;
    }
}
