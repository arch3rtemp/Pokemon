package dev.arch3rtemp.pokemon.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.arch3rtemp.pokemon.data.repository.PokeRepositoryImpl
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository
import dev.arch3rtemp.pokemon.domain.useCase.GetPokesUseCase
import dev.arch3rtemp.pokemon.domain.useCase.LoadPokeUseCase
import dev.arch3rtemp.pokemon.domain.useCase.UpdatePokeUseCase

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetPokeUseCase(pokeRepository: PokeRepository): GetPokesUseCase {
        return GetPokesUseCase(pokeRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideLoadPokeUseCase(pokeRepository: PokeRepository): LoadPokeUseCase {
        return LoadPokeUseCase(pokeRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideUpdatePokeUseCase(pokeRepository: PokeRepositoryImpl): UpdatePokeUseCase {
        return UpdatePokeUseCase(pokeRepository)
    }
}