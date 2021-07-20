package com.example.myfavoritemovie.domain.usecase.movies

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class ChangeMovieNameUseCase(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movie: Movie, name: String): Movie {
        val updatedMovie = movie.copy(name = name)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        return updatedMovie
    }
}