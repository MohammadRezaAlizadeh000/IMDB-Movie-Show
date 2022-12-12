package ir.mralizade.imdbshow.utils

import ir.mralizade.imdbshow.data.mapper.BaseMapper
import retrofit2.Response

sealed class NetworkResponseState<T>(
    val data: T? = null,
    val message: String? = null
) {

    companion object {
        val APP_STATE_SUCCESS = "APP_STATE_SUCCESS"
        val APP_STATE_ERROR = "APP_STATE_ERROR"
        val APP_STATE_LOADING = "APP_STATE_LOADING"

    }


    class Success<T>(data: T?, message: String? = null): NetworkResponseState<T>(data)
    class Error<T>(data: T? = null, message: String?): NetworkResponseState<T>(data, message)
    class Loading<T>: NetworkResponseState<T>()

}

suspend fun <T, R> Response<T>.toNetWorkResponseState(
    isOnline: Boolean,
    mapper: BaseMapper<T, R>
): NetworkResponseState<R> {
    try {
        if (!isOnline)
            return NetworkResponseState.Error(null, "Network Error")

        return if (this.body() != null) {
            when (this.code()) {
                404 -> NetworkResponseState.Error(null, message = "Please try later")
                402 -> NetworkResponseState.Error(null, message = "Authentication Error")
                in 300..399 -> NetworkResponseState.Error(null, message = this.message())
                in 400..499 -> NetworkResponseState.Error(null, message = this.message())
                in 500..599 -> NetworkResponseState.Error(null, message = "Server note responding")
                else -> NetworkResponseState.Success(
                    mapper.mapTo(this.body()),
                    message = "collect data successful"
                )
            }
        } else {
            NetworkResponseState.Error(null, message = "System Error")
        }
    } catch (e: Exception) {
        return NetworkResponseState.Error(null, message = e.message)
    }
}
