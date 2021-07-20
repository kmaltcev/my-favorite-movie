package com.example.myfavoritemovie.domain.repository

import androidx.lifecycle.LiveData
import com.example.myfavoritemovie.domain.entity.ChangedMovie

interface MovieChangesRepository  {
    val changedMovie: LiveData<ChangedMovie>
    fun movieWasChanged(changedMovie: ChangedMovie)
}