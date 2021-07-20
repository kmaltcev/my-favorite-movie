package com.example.myfavoritemovie.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.usecase.movies.GetChangedMovieUseCase

abstract class ChangesMovieViewModel(
    getChangedMovieUseCase: GetChangedMovieUseCase
) : ViewModel() {

    private val changedMovie = getChangedMovieUseCase()
    private val movieChangesObserver = Observer<ChangedMovie> { onMovieChanged(it) }

    init {
        changedMovie.observeForever(movieChangesObserver)
    }

    protected abstract fun onMovieChanged(changedMovie: ChangedMovie)

    override fun onCleared() {
        changedMovie.removeObserver(movieChangesObserver)
    }
}