package pablo.armario.sportivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Clase Weather me he ayudado de https://github.com/barmangolap15/Weather-Information-App-in-Android-JAVA
 */
public class WeatherActivity extends AppCompatActivity {
    //Dejo el valor cadiz predeterminado como la String que se busca, ya que no tengo suficiente tiempo para añadir
    //una funcionalidad de ubicación
    String CITY = "Cadiz";
    String API = "275dd33817d21c3d0f9e8c2792f90b6a";
    ImageView search;
    EditText etCity;
    TextView city,country,time,temp,forecast,humidity,min_temp,max_temp,sunrises,sunsets,pressure,windSpeed;

    public WeatherActivity() throws CertificateException, KeyStoreException, FileNotFoundException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);
        {
            etCity =  findViewById(R.id.Your_city);
            search = findViewById(R.id.search);

            city =  findViewById(R.id.city);
            country = findViewById(R.id.country);
            time = findViewById(R.id.time);
            temp = findViewById(R.id.temp);
            forecast = findViewById(R.id.forecast);
            humidity = findViewById(R.id.humidity);
            min_temp = findViewById(R.id.min_temp);
            max_temp = findViewById(R.id.max_temp);
            sunrises = findViewById(R.id.sunrises);
            sunsets =  findViewById(R.id.sunsets);
            pressure = findViewById(R.id.pressure);
            windSpeed = findViewById(R.id.wind_speed);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!etCity.getText().toString().trim().equals("")){
                        CITY = etCity.getText().toString();
                    }
                    new weatherTask().execute();
                }
            });
        }
    }


    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API;
                URL url = new URL(apiUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                try {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                Log.e("WeatherApp", "Error in doInBackground: " + e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject sys = jsonObj.getJSONObject("sys");


                String city_name = jsonObj.getString("name");
                String countryname = sys.getString("country");
                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temperature = main.getString("temp");
                String cast = weather.getString("description");
                String humi_dity = main.getString("humidity");
                String temp_min = main.getString("temp_min");
                String temp_max = main.getString("temp_max");
                String pre = main.getString("pressure");
                String windspeed = wind.getString("speed");
                Long rise = sys.getLong("sunrise");
                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
                Long set = sys.getLong("sunset");
                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));


                city.setText(city_name);
                country.setText(countryname);
                time.setText(updatedAtText);
                temp.setText(temperature + "°C");
                forecast.setText(cast);
                humidity.setText(humi_dity);
                min_temp.setText(temp_min);
                max_temp.setText(temp_max);
                sunrises.setText(sunrise);
                sunsets.setText(sunset);
                pressure.setText(pre);
                windSpeed.setText(windspeed);

            } catch (Exception e) {
                Log.e("WeatherApp", "Error: " + e.toString());
                }
        }
    }
}
