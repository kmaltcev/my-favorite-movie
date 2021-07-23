package com.example.myfavoritemovie.domain.actions.search

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchUpcomingAction(private val repository: SearchRepository) {
    suspend operator fun invoke(region: String): List<Movie> = repository.searchUpcomingMovies(region)
}