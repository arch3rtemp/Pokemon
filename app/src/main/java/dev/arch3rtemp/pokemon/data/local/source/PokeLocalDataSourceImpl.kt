package dev.arch3rtemp.pokemon.data.local.source

import dev.arch3rtemp.pokemon.data.local.dao.PokeDao
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import javax.inject.Inject

class PokeLocalDataSourceImpl @Inject constructor(
    private val pokeDao: PokeDao
) : PokeLocalDataSource {
    override suspend fun savePokemons(pokes: List<Pokemon>) {
        pokeDao.insertPokes(pokes)
    }

    override suspend fun updatePokemon(poke: Pokemon) {
        pokeDao.updatePoke(poke)
    }

    override fun loadPokemons(): List<Pokemon> {
        return pokeDao.selectPokes()
    }

    override fun loadPokemon(id: Int): Pokemon {
        return pokeDao.selectPoke(id)
    }
}