package com.example.myapp;

public class Data {
    private int image;
    private String text;

    public Data(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}
