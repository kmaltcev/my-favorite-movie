package com.example.myfavoritemovie.domain.usecase.search

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchNamesForMovieUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(movie: Movie): List<String> {
        return repository.searchNames(movie)
    }
}