package com.kc.marvelapp.presentation.character_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kc.marvelapp.domain.repository.MarvelRepository
import com.kc.marvelapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    private val repository: MarvelRepository
): ViewModel() {

    var state by mutableStateOf(CharacterInfoState())

    init {
        getCharacterInfo()
    }

    private fun getCharacterInfo() {
        viewModelScope.launch {
            repository
                .getCharacterInfo(10009144)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { character ->
                                state = state.copy(
                                    character = character
                                )
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }
}