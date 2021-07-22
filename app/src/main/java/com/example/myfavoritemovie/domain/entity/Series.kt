package com.example.myfavoritemovie.domain.entity

import java.util.*

data class Series(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Image? = null,
    val internalId: UUID? = null,
    val externalId: Int? = null,
    val releaseDate: String = ""
)