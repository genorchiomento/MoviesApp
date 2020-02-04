package com.arctouch.codechallenge

import android.annotation.SuppressLint
import android.app.Application
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.retrofit.RetrofitConfig
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import com.arctouch.codechallenge.di.appModules
import com.arctouch.codechallenge.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

  companion object {
    lateinit var service: TmdbApi
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@AppApplication)
      modules(appModules)
    }

    service = RetrofitConfig().apiService

    getGenres()
  }

  @SuppressLint("CheckResult")
  private fun getGenres() {
    service.genres(
        Constants.API_KEY,
        Constants.DEFAULT_LANGUAGE)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          Cache.cacheGenres(it.genres)
        }
  }
}
