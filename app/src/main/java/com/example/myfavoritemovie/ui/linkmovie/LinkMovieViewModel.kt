package com.example.myfavoritemovie.ui.linkmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.usecase.movies.GetChangedMovieUseCase
import com.example.myfavoritemovie.domain.usecase.movies.LinkMovieUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchByMovieUseCase
import com.example.myfavoritemovie.ui.ChangesMovieViewModel
import com.example.myfavoritemovie.ui.ext.changeItem
import kotlinx.coroutines.launch

class LinkMovieViewModel(
    private val searchByMovieUseCase: SearchByMovieUseCase,
    private val linkMovieUseCase: LinkMovieUseCase,
    getChangedMovieUseCase: GetChangedMovieUseCase
) : ChangesMovieViewModel(getChangedMovieUseCase) {

    private val _originalMovie = MutableLiveData<Movie>()
    val originalMovie: LiveData<Movie> = _originalMovie

    private val _searchResults = MutableLiveData<List<Movie>?>()
    val searchResults: LiveData<List<Movie>?> = _searchResults

    fun searchByMovie(movie: Movie) = viewModelScope.launch {
        _searchResults.value = null
        _originalMovie.value = movie
        _searchResults.value = searchByMovieUseCase(movie)
    }

    fun linkWithMovie(selectedMovie: Movie) = viewModelScope.launch {
        originalMovie.value?.let {
            linkMovieUseCase(it, selectedMovie)
        }
    }

    override fun onMovieChanged(changedMovie: ChangedMovie) {
        _searchResults.changeItem(changedMovie.oldMovie, changedMovie.newMovie)
    }

}