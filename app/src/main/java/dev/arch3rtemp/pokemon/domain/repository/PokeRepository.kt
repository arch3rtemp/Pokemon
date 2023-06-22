package dev.arch3rtemp.pokemon.domain.repository

import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.util.Resource

interface PokeRepository {

    suspend fun getPokes(): Resource<List<Pokemon>>

    suspend fun updatePoke(pokemon: Pokemon)

    fun loadPoke(id: Int): Resource<Pokemon>
}