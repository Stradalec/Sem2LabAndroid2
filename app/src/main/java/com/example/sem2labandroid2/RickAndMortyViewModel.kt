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
    fun fetchCharacters(start: Int, end: Int) {
        viewModelScope.launch {
            try {
                val characters = (start..end).map { id ->
                    service.getCharacter(id)
                }
                _characterData.value = characters
            } catch (e: Exception) {

            }
        }


    }

}