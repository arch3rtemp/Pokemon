package dev.arch3rtemp.pokemon.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dev.arch3rtemp.pokemon.domain.model.SpriteResponse
import dev.arch3rtemp.pokemon.domain.model.StatResponse
import dev.arch3rtemp.pokemon.domain.model.TypeResponse
import javax.inject.Inject

@ProvidedTypeConverter
class RoomJsonConverter @Inject constructor(private val moshi: Moshi) {

    // Convert List<Stats> to JSON
    @TypeConverter
    fun statsListToJson(statsList: List<StatResponse>): String {
        val adapter: JsonAdapter<List<StatResponse>> = moshi.adapter(Types.newParameterizedType(List::class.java, StatResponse::class.java))
        return adapter.toJson(statsList)
    }

    // Convert JSON to List<Stats>
    @TypeConverter
    fun jsonToStatsList(json: String): List<StatResponse>? {
        val adapter: JsonAdapter<List<StatResponse>> = moshi.adapter(Types.newParameterizedType(List::class.java, StatResponse::class.java))
        return adapter.fromJson(json)
    }

    // Convert List<Types> to JSON
    @TypeConverter
    fun typesListToJson(typesList: List<TypeResponse>): String {
        val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(Types.newParameterizedType(List::class.java, TypeResponse::class.java))
        return adapter.toJson(typesList)
    }

    // Convert JSON to List<Types>
    @TypeConverter
    fun jsonToTypesList(json: String): List<TypeResponse>? {
        val adapter: JsonAdapter<List<TypeResponse>> = moshi.adapter(Types.newParameterizedType(List::class.java, TypeResponse::class.java))
        return adapter.fromJson(json)
    }

    // Convert Sprites to JSON
    @TypeConverter
    fun spritesToJson(sprite: SpriteResponse): String {
        val adapter: JsonAdapter<SpriteResponse> = moshi.adapter(SpriteResponse::class.java)
        return adapter.toJson(sprite)
    }

    // Convert JSON to Sprites
    @TypeConverter
    fun jsonToSprites(json: String): SpriteResponse? {
        val adapter: JsonAdapter<SpriteResponse> = moshi.adapter(SpriteResponse::class.java)
        return adapter.fromJson(json)
    }
}