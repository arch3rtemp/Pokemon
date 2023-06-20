package dev.arch3rtemp.pokemon.domain.useCase

import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository
import dev.arch3rtemp.pokemon.util.Resource

class GetPokesUseCase(
    private val pokeRepository: PokeRepository
) {
    suspend operator fun invoke(): Resource<List<Pokemon>> {
        return pokeRepository.getPokes()
    }
}