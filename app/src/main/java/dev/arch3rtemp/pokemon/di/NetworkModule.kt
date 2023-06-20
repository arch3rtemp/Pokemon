package dev.arch3rtemp.pokemon.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.arch3rtemp.pokemon.data.global.api.PokeService
import dev.arch3rtemp.pokemon.data.local.converter.RoomJsonConverter
import dev.arch3rtemp.pokemon.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRoomJsonConverter(moshi: Moshi): RoomJsonConverter {
        return RoomJsonConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideRetrofitInterface(moshi: Moshi): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun providePokeApi(retrofit: Retrofit): PokeService = retrofit.create(PokeService::class.java)

}