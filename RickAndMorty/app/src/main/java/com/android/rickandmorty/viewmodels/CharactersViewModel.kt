package com.android.rickandmorty.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.rickandmorty.api.ApiRepository
import com.android.rickandmorty.model.Characters

class CharactersViewModel: ViewModel() {
    //номера страниц
    var pageNumber: Int
    val MAX_PAGE_NUMBER = 34
    //репозиторий api
    val apiRepository: ApiRepository?
    //список данных для вывода в список
    var charactersLiveData: LiveData<List<Characters>>

    init {
        apiRepository =  ApiRepository.getInstance()
        pageNumber = 1
        charactersLiveData = apiRepository!!.getCharacters(pageNumber)
    }
    //выполнение запросов и обновление данных списка
    fun updateCharactersList() {
        charactersLiveData = apiRepository!!.getCharacters(pageNumber)
    }

    fun searchCharacters(name: String){
        charactersLiveData = apiRepository!!.searchCharacters(name)
    }

    fun filterCharacters(status: String, gender: String){
        charactersLiveData = apiRepository!!.findCharacters(status, gender)
    }
}
