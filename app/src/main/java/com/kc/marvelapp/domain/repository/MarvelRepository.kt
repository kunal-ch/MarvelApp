package com.kc.marvelapp.domain.repository

import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Repository to get character listing and info
 */
interface MarvelRepository {
    suspend fun getCharacterListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ComicCharacter>>>

    suspend fun getCharacterInfo(
        id: String
    ): Flow<Resource<ComicCharacter>>
}