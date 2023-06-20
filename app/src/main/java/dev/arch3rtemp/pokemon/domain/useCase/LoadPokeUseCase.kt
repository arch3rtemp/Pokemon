package dev.arch3rtemp.pokemon.domain.useCase

import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository
import dev.arch3rtemp.pokemon.util.Resource

class LoadPokeUseCase(
    private val pokeRepository: PokeRepository
) {
    operator fun invoke(id: Int): Resource<Pokemon> {
        return pokeRepository.loadPoke(id)
    }
}