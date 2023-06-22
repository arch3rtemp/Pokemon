package dev.arch3rtemp.pokemon.data.global.api

import dev.arch3rtemp.pokemon.data.global.dto.PokemonResponse
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    @GET("pokemon/")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemon(
        @Path("id") id: Int
    ): Pokemon
}