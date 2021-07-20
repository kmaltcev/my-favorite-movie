package com.example.myfavoritemovie.ui.movies.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.usecase.movies.ChangeMovieNameUseCase
import com.example.myfavoritemovie.domain.usecase.movies.ChangeMoviePosterUseCase
import com.example.myfavoritemovie.domain.usecase.movies.DeleteMovieUseCase
import com.example.myfavoritemovie.domain.usecase.movies.MoveToGoodMoviesUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchNamesForMovieUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchPostersByMovieUseCase
import com.example.myfavoritemovie.ui.Event
import kotlinx.coroutines.launch

class MovieDialogViewModel(
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val moveToGoodMoviesUseCase: MoveToGoodMoviesUseCase,
    private val changeMoviePosterUseCase: ChangeMoviePosterUseCase,
    private val searchPostersByMovieUseCase: SearchPostersByMovieUseCase,
    private val searchNamesForMovieUseCase: SearchNamesForMovieUseCase,
    private val changeMovieNameUseCase: ChangeMovieNameUseCase
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
        deleteMovieUseCase(movie)
        _closeDialog.value = Event(true)
    }

    fun moveToGoodMovies(movie: Movie) = viewModelScope.launch {
        moveToGoodMoviesUseCase(movie)
        _closeDialog.value = Event(true)
    }

    fun searchPosters() = viewModelScope.launch {
        selectedMovie.value?.let {
            _posters.value = Event(searchPostersByMovieUseCase(it))
        }
    }

    fun changeMoviePoster(poster: Image) = viewModelScope.launch {
        _selectedMovie.value?.let {
//            _selectedMove.value = changeMoviePosterUseCase(it, poster)
            changeMoviePosterUseCase(it, poster)
            _closeDialog.value = Event(true)
        }
    }

    fun searchNames(movie: Movie) = viewModelScope.launch {
        _names.value = Event(searchNamesForMovieUseCase(movie))
    }

    fun changeName(name: String) = viewModelScope.launch {
        _selectedMovie.value?.let {
            _selectedMovie.value = changeMovieNameUseCase(it, name)
        }
    }
}