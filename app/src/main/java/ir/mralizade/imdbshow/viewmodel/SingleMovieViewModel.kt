package ir.mralizade.imdbshow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mralizade.imdbshow.R
import ir.mralizade.imdbshow.data.Repository
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.model.singlemovie.SingleMovieResponseModel
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val singleMovieResponse = MutableLiveData<AppState<SingleMoviesEntity>>()

    fun getMovieData(movieId: String) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        initLiveData(AppState.Loading())

        viewModelScope.launch {
            try {
                val movieData = getDataFromDatabase(movieId)
                if (movieData != null) {
                    initLiveData(AppState.Success(movieData))
                } else {
                    callRequest(movieId)
                }
            } catch (e: Exception) {
                initLiveData(handleCatchBlock(e, funName))
            }
        }
    }

    private suspend fun getDataFromDatabase(movieId: String): SingleMoviesEntity? {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        return withContext(Dispatchers.IO) {
            return@withContext repository.local.getSingleMovie(movieId)
        }
    }

    private fun convertDataToEntity(rawData: SingleMovieResponseModel): SingleMoviesEntity? {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        return SingleMoviesEntity(
            rawData.id!!,
            rawData.fullTitle!!,
            rawData.image!!,
            rawData.imDbRating!!,
            rawData.imDbRatingVotes!!,
            rawData.companies!!,
            rawData.countries!!,
            rawData.languages!!,
            rawData.genres!!,
            rawData.stars!!,
            rawData.year!!,
            rawData.writers!!,
            rawData.directors!!,
            rawData.plot!!
        )
    }

    private suspend fun callRequest(movieId: String) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        if (hasInternetConnection()) {
            try {
                val response = withContext(Dispatchers.IO) {
                    return@withContext repository.remote.getSingleMovie(movieId)
                }
                log(PUBLIC_TAG, response.raw().request().url().toString())
                if (response.body() != null) {
                    val finalData = convertDataToEntity(response.body()!!)
                    insertToDatabase(finalData!!)
                    val movieData = getDataFromDatabase(movieId)!!
                    initLiveData(AppState.Success(movieData))
                } else initLiveData(
                    AppState.Error(
                        message = getApplication<Application>().getString(
                            R.string.connection_error
                        )
                    )
                )
            } catch (e: Exception) {
                initLiveData(handleCatchBlock(e, funName))
            }
        } else {
            initLiveData(AppState.Error(message = getApplication<Application>().getString(R.string.check_internet_status)))
        }
    }

    private suspend fun insertToDatabase(movieData: SingleMoviesEntity) {
        withContext(Dispatchers.IO) {
            repository.local.insertSingleMovie(movieData)
        }
    }


    private fun initLiveData(appState: AppState<SingleMoviesEntity>) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        singleMovieResponse.value = appState
    }

}