package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.data.retrofit.RetrofitConfig
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import org.koin.dsl.module

val appModules = module {
  single<TmdbApi> {
    RetrofitConfig().apiService
  }

  single<MovieImageUrlBuilder> {
    MovieImageUrlBuilder()
  }
}
