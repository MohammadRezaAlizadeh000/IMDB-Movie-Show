package ir.mralizade.imdbshow.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity

@Dao
interface SingleMovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleMovie(singleMovie: SingleMoviesEntity)

    @Query("SELECT * FROM single_movies WHERE movieId=:id")
    suspend fun getSingleMovie(id: String): SingleMoviesEntity

    @Query("DELETE FROM single_movies WHERE movieId=:id")
    suspend fun clearMovieFromDatabase(id: String)

}