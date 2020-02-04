package com.arctouch.codechallenge.util

class MovieImageUrlBuilder {

  fun buildPosterUrl(posterPath: String): String {
    return Constants.POSTER_URL + posterPath + "?api_key=" + Constants.API_KEY
  }

  fun buildBackdropUrl(backdropPath: String): String {
    return Constants.BACKDROP_URL + backdropPath + "?api_key=" + Constants.API_KEY
  }
}
