package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.utils.NetworkResponseState
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {

    suspend fun getPopularMovies(startPoint: Int, isOnline: Boolean): NetworkResponseState<List<PopularMovieEntity>>

}