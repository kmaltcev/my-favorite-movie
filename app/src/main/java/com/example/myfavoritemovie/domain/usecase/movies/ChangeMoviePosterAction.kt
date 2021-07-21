package com.example.myfavoritemovie.domain.usecase.movies

import com.example.myfavoritemovie.domain.entity.Image
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class ChangeMoviePosterAction(private val moviesRepository: MoviesRepository) {
    suspend operator fun invoke(movie: Movie, poster: Image): Movie {
        val updatedMovie = movie.copy(poster = poster)

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateGoodMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        moviesRepository.deletePoster(movie)

        return updatedMovie
    }
}