package com.kc.marvelapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kc.marvelapp.models.ComicCharacter

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(character: ComicCharacter) : Long

    @Query("SELECT * from characters")
    fun getAllCharacters(): LiveData<List<ComicCharacter>>

    @Delete
    suspend fun deleteCharacter(character: ComicCharacter)
}