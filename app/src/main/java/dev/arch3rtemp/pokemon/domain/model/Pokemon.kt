package dev.arch3rtemp.pokemon.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.arch3rtemp.pokemon.data.local.converter.RoomJsonConverter

@JsonClass(generateAdapter = true)
@Entity(tableName = "poke_table")
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    @Json(name = "sprites")
    val image: SpriteResponse,
    var dominantColor: Int?,
    @TypeConverters(RoomJsonConverter::class)
    @Json(name = "types")
    @ColumnInfo(name = "types")
    val typeList: List<TypeResponse>,
    @TypeConverters(RoomJsonConverter::class)
    @Json(name = "stats")
    @ColumnInfo(name = "stats")
    val statList: List<StatResponse>
)
