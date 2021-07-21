package com.example.myfavoritemovie.domain.actions.movies

import androidx.lifecycle.LiveData
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository

class GetChangedMovieAction(private val repository: MovieChangesRepository) {
    operator fun invoke(): LiveData<ChangedMovie> = repository.changedMovie
}