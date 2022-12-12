package ir.mralizade.imdbshow.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.mralizade.imdbshow.data.database.entity.SingleMoviesEntity
import ir.mralizade.imdbshow.domin.SingleMovieUseCaseImpl
import ir.mralizade.imdbshow.utils.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleMovieViewModel @Inject constructor(
    application: Application,
    private val useCase: SingleMovieUseCaseImpl
) : AndroidViewModel(application) {

    val singleMovieResponseFlow = MutableStateFlow<NetworkResponseState<SingleMoviesEntity>>(NetworkResponseState.Loading())

    fun getSingleMovieData(movieId: String) {
        viewModelScope.launch {
            useCase.getSingleMovie(movieId).collect { data ->
                singleMovieResponseFlow.value = data
            }
        }
    }
}