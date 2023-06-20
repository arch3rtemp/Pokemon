package dev.arch3rtemp.pokemon.data.global.dto

import dev.arch3rtemp.pokemon.domain.model.PokePreview

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokePreview>
)