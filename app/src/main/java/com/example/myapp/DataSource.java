package com.example.myapp;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private List<Data> dataSource;
    private Resources resources;

    public DataSource(Resources resources) {
        this.dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public DataSource build() {
        String[] text = resources.getStringArray(R.array.degrees);

        int image = resources.getIntArray(R.array.image)[0];

        for (int i = 0; i < text.length; i++) {
            dataSource.add(new Data(image, text[i]));
        }

        return this;
    }

    public int size() {
        return dataSource.size();
    }

    public Data getData(int position) {
        return dataSource.get(position);
    }
}
