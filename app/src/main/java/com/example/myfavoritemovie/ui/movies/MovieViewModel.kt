package com.example.myfavoritemovie.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.ui.ext.Event

class MovieViewModel : ViewModel() {

    private val _openMovieDialog = MutableLiveData<Event<Movie>>()
    val openMovieDialog: LiveData<Event<Movie>> = _openMovieDialog

    fun openMovieDialog(movie: Movie) {
        _openMovieDialog.value = Event(movie)
    }
}