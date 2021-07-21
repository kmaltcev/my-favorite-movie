package com.example.myfavoritemovie.domain.actions.search

import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.repository.SearchRepository

class SearchPostersByMovieAction(private val repository: SearchRepository) {
    suspend operator fun invoke(movie: Movie): List<Image> {
        return repository.searchPosters(movie)
    }
}