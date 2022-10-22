package com.kc.marvelapp.repository

import com.kc.marvelapp.api.RetrofitInstance
import com.kc.marvelapp.db.CharacterDatabase

class MarvelRepository (db: CharacterDatabase){
    suspend fun getAllCharacters() =
        RetrofitInstance.api.getAllCharacters()
}