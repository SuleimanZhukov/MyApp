package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        button = findViewById(R.id.amman);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcel parcel = new Parcel();
                parcel.setCityName(button.getText().toString());

                Intent intent = new Intent(MenuActivity.this, WeatherActivity.class);
                intent.putExtra(Key.KEY_EXTRA, parcel);
                startActivity(intent);
            }
        });
    }
}
