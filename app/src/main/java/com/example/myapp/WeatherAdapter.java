package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private DataSource dataSource;

    public WeatherAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = dataSource.getData(position);
        holder.setData(data.getImage(), data.getText());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_weather_item);
            imageView = itemView.findViewById(R.id.image_weather_item);
        }

        public void setData(int image, String text) {
            getText().setText(text);
        }

        public TextView getText() {
            return textView;
        }
    }
}
