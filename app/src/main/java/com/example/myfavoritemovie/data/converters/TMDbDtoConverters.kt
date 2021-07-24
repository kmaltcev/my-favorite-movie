package com.example.myfavoritemovie.data.converters

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myfavoritemovie.data.source.tmdb.createTMDbAbsoluteImageUri
import com.example.myfavoritemovie.data.source.tmdb.dto.MediaDto
import com.example.myfavoritemovie.data.source.tmdb.dto.SeasonDto
import com.example.myfavoritemovie.data.source.tmdb.getYearFromTMDbDate
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.Series
import com.example.myfavoritemovie.domain.entity.UriImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun MediaDto.toMovie() = Movie(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE)
)

@RequiresApi(Build.VERSION_CODES.O)
fun MediaDto.toSeries() = Series(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE)
)

@RequiresApi(Build.VERSION_CODES.O)
fun SeasonDto.toMovie(relatedSeries: Series) = Movie(
    name,
    relatedSeries.originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    relatedSeries = relatedSeries,
    episodeCount = episodeCount,
    seasonNumber = seasonNumber,
    releaseDate = LocalDate.parse((releaseDate ?: "0000-01-01").toString(), DateTimeFormatter.ISO_DATE)
)

fun buildImage(image: Uri?) = image?.let { UriImage(it) }