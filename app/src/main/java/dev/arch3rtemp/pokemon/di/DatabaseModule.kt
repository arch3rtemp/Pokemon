package dev.arch3rtemp.pokemon.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.pokemon.data.local.converter.RoomJsonConverter
import dev.arch3rtemp.pokemon.data.local.dao.PokeDao
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Database(entities = [Pokemon::class], version = 1, exportSchema = false)
    @TypeConverters(RoomJsonConverter::class)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun pokeDao(): PokeDao
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context, roomJsonConverter: RoomJsonConverter) = Room
        .databaseBuilder(appContext, AppDatabase::class.java,"db")
        .fallbackToDestructiveMigration()
        .addTypeConverter(roomJsonConverter)
        .build()

    @Singleton
    @Provides
    fun providePokeDao(database: AppDatabase): PokeDao {
        return database.pokeDao()
    }
}