package com.example.myfavoritemovie.domain.usecase.movies

import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class MoveToGoodMoviesUseCase(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        moviesRepository.deleteNeedToWatchMovie(movie)
        val updatedMovie = movie.copy(
            watchStatus = WatchStatus.WATCHED,
            dateAdded = generateDateAdded()
        )
        moviesRepository.addGoodMovie(updatedMovie)
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, updatedMovie))
    }

    private fun generateDateAdded(): Long = System.currentTimeMillis()
}