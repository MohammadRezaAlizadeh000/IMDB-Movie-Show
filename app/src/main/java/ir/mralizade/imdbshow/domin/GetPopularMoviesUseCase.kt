package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.utils.AppState
import kotlinx.coroutines.flow.Flow

interface GetPopularMoviesUseCase {

    suspend fun getPopularMovies(startPoint: Int): Flow<AppState<List<PopularMovieEntity>>>

}