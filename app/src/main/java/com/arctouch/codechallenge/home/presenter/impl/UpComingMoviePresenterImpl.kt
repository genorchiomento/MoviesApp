package com.arctouch.codechallenge.home.presenter.impl

import com.arctouch.codechallenge.api.RetrofitConfig
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.home.presenter.UpComingMoviePresenter

class UpComingMoviePresenterImpl(
    private val service: TmdbApi = RetrofitConfig().apiService
) : UpComingMoviePresenter {

  override fun upcomingMovie() {

  }
}
