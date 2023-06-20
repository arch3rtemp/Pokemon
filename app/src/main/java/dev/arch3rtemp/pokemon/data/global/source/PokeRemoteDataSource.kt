package dev.arch3rtemp.pokemon.data.global.source

import dev.arch3rtemp.pokemon.data.global.dto.PokemonResponse
import dev.arch3rtemp.pokemon.domain.model.Pokemon

interface PokeRemoteDataSource {
    suspend fun fetchPokeList(limit: Int): PokemonResponse
    suspend fun fetchPokemon(id: Int): Pokemon
}