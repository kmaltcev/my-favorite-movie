package com.example.myfavoritemovie.domain.actions.movies

import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.Series
import com.example.myfavoritemovie.domain.entity.WatchStatus
import java.util.*

class PrepareMovieToAddAction(
    private val prepareSeriesToAddAction: PrepareSeriesToAddAction
) {

    operator fun invoke(movie: Movie, watchStatus: WatchStatus): Movie {
        val preparedRelatedSeries = movie.relatedSeries?.let { prepareSeriesToAddAction(it) }
        return prepareMovie(movie, watchStatus, preparedRelatedSeries)
    }

    private fun prepareMovie(movie: Movie, watchStatus: WatchStatus, relatedSeries: Series?) =
        when {
            movie.isNotAddedBefore() -> movie.copy(
                watchStatus = watchStatus,
                internalId = generateInternalId(),
                relatedSeries = relatedSeries,
                dateAdded = generateDateAdded(),
            )
            movie.inNotStatus(watchStatus) -> movie.copy(
                watchStatus = watchStatus,
                relatedSeries = relatedSeries
            )
            else -> movie
        }

    private fun Movie.isNotAddedBefore() = this.internalId == null
    private fun Movie.inNotStatus(watchStatus: WatchStatus) = this.watchStatus == watchStatus

    private fun generateInternalId(): UUID = UUID.randomUUID()
    private fun generateDateAdded(): Long = System.currentTimeMillis()
}