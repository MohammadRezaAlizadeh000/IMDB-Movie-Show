package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.RepositoryImpl
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.NetworkResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SingleMovieUseCaseImpl @Inject constructor(
    private val repository: RepositoryImpl
): SingleMovieUseCase {

    override suspend fun getSingleMovie(movieId: String): Flow<NetworkResponseState<SingleMoviesEntity>> = repository.getSingleMovieData(movieId)


}