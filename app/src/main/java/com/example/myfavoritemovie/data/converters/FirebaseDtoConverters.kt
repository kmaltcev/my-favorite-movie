package com.example.myfavoritemovie.data.converters

import android.net.Uri
import com.example.myfavoritemovie.data.source.firebase.dto.*
import com.example.myfavoritemovie.domain.entity.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

private val storageReference by lazy { Firebase.storage.reference }


fun parseMovie(snapshot: DataSnapshot): Movie =
    snapshot.getValue(FirebaseMovieDto::class.java)!!.toMovie()


fun buildFirebaseMovieDto(movie: Movie): FirebaseMovieDto =
    with(movie) {
        FirebaseMovieDto(
            name = name,
            originalName = originalName,
            releaseYear = releaseYear,
            poster = poster.toString(),
            movieType = movieType.toFirebaseConst(),
            watchStatus = watchStatus.toFirebaseConst(),
            saveStatus = saveStatus.toFirebaseConst(),
            externalId = externalId,
            internalId = internalId.toString(),
            dateAdded = dateAdded!!,
            relatedSeries = relatedSeries?.let { buildFirebaseSeriesDto(it) },
            episodeCount = episodeCount,
            seasonNumber = seasonNumber,
            releaseDate = formatDate(releaseDate),
            overview = overview ?: "",
            vote_average = vote_average
        )
    }


fun buildFirebaseSeriesDto(series: Series): FirebaseSeriesDto =
    with(series) {
        FirebaseSeriesDto(
            name = name,
            originalName = originalName,
            releaseYear = releaseYear,
            poster = poster.toString(),
            internalId = internalId.toString(),
            externalId = externalId,
            releaseDate = formatDate(releaseDate),
            overview = overview,
            vote_average = vote_average
        )
    }

private fun MovieType.toFirebaseConst() = when (this) {
    MovieType.MOVIE -> MEDIA_TYPE_MOVIE
    MovieType.TV -> MEDIA_TYPE_TV
    MovieType.ANIME -> MEDIA_TYPE_ANIME
    MovieType.UNKNOWN -> MEDIA_TYPE_UNKNOWN
    MovieType.CARTOON -> MEDIA_TYPE_CARTOON
}

private fun WatchStatus.toFirebaseConst() = when (this) {
    WatchStatus.UNKNOWN -> WATCH_STATUS_UNKNOWN
    WatchStatus.WATCHED -> WATCH_STATUS_WATCHED
    WatchStatus.NOT_WATCHED -> WATCH_STATUS_NOT_WATCHED
}

private fun SaveStatus.toFirebaseConst() = when (this) {
    SaveStatus.UNKNOWN -> SAVE_STATUS_UNKNOWN
    SaveStatus.SAVED -> SAVE_STATUS_SAVED
    SaveStatus.NOT_SAVED -> SAVE_STATUS_NOT_SAVED
}


fun FirebaseMovieDto.toMovie(): Movie = Movie(
    name = name,
    originalName = originalName,
    releaseYear = releaseYear,
    poster = poster?.let { buildImage(it) },
    movieType = movieType.toMovieType(),
    watchStatus = watchStatus.toWatchStatus(),
    saveStatus = saveStatus.toSaveStatus(),
    externalId = externalId,
    internalId = UUID.fromString(internalId),
    dateAdded = dateAdded,
    relatedSeries = relatedSeries?.toSeries(),
    episodeCount = episodeCount,
    seasonNumber = seasonNumber,
    releaseDate = formatDate(releaseDate),
    overview = overview,
    vote_average = vote_average
)


fun FirebaseSeriesDto.toSeries(): Series = Series(
    name = name,
    originalName = originalName,
    releaseYear = releaseYear,
    poster = poster?.let { buildImage(it) },
    internalId = UUID.fromString(internalId),
    externalId = externalId,
    releaseDate = formatDate(releaseDate),
    overview = overview,
    vote_average = vote_average
)

private fun buildImage(image: String): Image = if (image.contains("http")) {
    UriImage(Uri.parse(image))
} else {
    StorageReferenceImage(storageReference.child(image))
}

private fun Int.toMovieType() = when (this) {
    MEDIA_TYPE_TV -> MovieType.TV
    MEDIA_TYPE_MOVIE -> MovieType.MOVIE
    MEDIA_TYPE_ANIME -> MovieType.ANIME
    MEDIA_TYPE_CARTOON -> MovieType.CARTOON
    else -> MovieType.UNKNOWN
}

private fun Int.toWatchStatus() = when (this) {
    WATCH_STATUS_WATCHED -> WatchStatus.WATCHED
    WATCH_STATUS_NOT_WATCHED -> WatchStatus.NOT_WATCHED
    else -> WatchStatus.UNKNOWN
}

private fun Int.toSaveStatus() = when (this) {
    SAVE_STATUS_SAVED -> SaveStatus.SAVED
    SAVE_STATUS_NOT_SAVED -> SaveStatus.NOT_SAVED
    else -> SaveStatus.UNKNOWN
}
