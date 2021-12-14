package ir.mralizade.imdbshow.domin

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ir.mralizade.imdbshow.data.Repository
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

var funName = ""

// check internet status connect to repository - call request - get data - return to view model
suspend fun getPopularMovies(
    repository: Repository,
    startPoint: Int = 0,
    context: Context
): (data: List<PopularMovieEntity>?, appState: String, message: String) -> Unit {

    val moviesData = getDataFromDatabase(repository, startPoint)
    return if (moviesData.isEmpty()) {
        val movieData = getDataFromServer(repository, context)
        if (movieData != null) {
            val finalData = convertServerDataToEntity(movieData)
            insertMoviesDataToDatabase(finalData, repository)
            val finalMovieData = getDataFromDatabase(repository)
            ((finalMovieData); AppState; )
        } else {
            return (null; AppState.APP_STATE_ERROR; (context.systemMessages(SYSTEM_ERROR)))
        }
    } else {
        (moviesData; AppState.APP_STATE_SUCCESS; "")
    }
}

private suspend fun insertMoviesDataToDatabase(finalData: List<PopularMovieEntity>, repository: Repository) {
    withContext(Dispatchers.IO) {
        try {
            repository.local.insertMovie(finalData)
        } catch (e: Exception) {

        }
    }
}

private fun hasInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

    return when {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}

private suspend fun getDataFromDatabase(repository: Repository, startPoint: Int = 0): List<PopularMovieEntity> {
    return withContext(Dispatchers.IO) {
        return@withContext repository.local.getAllMovies(startPoint)
    }
}

private suspend fun getDataFromServer(repository: Repository, context: Context): PopularMoviesResponseModel? {
    return withContext(Dispatchers.IO) {
        try {
            if (hasInternetConnection(context)){
                val response = repository.remote.getPopularMovies()
                if(isServerResponseSuccess(response.body()!!)) {
                    return@withContext response.body()
                } else {
                    return@withContext null
                }
            } else {
                return@withContext null
            }
        } catch (e: Exception) {
           return@withContext null
        }
    }
}

private fun isServerResponseSuccess(response: PopularMoviesResponseModel?) = response != null

private suspend fun convertServerDataToEntity(rawData: PopularMoviesResponseModel): List<PopularMovieEntity> {
    funName = java.lang.Exception().stackTrace[0].methodName
    log(APP_STATE_TAG, funName)

    return withContext(Dispatchers.Default) {
        val dataList = mutableListOf<PopularMovieEntity>()

        rawData.items?.forEach { data ->
            dataList.add(
                PopularMovieEntity(
                    data.id!!,
                    data.rank!!,
                    data.title!!,
                    data.year!!,
                    data.image!!,
                    data.crew!!,
                    data.imDbRating!!,
                    data.imDbRatingCount!!
                )
            )
        }
        return@withContext dataList
    }
}