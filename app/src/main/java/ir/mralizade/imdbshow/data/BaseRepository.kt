package ir.mralizade.imdbshow.data

abstract class BaseRepository {

    abstract suspend fun hasInternetConnection() : Boolean

    abstract suspend fun <R, F> mapData(rawData: R, finalData: F): F
}