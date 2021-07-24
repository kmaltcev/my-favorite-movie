package com.example.myfavoritemovie.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myfavoritemovie.domain.actions.movies.GetChangedMovieAction
import com.example.myfavoritemovie.domain.actions.search.SearchUpcomingAction
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.ChangesMovieViewModel
import com.example.myfavoritemovie.ui.ext.changeItem
import kotlinx.coroutines.launch

class UpcomingViewModel(
    private val searchUpcomingAction: SearchUpcomingAction,
    getChangedMovieAction: GetChangedMovieAction
) : ChangesMovieViewModel(getChangedMovieAction) {

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    fun search(query: String) = viewModelScope.launch {
        _upcomingMovies.value = null
        if (query.isNotEmpty()) {
            _upcomingMovies.value = searchUpcomingAction(query)
        }
    }

    override fun onMovieChanged(changedMovie: ChangedMovie) {
        _upcomingMovies?.changeItem(changedMovie.oldMovie, changedMovie.newMovie)
    }
}