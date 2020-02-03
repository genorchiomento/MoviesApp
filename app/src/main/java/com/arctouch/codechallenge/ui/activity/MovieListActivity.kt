package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.adapter.MovieListAdapter
import com.arctouch.codechallenge.ui.viewmodel.MovieListViewModel
import com.arctouch.codechallenge.util.Constants
import kotlinx.android.synthetic.main.movie_list_activity.*

class MovieListActivity : AppCompatActivity() {

  private lateinit var viewModel: MovieListViewModel
  private val adapter by lazy { MovieListAdapter() }
  private var page: Long = Constants.PAGE
  private var isScroll: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.movie_list_activity)

    setListener()
  }

  private fun setListener() {
    configRecyclerView()
    callViewModelObserver()
    viewModelListener()
    configScrollListener()
  }

  private fun configRecyclerView() {
    recyclerViewMovieList.adapter = adapter
  }

  private fun callViewModelObserver() {
    viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

    viewModel.movieListLiveData.observe(this, Observer {

      progressBarMovieList.visibility = View.GONE

      isEqualsPage(it)
    })
  }

  private fun isEqualsPage(it: List<Movie>) {
    if (page == Constants.PAGE) adapter.movies = it else {
      val helper: MutableList<Movie> = mutableListOf()
      helper.addAll(adapter.movies)
      helper.addAll(it)
      adapter.movies = helper
    }
  }

  private fun viewModelListener() {
    viewModel.getUpcomingMovies(page)
  }

  private fun configScrollListener() {

    var layoutManager = recyclerViewMovieList.layoutManager as LinearLayoutManager

    recyclerViewMovieList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
          viewModel.getUpcomingMovies(page)
        }
      }
    })
  }
}
