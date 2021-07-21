package com.example.myfavoritemovie.domain.actions.search

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchNamesForMovieAction(private val repository: SearchRepository) {
    suspend operator fun invoke(movie: Movie): List<String> {
        return repository.searchNames(movie)
    }
}