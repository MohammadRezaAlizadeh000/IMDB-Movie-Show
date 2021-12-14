package ir.mralizade.imdbshow.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mralizade.imdbshow.utils.SINGLE_MOVIES_TABLE_NAME

@Entity(tableName = SINGLE_MOVIES_TABLE_NAME)
data class SingleMoviesEntity(
    @PrimaryKey
    val movieId: String,
    val name: String,
    val posterUrl: String,
    val imDbRate: String,
    val imDbRateCount: String,
    val companies: String,
    val countries: String,
    val languages: String,
    val genres: String,
    val stars: String,
    val year: String,
    val writers: String,
    val directors: String,
    val description: String
)
