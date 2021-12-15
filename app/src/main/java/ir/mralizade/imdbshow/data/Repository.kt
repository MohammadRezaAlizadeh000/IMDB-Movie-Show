package ir.mralizade.imdbshow.data

import ir.mralizade.imdbshow.utils.AppState
import javax.inject.Inject

class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource

    val l = AppState.Loading<Int>()

    fun getPopularMovies(res: RequestResponse<AppState<*>>) {
        res.getFromLocal(l)

        res.getFromRemote(l)
    }

}