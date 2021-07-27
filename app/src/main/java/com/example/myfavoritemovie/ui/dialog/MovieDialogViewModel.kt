package com.example.myfavoritemovie.ui.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.actions.movies.AddFavoriteMovieAction
import com.example.myfavoritemovie.domain.actions.movies.AddNeedToWatchMovieAction
import com.example.myfavoritemovie.domain.actions.movies.DeleteMovieAction
import com.example.myfavoritemovie.domain.actions.movies.MoveToFavoriteMoviesAction
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.ext.Event
import kotlinx.coroutines.launch

class MovieDialogViewModel(
    private val deleteMovieAction: DeleteMovieAction,
    private val addFavoriteMoviesAction: AddFavoriteMovieAction,
    private val addNeedToWatchMovieAction: AddNeedToWatchMovieAction,
    private val moveToFavoriteMoviesAction: MoveToFavoriteMoviesAction
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> = _selectedMovie

    private val _buttonClicked = MutableLiveData<Boolean>()
    val buttonClicked: LiveData<Boolean> = _buttonClicked

    private val _closeDialog = MutableLiveData<Event<Boolean>>()
    val closeDialog: LiveData<Event<Boolean>> = _closeDialog

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        _buttonClicked.value = true
        deleteMovieAction(movie)
        _closeDialog.value = Event(true)
        _buttonClicked.value = false

    }

    fun addToFavoriteMovies(movie: Movie) = viewModelScope.launch {
        _buttonClicked.value = true
        addFavoriteMoviesAction(movie)
        _closeDialog.value = Event(true)
        _buttonClicked.value = false
    }

    fun addToNeedToWatchMovies(movie: Movie) = viewModelScope.launch {
        _buttonClicked.value = true
        addNeedToWatchMovieAction(movie)
        _closeDialog.value = Event(true)
        _buttonClicked.value = false
    }

    fun moveToFavoriteMovies(movie: Movie) = viewModelScope.launch {
        _buttonClicked.value = true
        moveToFavoriteMoviesAction(movie)
        _closeDialog.value = Event(true)
        _buttonClicked.value = false
    }
}