package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.ui.viewmodel.MovieDetailsViewModel
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.ext.android.inject

class MovieDetailsActivity : BaseActivity() {

  private lateinit var viewModel: MovieDetailsViewModel
  private val movieImageUrlBuilder: MovieImageUrlBuilder by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_details)

    val movieId = intent.extras?.getInt("id", 0)
    val movieTitle = intent.extras?.getString("title", getString(R.string.toolbar_movie_details_title))

    configToolbar(toolbar as Toolbar, movieTitle.toString(), true)

    movieId?.let { setListeners(it) }
  }

  private fun setListeners(movieId: Int) {
    callViewModelObserver()
    viewModelListener(movieId?.let { it })
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  private fun viewModelListener(movieId: Int) {
    viewModel.getMovieById(movieId?.let { it.toLong() })
  }

  private fun callViewModelObserver() {
    viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)


    viewModel.movieDetailsLiveData.observe(this, Observer {

      checkMovieData(it)

      loadImages(it)

    })
  }

  private fun checkMovieData(it: Movie) {
    if (it.title.isEmpty() || it.releaseDate.isNullOrEmpty()) {
      setMessageGenericTitleOrReleaseDate()
    } else {
      setMovieDataTitleOrReleaseDate(it)
    }

    if (it.overview.isNullOrEmpty()) {
      textViewMovieOverview.text = getString(R.string.msg_missing_detail_overview)
    } else {
      textViewMovieOverview.text = it.overview
    }

    if (it.genres.isNullOrEmpty()) {
      textViewMovieGenres.text = getString(R.string.msg_missing_detail_genre)
    } else {
      textViewMovieGenres.text = it.genres?.joinToString(separator = " - ") { it.name }
    }
  }

  private fun setMovieDataTitleOrReleaseDate(it: Movie) {
    textViewMovieTitle.text = it.title
    textViewMovieRelease.text = it.releaseDate
  }

  private fun setMessageGenericTitleOrReleaseDate() {
    textViewMovieTitle.text = getString(R.string.msg_missing_detail_generic)
    textViewMovieRelease.text = getString(R.string.msg_missing_detail_generic)
  }

  private fun loadImages(it: Movie) {
    val urlPoster = it.posterPath?.let { poster -> movieImageUrlBuilder.buildPosterUrl(poster) }
    val urlBackdrop = it.backdropPath?.let { backdrop -> movieImageUrlBuilder.buildBackdropUrl(backdrop) }

    Glide.with(imageViewMoviePoster)
        .load(urlPoster)
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(imageViewMoviePoster)

    Glide.with(imageViewMovieBackdrop)
        .load(urlBackdrop)
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(imageViewMovieBackdrop)
  }
}
