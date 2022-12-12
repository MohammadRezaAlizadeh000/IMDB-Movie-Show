package ir.mralizade.imdbshow.data.mapper

interface BaseMapper<T, R> {
    suspend fun mapTo(data: T?): R?
}