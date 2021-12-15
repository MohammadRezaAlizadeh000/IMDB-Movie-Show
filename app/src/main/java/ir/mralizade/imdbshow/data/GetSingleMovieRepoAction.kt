package ir.mralizade.imdbshow.data

import ir.mralizade.imdbshow.utils.AppState

interface GetSingleMovieRepoAction {

    suspend fun getSingleMovie(movieId: String): AppState<*>

}