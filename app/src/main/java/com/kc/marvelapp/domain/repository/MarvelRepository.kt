package com.kc.marvelapp.domain.repository

import com.kc.marvelapp.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {
    suspend fun getCharacterListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ComicCharacter>>>

    suspend fun getCharacterInfo(
        id: Int
    ): Flow<Resource<ComicCharacter>>
}