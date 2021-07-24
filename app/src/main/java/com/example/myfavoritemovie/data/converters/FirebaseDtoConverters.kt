package com.example.myfavoritemovie.data.converters

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myfavoritemovie.data.source.firebase.dto.*
import com.example.myfavoritemovie.domain.entity.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

private val storageReference by lazy { Firebase.storage.reference }

@RequiresApi(Build.VERSION_CODES.O)
fun parseMovie(snapshot: DataSnapshot): Movie =
    snapshot.getValue(FirebaseMovieDto::class.java)!!.toMovie()

fun buildFirebaseMovieDto(movie: Movie): FirebaseMovieDto =
    with(movie) {
        FirebaseMovieDto(
            name,
            originalName,
            releaseYear,
            poster.toString(),
            movieType.toFirebaseConst(),
            watchStatus.toFirebaseConst(),
            saveStatus.toFirebaseConst(),
            externalId,
            internalId.toString(),
            dateAdded!!,
            relatedSeries?.let { buildFirebaseSeriesDto(it) },
            episodeCount,
            seasonNumber,
            releaseDate.toString()
        )
    }

fun buildFirebaseSeriesDto(series: Series): FirebaseSeriesDto =
    with(series) {
        FirebaseSeriesDto(
            name,
            originalName,
            releaseYear,
            poster.toString(),
            internalId.toString(),
            externalId,
            releaseDate.toString()
            //releaseDate.let { it?.toString() ?: "" }
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

@RequiresApi(Build.VERSION_CODES.O)
fun FirebaseMovieDto.toMovie(): Movie = Movie(
    name, originalName, releaseYear,
    poster?.let { buildImage(it) },
    movieType.toMovieType(),
    watchStatus.toWatchStatus(),
    saveStatus.toSaveStatus(),
    externalId,
    UUID.fromString(internalId),
    dateAdded,
    relatedSeries?.toSeries(),
    episodeCount,
    seasonNumber,
    LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE)
)

@RequiresApi(Build.VERSION_CODES.O)
fun FirebaseSeriesDto.toSeries(): Series = Series(
    name,
    originalName,
    releaseYear,
    poster?.let { buildImage(it) },
    UUID.fromString(internalId),
    externalId,
    LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE)
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