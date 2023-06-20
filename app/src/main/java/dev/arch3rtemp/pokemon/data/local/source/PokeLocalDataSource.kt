package dev.arch3rtemp.pokemon.data.local.source

import dev.arch3rtemp.pokemon.domain.model.Pokemon

interface PokeLocalDataSource {

    suspend fun savePokemons(pokes: List<Pokemon>)

    fun loadPokemons(): List<Pokemon>

    fun loadPokemon(id: Int): Pokemon
}