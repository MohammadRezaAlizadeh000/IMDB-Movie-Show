package ir.mralizade.imdbshow.data

import ir.mralizade.imdbshow.data.network.MoviesAPIService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesAPIService: MoviesAPIService
) {

    suspend fun getPopularMovies() = moviesAPIService.getPopularMovies()
    suspend fun getSingleMovie(movieId: String) = moviesAPIService.getSingleMovie(movieId)

}