package com.example.myfavoritemovie.domain.actions.search

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchMoviesAction(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String): List<Movie> = repository.searchMovies(query)
}