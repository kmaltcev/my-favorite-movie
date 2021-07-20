package com.example.myfavoritemovie.domain.usecase.search

import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchPostersByMovieUseCase(private val repository: SearchRepository) {
    suspend operator fun invoke(movie: Movie): List<Image> {
        return repository.searchPosters(movie)
    }
}