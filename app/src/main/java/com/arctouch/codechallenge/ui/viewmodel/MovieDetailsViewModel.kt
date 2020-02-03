package com.arctouch.codechallenge.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arctouch.codechallenge.AppApplication
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel : ViewModel() {
  private val service: TmdbApi = AppApplication.service
  val movieDetailsLiveData = MutableLiveData<Movie>()

  @SuppressLint("CheckResult")
  fun getMovieById(id: Long) {
    service.movie(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          movieDetailsLiveData.postValue(it)
        }

  }
}
