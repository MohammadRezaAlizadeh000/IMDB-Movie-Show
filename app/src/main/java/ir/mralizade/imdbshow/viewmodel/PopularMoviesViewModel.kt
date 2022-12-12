package ir.mralizade.imdbshow.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.domin.GetMoviesUseCaseImpl
import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val useCase: GetMoviesUseCaseImpl
) : ViewModel() {

    private var job: Job? = null

    data class PopularMoviesData(
        val message: String? = null,
        override val errorMessage: String? = null,
        override val data: List<PopularMovieEntity>? = null,
        override val page: Int? = null
    ) : BaseViewModelResponseData<List<PopularMovieEntity>>

    private val _popularMoviesResponseFlow = MutableStateFlow(PopularMoviesData())
    val popularMoviesResponseFlow: StateFlow<PopularMoviesData> = _popularMoviesResponseFlow

    fun getPopularMovies(startPoint: Int, isOnline: Boolean) {
        job = viewModelScope.launch {
            _popularMoviesResponseFlow.value =
                when (val response = useCase.getPopularMovies(startPoint, isOnline)) {
                    is NetworkResponseState.Success -> PopularMoviesData(data = response.data)
                    is NetworkResponseState.Loading -> PopularMoviesData(message = response.message)
                    else -> PopularMoviesData(errorMessage = response.message)
                }
        }
    }

    fun clearPopularResponseMessage() {
        _popularMoviesResponseFlow.update {
            it.copy(errorMessage = null, message = null)
        }
    }

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }

//    fun getAllVideo(startPoint: Int) {
//        funName = Exception().stackTrace[0].methodName
//        log(APP_STATE_TAG, funName)
//
//        viewModelScope.launch {
//
//            initLiveData(mutableListOf(), AppState.APP_STATE_LOADING, getApplication<Application>().systemMessages(""))
//
//            try {
//
//                val movieData = getDataFromDatabase(startPoint)
//                if (movieData.isNotEmpty())
//                    initLiveData(movieData, AppState.APP_STATE_SUCCESS, getApplication<Application>().systemMessages(""))
//                else {
//                    val serverData = getDataFromServer()
//                    if (serverData != null) {
//                        val moviesData = convertDataToEntity(serverData)
//                        insertDataToDatabase(moviesData)
//                        val finalData = getDataFromDatabase(startPoint)
//                        initLiveData(finalData, AppState.APP_STATE_SUCCESS, getApplication<Application>().systemMessages(""))
//                    }
//                }
//
//            } catch (e: Exception) {
//                funName = Exception().stackTrace[0].methodName
//                log(APP_STATE_TAG, funName)
//                initLiveData(
//                    mutableListOf(),
//                    AppState.APP_STATE_ERROR,
//                    getApplication<Application>().systemMessages(SYSTEM_ERROR)
//                )
//                catchBlockLogs(e, funName)
//            }
//        }
//    }


//    private suspend fun getDataFromServer(): PopularMoviesResponseModel? {
//        return withContext(Dispatchers.IO) {
//            if (hasInternetConnection()) {
//                val response = useCase.remote.getPopularMovies()
//
//
//                if (isServerResponseSuccess(response.body()))
//                    return@withContext response.body()
//                else
//                    initLiveData(mutableListOf(), AppState.APP_STATE_ERROR, getApplication<Application>().systemMessages(SYSTEM_ERROR))
//            } else {
//                initLiveData(mutableListOf(), AppState.APP_STATE_ERROR, getApplication<Application>().systemMessages(INTERNET_CONNECTION_ERROR))
//            }
//            return@withContext null
//        }
//    }

//    private suspend fun insertDataToDatabase(movieDataList: MutableList<PopularMovieEntity>) {
//        val funName = Exception().stackTrace[0].methodName
//        log(APP_STATE_TAG, funName)
//
//        withContext(Dispatchers.IO) {
//            useCase.local.insertMovie(movieDataList)
//        }
//    }

//    private suspend fun getDataFromDatabase(startPoint: Int): MutableList<PopularMovieEntity> {
//        val funName = Exception().stackTrace[0].methodName
//        log(APP_STATE_TAG, funName)
//
//        return withContext(Dispatchers.IO) {
//            return@withContext useCase.local.getAllMovies(startPoint).toMutableList()
//
//        }
//    }


//    fun refreshPageData() {
//        viewModelScope.launch {
//            clearDatabase()
//        }
//    }
//
//    private suspend fun clearDatabase() {
//        withContext(Dispatchers.IO) {
//            useCase.local.clearDatabase()
//        }
//    }
}