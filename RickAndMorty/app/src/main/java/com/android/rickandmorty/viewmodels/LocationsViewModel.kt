package com.android.rickandmorty.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.rickandmorty.api.ApiRepository
import com.android.rickandmorty.model.Characters
import com.android.rickandmorty.model.Location

class LocationsViewModel: ViewModel() {
    //номера страниц
    var pageNumber: Int
    val MAX_PAGE_NUMBER = 6
    //репозиторий api
    val apiRepository: ApiRepository?
    //список данных для вывода в список
    var locationsLiveData: LiveData<List<Location>>

    init {
        apiRepository =  ApiRepository.getInstance()
        pageNumber = 1
        locationsLiveData = apiRepository!!.getLocations(pageNumber)
    }
    //выполнение запросов и обновление данных списка
    fun updateLocationsList() {
        locationsLiveData = apiRepository!!.getLocations(pageNumber)
    }

    fun searchLocations(name: String){
        locationsLiveData = apiRepository!!.searchLocations(name)
    }
}
