package ir.mralizade.imdbshow.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.AppState
import ir.mralizade.imdbshow.utils.mapToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext val context: Context
) {

    suspend fun getPopularMovies(startPoint: Int): Flow<AppState<List<PopularMovieEntity>>> {
        val data = withContext(Dispatchers.IO) {
            return@withContext localDataSource.getAllMovies(startPoint)
        }
        return flow {
            if (data.isNotEmpty())
                emit(AppState.Success(data))
            else {
                when (val serverData = getPopularFromServer()) {
                    is AppState.Success -> {
                        insertToDb(serverData.data!!)
                        emit(serverData)
                    }
                    is AppState.Error -> emit(serverData)
                }
            }
        }
    }

    suspend fun getSingleMovieData(movieId: String): Flow<AppState<SingleMoviesEntity>> {
        val movieData = withContext(Dispatchers.IO) {
            return@withContext localDataSource.getSingleMovie(movieId)
        }
        return flow {
            if (movieData.isNotEmpty())
                emit(AppState.Success(movieData[0]))
            else {
                when (val data = getSingleFromServer(movieId)) {
                    is AppState.Success -> {
                        insertSingleMovieToDb(data.data!!)
                        emit(data)
                    }
                    is AppState.Error -> emit(data)
                }
            }
        }
    }

    private suspend fun insertSingleMovieToDb(data: SingleMoviesEntity) {
        withContext(Dispatchers.IO) {
            localDataSource.insertSingleMovie(data)
        }
    }

    private suspend fun getSingleFromServer(movieId: String): AppState<SingleMoviesEntity> {
        if (!hasInternetConnection())
            return AppState.Error(null, "Network Error")
        return withContext(Dispatchers.IO) {
            val response = remoteDataSource.getSingleMovie(movieId)
            if (response.body() != null) {
                return@withContext AppState.Success(response.body()?.mapToEntity()!!)
            } else {
                return@withContext AppState.Error(null, message = "System Error")
            }
        }
    }

    private suspend fun insertToDb(data: List<PopularMovieEntity>?) {
        withContext(Dispatchers.IO) {
            localDataSource.insertMovie(data!!)
        }
    }

    private suspend fun getPopularFromServer(): AppState<List<PopularMovieEntity>> {
        if (!hasInternetConnection())
            return AppState.Error(null, "Network Error")
        return withContext(Dispatchers.IO) {
            val response = remoteDataSource.getPopularMovies()
            if (response.body() != null) {
                return@withContext AppState.Success(response.body()?.items?.map { it.mapToEntity() }!!)
            } else {
                return@withContext AppState.Error(null, message = "System Error")
            }
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}