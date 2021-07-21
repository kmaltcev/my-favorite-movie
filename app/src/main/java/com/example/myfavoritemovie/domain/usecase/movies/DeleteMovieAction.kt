package com.example.myfavoritemovie.domain.usecase.movies

import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class DeleteMovieAction(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository
) {

    suspend operator fun invoke(movie: Movie) {

        when (movie.watchStatus) {
            WatchStatus.WATCHED -> moviesRepository.deleteGoodMovie(movie)
            WatchStatus.NOT_WATCHED -> moviesRepository.deleteNeedToWatchMovie(movie)
            else -> return
        }

        val deletedMovie = movie.copy(
            internalId = null,
            watchStatus = WatchStatus.UNKNOWN,
            dateAdded = null
        )
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, deletedMovie))
    }
}