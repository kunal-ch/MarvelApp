package com.kc.marvelapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kc.marvelapp.repository.MarvelRepository

class MarvelViewModelFactory(val marvelRepository: MarvelRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MarvelViewModel(marvelRepository) as T
    }
}