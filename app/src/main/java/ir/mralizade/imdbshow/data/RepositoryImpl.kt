package ir.mralizade.imdbshow.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.utils.NetworkResponseState
import ir.mralizade.imdbshow.utils.mapToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    @ApplicationContext val context: Context
) : Repository {

    override suspend fun getPopularMovies(
        startPoint: Int,
        isRefresh: Boolean,
        isOnline: Boolean
    ): NetworkResponseState<List<PopularMovieEntity>> {
        localDataSource.getAllMovies(startPoint)
        return remoteDataSource.getPopularMovies(isOnline)
    }

    override suspend fun getSingleMovieData(movieId: String): Flow<NetworkResponseState<SingleMoviesEntity>> {
        val movieData = withContext(Dispatchers.IO) {
            return@withContext localDataSource.getSingleMovie(movieId)
        }
        return flow {
            emit(NetworkResponseState.Success(movieData[0]))
        }
    }

//    private suspend fun insertSingleMovieToDb(data: SingleMoviesEntity) {
//        withContext(Dispatchers.IO) {
//            localDataSource.insertSingleMovie(data)
//        }
//    }
//
//    private suspend fun getSingleFromServer(movieId: String): NetworkResponseState<SingleMoviesEntity> {
//        if (!hasInternetConnection())
//            return NetworkResponseState.Error(null, "Network Error")
//        return withContext(Dispatchers.IO) {
//            val response = remoteDataSource.getSingleMovie(movieId)
//            if (response.body() != null) {
//                return@withContext NetworkResponseState.Success(response.body()?.mapToEntity()!!)
//            } else {
//                return@withContext NetworkResponseState.Error(null, message = "System Error")
//            }
//        }
//    }
//
//    private suspend fun insertToDb(data: List<PopularMovieEntity>?) {
//        withContext(Dispatchers.IO) {
//            localDataSource.insertMovie(data!!)
//        }
//    }

//    private suspend fun getPopularFromServer(): NetworkResponseState<List<PopularMovieEntity>> {
//        return remoteDataSource.getPopularMovies(true)
//        return withContext(Dispatchers.IO) {
//            val response = remoteDataSource.getPopularMovies(true)
//            remoteDataSource.getPopularMovies(true).data?.items?.let { items ->
//                return@withContext NetworkResponseState.Success(items.map { it.mapToEntity() })
//            }
//            if (response.data?.items?.isNotEmpty()) {
//                return@withContext NetworkResponseState.Success(response.body()?.items?.map { it.mapToEntity() }!!)
//            } else {
//                return@withContext NetworkResponseState.Error(null, message = "System Error")
//            }
//        }
//    }


//    private fun hasInternetConnection(): Boolean {
//        val connectivityManager = context
//            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//        val activeNetwork = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
//
//        return when {
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
//            else -> false
//        }
//    }

}