package com.kobbi.weatherapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY = "284b7c32ae55a7ae83fcff465117015b";

    private Button btnSearch;
    private EditText cityName;
    private ImageView iconWeather;
    private TextView temp, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSearch = findViewById(R.id.btnSearch);
        cityName = findViewById(R.id.cityName);
        iconWeather = findViewById(R.id.iconWeather);
        temp = findViewById(R.id.temp);
        city = findViewById(R.id.city);

        btnSearch.setOnClickListener(v -> {
            String cityValue = cityName.getText().toString().trim();

            if (cityValue.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter a city name", Toast.LENGTH_SHORT).show();
            } else {
                //  TODO: load weather by city name
                loadWeatherByCityName(cityValue);
            }
        });
    }

    private void loadWeatherByCityName(String cityValue) {
        Ion.with(getApplicationContext())
                .load("https://api.openweathermap.org/data/2.5/weather?q=" + cityValue + "&units=metric&appid=" + API_KEY)
                .asJsonObject()
                .setCallback((e, result) -> {
                    if (e != null) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                    } else {
                        // convert json to response java
                        try {
                            JsonObject main = result.get("main").getAsJsonObject();

                            // set temp
                            double tempValue = main.get("temp").getAsDouble();
                            String tempFinal = tempValue + " °C";
                            temp.setText(tempFinal);

                            // set city & country
                            String cityAndCountry;
                            JsonObject sys = result.get("sys").getAsJsonObject();
                            cityAndCountry = result.get("name").getAsString() + ", " + sys.get("country").getAsString();
                            city.setText(cityAndCountry);

                            // set icon weather
                            JsonArray weather = result.get("weather").getAsJsonArray();
                            String icon = weather.get(0).getAsJsonObject().get("icon").getAsString();
                            loadIcon(icon);
                        } catch (Exception exception) {
                            Toast.makeText(getApplicationContext(), result.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadIcon(String icon) {
        Ion.with(getApplicationContext())
                .load("http://openweathermap.org/img/w/" + icon + ".png")
                .intoImageView(iconWeather);
    }
}