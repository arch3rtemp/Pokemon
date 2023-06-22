package dev.arch3rtemp.pokemon.data.repository

import dev.arch3rtemp.pokemon.data.global.source.PokeRemoteDataSource
import dev.arch3rtemp.pokemon.data.local.source.PokeLocalDataSource
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository
import dev.arch3rtemp.pokemon.util.Constants
import dev.arch3rtemp.pokemon.util.Resource
import retrofit2.HttpException
import javax.inject.Inject

class PokeRepositoryImpl @Inject constructor(
    private val remoteDataSource: PokeRemoteDataSource,
    private val localDataSource: PokeLocalDataSource
) : PokeRepository {

    override suspend fun getPokes(): Resource<List<Pokemon>> {
        return try {
            val response = remoteDataSource.fetchPokeList(Constants.POKEMONS_LOAD_LIMIT)

            val results = response.results
            val pokemonList: MutableList<Pokemon> = mutableListOf()

            for (result in results) {

                val trimmedUrl = result.url.dropLast(1)
                val id = trimmedUrl.substring(trimmedUrl.lastIndexOf("/") + 1).toInt()
                pokemonList.add(remoteDataSource.fetchPokemon(id))
            }

            localDataSource.savePokemons(pokemonList)
            Resource.Success(localDataSource.loadPokemons())


        } catch (e: HttpException) {
            Resource.Error(e.code(), e.message)
        } catch (e: Exception) {
            return Resource.Exception(e)
        }
    }

    override suspend fun updatePoke(pokemon: Pokemon) {
        localDataSource.updatePokemon(pokemon)
    }

    override fun loadPoke(id: Int): Resource<Pokemon> {
        return try {
            Resource.Success(localDataSource.loadPokemon(id))
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }
}