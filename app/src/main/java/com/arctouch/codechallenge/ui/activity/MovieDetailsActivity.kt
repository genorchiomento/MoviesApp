package com.arctouch.codechallenge.ui.activity

import android.os.Bundle
import com.arctouch.codechallenge.R
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_movie_details)

    configToolbar(toolbarMovieDetails, getString(R.string.toolbar_movie_details_title), true)

  }
}
