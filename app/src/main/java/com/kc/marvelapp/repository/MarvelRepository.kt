package com.kc.marvelapp.repository

import android.util.Log
import com.kc.marvelapp.api.RetrofitInstance
import com.kc.marvelapp.db.CharacterDatabase
import com.kc.marvelapp.models.ComicCharacter

class MarvelRepository(val database: CharacterDatabase) {

    fun getAllCharactersFromDb() = database.getCharacterDao().getAllCharacters()

    suspend fun getAllCharactersApiAndUpdateDb(): List<ComicCharacter> {

        var result = listOf<ComicCharacter>()
        val response = RetrofitInstance.api.getAllCharacters()
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                Log.d("MarvelRepository", "api response successfull")
                result = resultResponse.data.characters
                database.getCharacterDao().insertList(result)
            }
        }
        return result
    }
}