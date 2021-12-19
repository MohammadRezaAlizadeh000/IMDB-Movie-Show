package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.Repository
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.AppState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SingleMovieUseCaseImpl @Inject constructor(
    private val repository: Repository
): SingleMovieUseCase {

    override suspend fun getSingleMovie(movieId: String): Flow<AppState<SingleMoviesEntity>> = repository.getSingleMovieData(movieId)


}