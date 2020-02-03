package com.arctouch.codechallenge.data.retrofit

import com.arctouch.codechallenge.data.model.GenreResponse
import com.arctouch.codechallenge.data.model.Movie
import com.arctouch.codechallenge.data.model.UpcomingMoviesResponse
import com.arctouch.codechallenge.util.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

  @GET(Constants.URL_GENRE_LIST)
  fun genres(
      @Query("api_key") apiKey: String = Constants.API_KEY,
      @Query("language") language: String = Constants.DEFAULT_LANGUAGE
  ): Observable<GenreResponse>

  @GET(Constants.URL_UPCOMING)
  fun upcomingMovies(
      @Query("page") page: Long,
      @Query("api_key") apiKey: String = Constants.API_KEY,
      @Query("language") language: String = Constants.DEFAULT_LANGUAGE,
      @Query("region") region: String = Constants.DEFAULT_REGION
  ): Observable<UpcomingMoviesResponse>

  @GET(Constants.URL_MOVIE + "{id}")
  fun movie(
      @Path("id") id: Long,
      @Query("api_key") apiKey: String = Constants.API_KEY,
      @Query("language") language: String = Constants.DEFAULT_LANGUAGE
  ): Observable<Movie>
}
