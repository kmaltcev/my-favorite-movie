package com.example.myfavoritemovie.data.repository.converters

import android.net.Uri
import com.example.myfavoritemovie.data.source.tmdb.createTMDbAbsoluteImageUri
import com.example.myfavoritemovie.data.source.tmdb.dto.MediaDto
import com.example.myfavoritemovie.data.source.tmdb.dto.SeasonDto
import com.example.myfavoritemovie.data.source.tmdb.getYearFromTMDbDate
import com.example.myfavoritemovie.domain.entity.Movie
import com.example.myfavoritemovie.domain.entity.Series
import com.example.myfavoritemovie.domain.entity.UriImage


fun MediaDto.toMovie() = Movie(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id
)

fun MediaDto.toSeries() = Series(
    name,
    originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id
)

fun SeasonDto.toMovie(relatedSeries: Series) = Movie(
    name,
    relatedSeries.originalName,
    releaseDate?.let { getYearFromTMDbDate(it) } ?: 0,
    buildImage(createTMDbAbsoluteImageUri(poster)),
    externalId = id,
    relatedSeries = relatedSeries,
    episodeCount = episodeCount,
    seasonNumber = seasonNumber
)

fun buildImage(image: Uri?) = image?.let { UriImage(it) }