package com.kc.marvelapp.data.local

import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.domain.models.Thumbnail

class CharacterDaoFake: CharacterDao {

    private var characterListing = emptyList<ComicCharacter>()

    override suspend fun insertList(characters: List<ComicCharacter>) {
        characterListing = characterListing + characters
    }

    override suspend fun searchCharacterListing(query: String): List<ComicCharacter> {
        return characterListing.filter {
            it.name.lowercase().contains(query.lowercase())
        }
    }

    override suspend fun upsert(character: ComicCharacter): Long {
        return -1
    }

    override fun updateItem(
        modified: String,
        description: String,
        name: String,
        resourceURI: String,
        thumbnail: Thumbnail,
        id: Int
    ): Int {
        return -1
    }
}