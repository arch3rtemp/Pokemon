package dev.arch3rtemp.pokemon.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.arch3rtemp.pokemon.domain.model.Pokemon

@Dao
interface PokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokes(pokes: List<Pokemon>)

    @Query("SELECT * FROM poke_table")
    fun selectPokes(): List<Pokemon>

    @Query("SELECT * FROM poke_table WHERE id == :id ")
    fun selectPoke(id: Int): Pokemon
}