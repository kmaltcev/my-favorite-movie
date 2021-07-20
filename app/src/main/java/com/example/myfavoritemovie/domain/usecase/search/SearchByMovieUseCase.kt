package com.example.myfavoritemovie.domain.usecase.search

import com.example.myfavoritemovie.domain.entity.Movie

class SearchByMovieUseCase(private val searchMoviesUseCase: SearchMoviesUseCase) {
    suspend operator fun invoke(movie: Movie): List<Movie> {
        val resultsForOriginalName = searchMoviesUseCase(movie.originalName)
        val resultsForName = searchMoviesUseCase(movie.name)

        val results = resultsForOriginalName.toMutableList()
        resultsForName.forEach {
            if (it !in resultsForOriginalName) {
                results.add(it)
            }
        }

        return results
    }
}