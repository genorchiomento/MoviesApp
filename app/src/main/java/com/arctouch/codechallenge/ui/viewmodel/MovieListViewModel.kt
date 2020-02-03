package com.arctouch.codechallenge.ui.viewmodel

import android.annotation.SuppressLint
import android.widget.AbsListView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.AppApplication
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.retrofit.TmdbApi
import com.arctouch.codechallenge.util.Constants
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

  fun configScrollListener(recyclerView: RecyclerView) {
    var isScroll: Boolean = false
    var page: Long = Constants.PAGE
    var layoutManager = recyclerView.layoutManager as LinearLayoutManager

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
          isScroll = true
        }
      }

      override fun onScrolled(recyclerView: RecyclerView, hresult: Int, vresult: Int) {
        super.onScrolled(recyclerView, hresult, vresult)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val expectedTotal = firstVisibleItemPosition.let { visibleItemCount.plus(it) }
        if (isScroll && expectedTotal == totalItemCount) {
          isScroll = false
          page = page.plus(1)
          getUpcomingMovies(page)
        }
      }
    })
  }
}
