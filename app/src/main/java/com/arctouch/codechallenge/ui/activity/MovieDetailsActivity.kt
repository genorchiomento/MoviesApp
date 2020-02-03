package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import android.view.MenuItem
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_details)

    val movieId = intent.extras?.getInt("id")
    val movieTitle = intent.extras?.getString("title")

    configToolbar(toolbarMovieDetails, movieTitle.toString(), true)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      android.R.id.home -> onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }
}
