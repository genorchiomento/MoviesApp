package com.arctouch.codechallenge.data.retrofit

import com.arctouch.codechallenge.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitConfig {

  private val client by lazy {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
  }

  private val retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(Constants.URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
  }

  val apiService: TmdbApi by lazy {
    retrofit.create(TmdbApi::class.java)
  }
}
