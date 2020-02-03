package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.movie_list_activity)

    setListener()
  }

  private fun setListener() {
    configRecyclerView()
    callViewModelObserver()
    viewModelListener()
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
    viewModel.configScrollListener(recyclerViewMovieList)
  }
}
