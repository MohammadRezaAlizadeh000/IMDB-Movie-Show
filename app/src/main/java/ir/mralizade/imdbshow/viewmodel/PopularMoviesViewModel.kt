package ir.mralizade.imdbshow.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mralizade.imdbshow.R
import ir.mralizade.imdbshow.data.Repository
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val popularMoviesResponse = MutableLiveData<AppState<MutableList<PopularMovieEntity>>>()

    fun getAllVideo(startPoint: Int) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        popularMoviesResponse.value = AppState.Loading()

        viewModelScope.launch {
            try {
                val dataList = getDataFromDatabase(startPoint)

                if (dataList.isNotEmpty()) initLiveData(dataList)
                else callRequest()

            } catch (e: Exception) {
                popularMoviesResponse.value = handleCatchBlock(e, funName)
            }
        }
    }

    private suspend fun callRequest() {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        if (hasInternetConnection()) {
            val response = repository.remote.getPopularMovies()
            if (response.body() != null) {
                viewModelScope.launch(Dispatchers.IO) {
                    val entityData = convertDataToEntity(response.body()!!)
                    insertDataToDatabase(entityData)
                    val finalData = getDataFromDatabase(0)
                    initLiveData(finalData)
                }
            } else
                popularMoviesResponse.value = AppState.Error(message = getApplication<Application>().getString(
                    R.string.connection_error))
        } else {
            popularMoviesResponse.value = handleNoInternetConnection(funName)
        }
    }

    private suspend fun convertDataToEntity(
        mainData: PopularMoviesResponseModel
    ): MutableList<PopularMovieEntity> {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        return withContext(Dispatchers.Default) {
            val dataList = mutableListOf<PopularMovieEntity>()

            mainData.items?.forEach { data ->
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

    private suspend fun insertDataToDatabase(movieDataList: MutableList<PopularMovieEntity>) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        withContext(Dispatchers.IO) {
            repository.local.insertMovie(movieDataList)
        }
    }

    private suspend fun getDataFromDatabase(startPoint: Int): MutableList<PopularMovieEntity> {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        return withContext(Dispatchers.IO) {
            return@withContext repository.local.getAllMovies(startPoint).toMutableList()

        }
    }

    private suspend fun initLiveData(finalData: MutableList<PopularMovieEntity>) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        withContext(Dispatchers.Main) {
            popularMoviesResponse.value = AppState.Success(finalData)
        }
    }

    fun refreshPageData() {
        viewModelScope.launch {
            clearDatabase()
        }
    }

    private suspend fun clearDatabase() {
        withContext(Dispatchers.IO) {
            repository.local.clearDatabase()
        }
    }
}