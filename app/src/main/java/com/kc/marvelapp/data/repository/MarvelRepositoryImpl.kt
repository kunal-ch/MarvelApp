package com.kc.marvelapp.data.repository

import android.util.Log
import com.kc.marvelapp.data.local.MarvelDatabase
import com.kc.marvelapp.data.remote.MarvelApi
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.domain.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository Implementation
 */
@Singleton
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val database: MarvelDatabase
    ): MarvelRepository {

    private val TAG = "MarvelRepositoryImpl"

    private val dao = database.dao

    suspend fun addCharacterToFavourite(character: ComicCharacter) {
        dao.upsert(character)
    }

    override suspend fun getCharacterListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ComicCharacter>>> {
        return flow<Resource<List<ComicCharacter>>> {
            emit(Resource.Loading(true))

            // First fetch from database
            val localListings = dao.searchCharacterListing(query)
            emit(
                Resource.Success(
                    data = localListings
                )
            )
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                Log.d(TAG, "List from Database : ${localListings.size}")
                emit(Resource.Loading(false))
                return@flow
            }

            // If database is empty then fetch from network
            val remoteListings = try {
                val response = api.getAllCharacters()
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        Log.d("MarvelRepository", "api response successfull")
                        resultResponse.data.characters
                    }
                } else {
                    listOf()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            Log.d(TAG, "Api list size : ${remoteListings?.size}")
            remoteListings?.let { listings ->

                // Update the database
                dao.insertList(listings)
                Log.d("MarvelRepository", "Now load from Database")
                emit(
                    Resource.Success(
                        data = dao
                            .searchCharacterListing("")
                    )
                )
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCharacterInfo(
        id: String,
    ): Flow<Resource<ComicCharacter>> {
        return flow<Resource<ComicCharacter>> {
            emit(Resource.Loading(true))

            val remoteListings = try {
                val response = api.getCharacter(id)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        Log.d("MarvelRepository", "Character detail api response successfull")
                        resultResponse.data.characters
                    }
                } else {
                    listOf()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let { remoteListings
                if (remoteListings.isNotEmpty()) {
                    emit(
                        Resource.Success(
                            data = remoteListings[0]
                        )
                    )
                }
            }
            emit(Resource.Loading(false))
            return@flow
        }
    }
}