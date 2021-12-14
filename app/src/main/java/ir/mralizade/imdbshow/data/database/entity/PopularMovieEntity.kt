package ir.mralizade.imdbshow.data.database.entity

import ir.mralizade.imdbshow.utils.POPULAR_MOVIES_TABLE_NAME
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = POPULAR_MOVIES_TABLE_NAME)
data class PopularMovieEntity(
    @PrimaryKey
    val movieId: String,
    val rank: String,
    val title: String,
    val year: String,
    val posterUrl: String,
    val crew: String,
    val imDbRate: String,
    val imDbRateCount: String
)