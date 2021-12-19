package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.AppState
import kotlinx.coroutines.flow.Flow

interface SingleMovieUseCase {

    suspend fun getSingleMovie(movieId: String) : Flow<AppState<SingleMoviesEntity>>

}