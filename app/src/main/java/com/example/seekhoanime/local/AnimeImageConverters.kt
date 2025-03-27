package com.example.seekhoanime.local

import androidx.room.TypeConverter
import com.example.seekhoanime.domain.model.Aired
import com.example.seekhoanime.domain.model.Broadcast
import com.example.seekhoanime.domain.model.Images
import com.example.seekhoanime.domain.model.Title
import com.example.seekhoanime.domain.model.Trailer
import com.example.seekhoanime.domain.model.TrailerInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AnimeImageConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromTitleList(value: List<Title>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTitleList(value: String?): List<Title>? {
        val type = object : TypeToken<List<Title>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTrailerInfoList(value: List<TrailerInfo>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toTrailerInfoList(value: String?): List<TrailerInfo>? {
        val type = object : TypeToken<List<TrailerInfo>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromImages(images: Images): String = gson.toJson(images)

    @TypeConverter
    fun toImages(data: String): Images = gson.fromJson(data, Images::class.java)

    @TypeConverter
    fun fromTrailer(trailer: Trailer): String = gson.toJson(trailer)

    @TypeConverter
    fun toTrailer(data: String): Trailer = gson.fromJson(data, Trailer::class.java)

    @TypeConverter
    fun fromAired(aired: Aired): String = gson.toJson(aired)

    @TypeConverter
    fun toAired(data: String): Aired = gson.fromJson(data, Aired::class.java)

    @TypeConverter
    fun fromBroadcast(broadcast: Broadcast): String = gson.toJson(broadcast)

    @TypeConverter
    fun toBroadcast(data: String): Broadcast = gson.fromJson(data, Broadcast::class.java)

    @TypeConverter
    fun fromStringList(value: List<String>?): String? = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromExplicitGenres(value: List<Any?>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExplicitGenres(value: String): List<Any?> {
        val type = object : TypeToken<List<Any?>>() {}.type
        return gson.fromJson(value, type)
    }
}