package com.kc.marvelapp.data.local

import androidx.room.*
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.models.Thumbnail

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(character: ComicCharacter) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(character: List<ComicCharacter>)

    @Query("UPDATE characters SET modified = :modified ,description= :description,name= :name, resourceURI= :resourceURI,thumbnail= :thumbnail   WHERE id LIKE :id ")
    fun updateItem(modified: String, description: String, name: String, resourceURI: String, thumbnail: Thumbnail, id: Int): Int

    @Query(
        """
            SELECT * 
            FROM characters
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchCharacterListing(query: String): List<ComicCharacter>
}