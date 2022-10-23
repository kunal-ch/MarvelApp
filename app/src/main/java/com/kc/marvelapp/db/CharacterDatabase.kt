package com.kc.marvelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kc.marvelapp.models.ComicCharacter

@Database(
    entities = [ComicCharacter::class],
    version = 1
)
@TypeConverters(CharacterConverter::class)
abstract class CharacterDatabase: RoomDatabase() {

    abstract fun getCharacterDao() : CharacterDao

    companion object {
        // Other threads can easily see when a thread changes this instance
        @Volatile
        private var INSTANCE: CharacterDatabase? = null
        // Makes sure that only one instance is created
        private val Lock = Any()

        // When CharacterDatabase() constructor is called it will call below method
        operator fun invoke(context: Context) {
            getDatabase(context)
        }

        fun getDatabase(context: Context): CharacterDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "character_db.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}