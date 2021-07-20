package com.example.myfavoritemovie.domain.usecase.movies

import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class AddNeedToWatchMovieUseCase(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository,
    private val prepareMovieToAddUseCase: PrepareMovieToAddUseCase
) {
    suspend operator fun invoke(movie: Movie) {
        val preparedMovie = prepareMovieToAddUseCase(movie, WatchStatus.NOT_WATCHED)
        moviesRepository.addNeedToWatchMovie(preparedMovie)
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, preparedMovie))
    }
}