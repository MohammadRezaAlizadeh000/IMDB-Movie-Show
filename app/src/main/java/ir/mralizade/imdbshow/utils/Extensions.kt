package ir.mralizade.imdbshow.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import ir.mralizade.imdbshow.R
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.ui.fragment.PopularMoviesListFragment
import java.lang.Exception

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun AndroidViewModel.hasInternetConnection(): Boolean {
    val connectivityManager = this.getApplication<Application>()
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

fun <T> AndroidViewModel.handleCatchBlock(
    exception: Exception,
    name: String
): AppState<T> {

    log(RESPONSE_TAG, "response = catch $name")
    log(RESPONSE_TAG, exception.stackTrace.contentToString())
    log(RESPONSE_TAG, exception.localizedMessage)
    log(RESPONSE_TAG, exception.suppressedExceptions.toString())
    log(RESPONSE_TAG, exception.message.toString())
    log(RESPONSE_TAG, exception.cause.toString())

    return AppState.Error(message = getApplication<Application>().getString(R.string.connection_error))
}

fun AndroidViewModel.catchBlockLogs(
    exception: Exception,
    name: String
) {
    log(RESPONSE_TAG, "response = catch $name")
    log(RESPONSE_TAG, exception.stackTrace.contentToString())
    log(RESPONSE_TAG, exception.localizedMessage)
    log(RESPONSE_TAG, exception.suppressedExceptions.toString())
    log(RESPONSE_TAG, exception.message.toString())
    log(RESPONSE_TAG, exception.cause.toString())

}

fun log(tag: String, message: String) {
    Log.d(tag, message)
}

fun Fragment.transactionFragment(fragment: Fragment, bundle: Bundle? = null) {
    fragment.arguments = bundle
    requireActivity().supportFragmentManager.beginTransaction()
        .add(R.id.navHostFragmentActivityMain, fragment)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.transactionFragment() {
    supportFragmentManager.beginTransaction()
        .replace(R.id.navHostFragmentActivityMain, PopularMoviesListFragment())
        .addToBackStack(null)
        .commit()
}

fun Context.systemMessages(type: String): String {
    return when(type) {
        SYSTEM_ERROR -> this.getString(R.string.connection_error)
        INTERNET_CONNECTION_ERROR -> this.getString(R.string.check_internet_status)
        else -> {"NULL"}
    }
}

fun <T> SingleMoviesEntity.mapTo(): SingleMoviesEntity {
    TODO()
}