package com.arctouch.codechallenge.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.arctouch.codechallenge.AppApplication
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieListViewModel : BaseViewModel() {

  private val service: TmdbApi = AppApplication.service
  val movieListLiveData = MutableLiveData<List<Movie>>()

  @SuppressLint("CheckResult")
  fun getUpcomingMovies(page: Long) {
    service.upcomingMovies(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          movieListLiveData.postValue(it.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
          })
        }
  }
}
