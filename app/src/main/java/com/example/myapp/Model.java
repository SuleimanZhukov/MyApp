package com.example.myapp;

public class Model {

    private static Model instance;
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

    private Model() {

    }

    public static Model getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new Model();
            }
            return instance;
        }
    }
}
