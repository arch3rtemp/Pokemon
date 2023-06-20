package dev.arch3rtemp.pokemon.domain.model

import com.squareup.moshi.Json

data class StatResponse(
    val stat: Stat,
    @Json(name = "base_stat")
    val baseStat: Int
)