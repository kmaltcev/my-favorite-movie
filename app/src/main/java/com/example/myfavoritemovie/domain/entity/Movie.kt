package com.example.myfavoritemovie.domain.entity

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
data class Movie(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Image? = null,

    val movieType: MovieType = MovieType.UNKNOWN,
    val watchStatus: WatchStatus = WatchStatus.UNKNOWN,
    val saveStatus: SaveStatus = SaveStatus.UNKNOWN,

    val externalId: Int? = null,
    val internalId: UUID? = null,
    val dateAdded: Long? = null,

    val relatedSeries: Series? = null,
    val episodeCount: Int? = null,
    val seasonNumber: Int? = null,
    val releaseDate: LocalDate = LocalDate.MIN
)