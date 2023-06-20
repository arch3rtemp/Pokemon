package dev.arch3rtemp.pokemon.domain.model

import com.squareup.moshi.Json

data class SpriteResponse(
    @Json(name = "front_default")
    val frontDefault: String
)