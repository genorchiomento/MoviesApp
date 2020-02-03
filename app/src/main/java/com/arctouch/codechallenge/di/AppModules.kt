package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.retrofit.RetrofitConfig
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import org.koin.dsl.module

val appModules = module {
  single<TmdbApi> {
    RetrofitConfig().apiService
  }
}
