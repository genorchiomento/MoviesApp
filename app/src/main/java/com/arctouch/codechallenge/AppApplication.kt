package com.arctouch.codechallenge

import android.app.Application
import com.arctouch.codechallenge.di.appModules
import com.arctouch.codechallenge.home.presenter.GenresPresenter
import com.arctouch.codechallenge.home.presenter.impl.GenresPresenterImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {

  companion object {
    private val genresPresenter: GenresPresenter = GenresPresenterImpl()
  }

  override fun onCreate() {
    super.onCreate()
    startKoin {
      androidContext(this@AppApplication)
      modules(appModules)
    }

    genresPresenter.genresPresenterImpl()
  }
}
