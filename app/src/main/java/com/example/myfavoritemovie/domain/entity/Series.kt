package com.example.myfavoritemovie.domain.entity

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
data class Series(
    val name: String,
    val originalName: String,
    val releaseYear: Int,
    val poster: Image? = null,
    val internalId: UUID? = null,
    val externalId: Int? = null,
    val releaseDate: LocalDate? = null
)