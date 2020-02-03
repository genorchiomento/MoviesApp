package com.arctouch.codechallenge.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import com.arctouch.codechallenge.ui.adapter.HomeAdapter
import com.arctouch.codechallenge.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*
import org.koin.android.ext.android.inject

class MovieListActivity : AppCompatActivity() {

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
