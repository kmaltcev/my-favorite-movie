package com.example.myfavoritemovie.ui.search

import androidx.lifecycle.*
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.actions.movies.GetChangedMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchMoviesAction
import com.example.myfavoritemovie.ui.ChangesMovieViewModel
import com.example.myfavoritemovie.ui.ext.changeItem
import kotlinx.coroutines.launch
class SearchViewModel(
    private val searchMoviesAction: SearchMoviesAction,
    getChangedMovieAction: GetChangedMovieAction
) : ChangesMovieViewModel(getChangedMovieAction) {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    private val _isSearching = MutableLiveData<Boolean>()
    val isSearching: LiveData<Boolean> = _isSearching

    fun search(query: String) = viewModelScope.launch {
        _searchResults.value = null
        _isSearching.value = true
        if (query.isNotEmpty()) {
            _searchResults.value = searchMoviesAction(query)
        }
        _isSearching.value = false
    }

    override fun onMovieChanged(changedMovie: ChangedMovie) {
        _searchResults?.changeItem(changedMovie.oldMovie, changedMovie.newMovie)
    }
}