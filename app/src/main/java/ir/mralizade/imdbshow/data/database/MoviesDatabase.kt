package ir.mralizade.imdbshow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.mralizade.imdbshow.data.database.dao.MoviesDAO
import ir.mralizade.imdbshow.data.database.dao.SingleMovieDAO
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.DATABASE_VERSION

@Database(
    entities = [PopularMovieEntity::class, SingleMoviesEntity::class],
    version = DATABASE_VERSION,
)
//@TypeConverters(SingleMovieTypeConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun accessPopularMovieDao(): MoviesDAO
    abstract fun accessSingleMovieDao(): SingleMovieDAO

}