package com.example.myfavoritemovie.ui.movies.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.actions.movies.ChangeMovieNameAction
import com.example.myfavoritemovie.domain.actions.movies.ChangeMoviePosterAction
import com.example.myfavoritemovie.domain.actions.movies.DeleteMovieAction
import com.example.myfavoritemovie.domain.actions.movies.MoveToGoodMoviesAction
import com.example.myfavoritemovie.domain.actions.search.SearchNamesForMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchPostersByMovieAction
import com.example.myfavoritemovie.ui.Event
import kotlinx.coroutines.launch

class MovieDialogViewModel(
    private val deleteMovieAction: DeleteMovieAction,
    private val moveToGoodMoviesAction: MoveToGoodMoviesAction,
    private val changeMoviePosterAction: ChangeMoviePosterAction,
    private val searchPostersByMovieAction: SearchPostersByMovieAction,
    private val searchNamesForMovieAction: SearchNamesForMovieAction,
    private val changeMovieNameAction: ChangeMovieNameAction
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie> = _selectedMovie


    private val _posters = MutableLiveData<Event<List<Image>>>()
    val posters: LiveData<Event<List<Image>>> by lazy { _posters }

    private val _names = MutableLiveData<Event<List<String>>>()
    val names: LiveData<Event<List<String>>> = _names

    private val _closeDialog = MutableLiveData<Event<Boolean>>()
    val closeDialog: LiveData<Event<Boolean>> = _closeDialog

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
//        _posters.value = null
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch {
        deleteMovieAction(movie)
        _closeDialog.value = Event(true)
    }

    fun moveToGoodMovies(movie: Movie) = viewModelScope.launch {
        moveToGoodMoviesAction(movie)
        _closeDialog.value = Event(true)
    }

    fun searchPosters() = viewModelScope.launch {
        selectedMovie.value?.let {
            _posters.value = Event(searchPostersByMovieAction(it))
        }
    }

    fun changeMoviePoster(poster: Image) = viewModelScope.launch {
        _selectedMovie.value?.let {
//            _selectedMove.value = changeMoviePosterUseCase(it, poster)
            changeMoviePosterAction(it, poster)
            _closeDialog.value = Event(true)
        }
    }

    fun searchNames(movie: Movie) = viewModelScope.launch {
        _names.value = Event(searchNamesForMovieAction(movie))
    }

    fun changeName(name: String) = viewModelScope.launch {
        _selectedMovie.value?.let {
            _selectedMovie.value = changeMovieNameAction(it, name)
        }
    }
}