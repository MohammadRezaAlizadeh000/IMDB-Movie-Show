package ir.mralizade.imdbshow.data.mapper

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.utils.mapToEntity

object PopularMoviesResponseMapper: BaseMapper<PopularMoviesResponseModel, List<PopularMovieEntity>> {
    override suspend fun mapTo(data: PopularMoviesResponseModel?): List<PopularMovieEntity>? {
        return data?.items?.map { it.mapToEntity() }
    }

}