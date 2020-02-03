package com.arctouch.codechallenge.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject

class HomeActivity : AppCompatActivity() {

  private val service: TmdbApi by inject()

  @SuppressLint("CheckResult")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.home_activity)

    service.upcomingMovies(
        Constants.API_KEY,
        Constants.DEFAULT_LANGUAGE,
        1,
        Constants.DEFAULT_REGION)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          val moviesWithGenres = it.results.map { movie ->
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
          }
          recyclerView.adapter = HomeAdapter(moviesWithGenres)
          progressBar.visibility = View.GONE
        }
  }
}
