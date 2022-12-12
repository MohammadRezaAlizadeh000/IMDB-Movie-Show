package ir.mralizade.imdbshow.data

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.NetworkResponseState
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPopularMovies(startPoint: Int, isRefresh: Boolean = false, isOnline: Boolean): NetworkResponseState<List<PopularMovieEntity>>

    suspend fun getSingleMovieData(movieId: String): Flow<NetworkResponseState<SingleMoviesEntity>>

}