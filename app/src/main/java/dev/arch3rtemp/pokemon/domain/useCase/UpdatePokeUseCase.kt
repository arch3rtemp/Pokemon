package dev.arch3rtemp.pokemon.domain.useCase

import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository

class UpdatePokeUseCase(
    private val pokeRepository: PokeRepository
) {
    suspend operator fun invoke(poke: Pokemon) {
        pokeRepository.updatePoke(poke)
    }
}