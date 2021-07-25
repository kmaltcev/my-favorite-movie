package com.example.myfavoritemovie.data.source.firebase.dto

data class FirebaseMovieDto(
    var name: String = "",
    var originalName: String = "",
    var releaseYear: Int = 0,
    var poster: String? = null,

    var movieType: Int = MEDIA_TYPE_UNKNOWN,
    var watchStatus: Int = WATCH_STATUS_UNKNOWN,
    var saveStatus: Int = SAVE_STATUS_UNKNOWN,

    var externalId: Int? = null,
    var internalId: String = "",
    var dateAdded: Long = 0,

    var relatedSeries: FirebaseSeriesDto? = null,
    var episodeCount: Int? = null,
    var seasonNumber: Int? = null,

    var releaseDate: String? = "",
    val overview: String = "",
    val vote_average: Float = 0.0f
)