package com.kc.marvelapp.repository

import android.util.Log
import com.kc.marvelapp.api.RetrofitInstance
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.models.ComicCharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MarvelRepository(val database: CharacterDatabase) {

    fun getAllCharactersFromDb() = database.getCharacterDao().getAllCharacters()

    suspend fun getAllCharactersApiAndUpdateDb(): List<ComicCharacter> {

        var result = listOf<ComicCharacter>()
        val response = RetrofitInstance.api.getAllCharacters()
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                Log.d("MarvelRepository", "api response successfull")
                result = resultResponse.data.characters
                withContext(Dispatchers.IO) {
                    for (character in result) {
                        database.getCharacterDao().updateItem(
                            modified = character.modified,
                            description = character.description,
                            name = character.name,
                            resourceURI = character.resourceURI,
                            thumbnail = character.thumbnail,
                            id = character.id
                        )
                    }
                }
                //database.getCharacterDao().insertList(result)
            }
        }
        return result
    }

    suspend fun addCharacterToFavourite(character: ComicCharacter) {
        database.getCharacterDao().upsert(character)
    }
}