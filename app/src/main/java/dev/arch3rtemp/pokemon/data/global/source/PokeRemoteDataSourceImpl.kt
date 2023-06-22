package dev.arch3rtemp.pokemon.data.global.source

import dev.arch3rtemp.pokemon.data.global.api.PokeService
import dev.arch3rtemp.pokemon.data.global.dto.PokemonResponse
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import javax.inject.Inject

class PokeRemoteDataSourceImpl @Inject constructor(
    private val pokeService: PokeService
) : PokeRemoteDataSource {
    override suspend fun fetchPokeList(limit: Int, offset: Int): PokemonResponse {
        return pokeService.getPokemonList(limit, offset)
    }

    override suspend fun fetchPokemon(id: Int): Pokemon {
        return pokeService.getPokemon(id)
    }
}