package com.kc.marvelapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.marvelapp.models.AllCharactersResponse
import com.kc.marvelapp.models.ComicCharacter
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MarvelViewModel(val marvelRepository: MarvelRepository): ViewModel() {

    val allCharacters: MutableLiveData<Resource<List<ComicCharacter>>> = MutableLiveData()

    private fun getAllCharactersApiAndUpdateDb(){
        viewModelScope.launch {
            marvelRepository.getAllCharactersApiAndUpdateDb()
        }
    }

    fun getGetAllCharacterFromDB(): LiveData<List<ComicCharacter>> {
        getAllCharactersApiAndUpdateDb()
        return marvelRepository.getAllCharactersFromDb()
    }

    private fun handleAllCharactersResponse(response: Response<AllCharactersResponse>): Resource<AllCharactersResponse> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavourites(character: ComicCharacter) {
        viewModelScope.launch {
            marvelRepository.addCharacterToFavourite(character)
        }
    }
}