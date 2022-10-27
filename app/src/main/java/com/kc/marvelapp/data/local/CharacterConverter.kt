package com.kc.marvelapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.kc.marvelapp.domain.models.Comics
import com.kc.marvelapp.domain.models.Item
import com.kc.marvelapp.domain.models.Thumbnail

class CharacterConverter {

    @TypeConverter
    fun fromThumbnail(thumbnail: Thumbnail): String {
        return Gson().toJson(thumbnail)
    }

    @TypeConverter
    fun toThumbnail(thumbnail: String): Thumbnail {
        return Gson().fromJson(thumbnail, Thumbnail::class.java)
    }

    @TypeConverter
    fun fromComics(comics: Comics): String {
        return Gson().toJson(comics)
    }

    @TypeConverter
    fun toComics(comics: String): Comics {
        return Gson().fromJson(comics, Comics::class.java)
    }

    @TypeConverter
    fun fromItem(item: Item): String {
        return Gson().toJson(item)
    }

    @TypeConverter
    fun toItem(item: String): Item {
        return Gson().fromJson(item, Item::class.java)
    }
}