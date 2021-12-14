package ir.mralizade.imdbshow.data.network

import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.model.singlemovie.SingleMovieResponseModel
import ir.mralizade.imdbshow.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesAPIService {

    @GET("MostPopularMovies/{apiKey}")
    suspend fun getPopularMovies(
        @Path("apiKey") apiKey: String = API_KEY
    ): Response<PopularMoviesResponseModel>

    @GET("Title/{apiKey}/{id}/Trailer")
    suspend fun getSingleMovie(
        @Path("id") movieId: String,
        @Path("apiKey") apiKey: String = API_KEY
    ): Response<SingleMovieResponseModel>

}