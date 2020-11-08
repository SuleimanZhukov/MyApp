package com.example.myapp;

public class MainPresenter {

    private static MainPresenter instance;
    private static Object object = new Object();

    private String weather;
    private String degree;

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWeather() {
        return weather;
    }

    public String getDegree() {
        return degree;
    }

    private MainPresenter() {

    }

    public static MainPresenter getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new MainPresenter();
            }
            return instance;
        }
    }
}
