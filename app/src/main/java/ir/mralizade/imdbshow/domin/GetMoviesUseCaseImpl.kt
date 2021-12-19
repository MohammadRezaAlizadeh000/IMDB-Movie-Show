package ir.mralizade.imdbshow.domin

import ir.mralizade.imdbshow.data.Repository
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.utils.AppState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCaseImpl @Inject constructor(
    private val repository: Repository
): GetPopularMoviesUseCase {

    override suspend fun getPopularMovies(startPoint: Int): Flow<AppState<List<PopularMovieEntity>>> =
        repository.getPopularMovies(startPoint)
}