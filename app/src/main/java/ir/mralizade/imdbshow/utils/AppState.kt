package ir.mralizade.imdbshow.utils

sealed class AppState<T>(
    val data: T? = null,
    val message: String? = null
) {

    companion object {
        val APP_STATE_SUCCESS = "APP_STATE_SUCCESS"
        val APP_STATE_ERROR = "APP_STATE_ERROR"
        val APP_STATE_LOADING = "APP_STATE_LOADING"

    }


    class Success<T>(data: T): AppState<T>(data)
    class Error<T>(data: T? = null, message: String?): AppState<T>(data, message)
    class Loading<T>: AppState<T>()

}
