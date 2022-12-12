package ir.mralizade.imdbshow.data.network

import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.model.singlemovie.SingleMovieResponseModel
import ir.mralizade.imdbshow.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesAPIService {

    @GET(HomeEndPointList.MOST_POPULAR_MOVIE)
    suspend fun getPopularMovies(
        @Path(RequestParamKeys.API_KEY_PARAM_KEY) apiKey: String = API_KEY
    ): Response<PopularMoviesResponseModel>

    @GET(SingleMovieEndpoints.SINGLE_MOVIE_DATA)
    suspend fun getSingleMovie(
        @Path(RequestParamKeys.ID_PARAM_KEY) movieId: String,
        @Path(RequestParamKeys.API_KEY_PARAM_KEY) apiKey: String = API_KEY
    ): Response<SingleMovieResponseModel>

}