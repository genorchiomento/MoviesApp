package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.api.RetrofitConfig
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.home.presenter.GenresPresenter
import com.arctouch.codechallenge.home.presenter.impl.GenresPresenterImpl
import org.koin.dsl.module

val appModules = module {
  single<TmdbApi> {
    RetrofitConfig().apiService
  }
}
