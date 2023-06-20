package dev.arch3rtemp.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.pokemon.data.repository.PokeRepositoryImpl
import dev.arch3rtemp.pokemon.domain.repository.PokeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokeRepository(pokeRepository: PokeRepositoryImpl): PokeRepository
}