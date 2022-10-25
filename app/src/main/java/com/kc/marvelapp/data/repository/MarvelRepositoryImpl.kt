package com.kc.marvelapp.data.repository

import android.util.Log
import com.kc.marvelapp.data.remote.RetrofitInstance
import com.kc.marvelapp.data.local.MarvelDatabase
import com.kc.marvelapp.data.remote.MarvelApi
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.models.ComicCharacter
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelRepositoryImpl @Inject constructor(
    private val api: MarvelApi,
    private val database: MarvelDatabase
    ): MarvelRepository {

    private val TAG = "MarvelRepositoryImpl"

    private val dao = database.dao

    //fun getAllCharactersFromDb() = dao.getAllCharacters()

    /*suspend fun getAllCharactersApiAndUpdateDb(): List<ComicCharacter> {

        var result = listOf<ComicCharacter>()
        val response = RetrofitInstance.api.getAllCharacters()
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                Log.d("MarvelRepository", "api response successfull")
                result = resultResponse.data.characters
                withContext(Dispatchers.IO) {
                    for (character in result) {
                        dao.updateItem(
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
    }*/

    suspend fun addCharacterToFavourite(character: ComicCharacter) {
        dao.upsert(character)
    }

    override suspend fun getCharacterListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<ComicCharacter>>> {
        return flow<Resource<List<ComicCharacter>>> {
            emit(Resource.Loading(true))
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
                dao.deleteAllCharacters()
                dao.insertList(listings)
                /*withContext(Dispatchers.IO) {
                    for (character in listings) {
                        dao.updateItem(
                            modified = character.modified,
                            description = character.description,
                            name = character.name,
                            resourceURI = character.resourceURI,
                            thumbnail = character.thumbnail,
                            id = character.id
                        )
                    }
                    Log.d("MarvelRepository", "Database update successfull")
                }*/
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
        id: Int,
    ): Flow<Resource<ComicCharacter>> {
        return flow<Resource<ComicCharacter>> {
            emit(Resource.Loading(true))
            val localCharacter = dao.getCharacterInfo()
            emit(
                Resource.Success(
                    data = localCharacter
                )
            )
            if (localCharacter != null) {
                Log.d(TAG, "Local character found : ${localCharacter.name}")
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }
}