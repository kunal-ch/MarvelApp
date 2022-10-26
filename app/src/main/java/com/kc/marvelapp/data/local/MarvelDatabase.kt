package com.kc.marvelapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kc.marvelapp.domain.models.ComicCharacter

@Database(
    entities = [ComicCharacter::class],
    version = 1
)
@TypeConverters(CharacterConverter::class)
abstract class MarvelDatabase: RoomDatabase() {
    abstract val dao: CharacterDao
}