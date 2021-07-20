package com.example.myfavoritemovie.domain.usecase.movies

import androidx.lifecycle.LiveData
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository

class GetChangedMovieUseCase(private val repository: MovieChangesRepository) {

    operator fun invoke(): LiveData<ChangedMovie> = repository.changedMovie
}