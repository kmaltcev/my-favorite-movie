package com.example.myfavoritemovie.domain.actions.movies

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class LinkMovieAction(
    private val moviesRepository: MoviesRepository,
    private val prepareSeriesToAddAction: PrepareSeriesToAddAction
) {
    suspend operator fun invoke(oldMovie: Movie, newMovie: Movie) {
        val updatedMovie = oldMovie.copy(
            originalName = newMovie.originalName,
            poster = newMovie.poster,
            externalId = newMovie.externalId,
            relatedSeries = newMovie.relatedSeries?.let { prepareSeriesToAddAction(it) },
            episodeCount = newMovie.episodeCount,
            seasonNumber = newMovie.seasonNumber
        )

        if (updatedMovie.watchStatus == WatchStatus.WATCHED) {
            moviesRepository.updateFavoriteMovie(updatedMovie)
        } else {
            moviesRepository.updateNeedToWatchMovie(updatedMovie)
        }

        moviesRepository.deletePoster(oldMovie)
    }
}