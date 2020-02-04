package com.arctouch.codechallenge.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.adapter.MovieListAdapter
import com.arctouch.codechallenge.ui.viewmodel.MovieListViewModel
import com.arctouch.codechallenge.util.Constants
import kotlinx.android.synthetic.main.activity_movie_list.*

class MovieListActivity : BaseActivity() {

  private lateinit var viewModel: MovieListViewModel
  private lateinit var searchViewMenu: SearchView

  private val adapter by lazy { MovieListAdapter() }
  private var page: Long = Constants.PAGE
  private var isScroll: Boolean = false
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_list)

    configToolbar(toolbar as Toolbar, getString(R.string.toolbar_movie_list_title))

    setListener()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.search_menu, menu)

    val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
    searchViewMenu = (menu.findItem(R.id.searchViewMenu).actionView as SearchView).apply {
      setSearchableInfo(searchManager.getSearchableInfo(componentName))
    }

    configSearchViewMenu()

    return super.onCreateOptionsMenu(menu)
  }

  private fun configSearchViewMenu() {
    searchViewMenu.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isNotEmpty()) {
          viewModel.searchMovies(newText)
        } else {
          viewModel.getUpcomingMovies(page)
        }
        return true
      }

      override fun onQueryTextSubmit(query: String?): Boolean {
        return false
      }
    })
  }

  private fun setListener() {
    configRecyclerView()
    callViewModelObserver()
    viewModelListener()
    configScrollListener()
  }

  private fun configRecyclerView() {
    recyclerViewMovieList.adapter = adapter

    adapter.setOnItemClickListener {
      startActivity(Intent(this, MovieDetailsActivity::class.java)
          .putExtra("id", it.id).putExtra("title", it.title))
    }
  }

  private fun callViewModelObserver() {
    viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

    viewModel.movieListLiveData.observe(this, Observer { it ->

      progressBarMovieList.visibility = View.GONE

      isEqualsPage(it)

      viewModel.searchMovieLiveData.observe(this, Observer {
        adapter.movies = it
      })
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

    val layoutManager = recyclerViewMovieList.layoutManager as LinearLayoutManager

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

  override fun onBackPressed() = Unit
}
