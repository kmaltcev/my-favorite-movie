package com.example.myfavoritemovie.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.usecase.movies.AddGoodMovieUseCase
import com.example.myfavoritemovie.domain.usecase.movies.AddNeedToWatchMovieUseCase
import com.example.myfavoritemovie.ui.Event
import kotlinx.coroutines.launch

class MovieViewModel(
    private val addGoodMovieUseCase: AddGoodMovieUseCase,
    private val addNeedToWatchMovieUseCase: AddNeedToWatchMovieUseCase
) : ViewModel() {

    private val _onLinkMovie = MutableLiveData<Event<Movie>>()
    val onLinkMovie: LiveData<Event<Movie>> = _onLinkMovie

    private val _openMovieDialog = MutableLiveData<Event<Movie>>()
    val openMovieDialog: LiveData<Event<Movie>> = _openMovieDialog

    fun addGoodMovie(movie: Movie) = viewModelScope.launch {
        addGoodMovieUseCase(movie)
    }

    fun addNeedToWatchMovie(movie: Movie) = viewModelScope.launch {
        addNeedToWatchMovieUseCase(movie)
    }

    fun linkMovie(movie: Movie) = viewModelScope.launch {
        _onLinkMovie.value = Event(movie)
    }

    fun openMovieDialog(movie: Movie) {
        _openMovieDialog.value = Event(movie)
    }
}