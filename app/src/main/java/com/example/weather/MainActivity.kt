package com.example.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var cityInput: EditText? = null
    private var cityBtn: Button? = null
    private var cityResult: TextView? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityInput = findViewById(R.id.cityInput)
        cityBtn = findViewById(R.id.cityBtn)
        cityResult = findViewById(R.id.cityResult)

        cityBtn?.setOnClickListener {
            if (cityInput?.text.toString().equals("")) {
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_LONG).show()
            }
            else {
                val city: String = cityInput?.text.toString().trim()
                val apiWeatherKey: String = "ac5169edd4ca08b26ae4ed2d83fe9aa3"
                val openWeatherMapApiUrl: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$apiWeatherKey&units=metric&lang=ru"
                doAsync {
                    val response = JSONObject(URL(openWeatherMapApiUrl).readText())
                    val description = response.getJSONArray("weather").getJSONObject(0).getString("description")
                    val temp = response.getJSONObject("main").getString("temp")
                    val feelsLike = response.getJSONObject("main").getString("feels_like")
                    cityResult?.text = "\nТемпература: $temp\nОщущается как: $feelsLike\nНебо: $description"
                }
            }
        }
    }
}