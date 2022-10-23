package com.kc.marvelapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.marvelapp.models.AllCharactersResponse
import com.kc.marvelapp.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class MarvelViewModel(val marvelRepository: MarvelRepository): ViewModel() {

    val allCharacters: MutableLiveData<Resource<AllCharactersResponse>> = MutableLiveData()

    fun getAllCharacters() {
        viewModelScope.launch {
            allCharacters.postValue(Resource.Loading())
            val response = marvelRepository.getAllCharacters()
            val result = handleAllCharactersResponse(response)
            allCharacters.postValue(result)
        }
    }

    private fun handleAllCharactersResponse(response: Response<AllCharactersResponse>): Resource<AllCharactersResponse> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}