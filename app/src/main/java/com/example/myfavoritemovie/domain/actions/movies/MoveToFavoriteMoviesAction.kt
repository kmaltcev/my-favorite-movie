package com.example.myfavoritemovie.domain.actions.movies

import android.util.Log
import com.example.myfavoritemovie.domain.entity.ChangedMovie
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.WatchStatus
import com.example.myfavoritemovie.domain.repository.MovieChangesRepository
import com.example.myfavoritemovie.domain.repository.MoviesRepository

class MoveToFavoriteMoviesAction(
    private val moviesRepository: MoviesRepository,
    private val movieChangesRepository: MovieChangesRepository
) {

    suspend operator fun invoke(movie: Movie) {
        try {
            moviesRepository.deleteNeedToWatchMovie(movie)
        } catch (e: NullPointerException) {
            Log.wtf("MY_APP_DELETE", "${movie.name} not removed")
        }
        val updatedMovie = movie.copy(
            watchStatus = WatchStatus.WATCHED,
            dateAdded = generateDateAdded()
        )
        moviesRepository.addFavoriteMovie(updatedMovie)
        movieChangesRepository.movieWasChanged(ChangedMovie(movie, updatedMovie))
    }

    private fun generateDateAdded(): Long = System.currentTimeMillis()
}