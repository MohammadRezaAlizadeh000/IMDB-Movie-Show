package ir.mralizade.imdbshow.utils

import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.model.popularmovies.Item
import ir.mralizade.imdbshow.model.singlemovie.SingleMovieResponseModel

fun Item.mapToEntity() = PopularMovieEntity(
    this.id!!,
    this.rank!!,
    this.title!!,
    this.year!!,
    this.image!!,
    this.crew!!,
    this.imDbRating!!,
    this.imDbRatingCount!!
)

fun SingleMovieResponseModel.mapToEntity() = SingleMoviesEntity(
    this.id!!,
    this.fullTitle!!,
    this.image!!,
    this.imDbRating!!,
    this.imDbRatingVotes!!,
    this.companies!!,
    this.countries!!,
    this.languages!!,
    this.genres!!,
    this.stars!!,
    this.year!!,
    this.writers!!,
    this.directors!!,
    this.plot!!
)