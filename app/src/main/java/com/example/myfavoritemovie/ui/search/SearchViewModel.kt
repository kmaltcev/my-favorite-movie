package com.example.myfavoritemovie.ui.search

import androidx.lifecycle.*
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.usecase.movies.GetChangedMovieUseCase
import com.example.myfavoritemovie.domain.usecase.search.SearchMoviesUseCase
import com.example.myfavoritemovie.ui.ChangesMovieViewModel
import com.example.myfavoritemovie.ui.ext.changeItem
import kotlinx.coroutines.launch
class SearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    getChangedMovieUseCase: GetChangedMovieUseCase
) : ChangesMovieViewModel(getChangedMovieUseCase) {

    private val _searchResults = MutableLiveData<List<Movie>?>()
    val searchResults: LiveData<List<Movie>?> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    fun search(query: String) = viewModelScope.launch {
        _searchResults.value = null
        _isSearching.value = true
        if (query.isNotEmpty()) {
            _searchResults.value = searchMoviesUseCase(query)
        }
        _isSearching.value = false
    }

    override fun onMovieChanged(changedMovie: ChangedMovie) {
        _searchResults.changeItem(changedMovie.oldMovie, changedMovie.newMovie)
    }
}