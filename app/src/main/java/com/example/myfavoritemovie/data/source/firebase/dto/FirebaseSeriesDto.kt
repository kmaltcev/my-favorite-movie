package com.example.myfavoritemovie.data.source.firebase.dto

data class FirebaseSeriesDto(
    var name: String = "",
    var originalName: String = "",
    var releaseYear: Int = 0,
    var poster: String? = null,

    var internalId: String = "",
    var externalId: Int? = null,
    var releaseDate: String? = "",
    val overview: String = "",
    val vote_average: Float = 0.0f
)