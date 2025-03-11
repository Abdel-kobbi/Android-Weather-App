package com.kobbi.weatherapp;

public class Weather {

    String date;
    double temp;

    public Weather(String date, double temp) {
        this.date = date;
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
