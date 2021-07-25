package com.example.myfavoritemovie.data.converters

import android.net.Uri
import com.example.myfavoritemovie.data.source.tmdb.createTMDbAbsoluteImageUri
import com.example.myfavoritemovie.data.source.tmdb.dto.MediaDto
import com.example.myfavoritemovie.data.source.tmdb.dto.SeasonDto
import com.example.myfavoritemovie.data.source.tmdb.getYearFromTMDbDate
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.Series
import com.example.myfavoritemovie.domain.entity.UriImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle

fun MediaDto.toMovie() = Movie(
    name = name,
    originalName = originalName,
    releaseYear = releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    poster = buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    releaseDate = formatDate(releaseDate),
    overview = overview,
    vote_average = vote_average
)


fun MediaDto.toSeries() = Series(
    name = name,
    originalName = originalName,
    releaseYear = releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    poster = buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    releaseDate = formatDate(releaseDate),
    overview = overview,
    vote_average = vote_average
)

fun SeasonDto.toMovie(relatedSeries: Series) = Movie(
    name = name,
    originalName = relatedSeries.originalName,
    releaseYear = releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    poster = buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    relatedSeries = relatedSeries,
    episodeCount = episodeCount,
    seasonNumber = seasonNumber,
    releaseDate = formatDate(releaseDate),
    overview = overview,
    vote_average = vote_average
)

fun buildImage(image: Uri?) = image?.let { UriImage(it) }

fun formatDate(date: String?): String? {
    return try {
        val localDate = LocalDate.parse(
            if (date.isNullOrEmpty()) "0000-01-01" else date,
            DateTimeFormatter.ISO_DATE
        )
        localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }
    catch (e: DateTimeParseException) {
        date
    }
}