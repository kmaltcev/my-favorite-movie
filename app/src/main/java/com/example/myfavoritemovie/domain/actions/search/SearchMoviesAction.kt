package com.example.myfavoritemovie.domain.actions.search

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchMoviesAction(private val repository: SearchRepository) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(query: String): List<Movie> = repository.searchMovies(query)
}