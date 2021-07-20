package com.example.myfavoritemovie.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository

class MovieChangesRepositoryImpl : MovieChangesRepository {

    override val changedMovie = MutableLiveData<ChangedMovie>()

    override fun movieWasChanged(changedMovie: ChangedMovie) {
        this.changedMovie.value = changedMovie
    }
}