package ir.mralizade.imdbshow.domin

interface GetPopularMoviesUseCase {

    suspend fun getPopularMoviesFromServer()

    suspend fun getPopularMoviesFromDatabase()

    suspend fun insertPopularMoviesToDatabase()

    suspend fun clearPopularDatabase()

}