package ir.mralizade.imdbshow.utils

sealed class AppState<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): AppState<T>(data)
    class Error<T>(data: T? = null, message: String?): AppState<T>(data, message)
    class Loading<T>: AppState<T>()

}
