package dev.arch3rtemp.pokemon.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.pokemon.data.global.source.PokeRemoteDataSource
import dev.arch3rtemp.pokemon.data.global.source.PokeRemoteDataSourceImpl
import dev.arch3rtemp.pokemon.data.local.source.PokeLocalDataSource
import dev.arch3rtemp.pokemon.data.local.source.PokeLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun bindPokeRemoteDataSource(pokeRemoteDataSource: PokeRemoteDataSourceImpl): PokeRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindPokeLocalDataSource(pokeLocalDataSource: PokeLocalDataSourceImpl): PokeLocalDataSource
}