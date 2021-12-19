package ir.mralizade.imdbshow.data

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.dao.MoviesDAO
import ir.mralizade.imdbshow.data.database.dao.SingleMovieDAO
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val moviesDAO: MoviesDAO,
    private val singleMovieDAO: SingleMovieDAO
) {



    suspend fun insertMovie(popularMovie: List<PopularMovieEntity>) = moviesDAO.insertMovie(popularMovie)
    suspend fun getAllMovies(startPoint: Int) = moviesDAO.getAllMovies(startPoint)
    suspend fun clearDatabase() = moviesDAO.clearDatabase()

    suspend fun getSingleMovie(id: String) = singleMovieDAO.getSingleMovie(id)
    suspend fun insertSingleMovie(singleMovie: SingleMoviesEntity) = singleMovieDAO.insertSingleMovie(singleMovie)
    suspend fun clearMovieFromDatabase(id: String) = singleMovieDAO.clearMovieFromDatabase(id)

}