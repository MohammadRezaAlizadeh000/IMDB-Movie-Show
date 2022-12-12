package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.RepositoryImpl
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.utils.NetworkResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val repository: RepositoryImpl
): GetPopularMoviesUseCase {

    override suspend fun getPopularMovies(startPoint: Int, isOnline: Boolean): NetworkResponseState<List<PopularMovieEntity>> =
        repository.getPopularMovies(startPoint, isOnline = isOnline)
}