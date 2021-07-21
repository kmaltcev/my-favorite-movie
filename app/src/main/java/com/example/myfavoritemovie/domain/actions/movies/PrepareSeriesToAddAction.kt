package com.example.myfavoritemovie.domain.actions.movies

import com.example.myfavoritemovie.domain.entity.Series
import java.util.*

class PrepareSeriesToAddAction {

    operator fun invoke(series: Series): Series {
        return prepareSeries(series)
    }

    private fun prepareSeries(series: Series) = when {
        series.isNotAddedBefore() -> series.copy(internalId = generateInternalId())
        else -> series
    }

    private fun Series.isNotAddedBefore() = this.internalId == null

    private fun generateInternalId(): UUID = UUID.randomUUID()
}