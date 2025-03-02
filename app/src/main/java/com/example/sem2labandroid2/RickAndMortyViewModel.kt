package com.example.sem2labandroid2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RickAndMortyViewModel(
    private val service: RickAndMortyApi = RetrofitClient.RickAndMorty
) : ViewModel() {
    private val _characterData = MutableLiveData<List<Character>>()
    val characterData: LiveData<List<Character>> = _characterData
    public fun fetchCharacters() {
        viewModelScope.launch {
            try {
                val characters = (1..20).map { id ->
                    service.getCharacter(id)
                }
                _characterData.value = characters
            } catch (e: Exception) {

            }
        }


    }

}