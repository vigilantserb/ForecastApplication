package com.stameni.com.forecastapplication.data.network

import com.stameni.com.forecastapplication.data.network.response.CurrentWeatherResponse
import com.stameni.com.forecastapplication.data.network.response.FutureWeatherResponse
import com.stameni.com.forecastapplication.internals.ConnectivityInterceptor
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "4585e98c71e64a4fad1124542191901"
private const val BASE_URL = "https://api.apixu.com/v1/"

interface WeatherApi {

    //http://api.apixu.com/v1/current.json?key=4585e98c71e64a4fad1124542191901&q=Belgrade
    @GET("current.json")
    fun getCurrentWeather(
        @Query("q") location: String,
        @Query("lang") language: String = "en"
    ): Observable<CurrentWeatherResponse>

    //http://api.apixu.com/v1/forecast.json?key=4585e98c71e64a4fad1124542191901&q=Belgrade&days=1
    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int,
        @Query("lang") language: String = "en"
    ): Observable<FutureWeatherResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherApi {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", "4585e98c71e64a4fad1124542191901")
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.level = Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}