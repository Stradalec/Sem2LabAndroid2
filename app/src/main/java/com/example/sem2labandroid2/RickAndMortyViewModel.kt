package com.example.sem2labandroid2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivityViewModel(
    private val service: RickAndMortyApi = RetrofitClient.RickAndMorty
) : ViewModel() {
    private val _characterData = MutableLiveData<List<Character>>()
    val characterData: LiveData<List<Character>> = _characterData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCharacters(start: Int, end: Int) {
        viewModelScope.launch {
            try {
                val characters = (start..end).map { id ->
                    service.getCharacter(id)
                }
                _characterData.value = characters
            } catch (e: Exception) {
                when (e) {
                    is IOException -> _error.value = "Ошибка сети. Проверьте подключение к интернету."
                    is HttpException -> _error.value = "Ошибка сервера: ${e.code()}. ${e.message()}"
                    else -> _error.value = "Произошла ошибка: ${e.message}"
                }
            }
        }
    }
}