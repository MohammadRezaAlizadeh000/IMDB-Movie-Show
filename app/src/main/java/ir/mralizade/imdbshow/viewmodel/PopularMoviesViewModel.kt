package ir.mralizade.imdbshow.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mralizade.imdbshow.data.database.entity.PopularMovieEntity
import ir.mralizade.imdbshow.domin.GetMoviesUseCaseImpl
import ir.mralizade.imdbshow.model.popularmovies.PopularMoviesResponseModel
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    application: Application,
    private val useCase: GetMoviesUseCaseImpl
) : AndroidViewModel(application) {

    var funName = ""
    val popularMoviesResponseFlow = MutableStateFlow<AppState<List<PopularMovieEntity>>>(AppState.Loading())

    fun getPopularMovies(startPoint: Int) {
        viewModelScope.launch {
            useCase.getPopularMovies(startPoint).collect { data ->
                popularMoviesResponseFlow.value = data
            }
        }
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

    private suspend fun isServerResponseSuccess(response: PopularMoviesResponseModel?) =
        withContext(Dispatchers.Default) { return@withContext response != null }

    private suspend fun convertDataToEntity(
        mainData: PopularMoviesResponseModel
    ): MutableList<PopularMovieEntity> {
        funName = Exception().stackTrace[0].methodName
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

    private suspend fun initLiveData(
        finalData: MutableList<PopularMovieEntity>,
        appState: String,
        message: String
    ) {
        val funName = Exception().stackTrace[0].methodName
        log(APP_STATE_TAG, funName)

        withContext(Dispatchers.Main) {
            when (appState) {
                AppState.APP_STATE_SUCCESS -> {
                    popularMoviesResponseFlow.emit(AppState.Success(finalData))
                }
                AppState.APP_STATE_ERROR -> {
                    popularMoviesResponseFlow.emit(AppState.Error(mutableListOf(), message))
                }
                AppState.APP_STATE_LOADING -> {
                    popularMoviesResponseFlow.emit(AppState.Error(mutableListOf(), message))
                }
            }
        }
    }

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