package com.example.myfavoritemovie.ui.ext

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myfavoritemovie.domain.actions.movies.GetChangedMovieAction
import com.example.myfavoritemovie.domain.entity.ChangedMovie

abstract class ChangesMovieViewModel(
    getChangedMovieAction: GetChangedMovieAction
) : ViewModel() {

    private val changedMovie = getChangedMovieAction()
    private val movieChangesObserver = Observer<ChangedMovie> { onMovieChanged(it) }

    init {
        changedMovie.observeForever(movieChangesObserver)
    }

    protected abstract fun onMovieChanged(changedMovie: ChangedMovie)

    override fun onCleared() {
        changedMovie.removeObserver(movieChangesObserver)
    }
}