package ir.mralizade.imdbshow.data

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mralizade.imdbshow.data.mapper.BaseMapper
import ir.mralizade.imdbshow.data.mapper.PopularMoviesResponseMapper
import ir.mralizade.imdbshow.data.network.MoviesAPIService
import ir.mralizade.imdbshow.utils.NetworkResponseState
import ir.mralizade.imdbshow.utils.mapToEntity
import ir.mralizade.imdbshow.utils.toNetWorkResponseState
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesAPIService: MoviesAPIService,
    @ApplicationContext val context: Context
) {

    suspend fun getPopularMovies(isOnline: Boolean) = moviesAPIService.getPopularMovies()
        .toNetWorkResponseState(isOnline, PopularMoviesResponseMapper)

    suspend fun getSingleMovie(movieId: String) = moviesAPIService.getSingleMovie(movieId)

}