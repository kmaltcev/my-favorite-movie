package com.example.myfavoritemovie.domain.repository

import androidx.lifecycle.MutableLiveData
import com.example.myfavoritemovie.domain.entity.ChangedMovie

class MovieChangesRepository  {
    val changedMovie = MutableLiveData<ChangedMovie>()

    fun movieWasChanged(changedMovie: ChangedMovie) {
        this.changedMovie.value = changedMovie
    }
}