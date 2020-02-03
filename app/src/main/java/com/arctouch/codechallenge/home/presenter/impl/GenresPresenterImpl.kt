package com.arctouch.codechallenge.home.presenter.impl

import com.arctouch.codechallenge.api.RetrofitConfig
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.home.presenter.GenresPresenter
import com.arctouch.codechallenge.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GenresPresenterImpl(
    private val service: TmdbApi = RetrofitConfig().apiService
) : GenresPresenter {

  override fun genres() {
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
