package ir.mralizade.imdbshow

import ir.mralizade.imdbshow.utils.AppState

interface GetPopularMoviesRepoAction {

    suspend fun getAllMovies(startPoint: Int): AppState<*>

}