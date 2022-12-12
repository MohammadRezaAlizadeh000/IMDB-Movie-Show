package ir.mralizade.imdbshow.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity

@Dao
interface MoviesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(popularMovie: List<PopularMovieEntity>)

    @Query("SELECT * FROM popular_movies LIMIT :startPoint, 20")
    suspend fun getAllMovies(startPoint: Int): List<PopularMovieEntity>

    @Query("DELETE FROM popular_movies")
    suspend fun clearDatabase()

}