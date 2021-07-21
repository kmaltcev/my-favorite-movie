package com.example.myfavoritemovie.domain.actions.search

import com.example.myfavoritemovie.domain.entity.Movie

class SearchByMovieAction(private val searchMoviesAction: SearchMoviesAction) {
    suspend operator fun invoke(movie: Movie): List<Movie> {
        val resultsForOriginalName = searchMoviesAction(movie.originalName)
        val resultsForName = searchMoviesAction(movie.name)

        val results = resultsForOriginalName.toMutableList()
        resultsForName.forEach {
            if (it !in resultsForOriginalName) {
                results.add(it)
            }
        }

        return results
    }
}