package ir.mralizade.imdbshow.model.popularmovies

data class PopularMoviesResponseModel(
    val errorMessage: String?,
    val items: List<Item>?
)